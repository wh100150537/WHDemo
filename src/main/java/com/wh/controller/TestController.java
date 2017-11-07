package com.wh.controller;

import com.wh.dao.UserMapper;
import com.wh.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wh on 2017/10/26.
 */
@RestController
public class TestController{

    @Resource
    UserMapper userMapper;

    @GetMapping("/test/{d}")
    @ApiOperation(value = "测试")
    public User testURL(HttpServletRequest request, @RequestParam String a, @RequestParam String b,
                        @RequestParam(required = false) Integer c, @PathVariable int d){
        System.out.println("ss"+a+b+c+d);
        User user = userMapper.selectByPrimaryKey(31);
        return user;
    }


    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${upload.url}")
    private String uploadUrl;

    @PostMapping("/upload")
    @ApiOperation(value = "图片上传")
    public void upload(MultipartFile file){
        String fileName = "";
        try{
            String originaFileName = file.getOriginalFilename();
            System.out.println(originaFileName);
            InputStream in = file.getInputStream();
            fileName = UUID.randomUUID()+"."+file.getOriginalFilename().split("\\.")[1];
            System.out.println(fileName);
            String dir = uploadDir + DateFormatUtils.format(new Date(),"yyyyMMdd");
            System.out.println(dir);
            File dirFile = new File(dir);
            if(!dirFile.exists()){
                if(dirFile.mkdir())
                    System.out.println("创建文件夹成功");
                else
                    System.out.println("创建文件夹失败");
            }

            String fileRealPath = uploadUrl + dir + "/" + fileName;
            System.out.println(fileRealPath);
            File file1 = new File(fileRealPath);
            if (!file1.exists()){
                file1.createNewFile();
            }
            FileUtils.copyInputStreamToFile(in,file1);
            System.out.println("上传成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        System.out.println(calendar.getTime());
    }

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
}
