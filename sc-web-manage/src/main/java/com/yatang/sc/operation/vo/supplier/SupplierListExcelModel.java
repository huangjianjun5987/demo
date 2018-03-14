package com.yatang.sc.operation.vo.supplier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
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
 * @描述: 供应商管理列表Excel模型
 * @作者: tankejia
 * @创建时间: 2017/5/27-14:45 .
 * @版本: 1.0 .
 */

@ExportModel(fileName = "供应商管理列表")
@Getter
@Setter
public class SupplierListExcelModel implements java.io.Serializable {

	private static final long	serialVersionUID	= 2700945840248576689L;
	@ExportField(colName = "供应商注册号", index = 0)
    private String	spRegNo;
    @ExportField(colName = "公司名称", index = 1)
    private String	companyName;
    @ExportField(colName = "联系人", index = 2)
    private String	name;
    @ExportField(colName = "联系人手机", index = 3)
    private String	phone;
    @ExportField(colName = "联系人邮箱", index = 4)
    private String	email;
    @ExportField(colName = "返利（%）", index = 5)
    private BigDecimal rebateRate;
    @ExportField(colName = "供应商编号", index = 6)
    private String	spNo;
    @ExportField(colName = "结算账户类型", index = 7)
    private String	settlementAccountType;
    @ExportField(colName = "结算账期", index = 8)
    private Integer	settlementPeriod;
    @ExportField(colName = "入驻时间", index = 9)
    private Date settledTime;
    @ExportField(colName = "保证金余额", index = 10)
    private BigDecimal guaranteeMoney;
    @ExportField(colName = " 供应商状态", index = 11)
    private String	status;

    public static List<SupplierListExcelModel> ofList(List<SupplierShowListDto> list) {
        return Lists.transform(list, new Function<SupplierShowListDto, SupplierListExcelModel>() {
            @Override
            public SupplierListExcelModel apply(SupplierShowListDto dto) {
                SupplierListExcelModel excelModel = new SupplierListExcelModel();
                BeanUtils.copyProperties(dto, excelModel);
                if (dto.getSettlementAccountType() != null) {
                    excelModel.setSettlementAccountType(SettlementAccountType.values()[dto.getSettlementAccountType()].getDescription());
                } else {
                    excelModel.setSettlementAccountType("无");
                }
                excelModel.setStatus(Status.values()[dto.getStatus()].getDescription());
                return excelModel;
            }
        });
    }

}
