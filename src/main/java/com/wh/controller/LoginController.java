package com.wh.controller;

import com.wh.domain.response.Response;
import com.wh.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/wx/login")
    @ApiOperation("微信登录")
    public Response WXLogin(
            @ApiParam("微信登录的code") @RequestParam String code
    ){
        return loginService.WXLogin(code);
    }

}
