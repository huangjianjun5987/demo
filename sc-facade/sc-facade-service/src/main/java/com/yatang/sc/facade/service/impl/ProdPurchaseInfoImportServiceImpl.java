package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.ProdPurchaseInfoImportDao;
import com.yatang.sc.facade.dao.ProdPurchaseInfoImportsDao;
import com.yatang.sc.facade.domain.ProdPurchaseInfoImportPo;

import com.yatang.sc.facade.domain.ProdPurchaseInfoImportsPo;
import com.yatang.sc.facade.domain.ProdPurchasePriceUpdateParamPo;
import com.yatang.sc.facade.service.ProdPurchaseInfoImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;


/**
 * @描述: 商品采购价导入记录Service实现类
 * @类名: ProdPurchaseInfoImportServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:30
 * @版本: v1.0
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseInfoImportServiceImpl implements ProdPurchaseInfoImportService {

    @Autowired
    private final ProdPurchaseInfoImportDao prodPurchaseInfoImportDao;

    @Autowired
    private final ProdPurchaseInfoImportsDao prodPurchaseInfoImportsDao;

    @Override
    public ProdPurchaseInfoImportPo selectByPrimaryKey(Long id) {
        return prodPurchaseInfoImportDao.selectByPrimaryKey(id);
    }


    @Override
    public PageInfo<ProdPurchaseInfoImportPo> getProdPurchaseInfoImportByParam(ProdPurchasePriceUpdateParamPo paramPo) {
        PageHelper.startPage(paramPo.getPageNum(), paramPo.getPageSize());
        return new PageInfo<>(prodPurchaseInfoImportDao.getProdPurchaseInfoImportByParam(paramPo));
    }

    @Override
    public Long insertImportList(ProdPurchaseInfoImportsPo prodPurchaseInfoImportsPo) {
        prodPurchaseInfoImportsPo.setCreateTime(new Date());
        prodPurchaseInfoImportsDao.insertSelective(prodPurchaseInfoImportsPo);
        List<ProdPurchaseInfoImportPo> prodPurchaseInfoImportPo = prodPurchaseInfoImportsPo.getImports();
        for(ProdPurchaseInfoImportPo list:prodPurchaseInfoImportsPo.getImports()) {
            list.setImportsId(prodPurchaseInfoImportsPo.getId());
        }
        prodPurchaseInfoImportDao.batchInsertProdPurchaseInfoImport(prodPurchaseInfoImportPo);
        return prodPurchaseInfoImportsPo.getId();
    }

    @Override
    public Boolean updateHandleResult(ProdPurchaseInfoImportPo record) {
        return prodPurchaseInfoImportDao.updateHandleResult(record) >= 1;
    }


    @Override
    public ProdPurchaseInfoImportsPo selectById(Long id) {
        return prodPurchaseInfoImportsDao.selectByPrimaryKey(id);
    }
}
