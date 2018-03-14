package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.OrderPriceDao;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.service.OrderPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/10 20:42
 * @版本:v1.0
 */
@Service
@Transactional
public class OrderPriceServiceImpl implements OrderPriceService {
    @Autowired
    private OrderPriceDao orderPriceDao;

    @Override
    public OrderPrice getOrderPriceForId(Long id) {
        return orderPriceDao.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderPriceDao.deleteByPrimaryKey( id );
    }

    @Override
    public int insertSelective(OrderPrice record) {
        return orderPriceDao.insertSelective( record );
    }

    @Override
    public OrderPrice selectByPrimaryKey(Long id) {
        return orderPriceDao.selectByPrimaryKey( id );
    }

    @Override
    public int updateByPrimaryKeySelective(OrderPrice record) {
        return orderPriceDao.updateByPrimaryKeySelective( record );
    }

    @Override
    public void save(OrderPrice orderPrice) {
        orderPriceDao.insert(orderPrice);
    }

	@Override
	public int updateOrderPriceCouponActivityReverted(Long id) {
		return orderPriceDao.updateOrderPriceCouponActivityReverted(id);
	}
}
