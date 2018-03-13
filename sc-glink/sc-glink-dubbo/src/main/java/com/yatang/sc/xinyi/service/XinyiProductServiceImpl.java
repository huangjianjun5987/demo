package com.yatang.sc.xinyi.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryDto;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryResponseDto;
import com.yatang.sc.kidd.dto.product.ProductSynchronizeDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.im.InventoryQueryRequestDto;
import com.yatang.sc.xinyi.dto.im.InventoryQueryResponseDto;
import com.yatang.sc.xinyi.dto.product.ProductSynchronizeItemDto;
import com.yatang.sc.xinyi.dto.product.ProductSynchronizeRequestDto;
import com.yatang.sc.xinyi.service.kidd.XinyiProductProxyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class XinyiProductServiceImpl {

    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private XinyiProductProxyService xinyiProductProxyService;

    @EventListener
    public Response<String> synchronizeProduct(ProductSynchronizeDto productSynchronizeDto) {
        log.info("xinyi 商品同步:{}", JSON.toJSON(productSynchronizeDto));

        // 心怡接口调用
        Response<String> response = new Response<>();
        try {

            Response<List<LogicWarehouseDto>> warehousesResponse = kiddFacadeService.queryLogicWarehousesBy(InterfaceProviderTypeEnum.XINYI.getValue());
            if (!warehousesResponse.isSuccess()) {
                throw new RuntimeException("获取仓库列表失败");
            }
            if (warehousesResponse.getResultObject() == null) {
                throw new RuntimeException("仓库未配置接口提供商");
            }

            List<LogicWarehouseDto> warehouses = warehousesResponse.getResultObject();
            List<Response<String>> results = Lists.newArrayList();
            for (LogicWarehouseDto warehouse : warehouses) {
                ProductSynchronizeRequestDto requestDto = new ProductSynchronizeRequestDto();
                requestDto.setActionType(Objects.equal("1", productSynchronizeDto.getActionType()) ? "add" : "update");
                requestDto.setWarehouseCode(warehouse.getWarehouseCode());
                requestDto.setOwnerCode(warehouse.getBranchCompanyCode());

                ProductSynchronizeItemDto itemDto = BeanConvertUtils.convert(productSynchronizeDto, ProductSynchronizeItemDto.class);
                itemDto.setPcs(productSynchronizeDto.getPcs().replace("*", ""));
                requestDto.setItem(itemDto);

                results.add(synchronizeProductWarehouse(requestDto));
            }

            // 如果有错误则返回错误
            StringBuffer errors = new StringBuffer();
            for (Response<String> result : results) {
                if (!result.isSuccess()) {
                    errors.append("[" + result.getErrorMessage() + "]");
                }
            }

            String errorMsg = errors.toString();
            if (StringUtils.isNotEmpty(errorMsg)) {
                response.setSuccess(false);
                response.setErrorMessage(errorMsg);
            } else {
                response.setSuccess(true);
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

    public Response<String> synchronizeProductWarehouse(ProductSynchronizeRequestDto requestDto) {
        String logHeader = requestDto.getWarehouseCode() + "-" + requestDto.getOwnerCode() + "-" + requestDto.getItem().getItemCode();
        log.info("xinyi 仓库商品同步:{}", logHeader);
        // 心怡接口调用
        Response<String> response = new Response<>();
        try {
            ResponseDto kiddResponse = xinyiProductProxyService.synchronize(requestDto);

            if (Objects.equal(kiddResponse.getFlag(), ResponseDto.SUCCESS)) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setErrorMessage(logHeader + "," + kiddResponse.getMessage());
            }
        } catch (Exception e) {
            log.error(logHeader, e);
            response.setSuccess(false);
            response.setErrorMessage(logHeader + "," + Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

    @EventListener
    public Response<KiddInventoryQueryResponseDto> inventoryQuery(KiddInventoryQueryDto kiddInventoryQueryDto) {
        log.info("xinyi 库存查询:{}", JSON.toJSON(kiddInventoryQueryDto));

        if (CollectionUtils.isEmpty(kiddInventoryQueryDto.getCriteriaList())) {
            log.error("库存查询,请求参数出错：{}", JSON.toJSONString(kiddInventoryQueryDto));
            throw new RuntimeException("库存查询,请求参数出错");
        }

        KiddUtils.checkXinyiListening(kiddFacadeService.warehouseInterfaceProvider(kiddInventoryQueryDto.getCriteriaList().get(0).getWarehouseCode()));
        // 接口调用
        Response<KiddInventoryQueryResponseDto> response = new Response<>();
        try {

            InventoryQueryRequestDto requestDto = BeanConvertUtils.convert(kiddInventoryQueryDto, InventoryQueryRequestDto.class);
            InventoryQueryResponseDto wmsReponseDto = xinyiProductProxyService.inventoryQuery(requestDto);

            KiddInventoryQueryResponseDto responseDto = BeanConvertUtils.convert(wmsReponseDto, KiddInventoryQueryResponseDto.class);
            response.setSuccess(true);
            response.setResultObject(responseDto);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

}


