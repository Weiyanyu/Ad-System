package top.yeonon.adsponsor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.constant.Constants;
import top.yeonon.adsponsor.entity.AdPlan;
import top.yeonon.adsponsor.entity.AdUnit;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitDistrict;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitIt;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitKeyword;
import top.yeonon.adsponsor.repository.AdPlanRepository;
import top.yeonon.adsponsor.repository.AdUnitRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitDistrictRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitItRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitKeywordRepository;
import top.yeonon.adsponsor.service.IAdPlanUnitService;
import top.yeonon.adsponsor.vo.request.AdPlanUnitDistrictRequest;
import top.yeonon.adsponsor.vo.request.AdPlanUnitItRequest;
import top.yeonon.adsponsor.vo.request.AdPlanUnitKeywordRequest;
import top.yeonon.adsponsor.vo.request.AdPlanUnitRequest;
import top.yeonon.adsponsor.vo.response.AdPlanUnitDistrictResponse;
import top.yeonon.adsponsor.vo.response.AdPlanUnitItResponse;
import top.yeonon.adsponsor.vo.response.AdPlanUnitKeywordResponse;
import top.yeonon.adsponsor.vo.response.AdPlanUnitResponse;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author yeonon
 * @date 2019/3/24 0024 11:29
 **/
@Slf4j
@Service
public class AdPlanUnitServiceImpl implements IAdPlanUnitService {

    @Autowired
    private AdUnitRepository adUnitRepository;

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;

    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;

    @Autowired
    private AdUnitItRepository adUnitItRepository;

    @Override
    public AdPlanUnitResponse createPlanUnit(AdPlanUnitRequest adPlanUnitRequest) throws AdException {
        if (!adPlanUnitRequest.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = adPlanRepository.findById(adPlanUnitRequest.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.NOT_FOUND_PLAN);
        }

        AdUnit oldUnit = adUnitRepository.findByPlanIdAndUnitName(adPlanUnitRequest.getPlanId(),
                                                                    adPlanUnitRequest.getUnitName());
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_UNIT_NAME_ERROR);
        }

        AdUnit newUnit = adUnitRepository.save(
                new AdUnit(adPlanUnitRequest.getPlanId(),
                            adPlanUnitRequest.getUnitName(),
                            adPlanUnitRequest.getPositionType(),
                            adPlanUnitRequest.getBudget())
        );

        return new AdPlanUnitResponse(newUnit.getId(), newUnit.getUnitName());
    }

    @Override
    public AdPlanUnitKeywordResponse createKeyword(AdPlanUnitKeywordRequest adPlanUnitKeywordRequest) throws AdException {

        //生成unit Id set
        Set<Long> unitIds = adPlanUnitKeywordRequest
                .getUnitKeywords()
                .stream()
                .map(AdPlanUnitKeywordRequest.UnitKeyword::getUnitId)
                .distinct()
                .collect(Collectors.toSet());

        //判断unit set内的id是否都存在
        if (!relateUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //将请求VO对象的信息转换成AdUnitKeyword对象
        List<AdUnitKeyword> adUnitKeywords = new ArrayList<>();
        adPlanUnitKeywordRequest.getUnitKeywords()
                .forEach(unitKeyword -> {
                    adUnitKeywords.add(new AdUnitKeyword(unitKeyword.getUnitId(),unitKeyword.getKeyword()));
                });

        //存入数据库，并将返回的对象映射成id list
        List<Long> ids = adUnitKeywordRepository
                .saveAll(adUnitKeywords)
                .stream()
                .map(AdUnitKeyword::getId)
                .collect(Collectors.toList());
        //返回响应VO对象
        return new AdPlanUnitKeywordResponse(ids);
    }

    @Override
    public AdPlanUnitItResponse createIt(AdPlanUnitItRequest adPlanUnitItRequest) throws AdException {
        Set<Long> unitIds = adPlanUnitItRequest
                .getUnitIts()
                .stream()
                .map(AdPlanUnitItRequest.UnitIt::getUnitId)
                .distinct()
                .collect(Collectors.toSet());

        if (!relateUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> adUnitIts = new ArrayList<>();
        adPlanUnitItRequest.getUnitIts()
                .forEach(unitIt -> {
                    adUnitIts.add(new AdUnitIt(unitIt.getUnitId(), unitIt.getItTag()));
                });

        List<Long> ids = adUnitItRepository
                .saveAll(adUnitIts)
                .stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());

        return new AdPlanUnitItResponse(ids);

    }

    @Override
    public AdPlanUnitDistrictResponse createDistrict(AdPlanUnitDistrictRequest adPlanUnitDistrictRequest) throws AdException {
        Set<Long> unitIds = adPlanUnitDistrictRequest
                .getUnitDistricts()
                .stream()
                .map(AdPlanUnitDistrictRequest.UnitDistrict::getUnitId)
                .distinct()
                .collect(Collectors.toSet());

        if (!relateUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> adUnitDistricts = new ArrayList<>();
        adPlanUnitDistrictRequest.getUnitDistricts()
                .forEach(unitDistrict -> {
                    adUnitDistricts.add(new AdUnitDistrict(
                            unitDistrict.getUnitId(),
                            unitDistrict.getProvince(),
                            unitDistrict.getCity())
                    );
                });

        List<Long> ids = adUnitDistrictRepository
                .saveAll(adUnitDistricts)
                .stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new AdPlanUnitDistrictResponse(ids);
    }

    /**
     * 判断依赖的unitId是否存在，有一个不存在则不合法
     * @param unitIds Set集合（为了去重）
     * @return true/false
     */
    private boolean relateUnitExist(Set<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        //找到的对象size和传入的id size一致，则表示unit id都是存在的
        return adUnitRepository.findAllById(unitIds).size()
                == unitIds.size();
    }
}
