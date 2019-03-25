package top.yeonon.adsponsor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.constant.Constants;
import top.yeonon.adsponsor.entity.AdUser;
import top.yeonon.adsponsor.repository.AdUserRepository;
import top.yeonon.adsponsor.service.IAdUserService;
import top.yeonon.adsponsor.utils.CommonUtils;
import top.yeonon.adsponsor.vo.request.CreateAdUserRequest;
import top.yeonon.adsponsor.vo.response.CreateAdUserResponse;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 12:04
 **/
@Service
@Slf4j
public class AdUserServiceImpl implements IAdUserService {

    @Autowired
    private AdUserRepository adUserRepository;

    @Override
    @Transactional
    public CreateAdUserResponse createAdUser(CreateAdUserRequest createAdUserRequest) throws AdException {
        if (!createAdUserRequest.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = adUserRepository.findByUsername(createAdUserRequest.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_USER_NAME_ERROR);
        }

        AdUser newUser = adUserRepository.save(
                new AdUser(createAdUserRequest.getUsername(),
                        CommonUtils.md5(createAdUserRequest.getUsername()))
        );

        return new CreateAdUserResponse(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getToken(),
                newUser.getCreateTime(),
                newUser.getUpdateTime()
        );
    }
}
