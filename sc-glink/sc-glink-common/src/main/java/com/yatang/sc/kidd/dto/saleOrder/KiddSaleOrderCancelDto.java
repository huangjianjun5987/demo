package com.yatang.sc.kidd.dto.saleOrder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 订单取消KiddDto 单个处理
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 15:50
 * @版本: v1.0
 */
@Getter
@Setter
public class KiddSaleOrderCancelDto implements Serializable {
    private static final long serialVersionUID = 1103467432275044838L;

    private String warehouseCode;//逻辑仓库编码 必填

    private String ownerCode;// 货主编码 子公司ID

    private String orderCode;//单据编号



    private String orderId;//JYCK= 一般交易出库单

    private String orderType;//单据类型

//    private String orgCode;     //机构编号ERP或者是WMS； glink todo 是否是心怡的orgCode


}
