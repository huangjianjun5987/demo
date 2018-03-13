package com.yatang.sc.xinyi.dto.saleOrder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 销售订单确认dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 13:15
 * @版本: v1.0
 */
@Getter
@Setter
public class SaleOrderConfirmRequestDto implements Serializable {
    private static final long serialVersionUID = 5294803458648464156L;


    private SaleOrderConfirmDeliveryInfoDto deliveryOrder;


    private List<SaleOrderConfirmOrderInfoDto> orderLines;//明细信息

}
