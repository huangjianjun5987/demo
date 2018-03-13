package com.yatang.sc.juban.dto.purchase;

import java.io.Serializable;
import java.util.List;

import com.yatang.sc.xinyi.dto.purchase.XinyiConfirmEntryOrderDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiConfirmOrderLinesDto;


/**
 * @描述:入库单确认实体类
 * @类名:KiddPurchaseOrderConfirmDto
 * @作者: lvheping
 * @创建时间: 2017/9/27 9:57
 * @版本: v1.0
 */

public class JubanPurchaseOrderConfirmRequestDto implements Serializable {

    private static final long serialVersionUID = 6933837475564191043L;
    private JubanConfirmEntryOrderDto entryOrder;//采购确认单基本信息
    private List<JubanConfirmOrderLinesDto> orderLines;//商品详细信息

    public JubanConfirmEntryOrderDto getEntryOrder() {
        return entryOrder;
    }

    public void setEntryOrder(JubanConfirmEntryOrderDto entryOrder) {
        this.entryOrder = entryOrder;
    }

    public List<JubanConfirmOrderLinesDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<JubanConfirmOrderLinesDto> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "XinyiPurchaseOrderConfirmRequestDto{" +
                "entryOrder=" + entryOrder +
                ", orderLines=" + orderLines +
                '}';
    }
}
