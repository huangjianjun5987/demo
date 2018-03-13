package com.yatang.sc.facade.domain.pm;

import java.util.Date;
import java.util.List;

/**
 * 
 * <class description>退货单PO拓展，添加两个字段以便查询
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年10月18日
 */
public class PmPurchaseRefundExtPo extends PmPurchaseRefundPo {

	private static final long	serialVersionUID	= 1L;
	/**
	 * 创建日期起
	 */
	private Date				createTimeStart;
	/**
	 * 创建日期止
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
	 * 子公司权限修改为集合(yinyuxin)
	 */
	private List<String> branchCompanyIds;

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