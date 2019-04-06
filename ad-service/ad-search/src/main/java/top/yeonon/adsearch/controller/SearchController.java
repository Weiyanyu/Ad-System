package top.yeonon.adsearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yeonon.adcommon.annotation.IgnoreResponseAdvice;
import top.yeonon.adcommon.vo.CommonResponse;
import top.yeonon.adsearch.client.feign.AdSponsorClient;


/**
 * @Author yeonon
 * @date 2019/3/27 0027 10:33
 **/
@RestController
@RequestMapping("search")
@Slf4j
public class SearchController {

    @Autowired
    private AdSponsorClient adSponsorClient;

    @GetMapping("/plans/{userId}")
    @IgnoreResponseAdvice
    public CommonResponse getPlans(@PathVariable("userId") Long userId) {

        return adSponsorClient.getAdPlans(userId);
    }
}
