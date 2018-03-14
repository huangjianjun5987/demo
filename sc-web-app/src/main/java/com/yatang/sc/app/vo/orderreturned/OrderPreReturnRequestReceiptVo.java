package com.yatang.sc.app.vo.orderreturned;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @描述: 退货预览返回接口
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 9:27
 * @版本: v1.0
 */
public class OrderPreReturnRequestReceiptVo implements Serializable {
    private static final long serialVersionUID = -4660347118732612731L;


    @Valid
    private OrderReturnRequestVo request;

    @Valid
    @NotEmpty(message = "{msg.notEmpty.message}")
    private List<OrderReturnRequestItemVo> items;

    public OrderReturnRequestVo getRequest() {
        return request;
    }

    public void setRequest(OrderReturnRequestVo request) {
        this.request = request;
    }

    public List<OrderReturnRequestItemVo> getItems() {
        return items;
    }

    public void setItems(List<OrderReturnRequestItemVo> items) {
        this.items = items;
    }
}
