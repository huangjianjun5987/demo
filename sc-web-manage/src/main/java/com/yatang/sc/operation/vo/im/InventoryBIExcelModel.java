package com.yatang.sc.operation.vo.im;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.im.InventoryBIDto;
import com.yatang.sc.operation.util.export.ExportField;
import com.yatang.sc.operation.util.export.ExportModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 库存 excel定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/31 下午12:11
 * @版本: v1.0
 */

@Getter
@Setter
@ExportModel(fileName = "库存报表")
public class InventoryBIExcelModel implements Serializable {
    private static final long serialVersionUID = 6986190942334249652L;


    //库存中的数据
//    @ExportField(colName = "商品id", index = 0)
//    private String productId;//商品id

    @ExportField(colName = "商品编码", index = 0)
    private String productCode;//商品Code

    @ExportField(colName = "商品名称", index = 1)
    private String productName;//商品名称


    @ExportField(colName = "国际码", index = 2)
    private String internationalCode;

    @ExportField(colName = "部类", index = 3)
    private String groups;//部类

    @ExportField(colName = "大类", index = 4)
    private String dept;//大类

    @ExportField(colName = "中类", index =5)
    private String classs;//中类

    @ExportField(colName = "小类", index = 6)
    private String subclass;//小类

    @ExportField(colName = "品牌名", index = 7)
    private String brandName;//品牌名

    @ExportField(colName = "分公司id", index = 8)
    private String branchCompanyId;//分公司id

    @ExportField(colName = "分公司名称", index = 9)
    private String branchCompanyName;//分公司名称

    @ExportField(colName = "仓库编码", index = 10)
    private String warehouseCode;//仓库id

    @ExportField(colName = "仓库名称", index = 11)
    private String warehouseName;//仓库名称

    @ExportField(colName = "采购价", index = 12)
    private Double unitCost;//上商品成本 主供应商的采购价

    @ExportField(colName = "移动加权成本", index = 13)
    private Double avCost;//移动加权成本

    @ExportField(colName = "现有库存", index = 14)
    private Long stockOnHand;//现有库存

    @ExportField(colName = "可用库存", index = 15)
    private Long availableStock;// 可用库存  可用库存=现有库存-销售保留-调拨预留-退货预留

    @ExportField(colName = "在途数量", index = 16)
    private Long inTransitQty;//在途数量

    @ExportField(colName = "销售保留", index = 17)
    private Long orderReservedQty;//销售保留

    @ExportField(colName = "调拨预留", index = 18)
    private Long tsfReservedQty;//调拨预留

    //    private Long tsfExpectedQty;//预期到货
    @ExportField(colName = "退货预留", index = 19)
    private Long rtvQty;//退货预留

    @ExportField(colName = "上周日均销量", index = 20)
    private Long lastWeekSaleQty;//上周日均销量

    @ExportField(colName = "安全库存天数", index = 21)
    private Double safeStockDay;//安全库存天数 安全库存天数=商品地点可用库存/上周日均销

//    @ExportField(colName = "销售未达数量", index = 21)
//    private Long saleUnArriveQty;//销售未达数量

    public static List<InventoryBIExcelModel> of(List<InventoryBIDto> list) {
        return Lists.transform(list, new Function<InventoryBIDto, InventoryBIExcelModel>() {
            @Override
            public InventoryBIExcelModel apply(InventoryBIDto dto) {
                InventoryBIExcelModel excelModel = new InventoryBIExcelModel();
                BeanUtils.copyProperties(dto, excelModel);
                return excelModel;
            }
        });
    }


}
