package com.yatang.sc.app.vo;

import java.io.Serializable;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartVo implements Serializable{

    private static final long serialVersionUID = -1756988493096624692L;

    private List<CommerceItemAppVo> items;    //购物车的商品

    /**
     * 订单中商品未执行任何折扣的净额
     */
    private double rawSubtotal;

    /**
     * 订单商品总额(去除优惠后的值)
     */
    private double amount;

    /**
     * 订单折扣额
     */
    private double discountAmount;

    /**
     * 手工调价总额
     */
    private double manualAdjustmentTotal;
    /**
     * 购物车中所有商品的数量
     */
    private long cartCount ;

    /**
     * 购物车选中商品的数量
     */
    private long selectCount ;

    private boolean selectAll;
    /**
     * 商品种类数
     */
    private long itemListCount;


}
