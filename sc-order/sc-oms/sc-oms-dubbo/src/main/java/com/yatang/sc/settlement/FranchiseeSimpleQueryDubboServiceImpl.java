package com.yatang.sc.settlement;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.datatable.TableDataResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.common.utils.MyBeanUtils;
import com.yatang.sc.dto.FranchiseeConditionDto;
import com.yatang.sc.dto.FranchiseePaymentDto;
import com.yatang.sc.dto.FranchiseeSettlementDto;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.order.domain.FranchiseePayment;
import com.yatang.sc.order.domain.FranchiseeSettlement;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.dubboservice.FranchiseeSimpleQueryDubboService;
import com.yatang.sc.order.service.FranchiseeService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.states.PayMethod;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.DataCenterScDubboService;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.dto.*;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("franchiseeSimpleQueryDubboService")
public class FranchiseeSimpleQueryDubboServiceImpl implements FranchiseeSimpleQueryDubboService {

	@Value("${queryOrder.way}")
	private int								way;
	@Value("${queryOrder.indexPageSize}")
	private int								indexPageSize;
	@Value("${queryOrder.indexSettlementPageSize}")
	private int								indexSettlementPageSize;

	@Autowired
	private FranchiseeService				franchiseeService;

	@Autowired
	private OrganizationService				organizationService;

	@Autowired
	private WarehouseLogicQueryDubboService	warehouseLogicQueryDubboService;

	@Autowired
	ProductScIndexDubboService				productScIndexDubboService;

	@Autowired
	DataCenterScDubboService				dataCenterScDubboService;

	@Autowired
	PaymentGroupService						paymentGroupService;

	@Autowired
	RefundDubboService						refundDubboService;



	@Override
	public Response<PageResult<FranchiseeSettlementDto>> queryFranchiseeSettlement(
			FranchiseeConditionDto conditionDto) {
		log.info("queryFranchiseeSettlement,param{}", JSONObject.toJSON(conditionDto));
		Response<PageResult<FranchiseeSettlementDto>> response = new Response<PageResult<FranchiseeSettlementDto>>();
		try {
			conditionDto.setPageSize(10000);
			return getFranchiseeSettlementDtoPageResult(conditionDto);
		} catch (Exception e) {
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	private Response<PageResult<FranchiseeSettlementDto>> getFranchiseeSettlementDtoPageResult(
			FranchiseeConditionDto conditionDto) throws Exception {
		if (way == 1) {
			log.info("getFranchiseeSettlementDtoPageResult from main data.");
			return getFranchiseeSettlementDtoPageResultFromMainData(conditionDto);
		}
		log.info("getFranchiseeSettlementDtoPageResult from locality.");
		return getFranchiseeSettlementDtoPageResultFromLocal(conditionDto);
	}



	private Response<PageResult<FranchiseeSettlementDto>> getFranchiseeSettlementDtoPageResultFromMainData(
			FranchiseeConditionDto conditionDto) throws Exception {
		Response<PageResult<FranchiseeSettlementDto>> response = new Response<PageResult<FranchiseeSettlementDto>>();
		PageResult<FranchiseeSettlementDto> pageResult = new PageResult<FranchiseeSettlementDto>();
		QueryOrderItemDto queryOrderItemDto = encapFranchiseeSettlementCondition(conditionDto);
		queryOrderItemDto.setPageNum(1);
		QueryOrderListDto queryDto = queryOrderItemDtoTOQueryOrderListDto(queryOrderItemDto);
		queryDto.setBranchCompanyIds(conditionDto.getBranchCompanyIds());
		queryDto.setPageSize(1);
		log.info("getFranchiseeSettlementDtoPageResult from main data condition:{}", JSON.toJSONString(queryDto));
		Response<TableDataResult<OrderListDto>> orderCountResponse = dataCenterScDubboService.queryOrderList(queryDto);
		if (orderCountResponse.getResultObject().getRecordsTotal() > indexPageSize) {
			log.info("getFranchiseeSettlementDtoPageResult from main data orderCount:{},current limit:{}",
					orderCountResponse.getResultObject().getRecordsTotal(), indexPageSize);
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_10023.getCode());
			response.setErrorMessage(String.valueOf(indexPageSize));
			return response;
		}
		log.info("getFranchiseeSettlementDtoPageResult from main data condition:{}",
				JSON.toJSONString(queryOrderItemDto));
		Response<TableDataResult<OrderItemDto>> resultResponse = dataCenterScDubboService
				.queryOrderItem(queryOrderItemDto);
		if (resultResponse == null || !resultResponse.isSuccess()) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			return response;
		}
		List<FranchiseeSettlementDto> franchiseeSettlementDtoList = BeanConvertUtils
				.convertList(resultResponse.getResultObject().getData(), FranchiseeSettlementDto.class);
		pageResult.setData(franchiseeSettlementDtoList);
		response.setResultObject(pageResult);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setSuccess(true);
		return response;
	}



	private QueryOrderListDto queryOrderItemDtoTOQueryOrderListDto(QueryOrderItemDto queryOrderItemDto) {
		QueryOrderListDto queryOrderListDto = new QueryOrderListDto();
		queryOrderListDto.setId(queryOrderItemDto.getId());
		queryOrderListDto.setCreatedByOrderId(queryOrderItemDto.getCreatedByOrderId());
		queryOrderListDto.setOrderType(queryOrderItemDto.getOrderType());
		queryOrderListDto.setOrderState(queryOrderItemDto.getOrderState());
		queryOrderListDto.setPaymentState(queryOrderItemDto.getPaymentState());
		queryOrderListDto.setShippingState(queryOrderItemDto.getShippingState());
		queryOrderListDto.setFranchiseeId(queryOrderItemDto.getFranchiseeId());
		queryOrderListDto.setBranchCompanyId(queryOrderItemDto.getBranchCompanyId());
		queryOrderListDto.setCellphone(queryOrderItemDto.getCellphone());
		queryOrderListDto.setSubmitStartTime(queryOrderItemDto.getSubmitStartTime());
		queryOrderListDto.setSubmitEndTime(queryOrderItemDto.getSubmitEndTime());
		queryOrderListDto.setCompletedTimeStart(queryOrderItemDto.getCompletedTimeStart());
		queryOrderListDto.setCompletedTimeEnd(queryOrderItemDto.getCompletedTimeEnd());
		queryOrderListDto.setPayTimeStart(queryOrderItemDto.getPayTimeStart());
		queryOrderListDto.setPayTimeEnd(queryOrderItemDto.getPayTimeEnd());
		queryOrderListDto.setRefundTimeStart(queryOrderItemDto.getRefundTimeStart());
		queryOrderListDto.setRefundTimeEnd(queryOrderItemDto.getRefundTimeEnd());
		queryOrderListDto.setProductId(queryOrderItemDto.getProductId());
		queryOrderListDto.setThirdPartOrderNo(queryOrderItemDto.getThirdPartOrderNo());
		queryOrderListDto.setPaymentStateOrQuery(queryOrderItemDto.getPaymentStateOrQuery());
		queryOrderListDto.setProductName(queryOrderItemDto.getProductName());
		queryOrderListDto.setFranchiseeStoreId(queryOrderItemDto.getFranchiseeStoreId());
		queryOrderListDto.setTransNum(queryOrderItemDto.getTransNum());
		queryOrderListDto.setContainParent(queryOrderItemDto.getContainParent());
		queryOrderListDto.setPageNum(queryOrderItemDto.getPageNum());
		return queryOrderListDto;
	}



	private QueryOrderItemDto encapFranchiseeSettlementCondition(FranchiseeConditionDto conditionDto) throws Exception {
		QueryOrderItemDto queryOrderItemDto = BeanConvertUtils.convert(conditionDto, QueryOrderItemDto.class);
		queryOrderItemDto.setPageNum(conditionDto.getPage());
		queryOrderItemDto.setId(conditionDto.getOrderId());
		queryOrderItemDto.setShippingState("YQS");
		List<String> payState = new ArrayList<String>();
		if (conditionDto.getIsPayState() != null) {
			payState.add(PaymentStates.DEBITED);
			payState.add(PaymentStates.GSN);
			payState.add(PaymentStates.RF_PENDING_APPROVE);
			payState.add(PaymentStates.RF_PENDING_CONFIRM);
		}
		if (conditionDto.getIsRefound() != null) {
			payState.add(PaymentStates.RF_COMPLETED);
		}
		queryOrderItemDto.setPaymentStateOrQuery(payState);
		queryOrderItemDto.setCompletedTimeStart(conditionDto.getCompleteDateStart());
		queryOrderItemDto.setCompletedTimeEnd(conditionDto.getCompleteDateEnd());
		queryOrderItemDto.setPayTimeStart(conditionDto.getPayDateStart());
		queryOrderItemDto.setPayTimeEnd(conditionDto.getPayDateEnd());
		queryOrderItemDto.setRefundTimeStart(conditionDto.getRefundDateStart());
		queryOrderItemDto.setRefundTimeEnd(conditionDto.getRefundDateEnd());
		return queryOrderItemDto;
	}



	private Response<PageResult<FranchiseeSettlementDto>> getFranchiseeSettlementDtoPageResultFromLocal(
			FranchiseeConditionDto conditionDto) {
		PageResult<FranchiseeSettlementDto> pageResult = new PageResult<FranchiseeSettlementDto>();
		Response<PageResult<FranchiseeSettlementDto>> response = new Response<PageResult<FranchiseeSettlementDto>>();
		conditionDto.setPage(0);
		Map<String, Object> map = MyBeanUtils.beanToMap(conditionDto);
		long totalCount = franchiseeService.queryFranchiseeSettlementCount(map);
		if (totalCount > indexPageSize) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_10023.getCode());
			response.setErrorMessage(String.valueOf(indexPageSize));
			return response;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		List<FranchiseeSettlement> list = franchiseeService.queryFranchiseeSettlementList(map);
		List<FranchiseeSettlementDto> listDto = BeanConvertUtils.convertList(list, FranchiseeSettlementDto.class);
		for (FranchiseeSettlementDto franchiseeSettlementDto : listDto) {
			Response<BranchCompanyDto> companyRep = organizationService
					.querySimpleByBranchCompanyId(franchiseeSettlementDto.getBranchCompanyId());
			if (companyRep.isSuccess() && companyRep.getResultObject() != null) {
				franchiseeSettlementDto.setBranchCompanyName(companyRep.getResultObject().getName());
			}

			Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService
					.selectLogicWarehouseByPrimaryKey(franchiseeSettlementDto.getBranchCompanyArehouseCode());
			if (logicWarehouseDtoResponse.isSuccess() && logicWarehouseDtoResponse.getResultObject() != null) {
				franchiseeSettlementDto
						.setBranchCompanyArehouseName(logicWarehouseDtoResponse.getResultObject().getWarehouseName());
			}

			Response<FranchiseeDto> repFranchiess = organizationService
					.querySimpleById(franchiseeSettlementDto.getFranchiseeId());
			if (repFranchiess.isSuccess() && repFranchiess.getResultObject() != null) {
				franchiseeSettlementDto.setFranchiseeName(repFranchiess.getResultObject().getName());
			}
			Response<ProductIndexDto> productDtoResponse = productScIndexDubboService
					.queryByProductId(franchiseeSettlementDto.getProductId());
			if (productDtoResponse.isSuccess() && productDtoResponse.getResultObject() != null) {
				ProductIndexDto productDto = productDtoResponse.getResultObject();
				franchiseeSettlementDto.setProductCode(productDto.getProductCode());
				franchiseeSettlementDto.setGroupsCode(productDto.getFirstLevelCategoryId());
				franchiseeSettlementDto.setGroupsName(productDto.getFirstLevelCategoryName());
				franchiseeSettlementDto.setDeptCode(productDto.getSecondLevelCategoryId());
				franchiseeSettlementDto.setDeptName(productDto.getSecondLevelCategoryName());
				franchiseeSettlementDto.setProductName(productDto.getSaleName());
				franchiseeSettlementDto
						.setSaleTax(productDto.getTaxRate() == null ? "" : productDto.getTaxRate().toString());
			}
			double orderDiscountShare = franchiseeSettlementDto.getOrderDiscountShare();
			double salePrice = (franchiseeSettlementDto.getAmount() - orderDiscountShare)
					/ franchiseeSettlementDto.getQuantity();
			BigDecimal sp = new BigDecimal(salePrice);
			BigDecimal qty = new BigDecimal(franchiseeSettlementDto.getCompletedQuantity());
			franchiseeSettlementDto.setCompletedAmount(df.format(qty.multiply(sp).doubleValue()));
			BigDecimal total = new BigDecimal(franchiseeSettlementDto.getTotal());
			franchiseeSettlementDto.setCompletedMulAmount(df.format(total.subtract(qty.multiply(sp)).doubleValue()));
		}
		pageResult.setData(listDto);
		response.setSuccess(true);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setResultObject(pageResult);
		return response;
	}



	@Override
	public Response<PageResult<FranchiseePaymentDto>> queryFranchiseePayment(FranchiseeConditionDto conditionDto) {

		log.info("queryFranchiseePayment,param{}", JSONObject.toJSON(conditionDto));
		Response<PageResult<FranchiseePaymentDto>> response = new Response<PageResult<FranchiseePaymentDto>>();
		try {
			conditionDto.setPageSize(10000);
			return getFranchiseePaymentDtoPageResult(conditionDto);
		} catch (Exception e) {
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	public Response<PageResult<FranchiseePaymentDto>> getFranchiseePaymentDtoPageResult(
			FranchiseeConditionDto conditionDto) throws Exception {
		if (way == 1) {
			log.info("getFranchiseePaymentDtoPageResult from main data.");
			return getFranchiseePaymentDtoPageResultFromMainData(conditionDto);
		}
		log.info("getFranchiseePaymentDtoPageResult from locality.");
		return getFranchiseePaymentDtoPageResultFromLocal(conditionDto);
	}



	private Response<PageResult<FranchiseePaymentDto>> getFranchiseePaymentDtoPageResultFromMainData(
			FranchiseeConditionDto conditionDto) throws Exception {
		Response<PageResult<FranchiseePaymentDto>> response = new Response<PageResult<FranchiseePaymentDto>>();
		PageResult<FranchiseePaymentDto> pageResult = new PageResult<FranchiseePaymentDto>();
		QueryOrderListDto queryOrderListDto = encapFranchiseePaymentCondition(conditionDto);
		queryOrderListDto.setPageNum(1);
		log.info("getFranchiseePaymentDtoPageResult from main data condition:{}", JSON.toJSONString(queryOrderListDto));
		Response<TableDataResult<OrderListDto>> resultResponse = dataCenterScDubboService
				.queryOrderList(queryOrderListDto);
		if (resultResponse == null || !resultResponse.isSuccess()) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			return response;
		}

		if (resultResponse.getResultObject().getRecordsTotal() > indexPageSize) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_10023.getCode());
			response.setErrorMessage(String.valueOf(indexPageSize));
			return response;
		}
		List<FranchiseePaymentDto> franchiseePaymentDtoList = encapFranchiseePaymentDtoFromMainDataResult(
				resultResponse.getResultObject().getData());
		pageResult.setData(franchiseePaymentDtoList);
		response.setResultObject(pageResult);
		response.setSuccess(true);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		return response;
	}



	private List<FranchiseePaymentDto> encapFranchiseePaymentDtoFromMainDataResult(List<OrderListDto> data) {
		List<FranchiseePaymentDto> franchiseePaymentDtoList = new ArrayList<FranchiseePaymentDto>();
		for (OrderListDto orderListDto : data) {
			if (!CollectionUtils.isEmpty(orderListDto.getPaymentGroupList())) {
				FranchiseePaymentDto franchiseePaymentDto = BeanConvertUtils.convert(orderListDto,
						FranchiseePaymentDto.class);
				for (OrderPaymentDto orderPaymentDto : orderListDto.getPaymentGroupList()) {
					franchiseePaymentDto.setPaymentMethod(orderPaymentDto.getPaymentMethod());
					franchiseePaymentDto.setPaymentMethodDesc(
							PayMethod.getStateByValue(orderPaymentDto.getPaymentMethod()).getDescription());
					franchiseePaymentDto.setChannel(orderPaymentDto.getChannel());
					franchiseePaymentDto.setChannelDesc(PayType.parse(orderPaymentDto.getChannel()).getName());
					franchiseePaymentDto.setPayDate(orderPaymentDto.getPayDate());
					franchiseePaymentDto.setTransNum(orderPaymentDto.getTransNum());
					franchiseePaymentDto.setAmount(orderPaymentDto.getAmount());
					franchiseePaymentDtoList.add(franchiseePaymentDto);
				}
			}
			if (!CollectionUtils.isEmpty(orderListDto.getRefundList())) {
				FranchiseePaymentDto franchiseePaymentDtoRefund = BeanConvertUtils.convert(orderListDto,
						FranchiseePaymentDto.class);
				for (OrderRefundDto orderRefundDto : orderListDto.getRefundList()) {
					franchiseePaymentDtoRefund.setRefundMethod(orderRefundDto.getPayType());
					franchiseePaymentDtoRefund
							.setRefundMethodDesc(PayType.parse(orderRefundDto.getPayType()).getName());
					franchiseePaymentDtoRefund.setRefundAmount(orderRefundDto.getRefundAmount());
					franchiseePaymentDtoRefund.setRefundDate(orderRefundDto.getRefundTime());
					franchiseePaymentDtoRefund.setRefundTradeNo(orderRefundDto.getRefundTradeNo());
					franchiseePaymentDtoList.add(franchiseePaymentDtoRefund);
				}
			}
			if (CollectionUtils.isEmpty(franchiseePaymentDtoList)) {
				FranchiseePaymentDto franchiseePaymentDto = BeanConvertUtils.convert(orderListDto,
						FranchiseePaymentDto.class);
				franchiseePaymentDtoList.add(franchiseePaymentDto);
			}
		}

		return franchiseePaymentDtoList;
	}



	private QueryOrderListDto encapFranchiseePaymentCondition(FranchiseeConditionDto conditionDto) throws Exception {

		QueryOrderListDto queryOrderListDto = BeanConvertUtils.convert(conditionDto, QueryOrderListDto.class);
		queryOrderListDto.setPageNum(conditionDto.getPage());
		queryOrderListDto.setId(conditionDto.getOrderId());
		List<String> payState = new ArrayList<String>();
		if (conditionDto.getIsPayState() != null) {
			payState.add(PaymentStates.DEBITED);
			payState.add(PaymentStates.GSN);
			payState.add(PaymentStates.RF_PENDING_APPROVE);
			payState.add(PaymentStates.RF_PENDING_CONFIRM);
		}
		if (conditionDto.getIsRefound() != null) {
			payState.add("YTK");
		}

		queryOrderListDto.setPaymentStateOrQuery(payState);
		queryOrderListDto.setCompletedTimeStart(conditionDto.getCompleteDateStart());
		queryOrderListDto.setCompletedTimeEnd(conditionDto.getCompleteDateEnd());
		queryOrderListDto.setPayTimeStart(conditionDto.getPayDateStart());
		queryOrderListDto.setPayTimeEnd(conditionDto.getPayDateEnd());
		queryOrderListDto.setRefundTimeStart(conditionDto.getRefundDateStart());
		queryOrderListDto.setRefundTimeEnd(conditionDto.getRefundDateEnd());
		return queryOrderListDto;
	}



	public Response<PageResult<FranchiseePaymentDto>> getFranchiseePaymentDtoPageResultFromLocal(
			FranchiseeConditionDto conditionDto) {
		Response<PageResult<FranchiseePaymentDto>> response = new Response<PageResult<FranchiseePaymentDto>>();
		PageResult<FranchiseePaymentDto> pageResult = new PageResult<FranchiseePaymentDto>();
		conditionDto.setPage(0);
		Map<String, Object> map = MyBeanUtils.beanToMap(conditionDto);
		long total = franchiseeService.queryFranchiseePayementCount(map);
		if (total > indexPageSize) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_10023.getCode());
			response.setErrorMessage(String.valueOf(indexPageSize));
			return response;
		}

		List<FranchiseePaymentDto> result = new ArrayList<FranchiseePaymentDto>();
		List<FranchiseePayment> list = franchiseeService.queryFranchiseePaymentList(map);
		List<FranchiseePaymentDto> listDto = BeanConvertUtils.convertList(list, FranchiseePaymentDto.class);
		for (FranchiseePaymentDto franchiseePaymentDto : listDto) {
			// franchiseePaymentDto.setRefundAmount(franchiseePaymentDto.getRefundAmount()==null?"":df.format(Double.valueOf(franchiseePaymentDto.getRefundAmount())));
			// franchiseePaymentDto.setAmount(franchiseePaymentDto.getAmount()
			// ==null?"":df.format(Double.valueOf(franchiseePaymentDto.getAmount())));
			if (!StringUtils.isEmpty(franchiseePaymentDto.getPayDate())) {
				Date payDate = new Date(Long.valueOf(franchiseePaymentDto.getPayDate()));
				franchiseePaymentDto.setPayDate(DateUtil.formatDateYMDhms(payDate));
			}
			if (!StringUtils.isEmpty(franchiseePaymentDto.getRefundDate())) {
				Date refundDate = new Date(Long.valueOf(franchiseePaymentDto.getRefundDate()));
				franchiseePaymentDto.setRefundDate(DateUtil.formatDateYMDhms(refundDate));
			}
			Response<BranchCompanyDto> companyRep = organizationService
					.querySimpleByBranchCompanyId(franchiseePaymentDto.getBranchCompanyId());
			if (companyRep.isSuccess() && companyRep.getResultObject() != null) {
				franchiseePaymentDto.setBranchCompanyName(companyRep.getResultObject().getName());
			}

			Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService
					.selectLogicWarehouseByPrimaryKey(franchiseePaymentDto.getBranchCompanyArehouseCode());
			if (logicWarehouseDtoResponse.isSuccess() && logicWarehouseDtoResponse.getResultObject() != null) {
				franchiseePaymentDto
						.setBranchCompanyArehouseName(logicWarehouseDtoResponse.getResultObject().getWarehouseName());
			}

			Response<FranchiseeDto> repFranchiess = organizationService
					.querySimpleById(franchiseePaymentDto.getFranchiseeId());
			if (repFranchiess.isSuccess() && repFranchiess.getResultObject() != null) {
				franchiseePaymentDto.setFranchiseeName(repFranchiess.getResultObject().getName());
			}
			List<FranchiseePaymentDto> paymentDtoList = getPaymentGroupList(franchiseePaymentDto.getId(),
					franchiseePaymentDto);
			result.addAll(paymentDtoList);
			List<FranchiseePaymentDto> refundDtoList = getRefundList(franchiseePaymentDto.getId(),
					franchiseePaymentDto);
			result.addAll(refundDtoList);
		}
		pageResult.setData(result);
		response.setSuccess(true);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setResultObject(pageResult);
		return response;
	}



	private List<FranchiseePaymentDto> getRefundList(String orderId, FranchiseePaymentDto franchiseePaymentDto) {
		List<FranchiseePaymentDto> franchiseeRefundList = new ArrayList<FranchiseePaymentDto>();
		Response<List<RefundDto>> refundRep = refundDubboService.getRefundByOrderId(orderId);
		if (refundRep.isSuccess() && !CollectionUtils.isEmpty(refundRep.getResultObject())) {
			for (RefundDto refundDto : refundRep.getResultObject()) {
				franchiseePaymentDto.setRefundMethod(refundDto.getPayType().getCode());
				franchiseePaymentDto.setRefundMethodDesc(refundDto.getPayType().getName());
				franchiseePaymentDto.setPayDate(refundDto.getRefundTime() != null
						? DateUtil.formatDateYMDhms(refundDto.getRefundTime()) : null);
				franchiseePaymentDto.setTransNum(refundDto.getRefundTradeNo());
				franchiseePaymentDto.setComment(refundDto.getRemark());
				franchiseeRefundList.add(franchiseePaymentDto);
			}
		}

		return franchiseeRefundList;

	}



	private List<FranchiseePaymentDto> getPaymentGroupList(String orderId, FranchiseePaymentDto franchiseePaymentDto) {

		List<FranchiseePaymentDto> franchiseePaymentDtoList = new ArrayList<FranchiseePaymentDto>();
		List<PaymentGroup> paymentGroupList = paymentGroupService.getPaymentGroupForOrderId(orderId);
		for (PaymentGroup paymentGroup : paymentGroupList) {
			franchiseePaymentDto.setPayDate(
					paymentGroup.getPayDate() != null ? DateUtil.formatDateYMDhms(paymentGroup.getPayDate()) : null);
			franchiseePaymentDto.setPaymentMethod(paymentGroup.getPaymentMethod());
			franchiseePaymentDto
					.setPaymentMethodDesc(PayMethod.getStateByValue(paymentGroup.getPaymentMethod()).getDescription());
			franchiseePaymentDto.setChannel(paymentGroup.getChannel());
			franchiseePaymentDto.setChannelDesc(PayType.parse(paymentGroup.getChannel()).getName());
			franchiseePaymentDto
					.setAmount(paymentGroup.getAmount() != null ? paymentGroup.getAmount().toString() : null);
			franchiseePaymentDto.setTransNum(paymentGroup.getTransNum());
			franchiseePaymentDto.setComment(paymentGroup.getComment());
			franchiseePaymentDtoList.add(franchiseePaymentDto);
		}
		return franchiseePaymentDtoList;
	}
}
