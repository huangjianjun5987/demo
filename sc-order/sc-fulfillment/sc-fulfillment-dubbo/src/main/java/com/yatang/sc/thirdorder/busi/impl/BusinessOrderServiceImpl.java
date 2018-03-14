package com.yatang.sc.thirdorder.busi.impl;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.dubboservice.SyncGYLOrderStateService;
import com.yatang.dubboservice.dto.OrderStateDto;
import com.yatang.sc.common.utils.excel.ExcelName;
import com.yatang.sc.thirdorder.busi.BusinessOrderService;
import com.yatang.sc.thirdorder.busi.dto.BusiOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusinessOrderServiceImpl implements BusinessOrderService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    SyncGYLOrderStateService syncGYLOrderStateService;
    @Override
    public void updateShippingStates(BusiOrderDto busiOrderDto) {
        log.info("调用电商接口更新订单物流状态,param{}", JSONObject.toJSON(busiOrderDto));
        OrderStateDto orderStateDto = BeanConvertUtils.convert(busiOrderDto,OrderStateDto.class);
        try{
            Map<String,Object> rep = syncGYLOrderStateService.updateGYLOrderState(orderStateDto);
            if(rep.get("success")!=null && !Boolean.valueOf(rep.get("success").toString())){
                log.error("调用电商接口更新订单物流状态失败,result{}",JSONObject.toJSON(rep));
                return ;
            }
            log.info("调用电商接口更新订单物流状态成功,result{}",JSONObject.toJSON(rep));
        }catch (Exception e){
            e.printStackTrace();
            log.info("调用电商接口更新订单物流状态异常");
        }

    }
}
