/**
 * 
 */
package com.yatang.sc.order.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 条件
 * @author dengdongshan
 *
 */
public class RuleConditionsPo implements Serializable {

	private static final long serialVersionUID = -6783373183582581806L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String purchaseType;

	private PromoCategoriesPo   promoCategories;

	private PromoProductPo promoProduct;

	private String conditionType;

	private BigDecimal conditionValue;
    
    public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public PromoCategoriesPo getPromoCategories() {
		return promoCategories;
	}

	public void setPromoCategories(PromoCategoriesPo promoCategories) {
		this.promoCategories = promoCategories;
	}

	public PromoProductPo getPromoProduct() {
		return promoProduct;
	}

	public void setPromoProduct(PromoProductPo promoProduct) {
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
