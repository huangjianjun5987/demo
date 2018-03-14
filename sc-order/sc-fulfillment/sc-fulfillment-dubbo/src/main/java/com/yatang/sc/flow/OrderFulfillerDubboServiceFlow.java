package com.yatang.sc.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.fulfillment.dto.OrderCancelConfirmDto;
import com.yatang.sc.fulfillment.dto.OrderCancelFailDto;
import com.yatang.sc.fulfillment.dto.OrderCancelShippingDto;
import com.yatang.sc.fulfillment.dto.OrderDeliveryDto;
import com.yatang.sc.fulfillment.dto.OrderNotReceiveDto;
import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.fulfillment.dto.OrderRejectDto;
import com.yatang.sc.fulfillment.dto.OrderSendCarDto;
import com.yatang.sc.fulfillment.dto.OrderTransferBackDto;
import com.yatang.sc.fulfillment.dto.OrderTransferDto;
import com.yatang.sc.fulfillment.dto.OrderWMSRejectDto;
import com.yatang.sc.fulfillment.dto.ProviderOrderSingReqDto;


public interface OrderFulfillerDubboServiceFlow {
    Response<Boolean> orderTransfer(OrderTransferDto orderTransferDto);

    Response<Boolean> orderTransferBack(OrderTransferBackDto orderTransferBackDto);

    Response<Boolean> orderDelivery(OrderDeliveryDto orderDeliveryDto) throws Exception;

    Response<Boolean> orderSendCar(OrderSendCarDto pOrderSendCarDto) throws Exception;

    Response<Boolean> orderReceive(OrderReceiveDto orderReceiveDto) throws Exception;

    Response<Boolean> orderCancelConfirm(OrderCancelConfirmDto pOrderCancelConfirmDto) throws Exception;

    Response<Boolean> orderCancelFail(OrderCancelFailDto pOrderCancelFailDto);

    Response<Boolean> orderNotReceive(OrderNotReceiveDto orderNotReceiveDto) throws Exception;

    Response<Boolean> orderCancelShipping(OrderCancelShippingDto orderCancelShippingDto) throws Exception;

    Response<Boolean> orderWMSReject(OrderWMSRejectDto orderWMSRejectDto);


    /**
     * 订单拒签
     *
     * @param orderRejectDto
     * @return
     */
    Response<Boolean> orderReject(OrderRejectDto orderRejectDto) throws Exception;

    Response<Boolean> providerOrderASN(String orderId,String receiptId);

    Response<Boolean> providerOrderSingReq(ProviderOrderSingReqDto pProviderOrderSingReqDto);
}
