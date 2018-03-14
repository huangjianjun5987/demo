package com.yatang.sc.app.vo.payment;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.PayWayCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/11.
 */
public class PrePayRequestVO implements Serializable {
    @NotNull(message = "PayType 不能为空")
    private PayType mPayType;
    @NotNull(message = "Body 不能为空")
    @Length(max = 128)
    private String mBody;//商品描述
    @NotNull(message = "OutOrderNo 不能为空")
    private String mOutOrderNo; //商户订单号
    @NotNull(message = "SpbillCreateIp 不能为空")
    private String mSpbillCreateIp; //用户端实际ip
    @NotNull(message = "TotalFee 不能为空")
    private Double mTotalFee;//总金额(元)
    private String mUserName;//账户名
    private String mShopUserName;
    private PayWayCode mPayWayCode = PayWayCode.APP_PAY;//支付方式

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

    public String getShopUserName() {
        return mShopUserName;
    }

    public void setShopUserName(String pShopUserName) {
        mShopUserName = pShopUserName;
    }

    @Override
    public String toString() {
        return "PrePayRequestVO{" +
                ", mPayType=" + mPayType +
                ", mBody='" + mBody + '\'' +
                ", mOutOrderNo='" + mOutOrderNo + '\'' +
                ", mSpbillCreateIp='" + mSpbillCreateIp + '\'' +
                ", mTotalFee=" + mTotalFee +
                ", mUserName='" + mUserName + '\'' +
                ", mShopUserName='" + mShopUserName + '\'' +
                ", mPayWayCode=" + mPayWayCode +
                '}';
    }
}
