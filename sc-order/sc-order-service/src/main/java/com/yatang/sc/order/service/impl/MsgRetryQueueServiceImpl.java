package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.MsgRetryQueueDao;
import com.yatang.sc.order.domain.MsgRetryQueue;
import com.yatang.sc.order.service.MsgRetryQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MsgRetryQueueServiceImpl implements MsgRetryQueueService {
    @Autowired
    private MsgRetryQueueDao mMsgRetryQueueDao;

    @Override
    public List<MsgRetryQueue> selectByUniqueId(String uniqueId) {
        return mMsgRetryQueueDao.selectByUniqueId(uniqueId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insert(MsgRetryQueue record) {
        return mMsgRetryQueueDao.insert(record);
    }

    @Override
    public int update(MsgRetryQueue pMqMsgLog) {
        return mMsgRetryQueueDao.update(pMqMsgLog);
    }

    @Override
    public MsgRetryQueue selectByUniqueIdAndMsgType(String uniqueId, String msgType) {
        return mMsgRetryQueueDao.selectByUniqueIdAndMsgType(uniqueId, msgType);
    }

    @Override
    public List<String> selectWZXUniqueIds() {
        return mMsgRetryQueueDao.selectWZXUniqueIds();
    }
}
