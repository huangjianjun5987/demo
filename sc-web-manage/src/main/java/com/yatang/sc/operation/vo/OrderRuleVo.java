package com.yatang.sc.operation.vo;



import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengdongshan
 *
 */
public class OrderRuleVo implements Serializable {
    private static final long serialVersionUID = -4849258759516444027L;

    /**
     *优惠方式
     */
    private String preferentialWay;

    /**
     *优惠的结果折扣
     */
    @NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
    @DecimalMin(value = "0", groups = {GroupOne.class, GroupTwo.class}, message = "{msg.largeThanZero.message}")
    //@Digits(integer = 2, fraction = 0, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.bigDecimal.message}")
    private BigDecimal preferentialValue;


    public String getPreferentialWay() {
        return preferentialWay;
    }



    public void setPreferentialWay(String preferentialWay) {
        this.preferentialWay = preferentialWay;
    }



    public BigDecimal getPreferentialValue() {
        return preferentialValue;
    }



    public void setPreferentialValue(BigDecimal preferentialValue) {
        this.preferentialValue = preferentialValue;
    }


    /**
     * 优惠方式枚举
     * AMOUNT,QUANTITY,FIXEDPRICE,GIVESAMEPRODUCT
     * @author dengdongshan
     *
     */
    public enum PreferentialWay {
        PERCENTAGE("PERCENTAGE"), DISCOUNTAMOUNT("DISCOUNTAMOUNT"),FIXEDPRICE("FIXEDPRICE"),GIVESAMEPRODUCT("GIVESAMEPRODUCT");

        private String name;

        private PreferentialWay(String name) {
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
