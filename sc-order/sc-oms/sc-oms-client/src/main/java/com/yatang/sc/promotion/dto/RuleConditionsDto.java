/**
 * 
 */
package com.yatang.sc.promotion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yatang.sc.dto.PromoCategoriesDto;
import com.yatang.sc.dto.PromoProductDto;

/**
 * 条件
 * @author dengdongshan
 *
 */
public class RuleConditionsDto implements Serializable {

	private static final long serialVersionUID = -6783373183582581806L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String purchaseType;

	private PromoCategoriesDto   promoCategories;

	private PromoProductDto promoProduct;

	private String conditionType;

	private BigDecimal conditionValue;
    
    public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public PromoCategoriesDto getPromoCategories() {
		return promoCategories;
	}

	public void setPromoCategories(PromoCategoriesDto promoCategories) {
		this.promoCategories = promoCategories;
	}

	public PromoProductDto getPromoProduct() {
		return promoProduct;
	}

	public void setPromoProduct(PromoProductDto promoProduct) {
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

	/**
     * 购买类型枚举
     * CATEGORY,PRODUCT
     * @author dengdongshan
     *
     */
    public enum PurchaseType {
    	ALL("ALL"), CATEGORY("CATEGORY"), PRODUCT("PRODUCT");

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
     * @author dengdongshan
     *
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
