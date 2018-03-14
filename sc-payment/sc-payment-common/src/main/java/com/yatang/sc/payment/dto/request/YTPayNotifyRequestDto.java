package com.yatang.sc.payment.dto.request;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/14.
 */
public class YTPayNotifyRequestDto extends AbstractPayNotifyRequestDto implements Serializable {
    private String mTradeStatus;

    public String getTradeStatus() {
        return mTradeStatus;
    }

    public void setTradeStatus(String pTradeStatus) {
        mTradeStatus = pTradeStatus;
    }
}
