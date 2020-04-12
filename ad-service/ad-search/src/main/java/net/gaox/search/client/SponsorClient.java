package net.gaox.search.client;

import net.gaox.common.api.ApiResponse;
import net.gaox.search.model.vo.AdPlanGetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p> Feign远程调用接口配置 </p>
 * FeignClient指定调用的应用
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/19 00:47
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    /**
     * 使用feign调用接口
     * 指定调用接口，接口方法
     *
     * @param request
     * @return
     */
    @PostMapping("/ad-sponsor/get/adPlan")
    ApiResponse getAdPlans(@RequestBody AdPlanGetRequest request);
}