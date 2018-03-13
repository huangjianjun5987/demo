package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


public class ProdSellSectionPriceDto implements Serializable {
	private Integer				id;							// 区间价格id

	private BigDecimal			price;						// 区间价格

	private Integer				startNumber;				// 开始区间

	private Integer				endNumber;					// 结束区间

	private Integer				sellPriceId;				// 定价id
	
	private Integer				deleteStatus;				// 0为未删除状态1为删除状态

	private BigDecimal			percentage;					// 毛利率(不持久化) yinyuxin

	private static final long	serialVersionUID	= 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	public Integer getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(Integer endNumber) {
		this.endNumber = endNumber;
	}

	public Integer getSellPriceId() {
		return sellPriceId;
	}

	public void setSellPriceId(Integer sellPriceId) {
		this.sellPriceId = sellPriceId;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "ProdSellSectionPriceDto{" +
				"id=" + id +
				", price=" + price +
				", startNumber=" + startNumber +
				", endNumber=" + endNumber +
				", sellPriceId=" + sellPriceId +
				", deleteStatus=" + deleteStatus +
				", percentage=" + percentage +
				'}';
	}
}