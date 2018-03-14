package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ShippingPriceAdjDao;
import com.yatang.sc.order.domain.ShippingPriceAdj;
import com.yatang.sc.order.service.ShippingPriceAdjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
@Service
@Transactional
public class ShippingPriceAdjServiceImpl implements ShippingPriceAdjService {

    @Autowired
    private ShippingPriceAdjDao shippingPriceAdjDao;

    @Override
    public void save(ShippingPriceAdj shippingPriceAdj) {
        shippingPriceAdjDao.insert(shippingPriceAdj);
    }

    @Override
    public List<ShippingPriceAdj> selectByShippingPriceId(Long id) {
        return shippingPriceAdjDao.selectByShippingPriceId(id);
    }
}
