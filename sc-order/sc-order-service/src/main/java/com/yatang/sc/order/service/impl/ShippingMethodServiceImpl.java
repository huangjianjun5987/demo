package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ShippingMethodDao;
import com.yatang.sc.order.domain.ShippingMethod;
import com.yatang.sc.order.service.ShippingMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by xiangyonghong on 2017/7/31.
 */
@Service
public class ShippingMethodServiceImpl implements ShippingMethodService {

    @Autowired
    private ShippingMethodDao shippingMethodDao;

    @Override
    public List<ShippingMethod> getAllShippingMethod() {

        return shippingMethodDao.getAllShippingMethod();
    }
}
