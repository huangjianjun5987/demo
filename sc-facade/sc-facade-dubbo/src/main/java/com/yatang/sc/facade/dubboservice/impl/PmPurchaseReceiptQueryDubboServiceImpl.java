package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.converter.DateConverter;
import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.dto.pm.*;
import com.yatang.sc.facade.dto.system.UserDetailDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptQueryDubboService;
import com.yatang.sc.facade.dubboservice.UserDubboService;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("pmPurchaseReceiptQueryDubboService")
public class PmPurchaseReceiptQueryDubboServiceImpl implements PmPurchaseReceiptQueryDubboService {

	@Autowired
	private PmPurchaseReceiptService	pmPurchaseReceiptService;
	@Autowired
	private PmPurchaseOrderService		pmPurchaseOrderService;
	@Autowired
	private ProductScIndexDubboService	indexDubboService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserDubboService userDubboService;

	@Override
	public Response<PageResult<PmPurchaseReceiptDto>> queryReceiptByPages(PmPurchaseReceiptParamDto paramDto) {
		if (log.isInfoEnabled()) {
			log.info("----------查询收货单列表>>queryReceiptByPages():paramDto=" + JSON.toJSONString(paramDto) + "----------");
		}
		PmPurchaseReceiptParamPo paramPo = BeanConvertUtils.convert(paramDto, PmPurchaseReceiptParamPo.class);
		Response<PageResult<PmPurchaseReceiptDto>> response = new Response<PageResult<PmPurchaseReceiptDto>>();
		PageResult<PmPurchaseReceiptDto> pageResult = new PageResult<>();
		List<PmPurchaseReceiptDto> list = new ArrayList<PmPurchaseReceiptDto>();
		try {
			PageInfo<PmPurchaseReceiptPo> queryReceiptPos = pmPurchaseReceiptService.queryReceipts(paramPo);
			if (queryReceiptPos != null && queryReceiptPos.getList() != null && queryReceiptPos.getList().size() > 0) {
				for (PmPurchaseReceiptPo pmPurchaseReceiptPo : queryReceiptPos.getList()) {
					PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService
							.selectByPrimaryKey(Long.valueOf(pmPurchaseReceiptPo.getPurchaseOrderId()));
					PmPurchaseReceiptDto pmPurchaseReceiptDto = new PmPurchaseReceiptDto();
					if (pmPurchaseOrderPo != null && pmPurchaseReceiptPo != null) {
						ConvertUtils.register(new DateConverter(null), Date.class);
						BeanUtils.copyProperties(pmPurchaseReceiptDto, pmPurchaseOrderPo);
						BeanUtils.copyProperties(pmPurchaseReceiptDto, pmPurchaseReceiptPo);
						pmPurchaseReceiptDto.setBusinessMode(pmPurchaseOrderPo.getBusinessMode());
						list.add(pmPurchaseReceiptDto);
					} else {
						response.setErrorMessage("采购单与收货单数据不匹配，查询失败");
					}
				}
				pageResult.setData(list);
				pageResult.setPageNum(queryReceiptPos.getPageNum());
				pageResult.setTotal(queryReceiptPos.getTotal());
				pageResult.setPageSize(queryReceiptPos.getPageSize());
			} else {
				response.setErrorMessage("未查到数据");
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<PmPurchaseReceiptDetailDto> queryReceiptDetailById(Long id) {
		if (log.isInfoEnabled()) {
			log.info("----------查询收货单详情>>queryReceiptDetailById():id=" + id + "----------");
		}
		Response<PmPurchaseReceiptDetailDto> response = new Response<>();
		PmPurchaseReceiptDetailDto pmPurchaseReceiptDetailDto = new PmPurchaseReceiptDetailDto();
		PmPurchaseReceiptDto pmPurchaseReceipt = new PmPurchaseReceiptDto();
		List<PmPurchaseReceiptItemsDto> receiptPruducts = new ArrayList<>();
		try {
			// 1：查收货单
			PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService.queryReceiptById(id);
			// 1.1:通过收货单查询对应的采购单基础信息
			PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService
					.selectByPrimaryKey(Long.valueOf(pmPurchaseReceiptPo.getPurchaseOrderId()));
			// 1.2：转换Receiptdto属性
			if (pmPurchaseOrderPo != null && pmPurchaseReceiptPo != null) {
				ConvertUtils.register(new DateConverter(null), Date.class);
				BeanUtils.copyProperties(pmPurchaseReceipt, pmPurchaseOrderPo);
				// 1.3：再将收货单自有的属性转换到pmPurchaseReceipt
				BeanUtils.copyProperties(pmPurchaseReceipt, pmPurchaseReceiptPo);
			} else {
				response.setErrorMessage("采购单与收货单数据不匹配，查询失败");
			}

			Integer receiveTotalNumber=0;
			Integer receiptTotalNumber=0;
			BigDecimal receiveTotalPrice=new BigDecimal(0.00);
			BigDecimal receiptTotalPrice=new BigDecimal(0.00);
			// 2:根据收货单id查询收货单明细
			List<PmPurchaseReceiptItemsPo> itemList = pmPurchaseReceiptService
					.queryReceiptItemsByReceiptId(id.toString());
			// 3:遍历明细单，查询相应的采购商品信息
			if (itemList != null && itemList.size() > 0) {
				for (PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo : itemList) {
					PmPurchaseOrderItemPo pmPurchaseOrderItemPo = pmPurchaseOrderService.queryItemById(Long.valueOf(pmPurchaseReceiptItemsPo.getPurchaseOrderItemsId()));
					PmPurchaseReceiptItemsDto pmPurchaseReceiptItemsDto = new PmPurchaseReceiptItemsDto();
					if (pmPurchaseOrderItemPo != null) {
						ConvertUtils.register(new DateConverter(null), Date.class);
						BeanUtils.copyProperties(pmPurchaseReceiptItemsDto, pmPurchaseOrderItemPo);
						BeanUtils.copyProperties(pmPurchaseReceiptItemsDto, pmPurchaseReceiptItemsPo);
						//收货金额
						BigDecimal receiveNumber = null==pmPurchaseReceiptItemsPo.getReceivedNumber()||pmPurchaseReceiptItemsPo.getReceivedNumber()==0 ?
								BigDecimal.ZERO : new BigDecimal(pmPurchaseReceiptItemsPo.getReceivedNumber());
						pmPurchaseReceiptItemsDto.setReceiptPrice(pmPurchaseOrderItemPo.getPurchasePrice().multiply(new BigDecimal(pmPurchaseReceiptItemsDto.getDeliveryNumber())));
						pmPurchaseReceiptItemsDto.setReceivedPrice(pmPurchaseOrderItemPo.getPurchasePrice().multiply(receiveNumber));
						receiptPruducts.add(pmPurchaseReceiptItemsDto);

						receiveTotalNumber+=receiveNumber.intValue();
						receiveTotalPrice=receiveTotalPrice.add(pmPurchaseReceiptItemsDto.getReceivedPrice());
						receiptTotalNumber+=pmPurchaseReceiptItemsPo.getDeliveryNumber();
						receiptTotalPrice=receiptTotalPrice.add(pmPurchaseReceiptItemsDto.getReceiptPrice());
					} else {
						response.setErrorMessage("采购单与收货单数据不匹配，查询失败");
					}
				}
			}
			//合计收货数量
			pmPurchaseReceipt.setReceiveTotalNumber(receiveTotalNumber);
			//合计收货金额
			pmPurchaseReceipt.setReceiveTotalPrice(receiveTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
			pmPurchaseReceipt.setReceiptTotalNumber(receiptTotalNumber);
			pmPurchaseReceipt.setReceiptTotalPrice(receiptTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));


			if (StringUtils.isNotBlank(pmPurchaseReceiptPo.getCreateUserId())) {
				Response<UserDetailDto> userById = userDubboService.findOne(Long.parseLong(pmPurchaseReceiptPo.getCreateUserId()));
				if (!userById.isSuccess()) {
					log.warn("查询当前用户名失败" + userById.toString());
				}
				if (userById.isSuccess() && userById.getResultObject() != null) {
					pmPurchaseReceipt.setCreateUserName(userById.getResultObject().getUserName());
				}

			}else {
				pmPurchaseReceipt.setCreateUserName("系统创建");
			}

			pmPurchaseReceiptDetailDto.setPmPurchaseReceipt(pmPurchaseReceipt);
			pmPurchaseReceiptDetailDto.setReceiptPruducts(receiptPruducts);
			response.setResultObject(pmPurchaseReceiptDetailDto);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;

	}

	public Response<List<PmPurchaseReceiptExportDto>> exportReceipt(PmPurchaseReceiptParamDto paramDto){
		log.info("导出收货单列表>>exportReceipt():paramDto={}", JSON.toJSONString(paramDto));
		Response<List<PmPurchaseReceiptExportDto>> response = new Response<>();
		List<PmPurchaseReceiptExportDto> exportDtos = Lists.newArrayList();
		try {
			PmPurchaseReceiptParamPo paramPo = BeanConvertUtils.convert(paramDto, PmPurchaseReceiptParamPo.class);
			List<PmPurchaseReceiptPo> receipts = pmPurchaseReceiptService.exportReceipts(paramPo);
			//收货单列表
			for (PmPurchaseReceiptPo receipt : receipts) {
				//采购单
				PmPurchaseOrderPo order = pmPurchaseOrderService.selectByPrimaryKey(Long.valueOf(receipt.getPurchaseOrderId()));
				//子公司信息
				BranchCompanyDto branchCompany = organizationService.querySimpleByBranchCompanyId(order.getBranchCompanyId()).getResultObject();
				//收货单项列表
				List<PmPurchaseReceiptItemsPo> receiptItems = pmPurchaseReceiptService.queryReceiptItemsByReceiptId(String.valueOf(receipt.getId()));
				//遍历收货单项列表并查出对应采购单项
				for (PmPurchaseReceiptItemsPo receiptItem : receiptItems) {
					PmPurchaseOrderItemPo orderItem = pmPurchaseOrderService.queryItemById(Long.valueOf(receiptItem.getPurchaseOrderItemsId()));
					if (orderItem != null) {
						//商品信息
						ProductIndexDto product = indexDubboService.queryByProductId(orderItem.getProductId()).getResultObject();
						//封装响应数据
						PmPurchaseReceiptExportDto exportDto = new PmPurchaseReceiptExportDto();
						ConvertUtils.register(new DateConverter(null), Date.class);
						BeanUtils.copyProperties(exportDto, order);
						BeanUtils.copyProperties(exportDto, receipt);
						BeanUtils.copyProperties(exportDto, orderItem);
						BeanUtils.copyProperties(exportDto, receiptItem);
						BeanUtils.copyProperties(exportDto, product);
						exportDto.setBranchCompanyName(branchCompany.getName()); //子公司名
						exportDto.setReceivedUnitPrice(orderItem.getPurchasePrice()); //含税收货单价
						exportDto.setItemTotalAmount(orderItem.getTotalAmount()); //采购单项总额 (含税)
						if (orderItem.getPurchasePrice() != null && receiptItem.getReceivedNumber() != null) {
							exportDto.setReceiptTotalAmount(orderItem.getPurchasePrice().multiply(BigDecimal.valueOf(receiptItem.getReceivedNumber()))); //收货单总金额
						}
						exportDto.setReceivedUnitPriceWithoutTax(orderItem.getTotalWithoutTaxAmount()
								.divide(BigDecimal.valueOf(orderItem.getPurchaseNumber()), 2, BigDecimal.ROUND_HALF_UP));//不含税收货单价
						exportDtos.add(exportDto);
					}
				}
			}
			response.setResultObject(exportDtos);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		}catch (Exception e){
			log.error("导出收货单列表导出异常: {}", ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}

	@Override
	public Response<PmPurchaseReceiptDto> queryPurchaseReceiptByPurchaseOrderId(Long id) {
		log.info("根据采购单ID获取收货单, id={}", id);
		Response<PmPurchaseReceiptDto> response = new Response<>();
		try {
			PmPurchaseReceiptPo purchaseReceiptPo = pmPurchaseReceiptService.queryPurchaseReceiptByPurchaseOrderId(id);
			PmPurchaseReceiptDto receiptDto = BeanConvertUtils.convert(purchaseReceiptPo, PmPurchaseReceiptDto.class);
			response.setResultObject(receiptDto);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		}catch (Exception e){
			log.error("根据采购单ID获取收货单异常, id={}, Exception", id, ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}


	/**
	 * 通过采购单id查询收货
	 * @author kangdong
	 * @param purchaseOrderId
	 * @return
	 */
	@Override
	public Response<PmPurchaseReceiptDetailDto> queryReceiptDetailByPurchaseOrderId(Long purchaseOrderId) {
		if (log.isInfoEnabled()) {
			log.info("----------查询收货单详情>>queryReceiptDetailByPurchaseOrderId():purchaseOrderId=" + purchaseOrderId + "----------");
		}
		Response<PmPurchaseReceiptDetailDto> response = new Response<>();
		PmPurchaseReceiptDetailDto pmPurchaseReceiptDetailDto = new PmPurchaseReceiptDetailDto();
		PmPurchaseReceiptDto pmPurchaseReceipt = new PmPurchaseReceiptDto();
		List<PmPurchaseReceiptItemsDto> receiptPruducts = new ArrayList<>();
		try {
			// 1：查收货单
			List<PmPurchaseReceiptPo> receiptPos = pmPurchaseReceiptService.queryPurchaseReceiptListByOrderIdAndStatus(purchaseOrderId.toString());
			Preconditions.checkArgument(receiptPos.size()>0,"数据为空");

			PmPurchaseReceiptPo pmPurchaseReceiptPo = receiptPos.get(0);
			//PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService.queryReceiptById(id);
			// 1.1:通过收货单查询对应的采购单基础信息
			PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService.selectByPrimaryKey(Long.valueOf(pmPurchaseReceiptPo.getPurchaseOrderId()));
			// 1.2：转换Receiptdto属性
			if (pmPurchaseOrderPo != null && pmPurchaseReceiptPo != null) {
				ConvertUtils.register(new DateConverter(null), Date.class);
				BeanUtils.copyProperties(pmPurchaseReceipt, pmPurchaseOrderPo);
				// 1.3：再将收货单自有的属性转换到pmPurchaseReceipt
				BeanUtils.copyProperties(pmPurchaseReceipt, pmPurchaseReceiptPo);
			} else {
				response.setErrorMessage("采购单与收货单数据不匹配，查询失败");
			}

			Integer receiveTotalNumber=0;
			BigDecimal receiveTotalPrice=new BigDecimal(0.00);
			Integer receiptTotalNumber=0;
			BigDecimal receiptTotalPrice=new BigDecimal(0.00);
			// 2:根据收货单id查询收货单明细
			List<PmPurchaseReceiptItemsPo> itemList = pmPurchaseReceiptService.queryReceiptItemsByReceiptId(pmPurchaseReceiptPo.getId().toString());
			// 3:遍历明细单，查询相应的采购商品信息
			if (itemList != null && itemList.size() > 0) {
				for (PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo : itemList) {
					PmPurchaseOrderItemPo pmPurchaseOrderItemPo = pmPurchaseOrderService.queryItemById(Long.valueOf(pmPurchaseReceiptItemsPo.getPurchaseOrderItemsId()));
					PmPurchaseReceiptItemsDto pmPurchaseReceiptItemsDto = new PmPurchaseReceiptItemsDto();
					if (pmPurchaseOrderItemPo != null && pmPurchaseReceiptItemsPo != null) {
						ConvertUtils.register(new DateConverter(null), Date.class);
						BeanUtils.copyProperties(pmPurchaseReceiptItemsDto, pmPurchaseOrderItemPo);
						BeanUtils.copyProperties(pmPurchaseReceiptItemsDto, pmPurchaseReceiptItemsPo);
						//收货金额
						//收货金额
						BigDecimal receiveNumber = null==pmPurchaseReceiptItemsPo.getReceivedNumber()||pmPurchaseReceiptItemsPo.getReceivedNumber()==0 ?
								BigDecimal.ZERO : new BigDecimal(pmPurchaseReceiptItemsPo.getReceivedNumber());
						pmPurchaseReceiptItemsDto.setReceiptPrice(pmPurchaseOrderItemPo.getPurchasePrice().multiply(new BigDecimal(pmPurchaseReceiptItemsDto.getDeliveryNumber())));
						pmPurchaseReceiptItemsDto.setReceivedPrice(pmPurchaseOrderItemPo.getPurchasePrice().multiply(receiveNumber));
						receiptPruducts.add(pmPurchaseReceiptItemsDto);

						receiveTotalNumber+=receiveNumber.intValue();
						receiveTotalPrice=receiveTotalPrice.add(pmPurchaseReceiptItemsDto.getReceivedPrice());
						receiptTotalNumber+=pmPurchaseReceiptItemsDto.getDeliveryNumber();
						receiptTotalPrice=receiptTotalPrice.add(pmPurchaseReceiptItemsDto.getReceiptPrice());
					} else {
						response.setErrorMessage("采购单与收货单数据不匹配，查询失败");
					}
				}
			}
			//合计收货数量
			pmPurchaseReceipt.setReceiveTotalNumber(receiveTotalNumber);
			//合计收货金额
			pmPurchaseReceipt.setReceiveTotalPrice(receiveTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
			pmPurchaseReceipt.setReceiptTotalNumber(receiptTotalNumber);
			pmPurchaseReceipt.setReceiptTotalPrice(receiptTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));

			if (StringUtils.isNotBlank(pmPurchaseReceiptPo.getCreateUserId())) {
				Response<UserDetailDto> userById = userDubboService.findOne(Long.parseLong(pmPurchaseReceiptPo.getCreateUserId()));
				if (!userById.isSuccess()) {
					log.warn("查询当前用户名失败" + userById.toString());
				}
				if (userById.isSuccess() && userById.getResultObject() != null) {
					pmPurchaseReceipt.setCreateUserName(userById.getResultObject().getUserName());
				}

			}else {
				pmPurchaseReceipt.setCreateUserName("系统创建");
			}

			pmPurchaseReceiptDetailDto.setPmPurchaseReceipt(pmPurchaseReceipt);
			pmPurchaseReceiptDetailDto.setReceiptPruducts(receiptPruducts);
			response.setResultObject(pmPurchaseReceiptDetailDto);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
		}
		return response;
	}

}
