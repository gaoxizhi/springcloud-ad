package net.gaox.search.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.gaox.search.mysql.constant.OpType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引 -> 字段名
     * */
    private Map<Integer, String> posMap = new HashMap<>();
}
