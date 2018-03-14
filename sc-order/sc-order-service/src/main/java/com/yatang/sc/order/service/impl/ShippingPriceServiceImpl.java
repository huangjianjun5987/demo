package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ShippingPriceDao;
import com.yatang.sc.order.domain.ShippingPrice;
import com.yatang.sc.order.service.ShippingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
@Service
@Transactional
public class ShippingPriceServiceImpl implements ShippingPriceService {

    @Autowired
    private ShippingPriceDao dao;

    @Override
    public void save(ShippingPrice shippingPrice) {
        dao.insert(shippingPrice);
    }

    @Override
    public ShippingPrice selectByPrimaryKey(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShippingPrice record) {
        return dao.updateByPrimaryKeySelective(record);
    }
}
