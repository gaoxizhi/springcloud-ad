package net.gaox.search.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import net.gaox.search.mysql.TemplateHolder;
import net.gaox.search.mysql.dto.BinlogRowData;
import net.gaox.search.mysql.dto.TableTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 收集并解析binlog
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 监听map
     */
    private Map<String, Ilistener> listenerMap = new HashMap<>();
    /**
     * 模版信息
     */
    private final TemplateHolder templateHolder;

    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    /**
     * 注册方法
     *
     * @param dbName    数据库名
     * @param tableName 表名
     * @param iListener 监听
     */
    public void register(String dbName, String tableName, Ilistener iListener) {
        log.info("register : {}-{}", dbName, tableName);
        this.listenerMap.put(genKey(dbName, tableName), iListener);
    }

    @Override
    public void onEvent(Event event) {

        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}", type);

        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // 表名和库名是否已经完成填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器
        String key = genKey(this.dbName, this.tableName);
        Ilistener listener = this.listenerMap.get(key);
        if (null == listener) {
            log.debug("skip {}", key);
            return;
        }

        log.info("trigger event: {}", type.name());
        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    /**
     * 获取更新后的数据
     *
     * @param eventData 事件数据
     * @return 数据列表
     */
    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue).collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }

    private BinlogRowData buildRowData(EventData eventData) {

        TableTemplate table = templateHolder.getTable(tableName);
        if (null == table) {
            log.warn("table {} not found", tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>(16);
            int colLen = after.length;
            for (int i = 0; i < colLen; ++i) {
                // 取出当前位置对应的列名
                String colName = table.getPosMap().get(i);
                // 如果没有则说明不关心这个列
                if (null == colName) {
                    log.debug("ignore position: {}", i);
                    continue;
                }

                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }

        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);

        return rowData;
    }
}