package com.yatang.sc.facade.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:供应商查询参数接收类
 * @类名:SupplierEnterQueryParamPo
 * @作者: yipeng
 * @创建时间: 2017年07月17日16:13:32
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierInfoQueryListPo implements java.io.Serializable {
	private static final long	serialVersionUID	= 3579092622021932383L;
	private String				id;
	private String				providerNo;
	private String				providerName;
	/**
	 * 供应商营业执照号
	 */
	private String				registLicenceNumber;
	/**
	 * 供应商类型:0:全部;1:供应商;2:供应商地点
	 */
	private Integer				providerType;
	/**
	 * 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商 供应商地点级别:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
	 */
	private Integer				grade;
	/**
	 * 供应商地点级别:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
	 */
	private Integer				adrGrade;
	/**
	 * 供应商状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:修改中
	 */
	private Integer				spStatus;
	/**
	 * 供应商状态/供应商地点状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:修改中
	 */
	private Integer				status;
	/**
	 * 审核失败原因
	 */
	private String				failedReason;
	/**
	 * 供应商入驻日期
	 */
	private Date				settledDate;
	/**
	 * 审核类型:1:入驻审核;2:修改审核
	 */
	private Integer				auditType;
	private Integer				basicInfoId;
	private Integer				licenseInfoId;

	private String   spNo;
	private String   companyName;
	/**
	 * 收货子公司
	 */
	private String orgId;

}
