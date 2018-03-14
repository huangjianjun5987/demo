package com.yatang.sc.operation.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.CategoryGoodsOrderDto;
import com.yatang.sc.facade.dto.ProductInfoQueryDto;
import com.yatang.sc.facade.dubboservice.CategoryGoodsOrderQueryDubboService;
import com.yatang.sc.facade.dubboservice.CategoryGoodsOrderWriteDubboService;
import com.yatang.sc.operation.vo.CategoryGoodsOrderQueryVo;
import com.yatang.sc.operation.vo.CategoryGoodsOrderVo;
import com.yatang.sc.operation.vo.prod.ProductQueryVo;

import lombok.extern.log4j.Log4j;

/**
 * @描述: 列表页商品排序 API
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日20:52:15
 * @版本: 1.0 .
 */
@Log4j
@RestController
@RequestMapping(value = "/sc/categoryGoodsOrder")
public class CategoryGoodsOrderAction extends BaseAction {

	@Autowired
	private CategoryGoodsOrderWriteDubboService	categoryGoodsOrderWriteDubboService;
	@Autowired
	private CategoryGoodsOrderQueryDubboService	categoryGoodsOrderQueryDubboService;



	/**
	 * @Description: 根据三层分类信息查询已排序的商品数是否超过48个，超过则禁止新增
	 * @author yinyuxin
	 * @date 2017年6月8日20:32:07
	 * @param param
	 *            CategoryGoodsOrderVo
	 */
	@RequestMapping(value = "checkGoodOrderCountOver", method = RequestMethod.GET)
	public Response<Boolean> checkGoodOrderCountOver(CategoryGoodsOrderVo param) {
		CategoryGoodsOrderDto categoryGoodsOrderDto = BeanConvertUtils.convert(param, CategoryGoodsOrderDto.class);
		Response<Boolean> dubboResponse = categoryGoodsOrderQueryDubboService
				.checkGoodsOrderCountOver(categoryGoodsOrderDto);
		return dubboResponse;
	}



	/**
	 * @Description: 根据商品编号获取商品名称（需验证商品与分类是否匹配）
	 * @author yinyuxin
	 * @date 2017年6月9日09:23:43
	 * @param param
	 *            ProductQueryVo
	 */
	@RequestMapping(value = "queryGoodsName", method = RequestMethod.GET)
	public Response<String> queryGoodsName(ProductQueryVo param) {
		ProductInfoQueryDto productInfoQueryDto = BeanConvertUtils.convert(param, ProductInfoQueryDto.class);
		Response<String> response = categoryGoodsOrderQueryDubboService.queryGoodsName(productInfoQueryDto);
		return response;
	}



	/**
	 * @Description: 新增分类商品排序（需验证排序号是否重复）
	 * @author yinyuxin
	 * @date 2017年6月9日09:23:43
	 * @param param
	 *            CategoryGoodsOrderVo
	 */
	@RequestMapping(value = "insertCategoryGoodsOrder", method = RequestMethod.POST)
	public Response<Integer> insertCategoryGoodsOrder(@RequestBody CategoryGoodsOrderVo param) {
		CategoryGoodsOrderDto categoryGoodsOrderDto = BeanConvertUtils.convert(param, CategoryGoodsOrderDto.class);
		Response<Integer> dubboResponse = categoryGoodsOrderWriteDubboService
				.insertCategoryGoodsOrder(categoryGoodsOrderDto);
		return dubboResponse;
	}



	/**
	 * @Description: 修改分类商品排序（需验证排序号是否重复）
	 * @author yinyuxin
	 * @date 2017年6月9日09:23:43
	 * @param param
	 *            CategoryGoodsOrderVo
	 */
	@RequestMapping(value = "updateCategoryGoodsOrderNum", method = RequestMethod.POST)
	public Response<Integer> updateCategoryGoodsOrderNum(@RequestBody CategoryGoodsOrderVo param) {
		CategoryGoodsOrderDto categoryGoodsOrderDto = BeanConvertUtils.convert(param, CategoryGoodsOrderDto.class);
		Response<Integer> dubboResponse = categoryGoodsOrderWriteDubboService
				.updateCategoryGoodsOrderNum(categoryGoodsOrderDto);
		return dubboResponse;
	}



	/**
	 * @Description: 根据条件分页查询
	 * @author yinyuxin
	 * @date 2017年6月9日09:23:43
	 * @param param
	 *            CategoryGoodsOrderVo
	 */
	@RequestMapping(value = "queryCategoryGoodsOrders", method = RequestMethod.GET)
	public Response<PageResult<CategoryGoodsOrderVo>> queryCategoryGoodsOrders(CategoryGoodsOrderQueryVo param) {
		Response<PageResult<CategoryGoodsOrderVo>> response = new Response<PageResult<CategoryGoodsOrderVo>>();
		PageResult<CategoryGoodsOrderVo> pageResult = new PageResult<CategoryGoodsOrderVo>();
		CategoryGoodsOrderDto categoryGoodsOrderDto = BeanConvertUtils.convert(param, CategoryGoodsOrderDto.class);
		Response<PageResult<CategoryGoodsOrderDto>> dubboResponse = categoryGoodsOrderQueryDubboService
				.queryCategoryGoodsOrders(categoryGoodsOrderDto);

		pageResult = BeanConvertUtils.convert(dubboResponse.getResultObject(), PageResult.class);
		if (pageResult.getData() == null) {
			pageResult.setData(new ArrayList<CategoryGoodsOrderVo>());
		}
		response.setResultObject(pageResult);
		response.setCode(dubboResponse.getCode());
		response.setErrorMessage(dubboResponse.getErrorMessage());
		response.setResultObject(pageResult);
		response.setSuccess(dubboResponse.isSuccess());
		return response;
	}



	/**
	 * @Description: 根据主键id删除分类下的商品排序
	 * @author yinyuxin
	 * @date 2017年6月9日09:23:43
	 * @param pkId
	 *            主键id
	 */
	@RequestMapping(value = "deleteCategoryGoodsOrderNum", method = RequestMethod.GET)
	public Response<Integer> deleteCategoryGoodsOrderNum(int pkId) {
		Response<Integer> response = new Response<Integer>();
		response = categoryGoodsOrderWriteDubboService.deleteCategoryGoodsOrderNum(pkId);
		return response;
	}
}
