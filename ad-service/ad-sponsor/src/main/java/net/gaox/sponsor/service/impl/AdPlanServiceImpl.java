package net.gaox.sponsor.service.impl;

import net.gaox.common.api.ApiException;
import net.gaox.sponsor.constant.CommonStatus;
import net.gaox.sponsor.constant.Constants;
import net.gaox.sponsor.dao.AdPlanRepository;
import net.gaox.sponsor.dao.AdUserRepository;
import net.gaox.sponsor.entity.AdPlan;
import net.gaox.sponsor.entity.AdUser;
import net.gaox.sponsor.service.IAdPlanService;
import net.gaox.sponsor.utils.CommonUtils;
import net.gaox.sponsor.model.vo.AdPlanGetRequest;
import net.gaox.sponsor.model.vo.AdPlanRequest;
import net.gaox.sponsor.model.vo.AdPlanResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserRepository userRepository;
    private final AdPlanRepository planRepository;

    public AdPlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request)
            throws ApiException {

        if (!request.createValidate()) {
            throw new ApiException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 确保关联的 User 是存在的
        Optional<AdUser> adUser =
                userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new ApiException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(
                request.getUserId(), request.getPlanName()
        );
        if (oldPlan != null) {
            throw new ApiException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        AdPlan newAdPlan = planRepository.save(
                new AdPlan(request.getUserId(), request.getPlanName(),
                        CommonUtils.parseStringDate(request.getStartDate()),
                        CommonUtils.parseStringDate(request.getEndDate())
                )
        );

        return new AdPlanResponse(newAdPlan.getId(),
                newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request)
            throws ApiException {

        if (!request.validate()) {
            throw new ApiException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return planRepository.findAllByIdInAndUserId(
                request.getIds(), request.getUserId()
        );
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request)
            throws ApiException {

        if (!request.updateValidate()) {
            throw new ApiException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByIdAndUserId(
                request.getId(), request.getUserId()
        );
        if (plan == null) {
            throw new ApiException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            plan.setStartDate(
                    CommonUtils.parseStringDate(request.getStartDate())
            );
        }
        if (request.getEndDate() != null) {
            plan.setEndDate(
                    CommonUtils.parseStringDate(request.getEndDate())
            );
        }

        plan.setUpdateTime(new Date());
        plan = planRepository.save(plan);

        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws ApiException {

        if (!request.deleteValidate()) {
            throw new ApiException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByIdAndUserId(
                request.getId(), request.getUserId()
        );
        if (plan == null) {
            throw new ApiException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }
}