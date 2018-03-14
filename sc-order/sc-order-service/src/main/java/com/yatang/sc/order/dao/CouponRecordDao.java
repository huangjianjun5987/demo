package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.CouponRecordDetailPo;
import com.yatang.sc.order.domain.CouponRecordParamPo;
import com.yatang.sc.order.domain.CouponRecordPo;

import java.util.List;

public interface CouponRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(CouponRecordPo record);

    int insertSelective(CouponRecordPo record);

    CouponRecordPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponRecordPo record);

    int updateByPrimaryKey(CouponRecordPo record);

    /**
     * 查询参与数据（已使用）
     * @param couponRecordParamPo
     * @return
     */
    List<CouponRecordDetailPo> queryRecordList(CouponRecordParamPo couponRecordParamPo);
    
    int deleteByOrderId(String orderId);
}