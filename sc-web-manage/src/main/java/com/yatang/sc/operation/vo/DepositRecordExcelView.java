package com.yatang.sc.operation.vo;

import com.yatang.sc.operation.util.export.BaseAbstractExcelView;

import java.util.List;

/**
 * @描述:供应商保证金记录Excel模型视图
 * @作者: yipeng
 * @创建时间: 2017年05月27日11:45:45
 * @版本: v1.0
 */
public class DepositRecordExcelView extends BaseAbstractExcelView<DepositRecordExcelModel> {

  public DepositRecordExcelView(List<DepositRecordExcelModel> elements) {
    super(DepositRecordExcelModel.class, elements);
  }

}
