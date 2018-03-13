package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.ProdPriceChangeDao;
import com.yatang.sc.facade.domain.ProdPriceChangePo;
import com.yatang.sc.facade.domain.ProdPriceChangeQueryPo;
import com.yatang.sc.facade.service.ProdPriceChangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @描述: 商品价格(进价,售价)变动 service实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 11:28
 * @版本: v1.0
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPriceChangeServiceImpl implements ProdPriceChangeService {


    private final ProdPriceChangeDao priceChangeDao;

    @Override
    public boolean addProdPriceChange(ProdPriceChangePo priceChangePo) {
        return priceChangeDao.insertSelective(priceChangePo) == 1;

    }


    @Override
    public PageInfo<ProdPriceChangePo> selectProdPriceChangePoList(ProdPriceChangeQueryPo prodPriceChangeQueryPo) {
        PageHelper.startPage(prodPriceChangeQueryPo.getPageNum(), prodPriceChangeQueryPo.getPageSize());
        List<ProdPriceChangePo> prodPriceChangePoList = priceChangeDao.selectProdPriceChangePoList(prodPriceChangeQueryPo);
        return new PageInfo<>(prodPriceChangePoList);
    }
}
