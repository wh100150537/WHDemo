package com.wh.dao;

import com.wh.entity.SxOrder;
import com.wh.entity.SxOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SxOrderMapper {
    long countByExample(SxOrderExample example);

    int deleteByExample(SxOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SxOrder record);

    int insertSelective(SxOrder record);

    List<SxOrder> selectByExample(SxOrderExample example);

    SxOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SxOrder record, @Param("example") SxOrderExample example);

    int updateByExample(@Param("record") SxOrder record, @Param("example") SxOrderExample example);

    int updateByPrimaryKeySelective(SxOrder record);

    int updateByPrimaryKey(SxOrder record);
}