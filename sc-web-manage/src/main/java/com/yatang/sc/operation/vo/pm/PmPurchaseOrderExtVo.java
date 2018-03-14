package com.yatang.sc.operation.vo.pm;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
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
public class PmPurchaseOrderExtVo implements Serializable {
    private static final long serialVersionUID = -4419833770289065973L;

    @Valid
    private PmPurchaseOrderVo pmPurchaseOrder;//订单主题

    @Valid
    @NotEmpty(message = "{msg.notEmpty.message}",groups = {GroupOne.class, DefaultGroup.class})
    private List<PmPurchaseOrderItemVo> pmPurchaseOrderItems;//订单列表


}
