package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.CouponPurchaseRecordDao;
import com.yatang.sc.order.domain.CouponPurchaseRecord;
import com.yatang.sc.order.service.CouponPurchaseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qiugang on 10/23/2017.
 */
@Service("couponPurchaseRecordService")
public class CouponPurchaseRecordServiceImpl implements CouponPurchaseRecordService {

    @Autowired
    CouponPurchaseRecordDao couponPurchaseRecordDao;


    @Override
    public int saveCouponPurchaseRecord(String orderId, Long commerceItemId, long qty) {
        CouponPurchaseRecord record = new CouponPurchaseRecord();
        record.setOrderId(orderId);
        record.setCommerceItemId(commerceItemId);
        record.setQuantity(qty);
        return couponPurchaseRecordDao.insertSelective(record);
    }

    @Override
    public List<CouponPurchaseRecord> queryByCommerceItemId(Long commerceItemId) {
        return couponPurchaseRecordDao.queryByCommerceItemId(commerceItemId);
    }
}
