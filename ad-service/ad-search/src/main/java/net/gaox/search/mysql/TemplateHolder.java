package net.gaox.search.mysql;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.gaox.search.mysql.constant.OpType;
import net.gaox.search.mysql.dto.ParseTemplate;
import net.gaox.search.mysql.dto.TableTemplate;
import net.gaox.search.mysql.dto.Template;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SCHEMA = "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * PostConstruct 当容器加载时运行初始化函数
     */
    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    /**
     * 通过表名获取列名列表
     *
     * @param tableName 表名
     * @return 表参数
     */
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);

        try {
            Template template = JSON.parseObject(inStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }

    private void loadMeta() {

        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {

            TableTemplate table = entry.getValue();

            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{template.getDatabase(), table.getTableName()}, (rs, i) -> {

                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                boolean checked = (null != updateFields && updateFields.contains(colName));
                checked |= (null != insertFields && insertFields.contains(colName));
                checked |= (null != deleteFields && deleteFields.contains(colName));

                if (checked) {
                    table.getPosMap().put(pos - 1, colName);
                }
                return null;
            });
        }
    }
}