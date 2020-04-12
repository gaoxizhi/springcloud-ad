package net.gaox.search.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 创意单元与推广单元 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {
    /**
     * 创意单元id
     */
    private Long adId;
    /**
     * 推广单元id
     */
    private Long unitId;
    /**
     * 索引定义方式 adId-unitId
     */
}