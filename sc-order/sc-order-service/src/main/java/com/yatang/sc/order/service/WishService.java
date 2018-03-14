package com.yatang.sc.order.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.domain.WishDetailPo;
import com.yatang.sc.order.domain.WishListParamPo;
import com.yatang.sc.order.domain.WishListPo;

import com.yatang.sc.order.domain.WishDetailPo;
import com.yatang.sc.order.domain.WishListPo;

import java.util.List;
import java.util.Map;

/**
 * @description: 心愿单业务service
 * @author: yinyuxin
 * @date: 2018/1/2 17:19
 * @version: v1.0
 */
public interface WishService {

	/**
	 * 动态条件查询心愿单列表
	 * @param wishListParamPo
	 * @author yinyuxin
	 * @return
	 */
	PageInfo<WishListPo> queryWishListsByParam(WishListParamPo wishListParamPo);

	/**
	 * 根据wish_list主键id查询记录（不查询wish_detail）
	 * @param wishListId
	 * @author yinyuxin
	 * @return
	 */
	WishListPo queryWishListById(Long wishListId);

	/**
	 * 根据心愿单id和门店id查询心愿单明细清单
	 * @param wishListParamPo
	 * @author yinyuxin
 	 * @return
	 */
	PageInfo<WishDetailPo> queryWishDetailsByWishListIdAndStoreId(WishListParamPo wishListParamPo);

	/**
	 * 修改心愿单的状态
	 * @param wishListId wish_list主键id
	 * @param status 状态，init:待处理， complete:已完成， close：关闭
	 * @author yinyuxin
	 * @return
	 */
	void modifyWishListStatus(Long wishListId,String status);

	/**
	 *  根据心愿单id 查询心愿单清单门店id的集合
	 * @param wishListId
	 * @author yinyuxin
	 * @return
	 */
	List<String> queryWishDetailIdsByWishListId(Long wishListId);

    WishListPo findWishForBarcode(String barCode);

    void updateWish(WishListPo wishListPo);

    void saveWishListPo(WishListPo wishListPo);

    void saveWishDetail(WishDetailPo wishDetailPo);

    List<WishDetailPo> queryWishDetailByCondition(Map<String, Object> condition);

    long queryWishDetailCountByCondition(Map<String, Object> condition);

	WishListPo findWishListByCondition(Map<String, Object> cd);
}
