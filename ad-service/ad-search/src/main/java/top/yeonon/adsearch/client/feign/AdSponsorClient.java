package top.yeonon.adsearch.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.yeonon.adcommon.vo.CommonResponse;


/**
 * @Author yeonon
 * @date 2019/3/27 0027 10:29
 **/
@FeignClient(value = "ad-sponsor")
@Component
public interface AdSponsorClient {

    @GetMapping(value = "/plans/{userId}")
    CommonResponse getAdPlans(@PathVariable("userId") Long userId);


}
