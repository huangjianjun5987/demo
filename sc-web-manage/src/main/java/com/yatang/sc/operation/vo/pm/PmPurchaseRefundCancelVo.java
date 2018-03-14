package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;

/**
 * @描述: 取消退货单VO
 * @作者: huangjianjun
 * @创建时间: 2017年11月9日-下午4:47:08 .
 */
public class PmPurchaseRefundCancelVo implements Serializable{
	private static final long	serialVersionUID	= 1L;
	/**
	 * 退货单ID
	 */
	private Long id;
	/**
	 * 退货单号
	 */
	private String				purchaseRefundNo;
	
	/**
	 * 退货地点类型:0:仓库;1:门店
	 */
	private Integer				adrType;
	

	/**
	 * 退货地点编码
	 */
	private String				refundAdrCode;
	
	



	public String getPurchaseRefundNo() {
		return purchaseRefundNo;
	}



	public void setPurchaseRefundNo(String purchaseRefundNo) {
		this.purchaseRefundNo = purchaseRefundNo;
	}



	public String getRefundAdrCode() {
		return refundAdrCode;
	}



	public void setRefundAdrCode(String refundAdrCode) {
		this.refundAdrCode = refundAdrCode;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getAdrType() {
		return adrType;
	}



	public void setAdrType(Integer adrType) {
		this.adrType = adrType;
	}
	
}
