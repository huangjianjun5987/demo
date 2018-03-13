package com.yatang.sc.kidd.dto.saleOrder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: Kidd销售订单信息DTO
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 11:37
 * @版本: v1.0
 */
@Getter
@Setter
public class KiddSaleOrderDto implements Serializable{


    private static final long serialVersionUID = 4788490232479951365L;

    private KiddSaleOrderDeliveryInfoDto deliveryOrder; //物流信息

    private List<KiddSaleOrderCargoDto> orderLines;//订单详细信息
}
