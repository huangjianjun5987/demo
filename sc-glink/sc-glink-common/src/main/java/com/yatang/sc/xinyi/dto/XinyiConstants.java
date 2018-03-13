package com.yatang.sc.xinyi.dto;

import com.yatang.sc.xinyi.dto.im.XinYiInventoryAdjustNoticeRequestDto;
import com.yatang.sc.xinyi.dto.orderNotice.XinyiOrderNoticeRequestInfoDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiPurchaseOrderConfirmRequestDto;
import com.yatang.sc.xinyi.dto.returnrequest.XinyiReturnOrderRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderJYCKConfirmRequestDto;

import java.util.HashMap;
import java.util.Map;

public abstract class XinyiConstants {

    public static final Map<String, Class> provders = new HashMap<>();

    static {
        provders.put("taobao.qimen.entryorder.confirm", XinyiPurchaseOrderConfirmRequestDto.class);
        provders.put("taobao.qimen.stockout.confirm", SaleOrderConfirmRequestDto.class);
        provders.put("taobao.qimen.deliveryorder.confirm", SaleOrderJYCKConfirmRequestDto.class);
        provders.put("taobao.qimen.returnorder.confirm", XinyiReturnOrderRequestDto.class);
        provders.put("taobao.qimen.orderprocess.report", XinyiOrderNoticeRequestInfoDto.class);
        provders.put("taobao.qimen.inventory.report", XinYiInventoryAdjustNoticeRequestDto.class);
    }

}
