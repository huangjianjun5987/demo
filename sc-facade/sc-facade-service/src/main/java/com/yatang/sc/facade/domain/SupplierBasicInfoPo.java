package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商基本信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 */

@Getter
@Setter
public class SupplierBasicInfoPo implements Serializable {

	private static final long	serialVersionUID	= 852857990139209011L;

	private Integer				id;

	/**
	 * 公司名称
	 */
	private String				companyName;

	/**
	 * 供应商编号
	 */
	private String				spNo;

	/** 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商 */
	private Integer				grade;

	/** 入驻时间 */
	private Date				settledTime;

	private Integer				status;

	/**
	 * 修改后数据的id
	 */
	private Integer				modifyId;

	private SupplierBasicInfoPo	modification;

}