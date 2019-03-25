package top.yeonon.adsponsor.service;

import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.vo.request.AdCreativeRequest;
import top.yeonon.adsponsor.vo.response.AdCreativeResponse;

/**
 * @Author yeonon
 * @date 2019/3/25 0025 11:46
 **/
public interface IAdCreativeService {

    /**
     * 创建推广创意
     * @param adCreativeRequest 请求VO对象
     * @return 响应VO对象
     * @throws AdException 可能抛出的异常
     */
    AdCreativeResponse createCreative(AdCreativeRequest adCreativeRequest)
        throws AdException;
}
