package com.yatang.sc.operation.vo;

import com.yatang.sc.operation.common.BaseVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @描述: 接收供应商多条件查询信息
 * @作者: tankejia
 * @创建时间: 2017/6/12-14:58 .
 * @版本: 1.0 .
 */

public class MultiQuerySupplierVo extends BaseVo implements Serializable {

	private static final long	serialVersionUID	= -6514658917458468580L;

	/**
	 * 公司名称
	 * */
	private String				companyName;
	/**
	 * 供应商注册号
	 * */
	private String				spRegNo;
	/**
	 * 联系人
	 * */
	private String				name;
	/**
	 * 联系人手机
	 * */
	private String				phone;
	/**
	 * 供应商编号
	 * */
	private String				spNo;
	/**
	 * 入驻时间最小值(接收时间戳)
	 * */
	private Date				minSettledDate;
	/**
	 * 入驻时间最大值(接收时间戳)
	 * */
	private Date				maxSettledDate;
	/**
	 * 供应商状态
	 * */
	private Integer				status;
	/**
	 * 账期最小值
	 * */
	private Integer				minSettlementPeriod;
	/**
	 * 账期最大值
	 * */
	private Integer				maxSettlementPeriod;
	/**
	 * 结算账户类型
	 * */
	private Integer				settlementAccountType;
	/**
	 * 返利最小值
	 * */
	private BigDecimal			minRebateRate;
	/**
	 * 返利最大值
	 * */
	private BigDecimal			maxRebateRate;
	/**
	 * 查询类型（1：查询供应商管理列表，0：查询供应商修改资料申请列表）
	 * */
	private Integer				type;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getSpRegNo() {
		return spRegNo;
	}



	public void setSpRegNo(String spRegNo) {
		this.spRegNo = spRegNo;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getSpNo() {
		return spNo;
	}



	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}



	public Date getMinSettledDate() {
		return minSettledDate;
	}



	public void setMinSettledDate(String minSettledDate) {
		if (minSettledDate.length() == 10) {
			minSettledDate = minSettledDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(minSettledDate)));
			Date minDate = format.parse(format1);
			this.minSettledDate = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



	public Date getMaxSettledDate() {
		return maxSettledDate;
	}



	public void setMaxSettledDate(String maxSettledDate) {
		if (maxSettledDate.length() == 10) {
			maxSettledDate = maxSettledDate.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(maxSettledDate)));
			Date maxDate = format.parse(format1);
			this.maxSettledDate = maxDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public Integer getMinSettlementPeriod() {
		return minSettlementPeriod;
	}



	public void setMinSettlementPeriod(Integer minSettlementPeriod) {
		this.minSettlementPeriod = minSettlementPeriod;
	}



	public Integer getMaxSettlementPeriod() {
		return maxSettlementPeriod;
	}



	public void setMaxSettlementPeriod(Integer maxSettlementPeriod) {
		this.maxSettlementPeriod = maxSettlementPeriod;
	}



	public Integer getSettlementAccountType() {
		return settlementAccountType;
	}



	public void setSettlementAccountType(Integer settlementAccountType) {
		this.settlementAccountType = settlementAccountType;
	}



	public BigDecimal getMinRebateRate() {
		return minRebateRate;
	}



	public void setMinRebateRate(BigDecimal minRebateRate) {
		this.minRebateRate = minRebateRate;
	}



	public BigDecimal getMaxRebateRate() {
		return maxRebateRate;
	}



	public void setMaxRebateRate(BigDecimal maxRebateRate) {
		this.maxRebateRate = maxRebateRate;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}
}
