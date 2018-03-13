package com.yatang.sc.kidd.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.google.common.base.Objects;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.PhysicalWarehouseDto;
import com.yatang.sc.facade.dto.WarehouseLogicDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.kidd.dto.ProviderRequestDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderSenderInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("kiddFacadeService")
public class KiddFacadeServiceImpl implements KiddFacadeService {

    @Autowired
    public WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Autowired
    private ResultEventPublisher publisher;

    public Object send(ProviderRequestDto providerDto) {
        log.info("xinyi 提供者 发送服务处理 apiMethod:{},payload:{}", providerDto.getApiMethod(), JSON.toJSON(providerDto.getPayload()));

        return publisher.publishEvent(providerDto.getPayload());
    }

    @Override
    public Response<String> warehouseInterfaceProvider(String warehouseCode) {
        Response<String> response = new Response<>();

        if (Objects.equal("WLL", warehouseCode)) {
            response.setSuccess(true);
            response.setResultObject("xinyi");
            return response;
        }

        Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(warehouseCode);
        log.info("service--warehouseInterfaceProvider>>查询仓库信息:{}", JSON.toJSONString(logicWarehouseDtoResponse));
        if (!logicWarehouseDtoResponse.isSuccess()) {
            throw new RuntimeException("获取[" + warehouseCode + "]逻辑仓库失败。");
        }
        LogicWarehouseDto logicWarehouseDto = logicWarehouseDtoResponse.getResultObject();
        if (logicWarehouseDto == null) {
            throw new RuntimeException("获取[" + warehouseCode + "]逻辑仓库不存在。");
        }

        if (null == logicWarehouseDto.getPhysicalWarehouseDto()) {
            throw new RuntimeException("获取[" + warehouseCode + "]物理仓库不存在。");
        }
        String interfaceProvider = InterfaceProviderTypeEnum.getInterfaceProvider(logicWarehouseDto.getPhysicalWarehouseDto().getWarehouseService());

        response.setSuccess(true);
        response.setResultObject(interfaceProvider);
        return response;
    }

    @Override
    public Response<List<LogicWarehouseDto>> queryLogicWarehousesBy(String interfaceProvider) {
        Response<List<LogicWarehouseDto>> response = new Response<>();

//        Response<PageResult<WarehouseLogicDto>> pageResultResponse = warehouseLogicQueryDubboService.getWarehouseByOrCondition(
//                null, null, 1, Integer.MAX_VALUE
//        );
        Response<List<WarehouseLogicDto>> pageResultResponse = warehouseLogicQueryDubboService.queryWarehouseLogicList();
        if (!pageResultResponse.isSuccess()) {
            throw new RuntimeException("获取逻辑仓库列表失败。");
        }
        List<WarehouseLogicDto> logicWarehousePage = pageResultResponse.getResultObject();
        //List<WarehouseLogicDto> logicWarehouses = logicWarehousePage.getData();

        List<LogicWarehouseDto> warehouseDtos = new ArrayList<>();
        for (WarehouseLogicDto logicWarehouse : logicWarehousePage) {
            Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(logicWarehouse.getWarehouseCode());
            if (!logicWarehouseDtoResponse.isSuccess()) {
                continue;
            }
            LogicWarehouseDto logicWarehouseDto = logicWarehouseDtoResponse.getResultObject();
            if (logicWarehouseDto == null) {
                continue;
            }
            if (!Objects.equal(logicWarehouseDto.getPhysicalWarehouseDto().getWarehouseService(), InterfaceProviderTypeEnum.getInterfaceProviderName(interfaceProvider))) {
                continue;
            }

            LogicWarehouseDto dto = new LogicWarehouseDto();
            dto.setWarehouseCode(logicWarehouse.getWarehouseCode());
            dto.setBranchCompanyCode(logicWarehouse.getBranchCompanyCode());
            warehouseDtos.add(dto);
        }

//        LogicWarehouseDto dto = new LogicWarehouseDto();
//        dto.setWarehouseCode("WLL");
//        dto.setBranchCompanyCode("yatang");
//        warehouseDtos.add(dto);

        response.setSuccess(true);
        response.setResultObject(warehouseDtos);
        return response;
    }

    @Override
    public SaleOrderRequestDto setSaleOrderRequestDto(KiddSaleOrderDto saleOrderDto) {

        String warehouseCode = saleOrderDto.getDeliveryOrder().getWarehouseCode();
        log.info("setSaleOrderRequestDto,param:saleOrderDto:{}",JSON.toJSONString(saleOrderDto));
        SaleOrderRequestDto saleOrderRequestDto = BeanConvertUtils.convert(saleOrderDto, SaleOrderRequestDto.class);
        saleOrderRequestDto.getDeliveryOrder().setOrderType(KiddOrderLogisticsType.KIDD_JYCK);//JYCK=一般交易出库单
        saleOrderRequestDto.getDeliveryOrder().setPlaceOrderTime(new Date(saleOrderRequestDto.getDeliveryOrder().getCreateTime().getTime()));
        saleOrderRequestDto.getDeliveryOrder().setOperateTime(new Date(saleOrderRequestDto.getDeliveryOrder().getCreateTime().getTime()));
//        saleOrderRequestDto.getDeliveryOrder().setLogisticsCode("ZTO");
        saleOrderRequestDto.getDeliveryOrder().setSourcePlatformCode(KiddOrderLogisticsType.KIDD_OTHER);
        //获取发件人信息
        SaleOrderSenderInfoDto saleOrderSenderInfoDto = new SaleOrderSenderInfoDto();
        Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(warehouseCode);
        if(!logicWarehouseDtoResponse.isSuccess()){
            log.error("getSaleOrderSenderInfoDto,selectLogicWarehouseByPrimaryKey fail,warehouseCode:{}",warehouseCode);
            throw new RuntimeException("封装发件人信息，获取逻辑仓库失败。");
        }
        saleOrderRequestDto.getDeliveryOrder().setShopNick(logicWarehouseDtoResponse.getResultObject().getBranchCompanyName());
        PhysicalWarehouseDto physicalWarehouseDto = logicWarehouseDtoResponse.getResultObject().getPhysicalWarehouseDto();
        saleOrderSenderInfoDto.setProvince(physicalWarehouseDto.getProvince());
        saleOrderSenderInfoDto.setCity(physicalWarehouseDto.getCity());
        saleOrderSenderInfoDto.setArea(physicalWarehouseDto.getCounty());
        saleOrderSenderInfoDto.setDetailAddress(physicalWarehouseDto.getAddress());
        saleOrderSenderInfoDto.setName(physicalWarehouseDto.getContactPerson());
        saleOrderSenderInfoDto.setMobile(physicalWarehouseDto.getContactMode());
        saleOrderRequestDto.getDeliveryOrder().setSenderInfo(saleOrderSenderInfoDto);
        log.info("setSaleOrderRequestDto,return:saleOrderRequestDto:{}",JSON.toJSONString(saleOrderRequestDto));

        return saleOrderRequestDto;
    }

}


