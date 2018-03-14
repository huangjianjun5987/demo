package com.yatang.sc.payment.dto.request;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.PayWayCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by yuwei on 2017/7/10.
 */
public class PrePayRequestDto extends NoneStrRequestDto {
    @NotNull
    private PayType mPayType;
    @NotNull
    @Size(min = 1, max = 128)
    private String mBody;//商品描述
    @NotNull
    @Size(min = 1, max = 64)
    private String mOutOrderNo; //商户订单号
    @NotNull
    @Size(min = 1, max = 16)
    private String mSpbillCreateIp; //用户端实际ip
    @NotNull
    @Digits(fraction = 4, integer = 10)
    private Double mTotalFee;//总金额(元)
    @NotNull
    private PayWayCode mPayWayCode = PayWayCode.APP_PAY;//支付方式

    /***以下字段雅堂余额专用**/
    private String mUserName;//账户名

    public PayType getPayType() {
        return mPayType;
    }

    public void setPayType(PayType pPayType) {
        mPayType = pPayType;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String pBody) {
        mBody = pBody;
    }

    public String getOutOrderNo() {
        return mOutOrderNo;
    }

    public void setOutOrderNo(String pOutOrderNo) {
        mOutOrderNo = pOutOrderNo;
    }

    public String getSpbillCreateIp() {
        return mSpbillCreateIp;
    }

    public void setSpbillCreateIp(String pSpbillCreateIp) {
        mSpbillCreateIp = pSpbillCreateIp;
    }

    public Double getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Double pTotalFee) {
        mTotalFee = pTotalFee;
    }

    public PayWayCode getPayWayCode() {
        return mPayWayCode;
    }

    public void setPayWayCode(PayWayCode pPayWayCode) {
        mPayWayCode = pPayWayCode;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String pUserName) {
        mUserName = pUserName;
    }

    @Override
    public String toString() {
        return "PrePayRequestDto{" +
                "mPayType=" + mPayType +
                ", mBody='" + mBody + '\'' +
                ", mOutOrderNo='" + mOutOrderNo + '\'' +
                ", mSpbillCreateIp='" + mSpbillCreateIp + '\'' +
                ", mTotalFee='" + mTotalFee + '\'' +
                ", mPayWayCode=" + mPayWayCode +
                '}';
    }
}
