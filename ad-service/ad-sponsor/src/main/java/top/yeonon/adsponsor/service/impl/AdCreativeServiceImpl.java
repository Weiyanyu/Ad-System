package top.yeonon.adsponsor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.constant.Constants;
import top.yeonon.adsponsor.entity.AdCreative;
import top.yeonon.adsponsor.entity.AdUser;
import top.yeonon.adsponsor.repository.AdCreativeRepository;
import top.yeonon.adsponsor.repository.AdUserRepository;
import top.yeonon.adsponsor.service.IAdCreativeService;
import top.yeonon.adsponsor.vo.request.AdCreativeRequest;
import top.yeonon.adsponsor.vo.response.AdCreativeResponse;

import java.util.Optional;

/**
 * @Author yeonon
 * @date 2019/3/25 0025 11:47
 **/
@Service
@Slf4j
public class AdCreativeServiceImpl implements IAdCreativeService {

    @Autowired
    private AdCreativeRepository adCreativeRepository;

    @Autowired
    private AdUserRepository adUserRepository;


    @Override
    public AdCreativeResponse createCreative(AdCreativeRequest adCreativeRequest) throws AdException {

        if (!adCreativeRequest.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdUser> adUser = adUserRepository.findById(adCreativeRequest.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.NOT_FOUND_USER);
        }

        AdCreative oldCreative = adCreativeRepository.findByNameAndUserId(adCreativeRequest.getName(), adCreativeRequest.getUserId());
        if (oldCreative != null) {
            throw new AdException(Constants.ErrorMsg.SAME_CREATIVE_NAME_ERROR);
        }

        AdCreative newCreative = adCreativeRequest.convertToCreative();
        newCreative = adCreativeRepository.save(newCreative);
        return new AdCreativeResponse(newCreative.getId(), newCreative.getName());
    }
}
