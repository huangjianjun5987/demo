/**
 * 
 */
package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengdongshan
 *
 */
public class OrderRulePo implements Serializable{
	private static final long serialVersionUID = -4849258759516444027L;

    private String preferentialWay;

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
