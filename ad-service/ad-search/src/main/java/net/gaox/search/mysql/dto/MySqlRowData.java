package net.gaox.search.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.gaox.search.mysql.constant.OpType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简化的索引数据投递对象
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 层级
     */
    private String level;
    /**
     * 操作类型
     */
    private OpType opType;
    /**
     * 数据列表
     */
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}