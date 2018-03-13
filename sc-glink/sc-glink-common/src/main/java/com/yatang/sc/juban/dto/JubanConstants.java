package com.yatang.sc.juban.dto;

import com.yatang.sc.juban.dto.im.JubanInventoryAdjustNoticeRequestDto;
import com.yatang.sc.juban.dto.orderNotice.JubanOrderNoticeRequestInfoDto;
import com.yatang.sc.juban.dto.purchase.JubanPurchaseOrderConfirmRequestDto;
import com.yatang.sc.juban.dto.returnrequest.JubanReturnOrderRequestDto;
import com.yatang.sc.juban.dto.saleOrder.JubanSaleOrderConfirmRequestDto;
import com.yatang.sc.juban.dto.saleOrder.JubanSaleOrderJYCKConfirmRequestDto;

import java.util.HashMap;
import java.util.Map;

public abstract class JubanConstants {

    public static final Map<String, Class> provders = new HashMap<>();

    static {
        provders.put("taobao.qimen.entryorder.confirm", JubanPurchaseOrderConfirmRequestDto.class);
        provders.put("taobao.qimen.stockout.confirm", JubanSaleOrderConfirmRequestDto.class);
        provders.put("taobao.qimen.deliveryorder.confirm", JubanSaleOrderJYCKConfirmRequestDto.class);
        provders.put("taobao.qimen.returnorder.confirm", JubanReturnOrderRequestDto.class);
        provders.put("taobao.qimen.orderprocess.report", JubanOrderNoticeRequestInfoDto.class);
        provders.put("taobao.qimen.inventory.report", JubanInventoryAdjustNoticeRequestDto.class);
    }

}
