package com.yatang.sc.juban.dto.saleOrder;

import java.io.Serializable;
import java.util.List;

import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmDeliveryInfoDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmOrderInfoDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 销售订单确认dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 13:15
 * @版本: v1.0
 */
@Getter
@Setter
public class JubanSaleOrderConfirmRequestDto implements Serializable {
    private static final long serialVersionUID = 5294803458648464156L;


    private JubanSaleOrderConfirmDeliveryInfoDto deliveryOrder;


    private List<JubanSaleOrderConfirmOrderInfoDto> orderLines;//明细信息

}
