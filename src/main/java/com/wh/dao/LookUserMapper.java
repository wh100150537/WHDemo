package com.wh.dao;

import com.wh.entity.LookUser;
import com.wh.entity.LookUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LookUserMapper {
    long countByExample(LookUserExample example);

    int deleteByExample(LookUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LookUser record);

    int insertSelective(LookUser record);

    List<LookUser> selectByExample(LookUserExample example);

    LookUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LookUser record, @Param("example") LookUserExample example);

    int updateByExample(@Param("record") LookUser record, @Param("example") LookUserExample example);

    int updateByPrimaryKeySelective(LookUser record);

    int updateByPrimaryKey(LookUser record);
}