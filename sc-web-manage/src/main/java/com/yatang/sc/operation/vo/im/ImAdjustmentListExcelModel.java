package com.yatang.sc.operation.vo.im;

import java.math.BigDecimal;
import java.util.List;

import com.yatang.sc.inventory.enums.Status;
import org.springframework.beans.BeanUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yatang.sc.operation.util.export.ExportField;
import com.yatang.sc.operation.util.export.ExportModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商管理列表Excel模型
 * @作者: tankejia
 * @创建时间: 2017/5/27-14:45 .
 * @版本: 1.0 .
 */

@ExportModel(fileName = "库存调整列表")
@Getter
@Setter
public class ImAdjustmentListExcelModel implements java.io.Serializable {


    private static final long serialVersionUID = -4449164316478349243L;
    @ExportField(colName = "单据编号", index = 0)
    private Long	id;
    @ExportField(colName = "调整地点", index = 1)
    private String	warehouseName;
    @ExportField(colName = "调整数量合计", index = 2)
    private Long	totalQuantity;
    @ExportField(colName = "调整成本合计", index = 3)
    private BigDecimal	totalAdjustmentCost;
    @ExportField(colName = "外部单据号", index = 4)
    private String	externalBillNo;
    @ExportField(colName = "状态", index = 5)
    private String status;

    public static List<ImAdjustmentListExcelModel> ofList(List<ImAdjustmentQueryListVo> listVos) {
        return Lists.transform(listVos, new Function<ImAdjustmentQueryListVo, ImAdjustmentListExcelModel>() {
            @Override
            public ImAdjustmentListExcelModel apply(ImAdjustmentQueryListVo imAdjustmentQueryListVo) {
                ImAdjustmentListExcelModel excelModel = new ImAdjustmentListExcelModel();
                BeanUtils.copyProperties(imAdjustmentQueryListVo, excelModel);
                excelModel.setStatus(Status.values()[imAdjustmentQueryListVo.getStatus()].getDescription());
                return excelModel;
            }
        });
    }

}
