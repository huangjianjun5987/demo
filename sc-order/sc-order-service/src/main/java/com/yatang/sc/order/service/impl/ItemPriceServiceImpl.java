package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ItemPriceDao;
import com.yatang.sc.order.domain.ItemPrice;
import com.yatang.sc.order.service.ItemPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
@Service
@Transactional
public class ItemPriceServiceImpl implements ItemPriceService {

    @Autowired
    private ItemPriceDao itemPriceDao;

    @Override
    public ItemPrice getItemPriceForId(Long id) {
        return itemPriceDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ItemPrice itemPrice) {
        itemPriceDao.insert(itemPrice);
    }

    @Override
    public int updateByPrimaryKeySelective(ItemPrice record) {
        return itemPriceDao.updateByPrimaryKeySelective(record);
    }
}
