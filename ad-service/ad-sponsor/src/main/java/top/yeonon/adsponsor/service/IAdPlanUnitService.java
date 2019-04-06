package top.yeonon.adsponsor.service;

import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.entity.AdUnit;
import top.yeonon.adsponsor.vo.request.*;
import top.yeonon.adsponsor.vo.response.*;

/**
 * @Author yeonon
 * @date 2019/3/24 0024 11:28
 **/
public interface IAdPlanUnitService {

    /**
     * 创建推广计划单元
     *
     * @param adPlanUnitRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 可能抛出的异常
     */
    AdPlanUnitResponse createPlanUnit(AdPlanUnitRequest adPlanUnitRequest)
            throws AdException;

    /**
     * 创建关键字约束
     *
     * @param adPlanUnitKeywordRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 可能抛出的异常
     */
    AdPlanUnitKeywordResponse createKeyword(AdPlanUnitKeywordRequest adPlanUnitKeywordRequest)
            throws AdException;

    /**
     * 创建兴趣约束
     *
     * @param adPlanUnitItRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 可能抛出的异常
     */
    AdPlanUnitItResponse createIt(AdPlanUnitItRequest adPlanUnitItRequest)
            throws AdException;

    /**
     * 创建地域约束
     *
     * @param adPlanUnitDistrictRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 可能抛出的异常
     */
    AdPlanUnitDistrictResponse createDistrict(AdPlanUnitDistrictRequest adPlanUnitDistrictRequest)
            throws AdException;


    /**
     * 创建Creative 和 Unit 的关联记录
     *
     * @param adCreativeUnitRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 可能抛出的异常
     */
    AdCreativeUnitResponse createCreativeUnit(AdCreativeUnitRequest adCreativeUnitRequest)
            throws AdException;
}
