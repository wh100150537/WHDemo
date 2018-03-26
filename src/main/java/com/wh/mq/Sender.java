package com.wh.mq;

import com.google.common.collect.Maps;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by wangyc on 2018/3/21.
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(){
//        String context = "hello"+new Date();
        Map context = Maps.newHashMap();
        context.put("name","wh");
        context.put("age",12);
        System.out.println("sender:"+context);
        this.rabbitTemplate.convertAndSend("hello",context);
    }

}
