package com.yatang.sc.facade.purchase;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.mq.comsumer.processor.MQMsgProcessor;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.PhysicalWarehouseDto;
import com.yatang.sc.facade.dto.SupplierPlaceDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderInfoDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptParamDto;
import com.yatang.sc.facade.dto.supplier.SpAdrContactDto;
import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierOperTaxInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierlicenseInfoDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptQueryDubboService;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.purchase.KiddEntryOrderDto;
import com.yatang.sc.kidd.service.KiddPurchaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述:消费自己发送的采购订单 new
 * @类名:PurchaseOrderProcessor
 * @作者: lvheping
 * @创建时间: 2017/8/5 16:57
 * @版本: v1.0
 */
@Slf4j
@Service(value = "purchaseOrderProcessor")
public class PurchaseOrderProcessor implements MQMsgProcessor {
	@Autowired
	private PmPurchaseReceiptQueryDubboService	pmPurchaseReceiptQueryDubboService;
	@Autowired
	private WarehouseLogicQueryDubboService		warehouseLogicQueryDubboService;

	@Autowired
	private KiddPurchaseService					kiddPurchaseService;
	@Autowired
	private SupplierQueryDubboService			supplierQueryDubboService;
	@Autowired
	private PmPurchaseOrderQueryDubboService	pmPurchaseOrderQueryDubboService;
	// @Autowired
	// private ImAbnormalLogService imAbnormalLogService;



	@Override
	public void process(String message) {
		log.info("mq消息" + message);

		try {
			JSONObject jsonObject = JSON.parseObject(message, JSONObject.class);
			Object purchaseOrderNo = jsonObject.get("purchaseOrderNo");

			sendToGlink(purchaseOrderNo);

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));

			// ImAbnormalLogPo imAbnormalLogPo = new
			// ImAbnormalLogPo();//消息消费异常信息
			// imAbnormalLogPo.setName("CGTS");
			// imAbnormalLogPo.setMqMessage(message);
			// imAbnormalLogPo.setErrorMessage(Throwables.getRootCause(e).getMessage());
			// imAbnormalLogService.insert(imAbnormalLogPo);//记录消息消费异常信息

			throw new RuntimeException("处理mq消息出错了：mq=" + message, e);
		}
	}



	/**
	 * 
	 * <method description>
	 *
	 * @param purchaseOrderNo
	 * @throws InterruptedException
	 */
	public void sendToGlink(Object purchaseOrderNo) throws InterruptedException {
		PmPurchaseReceiptParamDto pmPurchaseReceiptParamDto = new PmPurchaseReceiptParamDto();
		pmPurchaseReceiptParamDto.setPageNum(1);
		pmPurchaseReceiptParamDto.setPageSize(Integer.MAX_VALUE);
		pmPurchaseReceiptParamDto.setPurchaseOrderNo(purchaseOrderNo.toString());

		for (int i = 1; i <= 3; i++) {
			Response<PageResult<PmPurchaseReceiptDto>> pageResultResponse = pmPurchaseReceiptQueryDubboService
					.queryReceiptByPages(pmPurchaseReceiptParamDto);
			log.info("采购收货单信息" + JSON.toJSONString(pageResultResponse));
			if (!pageResultResponse.isSuccess()) {
				throw new RuntimeException("查询收货单失败 " + JSON.toJSONString(pageResultResponse));
			}

			PageResult<PmPurchaseReceiptDto> resultObject = pageResultResponse.getResultObject();
			List<PmPurchaseReceiptDto> data1 = resultObject.getData();
			if (CollectionUtils.isEmpty(data1)) {
				log.error("查询收货单为空");
				throw new RuntimeException("查询收货单为空 " + JSON.toJSONString(pageResultResponse));
			}

			PmPurchaseReceiptDto pmPurchaseReceiptDto = data1.get(0);
			if (0 != pmPurchaseReceiptDto.getStatus()) {
				log.info("采购收货单状态不是待已下发，无需下发");
				throw new RuntimeException("采购收货单状态不是待已下发，无需下发。");
			}
			Response<PmPurchaseOrderInfoDto> purchaseOrderInfoByOrderNo = pmPurchaseOrderQueryDubboService
					.getPurchaseOrderInfoByOrderNo(pmPurchaseReceiptDto.getPurchaseOrderNo());
			PmPurchaseOrderInfoDto resultObject1 = purchaseOrderInfoByOrderNo.getResultObject();
			log.info("查询采购订单详情" + JSON.toJSONString(resultObject1));
			if (!purchaseOrderInfoByOrderNo.isSuccess() || resultObject1 == null) {
				throw new RuntimeException("查询采购订单详情失败 " + JSON.toJSONString(resultObject1));
			}
			List<PmPurchaseOrderItemDto> pmPurchaseOrderItems = resultObject1.getPmPurchaseOrderItems();
			if (CollectionUtils.isEmpty(pmPurchaseOrderItems)) {
				throw new RuntimeException("查询采购订单详情为空 " + JSON.toJSONString(pmPurchaseOrderItems));
			}

			Response<SupplierPlaceDto> supplierPlaceDtoResponse = supplierQueryDubboService
					.queryProviderPlaceInfo(Integer.parseInt(resultObject1.getSpAdrId()));
			SupplierPlaceDto suppl = supplierPlaceDtoResponse.getResultObject();
			log.info("查询供应商详情" + JSON.toJSONString(suppl));
			if (!supplierPlaceDtoResponse.isSuccess() || suppl == null) {
				throw new RuntimeException("查询供应商详情失败 " + JSON.toJSONString(suppl));
			}
			SupplierInfoDto supplierInfoDto = suppl.getSupplierInfo();
			if (supplierInfoDto == null) {
				throw new RuntimeException("查询供应商详情为空 " + JSON.toJSONString(supplierInfoDto));
			}
			SupplierlicenseInfoDto supplierlicenseInfo = supplierInfoDto.getSupplierlicenseInfo();
			if (supplierlicenseInfo == null) {
				throw new RuntimeException("查询供应商基本信息为空 " + JSON.toJSONString(supplierlicenseInfo));
			}
			SupplierAdrInfoDto supplierAdrInfoDto = suppl.getSupplierAdrInfo();
			if (supplierAdrInfoDto == null) {
				throw new RuntimeException("查询供应商地点信息为空 " + JSON.toJSONString(supplierAdrInfoDto));
			}
			SpAdrContactDto spAdrContact = supplierAdrInfoDto.getSpAdrContact();
			if (spAdrContact == null) {
				throw new RuntimeException("查询供应商地点信息为空 " + JSON.toJSONString(spAdrContact));
			}
			SupplierOperTaxInfoDto supplierOperTaxInfo = supplierInfoDto.getSupplierOperTaxInfo();
			if (supplierOperTaxInfo == null) {
				throw new RuntimeException("查询供应商地点信息为空 " + JSON.toJSONString(supplierOperTaxInfo));
			}
			// 根据逻辑仓库查询物理仓库
			Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService
					.selectLogicWarehouseByPrimaryKey(pmPurchaseReceiptDto.getAdrTypeCode());
			LogicWarehouseDto loginc = logicWarehouseDtoResponse.getResultObject();
			log.info("查询仓库信息" + JSON.toJSONString(logicWarehouseDtoResponse));
			if (!logicWarehouseDtoResponse.isSuccess() || loginc == null) {
				throw new RuntimeException("查询仓库信息失败 " + JSON.toJSONString(loginc));
			}
			PhysicalWarehouseDto physicalWarehouseDto = loginc.getPhysicalWarehouseDto();
			if (physicalWarehouseDto == null) {
				throw new RuntimeException("查询仓库信息为空 " + JSON.toJSONString(physicalWarehouseDto));
			}
			KiddEntryOrderDto entryOrderDto = new KiddEntryOrderDto();
			// entryOrderDto.setWareHouseCode("901200411");
			// entryOrderDto.setOwnerCode("20041");
			entryOrderDto.setWareHouseCode(pmPurchaseReceiptDto.getAdrTypeCode());// 设置逻辑仓库编码（仓库编码）
			entryOrderDto.setOwnerCode(loginc.getBranchCompanyCode());// 设置子公司编码code（货主编码）
			entryOrderDto.setEntryOrderCode(pmPurchaseReceiptDto.getPurchaseReceiptNo());// 设置采购收货单号
			entryOrderDto.setOwnerOrderCode(pmPurchaseReceiptDto.getPurchaseOrderNo());// 采购单号
			entryOrderDto.setOrderType("CGRK");// 采购单业务类型
			entryOrderDto.setExpectStartTime(pmPurchaseReceiptDto.getEstimatedReceivedDate());// 预计收货日期
			entryOrderDto.setExpectEndTime(pmPurchaseReceiptDto.getEstimatedDeliveryDate());// 最迟预期到货时间
			entryOrderDto.setSupplierName(pmPurchaseReceiptDto.getSpAdrName());// 采购单供应商地点名称
			entryOrderDto.setSupplierCode(pmPurchaseReceiptDto.getSpAdrNo());// 采购单供应商地点编码
			List<KiddOrderLinesDto> kiddorderLinesDtos = new ArrayList<>();// 货品详情
			for (PmPurchaseOrderItemDto pmPurchaseOrderItemDto : pmPurchaseOrderItems) {
				KiddOrderLinesDto kiddorderLinesDto = new KiddOrderLinesDto();// 采购订单明细
				kiddorderLinesDto.setOrderLineNo(pmPurchaseOrderItemDto.getId().toString());// 收货单明细ID
				kiddorderLinesDto.setItemCode(pmPurchaseOrderItemDto.getProductCode());// 采购单商品商品编码
				kiddorderLinesDto.setItemName(pmPurchaseOrderItemDto.getProductName());// 采购单商品商品名称
				kiddorderLinesDto.setPcs(pmPurchaseOrderItemDto.getPackingSpecifications());// 采购单商品规格
				kiddorderLinesDto.setPlanQty(pmPurchaseOrderItemDto.getPurchaseNumber());// 采购单商品采购数量
				kiddorderLinesDto.setStockUnit(pmPurchaseOrderItemDto.getUnitExplanation());// 采购单商品单位
				kiddorderLinesDto.setInventoryType("ZP");// 库存类型值：ZP=正品
				kiddorderLinesDto.setIsGift("N");// 是否为赠品值：N：非赠品
				kiddorderLinesDtos.add(kiddorderLinesDto);
			}
			entryOrderDto.setTotalOrderLines(resultObject1.getPmPurchaseOrderItems().size());// 采购收货单商品行数
			entryOrderDto.setCreateTimeERP(resultObject1.getCreateTime());// 采购单创建时间
			entryOrderDto.setUpdateTimeERP(resultObject1.getAuditTime());// 采购单更新时间
			entryOrderDto.setOperateTime(resultObject1.getAuditTime());// 采购单审核时间

			KiddSenderInfoDto senderInfoDto = new KiddSenderInfoDto();// 供应详细信息
			senderInfoDto.setSenderCompany(supplierlicenseInfo.getCompanyName());// 供应商的公司名称
			senderInfoDto.setSenderName(spAdrContact.getProviderName());// 供应商名称
			senderInfoDto.setSenderTel(spAdrContact.getProviderPhone());// 供应商地点的联系信息的供应商电话
			senderInfoDto.setSenderEmail(spAdrContact.getProviderEmail());// 供应商地点的联系信息的供应商邮箱
			senderInfoDto.setSenderProvince(supplierOperTaxInfo.getCompanyLocProvince());// 供应商证照信息的公司所在省
			senderInfoDto.setSenderCity(supplierOperTaxInfo.getCompanyLocCity());// 供应商证照信息的公司所在市
			senderInfoDto.setSenderArea(supplierOperTaxInfo.getCompanyLocCounty());// 供应商证照信息的公司所在区
			senderInfoDto.setSenderDetailAddress(supplierOperTaxInfo.getCompanyDetailAddress());// 供应商证照信息的公司详细地址
			entryOrderDto.setSenderInfoDto(senderInfoDto);
			entryOrderDto.setOrderLines(kiddorderLinesDtos);

			Response<String> response = kiddPurchaseService.add(entryOrderDto);
			if (response.isSuccess()) {
				log.info("推送收货单成功. request: " + JSON.toJSONString(entryOrderDto) + " ; response:"
						+ JSON.toJSONString(response));
				break;
			}
			log.error("第" + i + "次推送收货单失败. request: " + JSON.toJSONString(entryOrderDto) + " ; response:"
					+ JSON.toJSONString(response));
			if ("该订单已存在!".equals(response.getErrorMessage())) {
				return;
			}
			if (i == 3) {
				throw new RuntimeException("推送收货单，重发三次失败。");
			}
			Thread.sleep(i * 2000);// 延迟重发
		}
	}

}
