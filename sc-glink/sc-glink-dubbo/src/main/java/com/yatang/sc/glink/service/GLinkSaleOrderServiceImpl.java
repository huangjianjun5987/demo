package com.yatang.sc.glink.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.saleOrder.SaleCargoDto;
import com.yatang.sc.glink.dto.saleOrder.SaleGetInfoDto;
import com.yatang.sc.glink.dto.saleOrder.SaleOrderDto;
import com.yatang.sc.glink.service.kidd.GLinkSaleOrderProxyService;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderCargoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDeliveryInfoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: Glink销售订单处理service接口
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 14:33
 * @版本: v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GLinkSaleOrderServiceImpl {


    private final GLinkSaleOrderProxyService gLinkSaleOrderProxyService;

    private final KiddFacadeService kiddFacadeService;

    @EventListener
    public Response<String> add(KiddSaleOrderDto kiddSaleOrderDto) {

        log.info("service----add>>际链处理新增销售订单--请求参数:{}", JSON.toJSONString(kiddSaleOrderDto));

        if (null == kiddSaleOrderDto || null == kiddSaleOrderDto.getDeliveryOrder() || StringUtils.isBlank(kiddSaleOrderDto.getDeliveryOrder().getWarehouseCode())) {
            log.error("service----inventoryQuery>>传递参数出错仓库编号不存在:{}", JSON.toJSONString(kiddSaleOrderDto));
            throw new RuntimeException("service----inventoryQuery>>传递参数出错仓库编号不存在");
        }
        KiddUtils.checkGlinkListening(kiddFacadeService.warehouseInterfaceProvider(kiddSaleOrderDto.getDeliveryOrder().getWarehouseCode()));

        KiddSaleOrderDeliveryInfoDto deliveryOrder = kiddSaleOrderDto.getDeliveryOrder();
        //数据转换
        SaleOrderDto saleOrderDto = new SaleOrderDto();
        //与scm相关联的一些信息
        saleOrderDto.setOrderCode(deliveryOrder.getDeliveryOrderCode());//发货出库
        saleOrderDto.setWarehouseCode(deliveryOrder.getWarehouseCode());
        saleOrderDto.setOwnerCode(deliveryOrder.getOwnerCode());//分公司编号
        saleOrderDto.setOrderTime(deliveryOrder.getCreateTime());
        saleOrderDto.setLogisticsCode(deliveryOrder.getLogisticsCode());
        saleOrderDto.setLogisticsName(deliveryOrder.getLogisticsName());
        saleOrderDto.setOperTimeERP(null);//货主订单审核通过时间
        saleOrderDto.setPayTime(deliveryOrder.getPayTime());


                //收货人信息
        SaleGetInfoDto saleGetInfo = new SaleGetInfoDto();
        if("DSXS".equals(deliveryOrder.getScOrderType())
                || "XCC".equals(deliveryOrder.getScOrderType()) ){
            saleOrderDto.setDeliverOrderType("XSCK");//销售出库
            saleOrderDto.setLogisticsCode(deliveryOrder.getLogisticsCode());
            saleOrderDto.setLogisticsName(deliveryOrder.getLogisticsName());
        }else{
            saleOrderDto.setShopName(deliveryOrder.getRemarkAddress());//传加盟商名称，规则如下:雅堂小超_No.000999(凯华丽景便利店)
            saleOrderDto.setDeliverOrderType(KiddOrderLogisticsType.JL_FHCK);//发货出库
            saleGetInfo.setCustmoerCode(deliveryOrder.getReceiverInfo().getCustmoerCode());
        }

        saleOrderDto.setPreDeliveryOrderCode(deliveryOrder.getPreDeliveryOrderCode());//原发货单单号
        saleGetInfo.setConsignee(deliveryOrder.getReceiverInfo().getName());
        saleGetInfo.setConsigneePhone(deliveryOrder.getReceiverInfo().getMobile());
        saleGetInfo.setDeliveryProvince(deliveryOrder.getReceiverInfo().getProvince());
        saleGetInfo.setDeliveryCity(deliveryOrder.getReceiverInfo().getCity());
        saleGetInfo.setDeliveryAddress(deliveryOrder.getReceiverInfo().getDetailAddress());
        saleGetInfo.setDeliveryDistrict(deliveryOrder.getReceiverInfo().getArea());
        saleOrderDto.setGetInfo(saleGetInfo);


        //商品信息
        List<SaleCargoDto> cargoDtoList = new ArrayList<>();
        for (KiddSaleOrderCargoDto saleOrderCargoDto : kiddSaleOrderDto.getOrderLines()) {
            SaleCargoDto cargo = new SaleCargoDto();
            cargo.setItemCode(saleOrderCargoDto.getItemCode());
            cargo.setName(saleOrderCargoDto.getItemName());
            cargo.setInventoryType(saleOrderCargoDto.getInventoryType());
            cargo.setQuantity(saleOrderCargoDto.getPlanQty());
            cargo.setUnit(saleOrderCargoDto.getUnit());
            cargo.setRetailPrice(saleOrderCargoDto.getRetailPrice());
            cargo.setActualPrice(saleOrderCargoDto.getActualPrice());
            cargo.setDiscountAmount(saleOrderCargoDto.getDiscountAmount());
            cargo.setOrderLineNo(saleOrderCargoDto.getOrderLineNo());
            cargoDtoList.add(cargo);
        }
        saleOrderDto.setCargo(cargoDtoList);
        // 响应数据修改
        GlinkResponseDto<String> responseDto = gLinkSaleOrderProxyService.add(saleOrderDto);
        log.info("service----add>>际链处理新增销售订单返回结果：{}", JSON.toJSONString(responseDto));
        Response<String> response = new Response<>();
        if (0 == responseDto.getCode()) {
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.setCode(String.valueOf(responseDto.getCode()));
            response.setErrorMessage(responseDto.getMessage());
        }
        return response;

    }

}
