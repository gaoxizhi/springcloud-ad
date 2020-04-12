package net.gaox.search.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.common.annotation.IgnoreResponseAdvice;
import net.gaox.common.api.ApiResponse;
import net.gaox.search.client.SponsorClient;
import net.gaox.search.model.vo.AdPlanGetRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 23:42
 */
@Slf4j
@RestController
public class SearchController {
    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public ApiResponse getAdPlans(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlans -> {}", JSONObject.toJSONString(request));
        ApiResponse body = sponsorClient.getAdPlans(request);
        log.info("get 【{}】", JSONObject.toJSONString(body));
        return body;
    }

    /**
     * 使用ribbon调用微服务接口
     *
     * @param request
     * @return
     */
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public ApiResponse getAdPlansByRebbon(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlansByRibbon -> {}", JSONObject.toJSONString(request));
        ApiResponse body = restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan", request, ApiResponse.class
        ).getBody();
        log.info("get 【{}】", JSONObject.toJSONString(body));
        return body;
    }
}