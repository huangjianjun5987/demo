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
public class PmPurchaseRefundAuditListQueryVo extends BaseVo {
	private static final long	serialVersionUID	= 1L;
	/**
	 * 退货单号
	 */
	private String				purchaseRefundNo;
	/**
	 * 流程状态:0:进行中;1:已结束
	 */
	@Range(min = 0, max = 1)
	private Integer				auditStatus;

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
	 * 退货地点名称
	 */
	private String				refundAdrName;

	/**
	 * 流程开始时间起=创建日期
	 */
	private Date				createTimeStart;
	/**
	 * 流程开始时间止=创建日期
	 */
	private Date				createTimeEnd;
	/**
	 * 流程结束时间=审核日期
	 */
	private Date				stopTimeStart;
	/**
	 * 流程结束时间=审核日期
	 */
	private Date				stopTimeEnd;



	public String getPurchaseRefundNo() {
		return purchaseRefundNo;
	}



	public void setPurchaseRefundNo(String purchaseRefundNo) {
		this.purchaseRefundNo = purchaseRefundNo;
	}



	public Integer getAuditStatus() {
		return auditStatus;
	}



	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
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



	public String getRefundAdrName() {
		return refundAdrName;
	}



	public void setRefundAdrName(String refundAdrName) {
		this.refundAdrName = refundAdrName;
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



	public Date getStopTimeStart() {
		return stopTimeStart;
	}



	public void setStopTimeStart(Date stopTimeStart) {
		this.stopTimeStart = stopTimeStart;
	}



	public Date getStopTimeEnd() {
		return stopTimeEnd;
	}



	public void setStopTimeEnd(Date stopTimeEnd) {
		this.stopTimeEnd = stopTimeEnd;
	}

}
