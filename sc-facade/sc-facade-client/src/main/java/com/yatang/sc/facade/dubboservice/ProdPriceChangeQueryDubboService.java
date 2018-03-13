package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeListResultDto;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeQueryDto;

/**
 * @描述: 商品价格(进价, 售价)变动 dubbo 查询service接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 11:22
 * @版本: v1.0
 */
public interface ProdPriceChangeQueryDubboService {
	/**
	 * 
	* <method description>根据条件分页查询商品价格变化记录列表
	* @author zhoubaiyun
	* @param prodPriceChangeQueryDto
	* @return
	 */
	Response<PageResult<ProdPriceChangeListResultDto>> queryProdPriceChangeList(ProdPriceChangeQueryDto prodPriceChangeQueryDto);
}
