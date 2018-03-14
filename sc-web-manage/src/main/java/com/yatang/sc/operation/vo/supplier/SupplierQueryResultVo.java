package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.util.Date;

import com.yatang.sc.web.view.XlsHealder;
import com.yatang.sc.web.view.XlsSheet;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description> 供应商入驻列表查询结果VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月18日
 */
@Getter
@Setter
@XlsSheet("供应商列表")
public class SupplierQueryResultVo implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@XlsHealder(value = "供应商编码", width = 20)
	private String				providerNo;

	@XlsHealder(value = "供应商名称", width = 20)
	private String				providerName;

	// 供应商营业执照号
	@XlsHealder(value = "供应商营业执照号", width = 20)
	private String				registLicenceNumber;

	// 供应商类型:0:全部;1:供应商;2:供应商地点
	@XlsHealder(value = "供应商类型", width = 20)
	private String				providerType;

	// 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商 供应商地点等级:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他
	@XlsHealder(value = "供应商等级", width = 20)
	private String				grade;

	// 供应商入驻日期
	@XlsHealder(value = "供应商入驻日期", width = 20)
	private Date				settledDate;

	// 供应商状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:修改中
	@XlsHealder(value = "供应商状态", width = 20)
	private String				status;
}
