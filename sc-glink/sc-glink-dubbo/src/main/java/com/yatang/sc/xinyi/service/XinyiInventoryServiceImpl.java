package com.yatang.sc.xinyi.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentDataDto;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentItemDto;
import com.yatang.sc.kidd.dto.im.ImAdjustmentResultDto;
import com.yatang.sc.kidd.dto.im.InventoryQueryDto;
import com.yatang.sc.kidd.dto.im.WarehouseProductDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.xinyi.dto.im.InventoryQueryCriteriaDto;
import com.yatang.sc.xinyi.dto.im.InventoryQueryItemDto;
import com.yatang.sc.xinyi.dto.im.InventoryQueryRequestDto;
import com.yatang.sc.xinyi.dto.im.InventoryQueryResponseDto;
import com.yatang.sc.xinyi.dto.im.XinYiInventoryAdjustNoticeItemDto;
import com.yatang.sc.xinyi.dto.im.XinYiInventoryAdjustNoticeRequestDto;
import com.yatang.sc.xinyi.service.kidd.XinyiProductProxyService;
import com.yatang.xc.pi.solr.dto.PrdCodeDto;
import com.yatang.xc.pi.solr.dto.PrdCodeQueryDto;
import com.yatang.xc.pi.solr.dubboservice.ScBackendSearchDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class XinyiInventoryServiceImpl {

    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private XinyiProductProxyService xinyiProductProxyService;
    @Autowired
    private ScBackendSearchDubboService scBackendSearchDubboService;

    @Resource(name = "kiddAdjustmentMQProducer")
    private SimpleMQProducer kiddAdjustmentMQProducer;

    @EventListener
    public Response<ImAdjustmentResultDto> inventoryQuery(InventoryQueryDto inventoryQueryDto) {
        log.info("查询心怡实时库存消息 inventoryQueryDto{}" , JSON.toJSONString(inventoryQueryDto));

        if (null == inventoryQueryDto || StringUtils.isBlank(inventoryQueryDto.getWarehouseCode())) {
            log.error("service----inventoryQuery>>传递参数出错", JSON.toJSONString(inventoryQueryDto));
            throw new RuntimeException("service----inventoryQuery>>传递参数出错" + JSON.toJSONString(inventoryQueryDto));
        }


        KiddUtils.checkXinyiListening(kiddFacadeService.warehouseInterfaceProvider(inventoryQueryDto.getWarehouseCode()));
        Response<ImAdjustmentResultDto> response = new Response<>();
        List<WarehouseProductDto> warehouseProductDtos = new ArrayList<>();//返回list对象
        try {
            //查询所有的商品code
            if (inventoryQueryDto.getItemCode() == null) {
                List<String> strings = searchProduct();//查询所有的商品code
                if (strings != null&&strings.size()>0) {
                        List<InventoryQueryCriteriaDto> list = new ArrayList<>();
                        InventoryQueryRequestDto inventoryQueryRequestDto = new InventoryQueryRequestDto();
                        for (String proCode : strings) {
                            InventoryQueryCriteriaDto inventoryQueryCriteriaDto = new InventoryQueryCriteriaDto();
                            inventoryQueryCriteriaDto.setItemCode(proCode);//商品编码
                            inventoryQueryCriteriaDto.setItemId(proCode);//仓储系统商品ID
                            inventoryQueryCriteriaDto.setOwnerCode(inventoryQueryDto.getOwnerCode());//子公司编码
                            inventoryQueryCriteriaDto.setWarehouseCode(inventoryQueryDto.getWarehouseCode());//仓库编码
                            list.add(inventoryQueryCriteriaDto);
                            if (list.size() % 500 == 0) {//每五百个商品调用一次
                                inventoryQueryRequestDto.setCriteriaList(list);
                                log.info("查询心怡库存具体参数信息list {}" , JSON.toJSONString(list));
                                InventoryQueryResponseDto inventoryQuery = xinyiProductProxyService.inventoryQuery(inventoryQueryRequestDto);
                                if (inventoryQuery != null) {
                                    List<InventoryQueryItemDto> items = inventoryQuery.getItems();
                                    if (items != null && items.size() > 0) {
                                        for (InventoryQueryItemDto itemDto : items) {
                                            WarehouseProductDto convert = BeanConvertUtils.convert(itemDto, WarehouseProductDto.class);
                                            convert.setActualQty(itemDto.getQuantity());//设置未冻结数量
                                            convert.setWmsID(itemDto.getItemId());//仓储系统商品ID
                                            warehouseProductDtos.add(convert);//设置返回对象
                                        }
                                    }
                                }
                                list.clear();//调用完清除list
                                inventoryQueryRequestDto.setCriteriaList(null);
                            }
                        }
                        //最后一次商品调用
                        inventoryQueryRequestDto.setCriteriaList(list);
                        log.info("最后一次商品调用查询心怡库存具体参数信息list {}" , JSON.toJSONString(list));
                        InventoryQueryResponseDto inventoryQuery = xinyiProductProxyService.inventoryQuery(inventoryQueryRequestDto);
                        log.info("最后一次商品调用查询心怡库存信息inventoryQuery {}" , JSON.toJSONString(inventoryQuery));
                        if (inventoryQuery != null) {
                            List<InventoryQueryItemDto> items = inventoryQuery.getItems();
                            if (items != null && items.size() > 0) {
                                for (InventoryQueryItemDto itemDto : items) {
                                    WarehouseProductDto convert = BeanConvertUtils.convert(itemDto, WarehouseProductDto.class);
                                    convert.setActualQty(itemDto.getQuantity());//设置未冻结数量
                                    convert.setWmsID(itemDto.getItemId());//仓储系统商品ID
                                    warehouseProductDtos.add(convert);//设置返回对象
                                }
                            }
                        }
                }
            } else {
                InventoryQueryRequestDto requestDto = new InventoryQueryRequestDto();
                InventoryQueryCriteriaDto criteriaDto = BeanConvertUtils.convert(inventoryQueryDto, InventoryQueryCriteriaDto.class);
                criteriaDto.setItemId(inventoryQueryDto.getItemCode());
                requestDto.setCriteriaList(Lists.newArrayList(criteriaDto));
                log.info("查询心怡仓库库存信息所用参数requestDto {}" , JSON.toJSONString(requestDto));
                InventoryQueryResponseDto inventoryQuery = xinyiProductProxyService.inventoryQuery(requestDto);
                log.info("查询心怡仓库库存信息结果inventoryQuery {}" , JSON.toJSONString(inventoryQuery));
                if (inventoryQuery != null) {
                    List<InventoryQueryItemDto> items = inventoryQuery.getItems();
                    if (items != null && items.size() > 0) {
                        for (InventoryQueryItemDto itemDto : items) {
                            WarehouseProductDto convert = BeanConvertUtils.convert(itemDto, WarehouseProductDto.class);
                            convert.setActualQty(itemDto.getQuantity());//设置未冻结数量
                            convert.setWmsID(itemDto.getItemId());//仓储系统商品ID
                            warehouseProductDtos.add(convert);//设置返回对象
                        }
                    } else {
                        response.setCode(CommonsEnum.RESPONSE_400.getCode());
                        response.setSuccess(false);
                        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
                    }
                } else {//调用心怡仓库查询失败
                    response.setCode(CommonsEnum.RESPONSE_400.getCode());
                    response.setSuccess(false);
                    response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
                }
            }
            ImAdjustmentResultDto imAdjustmentResultDto = new ImAdjustmentResultDto();
            imAdjustmentResultDto.setResult(warehouseProductDtos);
            response.setResultObject(imAdjustmentResultDto);//设置返回对象
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
        }
        return response;
    }


    /**
     * 库存调整单通知
     *
     * @param adjustNoticeRequestDto
     * @return
     */
    @EventListener
    public Response<Void> adjustInventoryReceiptNotice(XinYiInventoryAdjustNoticeRequestDto adjustNoticeRequestDto) {

        log.info("service---adjustInventoryReceiptNotice>>心怡发送库存调整单通知:{}", JSON.toJSONString(adjustNoticeRequestDto));
        Response<Void> responseDto = new Response<>();
        try {

            //数据转换心怡调整单为标准调整单
            KiddImAdjustmentDataDto kiddImAdjustmentDataDto = new KiddImAdjustmentDataDto();
            kiddImAdjustmentDataDto.setWarehouseCode(adjustNoticeRequestDto.getWarehouseCode());
            kiddImAdjustmentDataDto.setCheckOrderCode(adjustNoticeRequestDto.getCheckOrderCode());
            kiddImAdjustmentDataDto.setOwnerCode(adjustNoticeRequestDto.getOwnerCode());

            Pattern pattern = Pattern.compile("[A-Z]+");
            Matcher matcher = pattern.matcher(adjustNoticeRequestDto.getRemark());
            if(matcher.find()) {
                kiddImAdjustmentDataDto.setRemark(matcher.group());
            } else {
                kiddImAdjustmentDataDto.setRemark(adjustNoticeRequestDto.getRemark());
            }

            kiddImAdjustmentDataDto.setCheckTime(adjustNoticeRequestDto.getCheckTime());
            kiddImAdjustmentDataDto.setOutBizCode(adjustNoticeRequestDto.getOutBizCode());
            kiddImAdjustmentDataDto.setCheckOrderCode(adjustNoticeRequestDto.getCheckOrderCode());
            List<KiddImAdjustmentItemDto> items = new ArrayList<>();
            for (XinYiInventoryAdjustNoticeItemDto adjustNoticeItemDto : adjustNoticeRequestDto.getItems()) {

                KiddImAdjustmentItemDto adjustmentItemDto = new KiddImAdjustmentItemDto();

                adjustmentItemDto.setItemCode(adjustNoticeItemDto.getItemCode());
                adjustmentItemDto.setItemId(adjustNoticeItemDto.getItemId());
                adjustmentItemDto.setInventoryType(adjustNoticeItemDto.getInventoryType());
                adjustmentItemDto.setQuantity(adjustNoticeItemDto.getQuantity());
                adjustmentItemDto.setBatchCode(adjustNoticeItemDto.getBatchCode());
                adjustmentItemDto.setProductDate(adjustNoticeItemDto.getProductDate());
                adjustmentItemDto.setExpireDate(adjustNoticeItemDto.getExpireDate());
                adjustmentItemDto.setSnCode(adjustNoticeItemDto.getSnCode());
                adjustmentItemDto.setRemark(adjustNoticeItemDto.getRemark());
                adjustmentItemDto.setProduceCode(adjustNoticeItemDto.getProduceCode());
                items.add(adjustmentItemDto);
            }
            kiddImAdjustmentDataDto.setItems(items);
            // 库存调整mq发送
            kiddAdjustmentMQProducer.sendOrderlyMsg(kiddImAdjustmentDataDto.getOwnerCode(), kiddImAdjustmentDataDto);
            responseDto.setCode("001");
            responseDto.setSuccess(true);
            responseDto.setErrorMessage("库存盘点单接收成功");
        } catch (Exception e) {
            log.error("心怡库存调整单接收失败+" + ExceptionUtils.getFullStackTrace(e));
            responseDto.setCode("002");
            responseDto.setSuccess(true);
            responseDto.setErrorMessage("库存盘点单接收成功失败");
        }
        return responseDto;
    }

    /**
     * 查询所有商品信息
     */
    public List<String> searchProduct(){
        List<String> list = new ArrayList<>();
        PrdCodeQueryDto prdCodeQueryDto = new PrdCodeQueryDto();
        prdCodeQueryDto.setPageNum(1);
        prdCodeQueryDto.setPageSize(1000);
        Response<PrdCodeDto> prdCodeDtoResponse = scBackendSearchDubboService.prdCodeSearch(prdCodeQueryDto);
        log.info("查询商品Code search {}" , JSON.toJSONString(prdCodeDtoResponse));
        if (prdCodeDtoResponse.isSuccess() && prdCodeDtoResponse.getResultObject() != null) {
            list.addAll(prdCodeDtoResponse.getResultObject().getProductCodes());
                int pageNo = prdCodeDtoResponse.getResultObject().getPageNum();//当前页数
                int pageSize = prdCodeDtoResponse.getResultObject().getTotalPage();//每页显示记录数
                int total = prdCodeDtoResponse.getResultObject().getTotalCount();//总记录数
                for (int i=pageNo+1;i<=(total/pageSize)+1;i++){
                    prdCodeQueryDto.setPageNum(i);
                    Response<PrdCodeDto> prdCodeDtoResponse1 = scBackendSearchDubboService.prdCodeSearch(prdCodeQueryDto);
                    log.info("查询商品Code search {}" , JSON.toJSONString(prdCodeDtoResponse1));
                    if (prdCodeDtoResponse1.isSuccess() && prdCodeDtoResponse1.getResultObject() != null){
                        List<String> productCodes = prdCodeDtoResponse1.getResultObject().getProductCodes();
                        list.addAll(productCodes);
                    }
                }
            }

        return list;
    }

}


