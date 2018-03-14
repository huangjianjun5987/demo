package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.WishDetailPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 心愿单详情dao
 */
public interface WishDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(WishDetailPo record);

    int insertSelective(WishDetailPo record);

    WishDetailPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WishDetailPo record);

    int updateByPrimaryKey(WishDetailPo record);

    /**
     * 根据心愿单id 和门店id查询心愿单详情
     * @author yinyuxin
     * @return
     */
    List<WishDetailPo> queryWishDetailByWishListIdAndStoreId(WishDetailPo wishDetailPo);

    /**
     *  根据心愿单id 查询心愿单清单门店id的集合
     * @param wishListId
     * @author yinyuxin
     * @return
     */
    List<String> queryWishDetailIdsByWishListId(@Param("wishListId") Long wishListId);

    List<WishDetailPo> queryWishDetailByCondition(Map<String, Object> condition);


    long queryWishDetailCountByCondition(Map<String, Object> condition);
}