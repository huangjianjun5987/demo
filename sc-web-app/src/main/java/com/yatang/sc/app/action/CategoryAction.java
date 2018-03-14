package com.yatang.sc.app.action;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.app.vo.prd.CategoryVo;
import com.yatang.xc.pi.solr.dto.CategoryDto;
import com.yatang.xc.pi.solr.dubboservice.ScMobileSearchDubboService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 商品分类的action
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年8月1日
 */
@Slf4j
@RestController
@RequestMapping(value = "/sc/category")
public class CategoryAction {

	@Autowired
	private ScMobileSearchDubboService scMobileSearchDubboService;

	/**
	 * 
	 * 查询一级分类列表
	 *
	 * @return
	 */
	@RequestMapping(value = "queryFirstCategorys", method = RequestMethod.GET)
	public Response<List<CategoryVo>> queryFirstCategorys() {
		Response<List<CategoryVo>> voResponse = new Response<List<CategoryVo>>();
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		Response<List<CategoryDto>> dtoResponse = scMobileSearchDubboService.queryFirstLevelCategories();
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), CategoryVo.class);
		if (CollectionUtils.isNotEmpty(list)) {
			// 默认把一级分类的子分类查出来
			CategoryVo categoryVo = list.get(0);
			List<CategoryDto> childCategories = scMobileSearchDubboService
					.querySecondLevelCategories(categoryVo.getDimValId()).getResultObject();
			List<CategoryVo> secondChildCategories = BeanConvertUtils.convertList(childCategories, CategoryVo.class);
			categoryVo.setChildCategories(secondChildCategories);
			if (CollectionUtils.isNotEmpty(secondChildCategories)) {
				CategoryVo secondCatgory = secondChildCategories.get(0);
				List<CategoryDto> thirdCategoies = scMobileSearchDubboService
						.queryThirdLevelCategories(secondCatgory.getDimValId()).getResultObject();
				if (CollectionUtils.isNotEmpty(thirdCategoies)) {
					List<CategoryVo> thirdChildCateogies = BeanConvertUtils.convertList(thirdCategoies,
							CategoryVo.class);
					secondCatgory.setChildCategories(thirdChildCateogies);
				}
			}
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(list);
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}



	/**
	 * 
	 * 查询二级分类列表
	 *
	 * @return
	 */
	@RequestMapping(value = "querySecondCategorysByFirstDimValId", method = RequestMethod.GET)
	public Response<List<CategoryVo>> querySecondCategorysByFirstDimValId(String firstDimValId) {
		Response<List<CategoryVo>> voResponse = new Response<List<CategoryVo>>();
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		Response<List<CategoryDto>> dtoResponse = scMobileSearchDubboService.querySecondLevelCategories(firstDimValId);
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), CategoryVo.class);
		if (CollectionUtils.isNotEmpty(list)) {
			CategoryVo categoryVo = list.get(0);
			List<CategoryDto> resultObject = scMobileSearchDubboService
					.queryThirdLevelCategories(categoryVo.getDimValId()).getResultObject();
			List<CategoryVo> thirdCategoies = BeanConvertUtils.convertList(resultObject, CategoryVo.class);
			categoryVo.setChildCategories(thirdCategoies);
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(list);
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}



	/**
	 * 
	 * 查询三、四级分类列表
	 *
	 * @return
	 */
	@RequestMapping(value = "queryThirdAndFourthCategorysBySecondDimValId", method = RequestMethod.GET)
	public Response<List<CategoryVo>> queryThirdAndFourthCategorysBySecondDimValId(String secondDimValId) {
		Response<List<CategoryVo>> voResponse = new Response<List<CategoryVo>>();
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		Response<List<CategoryDto>> dtoResponse = scMobileSearchDubboService.queryThirdLevelCategories(secondDimValId);
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), CategoryVo.class);
		// if (list != null && list.size() > 0) {
		// for (CategoryVo categoryVo : list) {
		// List<CategoryVo> fourthCategories = categoryVo.getChildCategories();
		// if (fourthCategories != null && fourthCategories.size() > 0) {
		// for (CategoryVo categoryVo2 : fourthCategories) {
		// categoryVo2.setIconImgUrl(fileServerUrl + "/" +
		// categoryVo2.getIconImgUrl());
		// }
		// }
		// }
		// }
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(list);
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}



	/**
	 * app端获取二级和三级的分类列表
	 * @return
	 */
	@RequestMapping(value = "querySecondAndThirdCategories", method = RequestMethod.GET)
	public Response<List<CategoryVo>> querySecondAndThirdCategories(){
		Response<List<CategoryVo>> voResponse = new Response<List<CategoryVo>>();
		List<CategoryVo> list = Lists.newArrayList();
		Response<List<CategoryDto>> dtoResponse = scMobileSearchDubboService.querySecondLevelCategories();
		log.info("-------------categoryAction-->querySecondAndThirdCategories()主数据返回的数据:"+ JSONObject.toJSONString(dtoResponse)+"-------------");
		if (dtoResponse.getResultObject()!=null){
			list=BeanConvertUtils.convertList(dtoResponse.getResultObject(),CategoryVo.class);
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(list);
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}

}
