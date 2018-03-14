package com.yatang.sc.operation.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yatang.sc.operation.vo.CategoryAppVo;
import com.yatang.xc.pi.solr.dubboservice.ScMobileSearchDubboService;
import org.apache.http.HttpResponse;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.operation.util.ImageDomainJoinUrlUtil;
import com.yatang.sc.operation.vo.CategoryFromSolrVo;
import com.yatang.sc.operation.vo.CategoryParamVo;
import com.yatang.sc.operation.vo.CategoryVo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.xc.mbd.biz.prod.dubboservice.CategoryDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductSCDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.CategoryDto;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.UpdateCategoryDisplay;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.UpdateCategorySort;
import com.yatang.xc.pi.solr.dubboservice.ScBackendSearchDubboService;

import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletResponse;

/**
 * @描述: 商品分类 HTTP API
 * @作者: yinyuxin
 * @创建时间: 2017年5月27日10:33:22
 * @版本: 1.0 .
 */
@Log4j
@RestController
@RequestMapping(value = "/sc/category")
public class CategoryAction {

	@Autowired
	private CategoryDubboService categoryDubboService;

	@Autowired
	private ProductSCDubboService productSCDubboService;

	@Autowired
	private ScBackendSearchDubboService scBackendSearchDubboService;

	@Autowired
	private ImageDomainJoinUrlUtil imageDomainJoinUrlUtil;

	@Autowired
	private ScMobileSearchDubboService scMobileSearchDubboService;



	/**
	 * 更改分类的排序号
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "updateSortNum", method = RequestMethod.POST)
	public Response<Integer> updateSortNum(@RequestBody CategoryParamVo param) {
		Response<Integer> response = new Response<Integer>();
		List<UpdateCategorySort> paramList = new ArrayList<UpdateCategorySort>();
		// CategoryDto categoryDto =
		// categoryDubboService.queryById(param.getId()).getResultObject();
		// if (categoryDto == null) {
		// response.setCode(CommonsEnum.RESPONSE_500.getCode().toString());
		// response.setErrorMessage("获取分类信息失败");
		// response.setSuccess(false);
		// return response;
		// }
		//
		// // 查询新、旧排序号之间的分类列表
		// QueryCategorySortDto dto = new QueryCategorySortDto();
		// dto.setParentCategoryId(categoryDto.getParentCategoryId());
		// // 如果新的排序号没有被占，则直接插入
		// dto.setRangeStart(param.getNewSortOrder());
		// dto.setRangeEnd(param.getNewSortOrder());
		// Response<List<CategoryDto>> dtoresponse =
		// productSCDubboService.queryBySortOrderRange(dto);
		// if (dtoresponse.getResultObject() == null ||
		// dtoresponse.getResultObject().size() == 0) {
		//
		// } else {
		// dto.setParentCategoryId(categoryDto.getParentCategoryId());
		// dto.setRangeStart((categoryDto.getSortOrder() -
		// param.getNewSortOrder() < 0) ? categoryDto.getSortOrder()
		// : param.getNewSortOrder());
		// dto.setRangeEnd((categoryDto.getSortOrder() - param.getNewSortOrder()
		// > 0) ? categoryDto.getSortOrder()
		// : param.getNewSortOrder());
		// Response<List<CategoryDto>> response2 =
		// productSCDubboService.queryBySortOrderRange(dto);
		// if (response2.getResultObject() != null &&
		// response2.getResultObject().size() > 0) {
		// for (CategoryDto categoryDto2 : response2.getResultObject()) {
		// if (!categoryDto2.getId().equals(param.getId())) {
		// UpdateCategorySort temp = BeanConvertUtils.convert(categoryDto2,
		// UpdateCategorySort.class);
		// if (categoryDto.getSortOrder() < param.getNewSortOrder()) {
		// temp.setSortOrder(temp.getSortOrder() - 1);
		// paramList.add(temp);
		// } else if (categoryDto.getSortOrder() > param.getNewSortOrder()) {
		// temp.setSortOrder(temp.getSortOrder() + 1);
		// paramList.add(temp);
		// }
		// }
		// }
		// }
		// }
		UpdateCategorySort updateCategorySort = BeanConvertUtils.convert(param, UpdateCategorySort.class);
		updateCategorySort.setSortOrder(param.getNewSortOrder());
		paramList.add(updateCategorySort);
		response = productSCDubboService.updateSortOrder(paramList);
		return response;
	}



	/**
	 * 更改分类的显示状态
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "updateShowStatus", method = RequestMethod.POST)
	public Response<Integer> updateShowStatus(@RequestBody CategoryParamVo param) {
		Response<Integer> response = new Response<Integer>();
		// paramList用于最后批量更新显示状态
		List<UpdateCategoryDisplay> paramList = new ArrayList<UpdateCategoryDisplay>();
		// 获取被点击的分类需要显示的状态，用于联动操作子分类
		UpdateCategoryDisplay updateCategoryDisplay = BeanConvertUtils.convert(param, UpdateCategoryDisplay.class);
		int displayStatus = param.getDisplayStatus();
		String id = param.getId();
		try {
			CategoryDto categoryDto = categoryDubboService.queryById(id).getResultObject();
			List<CategoryDto> childCategories = categoryDto.getChildCategories();
			if (childCategories != null && childCategories.size() > 0) {
				for (CategoryDto childCategory : childCategories) {
					UpdateCategoryDisplay updateCategoryDisplay1 = new UpdateCategoryDisplay();
					updateCategoryDisplay1.setId(childCategory.getId());
					updateCategoryDisplay1.setDisplayStatus(displayStatus);
					paramList.add(updateCategoryDisplay1);
					List<CategoryDto> grandSonCategories = childCategory.getChildCategories();
					if (grandSonCategories != null && grandSonCategories.size() > 0) {
						for (CategoryDto grandSonCategory : grandSonCategories) {
							UpdateCategoryDisplay updateCategoryDisplay2 = new UpdateCategoryDisplay();
							updateCategoryDisplay2.setId(grandSonCategory.getId());
							updateCategoryDisplay2.setDisplayStatus(displayStatus);
							paramList.add(updateCategoryDisplay2);
							List<CategoryDto> fourthCategories = grandSonCategory.getChildCategories();
							if (fourthCategories != null && fourthCategories.size() > 0) {
								for (CategoryDto fourthCategory : fourthCategories) {
									UpdateCategoryDisplay updateCategoryDisplay3 = new UpdateCategoryDisplay();
									updateCategoryDisplay3.setId(fourthCategory.getId());
									updateCategoryDisplay3.setDisplayStatus(displayStatus);
									paramList.add(updateCategoryDisplay3);
								}
							}
						}
					}
				}
			}
			paramList.add(updateCategoryDisplay);
			response = productSCDubboService.updateDisplayStatus(paramList);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode().toString());
			response.setErrorMessage("修改该分类或其子分类显示状态出错");
			response.setSuccess(false);
		}
		return response;
	}



	/**
	 * 查询所有分类对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryAllCategories", method = RequestMethod.GET)
	public Response<List<CategoryFromSolrVo>> queryAllCategories() {
		// 封装总记录数
		Response<List<CategoryFromSolrVo>> voResponse = new Response<List<CategoryFromSolrVo>>();
		Response<List<com.yatang.xc.pi.solr.dto.CategoryDto>> dtoResponse = scBackendSearchDubboService
				.queryAllCategories();
		List<CategoryFromSolrVo> list ;
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), CategoryFromSolrVo.class);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		voResponse.setResultObject(list);
		return voResponse;
	}



	/**
	 * 查询所有显示状态分类对象(带图片)
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryAllShowCategories", method = RequestMethod.GET)
	public Response<List<CategoryFromSolrVo>> queryAllShowCategories() {

		// 封装总记录数
		Response<List<CategoryFromSolrVo>> voResponse = new Response<List<CategoryFromSolrVo>>();
		Response<List<com.yatang.xc.pi.solr.dto.CategoryDto>> dtoResponse = scBackendSearchDubboService
				.queryAllShowCategories();
		List<CategoryFromSolrVo> list ;
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), CategoryFromSolrVo.class);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		voResponse.setResultObject(list);
		return voResponse;
	}



	/**
	 * 根据父id查询全部的子分类列表(带图片)
	 * 
	 * @param parentId
	 *            父分类ID
	 * @return
	 */
	@RequestMapping(value = "queryAllCategoriesWithIconByParentId", method = RequestMethod.GET)
	public Response<List<CategoryVo>> queryAllCategoriesWithIconByParentId(String parentId) {
		Response<List<CategoryVo>> voResponse = new Response<List<CategoryVo>>();
		// Response<List<CategoryDto>> dtoResponse =
		// categoryDubboService.queryChildCategories(parentId);
		Response<List<CategoryDto>> dtoResponse = categoryDubboService.queryChildCategories(parentId);
		List<CategoryVo> list;
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), CategoryVo.class);
		if (list != null && list.size() > 0) {
			for (CategoryVo categoryVo : list) {
				if (categoryVo.getChildCategories() == null || categoryVo.getChildCategories().size() <= 0) {
					categoryVo.setIconImg(categoryVo.getIconImg() == null ? null
							: imageDomainJoinUrlUtil.getImageUrl(categoryVo.getIconImg()));
				}
			}
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		voResponse.setResultObject(list);
		return voResponse;
	}



	/**
	 * 根据父id查询显示状态的子分类列表(带图片)
	 * 
	 * @param parentId
	 *            父分类ID
	 * @return
	 */
	@RequestMapping(value = "queryDisplayCategoriesWithIconByParentId", method = RequestMethod.GET)
	public Response<List<CategoryVo>> queryDisplayCategoriesWithIconByParentId(String parentId) {
		// 封装总记录数
		log.info("-------------queryDisplayCategoriesWithIconByParentId():" + parentId + "---------------");
		Response<List<CategoryVo>> voResponse = new Response<List<CategoryVo>>();
		Response<List<CategoryDto>> dtoResponse = categoryDubboService.queryDisplayChildCategories(parentId);
		List<CategoryVo> list ;
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), CategoryVo.class);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());
		voResponse.setResultObject(list);
		return voResponse;
	}



	/**
	 * 新增或修改商品分类图标
	 * 
	 * @categoryIconVO
	 * @return
	 */
	@RequestMapping(value = "addOrUpdateCategoryIcon", method = RequestMethod.POST)
	public Response<Integer> addOrUpdateCategoryIcon(@RequestBody CategoryParamVo categoryParamVo) {
		Response<Integer> response = new Response<Integer>();
		Response<Integer> dtoResponse = productSCDubboService.updateIconImg(categoryParamVo.getId(),
				categoryParamVo.getIconImg());
		response.setCode(dtoResponse.getCode());
		response.setErrorMessage(dtoResponse.getErrorMessage());
		response.setResultObject(dtoResponse.getResultObject());
		response.setSuccess(("200".equals(dtoResponse.getCode()) ? true : false));
		return response;
	}



	/**
	 * 
	 * 根据分类名字或者编码查询指定等级的分类列表(主要用于分类值清单)
	 *
	 * @param level
	 *            等级
	 * @param param
	 *            名字或者编码
	 * @return
	 */
	@RequestMapping(value = "queryCategories", method = RequestMethod.GET)
	public Response<List<CategoryVo>> queryCategories(
			@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String level, String param) {
		Response<List<CategoryVo>> voResponse = new Response<List<CategoryVo>>();
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		Response<List<CategoryDto>> dtoResponse = productSCDubboService.queryCurrentCategories(level, param);
		List<CategoryDto> dtoList = dtoResponse.getResultObject();
		if (dtoList != null && dtoList.size() > 0) {
			list = BeanConvertUtils.convertList(dtoList, CategoryVo.class);
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(list);
		voResponse.setSuccess(("200".equals(dtoResponse.getCode()) ? true : false));
		return voResponse;
	}


	/**
	 * app端获取二级分类列表(app)
	 * @return
	 */
	@RequestMapping(value = "querySecondCategoriesOfApp", method = RequestMethod.GET)
	public Response<List<CategoryAppVo>> querySecondCategoriesOfApp(){
		Response<List<CategoryAppVo>> voResponse = new Response<List<CategoryAppVo>>();
		List<CategoryAppVo> list = Lists.newArrayList();
		Response<List<com.yatang.xc.pi.solr.dto.CategoryDto>> dtoResponse = scMobileSearchDubboService.querySecondLevelCategories();
		if (dtoResponse.getResultObject()!=null){
			list=BeanConvertUtils.convertList(dtoResponse.getResultObject(),CategoryAppVo.class);
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(list);
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}
}
