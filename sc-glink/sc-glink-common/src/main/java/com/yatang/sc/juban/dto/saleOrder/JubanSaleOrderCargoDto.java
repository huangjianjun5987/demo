package com.yatang.sc.juban.dto.saleOrder;

import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 销售单商品信息Dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:07
 * @版本: v1.0
 */
@Getter
@Setter
@XStreamAliasType("orderLine")
public class JubanSaleOrderCargoDto implements Serializable {


    private static final long serialVersionUID = -9021023080845113626L;

    private String orderLineNo;////商品行号
    private String ownerCode;//商品名称 货主编码 子公司ID

    private String itemCode;//商品编码

    private String itemName;//商品名称

    private Integer planQty;//应发商品数量

    private double retailPrice;//零售价

    private double itemPrice;//零售价,出库单

    private double actualPrice;//实际成交价
}
