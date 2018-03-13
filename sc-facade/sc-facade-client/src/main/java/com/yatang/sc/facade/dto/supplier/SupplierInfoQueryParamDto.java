package com.yatang.sc.facade.dto.supplier;

import lombok.Getter;
import lombok.Setter;

import com.yatang.sc.facade.common.BaseDto;

import java.util.Date;

/**
 * @描述:供应商查询参数接收类
 * @类名:SupplierEnterQueryParamPo
 * @作者: yipeng
 * @创建时间: 2017年07月17日16:13:32
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierInfoQueryParamDto extends BaseDto implements java.io.Serializable {
	private static final long	serialVersionUID	= 3579092622021932383L;

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
	 * 供应商状态:1:待审核;2:已审核;3:已拒绝;
	 */
	private Integer				status;
	/**
	 * 供应商入驻日期
	 */
	private Date				settledDate;
	/**
	 * 供应商地点所属子公司
	 */
	private Integer				orgId;

}
