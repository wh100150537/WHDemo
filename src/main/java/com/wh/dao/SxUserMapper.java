package com.wh.dao;

import com.wh.entity.SxUser;
import com.wh.entity.SxUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SxUserMapper {
    long countByExample(SxUserExample example);

    int deleteByExample(SxUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SxUser record);

    int insertSelective(SxUser record);

    List<SxUser> selectByExample(SxUserExample example);

    SxUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SxUser record, @Param("example") SxUserExample example);

    int updateByExample(@Param("record") SxUser record, @Param("example") SxUserExample example);

    int updateByPrimaryKeySelective(SxUser record);

    int updateByPrimaryKey(SxUser record);
}