package com.yatang.sc.order.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.domain.returned.*;

import java.util.List;

/**
 * @描述: 退换货service接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/17 11:21
 * @版本: v1.0
 */
public interface ReturnRequestService {

    /**
     * 按条件查询退换货单列表
     *
     * @param convert
     * @return
     */
    PageInfo<ReturnRequestListPo> queryReturnRequestList(ReturnRequestQueryParamPo convert);

    /**
     * 查询退换货单据信息
     *
     * @param id
     * @return
     */
    ReturnRequestPo selectByPrimaryKey(String id);

    /**
     * 保存退换货单的备注
     *
     * @param po
     * @return
     */
    boolean updateDescriptionAndQuantity(ReturnRequestPo po, List<ReturnQuantityPo> returnQuantityPos);

    /**
     * 创建退换货单
     *
     * @param requestReceiptPo
     * @return
     */
    boolean createOrderReturnedReceipt(ReturnRequestReceiptPo requestReceiptPo);


    /**
     * 编辑退换货单
     *
     * @param operateMessageDto
     * @return
     */
    int operateOrderReturnedReceipt(OrderReturnOperateMessagePo operateMessageDto);

    /**
     * 检查能否创建退换货单
     *
     * @param orderId
     * @return
     */
    Integer getReturnReceiptCountByOrderId(String orderId);


    /**
     * 获取退货单状态
     *
     * @param returnId
     * @return
     */
    Short getOrderReturnedReceiptState(String returnId);

    /**
     * 根据返回单更新返回数量
     *
     * @param returnId
     * @return
     */
    Integer updateOrderReturnedReturnQuantity(String returnId);

    /**
     * 更新退货单
     *
     * @param receiptPo
     * @return
     */
    boolean updateReturnedOrderReceipt(ReturnRequestReceiptPo receiptPo);


    /**
     * 更新传输状态==>已发出
     *
     * @param returnRequest
     * @return
     */
    boolean deliveryExchangeOrderReceipt(ReturnRequestPo returnRequest);


    /**
     * 根据退换货单类型和原订单id查询数量
     *
     * @param orderId
     * @param returnRequestType
     * @return
     */
    Integer getReturnReceiptCountByOrderIdAndReturnRequestType(String orderId, String returnRequestType);


    /**
     * 通过OrderId查询退货单信息
     * @param orderId
     * @return
     */
    ReturnRequestPo queryReturnRequestByOrderId(String orderId);

    /**
     * 计算使用优惠卷后的退款金额
     *
     * @param price   实际退货数量原价格
     * @param orderId 原订单id
     * @return
     */
    OrderRefundPricePo refundPrice(Double price, String orderId);
}
