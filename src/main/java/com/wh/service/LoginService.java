package com.wh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.wh.controller.TestController;
import com.wh.dao.SxReceiverSenderMapper;
import com.wh.dao.SxUserMapper;
import com.wh.domain.response.Response;
import com.wh.domain.response.ResponseData;
import com.wh.entity.SxReceiverSender;
import com.wh.entity.SxReceiverSenderExample;
import com.wh.entity.SxUser;
import com.wh.entity.SxUserExample;
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
            SxUserExample example = new SxUserExample();
            example.or().andOpenIdEqualTo(openId);
            List<SxUser> users = userMapper.selectByExample(example);
            if(users.size()>0){
                return Response.SUCCESS(users.get(0));
            }

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

    public Response addReceiverSender(String name,String phone,String province,String city,String area,String detailAddress,Integer type,Integer userId){
        logger.info("name:{},phone:{},address:{},type:{},userId:{}",name,phone,detailAddress,type,userId);
        SxReceiverSender receiverSender = new SxReceiverSender();
        receiverSender.setName(name);
        receiverSender.setPhone(phone);
        receiverSender.setProvince(province);
        receiverSender.setCity(city);
        receiverSender.setArea(area);
        receiverSender.setDetailAddress(detailAddress);
        receiverSender.setType(type);
        receiverSender.setUserId(userId);
        receiverSender.setCreateTime(new Date());
        receiverSenderMapper.insertSelective(receiverSender);
        return Response.SUCCESS("添加成功");
    }

    public Response getReceiverSender(Integer type,Integer userId){
        logger.info("type:{},userId:{}",type,userId);
        SxReceiverSenderExample example = new SxReceiverSenderExample();
        example.or().andTypeEqualTo(type).andUserIdEqualTo(userId);
        List<SxReceiverSender> receiverSenderList = receiverSenderMapper.selectByExample(example);
        List<ResponseData> responseDataList = Lists.newArrayList();
        for (SxReceiverSender receiverSender:receiverSenderList){
            ResponseData responseData = new ResponseData();
            responseData.setReceiverSenderId(receiverSender.getId());
            responseData.setName(receiverSender.getName());
            responseData.setPhone(receiverSender.getPhone());
            responseData.setProvince(receiverSender.getProvince());
            responseData.setCity(receiverSender.getCity());
            responseData.setArea(receiverSender.getArea());
            responseData.setDetailAddress(receiverSender.getDetailAddress());
            responseData.setType(receiverSender.getType());

            responseDataList.add(responseData);
        }
        return Response.SUCCESS(responseDataList);
    }

}
