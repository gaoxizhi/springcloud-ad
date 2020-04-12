package net.gaox.search.client;

import net.gaox.common.api.ApiResponse;
import net.gaox.search.model.vo.AdPlanGetRequest;
import org.springframework.stereotype.Component;

/**
 * <p> 服务降级 </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/19 00:50
 */

@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public ApiResponse getAdPlans(AdPlanGetRequest request) {
        return ApiResponse.fail().error("eureka-client-ad-sponsor error");
    }
}