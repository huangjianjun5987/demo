package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.ItemPriceAdj;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
public interface ItemPriceAdjDao {
    void insert(ItemPriceAdj itemPriceAdj);

    int update(ItemPriceAdj itemPriceAdj);

    List<ItemPriceAdj> selectByItemPriceId(Long id);
}
