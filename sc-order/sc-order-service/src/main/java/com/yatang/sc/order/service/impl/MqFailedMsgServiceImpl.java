package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.MqFailedMsgDao;
import com.yatang.sc.order.domain.MqFailedMsg;
import com.yatang.sc.order.service.MqFailedMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MqFailedMsgServiceImpl implements MqFailedMsgService {
    @Autowired
    private MqFailedMsgDao mMqFailedMsgDao;

    @Override
    public MqFailedMsg selectByPrimaryKey(String id) {
        return mMqFailedMsgDao.selectByPrimaryKey(id);
    }

    @Override
    public List<MqFailedMsg> selectByMessageType(String messageType) {
        return mMqFailedMsgDao.selectByMessageType(messageType);
    }

    @Override
    public List<MqFailedMsg> selectByOrderId(String orderId) {
        return mMqFailedMsgDao.selectByOrderId(orderId);
    }

    @Override
    public int insert(MqFailedMsg record) {
        return mMqFailedMsgDao.insert(record);
    }

    @Override
    public int updateToResendSuccess(String orderId) {
        return mMqFailedMsgDao.updateToResendSuccess(orderId);
    }
}
