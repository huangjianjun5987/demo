package com.yatang.sc.operation.vo.supplier;

import com.yatang.sc.operation.util.export.BaseAbstractExcelView;

import java.util.List;

/**
 * @描述:供应商导出
 * @作者: kangdong
 * @创建时间: 2017/5/25 14:46
 * @版本: v1.0
 */
public class SupplierExcelView extends BaseAbstractExcelView<SupplierExcelModel> {

  public SupplierExcelView(List<SupplierExcelModel> elements) {

    super(SupplierExcelModel.class, elements);
  }

}
