package com.yatang.sc.facade.dubboservice.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.CategoryGoodsOrderPo;
import com.yatang.sc.facade.dto.CategoryGoodsOrderDto;
import com.yatang.sc.facade.dubboservice.CategoryGoodsOrderWriteDubboService;
import com.yatang.sc.facade.service.CategoryGoodsOrderService;

@Service("categoryGoodsOrderWriteDubboService")
public class CategoryGoodsOrderWriteDubboServiceImpl implements CategoryGoodsOrderWriteDubboService {
	
	protected final Log		log	= LogFactory.getLog(this.getClass());
	
	@Autowired
	private CategoryGoodsOrderService categoryGoodsOrderService;
 
	@Override
	public Response<Integer> insertCategoryGoodsOrder(CategoryGoodsOrderDto param) {
		Response<Integer> response = new Response<Integer>();
		try {
			CategoryGoodsOrderPo categoryGoodsOrderPo=BeanConvertUtils.convert(param, CategoryGoodsOrderPo.class);
			int num=categoryGoodsOrderService.insertCategoryGoodsOrder(categoryGoodsOrderPo);
			if (num==1) {
				//新增成功
				response.setErrorMessage("新增成功");
				response.setResultObject(num);
			}else if (num==2) {
				//已经存在
				response.setCode(CommonsEnum.RESPONSE_402.toString());
				response.setErrorMessage("分类下该商品已经排序，新增失败");
			}
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Integer> updateCategoryGoodsOrderNum(CategoryGoodsOrderDto param) {
		Response<Integer> response = new Response<Integer>();
		try {
			CategoryGoodsOrderPo categoryGoodsOrderPo=BeanConvertUtils.convert(param, CategoryGoodsOrderPo.class);
			int sum=categoryGoodsOrderService.updateCategoryGoodsOrderNum(categoryGoodsOrderPo,param.getNewOrderNum());
			if (sum==1) {
				//修改成功
				response.setErrorMessage("修改成功");
				response.setResultObject(sum);
			}
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@Override
	public Response<Integer> deleteCategoryGoodsOrderNum(Integer pkId) {
		Response<Integer> response = new Response<Integer>();
		try {
			int num=categoryGoodsOrderService.deleteCategoryGoodsOrder(pkId);
			response.setErrorMessage("删除成功");
			response.setResultObject(num);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



}
