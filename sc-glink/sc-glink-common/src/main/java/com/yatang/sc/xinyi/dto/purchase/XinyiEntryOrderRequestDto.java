package com.yatang.sc.xinyi.dto.purchase;

import java.io.Serializable;
import java.util.List;

import com.yatang.sc.xinyi.dto.common.XinyiOrderLineDto;

/**
 * @描述:推送采购订单信息到心怡仓库
 * @类名:XinyiRequestEntryOrderDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 15:15
 * @版本: v1.0
 */

public class XinyiEntryOrderRequestDto implements Serializable {
    private static final long serialVersionUID = -8507155714747809243L;
    private XinyiEntryOrderDto entryOrder;//入库单基本信息
    private List<XinyiOrderLineDto> orderLines;//商品详细信息

    public XinyiEntryOrderDto getEntryOrder() {
        return entryOrder;
    }

    public void setEntryOrder(XinyiEntryOrderDto entryOrder) {
        this.entryOrder = entryOrder;
    }

    public List<XinyiOrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<XinyiOrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "XinyiEntryOrderRequestDto{" +
                "xinyiEntryOrderDto=" + entryOrder +
                ", xinyiOrderLineDtoList=" + orderLines +
                '}';
    }
}
