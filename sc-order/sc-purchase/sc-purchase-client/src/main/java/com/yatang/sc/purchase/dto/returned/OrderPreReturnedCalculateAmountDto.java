package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;

/**
 * @描述: 金额与计算返回参数(退款金额和退货金额)
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 9:30
 * @版本: v1.0
 */
public class OrderPreReturnedCalculateAmountDto implements Serializable {


    private static final long serialVersionUID = 6185857954434390090L;


    private double productReturnedAmount;//退货金额


    private double refundAmount;//退款金额

    public double getProductReturnedAmount() {
        return productReturnedAmount;
    }

    public void setProductReturnedAmount(double productReturnedAmount) {
        this.productReturnedAmount = productReturnedAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }
}
