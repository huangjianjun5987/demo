package com.yatang.sc.wish.dubboService;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.WishDetailDto;
import com.yatang.sc.dto.WishListDto;
import com.yatang.sc.dto.WishListParamDto;

/**
 * @description: 心愿单 后端write接口
 * @author: yinyuxin
 * @date: 2018/1/3 11:40
 * @version: v1.0
 */
public interface WishBackQueryDubboService {

	/**
	 * 动态查询心愿单列表
	 * @param wishListParamDto
	 * @author yinyuxin
	 * @return
	 */
	Response<PageResult<WishListDto>> queryWishListsByParam(WishListParamDto wishListParamDto);

	/**
	 * 根据心愿单id 和门店id查询心愿单明细清单
	 * @param wishListParamDto
	 * @author yinyuxin
	 * @return
	 */
	Response<PageResult<WishDetailDto>> queryWishDetailsByWishListIdAndStoreId(WishListParamDto wishListParamDto);
}
