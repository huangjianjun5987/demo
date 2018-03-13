package com.yatang.sc.facade.dubboservice.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.CategoryGoodsOrderPo;
import com.yatang.sc.facade.dto.CategoryGoodsOrderDto;
import com.yatang.sc.facade.dto.ProductInfoQueryDto;
import com.yatang.sc.facade.dubboservice.CategoryGoodsOrderQueryDubboService;
import com.yatang.sc.facade.service.CategoryGoodsOrderService;
import com.yatang.xc.mbd.biz.prod.dubboservice.CategoryDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductSCDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductQueryDto;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.QueryProductSCResultDto;

@Service("categoryGoodsOrderQueryDubboService")
public class CategoryGoodsOrderQueryDubboServiceImpl implements CategoryGoodsOrderQueryDubboService {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private CategoryGoodsOrderService	categoryGoodsOrderService;
	@Autowired
	private CategoryDubboService		categoryDubboService;
	@Autowired
	private ProductSCDubboService		productSCDubboService;
	@Autowired
	private ProductDubboService			productDubboService;



	@Override
	public Response<PageResult<CategoryGoodsOrderDto>> queryCategoryGoodsOrders(CategoryGoodsOrderDto param) {
		Long beforeTotalDate = System.currentTimeMillis();
		PageResult<CategoryGoodsOrderDto> pageResult = new PageResult<CategoryGoodsOrderDto>();
		Response<PageResult<CategoryGoodsOrderDto>> response = new Response<PageResult<CategoryGoodsOrderDto>>();
		try {
			CategoryGoodsOrderPo categoryGoodsOrderPo = BeanConvertUtils.convert(param, CategoryGoodsOrderPo.class);
			Integer pageNum = param.getPageNum();
			Integer pageSize = param.getPageSize();
			// 供应链本地数据库没有存商品的名称（因为若主数据修改商品名称后，不能及时同步）
			// if (param.getName()!=null && !"".equals(param.getName())) {
			// ProductQueryDto productQueryDto=new ProductQueryDto();
			// productQueryDto.set
			// productSCDubboService.queryShelfProduct(productQueryDto);
			// }

			PageInfo<CategoryGoodsOrderPo> datas = categoryGoodsOrderService
					.queryCategoryGoodsOrder(categoryGoodsOrderPo, pageNum, pageSize);
			List<CategoryGoodsOrderDto> categoryGoodsOrderDtos = BeanConvertUtils.convertList(datas.getList(),
					CategoryGoodsOrderDto.class);
			// 为了避免分类、商品名称做了修改，本地数据库只存了对应的id，没有存名称，并且当以后商品所属的分类被更改后，还需要进一步修改该方法
			if (categoryGoodsOrderDtos != null && categoryGoodsOrderDtos.size() > 0) {
				for (CategoryGoodsOrderDto categoryGoodsOrderDto : categoryGoodsOrderDtos) {
					categoryGoodsOrderDto.setFirstCategoryName(categoryDubboService
							.queryById(categoryGoodsOrderDto.getFirstCategoryId()).getResultObject().getCategoryName());
					categoryGoodsOrderDto.setSecondCategoryName(
							categoryDubboService.queryById(categoryGoodsOrderDto.getSecondCategoryId())
									.getResultObject().getCategoryName());
					categoryGoodsOrderDto.setThirdCategoryName(categoryDubboService
							.queryById(categoryGoodsOrderDto.getThirdCategoryId()).getResultObject().getCategoryName());
					// ProductInfoQueryDto
					// productInfoQueryDto=ProductInfoQueryDto.builder().firstCategoryId(param.getFirstCategoryId())
					// .secondCategoryId(param.getSecondCategoryId())
					// .thirdCategoryId(param.getThirdCategoryId())
					// .productName(param.getName()).build();
					// categoryGoodsOrderDto.setName(queryGoodsName(productInfoQueryDto).getResultObject().toString());
				}
			}
			// 封装分页对象
			pageResult.setData(categoryGoodsOrderDtos);
			pageResult.setPageNum(datas.getPageNum());
			pageResult.setPageSize(datas.getPageSize());
			pageResult.setTotal(datas.getTotal());
			response.setErrorMessage("请求成功");
			response.setResultObject(pageResult);
			response.setSuccess(true);
			log.info("调用耗时:" + (System.currentTimeMillis() - beforeTotalDate));
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> checkGoodsOrderCountOver(CategoryGoodsOrderDto param) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			CategoryGoodsOrderPo categoryGoodsOrderPo = BeanConvertUtils.convert(param, CategoryGoodsOrderPo.class);
			int sum = categoryGoodsOrderService.checkGoodOrderCountOver(categoryGoodsOrderPo);
			if (sum == 0) {
				response.setCode(CommonsEnum.RESPONSE_402.getCode());
				response.setErrorMessage("该分类链下已有48条商品设置排序");
				response.setResultObject(false);
			} else {
				response.setResultObject(true);
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
	public Response<String> queryGoodsName(ProductInfoQueryDto productQueryInfoDto) {
		Response<String> response = new Response<String>();
		ProductQueryDto productQueryDto = BeanConvertUtils.convert(productQueryInfoDto, ProductQueryDto.class);
		Response<ProductDto> dubboResponse = productDubboService.queryBasicsProduct(productQueryDto.getProductNo());
		response.setCode(dubboResponse.getCode());
		response.setErrorMessage(dubboResponse.getErrorMessage());
		ProductDto productDto = dubboResponse.getResultObject();
		if (productDto != null) {
			response.setResultObject(productDto.getSaleName());
			response.setSuccess(true);
			return response;
		} else {
			response.setCode(CommonsEnum.RESPONSE_402.getCode());
			response.setErrorMessage("查询商品失败或分类信息不匹配");
			response.setSuccess(true);
			return response;
		}
	}

}
