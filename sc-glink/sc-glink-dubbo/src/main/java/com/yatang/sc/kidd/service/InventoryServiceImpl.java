package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.im.ImAdjustmentResultDto;
import com.yatang.sc.kidd.dto.im.InventoryQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

@Service("kiddInventoryService")
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ResultEventPublisher publisher;

    public Response<ImAdjustmentResultDto> inventoryQuery(InventoryQueryDto inventoryQueryDto) {
        Response<ImAdjustmentResultDto> result = (Response<ImAdjustmentResultDto>) publisher.publishEvent(inventoryQueryDto);

        return result;
    }

}


