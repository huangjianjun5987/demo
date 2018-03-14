package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.ItemPriceAdjDao;
import com.yatang.sc.order.domain.ItemPriceAdj;
import com.yatang.sc.order.service.ItemPriceAdjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
@Service
@Transactional
public class ItemPriceAdjServiceImpl implements ItemPriceAdjService {

    @Autowired
    private ItemPriceAdjDao itemPriceAdjDao;
    @Override
    public void save(ItemPriceAdj itemPriceAdj) {
        itemPriceAdjDao.insert(itemPriceAdj);
    }

    @Override
    public int update(ItemPriceAdj itemPriceAdj) {
        return itemPriceAdjDao.update(itemPriceAdj);
    }

    @Override
    public List<ItemPriceAdj> selectByItemPriceId(Long id) {
        return itemPriceAdjDao.selectByItemPriceId(id);
    }
}
