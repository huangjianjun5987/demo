package com.yatang.sc.payment.dto.request;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.payment.enums.NotifyType;
import com.yatang.sc.payment.enums.PayType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/11.
 */
public class PayNotifyRequestDto implements Serializable {
    @NotNull
    private PayType mPayType;//支付类型
    @NotNull
    private NotifyType mNotifyType;//通知类型

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

    public NotifyType getNotifyType() {
        return mNotifyType;
    }

    public void setNotifyType(NotifyType pNotifyType) {
        mNotifyType = pNotifyType;
    }

    private Map<String, String> mOriginalParameters = new HashMap<>();

    public Map<String, String> getOriginalParameters() {
        return mOriginalParameters;
    }

    public void putAll(Map<String, String> pOriginalParameters) {
        mOriginalParameters.putAll(pOriginalParameters);
    }


    public void put(String pKey, String pVal) {
        mOriginalParameters.put(pKey, pVal);
    }

    public String get(String pKey) {
        return mOriginalParameters.get(pKey);
    }

    @Override
    public String toString() {
        return "PayNotifyRequestDto{" +
                "mPayType=" + mPayType +
                "Map=" + JSON.toJSONString(getOriginalParameters()) +
                '}';
    }
}
