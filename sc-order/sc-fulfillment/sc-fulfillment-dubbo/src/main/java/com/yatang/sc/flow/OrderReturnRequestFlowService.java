package com.yatang.sc.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.fulfillment.dto.OrderReturnOperateMessageDto;
import com.yatang.sc.order.domain.returned.ReturnRequestItemPo;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.purchase.dto.returned.ReturnRequestItemDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;

import java.util.List;


/**
 * @描述: 退换货的flow服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/19 17:21
 * @版本: v1.0
 */
public interface OrderReturnRequestFlowService {


    /**
     * 退换货单确认
     *
     * @return
     */
    Response<Void> confirmOrderReturnedReceipt(OrderReturnOperateMessageDto operateMessageDto);

    /**
     * 退换货单取消
     *
     * @return
     */
    Response<Void> cancelOrderReturnedReceipt(OrderReturnOperateMessageDto operateMessageDto);


    /**
     * 完成退货单订单
     *
     * @param requestReceiptDto
     * @return
     */
    Response<Void> completeReturnedOrderTReceipt(ReturnRequestReceiptDto requestReceiptDto);

    /**
     * 换货的退货完成
     *
     * @param requestReceiptDto
     * @return
     */
    Response<Void> completeReturnedOrderHReceipt(ReturnRequestReceiptDto requestReceiptDto);

    /**
     * 更新订单的退换货数量
     *
     * @param returnId
     * @param requestItems
     */

    void updateOderCommerceItemService(String returnId, List<ReturnRequestItemDto> requestItems);

    /**
     * 更新原订单 签收状态以及以及数量
     *
     * @param returnRequestPo
     * @param returnRequestItemPos
     */
    void updateOriginOrder(ReturnRequestPo returnRequestPo, List<ReturnRequestItemPo> returnRequestItemPos);
}
