package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import com.yatang.sc.common.utils.NumberToCN;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.*;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderItemQueryParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.dto.pm.*;
import com.yatang.sc.facade.dto.system.UserDetailDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderQueryDubboService;
import com.yatang.sc.facade.dubboservice.UserDubboService;
import com.yatang.sc.facade.flow.PmPurchaseOrderQueryFlowService;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @描述:采购单的查询dubbo实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:19
 * @版本: v1.0
 */
@Slf4j
@Service("pmPurchaseOrderQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PmPurchaseOrderQueryDubboServiceImpl implements PmPurchaseOrderQueryDubboService {

	private final PmPurchaseOrderService			pmPurchaseOrderService;

	private final PmPurchaseOrderQueryFlowService	flowService;

	private final ItemLocInventoryDubboService itemLocInventoryDubboService;

	private final PmPurchaseReceiptService 	pmPurchaseReceiptService;

	private final UserDubboService userDubboService;

	@Override
	public Response<PmPurchaseOrderItemDto> getNewPmPurchaseOrderItem(PmPurchaseOrderItemQueryParamDto queryParamDto) {
		return flowService.getNewPmPurchaseOrderItem(queryParamDto);
	}



	/**
	 * 根据条件查询采购单打印管理列表详细信息
	 *
	 * @param pmPurchaseQueryParamDto
	 * @return
	 */
	@Override
	public Response<PageResult<PmPurchaseOrderInfoDto>> queryPurchaseOrderListInfo(
			PmPurchaseQueryParamDto pmPurchaseQueryParamDto) {
		Response response = new Response();
		try {

			log.info("start----queryPurchaseOrderListInfo---pmPurchaseQueryParamDto:{}",pmPurchaseQueryParamDto);
			PmPurchaseQueryParamPo convert = BeanConvertUtils.convert(pmPurchaseQueryParamDto,
					PmPurchaseQueryParamPo.class);
			PageInfo<PmPurchaseOrderInfoPo> pageInfo = pmPurchaseOrderService.queryPurchaseOrderListInfo(convert);
			List<PmPurchaseOrderInfoDto> pmPurchaseOrderDtos = BeanConvertUtils.convertList(pageInfo.getList(),
					PmPurchaseOrderInfoDto.class);

			PageResult pageResult = new PageResult();
			pageResult.setData(pmPurchaseOrderDtos);
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setPageNum(pageInfo.getPageNum());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			log.info("end----queryPurchaseOrderListInfo");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * 根据条件查询采购单管理列表
	 *
	 * @param pmPurchaseQueryParamDto
	 * @return
	 */
	@Override
	public Response<PageResult<PmPurchaseOrderDto>> queryPurchaseOrderList(
			PmPurchaseQueryParamDto pmPurchaseQueryParamDto) {
		Response response = new Response();
		try {

			log.info("start----queryPurchaseOrderList---pmPurchaseQueryParamDto:{}", JSON.toJSONString(pmPurchaseQueryParamDto));
			PmPurchaseQueryParamPo convert = BeanConvertUtils.convert(pmPurchaseQueryParamDto,
					PmPurchaseQueryParamPo.class);
			PageInfo<PmPurchaseOrderPo> pageInfo = pmPurchaseOrderService.queryPurchaseOrderList(convert);
			List<PmPurchaseOrderDto> pmPurchaseOrderDtos = BeanConvertUtils.convertList(pageInfo.getList(),
					PmPurchaseOrderDto.class);
			PageResult pageResult = new PageResult();
			pageResult.setData(pmPurchaseOrderDtos);
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setPageNum(pageInfo.getPageNum());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());

			log.info("end----queryPurchaseOrderList");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * 根据采购单id查询采购单详情（失败原因）
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Response<PmPurchaseOrderInfoDto> getPurchaseOrderInfoById(Long id) {
		Response response = new Response();
		try {
			log.info("start----getPurchaseOrderInfoById---id:{}",id);
			PmPurchaseOrderInfoPo orderInfoPo = pmPurchaseOrderService.getPurchaseOrderInfoById(id);
			PmPurchaseOrderInfoDto pmPurchaseOrderInfoDto = BeanConvertUtils.convert(orderInfoPo, PmPurchaseOrderInfoDto.class);

			if (orderInfoPo!=null){

				//地点类型为仓库(0)且状态为草稿(0)或者已拒绝(3)时显示该item的可用库存
				if(pmPurchaseOrderInfoDto.getAdrType()==0
						&& (pmPurchaseOrderInfoDto.getStatus()==0 || pmPurchaseOrderInfoDto.getStatus()==3)){
					String inventoryCode = pmPurchaseOrderInfoDto.getAdrTypeCode();
					for(PmPurchaseOrderItemDto orderItemDto : pmPurchaseOrderInfoDto.getPmPurchaseOrderItems()){
						String productCode = orderItemDto.getProductCode();
						ItemInventoryQueryParamDto itemInvDto = new ItemInventoryQueryParamDto();
						itemInvDto.setProductCode(productCode);
						itemInvDto.setLogicWareHouseCode(inventoryCode);
						Response<List<ItemLocSohDto>> itemLocResponse = itemLocInventoryDubboService.queryItemInventoryListByParam(itemInvDto);
						if(!itemLocResponse.isSuccess()){
							log.error("----------根据仓库code、商品code查询商品库存信息>>getPurchaseOrderInfoById():error=查询库存失败:{}------" ,itemLocResponse.getErrorMessage() );
							throw new RuntimeException(itemLocResponse.getErrorMessage());
						}
						int availableInventory = calculateAvailableInventory(itemLocResponse);
						orderItemDto.setAvailableInventory(availableInventory);
					}
				}

				if (0 == orderInfoPo.getStatus() && 0 == orderInfoPo.getPurchaseOrderType()) {
					Response<PmPurchaseOrderInfoDto> pmPurchaseOrderInfoDtoResponse = flowService.checkAndResetPmPurchaseOrderInfo(pmPurchaseOrderInfoDto);
					return pmPurchaseOrderInfoDtoResponse;
				}
			}
			response.setResultObject(pmPurchaseOrderInfoDto);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			log.info("start----getPurchaseOrderInfoById");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * 计算可用库存。
	 *可用库存=现有库存-销售保留-调拨预留-退货预留
	 * @param itemLocResponse 有且只有一条ItemLocSohDto记录
	 * @return
	 */
	private int calculateAvailableInventory(Response<List<ItemLocSohDto>> itemLocResponse) {
		int avaiInv = 0;
		if (itemLocResponse.getResultObject().size()>0){
			Long stockOnHand = itemLocResponse.getResultObject().get(0).getStockOnHand() == null ? 0L : itemLocResponse.getResultObject().get(0).getStockOnHand();//现有库存
			Long orderReservedQty = itemLocResponse.getResultObject().get(0).getOrderReservedQty() == null ? 0L : itemLocResponse.getResultObject().get(0).getOrderReservedQty();//销售保留
			Long tsfReservedQty = itemLocResponse.getResultObject().get(0).getTsfReservedQty() == null ? 0L : itemLocResponse.getResultObject().get(0).getTsfReservedQty();//调拨预留
			Long rtvQty = itemLocResponse.getResultObject().get(0).getRtvQty() == null ? 0L : itemLocResponse.getResultObject().get(0).getRtvQty();//退货预留
			avaiInv = Integer.valueOf(stockOnHand-orderReservedQty-tsfReservedQty-rtvQty+"");
		}
		return avaiInv;
	}

	/**
	 * 根据采购单号查询采购订单详情
	 *
	 * @param purchaseOrderNo
	 * @return
	 */
	@Override
	public Response<PmPurchaseOrderInfoDto> getPurchaseOrderInfoByOrderNo(String purchaseOrderNo) {
		Response response = new Response();
		try {
			log.info("start----getPurchaseOrderInfoByOrderNo---purchaseOrderNo:{}",purchaseOrderNo);
			PmPurchaseOrderInfoPo pmPurchaseOrderInfoPo = pmPurchaseOrderService.getPurchaseOrderInfoByOrderNo(purchaseOrderNo);
			response.setResultObject(BeanConvertUtils.convert(pmPurchaseOrderInfoPo, PmPurchaseOrderInfoDto.class));
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			log.info("end----getPurchaseOrderInfoByOrderNo");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * 根据采购单号和品牌名称查询品牌值清单
	 * @param pmPurchaseOrderItemQueryParamDto
	 * @author yinyuxin
	 * @return
	 */
	@Override
	public Response<PageResult<PmPurchaseOrderItemDto>> selectBrandsByOrderNo(
			PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto) {
		log.info("----------根据采购单号和品牌名称查询品牌值清单>>selectBrandsByOrderNo():param=" + JSONObject.toJSON(pmPurchaseOrderItemQueryParamDto) + "----------");
		Response<PageResult<PmPurchaseOrderItemDto>> response=new Response<>();
		PageResult<PmPurchaseOrderItemDto> pageResult=new PageResult<>();
		try {
			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo=BeanConvertUtils.convert(pmPurchaseOrderItemQueryParamDto,PmPurchaseOrderItemQueryParamPo.class);
			PageInfo<PmPurchaseOrderItemPo> pageInfo = pmPurchaseOrderService
					.selectBrandsByOrderNo(pmPurchaseOrderItemQueryParamPo);
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setData(null);
			if (pageInfo.getList()!=null && pageInfo.getList().size()>0){
				pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(),PmPurchaseOrderItemDto.class));
			}
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setTotal(pageInfo.getTotal());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error("----------根据采购单号和品牌名称查询品牌值清单>>selectBrandsByOrderNo():error=" + ExceptionUtils.getFullStackTrace(e) + "----------");
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName()+e.getMessage());
		}
		return response;
	}



	/**
	 * 根据采购单号和品牌名称查询商品值清单
	 * @param pmPurchaseOrderItemQueryParamDto
	 * @author yinyuxin
	 * @return
	 */
	@Override
	public Response<PageResult<PmPurchaseOrderItemDto>> selectProductByOrderNo(
			PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto) {
		log.info("----------根据采购单号和品牌名称查询商品值清单>>selectProductByOrderNo():param=" + JSONObject.toJSON(pmPurchaseOrderItemQueryParamDto) + "----------");
		Response<PageResult<PmPurchaseOrderItemDto>> response=new Response<>();
		PageResult<PmPurchaseOrderItemDto> pageResult=new PageResult<>();
		try {
			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo=BeanConvertUtils.convert(pmPurchaseOrderItemQueryParamDto,PmPurchaseOrderItemQueryParamPo.class);
			PageInfo<PmPurchaseOrderItemPo> pageInfo = pmPurchaseOrderService
					.selectProductByOrderNo(pmPurchaseOrderItemQueryParamPo);
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setData(null);
			if (pageInfo.getList()!=null && pageInfo.getList().size()>0){
				pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(),PmPurchaseOrderItemDto.class));
			}
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setTotal(pageInfo.getTotal());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error("----------根据采购单号和品牌名称查询商品值清单>>selectProductByOrderNo():error=" + ExceptionUtils.getFullStackTrace(e) + "----------");
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName()+e.getMessage());
		}
		return response;
	}



	@Override
	public Response<List<PmPurchaseRefundItemDto>> addRefundProducts(
			PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto) {
		log.info("----------根据采购单号、商品code、品牌id查询采购商品清单>>addRefundProducts():param=" + JSONObject.toJSON(pmPurchaseOrderItemQueryParamDto) + "----------");
		Response<List<PmPurchaseRefundItemDto>> response=new Response<>();
		try {
			//校验采购单所属仓库或门店是否跟用户所选的地址一样
			PmPurchaseOrderInfoPo purchaseOrderInfo = pmPurchaseOrderService
					.getPurchaseOrderInfoByOrderNo(pmPurchaseOrderItemQueryParamDto.getPurchaseOrderNo());
			if (purchaseOrderInfo==null || (!purchaseOrderInfo.getAdrTypeCode().equals(pmPurchaseOrderItemQueryParamDto.getLogicWareHouseCode()))){
				throw new RuntimeException("采购单查询失败，请检查采购单与退货地点是否匹配");
			}

			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo=BeanConvertUtils.convert(pmPurchaseOrderItemQueryParamDto,PmPurchaseOrderItemQueryParamPo.class);
			List<PmPurchaseOrderItemPo> products = pmPurchaseOrderService
					.addRefundProducts(pmPurchaseOrderItemQueryParamPo);
			List<PmPurchaseRefundItemDto> data=BeanConvertUtils.convertList(products,PmPurchaseRefundItemDto.class);
			//封装采购单单号和库存
			if (data!=null && data.size()>0){
				for (PmPurchaseRefundItemDto pmPurchaseRefundItemDto:data){
					pmPurchaseRefundItemDto.setPurchaseOrderNo(pmPurchaseOrderItemQueryParamDto.getPurchaseOrderNo());
					//查询库存
					ItemInventoryQueryParamDto	itemInventoryQueryParamDto=new ItemInventoryQueryParamDto();
					itemInventoryQueryParamDto.setLogicWareHouseCode(pmPurchaseOrderItemQueryParamDto.getLogicWareHouseCode());
					itemInventoryQueryParamDto.setProductCode(pmPurchaseRefundItemDto.getProductCode());
					Response<List<ItemLocSohDto>> dtoResponse = itemLocInventoryDubboService
							.queryItemInventoryListByParam(itemInventoryQueryParamDto);
					if (!com.google.common.base.Objects.equal("200",dtoResponse.getCode())){
						log.error("----------根据采购单号、商品code、品牌id查询采购商品清单>>addRefundProducts():error=查询库存失败" +dtoResponse.getErrorMessage()  + "----------");
						throw new RuntimeException(dtoResponse.getErrorMessage());
					}

					int possibleNum = calculateAvailableInventory(dtoResponse);
					if(possibleNum<0){
						throw new RuntimeException("商品编号为"+pmPurchaseRefundItemDto.getProductCode()+"的商品，可退库存为负数，添加失败");
					}
					pmPurchaseRefundItemDto.setPossibleNum(possibleNum);
					if(dtoResponse.getResultObject().size()>0){
						pmPurchaseRefundItemDto.setAvCost(dtoResponse.getResultObject().get(0).getAvCost());
					}

					//新建退货单商品时，没有id。但是beancoverutils会将采购单详情的id赋值过去
					pmPurchaseRefundItemDto.setId(null);
				}
			}
			response.setResultObject(data);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (RuntimeException e) {
			log.error("----------根据采购单号、商品code、品牌id查询采购商品清单>>addRefundProducts():error=" + ExceptionUtils.getFullStackTrace(e) + "----------");
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}


	/*
	 * 根据条件查询供应商采购单列表
	 * @param paramDto
	 * @author kangdong
	 * @return
	 */
	@Override
	public Response<PageResult<PmSupplierPurchaseOrderItemDto>> supplierPmPurchaseOrderList(PmSupplierPurchaseOrderQueryParamDto paramDto) {
		Response response=new Response<>();
		try {
			log.info("start----supplierPmPurchaseOrderList---paramDto:{}",JSON.toJSONString(paramDto));
			PmSupplierPurchaseOrderQueryParamPo paramPo = BeanConvertUtils.convert(paramDto,PmSupplierPurchaseOrderQueryParamPo.class);
			PageInfo<PmSupplierPurchaseOrderItemPo> pageInfo = pmPurchaseOrderService.querySupplierPurchaseOrderList(paramPo);
			List<PmSupplierPurchaseOrderItemDto> purchaseOrderDtoList = BeanConvertUtils.convertList(pageInfo.getList(),PmSupplierPurchaseOrderItemDto.class);

			for(PmSupplierPurchaseOrderItemDto item:purchaseOrderDtoList) {
				List<PmPurchaseReceiptPo> receiptPos = pmPurchaseReceiptService.queryPurchaseReceiptListByPurchaseOrderId(String.valueOf(item.getId()),true);
				List<ASNListDto> asnListDtos = new ArrayList<>();
				for(PmPurchaseReceiptPo receiptPo:receiptPos) {
					ASNListDto asnList = new ASNListDto();
					List<PmPurchaseReceiptItemsPo> receiptItemsPos = pmPurchaseReceiptService.queryReceiptItemsByReceiptId(String.valueOf(receiptPo.getId()));
					asnList.setPurchaseOrderNo(item.getPurchaseOrderNo());
					asnList.setAsnNo(receiptPo.getAsn());
					asnList.setPurchaseReceiptId(receiptPo.getId());
					asnList.setPurchaseReceiptNo(receiptPo.getPurchaseReceiptNo());
					asnList.setStatus(receiptPo.getStatus());
					int count = 0;//出口数量
					double totalAmount = 0d;
					BigDecimal purchaseOrderPrice = null;
					for(PmPurchaseReceiptItemsPo receiptItemsPo:receiptItemsPos) {
						PmPurchaseOrderItemPo pmPurchaseOrderItemPo = pmPurchaseOrderService.queryItemById(Long.valueOf(receiptItemsPo.getPurchaseOrderItemsId()));
						if(!Objects.equals(pmPurchaseOrderItemPo,null)) {
							//价格
							purchaseOrderPrice = pmPurchaseOrderItemPo.getPurchasePrice();
						}
						BigDecimal purchasePrice = purchaseOrderPrice.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
						//receivedNumber += receiptItemsPo.getReceivedNumber() !=null? receiptItemsPo.getReceivedNumber():0;
						int deliveryNumber = receiptItemsPo.getDeliveryNumber() !=null? receiptItemsPo.getDeliveryNumber():0;
						BigDecimal total = BigDecimal.valueOf(purchasePrice.doubleValue() * deliveryNumber).setScale(2, BigDecimal.ROUND_HALF_UP);
						//收货金额
						totalAmount += total.doubleValue();
						count += deliveryNumber;
					}
					asnList.setEstimatedCount(count);
					asnList.setOrderCount(count);
					asnList.setOrderTotalAmount(totalAmount);
					asnList.setEstimatedTotalAmount(totalAmount);
					asnListDtos.add(asnList);
				}
				item.setAsnList(asnListDtos);

				List<PmPurchaseReceiptPo> noAsnReceipts = pmPurchaseReceiptService.queryPurchaseReceiptListByPurchaseOrderId(String.valueOf(item.getId()),false);
				item.setIsAbleInput(
						Objects.equals(1, item.getAdrType()) && Objects.equals(1, item.getSpAcceptStatus()) &&
								CollectionUtils.isNotEmpty(noAsnReceipts) && asnListDtos.size() < 2
				);

			}
			PageResult pageResult = new PageResult();
			pageResult.setData(purchaseOrderDtoList);
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setPageNum(pageInfo.getPageNum());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			log.info("end----supplierPmPurchaseOrderList");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/*
	 * 根据ID查询供应商采购单详情
	 * @param purchaseOrderId
	 * @author kangdong
	 * @return
	 */
	@Override
	public Response<PmSupplierPurchaseOrderDetailDto> supplierPmPurchaseOrderDetail(Long purchaseOrderId) {
		Response<PmSupplierPurchaseOrderDetailDto> response = new Response<PmSupplierPurchaseOrderDetailDto>();
		try {
			log.info("start----supplierPmPurchaseOrderDetail---purchaseOrderId:{}",purchaseOrderId);
			PmPurchaseOrderInfoPo pmPurchaseOrderInfoPo = pmPurchaseOrderService.getPurchaseOrderInfoById(purchaseOrderId);
			PmSupplierPurchaseOrderDetailDto pmPurchaseOrderInfoDto = BeanConvertUtils.convert(pmPurchaseOrderInfoPo,PmSupplierPurchaseOrderDetailDto.class);

			pmPurchaseOrderInfoDto.setNumberToCN(NumberToCN.number2CNMontrayUnit(pmPurchaseOrderInfoDto.getTotalAmount()));
			int totalNumber = 0;
			for (PmPurchaseOrderItemDto pmPurchaseOrderItemVo : pmPurchaseOrderInfoDto.getPmPurchaseOrderItems()) {
				int purchaseNumber = pmPurchaseOrderItemVo.getPurchaseNumber() !=null? pmPurchaseOrderItemVo.getPurchaseNumber():0;
				totalNumber = totalNumber + purchaseNumber;
			}

			if (StringUtils.isNotBlank(pmPurchaseOrderInfoDto.getCreateUserId())) {
				Response<UserDetailDto> userById = userDubboService.findOne(Long.parseLong(pmPurchaseOrderInfoDto.getCreateUserId()));
				if (!userById.isSuccess()) {
					log.warn("查询当前用户名失败" + userById.toString());
				}
				if (userById.isSuccess() && userById.getResultObject() != null) {
					pmPurchaseOrderInfoDto.setCreateUserName(userById.getResultObject().getUserName());
				}

			}else {
				pmPurchaseOrderInfoDto.setCreateUserName("系统创建");
			}

			pmPurchaseOrderInfoDto.setTotalNumber(totalNumber);
			response.setResultObject(pmPurchaseOrderInfoDto);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			log.info("end----supplierPmPurchaseOrderDetail");
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(Throwables.getRootCause(e).getMessage());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
}
