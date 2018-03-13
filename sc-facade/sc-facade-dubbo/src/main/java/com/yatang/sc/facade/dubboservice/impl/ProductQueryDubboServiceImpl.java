package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ProductDetailDto;
import com.yatang.sc.facade.dto.ProductListForAppDto;
import com.yatang.sc.facade.dto.ProductListForAppQueryParamDto;
import com.yatang.sc.facade.dubboservice.ProductQueryDubboService;
import com.yatang.sc.facade.flow.ProductQueryFlowService;
import com.yatang.sc.facade.purchase.ProductMqCustomerProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/7 19:44
 * @版本: v1.0
 */
@Slf4j
@Service("productQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductQueryDubboServiceImpl implements ProductQueryDubboService {


    private final ProductQueryFlowService productQueryFlowService;

    private final ProductMqCustomerProcessor productMqCustomerProcessor;

    @Override
    public Response<ProductDetailDto> getProductDetail(String productCode, String branchCompanyId,String deliveryWarehouseCode) {
        return productQueryFlowService.getProductDetail(productCode, branchCompanyId,deliveryWarehouseCode);
    }

	@Override
	public Response<PageResult<ProductListForAppDto>> queryProductList(ProductListForAppQueryParamDto dto) {
		return productQueryFlowService.queryProductList(dto);
	}



	@Override
	public Response<Boolean> syncProduct(String productId) {
		Response<Boolean> response=new Response<>();
		try {
			Boolean aBoolean = productMqCustomerProcessor.syncProduct(productId);
			if (aBoolean){
				response.setResultObject(true);
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				return response;
			}else {
				response.setResultObject(false);
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				return response;
			}
		} catch (Exception e) {
			log.error("---------商品同步失败:"+e.toString()+"---------");
			response.setResultObject(false);
			response.setSuccess(false);
			response.setErrorMessage(e.toString());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}
}
