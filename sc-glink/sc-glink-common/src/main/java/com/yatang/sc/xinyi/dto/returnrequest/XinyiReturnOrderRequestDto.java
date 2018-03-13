package com.yatang.sc.xinyi.dto.returnrequest;

import com.yatang.sc.xinyi.dto.common.XinyiOrderLineDto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:心怡退货订单请求参数
 * @类名:ReturnOrderRequestDto
 * @作者: lvheping
 * @创建时间: 2017/10/17 11:13
 * @版本: v1.0
 */

public class XinyiReturnOrderRequestDto implements Serializable {
    private static final long serialVersionUID = -2113670958693368543L;
    private XinyiReturnOrderDto returnOrder;//心怡退货单基本信息
    private List<XinyiOrderLineDto> orderLines;//商品信息

    public XinyiReturnOrderDto getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(XinyiReturnOrderDto returnOrder) {
        this.returnOrder = returnOrder;
    }

    public List<XinyiOrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<XinyiOrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "XinyiReturnOrderRequestDto{" +
                "returnOrder=" + returnOrder +
                ", orderLines=" + orderLines +
                '}';
    }
}
