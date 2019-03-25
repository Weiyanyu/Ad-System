package top.yeonon.adsponsor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.entity.AdPlan;
import top.yeonon.adsponsor.service.IAdPlanService;
import top.yeonon.adsponsor.vo.request.AdPlanGetRequest;
import top.yeonon.adsponsor.vo.request.AdPlanRequest;
import top.yeonon.adsponsor.vo.response.AdPlanResponse;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 13:56
 **/
@RestController
@RequestMapping("/plans")
public class AdPlanController {

    @Autowired
    private IAdPlanService adPlanService;

    @PostMapping("{userId}")
    public AdPlanResponse createPlan(@PathVariable("userId") Long userId,
                                     @RequestBody AdPlanRequest adPlanRequest) throws AdException {
        adPlanRequest.setUserId(userId);
        return adPlanService.createAdPlan(adPlanRequest);
    }

    @GetMapping(value = "{userId}")
    public List<AdPlan> getPlans(@PathVariable("userId") Long userId,
                                 @RequestBody AdPlanGetRequest adPlanGetRequest) throws AdException {
        adPlanGetRequest.setUserId(userId);
        return adPlanService.getAdPlans(adPlanGetRequest);
    }

    @PutMapping("{userId}/{id}")
    public AdPlanResponse updatePlan(@PathVariable("userId") Long userId,
                                     @PathVariable("id") Long id,
                                     @RequestBody AdPlanRequest adPlanRequest) throws AdException {
        adPlanRequest.setUserId(userId);
        adPlanRequest.setId(id);
        return adPlanService.updateAdPlan(adPlanRequest);
    }

    @DeleteMapping("{userId}/{id}")
    public void deletePlan(@PathVariable("userId") Long userId,
                           @PathVariable("id") Long id) throws AdException {
        AdPlanRequest adPlanRequest = new AdPlanRequest();
        adPlanRequest.setUserId(userId);
        adPlanRequest.setId(id);
        adPlanService.deleteAdPlan(adPlanRequest);
    }
}
