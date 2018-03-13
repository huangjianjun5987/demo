package com.yatang.sc.juban.dto.saleOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmBatchInfoDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/26 18:09
 * @版本: v1.0
 */

@Getter
@Setter
@XStreamAliasType("orderLine")
public class JubanSaleOrderConfirmOrderInfoDto implements Serializable {
    private static final long serialVersionUID = 6306592743746382477L;

    private String outBizCode;//外部业务编码

    private String orderLineNo;//单据行号

    private String itemCode;//商品仓储系统编码

    private String itemName;//商品名称

    private String inventoryType;//库存类型 ZP

    private Integer actualQty;// 实发商品数量

    private String batchCode;// 批次编号

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date productDate;//生产日期
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd"})
    private Date expireDate;//过期日期

    private String produceCode;//生产批号


    private List<JubanSaleOrderConfirmBatchInfoDto> batchs;


}
