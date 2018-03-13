package com.yatang.sc.facade.dto.pm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 商品订单包裹（基本信息和订单item）
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 17:51
 * @版本: v1.0
 */
@Setter
@Getter
@ToString
public class PmPurchaseOrderExtDto implements Serializable{


    private static final long serialVersionUID = -5115863032212684517L;

    private PmPurchaseOrderDto pmPurchaseOrder;//订单主表

   private List<PmPurchaseOrderItemDto>  pmPurchaseOrderItems;//订单列表

}
