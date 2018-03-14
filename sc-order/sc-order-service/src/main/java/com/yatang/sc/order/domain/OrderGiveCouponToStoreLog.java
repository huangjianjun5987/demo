package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单返券日志
 * @author dengdongshan
 *
 */
public class OrderGiveCouponToStoreLog implements Serializable {
	private static final long serialVersionUID = 9047640690434794122L;
	private String id;
	private Date creationTime; 
    private Long priceInfoId;
    private boolean success;
    private String giveCouponInfo;
    private double discount;
    private double giveAmount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getPriceInfoId() {
		return priceInfoId;
	}
	public void setPriceInfoId(Long priceInfoId) {
		this.priceInfoId = priceInfoId;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getGiveCouponInfo() {
		return giveCouponInfo;
	}
	public void setGiveCouponInfo(String giveCouponInfo) {
		this.giveCouponInfo = giveCouponInfo;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getGiveAmount() {
		return giveAmount;
	}
	public void setGiveAmount(double giveAmount) {
		this.giveAmount = giveAmount;
	}
    
    
}