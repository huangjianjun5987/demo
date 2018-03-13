package com.yatang.sc.kidd.dto.orderCancel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:取消订单
 * @类名:KiddCancelAllOrdersDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 17:43
 * @版本: v1.0
 */

@Getter
@Setter
public class KiddCancelAllOrdersDto implements Serializable {
    private static final long serialVersionUID = 6700770724976305555L;
    private int type;               //系统重复处理订单方式 1.直接返回错误 2.重复订单跳过
    private List<KiddCancelOrdersDto> orders; //订单信息


    private String orderType;//单据类型(包含采购单和销售单)
}
