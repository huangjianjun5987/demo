package com.yatang.sc.operation.vo.settlement;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yatang.sc.operation.util.export.ExportField;
import com.yatang.sc.operation.util.export.ExportModel;
import com.yatang.sc.settlement.dto.SupplierSettledDto;
import com.yatang.sc.settlement.enums.AdrType;
import com.yatang.sc.settlement.enums.PayType;
import com.yatang.sc.settlement.enums.SettlementPeriod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @描述: 供应商结算Excel模型
 * @作者: tankejia
 * @创建时间: 2017/8/31-15:22 .
 * @版本: 1.0 .
 */
@ExportModel(fileName = "供应商结算列表")
@Getter
@Setter
public class SupplierSettlementExcelModel implements Serializable {

    private static final long serialVersionUID = -4939362687562129417L;

    @ExportField(colName = "收货单号", index = 0)
    private String				purchaseReceiptNo;

    @ExportField(colName = "ASN号", index = 1)
    private String				asn;

    @ExportField(colName = "采购单号", index = 2)
    private String				purchaseOrderNo;

    @ExportField(colName = "供应商编号", index = 3)
    private String				spNo;

    @ExportField(colName = "供应商名称", index = 4)
    private String				spName;

    @ExportField(colName = "子公司编码", index = 5)
    private String				branchCompanyCode;

    @ExportField(colName = "子公司名称", index = 6)
    private String				branchCompanyName;

    @ExportField(colName = "地点类型", index = 7)
    private String				adrType;

    @ExportField(colName = "地点编码", index = 8)
    private String				adrTypeCode;

    @ExportField(colName = "地点名称", index = 9)
    private String				adrTypeName;

    @ExportField(colName = "账期", index = 10)
    private String				accountPeriod;

    @ExportField(colName = "付款方式", index = 11)
    private String				payType;

    @ExportField(colName = "部类", index = 12)
    private String				groups;

    @ExportField(colName = "部类描述", index = 13)
    private String				groupsDesc;

    @ExportField(colName = "大类", index = 14)
    private String				dept;

    @ExportField(colName = "大类描述", index = 15)
    private String				deptDesc;

    @ExportField(colName = "商品编号", index = 16)
    private String				productCode;

    @ExportField(colName = "商品名称", index = 17)
    private String				productName;

    @ExportField(colName = "税率", index = 18)
    private BigDecimal          inputTaxRate;

    @ExportField(colName = "收货日期", index = 19)
    private Date                receivedTime;

    @ExportField(colName = "已收货数量", index = 20)
    private Integer				receivedNumber;

    @ExportField(colName = "收货金额（未税)", index = 21)
    private BigDecimal			receivedMoneyWithoutTax;

    @ExportField(colName = "收货金额（含税）", index = 22)
    private BigDecimal			receivedMoneyWithTax;

    public static List<SupplierSettlementExcelModel> ofList(List<SupplierSettledDto> list) {
        return Lists.transform(list, new Function<SupplierSettledDto, SupplierSettlementExcelModel>() {
            @Override
            public SupplierSettlementExcelModel apply(SupplierSettledDto dto) {
                SupplierSettlementExcelModel excelModel = new SupplierSettlementExcelModel();
                BeanUtils.copyProperties(dto, excelModel);
                if (dto.getAdrType() != null) {
                    excelModel.setAdrType(AdrType.values()[dto.getAdrType()].getDescription());
                }

                if (dto.getSettlementPeriod() != null) {
                    excelModel.setAccountPeriod(SettlementPeriod.values()[dto.getSettlementPeriod()].getDescription());
                }

                if (dto.getPayType() != null) {
                    excelModel.setPayType(PayType.values()[dto.getPayType()].getDescription());
                }
                return excelModel;
            }
        });
    }
}
