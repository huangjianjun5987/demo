package com.yatang.sc.facade.flow.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.bpm.dubboservice.WorkFlowDubboService;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.SupplierSettledPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderExtPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptSHDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmOrderLinesDto;
import com.yatang.sc.facade.exception.SaveAuditProcessException;
import com.yatang.sc.facade.flow.PmPurchaseOrderWriteFlowService;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import com.yatang.sc.inventory.dto.TranDataDto;
import com.yatang.sc.inventory.dubboservice.TranDataDubboService;
import com.yatang.xc.mbd.biz.system.dto.CompanyDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.CompanyDubboService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述: 商品采购单的writeFlow实现类
 * @类名: PmPurchaseOrderWriteFlowServiceImpl
 * @作者: liuxiaokun
 * @创建时间: 2017/12/20 10:20
 */
@Slf4j
@Service("pmPurchaseOrderWriteFlowService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PmPurchaseOrderWriteFlowServiceImpl implements PmPurchaseOrderWriteFlowService {

	private final PmPurchaseOrderService		pmPurchaseOrderService;

	private final WorkFlowDubboService			workFlowDubboService;

	private final TranDataDubboService			tranDataDubboService;

	private final PmPurchaseReceiptService		pmPurchaseReceiptService;

	private final ProductScIndexDubboService	productScIndexDubboService;
	private final CompanyDubboService			companyDubboService;



	@Override
	public  Response<String> addPmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto) {
		log.info("start----addPmPurchaseOrder In PmPurchaseOrderWriteFlowServiceImpl---pmPurchaseOrderExtDto:{}",
				JSON.toJSONString(pmPurchaseOrderExtDto));


		Response<String> response=new Response<>();
		PmPurchaseOrderExtPo pmPurchaseOrderExtPo = BeanConvertUtils.convert(pmPurchaseOrderExtDto,
				PmPurchaseOrderExtPo.class);

		Long mainId = pmPurchaseOrderService.addPmPurchaseOrder(pmPurchaseOrderExtPo);
		if (mainId <= 0L) {
            response.setSuccess(false);
            response.setErrorMessage("采购单生成失败");
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            return response;
		}

		// 当采购单状态为已提交（待审核）时，开始审核工作流程
		if (pmPurchaseOrderExtPo.getPmPurchaseOrder().getStatus() != null
				&& pmPurchaseOrderExtPo.getPmPurchaseOrder().getStatus() == 1) {
			Response<Boolean> booleanResponse = workFlowDubboService.saveStartProcess(mainId, 0,
					Integer.valueOf(pmPurchaseOrderExtPo.getPmPurchaseOrder().getBranchCompanyId()),
					pmPurchaseOrderExtPo.getPmPurchaseOrder().getCreateUserId());
			if (!booleanResponse.isSuccess()) {
				// 如果提交审核失败，则将状态改为制单
				PmPurchaseOrderPo forStatus = new PmPurchaseOrderPo();
				forStatus.setId(mainId);
				forStatus.setStatus(0);
				pmPurchaseOrderService.updateByPrimaryKeySelective(forStatus);
				log.warn("workFlowDubboService.saveStartProcess(purchaseOrderId:" + mainId + ",type:" + 0
						+ ",branchCompanyId:" + pmPurchaseOrderExtPo.getPmPurchaseOrder().getBranchCompanyId()
						+ " 创建审批流程失败，将采购单状态置为制单状态");
				throw new SaveAuditProcessException("创建审批流程失败，将采购单状态置为制单状态");
			}
		}
		PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService.selectByPrimaryKey(mainId);
		if (pmPurchaseOrderPo!=null){
			response.setResultObject(pmPurchaseOrderPo.getPurchaseOrderNo());
		}
		response.setSuccess(true);
		return response;
	}



	@Override
	public Boolean updatePmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto) {
		log.info("start----updatePmPurchaseOrder In PmPurchaseOrderWriteFlowServiceImpl---pmPurchaseOrderExtDto:{}",
				JSON.toJSONString(pmPurchaseOrderExtDto));
		// dto2po
		PmPurchaseOrderExtPo pmPurchaseOrderExtPo = BeanConvertUtils.convert(pmPurchaseOrderExtDto,
				PmPurchaseOrderExtPo.class);
		boolean updateSuccess = pmPurchaseOrderService.updatePmPurchaseOrder(pmPurchaseOrderExtPo);
		if (!updateSuccess) {
			return false;
		}

		// 当采购单状态为已提交（待审核）时，开始审核工作流程
		if (pmPurchaseOrderExtDto.getPmPurchaseOrder().getStatus() != null
				&& pmPurchaseOrderExtDto.getPmPurchaseOrder().getStatus() == 1) {
			Response<Boolean> booleanResponse = workFlowDubboService.saveStartProcess(
					pmPurchaseOrderExtDto.getPmPurchaseOrder().getId(), 0,
					Integer.valueOf(pmPurchaseOrderExtDto.getPmPurchaseOrder().getBranchCompanyId()),
					pmPurchaseOrderExtDto.getPmPurchaseOrder().getModifyUserId());
			if (!booleanResponse.isSuccess()) {
				// 如果提交审核失败，则将状态改为制单
				PmPurchaseOrderPo forStatus = new PmPurchaseOrderPo();
				forStatus.setId(pmPurchaseOrderExtDto.getPmPurchaseOrder().getId());
				forStatus.setStatus(0);
				pmPurchaseOrderService.updateByPrimaryKeySelective(forStatus);
				log.warn("workFlowDubboService.saveStartProcess(purchaseOrderId:"
						+ pmPurchaseOrderExtDto.getPmPurchaseOrder().getId() + ",type:" + 0 + ",branchCompanyId:"
						+ pmPurchaseOrderExtDto.getPmPurchaseOrder().getBranchCompanyId() + " 创建审批流程失败，将采购单状态置为制单状态");
				throw new SaveAuditProcessException("创建审批流程失败，将采购单状态置为制单状态");
			}
		}
		return true;
	}



	@Override
	public Response<Boolean> updatePmPurchaseOrderReceipt(PmPurchaseReceiptSHDto pmPurchaseReceiptSHDto) {
		log.info("updatePurchaseReceiptSHStatus接收参数pmPurchaseReceiptSHDto=【{}】",
				JSON.toJSONString(pmPurchaseReceiptSHDto));
		Response<Boolean> response = new Response<>();
		try {
			// 检查传入参数，有误直接返回
			String err = checkParam(pmPurchaseReceiptSHDto);
			if (null != err) {
				log.info("updatePurchaseReceiptSHStatus参数错误pmPurchaseReceiptSHDto=【{}】,错误：{}",
						JSON.toJSONString(pmPurchaseReceiptSHDto), err);
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage(err);
				return response;
			}
			//根据收货单号查询收货单
			PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService.selectByPurchaseReceiptNo(pmPurchaseReceiptSHDto.getPurchaseReceiptNo());
			if (null == pmPurchaseReceiptPo || pmPurchaseReceiptPo.getStatus() != 1) {
				log.info("当前收货单状态不是待收货，不能收货");
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage("当前收货单状态不是待收货，不能收货");
				return response;
			}
			// 根据采购单ID查询采购订单
			Long purchaseOrderId=null;
			try {
				purchaseOrderId=Long.parseLong(pmPurchaseReceiptPo.getPurchaseOrderId());
			} catch (Exception e) {
				log.info("收货单表中的采购订单ID不能转换为Long，ID=【{}】",pmPurchaseReceiptPo.getPurchaseOrderId());
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage("收货单表中的采购订单ID不能转换为Long，ID="+pmPurchaseReceiptPo.getPurchaseOrderId());
				return response;
			}
			PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService.selectByPrimaryKey(purchaseOrderId);
			if (null == pmPurchaseOrderPo)  {
				log.info("没有查询到采购订单,采购单ID=【{}】",pmPurchaseReceiptPo.getPurchaseOrderId());
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage("没有查询到采购订单,采购单ID=【{}】:"+pmPurchaseReceiptPo.getPurchaseOrderId());
				return response;
			}

			Date now = new Date();
			pmPurchaseReceiptPo.setStatus(2);
			pmPurchaseReceiptPo.setReceivedTime(now);
			CompanyDTO companyDTO = getBranchCompanyCode(pmPurchaseOrderPo);
			if (null == companyDTO.getCompanyCode()) {
				log.info("分公司信息有误,分公司ID=【{}】", pmPurchaseOrderPo.getBranchCompanyId());
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage("分公司信息有误,分公司ID=" + pmPurchaseOrderPo.getBranchCompanyId());
				return response;
			}
			// 每个子公司一个虚拟仓，仓库编码=所属子公司编码+逻辑仓类型（直送中转仓）--不知道类型是怎么定义的，这儿直接加个首字母后缀
			String inventedCompanyCode = companyDTO.getCompanyCode() + ".zszzc";
			// 查询采购订单明细行
			List<TranDataDto> tranDataDtoList = new ArrayList<>();
			List<PmPurchaseReceiptItemsPo> PmPurchaseReceiptItemsPoList = new ArrayList<>();
			List<SupplierSettledPo> supplierSettledPoList = new ArrayList<>();
			for (PurchaseConfirmOrderLinesDto purchaseConfirmOrderLinesDto : pmPurchaseReceiptSHDto.getOrderLines()) {

				// 根据采购订单ID和商品行商品编码查询【【【订单行明细】】】
				PmPurchaseOrderItemPo pmPurchaseOrderItemPo = pmPurchaseOrderService
						.selectByPurchaseOrderIdAndProductCode(pmPurchaseOrderPo.getId(),
								purchaseConfirmOrderLinesDto.getItemCode());
				if (null == pmPurchaseOrderItemPo) {
					log.info("当前订单不存在商品编码=【{}】的商品" + purchaseConfirmOrderLinesDto.getItemCode());
					response.setCode(CommonsEnum.RESPONSE_500.getCode());
					response.setSuccess(false);
					response.setErrorMessage("当前订单不存在商品编码=" + purchaseConfirmOrderLinesDto.getItemCode());
					return response;
				}
				if (purchaseConfirmOrderLinesDto.getActualQty() > pmPurchaseOrderItemPo.getPurchaseNumber()) {
					log.info("商品收货数量不能大于采购数量，商品编码【{}】", purchaseConfirmOrderLinesDto.getItemCode());
					response.setCode(CommonsEnum.RESPONSE_500.getCode());
					response.setSuccess(false);
					response.setErrorMessage("商品收货数量不能大于采购数量，商品编码=" + purchaseConfirmOrderLinesDto.getItemCode());
					return response;
				}
				// 根据采购单明细查询收货单明细
				PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo = pmPurchaseReceiptService
						.queryReceiptItemsByPurchaseOrderItemsId(pmPurchaseOrderItemPo.getId().toString());
				if (null == pmPurchaseReceiptItemsPo) {
					log.info("根据采购单明细ID=【{}】查询收货单明细为空", pmPurchaseOrderItemPo.getId());
					response.setCode(CommonsEnum.RESPONSE_500.getCode());
					response.setSuccess(false);
					response.setErrorMessage("根据采购单明细ID=【{}】查询收货单明细为空:" + pmPurchaseOrderItemPo.getId());
					return response;
				}
				pmPurchaseReceiptItemsPo.setReceivedNumber(purchaseConfirmOrderLinesDto.getActualQty());
				pmPurchaseReceiptItemsPo.setModifyTime(now);
				PmPurchaseReceiptItemsPoList.add(pmPurchaseReceiptItemsPo);

				ProductIndexDto productDto = getProductIndexDto(purchaseConfirmOrderLinesDto.getItemCode());
				if (null == productDto) {
					log.info("当前订单商品信息查询失败，商品编码【{}】", purchaseConfirmOrderLinesDto.getItemCode());
					response.setCode(CommonsEnum.RESPONSE_500.getCode());
					response.setSuccess(false);
					response.setErrorMessage("当前订单商品信息查询失败，商品编码=" + purchaseConfirmOrderLinesDto.getItemCode());
					return response;
				}
				// 组装trandata数据
				TranDataDto tranDataDto = getTranData(pmPurchaseOrderPo, pmPurchaseReceiptPo, inventedCompanyCode, now,
						purchaseConfirmOrderLinesDto, pmPurchaseOrderItemPo, productDto);
				SupplierSettledPo supplierSettledPo = getSupplierSettledPo(pmPurchaseOrderPo, pmPurchaseReceiptPo, now,
						companyDTO, purchaseConfirmOrderLinesDto, pmPurchaseOrderItemPo, productDto);
                supplierSettledPoList.add(supplierSettledPo);
				tranDataDtoList.add(tranDataDto);
			}
			// 插入事物数据
			log.info("插入事物数据请求参数【{}】",JSON.toJSONString(tranDataDtoList));
			Response<Boolean> addTranDataListResult = tranDataDubboService.addTranDataList(tranDataDtoList);
			log.info("插入事物数据返回结果【{}】",JSON.toJSONString(addTranDataListResult));
			if (null == addTranDataListResult || !"200" .equals( addTranDataListResult.getCode())) {
				log.info("插入事物数据出错了");
				response.setCode(CommonsEnum.RESPONSE_500.getCode());
				response.setSuccess(false);
				response.setErrorMessage("插入事物数据出错了");
				return response;
			}
			log.info("更新收货请求参数【{}】【{}】【{}】",JSON.toJSONString(pmPurchaseReceiptPo),
					JSON.toJSONString(PmPurchaseReceiptItemsPoList),JSON.toJSONString(supplierSettledPoList));
			pmPurchaseReceiptService.updateReceiptSH(pmPurchaseReceiptPo, PmPurchaseReceiptItemsPoList,supplierSettledPoList);
			//调用lhp关闭采购单接口
			pmPurchaseOrderService.closePmPurchaseOrder(pmPurchaseOrderPo.getId(), null);
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



	private SupplierSettledPo getSupplierSettledPo(PmPurchaseOrderPo pmPurchaseOrderPo,
			PmPurchaseReceiptPo pmPurchaseReceiptPo, Date now, CompanyDTO companyDTO,
			PurchaseConfirmOrderLinesDto purchaseConfirmOrderLinesDto, PmPurchaseOrderItemPo pmPurchaseOrderItemPo,
			ProductIndexDto productDto) {
		// 为供应商结算支持准备数据
		SupplierSettledPo supplierSettledPo = new SupplierSettledPo();
		supplierSettledPo.setPurchaseReceiptNo(pmPurchaseReceiptPo.getPurchaseReceiptNo());
		supplierSettledPo.setAsn(pmPurchaseReceiptPo.getAsn());
		supplierSettledPo.setPurchaseOrderNo(pmPurchaseOrderPo.getPurchaseOrderNo());
		supplierSettledPo.setSpNo(pmPurchaseOrderPo.getSpNo());
		supplierSettledPo.setSpName(pmPurchaseOrderPo.getSpName());
		supplierSettledPo.setSpAdrNo(pmPurchaseOrderPo.getSpAdrNo());
		supplierSettledPo.setBranchCompanyCode(companyDTO.getCompanyCode());
		supplierSettledPo.setBranchCompanyName(companyDTO.getCompanyName());
		supplierSettledPo.setAdrType(pmPurchaseOrderPo.getAdrType());
		supplierSettledPo.setAdrTypeCode(pmPurchaseOrderPo.getAdrTypeCode());
		supplierSettledPo.setAdrTypeName(pmPurchaseOrderPo.getAdrTypeName());
		supplierSettledPo.setSettlementPeriod(pmPurchaseOrderPo.getSettlementPeriod());
		supplierSettledPo.setPayType(pmPurchaseOrderPo.getPayType());
		// 部类
		supplierSettledPo.setGroups(productDto.getFirstLevelCategoryId());
		// 部类描述
		supplierSettledPo.setGroupsDesc(productDto.getFirstLevelCategoryName());
		// 大类
		supplierSettledPo.setDept(productDto.getSecondLevelCategoryId());
		// 大类描述
		supplierSettledPo.setDeptDesc(productDto.getSecondLevelCategoryName());
		// 商品编号
		supplierSettledPo.setProductCode(productDto.getProductCode());
		// 商品名称
		supplierSettledPo.setProductName(productDto.getName());
		// 税率
		supplierSettledPo.setInputTaxRate(pmPurchaseOrderItemPo.getInputTaxRate());
		// 收货日期
		supplierSettledPo.setReceivedTime(now);
		// 已收货数量
		supplierSettledPo.setReceivedNumber(purchaseConfirmOrderLinesDto.getActualQty());
		// 收货金额（未税)
		BigDecimal purchasePrice = pmPurchaseOrderItemPo.getPurchasePrice();// 含税采购价格
		BigDecimal totalMoney = purchasePrice.multiply(new BigDecimal(purchaseConfirmOrderLinesDto.getActualQty()));// 含税总额=销售价格*收货数量
		// 税额=含税总额/(1+税率*0.01)
		BigDecimal totalMoneyWithoutTax = totalMoney.divide(
		        new BigDecimal(1)
		                .add(pmPurchaseOrderItemPo.getInputTaxRate().multiply(new BigDecimal(0.01))),
		        2, RoundingMode.HALF_UP);
		supplierSettledPo.setReceivedMoneyWithoutTax(totalMoneyWithoutTax);
		// 收货金额
		supplierSettledPo.setReceivedMoneyWithTax(totalMoney);
		return supplierSettledPo;
	}



	private ProductIndexDto getProductIndexDto(String itemCode) {
		// 根据商品编码查询【【【商品信息】】】
		Response<ProductIndexDto> result = productScIndexDubboService.queryByProductCode(itemCode);
		log.info("根据商品编码={}查询商品详情结果为：{}", itemCode, JSONObject.toJSONString(result));
		if (null != result && "200".equals(result.getCode()) && null != result.getResultObject()) {
			return result.getResultObject();
		}
		return null;
	}



	private TranDataDto getTranData(PmPurchaseOrderPo pmPurchaseOrderPo, PmPurchaseReceiptPo pmPurchaseReceiptPo,
			String inventedCompanyCode, Date now, PurchaseConfirmOrderLinesDto purchaseConfirmOrderLinesDto,
			PmPurchaseOrderItemPo pmPurchaseOrderItemPo, ProductIndexDto productDto) {
		TranDataDto tranDataDto = new TranDataDto();
		tranDataDto.setProductCode(productDto.getProductCode());
		tranDataDto.setItem(productDto.getId());
		tranDataDto.setGroups(productDto.getFirstLevelCategoryId());
		tranDataDto.setDept(productDto.getSecondLevelCategoryId());
		tranDataDto.setClasss(productDto.getThirdLevelCategoryId());
		tranDataDto.setSubclass(productDto.getFourthCategoryId());
		// 每个子公司一个虚拟仓，仓库编码=所属子公司编码+逻辑仓类型（直送中转仓）
		tranDataDto.setLocation(inventedCompanyCode);
		tranDataDto.setTranDate(now);
		tranDataDto.setTranCode("20");
		tranDataDto.setUnits(purchaseConfirmOrderLinesDto.getActualQty());
		// 保留两位小数，交易数量*（单据商品成本/（1+税率））；
		tranDataDto.setTotalCost(new BigDecimal(purchaseConfirmOrderLinesDto.getActualQty())
				.multiply(pmPurchaseOrderItemPo.getPurchasePrice())
				.divide(new BigDecimal(1 + pmPurchaseOrderItemPo.getInputTaxRate().multiply(new BigDecimal(0.01)).doubleValue()), 2,
						BigDecimal.ROUND_HALF_UP)
				.doubleValue());
		// 保留两位小数，单据商品成本*税率；
		tranDataDto.setTaxes(pmPurchaseOrderItemPo.getPurchasePrice().multiply(pmPurchaseOrderItemPo.getInputTaxRate().multiply(new BigDecimal(0.01)))
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		// 保留两位小数，交易数量*业务单据中商品售价
		tranDataDto.setTotalRetail(new BigDecimal(purchaseConfirmOrderLinesDto.getActualQty())
				.multiply(pmPurchaseOrderItemPo.getPurchasePrice()).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue());

		tranDataDto.setRefNo1(pmPurchaseOrderPo.getPurchaseOrderNo());
		tranDataDto.setRefNo2(pmPurchaseReceiptPo.getPurchaseReceiptNo());
		tranDataDto.setVatRate(pmPurchaseOrderItemPo.getInputTaxRate().toString());
		return tranDataDto;
	}



	private CompanyDTO getBranchCompanyCode(PmPurchaseOrderPo pmPurchaseOrderPo) {
		// 获取子公司编码
		String branchCompanyId = pmPurchaseOrderPo.getBranchCompanyId();
		Response<CompanyDTO> findCompanyResponse = companyDubboService
				.findCompanyById(Integer.parseInt(branchCompanyId));
		if (!findCompanyResponse.isSuccess() && null != findCompanyResponse.getResultObject()) {
			return null;
		}
		return findCompanyResponse.getResultObject();
	}



	private String checkParam(PmPurchaseReceiptSHDto pmPurchaseReceiptSHDto) {
		if (StringUtils.isEmpty(pmPurchaseReceiptSHDto.getPurchaseReceiptNo())) {
			return "收货单号不能为空";
		}
		List<PurchaseConfirmOrderLinesDto> orderLines = pmPurchaseReceiptSHDto.getOrderLines();
		if (null == orderLines || orderLines.isEmpty()) {
			return "订单明细不能为空";
		}
		for (PurchaseConfirmOrderLinesDto purchaseConfirmOrderLinesDto : orderLines) {
			Integer actualQty = purchaseConfirmOrderLinesDto.getActualQty();
			if (null == actualQty || actualQty < 0) {
				return "收货数量必须大于等于0";
			}
			String itemCode = purchaseConfirmOrderLinesDto.getItemCode();
			if (null == itemCode) {
				return "商品编码不能为空";
			}
		}
		return null;
	}
}
