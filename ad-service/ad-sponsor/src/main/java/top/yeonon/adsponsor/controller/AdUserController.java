package top.yeonon.adsponsor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yeonon.adcommon.exception.AdException;
import top.yeonon.adsponsor.service.IAdUserService;
import top.yeonon.adsponsor.vo.request.CreateAdUserRequest;
import top.yeonon.adsponsor.vo.response.CreateAdUserResponse;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 12:13
 **/
@RestController
@RequestMapping("users/")
public class AdUserController {

    @Autowired
    private IAdUserService adUserService;

    @PostMapping
    public CreateAdUserResponse createAdUser(CreateAdUserRequest createAdUserRequest) throws AdException {
        return adUserService.createAdUser(createAdUserRequest);
    }
}
