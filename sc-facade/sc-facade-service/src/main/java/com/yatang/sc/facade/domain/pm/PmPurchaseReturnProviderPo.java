package com.yatang.sc.facade.domain.pm;

import java.util.Date;
import java.util.List;

/**
 * @描述:供应商退货单PO
 * @作者: leixin
 * @创建时间: 2018/1/10 17:26
 * @版本: v1.0
 */
public class PmPurchaseReturnProviderPo extends PmPurchaseRefundPo {

	private static final long	serialVersionUID	= 1L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 供应商ID
	 */
	private String spId;

	/**
	 * 供应商编码
	 */
	private String spNo;
	/**
	 * 供应商地点ID
	 */
	private String spAdrId;

	/**
	 * 供应商地点编码
	 */
	private String spAdrNo;
	/**
	 * 退货地点类型:0:仓库;1:门店
	 */
	private Integer adrType;
	/**
	 * 退货单号
	 */
	private String purchaseRefundNo;
	/**
	 * 退货地点编码
	 */
	private String	refundAdrCode;

	/**
	 * 退货地点名称
	 */
	private String refundAdrName;
	/**
	 * 状态:0:制单;1:已提交;2:已审核;3:已拒绝;4:待退货;5:已退货;6:已取消;7:取消失败;8:异常
	 */
	private Integer status;
	/**
	 * 退货日期起
	 */
	private Date				createTimeStart;
	/**
	 * 退货日期止
	 */
	private Date				createTimeEnd;
	/**
	 * 退货日期
	 */
	private Date refundTime;
	/**
	 * 当前页
	 */
	private Integer				pageNum;
	/**
	 * 每页显示记录数
	 */
	private Integer				pageSize;
	/**
	 * 分公司ID
	 */
	private String				branchCompanyId;

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
	private List<Long>				purchaseOrderNo;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getSpId() {
		return spId;
	}

	@Override
	public void setSpId(String spId) {
		this.spId = spId;
	}

	@Override
	public String getSpNo() {
		return spNo;
	}

	@Override
	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}

	@Override
	public String getSpAdrId() {
		return spAdrId;
	}

	@Override
	public void setSpAdrId(String spAdrId) {
		this.spAdrId = spAdrId;
	}

	@Override
	public String getSpAdrNo() {
		return spAdrNo;
	}

	@Override
	public void setSpAdrNo(String spAdrNo) {
		this.spAdrNo = spAdrNo;
	}

	@Override
	public Integer getAdrType() {
		return adrType;
	}

	@Override
	public void setAdrType(Integer adrType) {
		this.adrType = adrType;
	}

	@Override
	public String getPurchaseRefundNo() {
		return purchaseRefundNo;
	}

	@Override
	public void setPurchaseRefundNo(String purchaseRefundNo) {
		this.purchaseRefundNo = purchaseRefundNo;
	}

	@Override
	public String getRefundAdrCode() {
		return refundAdrCode;
	}

	@Override
	public void setRefundAdrCode(String refundAdrCode) {
		this.refundAdrCode = refundAdrCode;
	}

	@Override
	public String getRefundAdrName() {
		return refundAdrName;
	}

	@Override
	public void setRefundAdrName(String refundAdrName) {
		this.refundAdrName = refundAdrName;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	public Date getRefundTime() {
		return refundTime;
	}

	@Override
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
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

	@Override
	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	@Override
	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public Integer getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Integer orderItem) {
		this.orderItem = orderItem;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public List<Long> getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(List<Long> purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}
}