package com.yatang.sc.juban.dto.saleOrder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 销售订单创建请求DTO
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:19
 * @版本: v1.0
 */
@Getter
@Setter
public class JubanSaleOrderRequestDto implements Serializable {
    private static final long serialVersionUID = 3318644134770632628L;

    private JubanSaleOrderDeliveryInfoDto deliveryOrder; //物流信息

    private List<JubanSaleOrderCargoDto> orderLines;//订单详细信息
}
