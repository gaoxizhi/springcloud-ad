package net.gaox.search.index.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitItObject {

    /**
     * 推广id
     */
    private Long unitId;
    /**
     * 标签
     */
    private String itTag;
}
