package com.yatang.sc.fulfillment.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.fulfillment.dto.*;


/**
 * Created by qiugang on 7/27/2017.
 */
public interface OrderFulfillerDubboService {

    Response<Boolean> orderTransfer(OrderTransferDto orderTransferDto);

    Response<Boolean> orderTransferBack(OrderTransferBackDto orderTransferBackDto);

    Response<Boolean> orderDelivery(OrderDeliveryDto orderDeliveryDto);

    Response<Boolean> orderSendCar(OrderSendCarDto pOrderSendCarDto);

    Response<Boolean> orderReceive(OrderReceiveDto orderReceiveDto);

    Response<Boolean> orderCancelConfirm(OrderCancelConfirmDto pOrderCancelConfirmDto);

    Response<Boolean> orderCancelFail(OrderCancelFailDto pOrderCancelFailDto);


    /**
     * 订单拒签
     *
     * @param orderRejectDto
     * @return
     */
    Response<Boolean> orderReject(OrderRejectDto orderRejectDto);

    @Deprecated
    Response<Boolean> orderNotReceive(OrderNotReceiveDto orderNotReceiveDto);

    Response<Boolean> orderCancelShipping(OrderCancelShippingDto orderCancelShippingDto);

    Response<Boolean> orderWMSReject(OrderWMSRejectDto orderWMSRejectDto);

    Response<Boolean> providerOrderASN(String orderId,String receiptId);

    Response<Boolean> providerOrderSingReq(ProviderOrderSingReqDto pProviderOrderSingReqDto);
}
