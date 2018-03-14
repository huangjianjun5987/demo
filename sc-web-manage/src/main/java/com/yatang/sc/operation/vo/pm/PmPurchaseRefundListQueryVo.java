package com.yatang.sc.operation.vo.pm;

import java.util.Date;

import org.hibernate.validator.constraints.Range;

import com.yatang.sc.operation.common.BaseVo;

/**
 * 
 * <class description>查询退货单列表参数VO，需要分页
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年10月19日
 */
public class PmPurchaseRefundListQueryVo extends BaseVo {
	private static final long	serialVersionUID	= 1L;
	/**
	 * 退货单号
	 */
	private String				purchaseRefundNo;
	/**
	 * 状态:0:制单;1:已提交;2:已审核;3:已拒绝;4:待退货;5:已退货;6:已取消;7:取消失败;8:异常
	 */
	@Range(min = 0, max = 8)
	private Integer				status;

	/**
	 * 供应商ID
	 */
	private String				spId;

	/**
	 * 供应商地点ID
	 */
	private String				spAdrId;
	/**
	 * 退货地点类型:0:仓库;1:门店
	 */
	@Range(min = 0, max = 1)
	private Integer				adrType;

	/**
	 * 退货地点编码
	 */
	private String				refundAdrCode;
	/**
	 * 创建日期起
	 */
	private Date				createTimeStart;
	/**
	 * 创建日期止
	 */
	private Date				createTimeEnd;
	/**
	 * 排序字段:退货单号：0,创建日期：1,状态：2
	 */
	@Range(min = 0, max = 2)
	private Integer				orderItem			= 0;
	/**
	 * 排序方式:0升序,1降序
	 */
	@Range(min = 0, max = 1)
	private Integer				orderType			= 1;



	public String getPurchaseRefundNo() {
		return purchaseRefundNo;
	}



	public void setPurchaseRefundNo(String purchaseRefundNo) {
		this.purchaseRefundNo = purchaseRefundNo;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



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



	public String getRefundAdrCode() {
		return refundAdrCode;
	}



	public void setRefundAdrCode(String refundAdrCode) {
		this.refundAdrCode = refundAdrCode;
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

}
