package com.yatang.sc.xinyi.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;
import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 12:32
 * @版本: v1.0
 */

@Getter
@Setter
@XStreamAliasType("deliveryOrder")
public class SaleOrderConfirmDeliveryInfoDto implements Serializable {


    private String deliveryOrderCode;//出库单号


    private String deliveryOrderId;//仓储系统出库单号
    private String warehouseCode;//仓库编码

    private String orderType;//出库单类型

    private String status;//出库单状态 出库单状态, string (50)  (NEW-未开始处理,  ACCEPT-仓库接单 , PARTDELIVERED-部分发货完成,  DELIVERED-发货完成,  EXCEPTION-异常,  CANCELED-取消,  CLOSED-关闭,  REJECT-拒单,  CANCELEDFAIL-取消失败) ,  (只传英文编码)

    private String logisticsCode;//物流公司编码

    private String logisticsName;//物流公司名称

    private String expressCode;//运单号

    private String outBizCode;//出库单类型

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date orderConfirmTime;// 订单完成时间

//    private String deliveryVoucherNo;   //交货单号 （传给财务中心用）

}
