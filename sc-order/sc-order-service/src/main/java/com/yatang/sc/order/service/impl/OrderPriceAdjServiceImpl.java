package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.OrderPriceAdjDao;
import com.yatang.sc.order.domain.OrderPriceAdj;
import com.yatang.sc.order.service.OrderPriceAdjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
@Service
@Transactional
public class OrderPriceAdjServiceImpl implements OrderPriceAdjService {

    @Autowired
    private OrderPriceAdjDao orderPriceAdjDao;


    @Override
    public void save(OrderPriceAdj orderPriceAdj) {
        orderPriceAdjDao.insert(orderPriceAdj);
    }

    @Override
    public List<OrderPriceAdj> selectByOrderPriceId(Long id) {
        return orderPriceAdjDao.selectByOrderPriceId(id);
    }
}
