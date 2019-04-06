package top.yeonon.adsponsor.service;

import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.vo.request.CreateAdUserRequest;
import top.yeonon.adsponsor.vo.response.CreateAdUserResponse;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 12:03
 **/
public interface IAdUserService {

    CreateAdUserResponse createAdUser(CreateAdUserRequest createAdUserRequest)
            throws AdException;
}
