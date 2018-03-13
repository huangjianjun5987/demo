package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.util.List;



/**
 * @描述:入库单确认实体类（发送mq）
 * @类名:KiddPurchaseOrderConfirmDto
 * @作者: lvheping
 * @创建时间: 2017/9/27 9:57
 * @版本: v1.0
 */

public class PurchaseOrderConfirmDto implements Serializable {
    private static final long serialVersionUID = -2892855290709435168L;
    private PurchaseConfirmEntryOrderDto entryOrder;//采购确认单基本信息
    private List<PurchaseConfirmOrderLinesDto> orderLines;//商品详细信息

    @Override
    public String toString() {
        return "KiddPurchaseOrderConfirmDto{" +
                "entryOrder=" + entryOrder +
                ", orderLines=" + orderLines +
                '}';
    }

    public void setEntryOrder(PurchaseConfirmEntryOrderDto entryOrder) {
        this.entryOrder = entryOrder;
    }

    public void setOrderLines(List<PurchaseConfirmOrderLinesDto> orderLines) {
        this.orderLines = orderLines;
    }

    public PurchaseConfirmEntryOrderDto getEntryOrder() {
        return entryOrder;
    }

    public List<PurchaseConfirmOrderLinesDto> getOrderLines() {
        return orderLines;
    }
}
