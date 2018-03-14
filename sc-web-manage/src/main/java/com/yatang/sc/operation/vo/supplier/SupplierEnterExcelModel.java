package com.yatang.sc.operation.vo.supplier;

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
@ExportModel(fileName = "供应商入驻申请列表列表")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEnterExcelModel {
	@ExportField(colName = "供应商注册号", index = 0)
	private String	spRegNo;			// 供应商注册号
	@ExportField(colName = "公司名称", index = 1)
	private String	companyName;		// 公司名称
	@ExportField(colName = "联系人", index = 2)
	private String	name;				// 联系人
	@ExportField(colName = "联系人手机", index = 3)
	private String	phone;				// 联系人手机
	@ExportField(colName = "联系人邮箱", index = 4)
	private String	email;				// 联系人邮箱
	@ExportField(colName = "供应商编号", index = 5)
	private String	spNo;				// 供应商编号
	@ExportField(colName = "入驻申请时间", index = 6)
	private Date	settledRequestTime; // 入驻申请时间
	@ExportField(colName = " 审核状态", index = 7)
	private String	status;			// 审核状态



	public static List<SupplierEnterExcelModel> ofListEnter(List<SupplierShowListDto> list) {
		return Lists.transform(list, new Function<SupplierShowListDto, SupplierEnterExcelModel>() {
			@Override
			public SupplierEnterExcelModel apply(SupplierShowListDto dto) {
				SupplierEnterExcelModel excelModel = new SupplierEnterExcelModel();
				BeanUtils.copyProperties(dto, excelModel);
				excelModel.setStatus(Status.values()[dto.getStatus()].getDescription());
				return excelModel;
			}
		});
	}
}
