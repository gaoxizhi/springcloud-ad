package net.gaox.search.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 23:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanGetRequest {
    private Long userId;
    private List<Long> ids;
}