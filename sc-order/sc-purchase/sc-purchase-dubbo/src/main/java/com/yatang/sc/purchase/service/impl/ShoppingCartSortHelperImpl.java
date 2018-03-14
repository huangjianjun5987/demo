package com.yatang.sc.purchase.service.impl;

import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.ShoppingCart;
import com.yatang.sc.purchase.service.ShoppingCartSortHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("shoppingCartSortHelper")
public class ShoppingCartSortHelperImpl implements ShoppingCartSortHelper {

    //存放库存不足的商品
    private List<CommerceItemDto> stockNotEnoughList = new ArrayList<>();
    //存放库存充足的商品
    private List<CommerceItemDto> stockEnoughList = new ArrayList<>();

    @Override
    public ShoppingCart sortCart(ShoppingCart shoppingCart) {

        if (shoppingCart == null || shoppingCart.getCurrentOrder() ==null) {
            return shoppingCart;
        }
        List<CommerceItemDto> items = shoppingCart.getCurrentOrder().getItems();
        for (CommerceItemDto commerceItmDto : items) {
            if (!commerceItmDto.isStockEnough()) {
                stockNotEnoughList.add(commerceItmDto);
            } else {
                stockEnoughList.add(commerceItmDto);
            }
        }
        //将库存不足的商品进行排序
        stockNotEnoughList = sortCartUtil(stockNotEnoughList);
        //将库存充足的商品进行排序
        stockEnoughList = sortCartUtil(stockEnoughList);
        //将库存充足的商品集合加在库存不足的商品集合之后
        stockNotEnoughList.addAll(stockEnoughList);
        shoppingCart.getCurrentOrder().setItems(stockNotEnoughList);
        return shoppingCart;
    }

    //冒泡排序(根据创建时间倒序排列)
    public List<CommerceItemDto> sortCartUtil(List<CommerceItemDto> items) {

        CommerceItemDto temp = null;
        for (int index = items.size() - 1; index > 0; --index) {
            for (int j = 0; j < index; ++j) {
                if (items.get(j + 1).getCreationTime().after(items.get(j).getCreationTime())) {
                    temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
        return items;
    }

}
