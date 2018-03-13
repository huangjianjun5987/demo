package com.yatang.sc.glink.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.PushOrderStateRequestDto;
import com.yatang.sc.glink.dto.PushOrdersDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderRepDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderRepResultDto;
import com.yatang.sc.glink.dto.entryOrder.OrderLinesRepDto;
import com.yatang.sc.glink.dto.entryOrder.SelectEntryOrderDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmOrderLinesDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmResultDto;
import com.yatang.sc.glink.dto.purchase.PurchaseRefundConfirmResultResultDto;
import com.yatang.sc.glink.dto.saleOrder.SaleOrderRepDto;
import com.yatang.sc.glink.dto.saleOrder.SalePackageDto;
import com.yatang.sc.glink.dto.saleOrder.SelectOrderCodeOKDto;
import com.yatang.sc.glink.dto.saleOrder.SelectOrderCodeOKResultDto;
import com.yatang.sc.glink.service.kidd.GLinkPurchaseProxyService;
import com.yatang.sc.glink.service.kidd.GLinkSaleOrderProxyService;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.orderNotify.KiddOrderNoticeInfoDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmOrderLinesDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderMqDto;
import com.yatang.sc.logistics.LogisticsDictionary;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @描述: 际链订单通知服务(采购订单和销售)
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/28 16:35
 * @版本: v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GLinkOrderServiceImpl {

    @Resource(name = "orderMessageSender")
    private OrderMessageSender saleOrderMQProducer;        // 销售订单mq

    @Resource(name = "entryOrderMQProducer")
    private SimpleMQProducer entryOrderMQProducer;        // 采购订单

    @Resource(name = "purcharseRefundMQProducer")
    private SimpleMQProducer purchaseRefundMQProducer;    // 采购退货

    @Resource(name = "returnOrderMQProducer")
    private SimpleMQProducer returnOrderMQProducer;        // 退货换货单

    private final GLinkSaleOrderProxyService gLinkSaleOrderProxyService;

    // GLINK查询收货单明细的接口
    @Autowired
    private GLinkPurchaseProxyService gLinkPurchaseProxyService;


    /**
     * 际链订单通知(销售订单和采购订单)
     *
     * @param noticeRequestInfoDto
     * @return
     */
    @EventListener
    public Response<Void> getOrderNotice(PushOrderStateRequestDto noticeRequestInfoDto) {
        log.info("service--getOrderNotice>>际链订单通知分发信息:{}", JSON.toJSONString(noticeRequestInfoDto));
        Response<Void> response = new Response<>();
        try {
            if (CollectionUtils.isEmpty(noticeRequestInfoDto.getOrders())) {
                response.setCode("1");
                response.setErrorMessage("订单数据pushOrdersDtos不能为空");
                return response;
            }
            for (PushOrdersDto order : noticeRequestInfoDto.getOrders()) {
                if (null == order) {
                    continue;
                }
                if (null == order.getOrderType()) {
                    continue;
                }

                if (order.getOrderType().equals(KiddOrderLogisticsType.KIDD_CGRK)) {// 采购入库
                    dealWithPurchaseRK(order);
                } else if (KiddOrderLogisticsType.KIDD_CGTHCK.equalsIgnoreCase(order.getOrderType())) {
                    dealWithCGTH(order);
                } else if (order.getOrderCode().contains("H") || order.getOrderCode().contains("T")) {// 退货或者换货单返回状态
                    returnOrderConfirm(order);

                } else {

                    KiddOrderNoticeInfoDto kiddOrderNoticeInfoDto = new KiddOrderNoticeInfoDto();
                    kiddOrderNoticeInfoDto.setOrderCode(order.getOrderCode());
                    kiddOrderNoticeInfoDto.setRemark(order.getRemark());
                    kiddOrderNoticeInfoDto.setDeliveryTime(order.getDeliveryTime());
                    kiddOrderNoticeInfoDto.setDriverName(order.getDriverName());
                    kiddOrderNoticeInfoDto.setDriverPhone(order.getDriverPhone());
                    kiddOrderNoticeInfoDto.setOrderType(order.getOrderType());
                    if (StringUtils.isNotBlank(order.getCurrentStatus())) {
                        if (KiddOrderLogisticsType.KIDD_READY.equals(order.getCurrentStatus())) {// 待提货
                            getLogisticsInfo(kiddOrderNoticeInfoDto, order.getOwnerCode());// 补充物流信息
                        }
                        kiddOrderNoticeInfoDto.setCurrentStatus(order.getCurrentStatus());//
                        OrderMessage saleOrderMessage = new OrderMessage();
                        saleOrderMessage.setMssageType(OrderMessageType.KIDD_ORDER_STATUS_NOTIFY);
                        saleOrderMessage.setOrderId(order.getOrderCode());
                        saleOrderMessage.setBody(JSON.toJSONString(kiddOrderNoticeInfoDto));
                        saleOrderMQProducer.sendMsg(saleOrderMessage);
                    } else {
                        log.error("service--getOrderNotice>>{}:销售订单订单状态异常:{}", order.getOrderCode(),
                                JSON.toJSONString(order));
                    }
                }
            }

            response.setCode("0");
            response.setSuccess(true);

        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode("1");
            response.setSuccess(false);
            response.setErrorMessage("订单失败");
        }
        return response;
    }


    private Response<Void> dealWithCGTH(PushOrdersDto order) {
        Response<Void> response = new Response<>();
        KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = new KiddPurchaseOrderConfirmDto();
        KiddConfirmEntryOrderDto kiddConfirmEntryOrderDto = new KiddConfirmEntryOrderDto();
        kiddConfirmEntryOrderDto.setEntryOrderCode(order.getOrderCode());
        kiddConfirmEntryOrderDto.setOwnerCode(order.getOwnerCode());
        kiddConfirmEntryOrderDto.setWarehouseCode(order.getOrderCodeWMS());
        kiddConfirmEntryOrderDto.setStatus(order.getCurrentStatus());
        kiddConfirmEntryOrderDto.setEntryOrderType(order.getOrderType());
        kiddConfirmEntryOrderDto.setOperateTime(order.getOperatorTime());
        kiddConfirmEntryOrderDto.setSaleOrderCodeWMS(order.getOrderCodeWMS());
        kiddPurchaseOrderConfirmDto.setEntryOrder(kiddConfirmEntryOrderDto);

        if (KiddOrderLogisticsType.KIDD_PARTDELIVERED.equals(order.getCurrentStatus()) || KiddOrderLogisticsType.KIDD_DELIVERED.equals(order.getCurrentStatus())) {
            PurchaseRefundConfirmDto purchaseRefundConfirmDto = new PurchaseRefundConfirmDto();
            Calendar startTime = Calendar.getInstance();
            startTime.add(Calendar.MONTH, -1);// 查询开始时间
            Calendar endTime = Calendar.getInstance();
            endTime.add(Calendar.MONTH, 1);// 查询截止时间
            purchaseRefundConfirmDto.setStartTime(startTime.getTime());
            purchaseRefundConfirmDto.setEndTime(endTime.getTime());
            purchaseRefundConfirmDto.setServerOrgCode(order.getOwnerCode());// 设置子公司编码
            // purchaseRefundConfirmDto.setWarehouseCode(order.getOrderCodeWMS());//
            // 设置仓库编码
            purchaseRefundConfirmDto.setOrderType(KiddOrderLogisticsType.KIDD_CGTHCK);// 入库单业务类型
            purchaseRefundConfirmDto.setDeliveryOrderCode(order.getOrderCode());
            purchaseRefundConfirmDto.setPageNo(1);
            purchaseRefundConfirmDto.setPageSize(499);
            log.info("根据需求文档要求组装请求数据，调用glink方法gLinkPurchaseProxyService.confirm({})",
                    JSONObject.toJSONString(purchaseRefundConfirmDto));
            GlinkResponseDto<PurchaseRefundConfirmResultDto> glinkResponseDto = gLinkPurchaseProxyService
                    .confirm(purchaseRefundConfirmDto);
            log.info("调用glink获取到退货单明细数据：{}", JSONObject.toJSONString(glinkResponseDto));
            if (glinkResponseDto == null || glinkResponseDto.getData() == null
                    || glinkResponseDto.getData().getResult() == null
                    || glinkResponseDto.getData().getResult().isEmpty()) {
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("glink 查询退货单明细失败！");
                return response;
            }
            if (0 == glinkResponseDto.getCode()) {
                PurchaseRefundConfirmResultDto purchaseRefundConfirmResultDto = glinkResponseDto.getData();
                if (null != purchaseRefundConfirmResultDto && 1 == purchaseRefundConfirmResultDto.getTotal()) {
                    // 查询成功
                    PurchaseRefundConfirmResultResultDto purchaseRefundConfirmResultResultDto = purchaseRefundConfirmResultDto
                            .getResult().get(0);

                    List<PurchaseRefundConfirmOrderLinesDto> orderLines = purchaseRefundConfirmResultResultDto
                            .getOrderLines();
                    List<KiddConfirmOrderLinesDto> kiddConfirmOrderLinesDtos = new ArrayList<>();
                    for (PurchaseRefundConfirmOrderLinesDto linesDtos : orderLines) {
                        KiddConfirmOrderLinesDto kiddConfirmOrderLinesDto = new KiddConfirmOrderLinesDto();
                        kiddConfirmOrderLinesDto.setItemId(linesDtos.getItemCode());// 仓库储存商品编码
                        kiddConfirmOrderLinesDto.setOrderLineNo(linesDtos.getOrderLineNo());
                        kiddConfirmOrderLinesDto.setActualQty(linesDtos.getActualQty());// 设置实际收货数量
                        kiddConfirmOrderLinesDtos.add(kiddConfirmOrderLinesDto);

                    }
                    kiddPurchaseOrderConfirmDto.setOrderLines(kiddConfirmOrderLinesDtos);
                }
            } else {
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("glink 查询退货单明细失败！,响应信息：" + JSONObject.toJSONString(glinkResponseDto));
                return response;
            }
        }
        log.info("发送MQ际链回传消息信息" + JSON.toJSONString(kiddPurchaseOrderConfirmDto));
        purchaseRefundMQProducer.sendMsg(kiddPurchaseOrderConfirmDto);
        response.setCode("0");
        response.setSuccess(true);
        return response;
    }


    /**
     * 获取物流信息
     *
     * @param kiddOrderNoticeInfoDto 通知
     * @param ownerCode              分公司编码
     */
    private void getLogisticsInfo(KiddOrderNoticeInfoDto kiddOrderNoticeInfoDto, String ownerCode) {

        SelectOrderCodeOKDto selectOrderCodeOKResultDto = new SelectOrderCodeOKDto();
        DateTime startDateTime = DateTime.now().plusMonths(-1);
        DateTime endDateTime = DateTime.now().plusMonths(1);
        selectOrderCodeOKResultDto.setStartTime(startDateTime.toDate());
        selectOrderCodeOKResultDto.setEndTime(endDateTime.toDate());
        selectOrderCodeOKResultDto.setOrgCode(ownerCode);
        selectOrderCodeOKResultDto.setOrderType(kiddOrderNoticeInfoDto.getOrderType());
        selectOrderCodeOKResultDto.setDeliveryOrderCode(kiddOrderNoticeInfoDto.getOrderCode());
        GlinkResponseDto<SelectOrderCodeOKResultDto> response = gLinkSaleOrderProxyService
                .select(selectOrderCodeOKResultDto);
        log.info("GLink查询订单详情:{}", JSON.toJSONString(response));
        if (null == response || response.getCode() != 0) {
            throw new RuntimeException("GLink查询订单详情错误");
        }
        log.info("GLink 订单物流信息:{}", JSON.toJSONString(response.getData().getResult()));
        if (response.getData() == null || CollectionUtils.isEmpty(response.getData().getResult())) {
            log.info("GLink查询订单详情为空:{}", response.getMessage());
            throw new RuntimeException("GLink查询订单详情为空:" + response.getMessage());
        }
        if (response.getData().getResult().size() != 0) {
            SaleOrderRepDto saleOrderRepDto = response.getData().getResult().get(0);
            String status = saleOrderRepDto.getStatus();
            log.info("{}:订单发货状态：{}", kiddOrderNoticeInfoDto.getOrderCode(), status);
            List<SalePackageDto> packages = saleOrderRepDto.getPackages();
            if (packages != null && packages.size() > 0) {
                StringBuffer expressCode = new StringBuffer();
                for (SalePackageDto packageDto : packages) {
                    if (packageDto != null && !StringUtils.isEmpty(packageDto.getExpressCode())) {
                        if (expressCode.length() > 0) {
                            expressCode.append(";");
                        }
                        expressCode.append(packageDto.getExpressCode());
                    }
                }
                kiddOrderNoticeInfoDto.setCargoDtos(saleOrderRepDto.getCargo());// 物流信息
                kiddOrderNoticeInfoDto.setExpressCode(expressCode.toString());
                String logisticsCode = packages.get(0).getLogisticsCode();
                String logisticsName = packages.get(0).getLogisticsName();
                if (StringUtils.isEmpty(logisticsName)) {
                    logisticsName = LogisticsDictionary.getlogisticsName(logisticsCode);
                }
                kiddOrderNoticeInfoDto.setLogisticsName(logisticsName);
                kiddOrderNoticeInfoDto.setLogisticsCode(logisticsCode);
            }
            log.info("READY{},kiddOrderNoticeInfoDto{}", kiddOrderNoticeInfoDto.getOrderCode(),
                    JSON.toJSONString(kiddOrderNoticeInfoDto));

        }

    }


    /**
     * 推送退换货单状态
     *
     * @param ordersDto
     */
    private void returnOrderConfirm(PushOrdersDto ordersDto) {
        KiddReturnOrderMqDto kiddReturnOrderMqDto = new KiddReturnOrderMqDto();// 发送mq实体类
        if (KiddOrderLogisticsType.KIDD_FULFILLED.equals(ordersDto.getCurrentStatus())
                && (KiddOrderLogisticsType.KIDD_THRK.equals(ordersDto.getOrderType())
                || KiddOrderLogisticsType.KIDD_HHRK.equals(ordersDto.getOrderType()))) {// 退换货入库通知状态
            SelectEntryOrderDto selectEntryOrderDto = new SelectEntryOrderDto();
            Calendar startTime = Calendar.getInstance();
            startTime.add(Calendar.MONTH, -1);// 查询开始时间
            Calendar endTime = Calendar.getInstance();
            endTime.add(Calendar.MONTH, 1);// 查询截止时间
            selectEntryOrderDto.setStartTime(startTime.getTime());
            selectEntryOrderDto.setEndTime(endTime.getTime());
            selectEntryOrderDto.setOwnerCode(ordersDto.getOwnerCode());// 设置子公司编码
            // 传入入库单号，只会有一个结果，已跟cain确认
            selectEntryOrderDto.setEntryOrderCode(ordersDto.getOrderCode());// 退货入库单号
            selectEntryOrderDto.setPageNo(1);// 要请求第几页
            selectEntryOrderDto.setPageSize(1);// 每页显示条数
            log.info("根据需求文档要求组装请求数据，调用glink方法EntryOrderService.select({})",
                    JSONObject.toJSONString(selectEntryOrderDto));
            GlinkResponseDto<EntryOrderRepDto> glinkResponseDto = gLinkPurchaseProxyService.select(selectEntryOrderDto);
            log.info("调用glink获取到收货明细数据：{}", JSONObject.toJSONString(glinkResponseDto));
            if (glinkResponseDto != null) {
                if (0 == glinkResponseDto.getCode()) {
                    EntryOrderRepDto entryOrderRepDto = glinkResponseDto.getData();
                    if (null != entryOrderRepDto && 1 == entryOrderRepDto.getTotal()) {
                        List<EntryOrderRepResultDto> result = entryOrderRepDto.getResult();
                        if (result != null && result.size() == 1) {
                            EntryOrderRepResultDto entryOrderRepResultDto = result.get(0);
                            kiddReturnOrderMqDto.setReturnOrderCode(ordersDto.getOrderCode());// 退货单编码
                            kiddReturnOrderMqDto.setWarehouseCode(ordersDto.getOrderCodeWMS());// 仓库编码
                            kiddReturnOrderMqDto.setOrderType(ordersDto.getOrderType());// 单据类型
                            kiddReturnOrderMqDto.setStatus(ordersDto.getCurrentStatus());// 单据状态
                            List<KiddOrderLinesDto> orderLinesRepDtos1 = BeanConvertUtils
                                    .convertList(entryOrderRepResultDto.getOrderLines(), KiddOrderLinesDto.class);
                            kiddReturnOrderMqDto.setOrderLinesDtos(orderLinesRepDtos1);
                            log.info("发送际链的退货结果MQ消息:{}", JSON.toJSONString(kiddReturnOrderMqDto));
                            returnOrderMQProducer.sendMsg(kiddReturnOrderMqDto);
                            return;
                        }

                    }
                }
            }else {
                throw new RuntimeException("GLink查询退货入库单为空");
            }
        } else {// 换货出库
            if (KiddOrderLogisticsType.KIDD_SIGNED.equals(ordersDto.getCurrentStatus())
                    || KiddOrderLogisticsType.KIDD_READY.equals(ordersDto.getCurrentStatus())) {// 只处理正常签收和全部出库状态

                kiddReturnOrderMqDto.setOrderType(KiddOrderLogisticsType.KIDD_HHCK);// 设置类型(换货出库)
                kiddReturnOrderMqDto.setReturnOrderCode(
                        ordersDto.getOrderCode().substring(0, ordersDto.getOrderCode().length() - 1));// 设置订单编码
                kiddReturnOrderMqDto.setStatus(ordersDto.getCurrentStatus().equals(KiddOrderLogisticsType.KIDD_SIGNED)
                        ? ordersDto.getCurrentStatus() : KiddOrderLogisticsType.KIDD_DELIVERED);// 设置状态
                kiddReturnOrderMqDto.setWarehouseCode(ordersDto.getOrderCodeWMS());// 仓库编码
                returnOrderMQProducer.sendMsg(kiddReturnOrderMqDto);
            }
        }

    }


    /**
     * 采购
     * @param order
     * @return
     */
    private Response<Void> dealWithPurchaseRK(PushOrdersDto order) {

        log.info("GLink采购入库单请求通知参数:{}", JSON.toJSONString(order));

        Response<Void> response = new Response<>();

        try {
            KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = new KiddPurchaseOrderConfirmDto();
            KiddConfirmEntryOrderDto convert = new KiddConfirmEntryOrderDto();
            convert.setEntryOrderCode(order.getOrderCode());
            convert.setOwnerCode(order.getOwnerCode());
            convert.setWarehouseCode(order.getOrderCodeWMS());
            convert.setStatus(order.getCurrentStatus());
            convert.setEntryOrderType(order.getOrderType());

            kiddPurchaseOrderConfirmDto.setEntryOrder(convert);
            if (KiddOrderLogisticsType.KIDD_FULFILLED.equals(kiddPurchaseOrderConfirmDto.getEntryOrder().getStatus())) {
                // 接口入参
                // 参数名 数据
                // 类型 必填 说明
                // startTime dateTime 是 查询开始时间 值：当前系统时间-1月
                // endTime dateTime 是 查询截止时间 值：当前系统时间+1月
                // ownerCode string 是 采购单仓库编码
                // orderType string 是 入库单业务类型 值：CGRK=采购入库
                // entryOrderCode string 是 采购收货单号
                // pageNo int 要请求第几页，默认第一页
                // pageSize int 每页显示条数,默认100，最多500条
                SelectEntryOrderDto selectEntryOrderDto = new SelectEntryOrderDto();
                Calendar startTime = Calendar.getInstance();
                startTime.add(Calendar.MONTH, -1);// 查询开始时间
                Calendar endTime = Calendar.getInstance();
                endTime.add(Calendar.MONTH, 1);// 查询截止时间
                selectEntryOrderDto.setStartTime(startTime.getTime());
                selectEntryOrderDto.setEndTime(endTime.getTime());
                selectEntryOrderDto.setOwnerCode(order.getOwnerCode());// 设置子公司编码
                // selectEntryOrderDto.setWareHouseCode(order.getOrderCodeWMS());//设置仓库编码
                selectEntryOrderDto.setOrderType(KiddOrderLogisticsType.KIDD_CGRK);// 入库单业务类型
                // 传入入库单号，只会有一个结果，已跟cain确认
                selectEntryOrderDto.setEntryOrderCode(order.getOrderCode());// 采购收货单号
                selectEntryOrderDto.setPageNo(1);// 要请求第几页
                selectEntryOrderDto.setPageSize(1);// 每页显示条数
                log.info("根据需求文档要求组装请求数据，调用glink方法EntryOrderService.select({})",
                        JSONObject.toJSONString(selectEntryOrderDto));
                GlinkResponseDto<EntryOrderRepDto> glinkResponseDto = gLinkPurchaseProxyService
                        .select(selectEntryOrderDto);
                log.info("调用glink获取到收货明细数据：{}", JSONObject.toJSONString(glinkResponseDto));
                if (glinkResponseDto == null) {
                    response.setSuccess(false);
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setErrorMessage("glink 查询收货明细失败！");
                    return response;
                }
                if (0 == glinkResponseDto.getCode()) {
                    EntryOrderRepDto entryOrderRepDto = glinkResponseDto.getData();
                    if (null != entryOrderRepDto && 1 == entryOrderRepDto.getTotal()) {
                        // 查询成功
                        EntryOrderRepResultDto entryOrderRepResultDto = entryOrderRepDto.getResult().get(0);
                        KiddConfirmEntryOrderDto convert1 = BeanConvertUtils.convert(entryOrderRepResultDto, KiddConfirmEntryOrderDto.class);

                        List<OrderLinesRepDto> orderLines = entryOrderRepResultDto.getOrderLines();
                        List<KiddConfirmOrderLinesDto> kiddConfirmOrderLinesDtos = new ArrayList<>();
                        for (OrderLinesRepDto linesDtos : orderLines) {
                            KiddConfirmOrderLinesDto kiddConfirmOrderLinesDto = BeanConvertUtils
                                    .convert(linesDtos, KiddConfirmOrderLinesDto.class);
                            kiddConfirmOrderLinesDto.setItemId(linesDtos.getItemCode());// 仓库储存商品编码
                            // 采购收货结算数据统一取itemCode
                            kiddConfirmOrderLinesDto.setItemCode(linesDtos.getItemCode());// 仓库储存商品编码
                            kiddConfirmOrderLinesDto.setActualQty(linesDtos.getAcualQty());// 设置实际收货数量
                            kiddConfirmOrderLinesDtos.add(kiddConfirmOrderLinesDto);
                        }
                        kiddPurchaseOrderConfirmDto.setEntryOrder(convert1);
                        kiddPurchaseOrderConfirmDto.setOrderLines(kiddConfirmOrderLinesDtos);
                    }
                }
            }
            log.info("发送MQ际链回传消息信息kiddPurchaseOrderConfirmDto {}", JSON.toJSONString(kiddPurchaseOrderConfirmDto));
            entryOrderMQProducer.sendMsg(kiddPurchaseOrderConfirmDto);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode("1");
            response.setSuccess(false);
            response.setErrorMessage("采购订单失败");
        }
        return response;

    }

}
