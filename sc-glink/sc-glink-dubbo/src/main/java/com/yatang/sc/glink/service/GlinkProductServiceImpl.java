package com.yatang.sc.glink.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.product.CommodityDto;
import com.yatang.sc.glink.dto.product.CommodityItemDto;
import com.yatang.sc.glink.dto.product.CommodityReqDto;
import com.yatang.sc.glink.service.kidd.GlinkProductProxyService;
import com.yatang.sc.kidd.dto.product.ProductSynchronizeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GlinkProductServiceImpl {

    @Autowired
    private GlinkProductProxyService productProxyService;

    @EventListener
    public Response<String> synchronizeProduct(ProductSynchronizeDto productSynchronizeDto) {
        log.info("glink 商品同步:{}", JSON.toJSON(productSynchronizeDto));

        Response<String> response = new Response<>();
        try {
            CommodityDto commodityDto = new CommodityDto();
            // 系统重复订单处理方式:1、直接返回错误；2、重复商品跳过(skip)；3、重复商品修改(replace)
            commodityDto.setType("3");
            CommodityItemDto commodityItemDto = BeanConvertUtils.convert(productSynchronizeDto, CommodityItemDto.class);
            commodityItemDto.setBarcode(productSynchronizeDto.getBarCode());
            commodityItemDto.setCreateTimeERP(productSynchronizeDto.getCreateTime());
            commodityItemDto.setUpdateTimeERP(productSynchronizeDto.getUpdateTime());
            commodityDto.setItems(Lists.newArrayList(commodityItemDto));

            GlinkResponseDto<CommodityReqDto> glinkResponseDto = productProxyService.createOrUpdateCommodity(commodityDto);

            if (0 == glinkResponseDto.getCode()) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setErrorMessage(glinkResponseDto.getMessage());
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

}


