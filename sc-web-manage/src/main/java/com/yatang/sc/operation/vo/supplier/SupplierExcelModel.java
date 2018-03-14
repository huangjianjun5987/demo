package com.yatang.sc.operation.vo.supplier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.BeanUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.dto.supplier.SupplierShowListDto;
import com.yatang.sc.facade.enums.SettlementAccountType;
import com.yatang.sc.facade.enums.Status;
import com.yatang.sc.operation.util.export.ExportField;
import com.yatang.sc.operation.util.export.ExportModel;

/**
 * @描述:获取需要导出的数据
 * @类名:SupplierExcelModel
 * @作者: kangdong
 * @创建时间: 2017/5/25 16:21
 * @版本: v1.0
 */
@ExportModel(fileName = "供应商列表")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierExcelModel {
	@ExportField(colName = "供应商注册号", index = 0)
	private String		spRegNo;				// 供应商注册号
	@ExportField(colName = "公司名称", index = 1)
	private String		companyName;			// 公司名称
	@ExportField(colName = "联系人", index = 2)
	private String		name;					// 联系人
	@ExportField(colName = "联系人手机", index = 3)
	private String		phone;					// 联系人手机
	@ExportField(colName = "联系人邮箱", index = 4)
	private String		email;					// 联系人邮箱
	@ExportField(colName = "返利（%）", index = 5)
	private BigDecimal	rebateRate;			// 返利
	@ExportField(colName = "供应商编号", index = 6)
	private String		spNo;					// 供应商编号
	@ExportField(colName = "结算账户类型", index = 7)
	private String		settlementAccountType;	// 结算账户类型
	@ExportField(colName = "结算账期", index = 8)
	private Integer		settlementPeriod;		// 结算账期
	@ExportField(colName = "入驻时间", index = 9)
	private Date		settledTime;			// 入驻时间
	@ExportField(colName = " 审核状态", index = 10)
	private String		status;				// 审核状态



	public static List<SupplierExcelModel> ofList(List<SupplierShowListDto> list) {
		return Lists.transform(list, new Function<SupplierShowListDto, SupplierExcelModel>() {
			@Override
			public SupplierExcelModel apply(SupplierShowListDto dto) {
				SupplierExcelModel excelModel = new SupplierExcelModel();
				BeanUtils.copyProperties(dto, excelModel);
				excelModel.setSettlementAccountType(SettlementAccountType.values()[dto.getSettlementAccountType()]
						.getDescription());
				excelModel.setStatus(Status.values()[dto.getStatus()].getDescription());
				return excelModel;
			}
		});
	}
}
