package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.ProdSellInfoImportDao;
import com.yatang.sc.facade.dao.ProdSellInfoImportsDao;
import com.yatang.sc.facade.domain.ProdSellInfoImportPo;
import com.yatang.sc.facade.domain.ProdSellInfoImportsPo;
import com.yatang.sc.facade.domain.ProdSellPriceUpdateParamPo;
import com.yatang.sc.facade.service.ProdSellInfoImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * @描述: 商品售价导入记录表Service实现类
 * @类名: ProdSellInfoImportServiceImpl
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:30
 * @版本: v1.0
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdSellInfoImportServiceImpl implements ProdSellInfoImportService {

    private final ProdSellInfoImportDao prodSellInfoImportDao;

    private final ProdSellInfoImportsDao prodSellInfoImportsDao;

    @Override
    public ProdSellInfoImportPo selectByPrimaryKey(Long id) {
        return prodSellInfoImportDao.selectByPrimaryKey(id);
    }

    @Override
    public Long insertSellImportList(ProdSellInfoImportsPo prodSellInfoImportsPo) {
        prodSellInfoImportsPo.setCreateTime(new Date());
        prodSellInfoImportsDao.insertSelective(prodSellInfoImportsPo);
        List<ProdSellInfoImportPo> imports = prodSellInfoImportsPo.getImports();
        for(ProdSellInfoImportPo list:imports) {
            list.setImportsId(prodSellInfoImportsPo.getId());
        }
        prodSellInfoImportDao.batchInsertProdSellInfoImport(imports);
        return prodSellInfoImportsPo.getId();
    }

    @Override
    public PageInfo<ProdSellInfoImportPo> getProdSellInfoImportByParam(ProdSellPriceUpdateParamPo paramPo) {
        PageHelper.startPage(paramPo.getPageNum(), paramPo.getPageSize());
        return new PageInfo<>(prodSellInfoImportDao.getProdSellInfoImportByParam(paramPo));
    }

    @Override
    public ProdSellInfoImportsPo selectById(Long id) {
        return prodSellInfoImportsDao.selectByPrimaryKey(id);
    }

    @Override
    public Boolean updateHandleResult(ProdSellInfoImportPo record) {
        return prodSellInfoImportDao.updateHandleResult(record) >= 1;
    }

}
