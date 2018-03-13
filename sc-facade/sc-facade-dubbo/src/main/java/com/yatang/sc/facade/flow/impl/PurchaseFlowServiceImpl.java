package com.yatang.sc.facade.flow.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yatang.sc.facade.enums.PmPurchaseBusinessType;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.SupplierSettledPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptDetailPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.dto.UpdateReceiptYQXStatusDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptItemsDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmEntryOrderDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmOrderLinesDto;
import com.yatang.sc.facade.dto.pm.PurchaseOrderConfirmDto;
import com.yatang.sc.facade.enums.PmPurchaseOrderAcceptStatus;
import com.yatang.sc.facade.enums.PmPurchaseReceiptStatus;
import com.yatang.sc.facade.flow.PurchaseFlowService;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.ItemTranDto;
import com.yatang.sc.inventory.dto.ItemTranDtoList;
import com.yatang.sc.inventory.dto.TTranDataDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

import lombok.extern.slf4j.Slf4j;

/**
 * <class description>
 *
 * @author: zhoubaiyun
 * @version: 1.0, 2017年8月7日
 */
@Slf4j
@Service("purchaseFlowService")
@Transactional
public class PurchaseFlowServiceImpl implements PurchaseFlowService {


    final Integer first_create_purchase_receipt = 1;

    final Integer second_create_purchase_receipt = 2;

    @Autowired
    private PmPurchaseOrderService pmPurchaseOrderService;
    @Autowired
    private PmPurchaseReceiptService pmPurchaseReceiptService;
    // 库存入库服务
    @Autowired
    private ItemLocInventoryDubboService itemLocInventoryDubboService;
    // 商品服务
    @Autowired
    private ProductScIndexDubboService indexDubboService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Override
    public Response<Boolean> updateYSHStatus(PurchaseOrderConfirmDto purchaseOrderConfirmDto) {
        PurchaseConfirmEntryOrderDto entryOrder = purchaseOrderConfirmDto.getEntryOrder();
        List<PurchaseConfirmOrderLinesDto> orderLines = purchaseOrderConfirmDto.getOrderLines();
        Response<Boolean> response = new Response<>();
        try {
            //拒绝一张单多个相同商品情况
//			Set<String> uniqueSet=new HashSet<>();
//			for (PurchaseConfirmOrderLinesDto purchaseConfirmOrderLinesDto : orderLines) {
//				boolean alreadyContained = uniqueSet.add(purchaseConfirmOrderLinesDto.getItemCode());
//				if(alreadyContained){
//					log.info("同一个收货单不能出现同一个商品多行记录");
//					response.setSuccess(false);
//					response.setCode(CommonsEnum.RESPONSE_500.getCode());
//					response.setErrorMessage("同一个采购单不能出现同一个商品多行记录");
//					return response;
//				}
//			}
            log.info("准备更新为已收货状态PurchaseFlowServiceImpl.updateYSHStatus({})",
                    JSONObject.toJSONString(purchaseOrderConfirmDto));
            // 查询采购收货单对应的采购订单
            PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService
                    .selectPmPurchaseOrderByPurchaseReceiptNo(entryOrder.getEntryOrderCode());
            log.info("根据收货单编号{}获取到采购单数据：{}", entryOrder.getEntryOrderCode(),
                    JSONObject.toJSONString(pmPurchaseOrderPo));
            // 查询采购收货单
            PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService
                    .queryPurchaseReceiptByPurchaseOrderId(pmPurchaseOrderPo.getId());

            if (2 == pmPurchaseReceiptPo.getStatus()) {
                log.info("收货单已经收货，不再重复收货。收货单：{}", JSONObject.toJSONString(pmPurchaseReceiptPo));
                response.setSuccess(true);
                response.setCode(CommonsEnum.RESPONSE_20400.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20400.getName());
                response.setResultObject(true);
                return response;
            }
            log.info("根据采购单ID{}获取到采购收货单数据：{}", pmPurchaseOrderPo.getId(), JSONObject.toJSONString(pmPurchaseReceiptPo));
            RLock lock = mRedisDistributedLockFactory.getLock("PurchaseOrder" + pmPurchaseOrderPo.getId());
            try {
                boolean tryLock = lock.tryLock();
                if (!tryLock) {
                    response.setSuccess(true);
                    response.setCode(CommonsEnum.RESPONSE_20400.getCode());
                    response.setErrorMessage(CommonsEnum.RESPONSE_20400.getName());
                    response.setResultObject(true);
                    return response;
                }

                PurchaseConfirmEntryOrderDto entryOrderRepResultDto = purchaseOrderConfirmDto.getEntryOrder();
                // 收货头信息：
                // 收货日期：接口返回的“operateTime”
                // 收货单状态：已收货 (YSH):
                Date operateTime = entryOrderRepResultDto.getOperateTime();
                // 逻辑仓库编码
                String wareHouseCode = entryOrderRepResultDto.getWarehouseCode();
                // 收货行信息：
                // 收货数：接口返回明细的“acualQty”
                List<Map<String, Long>> list = new ArrayList<>();
                ItemTranDtoList itemTranDtoList = new ItemTranDtoList();
                List<ItemTranDto> itemTranDtos = new ArrayList<>();
                itemTranDtoList.setLoc(wareHouseCode);
                itemTranDtoList.setItemTrans(itemTranDtos);
                List<SupplierSettledPo> supplierSettledPoList = new ArrayList<>();
                for (PurchaseConfirmOrderLinesDto orderLinesRepDto : orderLines) {
                    if (null == orderLinesRepDto.getActualQty() || orderLinesRepDto.getActualQty() < 0) {
                        // 际链非要传负数，标记为异常，打印日志。12-25
                        log.info("收货数量有误！");
                        pmPurchaseReceiptPo.setStatus(4);
                        pmPurchaseReceiptPo.setModifyTime(new Date());
                        pmPurchaseReceiptPo.setModifyUserId("system");
                        pmPurchaseReceiptService.updateReceipt(pmPurchaseReceiptPo);
                        response.setSuccess(false);
                        response.setCode(CommonsEnum.RESPONSE_500.getCode());
                        response.setErrorMessage("收货数量有误！");
                        response.setResultObject(false);
                        return response;
                    }
                    // 收货单明细单号-跟LHP确认是使用采购单明细ID
                    long orderLineNo = 0;
                    if (null == orderLinesRepDto.getOrderLineNo()) {
                        log.info("订单明细行单号不能为空！");
                        pmPurchaseReceiptPo.setStatus(4);
                        pmPurchaseReceiptPo.setModifyTime(new Date());
                        pmPurchaseReceiptPo.setModifyUserId("system");
                        pmPurchaseReceiptService.updateReceipt(pmPurchaseReceiptPo);
                        response.setSuccess(false);
                        response.setCode(CommonsEnum.RESPONSE_500.getCode());
                        response.setErrorMessage("订单明细行单号有误！");
                        response.setResultObject(false);
                        return response;
                    }
                    try {
                        orderLineNo = Long.parseLong(orderLinesRepDto.getOrderLineNo());
                    } catch (Exception e) {
                        // 际链非要传负数，标记为异常，打印日志。12-25
                        log.info("订单明细行单号有误！");
                        pmPurchaseReceiptPo.setStatus(4);
                        pmPurchaseReceiptPo.setModifyTime(new Date());
                        pmPurchaseReceiptPo.setModifyUserId("system");
                        pmPurchaseReceiptService.updateReceipt(pmPurchaseReceiptPo);
                        response.setSuccess(false);
                        response.setCode(CommonsEnum.RESPONSE_500.getCode());
                        response.setErrorMessage("订单明细行单号有误！");
                        response.setResultObject(false);
                        return response;
                    }
                    // 收货数量（返回结果本来是int）

                    long receivedNum = orderLinesRepDto.getActualQty();
                    String itemCode = orderLinesRepDto.getItemCode();
                    Map<String, Long> map = new HashMap<>();
                    map.put("orderLineNo", orderLineNo);
                    map.put("ReceivedNum", receivedNum);
                    list.add(map);

                    String productCode = orderLinesRepDto.getItemCode();
                    // 根据采购收货单明细编号查询采购订单明细
                    // PmPurchaseOrderItemPo pmPurchaseOrderItemPo =
                    // pmPurchaseOrderService
                    // .selectPmPurchaseOrderItemByPurchaseReceiptItemId(orderLineNo);
                    PmPurchaseOrderItemPo pmPurchaseOrderItemPo = pmPurchaseOrderService
                            .selectBypurchaseOrderIdAndPrimaryKey(pmPurchaseOrderPo.getId(), orderLineNo, itemCode, receivedNum);
                    log.info("根据purchaseOrderId={},ID={}查询订单明细结果为：{}", pmPurchaseOrderPo.getId(), orderLineNo,
                            JSONObject.toJSONString(pmPurchaseOrderItemPo));
                    // check if not exist
                    if (null == pmPurchaseOrderItemPo) {
                        log.error("Accroding to the orderLinNo:{}, can`t find the OrderItemPo", orderLineNo);
                        throw new RuntimeException(
                                "Accroding to the orderLinNo:+" + orderLineNo + ", can`t find the OrderItemPo");
                    }


                    Response<ProductIndexDto> result = indexDubboService
                            .queryByProductId(pmPurchaseOrderItemPo.getProductId());
                    log.info("根据商品ID={}查询商品详情结果为：{}", pmPurchaseOrderItemPo.getProductId(),
                            JSONObject.toJSONString(result));
                    if (!"200".equals(result.getCode())) {
                        throw new RuntimeException("调用主数据接口根据商品ID查询商品详情出错");
                    }
                    ProductIndexDto productDto = result.getResultObject();

                    setItemTranDto(entryOrder, pmPurchaseOrderPo, itemTranDtos, receivedNum, productCode,
                            pmPurchaseOrderItemPo, productDto);

                    // 获取子公司信息
                    String branchCompanyId = pmPurchaseOrderPo.getBranchCompanyId();
                    Response<BranchCompanyDto> branchCompanyDtoResponse = organizationService
                            .querySimpleByBranchCompanyId(branchCompanyId);
                    log.info("根据子公司ID={}获取子公司信息结果为：{}", branchCompanyId,
                            JSONObject.toJSONString(branchCompanyDtoResponse));
                    if (!"200".equals(branchCompanyDtoResponse.getCode())) {
                        throw new RuntimeException("获取子公司信息出错");
                    }
                    setSupplierSettledPo(entryOrder, pmPurchaseOrderPo, pmPurchaseReceiptPo, operateTime,
                            supplierSettledPoList, orderLinesRepDto, pmPurchaseOrderItemPo, productDto, branchCompanyId,
                            branchCompanyDtoResponse);

                }
                log.info("调用入库dubbo服务ItemLocInventoryDubboService.purchaseStockIn({})",
                        JSONObject.toJSONString(itemTranDtoList));
                Response<Void> purchaseStockIn = itemLocInventoryDubboService.purchaseStockIn(itemTranDtoList);
                log.info("调用入库dubbo服务获取结果为：{}", JSONObject.toJSONString(purchaseStockIn));
                if ("200".equals(purchaseStockIn.getCode())) {
                    log.info("更新收货单信息以及供应商结算插入，参数：{},{},{},{},{}", pmPurchaseOrderPo.getId(),
                            entryOrder.getEntryOrderCode(), operateTime, JSONObject.toJSONString(list),
                            JSONObject.toJSONString(supplierSettledPoList));
                    boolean result = pmPurchaseReceiptService.updatePurchaseYSH(pmPurchaseOrderPo.getId(),
                            entryOrder.getEntryOrderCode(), operateTime, list, supplierSettledPoList);
                    log.info("更新收货单：{}状态结果为：{}", pmPurchaseOrderPo.getId(), result);
                    response.setSuccess(true);
                    response.setCode(CommonsEnum.RESPONSE_200.getCode());
                    response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    response.setResultObject(true);
                    return response;
                } else {
                    response.setSuccess(false);
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setErrorMessage("入库出错！");
                    response.setResultObject(false);
                    return response;
                }
            } finally {
                lock.unlock();
            }

        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(e.getMessage());
            response.setResultObject(false);
        }

        return response;
    }


    private void setSupplierSettledPo(PurchaseConfirmEntryOrderDto entryOrder, PmPurchaseOrderPo pmPurchaseOrderPo,
                                      PmPurchaseReceiptPo pmPurchaseReceiptPo, Date operateTime, List<SupplierSettledPo> supplierSettledPoList,
                                      PurchaseConfirmOrderLinesDto orderLinesRepDto, PmPurchaseOrderItemPo pmPurchaseOrderItemPo,
                                      ProductIndexDto productDto, String branchCompanyId, Response<BranchCompanyDto> branchCompanyDtoResponse) {
        // 为供应商结算支持准备数据
        SupplierSettledPo supplierSettledPo = new SupplierSettledPo();
        // 收货单号
        supplierSettledPo.setPurchaseReceiptNo(entryOrder.getEntryOrderCode());
        // ASN号
        supplierSettledPo.setAsn(pmPurchaseReceiptPo.getAsn());
        // 采购单号
        supplierSettledPo.setPurchaseOrderNo(pmPurchaseOrderPo.getPurchaseOrderNo());
        // 供应商编号
        supplierSettledPo.setSpNo(pmPurchaseOrderPo.getSpNo());
        // 供应商名称
        supplierSettledPo.setSpName(pmPurchaseOrderPo.getSpName());
        // 供应商地点编号
        supplierSettledPo.setSpAdrNo(pmPurchaseOrderPo.getSpAdrNo());
        // 子公司编码,没有子公司编码，子公司编码就是子公司ID，跟lhp确认
        supplierSettledPo.setBranchCompanyCode(branchCompanyId);
        // 子公司名称
        supplierSettledPo.setBranchCompanyName(null == branchCompanyDtoResponse.getResultObject() ? null
                : branchCompanyDtoResponse.getResultObject().getName());
        // 地点类型
        supplierSettledPo.setAdrType(pmPurchaseOrderPo.getAdrType());
        // 地点
        supplierSettledPo.setAdrTypeCode(pmPurchaseOrderPo.getAdrTypeCode());
        // 地点名称
        supplierSettledPo.setAdrTypeName(pmPurchaseOrderPo.getAdrTypeName());
        // 账期
        supplierSettledPo.setSettlementPeriod(pmPurchaseOrderPo.getSettlementPeriod());
        // 付款方式
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
        supplierSettledPo.setProductName(productDto.getSaleName());
        // 税率
        supplierSettledPo.setInputTaxRate(pmPurchaseOrderItemPo.getInputTaxRate());
        // 收货日期
        supplierSettledPo.setReceivedTime(operateTime);
        // 已收货数量
        supplierSettledPo.setReceivedNumber(orderLinesRepDto.getActualQty());
        // 收货金额（未税)
        BigDecimal purchasePrice = pmPurchaseOrderItemPo.getPurchasePrice();// 含税采购价格
        BigDecimal totalMoney = purchasePrice.multiply(new BigDecimal(orderLinesRepDto.getActualQty()));// 含税总额=销售价格*收货数量
        // 税额=含税总额/(1+税率*0.01)
        BigDecimal totalMoneyWithoutTax = totalMoney.divide(
                new BigDecimal(1)
                        .add(pmPurchaseOrderItemPo.getInputTaxRate().multiply(new BigDecimal(0.01))),
                2, RoundingMode.HALF_UP);
        supplierSettledPo.setReceivedMoneyWithoutTax(totalMoneyWithoutTax);
        // 收货金额
        supplierSettledPo.setReceivedMoneyWithTax(totalMoney);

        supplierSettledPoList.add(supplierSettledPo);
    }


    private void setItemTranDto(PurchaseConfirmEntryOrderDto entryOrder, PmPurchaseOrderPo pmPurchaseOrderPo,
                                List<ItemTranDto> itemTranDtos, long receivedNum, String productCode,
                                PmPurchaseOrderItemPo pmPurchaseOrderItemPo, ProductIndexDto productDto) {
        ItemTranDto itemTranDto = new ItemTranDto();
        ItemLocSohDto itemLocSoh = new ItemLocSohDto();
        TTranDataDto tTranData = new TTranDataDto();
        itemTranDto.setItemLocSoh(itemLocSoh);
        itemTranDto.settTranData(tTranData);
        // itemLocSoh.setLoc(wareHouseCode);
        itemLocSoh.setItemId(pmPurchaseOrderItemPo.getProductId());
        itemLocSoh.setUnitCost(pmPurchaseOrderItemPo.getPurchasePrice().doubleValue());
        itemLocSoh.setStockOnHand(receivedNum);
        itemLocSoh.setProductCode(pmPurchaseOrderItemPo.getProductCode());

        tTranData.setItem(pmPurchaseOrderItemPo.getProductId());
        // tTranData.setLocation(wareHouseCode);
        tTranData.setUnits(receivedNum);
        tTranData.setRefNo1(pmPurchaseOrderPo.getPurchaseOrderNo());
        tTranData.setRefNo2(entryOrder.getEntryOrderCode());
        tTranData.setVatRate(pmPurchaseOrderItemPo.getInputTaxRate().toString());
        tTranData.setProductCode(productCode);
        tTranData.setGroups(productDto.getFirstLevelCategoryId());
        tTranData.setDept(productDto.getSecondLevelCategoryId());
        tTranData.setClasss(productDto.getThirdLevelCategoryId());
        tTranData.setSubclass(productDto.getFourthCategoryId());

        itemTranDtos.add(itemTranDto);
    }


    @Override
    public Response<Boolean> updateYQXStatus(UpdateReceiptYQXStatusDto updateReceiptYQXStatusDto) {
        // 4.入库单状态= CANCELED，更新收货单状态为 “已取消(YQX)”,更新采购单状态为“已关闭”,
        // 取消数量=采购数量-已收货数量，取消原因填写超期关闭
        Response<Boolean> response = new Response<>();
        try {
            // // 查询采购收货单对应的采购订单
            PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService
                    .selectPmPurchaseOrderByPurchaseReceiptNo(updateReceiptYQXStatusDto.getPurchaseReceiptNo());

            if (pmPurchaseOrderPo.getStatus() == 4) {
                throw new RuntimeException("已收货的不能取消");
            }
            // // 接口入参
            // // 参数名 数据
            // // 类型 必填 说明
            // // startTime dateTime 是 查询开始时间 值：当前系统时间-1月
            // // endTime dateTime 是 查询截止时间 值：当前系统时间+1月
            // // ownerCode string 是 采购单仓库编码
            // // orderType string 是 入库单业务类型 值：CGRK=采购入库
            // // entryOrderCode string 是 采购收货单号
            // // pageNo int 要请求第几页，默认第一页
            // // pageSize int 每页显示条数,默认100，最多500条
            // SelectEntryOrderDto selectEntryOrderDto = new
            // SelectEntryOrderDto();
            // Calendar startTime = Calendar.getInstance();
            // startTime.add(Calendar.MONTH, -1);
            // Calendar endTime = Calendar.getInstance();
            // endTime.add(Calendar.MONTH, 1);
            // selectEntryOrderDto.setStartTime(startTime.getTime());
            // selectEntryOrderDto.setEndTime(endTime.getTime());
            // selectEntryOrderDto.setOwnerCode(pmPurchaseOrderPo.getAdrTypeCode());
            // selectEntryOrderDto.setOrderType("CGRK");
            // // 传入入库单号，只会有一个结果，已跟cain确认
            // selectEntryOrderDto.setEntryOrderCode(updateReceiptYQXStatusDto.getPurchaseReceiptNo());
            // selectEntryOrderDto.setPageNo(1);
            // selectEntryOrderDto.setPageSize(1);
            // GlinkResponseDto<EntryOrderRepDto> glinkResponseDto =
            // entryOrderService.select(selectEntryOrderDto);
            // if (0 == glinkResponseDto.getCode()) {
            // EntryOrderRepDto entryOrderRepDto = glinkResponseDto.getData();
            // if (null != entryOrderRepDto && 1 == entryOrderRepDto.getTotal())
            // {
            // EntryOrderRepResultDto entryOrderRepResultDto =
            // entryOrderRepDto.getResult().get(0);
            // // 收货行信息：
            // // 收货数：接口返回明细的“acualQty”
            // List<Map<String, Long>> list = new ArrayList<>();
            // for (OrderLinesRepDto orderLinesRepDto :
            // entryOrderRepResultDto.getOrderLines()) {
            // // 收货单明细单号-跟LHP确认是使用收货单ID
            // long orderLineNo =
            // Long.parseLong(orderLinesRepDto.getOrderLineNo());
            // // 收货数量（返回结果本来是int）
            // long ReceivedNum = orderLinesRepDto.getAcualQty();
            // Map<String, Long> map = new HashMap<>();
            // map.put("orderLineNo", orderLineNo);
            // map.put("ReceivedNum", ReceivedNum);
            // list.add(map);
            // }
            boolean result = pmPurchaseReceiptService.updatePurchaseYQX(pmPurchaseOrderPo.getId(),
                    updateReceiptYQXStatusDto.getPurchaseReceiptNo(), null);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(result);
            // }
            // }
            // response.setSuccess(false);
            // response.setCode(CommonsEnum.RESPONSE_500.getCode());
            // response.setErrorMessage("GLINK获取不到对应数据，收货单号：" +
            // updateReceiptYQXStatusDto.getPurchaseReceiptNo());
            // response.setResultObject(false);

        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(false);
        }
        return response;
    }

    @Override
    public Response<String> createPurchaseReceiptASN(PmPurchaseReceiptDetailDto detailDto) {

        Response<String> response = new Response<>();

        //1 根据收货单id查询收货单相关信息
        // 检查商品是否存在重复项并获取传入的商品size
        Set<String> receiptProductDtoKindSet = new HashSet<>();
        for (PmPurchaseReceiptItemsDto itemsDto : detailDto.getReceiptPruducts()) {
            String purchaseOrderItemsId = itemsDto.getPurchaseOrderItemsId();
            //过滤商品存在重复的并立即返回
            if (receiptProductDtoKindSet.contains(purchaseOrderItemsId)) {
                log.error("dubbo----createPurchaseReceiptASN>>传递的商品存在重复项:PurchaseOrderItemsId:{}", purchaseOrderItemsId);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("传递的商品存在重复项请核实");
                return response;
            }
            receiptProductDtoKindSet.add(purchaseOrderItemsId);

        }
        //1.1主表
        Long receiptId = detailDto.getPmPurchaseReceipt().getId();
        PmPurchaseReceiptPo pmPurchaseReceiptPo = pmPurchaseReceiptService.queryReceiptById(receiptId);
        if (pmPurchaseReceiptPo == null) {
            log.error("dubbo----createPurchaseReceiptASN>>根据收货单id:{}查询收货单失败", receiptId);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("查找收货单失败");
            return response;
        }
        if (!PmPurchaseReceiptStatus.RECEIPT_FOR_DELIVERY_STATUS.getCode().equals(pmPurchaseReceiptPo.getStatus())) {
            log.error("dubbo----createPurchaseReceiptASN>>当前收货单号:{}的状态不能执行asn录入", pmPurchaseReceiptPo.getPurchaseReceiptNo());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("当前收货单不支持录入ASN,请核实");
            return response;
        }
        //获取采购订单信息
        String purchaseOrderId = pmPurchaseReceiptPo.getPurchaseOrderId();
        PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService.selectByPrimaryKey(Long.parseLong(purchaseOrderId));
        if (!PmPurchaseBusinessType. CONSIGNMENT_SALE_TYPE.getCode().equals(pmPurchaseOrderPo.getBusinessMode())) {
            response.setCode(com.yatang.sc.facade.common.CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error("dubbo-updateSpAcceptStatus>>非寄售商品不能录入asn:{}", pmPurchaseOrderPo.getBusinessMode());
            response.setErrorMessage("非寄售商品不能录入asn");
            return response;
        }
        //传递的saleOrderId跟采购订单的比对
        if (!detailDto.getPmPurchaseReceipt().getSaleOrderId().equals(pmPurchaseOrderPo.getSaleOrderId())) {
            log.error("dubbo----createPurchaseReceiptASN>>当前销售单id:{}的和采购订单上:{}不一致", detailDto.getPmPurchaseReceipt().getSaleOrderId(), pmPurchaseOrderPo.getSaleOrderId());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("销售订单id传递出错,请核实");
            return response;
        }

        //离开焦点时校验，如果预计送货日期(至) 为空则自动带入 预计送货日期(从) ；
        //预计送货日期(从) 小于预计 收送日期(至)
        //收货单上预计收货日期
        Date estimatedDeliveryDate = detailDto.getPmPurchaseReceipt().getEstimatedDeliveryDate();
        //采购单时间
        Date defaultEstimatedReceivedDate = pmPurchaseReceiptPo.getEstimatedReceivedDate();
        if (!DateUtil.compareDate(estimatedDeliveryDate, new Date())) {
            log.error("dubbo----createPurchaseReceiptASN>>预计送货日期不能小于当前日期");
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("预计送货日期不能小于当前日期");
            return response;
        }
        if (defaultEstimatedReceivedDate != null && estimatedDeliveryDate != null) {
            if (DateUtil.compareBiggerThanOther(estimatedDeliveryDate, defaultEstimatedReceivedDate)) {
                log.error("dubbo----createPurchaseReceiptASN>>预计送货日期不能大于采购单预计到货日期");
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("预计送货日期不能大于采购单预计到货日期");
                return response;
            }
        }

        if (!PmPurchaseOrderAcceptStatus.ORDER_ACCEPT.getCode().equals(pmPurchaseOrderPo.getSpAcceptStatus())) {
            log.error("dubbo----createPurchaseReceiptASN>>原订单编号为:{},的采购单没有执行接单操作,当前的状态为：【{}】", pmPurchaseOrderPo.getPurchaseOrderNo(), PmPurchaseOrderAcceptStatus.parse(pmPurchaseOrderPo.getSpAcceptStatus()).getDesc());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("原采购单没有执行接单操作,不能录入ASN");
            return response;
        }


        //获取采购单号
        String purchaseOrderNo = pmPurchaseOrderPo.getPurchaseOrderNo();
        //1.2 item
        List<PmPurchaseReceiptItemsPo> itemsPoList = pmPurchaseReceiptService.queryReceiptItemsByReceiptId(String.valueOf(receiptId));
        if (itemsPoList == null) {
            log.error("dubbo----createPurchaseReceiptASN>>根据收货单id:{}查询收货单item失败", receiptId);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("根据收货单" + receiptId + "查询收货单子项列表失败");
            return response;
        }
        int itemPoSize = itemsPoList.size();
        if (itemPoSize == 0) {
            log.error("dubbo----createPurchaseReceiptASN>>根据收货单id:{}查询收货单item数量为0", receiptId);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("当前收货单查询子项列表数量为0");
            return response;
        }

        int receiptProductDtoKindSize = receiptProductDtoKindSet.size();
        if (receiptProductDtoKindSize > itemPoSize) {
            log.error("dubbo----createPurchaseReceiptASN>>收货单号:{}传入的商品数量:{}大于实际收货单数量:{}", receiptId, receiptProductDtoKindSet.size(), itemsPoList.size());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("传入的商品种类大于实际收货单的商品种类请核实");
            return response;

        }

        //2.1遍历筛选出需要录入asn的订单
        //根据收货单数量生成新的asn编码
        Response<Void> handlerAsnResponse = handlerCreatePurchaseReceiptASN(detailDto, pmPurchaseReceiptPo, itemsPoList, itemPoSize, receiptProductDtoKindSize, purchaseOrderNo);
        if (!handlerAsnResponse.isSuccess()) {
            return BeanConvertUtils.convert(handlerAsnResponse, Response.class);
        }
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        response.setResultObject(String.valueOf(receiptId));
        return response;

    }


    private Response<Void> handlerCreatePurchaseReceiptASN(PmPurchaseReceiptDetailDto detailDto, PmPurchaseReceiptPo pmPurchaseReceiptPo,
                                                           List<PmPurchaseReceiptItemsPo> itemsPoList, int itemPoSize, int receiptProductDtoKindSize,
                                                           String purchaseOrderNo) {
        Response<Void> response = new Response<>();
        PmPurchaseReceiptDto pmPurchaseReceiptDto = detailDto.getPmPurchaseReceipt();
        List<PmPurchaseReceiptItemsDto> receiptProductDtos = detailDto.getReceiptPruducts();
        String purchaseOrderId = pmPurchaseReceiptPo.getPurchaseOrderId();
        String purchaseReceiptNo = pmPurchaseReceiptPo.getPurchaseReceiptNo();

        List<PmPurchaseReceiptPo> receiptPoList = pmPurchaseReceiptService.queryPurchaseReceiptListByPurchaseOrderId(purchaseOrderId, true);
        int size = receiptPoList.size();
        //做两次的限定
        if (size > first_create_purchase_receipt) {
            log.error("dubbo----createPurchaseReceiptASN>>当前只支持录入两次asn,已经录入次数{}:请核实", size);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("当前只支持录入两次asn,请核实");
            return response;
        }

        size++;
        //asn生成规则:采购单号+"-"+收货单数量
        String asnNo = purchaseOrderNo + "-" + size;
        // 第二次录入
        if (second_create_purchase_receipt == size) {
            //需要验证 录入的数量和查询出来的数量相同

//            if (receiptProductDtos.size() < itemsPoList.size()) {
//                log.error("dubbo----createPurchaseReceiptASN>>第二次录入ASN的商品数量:{},小于收货单上数量:{}", receiptProductDtos.size(), itemsPoList.size());
//                response.setSuccess(false);
//                response.setCode(CommonsEnum.RESPONSE_500.getCode());
//                response.setErrorMessage("当前录入asn商品数量不能小于收货单数量");
//                return response;
//            }
            if (receiptProductDtos.size() > itemsPoList.size()) {
                log.error("dubbo----createPurchaseReceiptASN>>第二次录入ASN的商品数量:{},大于收货单上商品数量:{}", receiptProductDtos.size(), itemsPoList.size());
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("当前录入asn商品数量不能大于收货单上商品数量");
                return response;
            }


        }

        pmPurchaseReceiptDto.setAsn(asnNo);

        //录入后剩余的商品
        //List<PmPurchaseReceiptItemsPo> remainItemPoList = new ArrayList<>();
        for (PmPurchaseReceiptItemsDto receiptItemsDto : receiptProductDtos) {
            String purchaseOrderItemsId = receiptItemsDto.getPurchaseOrderItemsId();
            for (PmPurchaseReceiptItemsPo itemsPo : itemsPoList) {
                String poPurchaseOrderItemsId = itemsPo.getPurchaseOrderItemsId();
                if (purchaseOrderItemsId.equals(poPurchaseOrderItemsId) && receiptItemsDto.getId().equals(itemsPo.getId())) {
                    //如果录入的收货单数量大于传输数量返回失败
                    Integer defaultDeliveryNumber = itemsPo.getDeliveryNumber() == null ? 0 : itemsPo.getDeliveryNumber();
                    if (receiptItemsDto.getDeliveryNumber() > defaultDeliveryNumber) {
                        log.error("dubbo----createPurchaseReceiptASN>>purchaseItemIds为:{}的商品录入数量:{}大于默认数量:{}", purchaseOrderItemsId, receiptItemsDto.getDeliveryNumber(), defaultDeliveryNumber);
                        response.setSuccess(false);
                        response.setCode(CommonsEnum.RESPONSE_500.getCode());
                        response.setErrorMessage("存在商品录入数量:" + receiptItemsDto.getDeliveryNumber() + "大于默认数量:" + defaultDeliveryNumber);
                        return response;
                    }

                    //判定改商品录入数量还有剩余
//                    int deliveryNumber = defaultDeliveryNumber - receiptItemsDto.getDeliveryNumber();
//                    if (deliveryNumber > 0) {
//                        itemsPo.setDeliveryNumber(deliveryNumber);
//                        remainItemPoList.add(itemsPo);
//
//                    }
                    //获取id
                    receiptItemsDto.setId(itemsPo.getId());
                    itemsPoList.remove(itemsPo);
                    break;
                }
            }
        }

//        if (second_create_purchase_receipt == size) {
//            if (itemsPoList.size() != 0) {
//                log.error("dubbo----createPurchaseReceiptASN>>第二次录入ASN的商品数量收货单上商品必须全部录入,剩余商品:{}", JSON.toJSONString(itemsPoList));
//                response.setSuccess(false);
//                response.setCode(CommonsEnum.RESPONSE_500.getCode());
//                response.setErrorMessage("第二次录入ASN的商品数量收货单上商品必须全部录入");
//                return response;
//            }
//
//        }

        if (itemsPoList.size() == itemPoSize) {
            log.error("dubbo----handlerCreatePurchaseReceiptASN>>供应商录入的商品中没有存在于收货单上的数据");
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("在当前收货单上找不到对应的商品请核实");
            return response;
        }

        //2.2更新操作
        PmPurchaseReceiptDetailPo receiptDetailPo = BeanConvertUtils.convert(detailDto, PmPurchaseReceiptDetailPo.class);
        receiptDetailPo.getPmPurchaseReceipt().setStatus(PmPurchaseReceiptStatus.RECEIPT_DELIVERED_STATUS.getCode());
        Boolean updateSuccess = pmPurchaseReceiptService.updatePurchaseReceipt(receiptDetailPo);
        if (!updateSuccess) {
            log.error("dubbo----createPurchaseReceiptASN>>ASN录入更新收货单失败,收货单号:{}", purchaseReceiptNo);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("录入asn失败,请稍后重试");
            return response;
        }
        //2.3 剩余数量大于1生成新的收货单(原始订单还有数量)
//        if ((itemsPoList.size() > 0 && (itemPoSize - receiptProductDtoKindSize == itemsPoList.size())) || remainItemPoList.size() > 0) {
        if (itemsPoList.size() > 0 && (itemPoSize - receiptProductDtoKindSize == itemsPoList.size()) ) {

            //创建新的收货单
            PmPurchaseReceiptDetailPo detailPo = new PmPurchaseReceiptDetailPo();
            PmPurchaseReceiptPo receiptPo = BeanConvertUtils.convert(pmPurchaseReceiptPo, PmPurchaseReceiptPo.class);
            receiptPo.setStatus(PmPurchaseReceiptStatus.RECEIPT_FOR_DELIVERY_STATUS.getCode());
            receiptPo.setId(null);
            receiptPo.setPurchaseReceiptNo(null);
            detailPo.setReceiptPruducts(itemsPoList);
            //此时拆出来的订单为废弃的单据
            detailPo.setPmPurchaseReceipt(receiptPo);
            Boolean splitSuccess = pmPurchaseReceiptService.splitNewPurchaseReceipt(detailPo, null);
            if (!splitSuccess) {
                log.error("dubbo----createPurchaseReceiptASN>>ASN录入,拆分新的收货单失败--原收货单号:{}", purchaseReceiptNo);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("录入asn失败,请稍后重试");
                return response;

            }


        }
        response.setSuccess(true);
        return response;
    }
}
