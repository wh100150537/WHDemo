package com.wh.controller;

import com.google.common.collect.Maps;
import com.wh.dao.LookUserMapper;
import com.wh.entity.LookUser;
import com.wh.entity.LookUserExample;
import com.wh.entity.User;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2018/3/15.
 */
@RestController
@RequestMapping("/look")
public class LookController {

    private static Logger logger = LoggerFactory.getLogger(LookController.class);

    @Autowired
    LookUserMapper userMapper;

    @GetMapping("/login")
    @ApiOperation("登陆")
    public Map login(
            @RequestParam String account, @RequestParam String password, HttpServletRequest request) {
        Map response = Maps.newHashMap();
        logger.info("登陆---account:{},password:{}", account, password);

        LookUserExample example = new LookUserExample();
        example.or().andAccountEqualTo(account).andPasswordEqualTo(password);
        if (userMapper.countByExample(example) > 0) {
            response.put("code", 1);
            request.getSession().setAttribute("account", account);
            return response;
        }
        response.put("code", 0);
        return response;
    }


    @PostMapping("/register")
    @ApiOperation("注册")
    public Map register(
            @RequestParam String account, @RequestParam String password, @RequestParam String userName,
            @RequestParam Integer type, @RequestParam String idCard, @RequestParam String mail
    ) {
        Map response = Maps.newHashMap();
        logger.info("注册---account:{},password:{}，userName:{},type:{}，idCard:{},mail:{}", account, password, userName, type, idCard, mail);

        LookUserExample example = new LookUserExample();
        example.or().andAccountEqualTo(account);
        if (userMapper.countByExample(example) < 1) {
            LookUser user = new LookUser();
            user.setAccount(account);
            user.setPassword(password);
            user.setUserName(userName);
            user.setAccountType(type);
            user.setIdCard(idCard);
            user.setMail(mail);
            user.setMoney(0d);
            if (userMapper.insertSelective(user) > 0) {
                response.put("code", 1);
                return response;
            }
        }
        response.put("code", 0);
        return response;
    }




    @GetMapping("/getUser")
    public Map getUser(HttpServletRequest request) {

        Map response = Maps.newHashMap();
        String account = (String) request.getSession().getAttribute("account");
        if (!StringUtils.isEmpty(account)) {
            LookUserExample example = new LookUserExample();
            example.or().andAccountEqualTo(account);
            List<LookUser> users = userMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(users)) {
                response.put("code", 1);
                response.put("user", users.get(0));
                request.getSession().setAttribute("userId", users.get(0).getId());
                return response;
            }
        }
        response.put("code", 1);
        return response;
    }

    @PostMapping("/inMoney")
    public Map inMoney(HttpServletRequest request, @RequestParam Double money) {
        Map response = Maps.newHashMap();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId != null) {
            LookUser user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                LookUser user1 = new LookUser();
                user1.setId(user.getId());
                user1.setMoney(user.getMoney() + money);
                if (userMapper.updateByPrimaryKeySelective(user1) > 0) {
                    response.put("code", 1);
                    response.put("money", user1.getMoney());
                    return response;
                }
            }
        }
        response.put("code", 0);
        return response;
    }


    @PostMapping("/outMoney")
    public Map outMoney(HttpServletRequest request, @RequestParam Double money) {
        Map response = Maps.newHashMap();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId != null) {
            LookUser user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                if(user.getMoney()<money){
                    response.put("code",3);//余额不足
                    return response;
                }
                LookUser user1 = new LookUser();
                user1.setId(user.getId());
                user1.setMoney(user.getMoney() - money);
                if (userMapper.updateByPrimaryKeySelective(user1) > 0) {
                    response.put("code", 1);
                    response.put("money", user1.getMoney());
                    return response;
                }
            }
        }
        response.put("code", 0);
        return response;
    }



    @PostMapping("/toOtherMoney")
    public Map toOtherMoney(HttpServletRequest request, @RequestParam Double money,@RequestParam String account) {
        Map response = Maps.newHashMap();
        LookUserExample example = new LookUserExample();
        example.or().andAccountEqualTo(account);
        List<LookUser> users = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(users)){
            response.put("code",2);//转账账号不存在
            return response;
        }
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId != null) {
            LookUser user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                if(user.getMoney()<money){
                    response.put("code",3);//余额不足
                    return response;
                }

                LookUser user1 = new LookUser();
                user1.setId(user.getId());
                user1.setMoney(user.getMoney() - money);

                LookUser user2 = new LookUser();
                user2.setId(users.get(0).getId());
                user2.setMoney(users.get(0).getMoney() + money);
                if (userMapper.updateByPrimaryKeySelective(user1) > 0 &&userMapper.updateByPrimaryKeySelective(user2)>0) {
                    response.put("code", 1);
                    response.put("money", user1.getMoney());
                    return response;
                }
            }
        }
        response.put("code", 0);
        return response;
    }



    @PostMapping("/setLimitMoney")
    public Map setLimitMoney(HttpServletRequest request, @RequestParam Double money) {
        Map response = Maps.newHashMap();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId != null) {
            LookUser user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                if(user.getAccountType()!=2&&user.getAccountType()!=4){
                    response.put("code",4);//不是信用账户
                    return response;
                }
                LookUser user1 = new LookUser();
                user1.setId(user.getId());
                user1.setLimitMoney(user.getLimitMoney()==null?money:user.getLimitMoney()+money);
                if (userMapper.updateByPrimaryKeySelective(user1) > 0) {
                    response.put("code", 1);
                    return response;
                }
            }
        }
        response.put("code", 0);
        return response;
    }

    @GetMapping("/out")
    public void out(HttpServletRequest request){
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("account");
    }



}
