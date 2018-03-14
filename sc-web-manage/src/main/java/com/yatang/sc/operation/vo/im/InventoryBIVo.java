package com.yatang.sc.operation.vo.im;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 库存实时查询vo类
 */
@Getter
@Setter
public class InventoryBIVo implements Serializable {

    private static final long serialVersionUID = -4268111418328447711L;
    //库存中的数据
    private String productId;//商品id

    private String productCode;//商品Code

    private String productName;//商品名称

    private String groups;//部类

    private String dept;//大类

    private String classs;//中类

    private String subclass;//小类

    private String brandName;//品牌名


    private String branchCompanyId;//分公司id

    private String branchCompanyName;//分公司名称


    private String warehouseCode;//仓库id

    private String warehouseName;//仓库名称


    private Double unitCost;//上商品成本 主供应商的采购价

    private Double avCost;//移动加权成本

    private Long stockOnHand;//现有库存

    private Long availableStock;// 可用库存  可用库存=现有库存-销售保留-调拨预留-退货预留

    private Long inTransitQty;//在途数量

    private Long orderReservedQty;//销售保留

    private Long tsfReservedQty;//调拨预留

//    private Long tsfExpectedQty;//预期到货

    private Long rtvQty;//退货预留

    private Long lastWeekSaleQty;//上周日均销量  取值当前查询系统日期至往前倒推7天间的商品地点销售出库单返回的确认收货数量合计除以7 todo

    private Double safeStockDay;//安全库存天数 安全库存天数=商品地点可用库存/上周日均销

    private Long saleUnArriveQty;//销售未达数量 取值销售订单状态为“已审核”销售订单物流状态为“库存不足”销售的销售订单汇总单据上商品地点的商品订单数量  todo

    // 国际码信息
    private String internationalCode;

}
