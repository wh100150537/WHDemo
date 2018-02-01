package com.wh.controller;

import com.alibaba.fastjson.JSON;
import com.wh.dao.UserMapper;
import com.wh.entity.User;
import com.wh.utils.lm.HttpClient;
import com.wh.utils.lm.MDUtil;
import com.wh.utils.lm.StringUtils;
import com.wh.utils.token.AESUtil;
import com.wh.utils.token.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;


/**
 * Created by wh on 2017/10/26.
 */
@RestController
public class TestController{

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    public static HttpClient httpClient = new HttpClient();
    public static final String apiUrl = "https://t.limuzhengxin.cn/api/gateway";

    //haha
    //whwhwh

    @Resource
    UserMapper userMapper;



    @GetMapping("/test/{d}")
    @ApiOperation(value = "测试")
    public User testURL(HttpServletRequest request, @RequestParam String a, @RequestParam String b,
                        @RequestParam(required = false) Integer c, @PathVariable int d){
        System.out.println("ss"+a+b+c+d);
        logger.info(a);
        logger.warn(b);
        logger.error(c+"");
        User user = userMapper.selectByPrimaryKey(31);
        return user;
    }

    @PostMapping("/notify/lm")
    @ApiOperation("立木回调")
    public String lm(String uid,String bizType,String code, String msg, String token) {
        System.out.println("立木回调==/notify/lm");
        System.out.println("uid=" + uid);
        System.out.println("bizType=" + bizType);
        System.out.println("code=" + code);
        System.out.println("msg=" + msg);
        System.out.println("token=" + token);
        List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
        reqParam.add(new BasicNameValuePair("method", "api.common.getResult"));
        reqParam.add(new BasicNameValuePair("apiKey", "0848901135526939"));
        reqParam.add(new BasicNameValuePair("version", "1.2.0"));
        reqParam.add(new BasicNameValuePair("token", token));
        reqParam.add(new BasicNameValuePair("bizType", bizType));
        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        if ("0000".equals(code)) {
            String json = httpClient.doPost(apiUrl, reqParam);
            System.out.println(json);
        }
        return "success";
    }


    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${upload.url}")
    private String uploadUrl;

    @PostMapping("/upload")
    @ApiOperation(value = "图片上传")
    public String upload(MultipartFile file){
        String fileName = "";
        try{
            String originaFileName = file.getOriginalFilename();
            System.out.println("原图片名"+originaFileName);
            InputStream in = file.getInputStream();
            fileName = UUID.randomUUID()+"."+file.getOriginalFilename().split("\\.")[1];
            System.out.println("新生成图片名"+fileName);
            String dir = uploadDir + DateFormatUtils.format(new Date(),"yyyyMMdd");
            System.out.println("一天一个文件夹"+dir);
            File dirFile = new File(dir);
            if(!dirFile.exists()){
                if(dirFile.mkdir())
                    System.out.println("创建文件夹成功");
                else{
                    System.out.println("创建文件夹失败");
                    return "失败";
                }


            }

            String fileRealPath = dir + "/" + fileName;
            System.out.println("图片路径"+fileRealPath);
            File file1 = new File(fileRealPath);
            if (!file1.exists()){
                file1.createNewFile();
            }
            FileUtils.copyInputStreamToFile(in,file1);
            System.out.println("图片上传成功");
            return "成功";
        }catch (Exception e){
            e.printStackTrace();
            return "失败";
        }
    }

    @PostMapping("/login")
    @ApiOperation("登陆")
    public Map login(
            @ApiParam("号码") @RequestParam String phone,
            @ApiParam("密码") @RequestParam String password
    ){
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setTime(new Date().getTime());
        Map response = new HashMap();
        response.put("id",1);
        response.put("token", AESUtil.encrypt(JSON.toJSONString(loginUser)));


        return response;
    }




    //获取ip
    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = "";
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;

            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;

            }
        }
        return ip;
    }

    /**
     * 签名转化
     *
     * @param reqParam
     * @return
     */
    public String getSign(List<BasicNameValuePair> reqParam) {

        StringBuffer sbb = new StringBuffer();
        int index = 1;
        for (BasicNameValuePair nameValuePair : reqParam) {
            sbb.append(nameValuePair.getName() + "=" + nameValuePair.getValue());
            if (reqParam.size() != index) {
                sbb.append("&");
            }
            index++;
        }
        String sign = sbb.toString();

        Map<String, String> paramMap = new HashMap<String, String>();
        String ret = "";
        if (!StringUtils.isEmpty(sign)) {
            String[] arr = sign.split("&");
            for (int i = 0; i < arr.length; i++) {
                String tmp = arr[i];
                if (-1 != tmp.indexOf("=")) {
                    paramMap.put(tmp.substring(0, tmp.indexOf("=")), tmp.substring(tmp.indexOf("=") + 1, tmp.length()));
                }

            }
        }
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(paramMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                int ret = 0;
                ret = o1.getKey().compareTo(o2.getKey());
                if (ret > 0) {
                    ret = 1;
                } else {
                    ret = -1;
                }
                return ret;
            }
        });

        StringBuilder sbTmp = new StringBuilder();
        for (Map.Entry<String, String> mapping : list) {
            if (!"sign".equals(mapping.getKey())) {
                sbTmp.append(mapping.getKey()).append("=").append(mapping.getValue()).append("&");
            }
        }
        sbTmp.setLength(sbTmp.lastIndexOf("&"));
        sbTmp.append("Q3jW5mYt3tpN8EPAk2WfN9W3SH5WWPMQ");
        ret = MDUtil.SHA1(sbTmp.toString());

        //System.out.println(sb.toString());
        return ret;
    }

    @RequestMapping("/hahei")
    public String hahei(){
        return "hahei";
    }





}
