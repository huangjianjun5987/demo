package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 换退货单
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/18 10:48
 * @版本: v1.0
 */
public class ReturnRequestReceiptDto implements Serializable {


    private static final long serialVersionUID = -4190943115680897468L;
    private ReturnRequestDto returnRequest;

    private List<ReturnRequestItemDto> requestItems;
    /**
     * 是否来源于后台退货：0-否；1-是（后台退货默认传0）
     */
    private Integer source=1;

    public ReturnRequestDto getReturnRequest() {
        return returnRequest;
    }

    public void setReturnRequest(ReturnRequestDto returnRequest) {
        this.returnRequest = returnRequest;
    }

    public List<ReturnRequestItemDto> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<ReturnRequestItemDto> requestItems) {
        this.requestItems = requestItems;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}

