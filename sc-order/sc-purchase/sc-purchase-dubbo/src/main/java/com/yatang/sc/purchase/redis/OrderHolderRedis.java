package com.yatang.sc.purchase.redis;

import com.yatang.sc.purchase.dto.ShoppingCart;
import com.yatang.sc.purchase.exception.VersionException;

/**
 * Created by qiugang on 7/8/2017.
 */
public interface OrderHolderRedis {

    public void putShoppingCart(String userId, ShoppingCart shoppingCart) throws VersionException;

    public ShoppingCart getShoppingCart(String userId);


}
