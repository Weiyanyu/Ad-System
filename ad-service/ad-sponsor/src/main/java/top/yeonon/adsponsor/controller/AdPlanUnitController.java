package top.yeonon.adsponsor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.service.IAdPlanUnitService;
import top.yeonon.adsponsor.vo.request.AdPlanUnitDistrictRequest;
import top.yeonon.adsponsor.vo.request.AdPlanUnitItRequest;
import top.yeonon.adsponsor.vo.request.AdPlanUnitKeywordRequest;
import top.yeonon.adsponsor.vo.request.AdPlanUnitRequest;
import top.yeonon.adsponsor.vo.response.*;

/**
 * @Author yeonon
 * @date 2019/3/24 0024 13:19
 **/
@RestController
@RequestMapping("/units/")
public class AdPlanUnitController {

    @Autowired
    private IAdPlanUnitService adPlanUnitService;

    @PostMapping("{planId}")
    public AdPlanUnitResponse createPlanUnit(@PathVariable("planId") Long planId,
                                             @RequestBody AdPlanUnitRequest adPlanUnitRequest) throws AdException {
        adPlanUnitRequest.setPlanId(planId);
        return adPlanUnitService.createPlanUnit(adPlanUnitRequest);
    }

    @PostMapping("/constraint/keyword")
    public AdPlanUnitKeywordResponse createKeyword(@RequestBody AdPlanUnitKeywordRequest adPlanUnitKeywordRequest) throws AdException {
        return adPlanUnitService.createKeyword(adPlanUnitKeywordRequest);
    }

    @PostMapping("/constraint/it")
    public AdPlanUnitItResponse createIt(@RequestBody AdPlanUnitItRequest adPlanUnitItRequest) throws AdException {
        return adPlanUnitService.createIt(adPlanUnitItRequest);
    }

    @PostMapping("/constraint/district")
    public AdPlanUnitDistrictResponse createDistrict(@RequestBody AdPlanUnitDistrictRequest adPlanUnitDistrictRequest) throws AdException {
        return adPlanUnitService.createDistrict(adPlanUnitDistrictRequest);
    }


}
