package top.yeonon.adsponsor.service;

import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.entity.AdPlan;
import top.yeonon.adsponsor.repository.AdPlanRepository;
import top.yeonon.adsponsor.vo.request.AdPlanGetRequest;
import top.yeonon.adsponsor.vo.request.AdPlanRequest;
import top.yeonon.adsponsor.vo.response.AdPlanResponse;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 12:46
 **/
public interface IAdPlanService {

    /**
     * 创建推广计划
     * @param adPlanRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 自定义广告系统异常
     */
    AdPlanResponse createAdPlan(AdPlanRequest adPlanRequest)
        throws AdException;

    /**
     * 更新推广计划
     * @param adPlanRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 自定义广告系统异常
     */
    AdPlanResponse updateAdPlan(AdPlanRequest adPlanRequest)
        throws AdException;

    /**
     * 获取广告计划（批量）
     * @param adPlanGetRequest 广告计划获取VO对象
     * @return 响应VO对象
     * @throws AdException 可能抛出的异常
     */
    List<AdPlan> getAdPlans(AdPlanGetRequest adPlanGetRequest)
        throws AdException;

    /**
     * 删除广告计划
     * @param adPlanRequest 广告计划请求VO对象
     * @throws AdException 可能抛出的异常
     */
    void deleteAdPlan(AdPlanRequest adPlanRequest)
        throws AdException;
}
