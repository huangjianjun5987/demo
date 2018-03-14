package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @描述: 供应商组织机构代码证信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierOrgCodeInfoVo implements Serializable {
	private static final long		serialVersionUID	= 3908197668900244437L;

	private String					spId;										//供应商主表id

	private Integer					id;											// 编号

	private String					orgCode;									// 组织机构代码

	private String					orgCodeCerPic;								// 组织机构代码证电子版

	private Integer					modifyId;									// 修改信息id,如有修改操作关联到修改记录

	private Integer					status;										// 状态(0待审批,1:审核失败)

	private String					failedReason;								// 失败原因
	@JsonIgnore
	private SupplierOrgCodeInfoVo modification;

}