package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.MqMsgLogDao;
import com.yatang.sc.order.domain.MqMsgLog;
import com.yatang.sc.order.service.MqMsgLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("MqMsgLogService")
public class MqMsgLogServiceImpl implements MqMsgLogService {
    @Autowired
    private MqMsgLogDao mMqMsgLogDao;

    @Override
    public MqMsgLog selectByPrimaryKey(String id) {
        return mMqMsgLogDao.selectByPrimaryKey(id);
    }

    @Override
    public List<MqMsgLog> selectByMessageType(String messageType) {
        return mMqMsgLogDao.selectByMessageType(messageType);
    }

    @Override
    public List<MqMsgLog> selectByMsgId(String orderId) {
        return mMqMsgLogDao.selectByMsgId(orderId);
    }

    @Override
    public int insert(MqMsgLog record) {
        record.setMsgId(UUID.randomUUID().toString());
        return mMqMsgLogDao.insert(record);
    }

    @Override
    public int update(MqMsgLog pMqMsgLog) {
        return mMqMsgLogDao.update(pMqMsgLog);
    }
}
