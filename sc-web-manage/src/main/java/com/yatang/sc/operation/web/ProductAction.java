package com.yatang.sc.operation.web;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dubboservice.ProductQueryDubboService;
import com.yatang.xc.pi.solr.dto.PrdRecordDto;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.operation.vo.BrandQueryVo;
import com.yatang.sc.operation.vo.BrandVo;
import com.yatang.sc.operation.vo.prod.ProductDetailsVo;
import com.yatang.sc.operation.vo.prod.ProductForSelectVo;
import com.yatang.sc.operation.vo.prod.ProductListVo;
import com.yatang.sc.operation.vo.prod.ProductQueryVo;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.xc.mbd.biz.prod.dubboservice.BrandDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductSCDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.BrandQueryResultDto;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.QueryBrandDto;
import com.yatang.xc.mbd.pi.es.dto.ImageDto;
import com.yatang.xc.mbd.pi.es.dto.InternationalCodeDto;
import com.yatang.xc.mbd.pi.es.dto.KeywordDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import com.yatang.xc.pi.solr.dto.ScBackendPrdAddQueryDto;
import com.yatang.xc.pi.solr.dto.ScBackendQueryDto;
import com.yatang.xc.pi.solr.dto.ScBackendResultDto;
import com.yatang.xc.pi.solr.dubboservice.ScBackendSearchDubboService;

import lombok.extern.log4j.Log4j;

/**
 * @描述: 商品详情 HTTP API
 * @作者: yinyuxin
 * @创建时间: 2017年5月28日14:59:15
 * @版本: 1.0 .
 */
@Log4j
@RestController
@RequestMapping(value = "/sc/product")
public class ProductAction extends BaseAction{

	@Autowired
	private ProductScIndexDubboService indexDubboService;
	@Autowired
	private ScBackendSearchDubboService	 scBackendSearchDubboService;
	@Autowired
	private BrandDubboService			brandDubboService;
	@Autowired
	private ProductSCDubboService		productSCDubboService;
	@Autowired
	private ProductQueryDubboService productQueryDubboService;



	/**
	 * 根据商品id查询商品详情
	 * 
	 * @param productId
	 *            商品id
	 * @return
	 */
	@RequestMapping(value = "queryProductById", method = RequestMethod.GET)
	public Response<ProductDetailsVo> queryProductById(
			@NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productId) {
		Response<ProductDetailsVo> voResponse = new Response<ProductDetailsVo>();
		ProductDetailsVo productDetailsVo = new ProductDetailsVo();
		Response<ProductIndexDto> dtoResponse = indexDubboService.queryByProductId(productId);
		ProductIndexDto productDto = dtoResponse.getResultObject();

		if (productDto != null) {
			productDetailsVo = BeanConvertUtils.convert(productDto, ProductDetailsVo.class);
			// 设置商品国际码集合
			List<InternationalCodeDto> internationalCodeDtos = productDto.getInternationalCodes();
			List<String> internationalCodes = new ArrayList<String>();
			if (internationalCodeDtos != null && internationalCodeDtos.size() > 0) {
				for (InternationalCodeDto internationalCodeDto : internationalCodeDtos) {
					internationalCodes.add(internationalCodeDto.getInternationalCode());
				}
				productDetailsVo.setInternationalCodes(internationalCodes);
			}
			// 设置品牌名
			if (productDto.getBrand() != null) {
				productDetailsVo.setBrandName(productDto.getBrand().getName());
			}
			// 搜索关键字
			if (productDto.getKeywords() != null && productDto.getKeywords().size() > 0) {
				List<String> keyWords = Lists.newArrayList();
				for (KeywordDto keywordDto : productDto.getKeywords()) {
					keyWords.add(keywordDto.getName());
				}
				productDetailsVo.setKeyWords(keyWords);
			}
			// 商品图片
			if (productDto.getImages() != null && productDto.getImages().size() > 0) {
				List<String> imgUrls = Lists.newArrayList();
				for (ImageDto imageDto : productDto.getImages()) {
					imgUrls.add(imageDto.getUrl());
				}
				productDetailsVo.setImgUrls(imgUrls);
			}
		}
		voResponse.setResultObject(productDetailsVo);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess("200".equals(dtoResponse.getCode()) ? true : false);
		return voResponse;
	}



	/**
	 * 分页查询商品清单列表
	 * 
	 * @param productQueryVo
	 * @return
	 */
	@RequestMapping(value = "queryProductsByPages", method = RequestMethod.GET)
	public Response<PageResult<ProductListVo>> queryProductsByPages(ProductQueryVo productQueryVo) {
		log.info("-------------queryProductsByPages():" + productQueryVo.toString() + "---------------");
		Response<PageResult<ProductListVo>> voResponse = new Response<PageResult<ProductListVo>>();
		PageResult<ProductListVo> pageResult = new PageResult<ProductListVo>();
		List<ProductListVo> list = new ArrayList<ProductListVo>();
		ScBackendQueryDto scQueryDto = BeanConvertUtils.convert(productQueryVo, ScBackendQueryDto.class);
		Response<ScBackendResultDto> dtoResponse = scBackendSearchDubboService.search(scQueryDto);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess("200".equals(dtoResponse.getCode()) ? true : false);
		if (null == dtoResponse.getResultObject() || null == dtoResponse.getResultObject().getRecords()) {
			// 为了前端统一，list是空数据的时候返回[] ,而不是null
			pageResult.setData(list);
			voResponse.setResultObject(pageResult);
			return voResponse;
		}
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject().getRecords(), ProductListVo.class);
		pageResult.setData(list);
		pageResult.setPageNum(dtoResponse.getResultObject().getPageNum());
		pageResult.setTotal((long) dtoResponse.getResultObject().getTotalCount());
		pageResult.setPageSize(productQueryVo.getPageSize());
		voResponse.setResultObject(pageResult);
		return voResponse;
	}



	/**
	 * 分页查询品牌列表
	 * 
	 * @param brandQueryVo
	 * @return
	 */
	@RequestMapping(value = "queryBrandsByPages", method = RequestMethod.GET)
	public Response<PageResult<BrandVo>> queryBrandsByPages(BrandQueryVo brandQueryVo) {
		log.info("-------------queryBrandsByPages():" + brandQueryVo.toString() + "---------------");
		Response<PageResult<BrandVo>> voResponse = new Response<PageResult<BrandVo>>();
		PageResult<BrandVo> pageResult = new PageResult<BrandVo>();
		List<BrandVo> list = new ArrayList<BrandVo>();
		QueryBrandDto queryBrandDto = BeanConvertUtils.convert(brandQueryVo, QueryBrandDto.class);
		Response<BrandQueryResultDto> dtoResponse = brandDubboService.queryBrands(queryBrandDto);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess("200".equals(dtoResponse.getCode()) ? true : false);
		if (null == dtoResponse.getResultObject() || null == dtoResponse.getResultObject().getRecords()) {
			// 为了前端统一，list是空数据的时候返回[] ,而不是null
			pageResult.setData(list);
			voResponse.setResultObject(pageResult);
			return voResponse;
		}
		list = BeanConvertUtils.convertList(dtoResponse.getResultObject().getRecords(), BrandVo.class);
		pageResult.setData(list);
		pageResult.setPageNum(dtoResponse.getResultObject().getPageNum());
		pageResult.setPageSize(dtoResponse.getResultObject().getPageSize());
		pageResult.setTotal((long) dtoResponse.getResultObject().getCount());
		voResponse.setResultObject(pageResult);
		return voResponse;
	}



	/**
	 * 
	 * 全国上下架
	 *
	 * @param productQueryVo
	 * @return
	 */
	@RequestMapping(value = "availablProducts", method = RequestMethod.POST)
	public Response<Integer> availablProducts(
			@RequestBody @Validated({ GroupOne.class }) ProductQueryVo productQueryVo) {
		Response<Integer> voResponse = new Response<Integer>();
		Response<Integer> dtoResponse = new Response<Integer>();
		if (productQueryVo.getSupplyChainStatus() == 2) {
			// 全国上架
			dtoResponse = productSCDubboService.updateOnShelf(productQueryVo.getIds());
		} else if (productQueryVo.getSupplyChainStatus() == 3) {
			// 全国下架
			productSCDubboService.updateOffShelf(productQueryVo.getIds());
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setResultObject(dtoResponse.getResultObject());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess("200".equals(dtoResponse.getCode()) ? true : false);
		return voResponse;
	}



	/**
	 * 通过商品编号或者名称查询商品下拉框数据
	 *
	 * @param param
	 *            商品编号或者名称
	 * @return
	 */
	@RequestMapping(value = "queryProductForSelect", method = RequestMethod.GET)
	public Response<PageResult<ProductForSelectVo>> queryProductForSelect(ProductQueryVo param) {
		Response<PageResult<ProductForSelectVo>> voResponse = new Response<PageResult<ProductForSelectVo>>();
		PageResult<ProductForSelectVo> pageResult = new PageResult<ProductForSelectVo>();
		List<ProductForSelectVo> list = new ArrayList<ProductForSelectVo>();

		ScBackendPrdAddQueryDto scBackendPrdAddQueryDto;
		scBackendPrdAddQueryDto = BeanConvertUtils.convert(param, ScBackendPrdAddQueryDto.class);
		Response<ScBackendResultDto> dtoResponse = scBackendSearchDubboService.prdAddSearch(scBackendPrdAddQueryDto);
		if (dtoResponse.getResultObject() != null && dtoResponse.getResultObject().getRecords() != null
				&& dtoResponse.getResultObject().getRecords().size() > 0) {
			for(PrdRecordDto prdRecordDto:dtoResponse.getResultObject().getRecords()){
				ProductForSelectVo productForSelectVo=new ProductForSelectVo();
				productForSelectVo.setProductCode(prdRecordDto.getProductCode());
				productForSelectVo.setProductId(prdRecordDto.getProductId());
				productForSelectVo.setProductName(prdRecordDto.getSaleName());
				list.add(productForSelectVo);
			}
			pageResult.setPageNum(dtoResponse.getResultObject().getPageNum());
			pageResult.setTotal((long) dtoResponse.getResultObject().getTotalCount());
		}
		pageResult.setData(list);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(pageResult);
		voResponse.setSuccess("200".equals(dtoResponse.getCode()) ? true : false);
		return voResponse;
	}



	/**
	 * 手动同步商品
	 * @param productId 商品编码
	 * @return
	 */
	@RequestMapping(value = "syncProductByManual",method = RequestMethod.GET)
	public Response<Boolean> syncProductByManual(String productId){
		Response<Boolean> Response = productQueryDubboService.syncProduct(productId);
		return Response;
	}
}
