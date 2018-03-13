package com.yatang.sc.facade.purchase;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.UpdateReceiptYCStatusDto;
import com.yatang.sc.facade.dto.UpdateReceiptYQXStatusDto;
import com.yatang.sc.facade.dto.UpdateReceiptYXFStatusDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptParamDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmEntryOrderDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmOrderLinesDto;
import com.yatang.sc.facade.dto.pm.PurchaseOrderConfirmDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述:采购订单mq消费者（对际联） new
 * @类名:PurchaseMsgProcessor
 * @作者: lvheping
 * @创建时间: 2017/8/5 14:57
 * @版本: v1.0
 */
@Slf4j
@Service(value = "purchaseMsgProcessor")
public class PurchaseMsgProcessor implements MQMsgProcessor {
	@Autowired
	private PmPurchaseReceiptWriteDubboService	pmPurchaseReceiptWriteDubboService;
	// @Autowired
	// private ImAbnormalLogService imAbnormalLogService;
	@Autowired
	private PmPurchaseReceiptQueryDubboService	pmPurchaseReceiptQueryDubboService;



	@Override
	public void process(String message) {
		log.info("PurchaseMsgProcessor收到信息：{}", message);

		try {
			if (StringUtils.isEmpty(message)) {
				log.info("message不能为空");
				return;
			}
			KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = JSON.parseObject(message,
					KiddPurchaseOrderConfirmDto.class);
			if (null == kiddPurchaseOrderConfirmDto) {
				log.info("kiddPurchaseOrderConfirmDto不能为空");
				return;
			}
			KiddConfirmEntryOrderDto kiddConfirmEntryOrderDto = kiddPurchaseOrderConfirmDto.getEntryOrder();
			if (null == kiddConfirmEntryOrderDto) {
				log.info("kiddConfirmEntryOrderDto不能为空");
				return;
			}
			String orderCode = kiddConfirmEntryOrderDto.getEntryOrderCode();
			if (StringUtils.isEmpty(orderCode)) {
				log.info("orderCode不能为空");
				return;
			}
			String currentStatus = kiddConfirmEntryOrderDto.getStatus();
			if (StringUtils.isEmpty(currentStatus)) {
				log.info("currentStatus不能为空");
				return;
			}
			Response<Boolean> result = null;
			switch (currentStatus) {
			case "NEW":
				// 无处理
				break;
			case "ACCEPT":
				// 如果状态为已收货就不处理
				String staus = findStaus(orderCode);
				if (staus != null && (staus.equals("2") || staus.equals("1"))) {// 当前状态为已收货状态不做处理
					break;
				}
				UpdateReceiptYXFStatusDto updateReceiptYXFStatusDto = new UpdateReceiptYXFStatusDto();
				updateReceiptYXFStatusDto.setPurchaseReceiptNo(orderCode);
				result = pmPurchaseReceiptWriteDubboService.updateYXFStatus(updateReceiptYXFStatusDto);
				break;
			case "PARTFULFILLED":
			case "FULFILLED":
				PurchaseOrderConfirmDto p = new PurchaseOrderConfirmDto();
				p.setEntryOrder(BeanConvertUtils.convert(kiddConfirmEntryOrderDto, PurchaseConfirmEntryOrderDto.class));
				p.setOrderLines(BeanConvertUtils.convertList(kiddPurchaseOrderConfirmDto.getOrderLines(),
						PurchaseConfirmOrderLinesDto.class));
				result = pmPurchaseReceiptWriteDubboService.updateYSHStatus(p);
				break;
			case "CANCELED":
				UpdateReceiptYQXStatusDto updateReceiptYQXStatusDto = new UpdateReceiptYQXStatusDto();
				updateReceiptYQXStatusDto.setPurchaseReceiptNo(orderCode);
				result = pmPurchaseReceiptWriteDubboService.updateYQXStatus(updateReceiptYQXStatusDto);
				break;
			case "CANCELEDFAIL":
				break;
			case "EXCEPTION":
			case "REJECT":
				UpdateReceiptYCStatusDto updateReceiptYCStatusDto = new UpdateReceiptYCStatusDto();
				updateReceiptYCStatusDto.setPurchaseReceiptNo(orderCode);
				result = pmPurchaseReceiptWriteDubboService.updateYCStatus(updateReceiptYCStatusDto);
				break;
			default:
				throw new RuntimeException("消费MQ消息出错，原因：没有对应状态");
			}
			if (null != result && !result.isSuccess()) {
				throw new RuntimeException("调用dubbo服务出错，原因：" + result.getErrorMessage());
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));

			// ImAbnormalLogPo imAbnormalLogPo = new
			// ImAbnormalLogPo();//消息消费异常信息
			// imAbnormalLogPo.setName("CGJS");
			// imAbnormalLogPo.setMqMessage(message);
			// imAbnormalLogPo.setErrorMessage(Throwables.getRootCause(e).getMessage());
			// imAbnormalLogService.insert(imAbnormalLogPo);//记录消息消费异常信息

			throw new RuntimeException("处理mq消息出错了：mq=" + message, e);
		}
	}



	// 判断当前收货单的状态
	public String findStaus(String orderCode) {
		PmPurchaseReceiptParamDto pmPurchaseReceiptParamDto = new PmPurchaseReceiptParamDto();
		pmPurchaseReceiptParamDto.setPurchaseReceiptNo(orderCode);
		Response<PageResult<PmPurchaseReceiptDto>> pageResultResponse = pmPurchaseReceiptQueryDubboService
				.queryReceiptByPages(pmPurchaseReceiptParamDto);
		if (pageResultResponse.isSuccess()) {
			PageResult<PmPurchaseReceiptDto> resultObject = pageResultResponse.getResultObject();
			if (resultObject != null) {
				List<PmPurchaseReceiptDto> data = resultObject.getData();
				if (data != null && data.size() > 0) {
					PmPurchaseReceiptDto pmPurchaseReceiptDto = data.get(0);
					return pmPurchaseReceiptDto.getStatus() == null ? null
							: pmPurchaseReceiptDto.getStatus().toString();
				}
			}

		}
		return null;
	}
}
