package net.gaox.search.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictFeature {

    private List<ProvinceAndCity> districts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvinceAndCity {

        private String province;
        private String city;
    }
}