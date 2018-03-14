package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.PromoCategoriesPo;

public interface PromoCategoriesDao {

    PromoCategoriesPo queryByPromoId(String id);

    int insert(PromoCategoriesPo po);

}
