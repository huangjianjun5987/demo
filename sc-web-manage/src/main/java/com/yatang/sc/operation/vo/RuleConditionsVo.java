package com.yatang.sc.operation.vo;


import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 条件
 *
 * @author dengdongshan
 */

public class RuleConditionsVo implements Serializable {

    private static final long serialVersionUID = -6783373183582581806L;



    /**
     * 前段所需要的标识符
     */

    private String key;
    /**
     * 按品类
     */
    private String purchaseType;

    private PromoCategoriesVo promoCategories;
    /**
     * 按商品
     */

    private PromoProductVo promoProduct;
    /**
     * 累计数量或者金额
     */
    private String conditionType;

    /**
     * 条件金额
     */
    @DecimalMin(value = "0", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.largeThanZero.message}")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.bigDecimal.message}")
    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, GroupTwo.class})
    private BigDecimal conditionValue;//

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public PromoCategoriesVo getPromoCategories() {
        return promoCategories;
    }

    public void setPromoCategories(PromoCategoriesVo promoCategories) {
        this.promoCategories = promoCategories;
    }

    public PromoProductVo getPromoProduct() {
        return promoProduct;
    }

    public void setPromoProduct(PromoProductVo promoProduct) {
        this.promoProduct = promoProduct;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public BigDecimal getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(BigDecimal conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    /**
     * 购买类型枚举
     * CATEGORY,PRODUCT
     *
     * @author dengdongshan
     */
    public enum PurchaseType {
        CATEGORY("CATEGORY"), PRODUCT("PRODUCT"),ALL("ALL");

        private String name;

        private PurchaseType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    /**
     * 购买类型枚举
     * AMOUNT,QUANTITY
     *
     * @author dengdongshan
     */
    public enum ConditionType {
        AMOUNT("AMOUNT"), QUANTITY("QUANTITY");

        private String name;

        private ConditionType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}