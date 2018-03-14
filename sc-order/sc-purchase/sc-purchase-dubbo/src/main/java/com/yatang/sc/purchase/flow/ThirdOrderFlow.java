package com.yatang.sc.purchase.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.OrderDto;

/**
 * Created by xiangyonghong on 2017/10/16.
 */
public interface ThirdOrderFlow {

    /**
     * 保存第三方订单
     * @param orderDto
     * @return
     */
    Response<Boolean> addThirdOrder(OrderDto orderDto);

    /**
     * 签收第三方订单
     * @param thirdPartOrderNo
     * @return
     */
    Response<Boolean> signOrder(String thirdPartOrderNo);
}
