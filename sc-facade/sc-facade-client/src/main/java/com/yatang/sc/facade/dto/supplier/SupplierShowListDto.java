package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:供应商列表显示信息
 * @类名:QueryProviderEnterlistVo
 * @作者: tankejia
 * @创建时间: 2017/5/26 09:50
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierShowListDto implements Serializable {

	private static final long	serialVersionUID	= 2097363562599297708L;

	/**
	 * 供应商注册号
	 */
	private String				spRegNo;

	/**
	 * 公司名称
	 */
	private String				companyName;

	/**
	 * 联系人
	 */
	private String				name;

	/**
	 * 联系人手机
	 */
	private String				phone;

	/**
	 * 联系人邮箱
	 */
	private String				email;

	/**
	 * 返利
	 */
	private BigDecimal			rebateRate;

	/**
	 * 供应商编号
	 */
	private String				spNo;

	/**
	 * 结算账户类型
	 */
	private Integer				settlementAccountType;

	/**
	 * 结算账期
	 */
	private Integer				settlementPeriod;

	/**
	 * 入驻时间
	 */
	private Date				settledTime;

	/**
	 * 入驻申请时间
	 * */
	private Date				settledRequestTime;

	/**
	 * 保证金余额
	 */
	private BigDecimal			guaranteeMoney;

	/**
	 * 状态
	 */
	private Integer				status;

	/**
	 * 供应商合作信息ID
	 */
	private Integer				supplierCooperationId;

	/**
	 * 供应商主表信息ID
	 */
	private String				id;

}
