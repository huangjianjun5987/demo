package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商基本信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierBasicInfoDto implements Serializable {

	private static final long		serialVersionUID	= 852857990139209011L;

	private Integer					id;										// 编号

	private String					companyName;								// 公司名称

	private String					spNo;										// 供应商编号

	/** 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商 */
	private Integer					grade;

	/** 入驻时间 */
	private Date					settledTime;

	private Integer					status;									// 状态0：待审批,1;审核失败,默认为0

	/**
	 * 修改后数据的id
	 * */
	private Integer					modifyId;

	@JsonIgnore
	private SupplierBasicInfoDto	modification;								// 供应商银行信息
}