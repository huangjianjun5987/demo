package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.split.SplitOrderSubGroupDto;

import java.util.Map;

public interface SplitOrderService {
    Response<Boolean> splitOrderByInventory(String mainOrder, SplitOrderRequestDto groups);

    Response<Boolean> manualSplitOrderByInventory(String mainOrder, SplitOrderRequestDto groups);

    Response<Boolean> splitOrderByShippingModes(String mainOrder);

    /**
     * 直配订单拆单
     *
     * @param mainOrder
     * @return
     * @throws Exception
     */
    Response<String> splitOrderByASN(String mainOrder,  String receiptId) throws Exception;
}
