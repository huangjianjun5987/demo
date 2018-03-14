package com.yatang.sc.operation.vo.im;

import java.util.List;

import com.yatang.sc.operation.util.export.BaseAbstractExcelView;
import com.yatang.sc.operation.vo.supplier.SupplierListExcelModel;

/**
 * @描述: 供应商管理列表Excel模型视图
 * @作者: tankejia
 * @创建时间: 2017/5/27-15:25 .
 * @版本: 1.0 .
 */
public class ImAdjustmentListExcelView extends BaseAbstractExcelView<ImAdjustmentListExcelModel> {
    public ImAdjustmentListExcelView(List<ImAdjustmentListExcelModel> elements) {
        super(ImAdjustmentListExcelModel.class, elements);
    }

}
