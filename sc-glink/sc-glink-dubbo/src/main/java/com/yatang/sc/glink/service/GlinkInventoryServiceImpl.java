package com.yatang.sc.glink.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.im.ImAdjuestmentParamentDto;
import com.yatang.sc.glink.dto.im.InventoryReportRequestDto;
import com.yatang.sc.glink.service.kidd.GlinkProductProxyService;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentDataDto;
import com.yatang.sc.kidd.dto.im.ImAdjustmentResultDto;
import com.yatang.sc.kidd.dto.im.InventoryQueryDto;
import com.yatang.sc.kidd.dto.im.WarehouseProductDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class GlinkInventoryServiceImpl {

    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private GlinkProductProxyService productProxyService;

    @Resource(name = "kiddAdjustmentMQProducer")
    private SimpleMQProducer kiddAdjustmentMQProducer;

    @EventListener
    public Response<ImAdjustmentResultDto> inventoryQuery(InventoryQueryDto inventoryQueryDto) {


        if (null == inventoryQueryDto || StringUtils.isBlank(inventoryQueryDto.getWarehouseCode())) {
            log.error("service----inventoryQuery>>传递参数出错仓库编号不存在:{}", JSON.toJSONString(inventoryQueryDto));
            throw new RuntimeException("service----inventoryQuery>>传递参数出错仓库编号不存在");
        }

        KiddUtils.checkGlinkListening(kiddFacadeService.warehouseInterfaceProvider(inventoryQueryDto.getWarehouseCode()));
        ImAdjuestmentParamentDto imAdjuestmentParamentDto = BeanConvertUtils.convert(inventoryQueryDto, ImAdjuestmentParamentDto.class);
        imAdjuestmentParamentDto.setPageNo(1);//当前页数
        imAdjuestmentParamentDto.setPageSize(500);//每页显示条数
        imAdjuestmentParamentDto.setStartTime(new Date());//查询开始时间
        imAdjuestmentParamentDto.setEndTime(new Date());//查询结束时间
        GlinkResponseDto<ImAdjustmentResultDto> query = productProxyService.inventoryQuery(imAdjuestmentParamentDto);
        log.info("查询际链库存信息 query {}" , JSON.toJSONString(query));
        Response response = new Response();
        if (query.getCode() != 0) {
            response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
            response.setCode(CommonsEnum.RESPONSE_20006.getCode());
            response.setSuccess(false);
            return response;
        }
        ImAdjustmentResultDto data = query.getData();
        if (data == null) {
            response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
            response.setCode(CommonsEnum.RESPONSE_20006.getCode());
            response.setSuccess(false);
            return response;
        }
        List<WarehouseProductDto> result = data.getResult();
        log.info("查询际链仓库信息result {}", result);
        if (result == null || result.size() == 0) {
            response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
            response.setCode(CommonsEnum.RESPONSE_20006.getCode());
            response.setSuccess(false);
            return response;
        }
        int pageNo = data.getPageNo();//当前页数
        int pageSize = data.getPageSize();//每页显示记录数
        int total = data.getTotal();//总记录数
        for (int i = pageNo + 1; i <= (total / pageSize) + 1; i++) {
            imAdjuestmentParamentDto.setPageNo(i);
            GlinkResponseDto<ImAdjustmentResultDto> glinkResponseDto = productProxyService.inventoryQuery(imAdjuestmentParamentDto);
            log.info("查询际链库存信息 glinkResponseDto{}" , i + JSON.toJSONString(glinkResponseDto));
            if (glinkResponseDto.getCode() == 0) {
                response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
                response.setCode(CommonsEnum.RESPONSE_20006.getCode());
                response.setSuccess(false);
                return response;
            }
            ImAdjustmentResultDto imAdjustmentResultDto = glinkResponseDto.getData();
            if (imAdjustmentResultDto == null) {
                response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
                response.setCode(CommonsEnum.RESPONSE_20006.getCode());
                response.setSuccess(false);
                return response;
            }
            List<WarehouseProductDto> warehouseProductDtos = imAdjustmentResultDto.getResult();
            log.info("查询际链仓库信息warehouseProductDtos: {}" , warehouseProductDtos);
            if (warehouseProductDtos == null || warehouseProductDtos.size() == 0) {
                response.setErrorMessage(CommonsEnum.RESPONSE_20006.getName());
                response.setCode(CommonsEnum.RESPONSE_20006.getCode());
                response.setSuccess(false);
                return response;
            }
            result.addAll(warehouseProductDtos);
        }
        response.setSuccess(true);
        response.setResultObject(result);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        return response;
    }


    /**
     * 际链 库存盘点通知 处理
     *
     * @param requestDto
     * @return
     */
    @EventListener
    public Response<String> inventoryReportHandle(InventoryReportRequestDto requestDto) {
        Response<String> response = new Response<>();
        log.info("service---inventoryReportHandle>>库存调整通知:{}", JSON.toJSONString(requestDto));
        KiddImAdjustmentDataDto data = JSON.parseObject(requestDto.getData(), KiddImAdjustmentDataDto.class);//获取调整单数据
        if (null == data) {
            response.setCode("1");
            response.setErrorMessage("调整单数据不能为空");
            return response;
        }
        String dataOwnerCode = data.getOwnerCode();
        if (StringUtils.isEmpty(dataOwnerCode)) {
            response.setCode("8106");
            response.setErrorMessage("ownerCode不能为空");
            return response;
        }
        //mq发送
        kiddAdjustmentMQProducer.sendOrderlyMsg(data.getOwnerCode(), data);
        response.setSuccess(true);
        return response;
    }

}


