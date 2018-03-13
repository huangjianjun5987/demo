package com.yatang.sc.facade.purchase;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundItemDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundWriteDubboService;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmOrderLinesDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月11日
 */
@Service(value = "purchaseRefundProcessor")
public class PurchaseRefundProcessor implements MQMsgProcessor {

	private static final Logger					log	= LoggerFactory.getLogger(PurchaseRefundProcessor.class);

	@Autowired
	private PmPurchaseRefundWriteDubboService	pmPurchaseRefundWriteDubboService;



	@Override
	public void process(String message) {
		log.info("purchaseRefundProcessor收到信息：{}", message);

		try {
			KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = JSON.parseObject(message,
					KiddPurchaseOrderConfirmDto.class);
			KiddConfirmEntryOrderDto kiddConfirmEntryOrderDto = kiddPurchaseOrderConfirmDto.getEntryOrder();
			String orderCode = kiddConfirmEntryOrderDto.getEntryOrderCode();
			String currentStatus = kiddConfirmEntryOrderDto.getStatus();
			Response<Boolean> result = null;
			PmPurchaseRefundDto pmPurchaseRefundDto = new PmPurchaseRefundDto();
			pmPurchaseRefundDto.setPurchaseRefundNo(orderCode);
			pmPurchaseRefundDto.setRefundTime(kiddConfirmEntryOrderDto.getOperateTime());
			// 第三方物流仓库记录号（财务中心用）
			pmPurchaseRefundDto.setLogisticsWarehouseCode(StringUtils.isBlank(kiddConfirmEntryOrderDto.getSaleOrderCodeWMS())
					? kiddConfirmEntryOrderDto.getOutBizCode() : kiddConfirmEntryOrderDto.getSaleOrderCodeWMS());
			// 交货单号（采购时供应商的出库/送货单号，退货时提货单号）（财务中心用）
			pmPurchaseRefundDto.setDeliveryVoucherNo(kiddConfirmEntryOrderDto.getDeliveryVoucherNo());
			List<PmPurchaseRefundItemDto> pmPurchaseRefundItemDtos = new ArrayList<>();
			if (kiddPurchaseOrderConfirmDto.getOrderLines()!=null && kiddPurchaseOrderConfirmDto.getOrderLines().size()>0){
				for (KiddConfirmOrderLinesDto kiddConfirmOrderLinesDto : kiddPurchaseOrderConfirmDto.getOrderLines()) {
					PmPurchaseRefundItemDto pmPurchaseRefundItemDto = new PmPurchaseRefundItemDto();
					pmPurchaseRefundItemDto.setId(Long.valueOf(kiddConfirmOrderLinesDto.getOrderLineNo()));
					pmPurchaseRefundItemDto.setRealRefundAmount(kiddConfirmOrderLinesDto.getActualQty());
					pmPurchaseRefundItemDtos.add(pmPurchaseRefundItemDto);
				}
				pmPurchaseRefundDto.setPmPurchaseRefundItems(pmPurchaseRefundItemDtos);
			}
			switch (currentStatus) {
			case "NEW":
				// 1.出库单状态= NEW，无处理
				break;
			case "ACCEPT":
				// 2.出库单状态= ACCEPT，更新退货单状态为“待退货(DTH)”
				result = pmPurchaseRefundWriteDubboService.updateDTHStatus(orderCode);
				break;
			case "PARTDELIVERED":
			case "DELIVERED":
				// 3.出库单状态=
				// PARTDELIVERED、DELIVERED，调用“采购退货出库确认结果程序”接口获取入库单详情，并做如下处理:
				// a.更新退货出库单信息
				// 退货单头信息：
				// 退货日期：接口返回的“operateTime”
				// 退货单状态：已退货 (YTH)
				// 退货单行信息：
				// 退货数：接口返回明细的“acualQty”
				// 调用库存管理(后台)退货接口，扣减“现有库存”并释放退货预留

				Response<Boolean> response = pmPurchaseRefundWriteDubboService
						.refundSuccessCallBack(pmPurchaseRefundDto);
				if (response==null || !response.isSuccess()) {
					throw new RuntimeException("处理仓库采购退货，已退货，失败：" + response.getErrorMessage());
				}
				break;
			case "CANCELED":
				// 4.入库单状态= CANCELED，做如下处理：
				// a.更新退货单状态为 “已取消(YQX)”
				// b.调用库存管理(后台)退货接口释放退货预留库存
				result = pmPurchaseRefundWriteDubboService.updateYQXStatus(pmPurchaseRefundDto);
				break;
			case "CANCELEDFAIL":
				// 5.入库单状态= CANCELEDFAIL，更新退货单状态为 “取消失败(QXSB)”
				result = pmPurchaseRefundWriteDubboService.updateQXSBStatus(orderCode);
				break;
			case "EXCEPTION":
			case "REJECT":
				// 6.入库单状态= EXCEPTION、REJECT，更新收货单状态为 “异常(YC)”。异常状态入库单需人工分析处理
				result = pmPurchaseRefundWriteDubboService.updateYCStatus(orderCode);
				break;
			default:
				throw new RuntimeException("消费MQ消息出错，原因：没有对应状态");
			}
			if (null != result && !result.isSuccess()) {
				throw new RuntimeException("调用dubbo服务出错，原因：" + result.getErrorMessage());
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException("处理mq消息出错了：mq=" + message, e);
		}
	}
}
