package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierInfoQueryVo implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private String				providerNo;

	private String				providerName;

	// 供应商营业执照号
	private String				registLicenceNumber;

	// 供应商类型:0:全部;1:供应商;2:供应商地点
	private Integer				providerType;

	// 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商 供应商地点级别:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
	private Integer				grade;

	// 供应商状态:1:待审核;2:已审核;3:已拒绝;
	private Integer				status;

	// 供应商入驻日期
	private Date					settledDate;
}
