package com.yatang.sc.order.domain.returned;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 换退货单
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/18 10:48
 * @版本: v1.0
 */
public class ReturnRequestReceiptPo implements Serializable {


    private static final long serialVersionUID = -4190943115680897468L;
    private ReturnRequestPo returnRequest;

    private List<ReturnRequestItemPo> requestItems;

    public ReturnRequestPo getReturnRequest() {
        return returnRequest;
    }

    public void setReturnRequest(ReturnRequestPo returnRequest) {
        this.returnRequest = returnRequest;
    }

    public List<ReturnRequestItemPo> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<ReturnRequestItemPo> requestItems) {
        this.requestItems = requestItems;
    }
}

