package com.yatang.sc.operation.vo.supplier;

import com.yatang.sc.operation.util.export.BaseAbstractExcelView;

import java.util.List;

/**
 * @描述: 供应商管理列表Excel模型视图
 * @作者: tankejia
 * @创建时间: 2017/5/27-15:25 .
 * @版本: 1.0 .
 */
public class SupplierListExcelView extends BaseAbstractExcelView<SupplierListExcelModel> {
    public SupplierListExcelView(List<SupplierListExcelModel> elements) {
        super(SupplierListExcelModel.class, elements);
    }

}
