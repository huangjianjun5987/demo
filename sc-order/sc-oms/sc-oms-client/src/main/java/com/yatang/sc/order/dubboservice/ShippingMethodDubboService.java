package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.dto.ShippingMethodDto;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
public interface ShippingMethodDubboService {
    Response<List<ShippingMethodDto>>  getAllShippingMethod();
}
