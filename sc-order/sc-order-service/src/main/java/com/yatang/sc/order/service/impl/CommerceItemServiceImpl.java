package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.CommerceItemDao;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.states.CommerceItemStatus;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
@Service
@Transactional
public class CommerceItemServiceImpl implements CommerceItemService {

    @Autowired
    private CommerceItemDao dao;
    @Override
    public CommerceItem getCommerceItemForId(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public void save(CommerceItem commerceItem) {
        dao.insert(commerceItem);
    }

    @Override
    public List<CommerceItem> getCommerceItemForOrderId(String orderId) {
        return dao.getCommerceItemForOrderId(orderId);
    }

    @Override
    public void updateByPrimaryKeySelective(CommerceItem commerceItem) {
        dao.updateByPrimaryKeySelective(commerceItem);
    }

    @Override
    public void updateState(List<Long> enoughStock, List<Long> lessStock) {
        if (CollectionUtils.isNotEmpty(enoughStock)) {
            dao.updateState(enoughStock, CommerceItemStatus.STOCK_ENOUGH.getStateValue(), CommerceItemStatus.STOCK_ENOUGH.getDescription());
        }
        if (CollectionUtils.isNotEmpty(lessStock)) {
            dao.updateState(lessStock, CommerceItemStatus.STOCK_NOT_ENOUGH.getStateValue(), CommerceItemStatus.STOCK_NOT_ENOUGH.getDescription());
        }
    }

    @Override
    public void updateCommerceItem(CommerceItem commerceItem) {
        dao.updateByPrimaryKey(commerceItem);
    }

    @Override
    public List<CommerceItem> getCommerceItemAndPriceForOrderId(String orderId) {
        return dao.getCommerceItemAndPriceForOrderId(orderId);
    }
}
