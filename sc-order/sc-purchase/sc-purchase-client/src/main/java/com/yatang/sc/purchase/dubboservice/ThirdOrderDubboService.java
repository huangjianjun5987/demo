package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.OrderDto;

/**
 * Created by xiangyonghong on 2017/10/16.
 */
public interface ThirdOrderDubboService {

    /**
     * 保存第三方订单
     * @param orderDto
     * @return
     */
    Response<Boolean> addThirdOrder(OrderDto orderDto);

    /**
     * 第三方订单签收
     * @param thirdPartOrderNo
     * @return
     */
    Response<Boolean> signOrder(String thirdPartOrderNo);
}
