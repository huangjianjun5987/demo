package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.PriceAdjustmentDao;
import com.yatang.sc.order.domain.PriceAdjustment;
import com.yatang.sc.order.service.PriceAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
@Service
@Transactional
public class PriceAdjustmentServiceImpl implements PriceAdjustmentService {

    @Autowired
    private PriceAdjustmentDao priceAdjustmentDao;

    @Override
    public void save(PriceAdjustment priceAdjustment) {
        priceAdjustmentDao.insert(priceAdjustment);
    }

    @Override
    public PriceAdjustment selectByPrimaryKey(Long adjustmentId) {
        return priceAdjustmentDao.selectByPrimaryKey(adjustmentId);
    }

    @Override
    public int updateByPrimaryKey(PriceAdjustment record) {
        return priceAdjustmentDao.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PriceAdjustment record) {
        return priceAdjustmentDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<PriceAdjustment> queryByOrderPriceId(Long id) {
        return priceAdjustmentDao.queryByOrderPriceId(id);
    }
}
