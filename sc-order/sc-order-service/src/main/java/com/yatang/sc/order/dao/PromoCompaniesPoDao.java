package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.PromoCompaniesPo;

import java.util.List;

public interface PromoCompaniesPoDao {

    List<PromoCompaniesPo> queryByPromoId(String id);

    int insert(PromoCompaniesPo po);

}
