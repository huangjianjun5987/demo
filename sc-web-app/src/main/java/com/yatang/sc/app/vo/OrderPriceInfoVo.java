package com.yatang.sc.app.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 商品分类vo(app)
 * @作者: yinyuxin
 * @创建时间: 2017年7月8日16:15:53
 * @版本: 1.0 .
 */
public class OrderPriceInfoVo implements Serializable{

	private static final long serialVersionUID = -4883964242355956290L;
	private double amount;

	private double shipping;

	private double rawSubtotal;  //订单中商品未执行任何折扣的净额

	private double total;

	private double discountAmount;

	private double userDiscountAmount;

	private double couponDiscountAmount;

	private double giveAmount;

	public double getGiveAmount() {
		return giveAmount;
	}

	public void setGiveAmount(double giveAmount) {
		this.giveAmount = giveAmount;
	}

	public double getCouponDiscountAmount() {
		return couponDiscountAmount;
	}

	public void setCouponDiscountAmount(double couponDiscountAmount) {
		this.couponDiscountAmount = couponDiscountAmount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getUserDiscountAmount() {
		return userDiscountAmount;
	}

	public void setUserDiscountAmount(double userDiscountAmount) {
		this.userDiscountAmount = userDiscountAmount;
	}

	public double getRawSubtotal() {
		return rawSubtotal;
	}

	public void setRawSubtotal(double rawSubtotal) {
		this.rawSubtotal = rawSubtotal;
	}
}
