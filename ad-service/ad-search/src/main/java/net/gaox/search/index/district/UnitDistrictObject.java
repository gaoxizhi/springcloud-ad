package net.gaox.search.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 地域索引对象 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictObject {

    /**
     * 推广单元id
     */
    private Long unitId;
    /**
     * 推广省份
     */
    private String province;
    /**
     * 推广城市
     */
    private String city;

    // <String, Set<Long>>
    /**
     * 反向索引构造方式，索引字段组成
     * */
    // province-city
}
