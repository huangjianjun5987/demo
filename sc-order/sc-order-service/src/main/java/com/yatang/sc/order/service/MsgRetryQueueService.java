package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.MsgRetryQueue;

import java.util.List;

public interface MsgRetryQueueService {
    List<MsgRetryQueue> selectByUniqueId(String uniqueId);

    int insert(MsgRetryQueue record);

    int update(MsgRetryQueue pMqMsgLog);

    MsgRetryQueue selectByUniqueIdAndMsgType(String uniqueId, String msgType);

    List<String> selectWZXUniqueIds();
}
