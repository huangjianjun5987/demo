package com.yatang.sc.payment.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/17.
 */
public abstract class NoneStrRequestDto implements Serializable {
    @NotNull(message = "请求随机字符串不能为空")
    @Size(min = 32, max = 32, message = "随机字符串为32位")
    private String mNonceStr;//32位随机字符串

    public String getNonceStr() {
        return mNonceStr;
    }

    public void setNonceStr(String pNonceStr) {
        mNonceStr = pNonceStr;
    }
}
