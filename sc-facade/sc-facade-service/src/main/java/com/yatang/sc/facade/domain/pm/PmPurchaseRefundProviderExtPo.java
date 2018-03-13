package com.yatang.sc.facade.domain.pm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * <class description>退货单PO
 * 
 * @author: leixin
 * @version: 1.0, 2018年01月11日
 */
public class PmPurchaseRefundProviderExtPo implements Serializable {

	private static final long	serialVersionUID	= 1L;
	/**
	 * 供应商ID
	 */
	private String				spId;
	/**
	 * 供应商地点ID
	 */
	private String				spAdrId;
	/**
	 * 地点类型
	 */
	private Integer				adrType;
	/**
	 * 退货单号
	 */
	private String				purchaseRefundNo;
	/**
	 * 退货地点编码
	 */
	private String				refundAdrCode;
	/**
	 * 状态
	 */
	private String				status;
	/**
	 * 退货日期起
	 */
	private Date				createTimeStart;
	/**
	 * 退货日期止
	 */
	private Date				createTimeEnd;
	/**
	 * 当前页
	 */
	private Integer				pageNum;
	/**
	 * 每页显示记录数
	 */
	private Integer				pageSize;
	/**
	 * 排序字段:退货单号：0,创建日期：1,状态：2
	 */
	private Integer				orderItem;
	/**
	 * 排序方式:0升序,1降序
	 */
	private Integer				orderType;
	/**
	 * 关联采购单号
	 */
	private Long				purchaseOrderNo;

	/**
	 * 子供应商地点ID
	 */
	private List<String> branchCompanyIds;

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpAdrId() {
		return spAdrId;
	}

	public void setSpAdrId(String spAdrId) {
		this.spAdrId = spAdrId;
	}

	public Integer getAdrType() {
		return adrType;
	}

	public void setAdrType(Integer adrType) {
		this.adrType = adrType;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Integer orderItem) {
		this.orderItem = orderItem;
	}

	public Long getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(Long purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}



	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}



	public Date getCreateTimeStart() {
		return createTimeStart;
	}



	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}



	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}



	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}



	public Integer getPageNum() {
		return pageNum;
	}



	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}



	public List<String> getBranchCompanyIds() {
		return branchCompanyIds;
	}



	public void setBranchCompanyIds(List<String> branchCompanyIds) {
		this.branchCompanyIds = branchCompanyIds;
	}
}