package com.wh.controller;

import com.wh.domain.response.Response;
import com.wh.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/wx/login")
    @ApiOperation("微信登录")
    public Response WXLogin(
            @ApiParam("微信登录的code") @RequestParam String code
    ){
        return loginService.WXLogin(code);
    }

    @PostMapping("/add/receiver/sender")
    @ApiOperation("添加收件/寄件人")
    public Response addReceiverSender(
            @ApiParam("姓名") @RequestParam String name,
            @ApiParam("电话") @RequestParam String phone,
            @ApiParam("地址") @RequestParam String address,
            @ApiParam("类型，1-发件，2-收件") @RequestParam Integer type,
            @ApiParam("用户id") @RequestParam Integer userId
    ){
        return loginService.addReceiverSender(name,phone,address,type,userId);
    }

    @PostMapping("/get/receiver/sender")
    @ApiOperation("获取收件/寄件人")
    public Response getReceiverSender(
            @ApiParam("类型，1-发件，2-收件") @RequestParam Integer type,
            @ApiParam("用户id") @RequestParam Integer userId
    ){
        return loginService.getReceiverSender(type,userId);
    }

}
