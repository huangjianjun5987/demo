package com.yatang.sc.juban.dto.returnrequest;

import com.yatang.sc.juban.dto.common.JubanOrderLineDto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:心怡退货订单请求参数
 * @类名:ReturnOrderRequestDto
 * @作者: lvheping
 * @创建时间: 2017/10/17 11:13
 * @版本: v1.0
 */

public class JubanReturnOrderRequestDto implements Serializable {
    private static final long serialVersionUID = -2113670958693368543L;
    private JubanReturnOrderDto returnOrder;//心怡退货单基本信息
    private List<JubanOrderLineDto> orderLines;//商品信息

    public JubanReturnOrderDto getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(JubanReturnOrderDto returnOrder) {
        this.returnOrder = returnOrder;
    }

    public List<JubanOrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<JubanOrderLineDto> orderLines) {
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
