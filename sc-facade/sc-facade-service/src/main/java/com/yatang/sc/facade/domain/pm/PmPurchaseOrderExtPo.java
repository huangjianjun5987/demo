package com.yatang.sc.facade.domain.pm;

import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
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
public class PmPurchaseOrderExtPo implements Serializable{

    private static final long serialVersionUID = -7842899035930007424L;
    private PmPurchaseOrderPo pmPurchaseOrder;//订单主题

   private List<PmPurchaseOrderItemPo>  pmPurchaseOrderItems;//订单列表

}
