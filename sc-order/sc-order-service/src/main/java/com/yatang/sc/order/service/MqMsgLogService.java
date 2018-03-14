package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.MqMsgLog;

import java.util.List;

public interface MqMsgLogService {
    MqMsgLog selectByPrimaryKey(String id);

    List<MqMsgLog> selectByMessageType(String messageType);

    List<MqMsgLog> selectByMsgId(String orderId);

    int insert(MqMsgLog record);

    int update(MqMsgLog pMqMsgLog);
}
