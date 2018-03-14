package com.yatang.sc.operation.vo.supplier;

import java.util.List;

import com.yatang.sc.operation.util.export.BaseAbstractExcelView;

/**
 * @描述:供应商申请入驻列表导出
 * @作者: kangdong
 * @创建时间: 2017/5/25 14:46
 * @版本: v1.0
 */
public class SupplierEnterExcelView extends BaseAbstractExcelView<SupplierEnterExcelModel> {

  public SupplierEnterExcelView(List<SupplierEnterExcelModel> elements) {

    super(SupplierEnterExcelModel.class, elements);
  }

}
