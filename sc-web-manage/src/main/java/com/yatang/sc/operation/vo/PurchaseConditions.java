package com.yatang.sc.operation.vo;

import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * 购买条件
 */
public class PurchaseConditions {

    /**
     * 商品的分类
     */
    private PromoCategoriesVo promoCategoriesVo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 条件类型 1代表累计商品金额。2代表累计商品数量
     */
    private String conditionType;

    /**
     * 条件金额
     * */
    @DecimalMin(value = "0", inclusive = false, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.largeThanZero.message}")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.bigDecimal.message}")
    private BigDecimal quanifyAmount;

    public PromoCategoriesVo getpromoCategoriesVo() {
        return promoCategoriesVo;
    }

    public void setpromoCategoriesVo(PromoCategoriesVo promoCategoriesVo) {
        this.promoCategoriesVo = promoCategoriesVo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public BigDecimal getQuanifyAmount() {
        return quanifyAmount;
    }

    public void setQuanifyAmount(BigDecimal quanifyAmount) {
        this.quanifyAmount = quanifyAmount;
    }
}
