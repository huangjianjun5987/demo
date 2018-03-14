package com.yatang.sc.payment.service.impl;

import com.yatang.sc.payment.dao.PayRefundDao;
import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.payment.service.PayRefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yuwei on 2017/7/11.
 */
@Service("payRefundService")
@Transactional(rollbackFor = Exception.class)
public class PayRefundServiceImpl implements PayRefundService {
    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayRefundDao mPayRefundDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Long insert(PayRefundPO pPayRefundPO) {
        return mPayRefundDao.insert(pPayRefundPO);
    }

    @Override
    public int update(PayRefundPO pPayRefundPO) {
        return mPayRefundDao.update(pPayRefundPO);
    }

    @Override
    public PayRefundPO get(Long pRefundId) {
        return mPayRefundDao.get(pRefundId);
    }

    @Override
    public List<PayRefundPO> queryRefundsByPayNo(String pPayNo) {
        return mPayRefundDao.queryRefundsByPayNo(pPayNo);
    }

    @Override
    public PayRefundPO queryRefundsByRefundNo(String pRefundNo) {
        return mPayRefundDao.queryRefundsByRefundNo(pRefundNo);
    }

    @Override
    public int updateTryCount(Long mRefundId) {
        PayRefundPO payRefundPO = mPayRefundDao.get(mRefundId);
        if (payRefundPO == null) {
            mLogger.error("未查询到退款记录:{}", mRefundId);
            return 0;
        }
        payRefundPO.setTryRefundCount(payRefundPO.getTryRefundCount() + 1);
        return mPayRefundDao.update(payRefundPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int updateRefundStatus(Long mRefundId, RefundStatus pRefundStatus) {
        PayRefundPO payRefundPO = mPayRefundDao.get(mRefundId);
        if (payRefundPO == null) {
            mLogger.error("未查询到退款记录:{}", mRefundId);
            return 0;
        }
        payRefundPO.setStatus(pRefundStatus);
        return mPayRefundDao.update(payRefundPO);
    }

    @Override
    public List<PayRefundPO> queryRefundsByOrderNo(String orderNo) {
        return mPayRefundDao.queryRefundsByOrderNo(orderNo);
    }
}
