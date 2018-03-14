package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 退货预览返回接口
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 9:27
 * @版本: v1.0
 */
public class OrderPreReturnRequestReceiptDto implements Serializable {


    private static final long serialVersionUID = -3353194911075847386L;
    private OrderReturnRequestDto request;


    private List<OrderReturnRequestItemDto> items;

    public OrderReturnRequestDto getRequest() {
        return request;
    }

    public void setRequest(OrderReturnRequestDto request) {
        this.request = request;
    }

    public List<OrderReturnRequestItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderReturnRequestItemDto> items) {
        this.items = items;
    }
}
