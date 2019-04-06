package top.yeonon.adsponsor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.constant.Constants;
import top.yeonon.adsponsor.entity.AdPlan;
import top.yeonon.adsponsor.entity.AdUnit;
import top.yeonon.adsponsor.entity.unitCondition.AdCreativeUnit;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitDistrict;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitIt;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitKeyword;
import top.yeonon.adsponsor.repository.AdCreativeRepository;
import top.yeonon.adsponsor.repository.AdPlanRepository;
import top.yeonon.adsponsor.repository.AdUnitRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdCreativeUnitRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitDistrictRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitItRepository;
import top.yeonon.adsponsor.repository.unitCondition.AdUnitKeywordRepository;
import top.yeonon.adsponsor.service.IAdPlanUnitService;
import top.yeonon.adsponsor.vo.request.*;
import top.yeonon.adsponsor.vo.response.*;

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

    @Autowired
    private AdCreativeUnitRepository adCreativeUnitRepository;

    @Autowired
    private AdCreativeRepository adCreativeRepository;

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
                    adUnitKeywords.add(new AdUnitKeyword(unitKeyword.getUnitId(), unitKeyword.getKeyword()));
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

    @Override
    public AdCreativeUnitResponse createCreativeUnit(AdCreativeUnitRequest adCreativeUnitRequest) throws AdException {
        if (!adCreativeUnitRequest.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Set<Long> creativeIds = adCreativeUnitRequest.getCreativeUnitItems()
                .stream()
                .map(AdCreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .distinct()
                .collect(Collectors.toSet());

        Set<Long> unitIds = adCreativeUnitRequest.getCreativeUnitItems()
                .stream()
                .map(AdCreativeUnitRequest.CreativeUnitItem::getUnitId)
                .distinct()
                .collect(Collectors.toSet());

        if (!relateCreativeExist(creativeIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        if (!relateUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdCreativeUnit> adCreativeUnits = new ArrayList<>();
        adCreativeUnitRequest.getCreativeUnitItems()
                .forEach(creativeUnitItem -> {
                    adCreativeUnits.add(new AdCreativeUnit(creativeUnitItem.getCreativeId(),
                            creativeUnitItem.getUnitId()));
                });

        List<Long> ids = adCreativeUnitRepository
                .saveAll(adCreativeUnits)
                .stream()
                .map(AdCreativeUnit::getId)
                .collect(Collectors.toList());
        return new AdCreativeUnitResponse(ids);
    }

    /**
     * 判断依赖的unitId是否存在，有一个不存在则不合法
     *
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

    private boolean relateCreativeExist(Set<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return adCreativeRepository.findAllById(creativeIds).size()
                == creativeIds.size();
    }
}
