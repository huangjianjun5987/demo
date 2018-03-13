package com.yatang.sc.facade.dubboservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeListResultDto;
import com.yatang.sc.facade.dto.prod.ProdPriceChangeQueryDto;
import com.yatang.sc.facade.dubboservice.ProdPriceChangeQueryDubboService;
import com.yatang.sc.facade.flow.ProdPriceChangeFlowService;
import com.yatang.sc.facade.service.ProdPriceChangeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述: 商品价格(进价, 售价)变动 dubbo 查询service接口实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 11:22
 * @版本: v1.0
 */
@Slf4j
@Service("prodPriceChangeQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPriceChangeQueryDubboServiceImpl implements ProdPriceChangeQueryDubboService {


    private final ProdPriceChangeService priceChangeService;
    
	private final ProdPriceChangeFlowService prodPriceChangeFlowService;

	@Override
	public Response<PageResult<ProdPriceChangeListResultDto>> queryProdPriceChangeList(
			ProdPriceChangeQueryDto prodPriceChangeQueryDto) {
		return prodPriceChangeFlowService.queryProdPriceChangeList(prodPriceChangeQueryDto);
	}
}
