package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.MqFailedMsg;

import java.util.List;

public interface MqFailedMsgService {
    MqFailedMsg selectByPrimaryKey(String id);

    List<MqFailedMsg> selectByMessageType(String messageType);

    List<MqFailedMsg> selectByOrderId(String orderId);

    int insert(MqFailedMsg record);

    int updateToResendSuccess(String orderId);
}
