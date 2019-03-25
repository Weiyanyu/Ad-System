package top.yeonon.adsponsor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.constant.CommonStatus;
import top.yeonon.adsponsor.constant.Constants;
import top.yeonon.adsponsor.entity.AdPlan;
import top.yeonon.adsponsor.entity.AdUser;
import top.yeonon.adsponsor.repository.AdPlanRepository;
import top.yeonon.adsponsor.repository.AdUserRepository;
import top.yeonon.adsponsor.service.IAdPlanService;
import top.yeonon.adsponsor.utils.CommonUtils;
import top.yeonon.adsponsor.vo.request.AdPlanGetRequest;
import top.yeonon.adsponsor.vo.request.AdPlanRequest;
import top.yeonon.adsponsor.vo.response.AdPlanResponse;

import java.util.Date;
import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 12:51
 **/
@Service
@Slf4j
public class AdPlanServiceImpl implements IAdPlanService {


    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUserRepository adUserRepository;

    @Override
    public AdPlanResponse createAdPlan(AdPlanRequest adPlanRequest) throws AdException {
        if (!adPlanRequest.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //检查请求中的用户是否存在
        AdUser adUser = adUserRepository
                .findById(adPlanRequest.getUserId())
                .orElse(null);
        if (adUser == null) {
            //不存在该用户
            throw new AdException(Constants.ErrorMsg.NOT_FOUND_USER);
        }

        AdPlan oldPlan = adPlanRepository.findByUserIdAndPlanName(adPlanRequest.getUserId(),
                                adPlanRequest.getPlanName());
        //业务中同一用户不允许存在相同的计划名称
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_PLAN_NAME_ERROR);
        }

        //创建并保存新的计划
        AdPlan newAdPlan = adPlanRepository.save(new AdPlan(
                adPlanRequest.getUserId(),
                adPlanRequest.getPlanName(),
                CommonUtils.stringToDate(adPlanRequest.getStartDate()),
                CommonUtils.stringToDate(adPlanRequest.getEndDate())
        ));

        //返回响应对象
        return new AdPlanResponse(
                newAdPlan.getId(),
                newAdPlan.getPlanName()
        );
    }

    @Override
    public AdPlanResponse updateAdPlan(AdPlanRequest adPlanRequest) throws AdException {
        if (!adPlanRequest.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //根据用户ID以及计划ID查找对象
        AdPlan adPlan = adPlanRepository.findByIdAndUserId(adPlanRequest.getId(), adPlanRequest.getUserId());

        //如果对象为空，则表示不存在满足条件的计划，更新就没有意义，直接结束流程即可
        if (adPlan == null) {
            throw new AdException(Constants.ErrorMsg.NOT_FOUND_PLAN);
        }

        //因为JPA不支持部分更新，故需要自己写判断逻辑来实现部分更新
        if (adPlanRequest.getPlanName() != null) {
            adPlan.setPlanName(adPlanRequest.getPlanName());
        }
        if (adPlanRequest.getStartDate() != null) {
            adPlan.setStartDate(CommonUtils.stringToDate(adPlanRequest.getStartDate()));
        }

        if (adPlanRequest.getEndDate() != null) {
            adPlan.setEndDate(CommonUtils.stringToDate(adPlanRequest.getEndDate()));
        }

        adPlan.setUpdateTime(new Date());

        adPlan = adPlanRepository.save(adPlan);

        //返回由更新后的对象构造的响应对象
        return new AdPlanResponse(
                adPlan.getId(),
                adPlan.getPlanName());
    }

    @Override
    public List<AdPlan>getAdPlans(AdPlanGetRequest adPlanGetRequest) throws AdException {
        if (!adPlanGetRequest.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return adPlanRepository.
                findAllByIdInAndUserId(adPlanGetRequest.getIds(),
                        adPlanGetRequest.getUserId());

    }

    @Override
    public void deleteAdPlan(AdPlanRequest adPlanRequest) throws AdException {

        if (!adPlanRequest.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //查找对象
        AdPlan adPlan = adPlanRepository.findByIdAndUserId(adPlanRequest.getId(), adPlanRequest.getUserId());
        if (adPlan == null) {
            //没有找到对象，直接返回即可
            throw new AdException(Constants.ErrorMsg.NOT_FOUND_PLAN);
        }

        //这里的删除只是设置状态为非法状态，并不是真的从数据库里删除对象
        //可以开启一个定时任务定期的清理无效记录
        adPlan.setPlanStatus(CommonStatus.INVAILD.getCode());
        adPlan.setUpdateTime(new Date());
        adPlanRepository.save(adPlan);
    }
}
