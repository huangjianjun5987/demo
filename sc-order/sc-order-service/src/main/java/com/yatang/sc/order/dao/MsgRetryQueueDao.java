package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.MsgRetryQueue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgRetryQueueDao {
    List<MsgRetryQueue> selectByUniqueId(String uniqueId);

    int insert(MsgRetryQueue record);

    int update(MsgRetryQueue pMqMsgLog);

    MsgRetryQueue selectByUniqueIdAndMsgType(@Param("uniqueId") String uniqueId, @Param("msgType") String msgType);

    List<String> selectWZXUniqueIds();
}
