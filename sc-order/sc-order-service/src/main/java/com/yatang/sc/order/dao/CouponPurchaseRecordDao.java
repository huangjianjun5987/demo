package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.CouponPurchaseRecord;

import java.util.List;

public interface CouponPurchaseRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(CouponPurchaseRecord record);

    int insertSelective(CouponPurchaseRecord record);

    CouponPurchaseRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponPurchaseRecord record);

    int updateByPrimaryKey(CouponPurchaseRecord record);

    List<CouponPurchaseRecord> queryByCommerceItemId(Long commerceItemId);
}