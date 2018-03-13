package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.purchase.KiddEntryOrderDto;

/**
 * @描述:采购订单中间层接口定义
 * @类名:KiddPurchaseService
 * @作者: lvheping
 * @创建时间: 2017/9/25 9:55
 * @版本: v1.0
 */

public interface KiddPurchaseService {
	/**
	 * 新增采购订单（入库单）
	 * 
	 * @param kiddEntryOrderDto
	 * @return
	 */
	Response<String> add(KiddEntryOrderDto kiddEntryOrderDto);

}
