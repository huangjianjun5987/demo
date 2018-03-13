package com.yatang.sc.facade.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeListResultDto;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeQueryDto;

/**
 * 
 * <class description>商品价格变化Flow
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年12月7日
 */
public interface ProdPriceChangeFlowService {
	/**
	 * 
	 * <method description>根据条件查询商品价格变化列表
	 * 
	 * @author zhoubaiyun
	 * @param prodPriceChangeQueryDto
	 * @return
	 */
	Response<PageResult<ProdPriceChangeListResultDto>> queryProdPriceChangeList(
			ProdPriceChangeQueryDto prodPriceChangeQueryDto);
}
