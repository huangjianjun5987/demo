package com.yatang.sc.payment.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by yuwei on 2017/7/17.
 */
public class QueryPayStatusRequestDto extends NoneStrRequestDto {
    @NotNull
    private String mPayNo;//不能和orderNo同时为空
    @NotNull
    private String mOrderNo;//不能和PayNo同时为空

    public String getPayNo() {
        return mPayNo;
    }

    public void setPayNo(String pPayNo) {
        mPayNo = pPayNo;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String pOrderNo) {
        mOrderNo = pOrderNo;
    }
}
