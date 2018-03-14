package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.OrderClassificationDto;
import com.yatang.sc.purchase.dto.OrderSummaryDto;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
public interface QueryOrderDubboService {

    Response<PageResult<OrderSummaryDto>> queryOrder(OrderClassificationDto orderClassificationDto);
}
