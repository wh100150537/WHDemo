package com.wh.service;

import com.wh.domain.response.Response;
import com.wh.utils.http.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";


    @Value("${mini.program.appid}")
    private String appId;
    @Value("${mini.program.secret}")
    private String secret;


    public Response WXLogin(String code){

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(WX_LOGIN_URL)
                .append("?appid=").append(appId)
                .append("&secret=").append(secret)
                .append("&js_code=").append(code)
                .append("&grant_type=").append("authorization_code");
        String result = HttpClientUtil.get(urlBuilder.toString());
        return null;
    }

}
