package com.yatang.sc.glink.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Throwables;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.OrderLinesDto;
import com.yatang.sc.glink.dto.SenderInfoDto;
import com.yatang.sc.glink.dto.returnrequest.ReturnOrderDto;
import com.yatang.sc.glink.service.kidd.GlinReturnOrderProxyService;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述:际链退货接口调用
 * @类名:GlinkReturnOrderServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/10/18 13:45
 * @版本: v1.0
 */
@Slf4j
@Service
public class GlinkReturnOrderServiceImpl {
    @Autowired
    private GlinReturnOrderProxyService glinReturnOrderProxyService;
    @Autowired
    private KiddFacadeService kiddFacadeService;

    @EventListener
    public Response<String> create(KiddReturnOrderDto kiddReturnOrderDto){
        log.info("推送退货单：kiddReturnOrderDto {}", JSON.toJSONString(kiddReturnOrderDto));
        if (null == kiddReturnOrderDto || StringUtils.isBlank(kiddReturnOrderDto.getWareHouseCode())) {
            log.error("service----add>>传递参数出错仓库编号不存在:{}",JSON.toJSONString(kiddReturnOrderDto));
            throw new RuntimeException("service----creact>>传递参数出错仓库编号不存在");
        }
        KiddUtils.checkGlinkListening(kiddFacadeService.warehouseInterfaceProvider(kiddReturnOrderDto.getWareHouseCode()));
        Response<String> response = new Response<>();
        try {
            ReturnOrderDto returnOrderDto = BeanConvertUtils.convert(kiddReturnOrderDto, ReturnOrderDto.class);
            KiddSenderInfoDto senderInfo = kiddReturnOrderDto.getSenderInfo();
            SenderInfoDto convert = BeanConvertUtils.convert(senderInfo, SenderInfoDto.class);
            List<KiddOrderLinesDto> orderLines = kiddReturnOrderDto.getOrderLines();
            List<OrderLinesDto> orderLinesDtos = BeanConvertUtils.convertList(orderLines, OrderLinesDto.class);
            returnOrderDto.setSenderInfo(convert);
            returnOrderDto.setOrderLines(orderLinesDtos);
            returnOrderDto.setSaleOrderCodeOwner(kiddReturnOrderDto.getPreDeliveryOrderCode());
            log.info("退货单调用际链参数：returnOrderDto {}", JSON.toJSONString(returnOrderDto));
            GlinkResponseDto<String> creact = glinReturnOrderProxyService.create(returnOrderDto);
            log.info("退货单调用际链接口返回结果：creact {}", JSON.toJSONString(creact));
            if (creact.getCode()==0) {//调用成功
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
            } else {//调用失败
                response.setCode(CommonsEnum.RESPONSE_5001.getCode());
                response.setSuccess(false);
                response.setErrorMessage(creact.getMessage());
            }

        }catch (Exception e){
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }
}
