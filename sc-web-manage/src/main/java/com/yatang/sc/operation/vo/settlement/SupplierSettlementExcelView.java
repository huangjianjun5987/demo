package com.yatang.sc.operation.vo.settlement;

import com.yatang.sc.operation.util.export.BaseAbstractExcelView;

import java.util.List;

/**
 * @描述: 供应商结算Excel模型视图
 * @作者: tankejia
 * @创建时间: 2017/8/31-15:22 .
 * @版本: 1.0 .
 */
public class SupplierSettlementExcelView extends BaseAbstractExcelView<SupplierSettlementExcelModel> {
    public SupplierSettlementExcelView(List<SupplierSettlementExcelModel> elements) {
        super(SupplierSettlementExcelModel.class, elements);
    }
}
