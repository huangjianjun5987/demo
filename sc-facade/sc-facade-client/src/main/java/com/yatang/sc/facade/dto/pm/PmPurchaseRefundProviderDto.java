package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 供应商采购退货详情单dto
 * @author: leixin
 * @date: 2017/10/17 15:24
 * @version: v1.0
 */
public class PmPurchaseRefundProviderDto implements Serializable{

	private static final long serialVersionUID = -7807803805778925439L;

	/**
	 *
	 */
	private Long							id;

	/**
	 * 退货单号
	 */
	private String							purchaseRefundNo;

	/**
	 * 供应商ID
	 */
	private String							spId;

	/**
	 * 供应商编码
	 */
	private String							spNo;

	/**
	 * 供应商名称
	 */
	private String							spName;
	/**
	 * 退货地点编码
	 */
	private String							refundAdrCode;
	/**
	 * 退货地点
	 */
	private String							refundAdrName;
	/**
	 * 退货地址
	 */
	private String							adrName;

	/**
	 * 货币种类代码,CNY
	 */
	private String							currencyCode;

	/**
	 * 合计实际退货数量
	 */
	private Integer							totalRealRefundAmount;

	/**
	 * 合计实际退货金额(含税)
	 */
	private BigDecimal						totalRealRefundMoney;

	/**
	 * 创建人ID
	 */
	private String							createUserId;
	/**
	 * 创建人名称
	 */
	private String							createUser;

	/**
	 * 创建时间
	 */
	private Date							createTime;

	/**
	 * 备注
	 */
	private String							remark;
	/**
	 * 退货单商品
	 */
	private List<PmPurchaseRefundProviderItemDto> returnList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public String getRefundAdrName() {
		return refundAdrName;
	}

	public void setRefundAdrName(String refundAdrName) {
		this.refundAdrName = refundAdrName;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpNo() {
		return spNo;
	}

	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public Integer getTotalRealRefundAmount() {
		return totalRealRefundAmount;
	}

	public void setTotalRealRefundAmount(Integer totalRealRefundAmount) {
		this.totalRealRefundAmount = totalRealRefundAmount;
	}

	public BigDecimal getTotalRealRefundMoney() {
		return totalRealRefundMoney;
	}

	public void setTotalRealRefundMoney(BigDecimal totalRealRefundMoney) {
		this.totalRealRefundMoney = totalRealRefundMoney;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<PmPurchaseRefundProviderItemDto> getReturnList() {
		return returnList;
	}

	public void setReturnList(List<PmPurchaseRefundProviderItemDto> returnList) {
		this.returnList = returnList;
	}

	public String getAdrName() {
		return adrName;
	}

	public void setAdrName(String adrName) {
		this.adrName = adrName;
	}
}
