package com.wh.dao;

import com.wh.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by wangyc on 2017/10/27.
 */
@Component
public interface UserMapper {


    User selectByPrimaryKey(Integer id);
}
