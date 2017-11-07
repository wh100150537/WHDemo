package com.wh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wanghai on 2017/10/26.
 */
@SpringBootApplication
@MapperScan("com.wh.dao")
public class MyApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {

        SpringApplication.run(MyApplication.class,args);
    }
}
