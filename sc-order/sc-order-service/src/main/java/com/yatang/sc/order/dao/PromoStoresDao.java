package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.PromoStoresPo;

import java.util.List;

public interface PromoStoresDao {

    List<PromoStoresPo> queryByPromoId(String id);

    int deleteByPrimaryKey(String id);

    int insert(PromoStoresPo po);

    int updateByPrimaryKey(PromoStoresPo po);

}
