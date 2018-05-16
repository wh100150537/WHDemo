package com.wh.dao;

import com.wh.entity.SxReceiverSender;
import com.wh.entity.SxReceiverSenderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SxReceiverSenderMapper {
    long countByExample(SxReceiverSenderExample example);

    int deleteByExample(SxReceiverSenderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SxReceiverSender record);

    int insertSelective(SxReceiverSender record);

    List<SxReceiverSender> selectByExample(SxReceiverSenderExample example);

    SxReceiverSender selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SxReceiverSender record, @Param("example") SxReceiverSenderExample example);

    int updateByExample(@Param("record") SxReceiverSender record, @Param("example") SxReceiverSenderExample example);

    int updateByPrimaryKeySelective(SxReceiverSender record);

    int updateByPrimaryKey(SxReceiverSender record);
}