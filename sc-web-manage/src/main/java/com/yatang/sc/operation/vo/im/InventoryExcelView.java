package com.yatang.sc.operation.vo.im;

import com.yatang.sc.operation.util.export.BaseAbstractExcelView;

import java.util.List;

/**
 * @描述: 库存类excel视图
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/31 下午12:12
 * @版本: v1.0
 */
public class InventoryExcelView extends BaseAbstractExcelView<InventoryBIExcelModel> {
    public InventoryExcelView(List<InventoryBIExcelModel> elements) {
        super(InventoryBIExcelModel.class, elements);
    }
}
