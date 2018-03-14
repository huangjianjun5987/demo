package com.yatang.sc.order.domain.returned;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AppQueryReturnRequestItemPo implements Serializable {

	private static final long	serialVersionUID	= 3229548720378779843L;
	private String				saleName;
	private double				price;
	private double				rawTotalPrice;
	private long				num;
	private String				product_id;
	private String				sku_id;
	private Integer				unitQuantity;								// 销售内装数
	private Integer				quantity;									// 退货数量



	public String getSaleName() {
		return saleName;
	}



	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public double getRawTotalPrice() {
		return rawTotalPrice;
	}



	public void setRawTotalPrice(double rawTotalPrice) {
		this.rawTotalPrice = rawTotalPrice;
	}



	public long getNum() {
		return num;
	}



	public void setNum(long num) {
		this.num = num;
	}



	public String getProduct_id() {
		return product_id;
	}



	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}



	public String getSku_id() {
		return sku_id;
	}



	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}



	public Integer getUnitQuantity() {
		return unitQuantity;
	}



	public void setUnitQuantity(Integer unitQuantity) {
		this.unitQuantity = unitQuantity;
	}



	public Integer getQuantity() {
		return quantity;
	}



	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}