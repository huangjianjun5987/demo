package com.yatang.sc.xinyi.dto.saleOrder;

import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 销售订单关联单据类型
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 9:18
 * @版本: v1.0
 */
@Getter
@Setter
@XStreamAliasType("relatedOrder")
public class SaleOrderRelateOrderDto implements Serializable {
    private static final long serialVersionUID = 4692905072071208702L;

    private String orderType;//关联的订单类型，CG=采购单，DB=调拨单, CK=出库单，RK=入库单

    private String orderCode;//关联的订单编号
}
