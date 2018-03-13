package com.yatang.sc.juban.dto.purchase;

import com.yatang.sc.juban.dto.common.JubanOrderLineDto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:推送采购订单信息到心怡仓库
 * @类名:XinyiRequestEntryOrderDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 15:15
 * @版本: v1.0
 */

public class JubanEntryOrderRequestDto implements Serializable {
    private static final long serialVersionUID = -8507155714747809243L;
    private JubanEntryOrderDto entryOrder;//入库单基本信息
    private List<JubanOrderLineDto> orderLines;//商品详细信息

    public JubanEntryOrderDto getEntryOrder() {
        return entryOrder;
    }

    public void setEntryOrder(JubanEntryOrderDto entryOrder) {
        this.entryOrder = entryOrder;
    }

    public List<JubanOrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<JubanOrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "jubanEntryOrderRequestDto{" +
                "jubanEntryOrderDto=" + entryOrder +
                ", jubanOrderLineDtoList=" + orderLines +
                '}';
    }
}
