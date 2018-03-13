package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.domain.pm.PurchaseActualItem;
import com.yatang.sc.facade.domain.pm.PurchaseSettlementData;
import com.yatang.sc.facade.dto.UpdateReceiptYCStatusDto;
import com.yatang.sc.facade.dto.UpdateReceiptYQXStatusDto;
import com.yatang.sc.facade.dto.UpdateReceiptYXFStatusDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmUploadTicketParamDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmEntryOrderDto;
import com.yatang.sc.facade.dto.pm.PurchaseOrderConfirmDto;
import com.yatang.sc.facade.dubboservice.CommonUtilDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import com.yatang.sc.facade.enums.PmPurchaseReceiptStatus;
import com.yatang.sc.facade.flow.PurchaseFlowService;
import com.yatang.sc.facade.purchase.PurchaseOrderProcessor;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.provider.order.ProviderOrderMsg;
import com.yatang.sc.order.provider.order.ProviderOrderStatus;
import com.yatang.xc.mbd.biz.image.dubboservice.ImageDubboService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("pmPurchaseReceiptWriteDubboService")
public class PmPurchaseReceiptWriteDubboServiceImpl implements PmPurchaseReceiptWriteDubboService {

    @Autowired
    private PmPurchaseReceiptService pmPurchaseReceiptService;

    @Autowired
    private PurchaseFlowService purchaseFlowService;

    @Autowired
    private ImageDubboService imageDubboService;

    @Autowired
    private PmPurchaseOrderService pmPurchaseOrderService;

    @Autowired
    private CommonUtilDubboService commonUtilDubboService;

    @Autowired
    private PurchaseOrderProcessor purchaseOrderProcessor;

    @Autowired
    private OrganizationService organizationService;

    // 商品服务
    @Autowired
    private ProductScIndexDubboService indexDubboService;

    @Resource(name = "purcharseSettlementMQProducer")
    private SimpleMQProducer purchaseSettlementMQProducer;


    @Autowired
    OrderMessageSender orderMessageSender;


    @Override
    public Response<Boolean> createReceipt(String orderCode) {
        if (log.isInfoEnabled()) {
            log.info("----------根据采购单号新增收货单>>createReceipt():orderCode=" + orderCode + "----------");
        }
        Response<Boolean> response = new Response<Boolean>();
        try {
            PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService
                    .insertReceiptAndItmsByOrderCode(orderCode);
            if (pmPurchaseReceiptPo != null) {
                PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService
                        .selectPmPurchaseOrderByPurchaseReceiptNo(pmPurchaseReceiptPo.getPurchaseReceiptNo());
                Response<byte[]> generateBarCodeResponse = commonUtilDubboService
                        .generateBarCode(pmPurchaseReceiptPo.getPurchaseReceiptNo());// 生成条形码的服务
                byte[] barCodeStream = generateBarCodeResponse.getResultObject();
                if (!CommonsEnum.RESPONSE_200.getCode().equals(generateBarCodeResponse.getCode())) {// 序列号生成失败
                    return BeanConvertUtils.convert(generateBarCodeResponse, Response.class);
                }
                // 调用上传服务
                Response<String> uploadResponse = imageDubboService.uploadFile(barCodeStream, "png");
                if (!CommonsEnum.RESPONSE_200.getCode().equals(uploadResponse.getCode())) {// 序列号生成失败
                    return BeanConvertUtils.convert(uploadResponse, Response.class);
                }
                String barCodeUrl = uploadResponse.getResultObject();// 条形码后缀
                boolean insertBarCodeSuccess = pmPurchaseOrderService.updateBarCodeUrlById(pmPurchaseOrderPo.getId(),
                        barCodeUrl);
                if (insertBarCodeSuccess) {
                    response.setCode(CommonsEnum.RESPONSE_200.getCode());
                    response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    response.setResultObject(true);
                    response.setSuccess(true);
                } else {
                    response.setCode(CommonsEnum.RESPONSE_10034.getCode());
                    response.setSuccess(false);
                    response.setErrorMessage(CommonsEnum.RESPONSE_10034.getName());
                    response.setResultObject(false);
                }

            } else {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setResultObject(false);
            }
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<Boolean> updateYXFStatus(UpdateReceiptYXFStatusDto updateReceiptYXFStatusDto) {
        // 2.入库单状态= ACCEPT，更新收货单状态为“已下发(YXF)”
        Response<Boolean> response = new Response<>();
        PmPurchaseReceiptPo purchaseReceiptPo = BeanConvertUtils.convert(updateReceiptYXFStatusDto,
                PmPurchaseReceiptPo.class);
        // 状态:0:待下发，1：已下发，2:已收货，3:已取消，4：异常
        purchaseReceiptPo.setStatus(1);
        try {
            pmPurchaseReceiptService.updateByPurchaseReceiptNoSelective(purchaseReceiptPo);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(true);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(false);
        }
        return response;
    }


    @Override
    public Response<Boolean> updateYSHStatus(PurchaseOrderConfirmDto purchaseOrderConfirmDto) {
        Response<Boolean> response = purchaseFlowService.updateYSHStatus(purchaseOrderConfirmDto);
        if (null != response && CommonsEnum.RESPONSE_200.getCode().equals(response.getCode())) {
            PurchaseConfirmEntryOrderDto entryOrderDto = purchaseOrderConfirmDto.getEntryOrder();
            PmPurchaseReceiptPo receiptPo = pmPurchaseReceiptService.selectByPurchaseReceiptNo(entryOrderDto.getEntryOrderCode());
            PmPurchaseReceiptDto receiptDto = BeanConvertUtils.convert(receiptPo, PmPurchaseReceiptDto.class);
            receiptDto.setDeliveryVoucherNo(StringUtils.isBlank(entryOrderDto.getDeliveryVoucherNo())
                    ? "" : entryOrderDto.getDeliveryVoucherNo());
            receiptDto.setLogWhRecNo(StringUtils.isBlank(entryOrderDto.getSaleOrderCodeWMS())
                    ? entryOrderDto.getOutBizCode() : entryOrderDto.getSaleOrderCodeWMS());
            pushPurchaseReceiptSettlementMsg(receiptDto);
        }
        return response;
    }


    @Override
    public Response<Boolean> updateYQXStatus(UpdateReceiptYQXStatusDto updateReceiptYQXStatusDto) {
        // 4.入库单状态= CANCELED，更新收货单状态为 “已取消(YQX)”,更新采购单状态为“已关闭”,
        // 取消数量=采购数量-已收货数量，取消原因填写超期关闭
        return purchaseFlowService.updateYQXStatus(updateReceiptYQXStatusDto);
    }


    @Override
    public Response<Boolean> updateYCStatus(UpdateReceiptYCStatusDto updateReceiptYCStatusDto) {
        // 6.入库单状态= EXCEPTION、REJECT，更新收货单状态为 “异常(YC)”。异常状态入库单需人工分析处理
        Response<Boolean> response = new Response<>();
        PmPurchaseReceiptPo purchaseReceiptPo = BeanConvertUtils.convert(updateReceiptYCStatusDto,
                PmPurchaseReceiptPo.class);
        // 状态:0:待下发，1：已下发，2:已收货，3:已取消，4：异常
        purchaseReceiptPo.setStatus(4);
        try {
            pmPurchaseReceiptService.updateByPurchaseReceiptNoSelective(purchaseReceiptPo);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(true);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(false);
        }
        return response;
    }

    @Override
    public Response<Void> pushPurchaseReceiptSettlementMsg(PmPurchaseReceiptDto receiptDto) {
        try {
            // 推送采购入库数据给财务中心做结算
            purchaseSettlementMQProducer.sendMsg(buildSettlementData(receiptDto));
            log.info("....................................................当前推送收货单号为：" + receiptDto.getPurchaseReceiptNo());
        } catch (Exception e) {
            log.error("pushPurchaseReceiptSettlementMsg-->{}，推送采购收货结算数据出错啦！ 采购收货单号：", receiptDto.getPurchaseReceiptNo(), e);
            //throw new RuntimeException("推送采购收货结算数据出错，操作终止");
        }
        return null;
    }


    @Override
    public Response<Boolean> pushPurchaseReceiptToMQ(String purchaseOrderNo) {
        Response<Boolean> response = new Response<Boolean>();
        try {

            log.info("推送采购收货单到GLINK，采购订单编号{}", purchaseOrderNo);
            purchaseOrderProcessor.sendToGlink(purchaseOrderNo);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(true);
        } catch (Exception e) {
            log.info("推送采购收货单到GLINK出错了，采购订单号为：{}", purchaseOrderNo);
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(false);
        }
        return response;
    }


    /**
     * @param receiptDto
     * @Description: 封装采购入库结算数据（财务中心用）
     * @author tankejia
     * @date 2017/12/11- 10:44
     */
    private PurchaseSettlementData buildSettlementData(PmPurchaseReceiptDto receiptDto) {
        // 查询采购收货单对应的采购订单
        PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService
                .selectPmPurchaseOrderByPurchaseReceiptNo(receiptDto.getPurchaseReceiptNo());

        Response<BranchCompanyDto> companyDtoResponse = organizationService.querySimpleByBranchCompanyId(pmPurchaseOrderPo.getBranchCompanyId());
        if (!companyDtoResponse.isSuccess() || null == companyDtoResponse.getResultObject()) {
            log.info("采购订单分公司id有误或organizationService查询分公司信息出错", pmPurchaseOrderPo.getBranchCompanyId());
            throw new RuntimeException("采购订单分公司id有误或organizationService查询分公司信息出错");
        }

        PurchaseSettlementData settlementData = new PurchaseSettlementData();

        StringBuffer sb = new StringBuffer(CommonsEnum.RESPONSE_20304.getName());
        settlementData.setId(sb.append(pmPurchaseOrderPo.getId()).toString());
        settlementData.setSubCompId(companyDtoResponse.getResultObject().getId());
        settlementData.setSubCompName(companyDtoResponse.getResultObject().getName());
        settlementData.setLocaleNo(StringUtils.isBlank(pmPurchaseOrderPo.getAdrTypeCode()) ? "" : pmPurchaseOrderPo.getAdrTypeCode());
        settlementData.setLocaleName(StringUtils.isBlank(pmPurchaseOrderPo.getAdrTypeName()) ? "" : pmPurchaseOrderPo.getAdrTypeName());
        // 第三方仓库目前没有传交货单号过来(2017.12.11)
        settlementData.setDeliveryVoucherNo(StringUtils.isBlank(receiptDto.getDeliveryVoucherNo())
                ? "" : receiptDto.getDeliveryVoucherNo());
        settlementData.setWhVoucherNo(StringUtils.isBlank(receiptDto.getPurchaseReceiptNo()) ? "" : receiptDto.getPurchaseReceiptNo());
        settlementData.setPoNo(StringUtils.isBlank(pmPurchaseOrderPo.getPurchaseOrderNo()) ? "" : pmPurchaseOrderPo.getPurchaseOrderNo());
        // 第三方物流仓库记录号
        settlementData.setLogWhRecNo(StringUtils.isBlank(receiptDto.getLogWhRecNo()) ? "" : receiptDto.getLogWhRecNo());
        if (0 == pmPurchaseOrderPo.getPurchaseOrderType()) {
            settlementData.setOrderTypeNo(CommonsEnum.RESPONSE_20300.getCode());
            settlementData.setOrderType(CommonsEnum.RESPONSE_20300.getName());
        } else if (1 == pmPurchaseOrderPo.getPurchaseOrderType()) {
            settlementData.setOrderTypeNo(CommonsEnum.RESPONSE_20302.getCode());
            settlementData.setOrderType(CommonsEnum.RESPONSE_20302.getName());
        } else {
            settlementData.setOrderTypeNo(CommonsEnum.RESPONSE_20301.getCode());
            settlementData.setOrderType(CommonsEnum.RESPONSE_20301.getName());
        }
        settlementData.setWhInTypeNo(CommonsEnum.RESPONSE_20060.getCode());
        settlementData.setWhInType(CommonsEnum.RESPONSE_20060.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        settlementData.setWhInDate(null == receiptDto.getReceivedTime() ? "" : sdf.format(receiptDto.getReceivedTime()));
        settlementData.setVendorNo(StringUtils.isBlank(pmPurchaseOrderPo.getSpAdrNo()) ? "" : pmPurchaseOrderPo.getSpAdrNo());
        settlementData.setVendorName(StringUtils.isBlank(pmPurchaseOrderPo.getSpAdrName()) ? "" : pmPurchaseOrderPo.getSpAdrName());
        settlementData.setPurchaseActualItems(buildPurchaseActualItems(receiptDto));
        return settlementData;
    }

    private List<PurchaseActualItem> buildPurchaseActualItems(PmPurchaseReceiptDto receiptDto) {
        List<PmPurchaseReceiptItemsPo> receiptItemsPos = pmPurchaseReceiptService.queryReceiptItemsByReceiptId(String.valueOf(receiptDto.getId()));
        List<PurchaseActualItem> actualItems = new ArrayList<>();

        if (CollectionUtils.isEmpty(receiptItemsPos)) {
            log.info("该收货单下面没有相应的商品, 收货单id：{}", receiptDto.getPurchaseReceiptNo());
            throw new RuntimeException("该收货单下面没有相应的商品，操作终止");
        }

        for (PmPurchaseReceiptItemsPo receiptItemsPo : receiptItemsPos) {
            PurchaseActualItem actualItem = new PurchaseActualItem();

            // 取实际收货的商品
            if (null != receiptItemsPo.getReceivedNumber() && 0 < receiptItemsPo.getReceivedNumber()) {
                actualItem.setInQty(null == receiptItemsPo.getReceivedNumber() ? "" : receiptItemsPo.getReceivedNumber().toString());

                // 从采购订单item表取商品信息
                PmPurchaseOrderItemPo orderItemPo = pmPurchaseOrderService.selectPmPurchaseOrderItemByPurchaseReceiptItemId(receiptItemsPo.getId());
                if (null == orderItemPo) {
                    log.error("跟收货单item对应的采购单item信息有误, 采购单item表id：{}", receiptItemsPo.getPurchaseOrderItemsId());
                    throw new RuntimeException("根据收货单item查询不到采购单item信息，操作终止");
                }
                actualItem.setGoodsNo(orderItemPo.getProductCode());
                actualItem.setGoodsName(orderItemPo.getProductName());

                // 查询商品分类信息
                Response<ProductIndexDto> dtoResponse = indexDubboService.queryByProductId(orderItemPo.getProductId());
                if (!dtoResponse.isSuccess() || null == dtoResponse.getResultObject()) {
                    log.error("收货结算时根据商品id查询不到相应的分类信息，商品id：{}", orderItemPo.getProductId());
                    throw new RuntimeException("收货结算时根据商品id查询不到相应的分类信息，操作终止");
                }

                // 如果没有四级分类则取三级分类
                actualItem.setGoodsTypeNo(null == dtoResponse.getResultObject().getFourthCategoryId()
                        ? dtoResponse.getResultObject().getThirdLevelCategoryId() : dtoResponse.getResultObject().getFourthCategoryId());
                actualItem.setGoodsType(null == dtoResponse.getResultObject().getFourthLevelCategoryName()
                        ? dtoResponse.getResultObject().getThirdLevelCategoryName() : dtoResponse.getResultObject().getFourthLevelCategoryName());

                actualItem.setDealerprice(PricingUtil.roundPrice(orderItemPo.getPurchasePrice().doubleValue()));
                // 进货金额（含税）= 采购价格（含税）* 入库数量
                actualItem.setPurchaseAmt(PricingUtil.roundPrice(Double.valueOf(actualItem.getInQty()) * actualItem.getDealerprice()));
                actualItem.setTaxRate(PricingUtil.roundPrice(orderItemPo.getInputTaxRate().doubleValue() / 100));
                // 未税金额 = 进货金额（含税）/ （1 + 税率）
                actualItem.setOutTaxAmt(PricingUtil.roundPrice(actualItem.getPurchaseAmt() / (1 + actualItem.getTaxRate())));
                // 税额 = 进货金额（含税）- 未税金额
                actualItem.setTaxAmt(PricingUtil.roundPrice(actualItem.getPurchaseAmt() - actualItem.getOutTaxAmt()));
                actualItems.add(actualItem);
            }
        }
        return actualItems;
    }

    @Override
    public Response<Void> createPurchaseReceiptASN(PmPurchaseReceiptDetailDto detailDto) {


        log.info("dubbo----createPurchaseReceiptASN>>ASN录入请求参数:{}", JSON.toJSONString(detailDto));

        Response<Void> response = new Response<>();
        try {
            Response<String> rep = purchaseFlowService.createPurchaseReceiptASN(detailDto);

            if (CommonsEnum.RESPONSE_200.getCode().equals(rep.getCode())) {
                //发送mq
                String receiptId = rep.getResultObject();
                ProviderOrderMsg providerOrderMsg = new ProviderOrderMsg();
                providerOrderMsg.setStatus(ProviderOrderStatus.ASN);
                providerOrderMsg.setBody(receiptId);
                orderMessageSender.sendProviderOrderMsg(detailDto.getPmPurchaseReceipt().getSaleOrderId(), providerOrderMsg);
            } else {
                return BeanConvertUtils.convert(rep, Response.class);
            }
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setSuccess(true);
        return response;
    }

    @Override
    public Response<Void> updateSplitPurchaseReceipt(Long purchaseReceiptId, String saleOrderId) {
        log.info("dubbo--updateSplitPurchaseReceipt>更新拆分后的订单的请求参数:purchaseReceiptId:{},saleOrderId:{}", purchaseReceiptId, saleOrderId);
        Response<Void> response = new Response<>();
        try {

            if (StringUtils.isBlank(saleOrderId)) {
                log.error("dubbo--updateSplitPurchaseReceipt>更新拆分后的订单的请求参数出现为空的情况:saleOrderId:{}", saleOrderId);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setErrorMessage("销售订单id为空");
                return response;
            }

            //查询是否存在该订单
            PmPurchaseReceiptPo receiptPo = pmPurchaseReceiptService.queryReceiptById(purchaseReceiptId);
            if (receiptPo == null) {
                log.error("dubbo--updateSplitPurchaseReceipt>更新拆分后的订单的请求参数通过收货单id:{}查询收货单不存在", purchaseReceiptId);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setErrorMessage("根据收货单id" + purchaseReceiptId + "查询收货单失败为空");
                return response;
            }


            PmPurchaseReceiptPo pmPurchaseReceiptPo = new PmPurchaseReceiptPo();
            pmPurchaseReceiptPo.setId(purchaseReceiptId);
            pmPurchaseReceiptPo.setSaleOrderId(saleOrderId);
            pmPurchaseReceiptPo.setModifyTime(new Date());
            Boolean updateSuccess = pmPurchaseReceiptService.updateReceipt(pmPurchaseReceiptPo);
            if (!updateSuccess) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                return response;
            }
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Void> updatePmTicket(PmUploadTicketParamDto ticketParamDto) {
        log.info("dubbo----updatePmTicket>更新小票请求参数:{}", JSON.toJSONString(ticketParamDto));
        Response<Void> response = new Response<>();

        try {
            //收货单id
            Long pmReceiptId = ticketParamDto.getPmReceiptId();
            PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService.queryReceiptById(pmReceiptId);
            if (pmPurchaseReceiptPo == null) {
                log.error("dubbo----updatePmTicket>更新小票请求参数:{},查询未查询到收货单", pmReceiptId);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("");
                return response;
            }
            //收货单状态
            Integer status = pmPurchaseReceiptPo.getStatus();
            //已下发和已收货状态才执行处理
            if (!PmPurchaseReceiptStatus.RECEIPT_DELIVERED_STATUS.getCode().equals(status) && !PmPurchaseReceiptStatus.RECEIPT_RECEIVED_STATUS.getCode().equals(status)) {
                log.error("dubbo----updatePmTicket>更新小票请求参数:{}不能执行更新请求url请求", status);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("当前收货单暂不能执行上传发票操作");
                return response;
            }


            Integer ticketUploadStatus = ticketParamDto.getStatus();
            PmPurchaseReceiptPo receiptPo = new PmPurchaseReceiptPo();
            receiptPo.setModifyUserId(ticketParamDto.getModifyUserId());
            receiptPo.setModifyTime(new Date());
            receiptPo.setId(pmReceiptId);
            //删除
            if (0 == ticketUploadStatus) {
                pmPurchaseReceiptPo.setTicketUrl("");
            } else if (1 == ticketUploadStatus) {
                pmPurchaseReceiptPo.setTicketUrl(ticketParamDto.getTicketUrl());
            }
            Boolean updateSuccess = pmPurchaseReceiptService.updateReceipt(pmPurchaseReceiptPo);
            if (!updateSuccess) {
                log.error("dubbo----updatePmTicket>更新失败:{}", pmReceiptId);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("上传发票操作失败,请稍后重试");
                return response;
            }
            //发送mq
            ProviderOrderMsg providerOrderMsg = new ProviderOrderMsg();
            providerOrderMsg.setStatus(ProviderOrderStatus.PROVIDER_SINGED_REQ);
            providerOrderMsg.setBody(ticketParamDto.getTicketUrl());
            orderMessageSender.sendProviderOrderMsg(pmPurchaseReceiptPo.getSaleOrderId(), providerOrderMsg);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);

        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }
}
