package net.gaox.search.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 关键词搜索单元 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordObject {

    private Long unitId;
    private String keyword;
}
