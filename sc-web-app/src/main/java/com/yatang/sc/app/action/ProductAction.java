package com.yatang.sc.app.action;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.collect.Lists;
import com.yatang.sc.app.vo.ProductDetailVo;
import com.yatang.sc.app.vo.prd.ProductForAppVo;
import com.yatang.sc.app.vo.prd.ProductListForAppQueryParamVo;
import com.yatang.sc.app.vo.prd.ProductListQueryResultVo;
import com.yatang.sc.app.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.app.web.paramvalid.ParamValid;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.ProductDetailDto;
import com.yatang.sc.facade.dto.ServiceCommitmentsDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProductQueryDubboService;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceQueryDubboService;
import com.yatang.sc.inventory.dto.BathCheckInventoryDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.pi.solr.dto.QueryDto;
import com.yatang.xc.pi.solr.dto.ResultDto;
import com.yatang.xc.pi.solr.dubboservice.ScMobileSearchDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @描述: 商品的action
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/7 10:32
 * @版本: v1.0
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
@RestController
@RequestMapping(value = "/sc/product")
public class ProductAction extends AppBaseAction {

	private final ProductQueryDubboService     productQueryDubboService;
	private final OrganizationService          organizationService;
	private final ItemLocInventoryDubboService inventoryDubboService;
	private final ScMobileSearchDubboService   scMobileSearchDubboService;
	private final GoodsPriceQueryDubboService  goodsPriceQueryDubboService;
	private final ProdPlaceQueryDubboService   prodPlaceQueryDubboService;


	/**
	 * 根据商品code和区域编码查询商品信息
	 *
	 * @param productCode
	 *            商品编号
	 * @author yangshuang
	 * @return
	 */
	@RequestMapping(value = "/getProductDetail", method = RequestMethod.GET)
	@ParamValid
	public Response<ProductDetailVo> getProductDetail(
			@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String productCode,
			HttpServletRequest request) {
		Response<ProductDetailVo> response = new Response<>();
		String branchCompanyId = getBranchCompanyId(request);// 获取子公司id
		String storeId = getStoreId(request);//获取门店id
		Response<StoreDto> storeDtoResponse = organizationService.queryStoreById(storeId);//获取门店信息
        log.info("查询门店信息:{}",JSON.toJSONString(storeDtoResponse));
		if (!CommonsEnum.RESPONSE_200.getCode().equals(storeDtoResponse.getCode())) {// 响应异常
			return BeanConvertUtils.convert(storeDtoResponse, Response.class);
		}
		StoreDto storeDto = storeDtoResponse.getResultObject();

		if (null==storeDto) {//门店为空
			log.info("获取门店信息错误"+storeDto);
			response.setCode(CommonsEnum.RESPONSE_10039.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_10039.getName());
			return response;
		}
		String deliveryWarehouseCode = storeDto.getDeliveryWarehouseCode();//获取门店关联的逻辑仓库信息
		Response<ProductDetailDto> productDetailDtoResponse = productQueryDubboService.getProductDetail(productCode,
				branchCompanyId,deliveryWarehouseCode);
		log.info("查询商品详情:{}",JSON.toJSONString(productDetailDtoResponse));
		if (!CommonsEnum.RESPONSE_200.getCode().equals(productDetailDtoResponse.getCode())) {// 响应异常
			return BeanConvertUtils.convert(productDetailDtoResponse, Response.class);
		}
		// dto2vo
		ProductDetailDto productDetailDto = productDetailDtoResponse.getResultObject();
		ProductDetailVo productDetailVo = BeanConvertUtils.convert(productDetailDto, ProductDetailVo.class);

		//拼接箱装规格  yinyuxin
		String CNT=productDetailVo.getProdSellPriceInfo().getSalesInsideNumber()+productDetailVo.getMinUnit()+"/"+productDetailVo.getFullCaseUnit();
		productDetailVo.setCNT((productDetailVo.getSellFullCase()==0?null:CNT));

		productDetailVo.setPackingSpecifications(productDetailVo.getPackingSpecifications()+"/"+productDetailVo.getMinUnit());

		productDetailVo.setMiniNumber(productDetailVo.getProdSellPriceInfo().getMinNumber()+(productDetailVo.getSellFullCase()==0?productDetailVo.getMinUnit():productDetailVo.getFullCaseUnit())+"起订");

		//拼接商品地点关系（获取配送方式 因为需要门店参数，所以直接在action层调用，减少修改） 尹玉新
		Response<List<ProdPlaceDto>> prodPlaceResponse = prodPlaceQueryDubboService
				.queryDirectDeliveryProduct(storeDto.getId(), Lists.newArrayList(productDetailVo.getId()));
		ServiceCommitmentsDto serviceCommitmentsDto=new ServiceCommitmentsDto();
		List<ServiceCommitmentsDto> serviceCommitmentsVos=productDetailVo.getServiceCommitments();
		//保证与主键不一样
		serviceCommitmentsDto.setId(0);
		serviceCommitmentsDto.setStatus(1);
		serviceCommitmentsDto.setSort(Integer.MAX_VALUE);
		if (null!=prodPlaceResponse&& prodPlaceResponse.isSuccess()&&CollectionUtils.isNotEmpty(prodPlaceResponse.getResultObject())){
			Integer logistModel=prodPlaceResponse.getResultObject().get(0).getLogisticsModel();
			productDetailVo.setLogisticsModel(logistModel);
			serviceCommitmentsDto.setPromiseContent(logistModel==0?"供应商直送":"雅堂统配");
			if (logistModel != null && logistModel == 0) {
				productDetailVo.setStock(Long.MAX_VALUE);
			}
		}else {
			//默认是统配
			serviceCommitmentsDto.setPromiseContent("雅堂统配");
			productDetailVo.setLogisticsModel(1);
		}
		serviceCommitmentsVos.add(serviceCommitmentsDto);
		response.setResultObject(productDetailVo);
		response.setSuccess(true);
		response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		return response;

	}



	@RequestMapping(value = "testCity")
	public void test() {
		try {
			Response<FranchiseeDto> dtoResponse = organizationService.queryFranchiseeById("jims_000846");
			List<StoreDto> stores = dtoResponse.getResultObject().getStores();
			List<String> list = new ArrayList<>();
			for (StoreDto convenientStoreDTO : stores) {
				list.add(convenientStoreDTO.getCityId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	/**
	 * 
	 * 查询商品列表
	 *
	 * @param paramVo
	 * @return
	 */
	@RequestMapping(value = "queryProductList", method = RequestMethod.GET)
	public Response<ProductListQueryResultVo> queryProductList(ProductListForAppQueryParamVo paramVo) {
		//获取商品列表
		Response<ProductListQueryResultVo> voResponse = new Response<>();
		String branchCompanyId = getBranchCompanyId(getRequest());
		if (branchCompanyId == null || "".equals(branchCompanyId)) {
			voResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
			voResponse.setErrorMessage("获取门店所属子公司出错");
			voResponse.setResultObject(null);
			voResponse.setSuccess(false);
			return voResponse;
		}
		QueryDto queryDto = BeanConvertUtils.convert(paramVo, QueryDto.class);
		queryDto.setBranchCompanyId(branchCompanyId);
		Response<ResultDto> dtoResponse = scMobileSearchDubboService.search(queryDto);
		ResultDto resultDto = dtoResponse.getResultObject();

		ProductListQueryResultVo data = BeanConvertUtils.convert(resultDto, ProductListQueryResultVo.class);
		if (data==null){
			data=new ProductListQueryResultVo();
		}

		if (data.getRecords().size() > 0) {

			//添加库存信息    yinyuxin
			//获取仓库编码
			String storeId = getStoreId(getRequest());//获取门店id
			Response<StoreDto> storeDtoResponse = organizationService.queryStoreById(storeId);//获取门店信息
			log.info("查询门店信息:{}", JSON.toJSONString(storeDtoResponse));
			if (!CommonsEnum.RESPONSE_200.getCode().equals(storeDtoResponse.getCode())) {// 响应异常
				return BeanConvertUtils.convert(storeDtoResponse, Response.class);
			}
			StoreDto storeDto = storeDtoResponse.getResultObject();
			String deliveryWarehouseCode = storeDto.getDeliveryWarehouseCode();//仓库编码

			List<ItemInventoryDto> ids = new ArrayList();
			List<ProductForAppVo> recordVos = data.getRecords();
			List<String> productIds=new ArrayList<>();
			for (ProductForAppVo pro : recordVos) {
				ItemInventoryDto iid = new ItemInventoryDto();
				iid.setBranchCompanyId(storeDto.getBranchCompanyId());
				iid.setProductId(pro.getProductId());
				iid.setProductCode(pro.getProductCode());
				iid.setLoc(deliveryWarehouseCode);
				ids.add(iid);
				if ("1".equals(pro.getSellFullCase())){
					productIds.add(pro.getProductId());
				}
			}
			Response<BathCheckInventoryDto> response = inventoryDubboService.batchCheckInventory(ids);
			if (response.isSuccess()) {
				for (ProductForAppVo prdRecordVo : recordVos) {
					prdRecordVo.setInventory(response.getResultObject().getEnoughStock().contains(prdRecordVo.getProductId()) ? true : false);
				}
				//排序
				Collections.sort(recordVos);
			} else {
				log.error("queryProductList-->批量查询库存失败");
			}

			//根据商品是否按箱销售，渲染vo里商品的规格  yinyuxin


			Response<Map<String, Integer>> saleInnerNumberForAppResponse = goodsPriceQueryDubboService
					.querySaleInnerNumberForApp(productIds, branchCompanyId);
			if (saleInnerNumberForAppResponse.isSuccess()) {
				for (ProductForAppVo prdRecordVo : recordVos) {
					if ("1".equals(prdRecordVo.getSellFullCase())&&saleInnerNumberForAppResponse.getResultObject().containsKey(prdRecordVo.getProductId())){
						prdRecordVo.setPackingSpecifications(saleInnerNumberForAppResponse.getResultObject().get(prdRecordVo.getProductId())+prdRecordVo.getMinUnit()+"/"+prdRecordVo.getDisplayUnit());
					}else if ("0".equals(prdRecordVo.getSellFullCase())){
						prdRecordVo.setPackingSpecifications(prdRecordVo.getPackingSpecifications()+"/"+prdRecordVo.getMinUnit());
					}
				}
			} else {
				log.error("queryProductList-->渲染商品的规格失败");
			}
		}
		data.setPageSize(paramVo.getPageSize());
		data.setTotal(resultDto.getTotalCount());
		data.setPageNum(resultDto.getPageNum());
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(data);
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}

}
