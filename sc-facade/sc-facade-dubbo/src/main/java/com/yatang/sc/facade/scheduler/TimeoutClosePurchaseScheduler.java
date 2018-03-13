package com.yatang.sc.facade.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.WarehouseLogicInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import com.yatang.sc.facade.service.WarehouseLogicService;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelOrdersDto;
import com.yatang.sc.kidd.service.KiddOrderCancelService;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TimeoutClosePurchaseScheduler extends QuartzJobBean {
	private ApplicationContext			applicationContext;
	private PmPurchaseOrderService		purchaseOrderService;
	private PmPurchaseReceiptService	pmPurchaseReceiptService;
	private KiddOrderCancelService		kiddOrderCancelService;
	private WarehouseLogicService		warehouseLogicService;
	private Long						timeout;



	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		purchaseOrderService = applicationContext.getBean(PmPurchaseOrderService.class);
		pmPurchaseReceiptService = applicationContext.getBean(PmPurchaseReceiptService.class);
		kiddOrderCancelService = applicationContext.getBean(KiddOrderCancelService.class);
		warehouseLogicService = applicationContext.getBean(WarehouseLogicService.class);
		try {
			// 批量关闭采购单
			if (log.isInfoEnabled()) {
				log.info("开始批量关闭订单，当前时间：{}", new Date());
			}
			closePurchaseBatch(jobexecutioncontext);
			if (log.isInfoEnabled()) {
				log.info("批量关闭订单完成，当前时间：{}", new Date());
			}
		} catch (Exception e) {
			log.error("批量关闭订单出错了，报错信息：{}", ExceptionUtils.getFullStackTrace(e));
			try {
				// 批量关闭出错啦，单个关闭
				if (log.isInfoEnabled()) {
					log.info("批量关闭订单出错了，开始单个关闭订单，当前时间：{}", new Date());
				}
				closePurchase(jobexecutioncontext);
				if (log.isInfoEnabled()) {
					log.info("单个关闭订单完成，当前时间：{}", new Date());
				}
			} catch (Exception e1) {
				log.error("批量关闭订单出错了，报错信息：{}", ExceptionUtils.getFullStackTrace(e1));
				throw new JobExecutionException(e);
			}
		}
	}



	/**
	 * 
	 * <method description>批量关闭
	 *
	 * @param jobexecutioncontext
	 */
	private void closePurchaseBatch(JobExecutionContext jobexecutioncontext) {
		// SELECT * FROM pm_purchase_order WHERE STATUS=2 AND
		// estimated_delivery_date < DATE_SUB(NOW(),INTERVAL 5 DAY);
		List<PmPurchaseOrderPo> list = purchaseOrderService.selectCloseablePurchaseOrder(2, timeout.intValue());
		List<Long> updateListId = new ArrayList<>();
		List<Map<String, String>> purchaseOrderNos = new ArrayList<>();
		Map<String, List<PmPurchaseOrderPo>> map1 = new HashMap<>();
		for (PmPurchaseOrderPo pmPurchaseOrderPo : list) {
			if (map1.containsKey(pmPurchaseOrderPo.getAdrTypeCode())) {// 存在
				map1.get(pmPurchaseOrderPo.getAdrTypeCode()).add(pmPurchaseOrderPo);// 加入List
			} else {
				List<PmPurchaseOrderPo> pmPurchaseOrderPos = new ArrayList<>();
				pmPurchaseOrderPos.add(pmPurchaseOrderPo);
				map1.put(pmPurchaseOrderPo.getAdrTypeCode(), pmPurchaseOrderPos);
			}
		}
		Set<String> strings = map1.keySet();
		for (String s : strings) {
			List<PmPurchaseOrderPo> pmPurchaseOrderPos = map1.get(s);
			for (PmPurchaseOrderPo pmPurchaseOrderPo : pmPurchaseOrderPos) {
				// 根据采购单号查询对应的收货单
				PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService
						.queryPurchaseReceiptByPurchaseOrderId(pmPurchaseOrderPo.getId());
				// 收货单的状态为“待下发(DXF) 0”或“已收货 (YSH) 2”或“已取消(YQX) 3”
				if (null != pmPurchaseReceiptPo && null != pmPurchaseReceiptPo.getStatus()) {
					if (0 == pmPurchaseReceiptPo.getStatus() || 2 == pmPurchaseReceiptPo.getStatus()
							|| 3 == pmPurchaseReceiptPo.getStatus()) {
						if (log.isInfoEnabled()) {
							log.info("添加到待关闭订单列表，订单ID：{}", pmPurchaseOrderPo.getId());
						}
						updateListId.add(pmPurchaseOrderPo.getId());
					} else if (1 == pmPurchaseReceiptPo.getStatus()) {
						// 调用超期采购入库单取消接口，向WMS下发入库单取消指令
						Map<String, String> map = new HashMap<>();
						map.put("purchaseReceiptNo", pmPurchaseReceiptPo.getPurchaseReceiptNo());
						map.put("adrTypeCode", pmPurchaseOrderPo.getAdrTypeCode());
						if (log.isInfoEnabled()) {
							log.info("添加到待取消订单列表，订单编号：{}", pmPurchaseReceiptPo.getPurchaseReceiptNo());
						}
						purchaseOrderNos.add(map);
					}
				}
			}

		}

		if (!updateListId.isEmpty()) {
			if (log.isInfoEnabled()) {
				log.info("执行批量关闭订单操作，待关闭订单列表：{}", updateListId);
			}
			purchaseOrderService.closePurchaseListByPrimaryKey(updateListId, null);
		}
		if (!purchaseOrderNos.isEmpty()) {
			cancelPurchaseReceipt(kiddOrderCancelService, purchaseOrderNos);
		}
	}



	private void closePurchase(JobExecutionContext jobexecutioncontext) {
		// 查询已审核列表
		List<PmPurchaseOrderPo> list = purchaseOrderService.selectCloseablePurchaseOrder(2, timeout.intValue());
		for (PmPurchaseOrderPo pmPurchaseOrderPo : list) {
			try {
				// 根据采购单号查询对应的收货单
				PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService
						.queryPurchaseReceiptByPurchaseOrderId(pmPurchaseOrderPo.getId());
				// 收货单的状态为“待下发(DXF) 0”或“已收货 (YSH) 2”或“已取消(YQX) 3”
				if (0 == pmPurchaseReceiptPo.getStatus() || 2 == pmPurchaseReceiptPo.getStatus()
						|| 3 == pmPurchaseReceiptPo.getStatus()) {
					purchaseOrderService.closePurchaseByPrimaryKey(pmPurchaseOrderPo.getId(), null);
				} else if (1 == pmPurchaseReceiptPo.getStatus()) {
					// 调用超期采购入库单取消接口，向WMS下发入库单取消指令
					List<Map<String, String>> purchaseOrderNos = new ArrayList<>();
					Map<String, String> map = new HashMap<>();
					map.put("purchaseReceiptNo", pmPurchaseReceiptPo.getPurchaseReceiptNo());
					map.put("adrTypeCode", pmPurchaseOrderPo.getAdrTypeCode());
					purchaseOrderNos.add(map);
					cancelPurchaseReceipt(kiddOrderCancelService, purchaseOrderNos);

				}

			} catch (Exception e) {
				continue;
			}
		}

	}



	private void cancelPurchaseReceipt(KiddOrderCancelService kiddOrderCancelService, List<Map<String, String>> list) {

		KiddCancelAllOrdersDto kiddcancelAllOrdersDto = new KiddCancelAllOrdersDto();

		List<KiddCancelOrdersDto> orders = new ArrayList<>();
		for (Map<String, String> map : list) {
			KiddCancelOrdersDto kiddcancelOrdersDto = new KiddCancelOrdersDto();
			kiddcancelOrdersDto.setOrderCode(map.get("purchaseReceiptNo"));
			WarehouseLogicInfoPo warehouseLogicInfoPo = warehouseLogicService
					.selectByWarehouseLogicCode(map.get("adrTypeCode"));
			kiddcancelOrdersDto.setOrgCode(warehouseLogicInfoPo.getBranchCompanyCode());// 设置子公司编码
			kiddcancelOrdersDto.setOrderType(KiddOrderLogisticsType.KIDD_CGRK);// 采购入库
			kiddcancelOrdersDto.setWarehouseCode(warehouseLogicInfoPo.getWarehouseCode());// 设置逻辑仓编码
			orders.add(kiddcancelOrdersDto);
		}
		kiddcancelAllOrdersDto.setType(2);
		kiddcancelAllOrdersDto.setOrders(orders);
		kiddcancelAllOrdersDto.setOrderType(KiddOrderLogisticsType.KIDD_CGRK);// 采购入库
		if (log.isInfoEnabled()) {
			log.info("执行取消订单操作，调用glink方法参数：{}", ToStringBuilder.reflectionToString(kiddcancelAllOrdersDto));
		}
		Response<String> glinkResponseDto = null;
		for (int i = 0; i < 3; i++) {
			try {
				glinkResponseDto = kiddOrderCancelService.cancel(kiddcancelAllOrdersDto);
				if (log.isDebugEnabled()) {
					log.info("第{}次调用glink取消订单返回结果：{}", i, ToStringBuilder.reflectionToString(glinkResponseDto));
				}
			} catch (Exception e) {
				log.error("第{}次调用glibk取消订单报错，报错信息：{}", i, ExceptionUtils.getFullStackTrace(e));
				continue;
			}

			// 请求成功了
			if (glinkResponseDto != null && glinkResponseDto.isSuccess()) {
				break;
			} else {
				continue;
			}
		}

	}



	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}



	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

}
