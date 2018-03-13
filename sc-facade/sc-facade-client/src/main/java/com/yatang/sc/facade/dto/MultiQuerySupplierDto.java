package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.yatang.sc.facade.common.BaseDto;

/**
 * @描述: 接收供应商多条件查询信息
 * @作者: tankejia
 * @创建时间: 2017/6/12-14:58 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class MultiQuerySupplierDto extends BaseDto implements Serializable {

	private static final long	serialVersionUID	= 8888698209128464408L;

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
	 * 入驻时间最小值
	 * */
	private Date				minSettledDate;
	/**
	 * 入驻时间最大值
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

}
