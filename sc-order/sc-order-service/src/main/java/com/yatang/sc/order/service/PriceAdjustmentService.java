package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.PriceAdjustment;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
public interface PriceAdjustmentService {
    void save(PriceAdjustment priceAdjustment);

    PriceAdjustment selectByPrimaryKey(Long adjustmentId);

    int updateByPrimaryKey(PriceAdjustment record);

    int updateByPrimaryKeySelective(PriceAdjustment record);

    List<PriceAdjustment> queryByOrderPriceId(Long id);
}
