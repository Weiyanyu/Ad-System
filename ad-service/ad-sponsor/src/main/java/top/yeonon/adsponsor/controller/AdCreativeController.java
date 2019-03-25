package top.yeonon.adsponsor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.service.IAdCreativeService;
import top.yeonon.adsponsor.vo.request.AdCreativeRequest;
import top.yeonon.adsponsor.vo.response.AdCreativeResponse;

/**
 * @Author yeonon
 * @date 2019/3/25 0025 12:11
 **/
@RestController
@RequestMapping("creative")
public class AdCreativeController {


    @Autowired
    private IAdCreativeService adCreativeService;

    @PostMapping("{userId}")
    public AdCreativeResponse createCreative(@PathVariable("userId") Long userId,
                                             @RequestBody AdCreativeRequest adCreativeRequest) throws AdException {
        adCreativeRequest.setUserId(userId);
        return adCreativeService.createCreative(adCreativeRequest);
    }
}
