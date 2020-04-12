package net.gaox.search.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 从binlog解析到Java对象，映射关系类
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
public class BinlogRowData {
    /**
     * 表名
     */
    private TableTemplate table;
    /**
     * 操作类型
     */
    private EventType eventType;
    /**
     * 变更前
     */
    private List<Map<String, String>> after;
    /**
     * 变更后
     */
    private List<Map<String, String>> before;
}
