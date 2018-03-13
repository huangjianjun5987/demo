package com.yatang.sc.inventory.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.inventory.domain.ImAdjustmentPo;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryListPo;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryParamPo;

import com.yatang.sc.inventory.domain.ImAdjustmentItemPo;
import com.yatang.sc.inventory.domain.ImAdjustmentReceiptPo;

import java.util.List;

/**
 * @描述: 库存调整service接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午8:59
 * @版本: v1.0
 */
public interface ImAdjustmentService {

    /**
     * 新增库存调整单
     *
     * @param adjustmentReceiptPo
     * @return
     */
    boolean addAdjustmentReceipt(ImAdjustmentReceiptPo adjustmentReceiptPo);


    /**
     * 新增同步调整单
     *
     * @param adjustmentReceiptPo
     * @return
     */
    void addSynAdjustmentReceipt(ImAdjustmentReceiptPo adjustmentReceiptPo);

    /**
     * 调整库存中的商品
     *
     * @param receiptPo 库存调整单
     */
    void adjustInventoryItem(ImAdjustmentReceiptPo receiptPo);

    /**
     * 根据传入参数分页查询库存调整列表
     *
     * @param convert1
     * @return
     */
    PageInfo<ImAdjustmentQueryListPo> queryListImAdjustment(ImAdjustmentQueryParamPo convert1);

    /**
     * 根据传入参数id查询库存调整详情
     *
     * @param id
     * @return
     */
    ImAdjustmentQueryListPo getImAdjustment(Long id);



}
