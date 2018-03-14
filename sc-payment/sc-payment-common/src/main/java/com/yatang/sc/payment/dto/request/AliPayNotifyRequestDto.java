package com.yatang.sc.payment.dto.request;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/13.
 */
public class AliPayNotifyRequestDto extends AbstractPayNotifyRequestDto implements Serializable {
    private String mNotifyTime;//通知时间
    private String mNotifyId;//通知ID
    private String mTradeStatus;//交易状态

    public String getNotifyTime() {
        return mNotifyTime;
    }

    public void setNotifyTime(String pNotifyTime) {
        mNotifyTime = pNotifyTime;
    }

    public String getNotifyId() {
        return mNotifyId;
    }

    public void setNotifyId(String pNotifyId) {
        mNotifyId = pNotifyId;
    }

    public String getTradeStatus() {
        return mTradeStatus;
    }

    public void setTradeStatus(String pTradeStatus) {
        mTradeStatus = pTradeStatus;
    }


    @Override
    public String toString() {
        return "AliPayNotifyRequestDto{" +
                "mNotifyTime='" + mNotifyTime + '\'' +
                ", mNotifyId='" + mNotifyId + '\'' +
                ", mTradeStatus='" + mTradeStatus + '\'' +
                '}';
    }
}
