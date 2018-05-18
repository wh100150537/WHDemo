package com.wh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wh.controller.TestController;
import com.wh.dao.SxReceiverSenderMapper;
import com.wh.dao.SxUserMapper;
import com.wh.domain.response.Response;
import com.wh.entity.SxReceiverSender;
import com.wh.entity.SxReceiverSenderExample;
import com.wh.entity.SxUser;
import com.wh.utils.http.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";


    @Value("${mini.program.appid}")
    private String appId;
    @Value("${mini.program.secret}")
    private String secret;

    @Autowired
    SxReceiverSenderMapper receiverSenderMapper;
    @Autowired
    SxUserMapper userMapper;


    public Response WXLogin(String code){
        logger.info("appId:{},secret:{}",appId,secret);
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(WX_LOGIN_URL)
                .append("?appid=").append(appId)
                .append("&secret=").append(secret)
                .append("&js_code=").append(code)
                .append("&grant_type=").append("authorization_code");
        String result = HttpClientUtil.get(urlBuilder.toString());
        logger.info("微信登陆信息：{}",result);
        JSONObject info = JSON.parseObject(result);
        if(info.getString("errcode")==null) {
            String openId = info.getString("openid");
            SxUser user = new SxUser();
            user.setOpenId(openId);
            Date date = new Date();
            user.setRegisterDate(date);
            user.setLoginDate(date);
            userMapper.insertSelective(user);
            logger.info("user:{}", JSON.toJSONString(user));
            return Response.SUCCESS(user);
        }
        return Response.ERROR("login error");
    }

    public Response addReceiverSender(String name,String phone,String address,Integer type,Integer userId){
        logger.info("name:{},phone:{},address:{},type:{},userId:{}",name,phone,address,type,userId);
        SxReceiverSender receiverSender = new SxReceiverSender();
        receiverSender.setName(name);
        receiverSender.setPhone(phone);
        receiverSender.setAddress(address);
        receiverSender.setType(type);
        receiverSender.setUserId(userId);
        receiverSenderMapper.insertSelective(receiverSender);
        return Response.SUCCESS("添加成功");
    }

    public Response getReceiverSender(Integer type,Integer userId){
        logger.info("type:{},userId:{}",type,userId);
        SxReceiverSenderExample example = new SxReceiverSenderExample();
        example.or().andTypeEqualTo(type).andUserIdEqualTo(userId);
        List<SxReceiverSender> receiverSenderList = receiverSenderMapper.selectByExample(example);
        return Response.SUCCESS(receiverSenderList);
    }

}
