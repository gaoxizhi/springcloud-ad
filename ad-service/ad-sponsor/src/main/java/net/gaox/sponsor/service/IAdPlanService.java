package net.gaox.sponsor.service;

import net.gaox.common.api.ApiException;
import net.gaox.sponsor.entity.AdPlan;
import net.gaox.sponsor.model.vo.AdPlanGetRequest;
import net.gaox.sponsor.model.vo.AdPlanRequest;
import net.gaox.sponsor.model.vo.AdPlanResponse;

import java.util.List;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
public interface IAdPlanService {

    /**
     * <h2>创建推广计划</h2>
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws ApiException;

    /**
     * <h2>获取推广计划</h2>
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws ApiException;

    /**
     * <h2>更新推广计划</h2>
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws ApiException;

    /**
     * <h2>删除推广计划</h2>
     */
    void deleteAdPlan(AdPlanRequest request) throws ApiException;
}
