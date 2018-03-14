package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.CouponPurchaseRecord;

import java.util.List;

/**
 * Created by qiugang on 10/23/2017.
 */
public interface CouponPurchaseRecordService {


    int saveCouponPurchaseRecord(String orderId, Long commerceItemId, long qty);


    List<CouponPurchaseRecord> queryByCommerceItemId(Long commerceItemId);
}
