package com.yatang.sc.facade.dubboservice.impl;

import com.yatang.sc.facade.domain.pm.*;
import com.yatang.sc.facade.dto.pm.*;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierOperTaxInfoDto;
import com.yatang.sc.facade.dto.system.UserDetailDto;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.dubboservice.UserDubboService;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundQueryDubboService;
import com.yatang.sc.facade.service.PmpurchaseRefundService;

import java.util.List;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/10/17 15:03
 * @version: v1.0
 */
@Service("pmPurchaseRefundQueryDubboService")
public class PmPurchaseRefundQueryDubboServiceImpl implements PmPurchaseRefundQueryDubboService {
	private static final Logger		log	= LoggerFactory.getLogger(PmPurchaseRefundQueryDubboServiceImpl.class);
	@Autowired
	private PmpurchaseRefundService	pmpurchaseRefundService;
	@Autowired
	private ItemLocInventoryDubboService itemLocInventoryDubboService;
	@Autowired
	private UserDubboService userDubboService;
	@Autowired
	private SupplierQueryDubboService supplierQueryDubboService;

	@Override
	public Response<PageResult<PmPurchaseRefundDto>> queryPmPurchaseRefund(
			PmPurchaseRefundQueryListDto pmPurchaseRefundQueryListDto) {
		log.info("接收到请求：PmPurchaseRefundQueryDubboService.queryPmPrchaseRefund({})",
				JSON.toJSONString(pmPurchaseRefundQueryListDto));
		Response<PageResult<PmPurchaseRefundDto>> response = new Response<>();
		try {
			PmPurchaseRefundExtPo pmPurchaseRefundExtPo = BeanConvertUtils.convert(pmPurchaseRefundQueryListDto,
					PmPurchaseRefundExtPo.class);
			PageInfo<PmPurchaseRefundPo> pageInfo = pmpurchaseRefundService
					.queryPmPurchaseRefund(pmPurchaseRefundExtPo);
			log.info("查询列表结果：{}", JSON.toJSONString(pageInfo));
			if (null == pageInfo || null == pageInfo.getList() || pageInfo.getList().isEmpty()) {
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				return response;
			}
			PageResult<PmPurchaseRefundDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(), PmPurchaseRefundDto.class));
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(pageResult);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode()+e.getMessage());
			response.setSuccess(false);
		}
		return response;
	}

	@Override
	public Response<PageResult<PmPurchaseRefundQueryProviderDto>> queryPmPurchaseRefundProviderList(PmPurchaseRefundQueryProviderDto pmPurchaseRefundQueryProviderDto) {
		log.info("接收到请求：PmPurchaseRefundQueryDubboService.queryPmPurchaseRefundProviderList({})",
				JSON.toJSONString(pmPurchaseRefundQueryProviderDto));
		Response<PageResult<PmPurchaseRefundQueryProviderDto>> response = new Response<>();
		try {
			PmPurchaseRefundProviderExtPo pmPurchaseRefundProviderExtPo = BeanConvertUtils.convert(pmPurchaseRefundQueryProviderDto,
					PmPurchaseRefundProviderExtPo.class);
			PageInfo<PmPurchaseReturnProviderPo> pageInfo = pmpurchaseRefundService
					.queryPmPurchaseRefundProvider(pmPurchaseRefundProviderExtPo);
			log.info("查询列表结果：{}", JSON.toJSONString(pageInfo));
			if (null == pageInfo || null == pageInfo.getList() || pageInfo.getList().isEmpty()) {
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				return response;
			}
			PageResult<PmPurchaseRefundQueryProviderDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(), PmPurchaseRefundQueryProviderDto.class));
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(pageResult);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode() + e.getMessage());
			response.setSuccess(false);
		}
		return response;
	}

	@Override
	public Response<PmPurchaseRefundProviderDto> queryRefundDetailProviderById(Long id) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<查询供应商采购退货详情>> queryRefundDetailProviderById(Long id): id=" + id + "----------");
		}

		Response<PmPurchaseRefundProviderDto> response = new Response<>();

		try {
			PmPurchaseRefundProviderPo po = pmpurchaseRefundService.selectRefundDetailProviderById(id);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(BeanConvertUtils.convert(po, PmPurchaseRefundProviderDto.class));
			//获取用户名
			Response<UserDetailDto> one = userDubboService.findOne(Long.valueOf(po.getCreateUserId()));
			if (one.getResultObject()!=null) {
				response.getResultObject().setCreateUser(one.getResultObject().getUserName());
			}
			//获取供应商详细地址
			Response<SupplierInfoDto> supplierInfoDtoResponse = supplierQueryDubboService.queryById(po.getSpId());
			SupplierOperTaxInfoDto info = supplierInfoDtoResponse.getResultObject().getSupplierOperTaxInfo();
			response.getResultObject().setAdrName(info.getCompanyLocProvince()+info.getCompanyLocCity()+info.getCompanyLocCounty()+info.getCompanyDetailAddress());

			response.setSuccess(true);
		} catch (Exception e) {
			log.error("---------查询供应商采购退货详情>>queryRefundDetailById(),error=" + ExceptionUtils.getFullStackTrace(e) + "----------");
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
		}
		return response;
	}

	@Override
	public Response<PmPurchaseRefundDto>  queryRefundDetailById(Long id) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<查询采购退货详情>> queryRefundDetailById(Long id): id=" + id + "----------");
		}

		Response<PmPurchaseRefundDto> response = new Response<>();

		try {
			PmPurchaseRefundPo po = pmpurchaseRefundService.selectRefundDetailById(id);

			if (po != null) {
				List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos=po.getPmPurchaseRefundItems();
				if (pmPurchaseRefundItemPos!=null && pmPurchaseRefundItemPos.size()>0){
					for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo:pmPurchaseRefundItemPos){
						pmPurchaseRefundItemPo.setPurchaseOrderNo(pmPurchaseRefundItemPo.getPurchaseOrderNo());
						//查询库存
						ItemInventoryQueryParamDto itemInventoryQueryParamDto=new ItemInventoryQueryParamDto();
						itemInventoryQueryParamDto.setLogicWareHouseCode(po.getRefundAdrCode());
						itemInventoryQueryParamDto.setProductCode(pmPurchaseRefundItemPo.getProductCode());
						Response<List<ItemLocSohDto>> dtoResponse = itemLocInventoryDubboService
								.queryItemInventoryListByParam(itemInventoryQueryParamDto);
						if (!com.google.common.base.Objects.equal("200",dtoResponse.getCode())){
							log.error("----------根据采购单号、商品code、品牌id查询采购商品清单>>addRefundProducts():error=查询库存失败" +dtoResponse.getErrorMessage()  + "----------");
							throw new RuntimeException(dtoResponse.getErrorMessage());
						}
						if (dtoResponse.getResultObject().size()>0){
							//计算可用库存=现有库存-销售保留-调拨预留-退货预留(因为只用于详情页显示，不减当前单子的退货预留，否则会导致页面可退库存不足)
							Long stockOnHand = dtoResponse.getResultObject().get(0).getStockOnHand() == null ? 0L : dtoResponse.getResultObject().get(0).getStockOnHand();//现有库存
							Long orderReservedQty = dtoResponse.getResultObject().get(0).getOrderReservedQty() == null ? 0L : dtoResponse.getResultObject().get(0).getOrderReservedQty();//销售保留
							Long tsfReservedQty = dtoResponse.getResultObject().get(0).getTsfReservedQty() == null ? 0L : dtoResponse.getResultObject().get(0).getTsfReservedQty();//调拨预留
							Long rtvQty = dtoResponse.getResultObject().get(0).getRtvQty() == null ? 0L : dtoResponse.getResultObject().get(0).getRtvQty();//退货预留
							int possibleNum=Integer.valueOf(stockOnHand-orderReservedQty-tsfReservedQty-rtvQty+pmPurchaseRefundItemPo.getRefundAmount()+"");
							if (po.getStatus()==3||po.getStatus()==5||po.getStatus()==6){
								possibleNum=Integer.valueOf(stockOnHand-orderReservedQty-tsfReservedQty-rtvQty+"");
							}
							pmPurchaseRefundItemPo.setPossibleNum(possibleNum);
							pmPurchaseRefundItemPo.setAvCost(dtoResponse.getResultObject().get(0).getAvCost());
						}else {
							pmPurchaseRefundItemPo.setPossibleNum(0);
							pmPurchaseRefundItemPo.setAvCost(0.00);
						}
					}
				}

				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(BeanConvertUtils.convert(po, PmPurchaseRefundDto.class));
				response.setSuccess(true);
			} else {
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				response.setResultObject(null);
				response.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("---------查询采购退货详情>>queryRefundDetailById(),error="+ ExceptionUtils.getFullStackTrace(e)+"----------");
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName()+e.getMessage());
		}
		return response;
	}


	/*
	@Override
	public Response<PageResult<PmPurchaseRefundAuditResultDto>> queryPmPurchaseRefundAudit(
			PmPurchaseRefundAuditQueryDto pmPurchaseRefundAuditQueryDto) {
		log.info("接收到请求：PmPurchaseRefundQueryDubboService.queryPmPurchaseRefundAudit{}", JSON.toJSONString(pmPurchaseRefundAuditQueryDto));
		Response<PageResult<PmPurchaseRefundAuditResultDto>> response = new Response<>();
		try {
			PmPurchaseRefundAuditQueryPo pmPurchaseRefundAuditQueryPo = BeanConvertUtils
					.convert(pmPurchaseRefundAuditQueryDto, PmPurchaseRefundAuditQueryPo.class);
			PageInfo<PmPurchaseRefundWithProcessNodeNamePo> pageInfo = pmpurchaseRefundService
					.queryPurchaseRefundAuditList(pmPurchaseRefundAuditQueryPo);
			log.info("查询列表结果：{}", JSON.toJSONString(pageInfo));
			if (null == pageInfo || null == pageInfo.getList() || pageInfo.getList().isEmpty()) {
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				return response;
			}
			PageResult<PmPurchaseRefundAuditResultDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(), PmPurchaseRefundAuditResultDto.class));
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(pageResult);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName()+e.getMessage());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
		}
		return response;
	}
	*/
}
