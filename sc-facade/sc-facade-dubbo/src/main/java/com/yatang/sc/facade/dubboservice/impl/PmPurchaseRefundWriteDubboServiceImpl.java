package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.idgenerator.IdGenerator;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Strings;
import com.yatang.sc.bpm.dubboservice.WorkFlowDubboService;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.facade.domain.pm.PmPurchaseRefundItemPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseRefundPo;
import com.yatang.sc.facade.domain.pm.PurchaseActualItem;
import com.yatang.sc.facade.domain.pm.PurchaseSettlementData;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.SupplierPlaceDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundItemDto;
import com.yatang.sc.facade.dto.supplier.SpAdrBasicDto;
import com.yatang.sc.facade.dto.supplier.SpAdrContactDto;
import com.yatang.sc.facade.dto.supplier.SupplierlicenseInfoDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundWriteDubboService;
import com.yatang.sc.facade.dubboservice.ProcessDefinitionQueryDubboService;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.facade.service.PmpurchaseRefundItemService;
import com.yatang.sc.facade.service.PmpurchaseRefundService;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.TranDataDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundOrderLinesDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundStockoutCreateDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnReceiverGetInfoDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnReceiverInfoDto;
import com.yatang.sc.kidd.service.KiddPurchaseReturnService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/10/17 15:02
 * @version: v1.0
 */
@Service("pmPurchaseRefundWriteDubboService")
public class PmPurchaseRefundWriteDubboServiceImpl implements PmPurchaseRefundWriteDubboService {


	private static final Integer REFUND_NO_PRE_FIX =9;
	@Autowired
	private PmpurchaseRefundService				pmPurchaseRefundService;
	@Autowired
	private PmpurchaseRefundItemService			pmpurchaseRefundItemService;
	@Autowired
	private ItemLocInventoryDubboService		itemLocInventoryDubboService;
	@Autowired
	private ProcessDefinitionQueryDubboService	processDefinitionQueryDubboService;
	@Autowired
	private KiddPurchaseReturnService			kiddPurchaseReturnService;
	@Autowired
	private SupplierQueryDubboService			supplierQueryDubboService;
	@Autowired
	private IdGenerator							idGenerator;
	@Autowired
	private OrganizationService 				organizationService;
	// 商品服务
	@Autowired
	private ProductScIndexDubboService 			indexDubboService;
	@Autowired
	private WarehouseLogicQueryDubboService		warehouseLogicQueryDubboService;
	@Autowired
	private WorkFlowDubboService 				workFlowDubboService;


	@Resource(name = "purcharseSettlementMQProducer")
	private SimpleMQProducer 					purchaseSettlementMQProducer;

	private static final Logger					log	= LoggerFactory
			.getLogger(PmPurchaseRefundWriteDubboServiceImpl.class);



	@Override
	public Response<String> getRefundNo() {
		Response<String> response = new Response<>();
		String dateFormat = "yyyyMMdd";// 1.前缀格式日期yyyyMMdd
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);// 格式化
		String dateFormatStr = sdf.format(new Date());
		String refundNo = idGenerator
				.generateScReturnPurchaseOrderId(REFUND_NO_PRE_FIX +dateFormatStr.substring(2, dateFormatStr.length()));
		if (!Strings.isNullOrEmpty(refundNo)) {
			response.setResultObject(refundNo);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			return response;
		} else {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage("调用主数据生成退货单号失败");
			response.setSuccess(false);
			return response;
		}
	}



	@Override
	public Response<Boolean> updateStatus(PmPurchaseRefundDto param) {
		Response<Boolean> response = new Response<Boolean>();
		PmPurchaseRefundPo pmPurchaseRefundPo = BeanConvertUtils.convert(param, PmPurchaseRefundPo.class);
		if (pmPurchaseRefundService.updateStatus(pmPurchaseRefundPo)) {
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			return response;
		} else {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			return response;
		}
	}



	@Override
	public Response<Boolean> saveRefundWithItems(PmPurchaseRefundDto pmPurchaseRefundDto) {
		log.info("----------保存采购退回单>>saveRefundWithItems(),param=" + JSONObject.toJSONString(pmPurchaseRefundDto)
				+ "----------");
		Response<Boolean> response = new Response<>();
		if (pmPurchaseRefundDto.getStatus() != 0 && pmPurchaseRefundDto.getStatus() != 1) {
			response.setResultObject(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage("此次操作只允许保存或者提交退货单");
			return response;
		}
		try {
			// 校验是否有清单，没有则拒绝新建
			if (null != pmPurchaseRefundDto.getPmPurchaseRefundItems()
					&& pmPurchaseRefundDto.getPmPurchaseRefundItems().size() > 0) {
				PmPurchaseRefundPo pmPurchaseRefundPo = BeanConvertUtils.convert(pmPurchaseRefundDto,
						PmPurchaseRefundPo.class);
				int totalRefundAmount = 0;
				BigDecimal totalRefundMoney = new BigDecimal(0.00);
				BigDecimal totalRefunCost = new BigDecimal(0.00);
				Map<String, Integer> map = new HashMap<>();
				for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo : pmPurchaseRefundPo.getPmPurchaseRefundItems()) {
					// 校验退货数目是否超过可退库存数
					if (map.containsKey(pmPurchaseRefundItemPo.getProductCode())) {
						Integer refundAmount = map.get(pmPurchaseRefundItemPo.getProductCode())
								+ pmPurchaseRefundItemPo.getRefundAmount();
						map.put(pmPurchaseRefundItemPo.getProductCode(), refundAmount);
					} else {
						map.put(pmPurchaseRefundItemPo.getProductCode(), pmPurchaseRefundItemPo.getRefundAmount());
					}

					totalRefundAmount += pmPurchaseRefundItemPo.getRefundAmount();
					pmPurchaseRefundItemPo.setRefundMoney(pmPurchaseRefundItemPo.getPurchasePrice()
							.multiply(new BigDecimal(pmPurchaseRefundItemPo.getRefundAmount())));
					totalRefundMoney = totalRefundMoney.add(pmPurchaseRefundItemPo.getRefundMoney());
					pmPurchaseRefundItemPo.setRefundCost((new BigDecimal(pmPurchaseRefundItemPo.getAvCost()))
							.multiply(new BigDecimal(pmPurchaseRefundItemPo.getRefundAmount())));
					totalRefunCost = totalRefunCost.add(pmPurchaseRefundItemPo.getRefundCost());
				}
				pmPurchaseRefundPo.setTotalRefundMoney(totalRefundMoney);
				pmPurchaseRefundPo.setTotalRefundAmount(totalRefundAmount);
				pmPurchaseRefundPo.setTotalRefundCost(totalRefunCost);
				// 只有仓库退货才会涉及库存相关信息
				if (pmPurchaseRefundDto.getAdrType() == 0) {
					for (PmPurchaseRefundItemDto pmPurchaseRefundItemDto : pmPurchaseRefundDto
							.getPmPurchaseRefundItems()) {
						Integer refundAmount = map.get(pmPurchaseRefundItemDto.getProductCode());
						if (refundAmount > pmPurchaseRefundItemDto.getPossibleNum()) {
							throw new RuntimeException(
									"商品code:" + pmPurchaseRefundItemDto.getProductCode() + "合计退货数目超过可退库存数，操作终止");
						}
					}

					if (pmPurchaseRefundPo.getStatus() == 1) {
						// 如果是提交操作，则需要更新商品退货预留
						List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos = BeanConvertUtils.convertList(
								pmPurchaseRefundPo.getPmPurchaseRefundItems(), ItemInventoryQueryParamDto.class);
						for (ItemInventoryQueryParamDto itemInventoryQueryParamDto : itemInventoryQueryParamDtos) {
							itemInventoryQueryParamDto.setLogicWareHouseCode(pmPurchaseRefundPo.getRefundAdrCode());
						}
						Response<Boolean> response1 = itemLocInventoryDubboService
								.updateBatchRtvQty(itemInventoryQueryParamDtos);
						if (!"200".equals(response1.getCode())) {
							throw new RuntimeException(response1.getErrorMessage());
						}

					}
				}
				//流程启动人id
				String commitUserId;
				if (null == pmPurchaseRefundDto.getId() || 0 == pmPurchaseRefundDto.getId()) {
					pmPurchaseRefundService.createRefundWithItems(pmPurchaseRefundPo);
					commitUserId=pmPurchaseRefundPo.getCreateUserId();
				} else {
					pmPurchaseRefundService.updateRefundWithItems(pmPurchaseRefundPo);
					commitUserId=pmPurchaseRefundPo.getModifyUserId();
				}
				//提交操作才启动审批流
				if (pmPurchaseRefundPo.getStatus()==1){
					log.info("采购退货单调用工作流启动审批,参数{}",JSONObject.toJSONString(pmPurchaseRefundPo));
					Response<Boolean> workFlowResponse = workFlowDubboService.saveStartProcess(pmPurchaseRefundPo.getId(), 1,
																		Integer.valueOf(pmPurchaseRefundPo.getBranchCompanyId()),commitUserId);
					log.info("采购退货单调用工作流启动审批,调用结果{}",JSONObject.toJSONString(workFlowResponse));
					if (!workFlowResponse.isSuccess()){
						//启动失败，则将单子设置为制单状态，并提示用户
						log.info("采购退货单启动审批流失败,原因:{}"+JSONObject.toJSONString(workFlowResponse));
						pmPurchaseRefundPo.setStatus(0);
						pmPurchaseRefundService.updateRefundWithItems(pmPurchaseRefundPo);
						response.setErrorMessage("启动审批失败，单子退回为制单状态");
						response.setCode(CommonsEnum.RESPONSE_500.getCode());
						response.setSuccess(false);
						response.setResultObject(false);
						return response;
					}
				}

				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(true);
			} else {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + ":退货单清单为空");
				response.setResultObject(false);
			}
		} catch (Exception e) {
			log.error("----------保存采购退回单>>createRefundWithItems(),error=" + ExceptionUtils.getFullStackTrace(e)
					+ "----------");
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
			response.setResultObject(false);
		}
		return response;
	}



	@Override
	public Response<Boolean> deleteBatchRefundOrder(Long[] purchaseRefundIds) {
		log.info("----------批量删除采购退货单>>deleteBatchRefundOrder(),param=" + JSONObject.toJSONString(purchaseRefundIds)
				+ "----------");
		Response<Boolean> response = new Response<Boolean>();
		try {
			// 校验是否有非制单的退货单号,有非制单的打回修改
			if (!pmPurchaseRefundService.queryContainUndraftRefundOrder(purchaseRefundIds)) {
				pmPurchaseRefundService.deleteBatchRefundOrder(purchaseRefundIds);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(true);
			} else {
				response.setCode(CommonsEnum.RESPONSE_10026.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_10026.getName() + ":退货单删除失败。原因：只能删除制单状态且无审批记录退货单");
				response.setResultObject(false);
			}
		} catch (Exception e) {
			log.error("---------批量删除采购退货单>>deleteBatchRefundOrder(),error=" + ExceptionUtils.getFullStackTrace(e)
					+ "----------");
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
			response.setResultObject(false);
		}
		return response;
	}



	/*
	@Override
	public Response<Boolean> approveRefund(ProcessAuditLogParamDto processAuditLogParamDto) {
		if (log.isInfoEnabled()) {
			log.info("----- 审批采购退货单>> approveRefund(): param=" + JSONObject.toJSONString(processAuditLogParamDto)
					+ "-----");
		}
		Response<Boolean> response = new Response<>();
		try {
			if (processAuditLogParamDto.getType() != 1) {
				throw new RuntimeException("非采购退货单审批，操作失败");
			}
			PmPurchaseRefundPo pmPurchaseRefundPo = pmPurchaseRefundService
					.selectRefundDetailById(processAuditLogParamDto.getBusinessId());
			if (pmPurchaseRefundPo == null) {
				throw new RuntimeException("采购退货单查询失败");
			}
			// 1：处理审批
			Response<Long> nextProcessNodeCodeResponse = processDefinitionQueryDubboService
					.getNextProcessNodeCode(processAuditLogParamDto);
			if (!nextProcessNodeCodeResponse.isSuccess()) {
				throw new RuntimeException("审批失败,请查询后台日志");
			}
			// 1：审批成功，进行后续业务
			pmPurchaseRefundPo.setAuditTime(new Date());
			pmPurchaseRefundPo.setAuditUserId(processAuditLogParamDto.getAuditUser());
			Integer status = 0;
			if (nextProcessNodeCodeResponse.getResultObject() != null) {
				// 还有下一审批节点
				pmPurchaseRefundPo.setProcessId(nextProcessNodeCodeResponse.getResultObject());
				pmPurchaseRefundService.updateRefund(pmPurchaseRefundPo);
			} else {
				status = processAuditLogParamDto.getAuditResult() == 0 ? 3 : 2;
				if (status == 2 && pmPurchaseRefundPo.getAdrType() == 1) {
					// 门店的单子审批完直接变成待退货
					status = 4;
				}
				pmPurchaseRefundService.endApproveProcess(pmPurchaseRefundPo.getId(),
						pmPurchaseRefundPo.getAuditUserId(), status);
			}
			if (status == 3 && pmPurchaseRefundPo.getAdrType() == 0) {
				// 回退退货预留
				List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos = new ArrayList<>();
				for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo : pmPurchaseRefundPo.getPmPurchaseRefundItems()) {
					// 修改释放退货预留
					ItemInventoryQueryParamDto itemInventoryQueryParamDto = new ItemInventoryQueryParamDto();
					itemInventoryQueryParamDto.setRefundAmount(-pmPurchaseRefundItemPo.getRefundAmount());
					itemInventoryQueryParamDto.setLogicWareHouseCode(pmPurchaseRefundPo.getRefundAdrCode());
					itemInventoryQueryParamDto.setProductCode(pmPurchaseRefundItemPo.getProductCode());
					itemInventoryQueryParamDto.setProductId(pmPurchaseRefundItemPo.getProductId());
					itemInventoryQueryParamDtos.add(itemInventoryQueryParamDto);
				}
				Response<Boolean> inventoryResponse = itemLocInventoryDubboService
						.updateBatchRtvQty(itemInventoryQueryParamDtos);
				if (!inventoryResponse.isSuccess()) {
					throw new RuntimeException("更新库存失败:" + inventoryResponse.getErrorMessage());
				}

			}
			if (status == 2 && pmPurchaseRefundPo.getAdrType() == 0) {
				// 下发退货单
				if (!createPurchaseRefundHelper(pmPurchaseRefundPo)) {
					// 更新单子为异常状态
					pmPurchaseRefundService
							.updatePurchaseRefundStatusByPurchaseRefundNo(pmPurchaseRefundPo.getPurchaseRefundNo(), 8);
				}
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error("-----审批采购退货单>> approveRefund(),error=" + ExceptionUtils.getFullStackTrace(e) + "----------");
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
		}
		return response;
	}
	*/


	@Override
	public Response<Boolean> refundSuccessCallBack(PmPurchaseRefundDto pmPurchaseRefundDto) {
		if (log.isInfoEnabled()) {
			log.info("----- 退货成功，回调修改采购单>> refundSuccessCallBack(): param="
					+ JSONObject.toJSONString(pmPurchaseRefundDto) + "-----");
		}
		Response<Boolean> response = new Response<>();
		try {
			// 查询退货单及清单
			PmPurchaseRefundPo pmPurchaseRefundPo = pmPurchaseRefundService
					.selectPurchaseRefundByPurchaseRefundNo(pmPurchaseRefundDto.getPurchaseRefundNo());
			if (pmPurchaseRefundPo == null) {
				throw new RuntimeException("采购退货单查询为空，操作终止");
			}
			if (4!=pmPurchaseRefundPo.getStatus()){
				throw new RuntimeException("采购退货单状态有误，操作终止");
			}
			List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos = pmPurchaseRefundPo.getPmPurchaseRefundItems();
			if (pmPurchaseRefundItemPos == null || pmPurchaseRefundItemPos.size() <= 0) {
				throw new RuntimeException("采购退货单清单查询为空，操作终止");
			}

			List<PmPurchaseRefundItemDto> pmPurchaseRefundItemDtos = pmPurchaseRefundDto.getPmPurchaseRefundItems();
			if (pmPurchaseRefundItemDtos == null || pmPurchaseRefundItemDtos.size() <= 0) {
				throw new RuntimeException("采购退货单,回传数据中清单为空，操作终止");
			}

			int totalRealRefundAmount = 0; // 合计实际退货总数
			BigDecimal totalRealRefundMoney = new BigDecimal(0.00); // 合计实际退货总金额
			List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos = new ArrayList<>();
			List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPosForUpdate = new ArrayList<>();
			List<TranDataDto> tranDataDtos = new ArrayList<>();
			for (PmPurchaseRefundItemDto pmPurchaseRefundItemDto : pmPurchaseRefundItemDtos) {
				int realRefundAmount = pmPurchaseRefundItemDto.getRealRefundAmount() == null ? 0
						: pmPurchaseRefundItemDto.getRealRefundAmount();
				PmPurchaseRefundItemPo pmPurchaseRefundItemPo = pmpurchaseRefundItemService
						.selectByPrimaryKey(pmPurchaseRefundItemDto.getId());
				if (pmPurchaseRefundItemPo.getRefundAmount() < realRefundAmount) {
					throw new RuntimeException(
							"采购退货商品实际退货数量超过计划退货数量:code--实际数--计划数:" + pmPurchaseRefundItemPo.getProductCode() + "--"
									+ realRefundAmount + "--" + pmPurchaseRefundItemPo.getRefundAmount());
				}
				pmPurchaseRefundItemPo.setRealRefundAmount(realRefundAmount);
				pmPurchaseRefundItemPo.setRealRefundMoney(
						pmPurchaseRefundItemPo.getPurchasePrice().multiply(new BigDecimal(realRefundAmount)));
				totalRealRefundAmount += pmPurchaseRefundItemPo.getRealRefundAmount();
				totalRealRefundMoney = totalRealRefundMoney.add(pmPurchaseRefundItemPo.getRealRefundMoney());
				// 修改退货单清单里的实际退货数量
				pmPurchaseRefundItemPosForUpdate.add(pmPurchaseRefundItemPo);

				// 修改库存,退货成功：释放退货预留，减少库存
				ItemInventoryQueryParamDto itemInventoryQueryParamDto = new ItemInventoryQueryParamDto();
				itemInventoryQueryParamDto.setRealRefundAmount(pmPurchaseRefundItemDto.getRealRefundAmount());
				itemInventoryQueryParamDto.setRefundAmount(-pmPurchaseRefundItemPo.getRefundAmount());
				itemInventoryQueryParamDto.setLogicWareHouseCode(pmPurchaseRefundPo.getRefundAdrCode());
				itemInventoryQueryParamDto.setProductCode(pmPurchaseRefundItemPo.getProductCode());
				itemInventoryQueryParamDto.setProductId(pmPurchaseRefundItemPo.getProductId());

				// 查询移动平均加权成本
				Response<List<ItemLocSohDto>> listResponse = itemLocInventoryDubboService
						.queryItemInventoryListByParam(itemInventoryQueryParamDto);
				if (listResponse == null || listResponse.getResultObject() == null
						|| listResponse.getResultObject().size() <= 0) {
					throw new RuntimeException("采购退货单,查询库存记录为空，操作终止");
				}
				Double avCost = listResponse.getResultObject().get(0).getAvCost();

				itemInventoryQueryParamDtos.add(itemInventoryQueryParamDto);
				// 新增tran-data表记录(24)
				TranDataDto tranDataDto = new TranDataDto();
				tranDataDto.setItem(itemInventoryQueryParamDto.getProductId());
				tranDataDto.setLocation(itemInventoryQueryParamDto.getLogicWareHouseCode());
				tranDataDto.setVatRate(pmPurchaseRefundItemPo.getInputTaxRate() + "");
				tranDataDto.setUnits(pmPurchaseRefundItemPo.getRealRefundAmount());
				BigDecimal inputTaxRate = (null == pmPurchaseRefundItemPo.getInputTaxRate() ? new BigDecimal(0.00)
						: pmPurchaseRefundItemPo.getInputTaxRate());
				tranDataDto.setVatRate(inputTaxRate.toString());
				// 总成本:交易数量*退单上的商品成本
				tranDataDto.setTotalCost(
						tranDataDto.getUnits() * (pmPurchaseRefundItemPo.getPurchasePrice().doubleValue()));
				tranDataDto.setRefNo1(pmPurchaseRefundDto.getPurchaseRefundNo());
				tranDataDto.setTranCode("24");

				// 新增tran-data表记录(72)
				TranDataDto tranDataDto2 = BeanConvertUtils.convert(tranDataDto, TranDataDto.class);
				// 总成本:退货数量*（当前WAC-退货成本）
				tranDataDto2.setTotalCost(
						tranDataDto.getUnits() * (avCost - pmPurchaseRefundItemPo.getPurchasePrice().doubleValue()));
				tranDataDto2.setTranCode("72");
				tranDataDtos.add(tranDataDto);
				tranDataDtos.add(tranDataDto2);
			}
			if (pmPurchaseRefundItemPosForUpdate != null && pmPurchaseRefundItemPosForUpdate.size() > 0) {
				pmpurchaseRefundItemService.batchUpdate(pmPurchaseRefundItemPosForUpdate);
			}
			Response<Boolean> inventoryResponse = itemLocInventoryDubboService
					.updateBatchStockOnHandByRtvQty(itemInventoryQueryParamDtos, tranDataDtos);
			if (!inventoryResponse.isSuccess()) {
				throw new RuntimeException("更新库存失败:" + inventoryResponse.getErrorMessage());
			}

			// 更新退货单状态为已退货
			pmPurchaseRefundPo.setStatus(5);
			pmPurchaseRefundPo.setRefundTime(pmPurchaseRefundDto.getRefundTime());
			pmPurchaseRefundPo.setTotalRealRefundAmount(totalRealRefundAmount);
			pmPurchaseRefundPo.setTotalRealRefundMoney(totalRealRefundMoney);
			pmPurchaseRefundService.updateRefundDetail(pmPurchaseRefundPo);

			PmPurchaseRefundPo refundPo = pmPurchaseRefundService.selectRefundDetailById(pmPurchaseRefundPo.getId());
			PmPurchaseRefundDto refundDto = BeanConvertUtils.convert(refundPo, PmPurchaseRefundDto.class);
			refundDto.setDeliveryVoucherNo(pmPurchaseRefundDto.getDeliveryVoucherNo());
			refundDto.setLogisticsWarehouseCode(pmPurchaseRefundDto.getLogisticsWarehouseCode());
			// 状态为已退货
			if (5 == refundPo.getStatus()) {
				// 发送采购退货数据给财务中心做结算
				pushPurchaseRefundSettlementMsg(refundDto);
			}

			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(true);
		} catch (Exception e) {
			log.error("-----退货成功，回调修改采购单>> refundSuccessCallBack(),error=" + ExceptionUtils.getFullStackTrace(e)
					+ "----------");
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
		}
		return response;

	}


	/**
	 * @Description: 封装采购退货结算数据（财务中心用）
	 * @author tankejia
	 * @date 2017/12/11- 18:25
	 * @param refundDto
	 */
	private PurchaseSettlementData buildRefundSettlementData(PmPurchaseRefundDto refundDto){

		PmPurchaseRefundPo refundPo = pmPurchaseRefundService
				.selectPurchaseRefundByPurchaseRefundNo(refundDto.getPurchaseRefundNo());

		Response<BranchCompanyDto> companyDtoResponse = organizationService.querySimpleByBranchCompanyId(refundPo.getBranchCompanyId());
		if (!companyDtoResponse.isSuccess() || null == companyDtoResponse.getResultObject()) {
			log.error("根据分公司id查询分公司信息出错，分公司id：{}", refundPo.getBranchCompanyId());
			throw new RuntimeException("根据分公司id查询分公司信息出错，操作终止");
		}

		PurchaseSettlementData settlementData = new PurchaseSettlementData();
		StringBuffer sb = new StringBuffer(CommonsEnum.RESPONSE_20305.getName());
		settlementData.setId(sb.append(refundPo.getId()).toString());
		settlementData.setSubCompId(companyDtoResponse.getResultObject().getId());
		settlementData.setSubCompName(companyDtoResponse.getResultObject().getName());
		settlementData.setLocaleNo(StringUtils.isBlank(refundPo.getRefundAdrCode()) ? "" : refundPo.getRefundAdrCode());
		settlementData.setLocaleName(StringUtils.isBlank(refundPo.getRefundAdrName()) ? "" : refundPo.getRefundAdrName());
		// 第三方仓库目前没有传交货单号过来(2017.12.11)
		settlementData.setDeliveryVoucherNo(StringUtils.isBlank(refundDto.getDeliveryVoucherNo())
				? "" : refundDto.getDeliveryVoucherNo());
		settlementData.setWhVoucherNo(StringUtils.isBlank(refundPo.getPurchaseRefundNo()) ? "" : refundPo.getPurchaseRefundNo());
		settlementData.setPoNo(StringUtils.isBlank(refundPo.getPurchaseRefundNo()) ? "" : refundPo.getPurchaseRefundNo());
		settlementData.setLogWhRecNo(StringUtils.isBlank(refundDto.getLogisticsWarehouseCode())
				? "" : refundDto.getLogisticsWarehouseCode());
		settlementData.setOrderTypeNo(CommonsEnum.RESPONSE_20303.getCode());
		settlementData.setOrderType(CommonsEnum.RESPONSE_20303.getName());
		settlementData.setWhInTypeNo(CommonsEnum.RESPONSE_20061.getCode());
		settlementData.setWhInType(CommonsEnum.RESPONSE_20061.getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		settlementData.setWhInDate(null == refundPo.getRefundTime() ? null : sdf.format(refundPo.getRefundTime()));
		settlementData.setVendorNo(StringUtils.isBlank(refundPo.getSpAdrNo()) ? "" : refundPo.getSpAdrNo());
		settlementData.setVendorName(StringUtils.isBlank(refundPo.getSpAdrName()) ? "" : refundPo.getSpAdrName());
		settlementData.setPurchaseActualItems(buildRefundActualItems(refundDto));
		return settlementData;
	}

	private List<PurchaseActualItem> buildRefundActualItems(PmPurchaseRefundDto refundDto){
		List<PurchaseActualItem> actualItems = new ArrayList<>();
		// 根据采购退货单id查询退货单item
		List<PmPurchaseRefundItemPo> refundItemPos = pmpurchaseRefundItemService.selectRefundItemsByRefundId(refundDto.getId());
		if (CollectionUtils.isEmpty(refundItemPos)) {
			log.error("该退货单下面没有相应的商品, 退货单号：{}", refundDto.getPurchaseRefundNo());
			throw new RuntimeException("该退货单下面没有相应的商品，操作终止");
		}

		for (PmPurchaseRefundItemPo refundItemPo : refundItemPos) {
			PurchaseActualItem actualItem = new PurchaseActualItem();

			// 查询商品分类信息
			Response<ProductIndexDto> dtoResponse = indexDubboService.queryByProductId(refundItemPo.getProductId());
			if (!dtoResponse.isSuccess() || null == dtoResponse.getResultObject()) {
				log.error("退货结算时根据商品id查询不到相应的分类信息，商品id：{}", refundItemPo.getProductId());
				throw new RuntimeException("退货结算时根据商品id查询不到相应的分类信息，操作终止");
			}

			// 如果没有四级分类则取三级分类
			actualItem.setGoodsTypeNo(null == dtoResponse.getResultObject().getFourthCategoryId()
					? dtoResponse.getResultObject().getThirdLevelCategoryId() : dtoResponse.getResultObject().getFourthCategoryId());
			actualItem.setGoodsType(null == dtoResponse.getResultObject().getFourthLevelCategoryName()
					? dtoResponse.getResultObject().getThirdLevelCategoryName() : dtoResponse.getResultObject().getFourthLevelCategoryName());
			actualItem.setGoodsNo(refundItemPo.getProductCode());
			actualItem.setGoodsName(refundItemPo.getProductName());
			// 设置出库数量为负数
			actualItem.setInQty(null == refundItemPo.getRealRefundAmount() ? "" : "-".concat(refundItemPo.getRealRefundAmount().toString()));
			actualItem.setDealerprice(PricingUtil.roundPrice(refundItemPo.getPurchasePrice().doubleValue()));
			// 退货金额（含税）= 采购价格（含税）* 出库数量 （采购退货时设置退货金额为负数）
			actualItem.setPurchaseAmt(PricingUtil.roundPrice(Double.valueOf(actualItem.getInQty())
					*refundItemPo.getPurchasePrice().doubleValue()));
			actualItem.setTaxRate(PricingUtil.roundPrice(refundItemPo.getInputTaxRate().doubleValue()/100));
			// 未税金额 = 退货金额（含税）/ （1 + 税率）（采购退货时设置退货金额为负数）
			actualItem.setOutTaxAmt(PricingUtil.roundPrice(actualItem.getPurchaseAmt() / (1 + actualItem.getTaxRate())));
			// 税额 = 进货金额（含税）- 未税金额
			actualItem.setTaxAmt(PricingUtil.roundPrice(actualItem.getPurchaseAmt() - actualItem.getOutTaxAmt()));
			actualItems.add(actualItem);
		}
		return actualItems;
	}



	public Boolean createPurchaseRefundHelper(PmPurchaseRefundPo pmPurchaseRefundPo) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		KiddPurchaseRefundStockoutCreateDto kiddPurchaseRefundStockoutCreateDto = new KiddPurchaseRefundStockoutCreateDto();

		KiddPurchaseReturnDto kiddPurchaseReturnDto = new KiddPurchaseReturnDto();
		kiddPurchaseReturnDto.setCreateTime(simpleDateFormat.format(pmPurchaseRefundPo.getCreateTime()));
		kiddPurchaseReturnDto.setDeliveryOrderCode(pmPurchaseRefundPo.getPurchaseRefundNo());
		kiddPurchaseReturnDto.setOrderType("CGTH");
		// 子公司id需要用跟仓库约定好的编码，而不是我们雅堂的子公司编码
		Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService
				.selectLogicWarehouseByPrimaryKey(pmPurchaseRefundPo.getRefundAdrCode());
		if (!logicWarehouseDtoResponse.isSuccess() || logicWarehouseDtoResponse.getResultObject() == null) {
			throw new RuntimeException("查询逻辑仓失败，操作终止");
		}
		kiddPurchaseReturnDto.setOwnerCode(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode());
		kiddPurchaseReturnDto.setWarehouseCode(pmPurchaseRefundPo.getRefundAdrCode());

		// 查询供应商地点的信息
		Response<SupplierPlaceDto> supplierPlaceDtoResponse = supplierQueryDubboService
				.queryProviderPlaceInfo(Integer.valueOf(pmPurchaseRefundPo.getSpAdrId()));
		if (supplierPlaceDtoResponse.getResultObject() == null) {
			throw new RuntimeException("供应商地点信息查询失败:" + supplierPlaceDtoResponse.getErrorMessage());
		}

		SpAdrBasicDto spAdrBasicDto = supplierPlaceDtoResponse.getResultObject().getSupplierAdrInfo().getSpAdrBasic();
		SpAdrContactDto spAdrContact = supplierPlaceDtoResponse.getResultObject().getSupplierAdrInfo()
				.getSpAdrContact();
		SupplierlicenseInfoDto supplierlicenseInfo = supplierPlaceDtoResponse.getResultObject().getSupplierInfo()
				.getSupplierlicenseInfo();

		// 接收人信息--心怡
		KiddPurchaseReturnReceiverInfoDto kiddPurchaseReturnReceiverInfoDto = new KiddPurchaseReturnReceiverInfoDto();
		kiddPurchaseReturnReceiverInfoDto.setCompany(spAdrBasicDto.getProviderName());
		kiddPurchaseReturnReceiverInfoDto.setName(spAdrContact.getProviderName());
		kiddPurchaseReturnReceiverInfoDto.setMobile(spAdrContact.getProviderPhone());
		kiddPurchaseReturnReceiverInfoDto.setEmail(spAdrContact.getProviderEmail());
		kiddPurchaseReturnReceiverInfoDto.setProvince(supplierlicenseInfo.getLicenseLocProvince());
		kiddPurchaseReturnReceiverInfoDto.setCity(supplierlicenseInfo.getLicenseLocCity());
		kiddPurchaseReturnReceiverInfoDto.setArea(supplierlicenseInfo.getLicenseLocCounty());
		kiddPurchaseReturnReceiverInfoDto.setDetailAddress(supplierlicenseInfo.getLicenseAddress());
		kiddPurchaseReturnDto.setReceiverInfo(kiddPurchaseReturnReceiverInfoDto);
		// 接收人信息--际链
		KiddPurchaseReturnReceiverGetInfoDto kiddPurchaseReturnReceiverGetInfoDto = new KiddPurchaseReturnReceiverGetInfoDto();
		kiddPurchaseReturnReceiverGetInfoDto.setGetCompany(spAdrBasicDto.getProviderName());
		kiddPurchaseReturnReceiverGetInfoDto.setGetName(spAdrContact.getProviderName());
		kiddPurchaseReturnReceiverGetInfoDto.setGetMobile(spAdrContact.getProviderPhone());
		kiddPurchaseReturnReceiverGetInfoDto.setGetEmail(spAdrContact.getProviderEmail());
		kiddPurchaseReturnReceiverGetInfoDto.setGetProvince(supplierlicenseInfo.getLicenseLocProvince());
		kiddPurchaseReturnReceiverGetInfoDto.setGetCity(supplierlicenseInfo.getLicenseLocCity());
		kiddPurchaseReturnReceiverGetInfoDto.setGetArea(supplierlicenseInfo.getLicenseLocCounty());
		kiddPurchaseReturnReceiverGetInfoDto.setGetDetailAddress(supplierlicenseInfo.getLicenseAddress());
		kiddPurchaseReturnDto.setGetInfo(kiddPurchaseReturnReceiverGetInfoDto);
		// 货品详情
		List<KiddPurchaseRefundOrderLinesDto> orderLines = new ArrayList<>();
		if (pmPurchaseRefundPo.getPmPurchaseRefundItems() == null
				|| pmPurchaseRefundPo.getPmPurchaseRefundItems().size() <= 0) {
			throw new RuntimeException("采购退货单商品为空，操作失败");
		}
		for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo : pmPurchaseRefundPo.getPmPurchaseRefundItems()) {
			KiddPurchaseRefundOrderLinesDto kiddPurchaseRefundOrderLinesDto = new KiddPurchaseRefundOrderLinesDto();
			kiddPurchaseRefundOrderLinesDto.setOrderLineNo(pmPurchaseRefundItemPo.getId() + "");
			kiddPurchaseRefundOrderLinesDto.setItemCode(pmPurchaseRefundItemPo.getProductCode());
			kiddPurchaseRefundOrderLinesDto.setItemName(pmPurchaseRefundItemPo.getProductName());
			kiddPurchaseRefundOrderLinesDto.setPlanQty(pmPurchaseRefundItemPo.getRefundAmount());
			kiddPurchaseRefundOrderLinesDto
					.setOwnerCode(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode());
			kiddPurchaseRefundOrderLinesDto.setUnit(pmPurchaseRefundItemPo.getUnitExplanation());
			orderLines.add(kiddPurchaseRefundOrderLinesDto);
		}
		kiddPurchaseReturnDto.setOrderLines(orderLines);// 际链
		kiddPurchaseRefundStockoutCreateDto.setOrderLines(orderLines);
		kiddPurchaseRefundStockoutCreateDto.setDeliveryOrder(kiddPurchaseReturnDto);
		kiddPurchaseRefundStockoutCreateDto.setOrder(kiddPurchaseReturnDto);// 际链
		log.info("采购退货单下发仓库，参数:{}" + JSONObject.toJSONString(kiddPurchaseRefundStockoutCreateDto));
		Response<String> response = kiddPurchaseReturnService.createPurchaseRefund(kiddPurchaseRefundStockoutCreateDto);
		log.info("采购退货单下发仓库，返回值:{}"+JSONObject.toJSONString(response));
		return response.isSuccess();
	}



	@Override
	public Response<Boolean> updateDTHStatus(String entryOrderCode) {
		log.info("更新采购退货单状态为待下发状态，单号：{}", entryOrderCode);
		Response<Boolean> response = new Response<>();
		try {
			if (StringUtils.isBlank(entryOrderCode)) {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
				response.setResultObject(false);
				return response;
			}
			PmPurchaseRefundPo pmPurchaseRefundPo = pmPurchaseRefundService
					.selectPurchaseRefundByPurchaseRefundNo(entryOrderCode);
			log.info("查询到采购退货单信息为：{}", JSON.toJSONString(pmPurchaseRefundPo));
			if (2 != pmPurchaseRefundPo.getStatus()) {
				log.info("采购退货单状态不是已审核，不能改为待退货！");
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(true);
				return response;// 返回成功，丢弃消息，避免一直重发
			}
			pmPurchaseRefundPo.setStatus(4);
			pmPurchaseRefundService.updateRefund(pmPurchaseRefundPo);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
		}
		return response;
	}



	@Override
	public Response<Boolean> updateQXSBStatus(String entryOrderCode) {
		log.info("更新采购退货单状态为取消失败状态，单号：{}", entryOrderCode);
		Response<Boolean> response = new Response<>();
		try {
			if (StringUtils.isBlank(entryOrderCode)) {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
				response.setResultObject(false);
				return response;
			}
			pmPurchaseRefundService.updatePurchaseRefundStatusByPurchaseRefundNo(entryOrderCode, 7);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
		}
		return response;
	}



	@Override
	public Response<Boolean> updateYCStatus(String entryOrderCode) {
		log.info("更新采购退货单状态为异常状态，单号：{}", entryOrderCode);
		Response<Boolean> response = new Response<>();
		try {
			if (StringUtils.isBlank(entryOrderCode)) {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
				response.setResultObject(false);
				return response;
			}
			pmPurchaseRefundService.updatePurchaseRefundStatusByPurchaseRefundNo(entryOrderCode, 8);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
		}
		return response;
	}



	@Override
	public Response<Boolean> updateYQXStatus(PmPurchaseRefundDto pmPurchaseRefundDto) {
		log.info("修改退货单为已取消状态吗，退货单：{}", JSONObject.toJSONString(pmPurchaseRefundDto));

		Response<Boolean> response = new Response<>();
		try {
			// 查询退货单及清单
			PmPurchaseRefundPo pmPurchaseRefundPo = pmPurchaseRefundService
					.selectPurchaseRefundByPurchaseRefundNo(pmPurchaseRefundDto.getPurchaseRefundNo());
			if (pmPurchaseRefundPo == null) {
				throw new RuntimeException("采购退货单查询为空，操作终止");
			}
			List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos = pmPurchaseRefundPo.getPmPurchaseRefundItems();
			if (pmPurchaseRefundItemPos == null || pmPurchaseRefundItemPos.size() <= 0) {
				throw new RuntimeException("采购退货单清单查询为空，操作终止");
			}
			/**
			 * 4.入库单状态= CANCELED，做如下处理： a.更新退货单状态为 “已取消(YQX)”
			 * b.调用库存管理(后台)退货接口释放退货预留库存
			 */

			List<ItemInventoryQueryParamDto> list = new ArrayList<>();
			for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo : pmPurchaseRefundItemPos) {
				ItemInventoryQueryParamDto itemInventoryQueryParamDto = new ItemInventoryQueryParamDto();
				itemInventoryQueryParamDto.setRefundAmount(-pmPurchaseRefundItemPo.getRefundAmount());
				itemInventoryQueryParamDto.setLogicWareHouseCode(pmPurchaseRefundPo.getRefundAdrCode());
				itemInventoryQueryParamDto.setProductCode(pmPurchaseRefundItemPo.getProductCode());
				itemInventoryQueryParamDto.setProductId(pmPurchaseRefundItemPo.getProductId());
				list.add(itemInventoryQueryParamDto);
			}
			Response<Boolean> updateBatchRtvQty = itemLocInventoryDubboService.updateBatchRtvQty(list);
			log.info("调用释放退货预留服务itemLocInventoryDubboService.updateBatchRtvQty返回结果：{}",
					JSON.toJSONString(updateBatchRtvQty));
			if (!updateBatchRtvQty.isSuccess()) {
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
				return response;
			}
			pmPurchaseRefundService
					.updatePurchaseRefundStatusByPurchaseRefundNo(pmPurchaseRefundDto.getPurchaseRefundNo(), 6);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(true);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
		}
		return response;
	}



	@Override
	public Response<Boolean> workFlowCallBack(Long keyId, String auditorId, String auditorName, Boolean auditorResult) {
		Response<Boolean> response=new Response<>();
		log.info("pmpurchaseRefundWriteDubboService-->workFlowCallBack()-->param:"+keyId+","+auditorId+","+auditorName+","+auditorResult);
		try {
			//查询采购退货单详情
			PmPurchaseRefundPo pmPurchaseRefundPo = pmPurchaseRefundService.selectRefundDetailById(keyId);
			if (null==pmPurchaseRefundPo){
				throw new RuntimeException("查询采购退货单失败");
			}
			if (null==auditorResult){
				throw new RuntimeException("无审批结果，操作终止");
			}
			//根据审批结果修改采购退货单状态
			pmPurchaseRefundPo.setAuditUserId(auditorId);
			pmPurchaseRefundPo.setAuditTime(new Date());
			if (auditorResult){
				pmPurchaseRefundPo.setStatus(2);
				pmPurchaseRefundService.updateRefund(pmPurchaseRefundPo);
				//下发采购退货单
				if (!createPurchaseRefundHelper(pmPurchaseRefundPo)) {
					// 更新单子为异常状态
					pmPurchaseRefundPo.setStatus(8);
					pmPurchaseRefundService.updateRefund(pmPurchaseRefundPo);
				}
			}else {
				pmPurchaseRefundPo.setStatus(3);
				pmPurchaseRefundService.updateRefund(pmPurchaseRefundPo);
				// 回退退货预留
				List<ItemInventoryQueryParamDto> itemInventoryQueryParamDtos = new ArrayList<>();
				for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo : pmPurchaseRefundPo.getPmPurchaseRefundItems()) {
					// 修改释放退货预留
					ItemInventoryQueryParamDto itemInventoryQueryParamDto = new ItemInventoryQueryParamDto();
					itemInventoryQueryParamDto.setRefundAmount(-pmPurchaseRefundItemPo.getRefundAmount());
					itemInventoryQueryParamDto.setLogicWareHouseCode(pmPurchaseRefundPo.getRefundAdrCode());
					itemInventoryQueryParamDto.setProductCode(pmPurchaseRefundItemPo.getProductCode());
					itemInventoryQueryParamDto.setProductId(pmPurchaseRefundItemPo.getProductId());
					itemInventoryQueryParamDtos.add(itemInventoryQueryParamDto);
				}
				Response<Boolean> inventoryResponse = itemLocInventoryDubboService
						.updateBatchRtvQty(itemInventoryQueryParamDtos);
				if (!inventoryResponse.isSuccess()) {
					log.info("pmpurchaseRefundWriteDubboService-->workFlowCallBack()-->error:{}",inventoryResponse.getErrorMessage());
					throw new RuntimeException("更新库存出现异常，操作终止");
				}
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName() + e.getMessage());
			return response;
		}
	}

	@Override
	public Response<Void> pushPurchaseRefundSettlementMsg(PmPurchaseRefundDto refundDto) {
		try {
			// 推送采购入库数据给财务中心做结算
			purchaseSettlementMQProducer.sendMsg(buildRefundSettlementData(refundDto));
			log.info("....................................................当前推送退货单号为："+refundDto.getPurchaseRefundNo());
		} catch (Exception e) {
			log.error("pushPurchaseRefundSettlementMsg-->，推送采购退货结算数据出错啦！ 退货单号：", refundDto.getPurchaseRefundNo(),e);
			//throw new RuntimeException("推送采购退货结算数据出错，操作终止");
		}
		return null;
	}
}
