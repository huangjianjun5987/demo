package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.ProdSellInfoLogPo;

/**
 * @description: 商品销售关系修改日志
 * @author: yinyuxin
 * @date: 2017/11/3 14:12
 * @version: v1.0
 */
public interface ProdSellInfoLogService {

	/**
	 * 插入日志
	 * @param prodSellInfoLogPo
	 * @return
	 */
	Boolean insertLog(ProdSellInfoLogPo prodSellInfoLogPo);
}
