package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.WishListParamPo;
import com.yatang.sc.order.domain.WishListPo;

import java.util.List;
import java.util.Map;

/**
 * 心愿单列表dao
 */
public interface WishListDao {
    int deleteByPrimaryKey(Long id);

    int insert(WishListPo record);

    int insertSelective(WishListPo record);

    WishListPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WishListPo record);

    int updateByPrimaryKey(WishListPo record);

    /**
     * 动态条件查询心愿单列表
     * @param wishListParamPo
     * @author yinyuxin
     * @return
     */
    List<WishListPo> queryWishListsByParam(WishListParamPo wishListParamPo);

    WishListPo findWishForBarcode(String barCode);

    WishListPo findWishListByCondition(Map<String, Object> condition);
}