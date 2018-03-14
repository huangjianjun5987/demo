package com.yatang.sc.purchase.redis.impl;

import com.github.pagehelper.StringUtil;
import com.yatang.sc.purchase.dto.ShoppingCart;
import com.yatang.sc.purchase.exception.VersionException;
import com.yatang.sc.purchase.redis.OrderHolderRedis;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import com.yatang.xc.oc.biz.redis.dubboservice.SerializeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by qiugang on 7/10/2017.
 */
@Service("orderHolderRedis")
public class OrderHolderRedisImpl implements OrderHolderRedis{

    Logger log = LoggerFactory.getLogger(OrderHolderRedisImpl.class);
    public static final String REDIS_SHOPING_CART_KEY_PREFIX = "redis_sc_shopping_cart:";

    @Autowired
    RedisAdapterServie<String, ShoppingCart> redisAdapterServie;


    @Override
    public void putShoppingCart(String userId, ShoppingCart shoppingCart) throws VersionException {

        log.info("save shopping cart: {}", userId);
        if(StringUtil.isEmpty(userId)){
            log.error("save shopping cart failed, user id is null!");
            throw new RuntimeException("服务器异常！");
        }
        ShoppingCart persistCart = getShoppingCart(userId);
        if(persistCart != null && persistCart.getVersion() > shoppingCart.getVersion()){
            throw new VersionException("old version is"+ persistCart.getVersion() +",shopping cart version is:" + shoppingCart.getVersion());
        }
        shoppingCart.getCurrentOrder().setLastModifiedTime( new Date() );
        shoppingCart.updateVersion();
        Boolean result = redisAdapterServie.set(RedisPlatform.gyl, SerializeType.FASTJSON,REDIS_SHOPING_CART_KEY_PREFIX + userId, shoppingCart);
        if(result != null && !result){
            throw new RuntimeException("服务器异常！");
        }

    }

    @Override
    public ShoppingCart getShoppingCart(String userId) {
        ShoppingCart shoppingCart =  redisAdapterServie.get(RedisPlatform.gyl, SerializeType.FASTJSON,REDIS_SHOPING_CART_KEY_PREFIX + userId, ShoppingCart.class);
        return shoppingCart;
    }




}
