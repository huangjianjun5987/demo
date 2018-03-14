package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.OrderRefundPriceDto;
import com.yatang.sc.purchase.dto.returned.OrderPreReturnRequestReceiptDto;
import com.yatang.sc.purchase.dto.returned.OrderPreReturnedCalculateAmountDto;

/**
 * @描述:使用优惠后实际退款金额计算
 * @类名:OrderRefundDubboService
 * @作者: lvheping
 * @创建时间: 2017/11/3 11:08
 * @版本: v1.0
 */

public interface OrderRefundDubboService {
    /**
     * 计算使用优惠卷后的退款金额
     *
     * @param price   实际退货数量原价格
     * @param orderId 原订单id
     * @return
     */
    Response<OrderRefundPriceDto> refundPrice(Double price, String orderId);

    /**
     * 生成预览金额
     *
     * @param receiptDto
     * @return
     */
    Response<OrderPreReturnedCalculateAmountDto> calculatePreOrderReturnedAmount(OrderPreReturnRequestReceiptDto receiptDto);
}
