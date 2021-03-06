package net.gaox.search.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据变更
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    private String tableName;
    private Integer level;

    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {
        private String column;
    }
}