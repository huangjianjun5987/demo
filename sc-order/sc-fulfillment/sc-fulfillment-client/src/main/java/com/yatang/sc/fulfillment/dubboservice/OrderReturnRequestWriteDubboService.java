package com.yatang.sc.fulfillment.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.fulfillment.dto.OrderReturnOperateMessageDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;

/**
 * @描述: 退换货dubboWrite服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/19 17:19
 * @版本: v1.0
 */
public interface OrderReturnRequestWriteDubboService {


    /**
     * 退货单操作(取消,确认)
     *
     * @return
     */
    Response<Void> operateOrderReturnedReceipt(OrderReturnOperateMessageDto operateMessageDto);

    /**
     * 退货完成操作
     *
     * @param requestReceiptDto
     * @return
     */
    Response<Void> completeReturnedOrderReceipt(ReturnRequestReceiptDto requestReceiptDto);

    /**
     * 换货发货
     *
     * @param requestReceiptDto
     * @return
     */
    Response<Void> deliveryExchangeOrderReceipt(ReturnRequestReceiptDto requestReceiptDto);

    /**
     * 换货完成
     *
     * @param requestReceiptDto
     * @return
     */
    Response<Void> completeExchangeOrderReceipt(ReturnRequestReceiptDto requestReceiptDto);
}
