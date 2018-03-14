package com.yatang.sc.wish.dubboService;

import com.busi.common.resp.Response;

/**
 * @description: 心愿单 后端write接口
 * @author: yinyuxin
 * @date: 2018/1/3 11:40
 * @version: v1.0
 */
public interface WishBackWriteDubboService {

	/**
	 * 完成或关闭心愿单（会触发小超B端服务，通知用户处理结果）
	 * @param wishListId 心愿单主键
	 * @param status 状态，init:待处理， complete:已完成， close：关闭
	 * @author yinyuxin
	 * @return
	 */
	Response<Boolean> comleteOrCloseWishList(Long wishListId,String status);
}
