package com.yatang.sc.facade.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.facade.dao.ProdPurchaseInfoDao;
import com.yatang.sc.facade.dao.ProdPurchaseInfoLogDao;
import com.yatang.sc.facade.domain.ProPurchaseBatchChangeStatusPo;
import com.yatang.sc.facade.domain.ProdPurchaseExtPo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoLogPo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoPo;
import com.yatang.sc.facade.domain.ProdPurchaseListPo;
import com.yatang.sc.facade.domain.ProdPurchaseModifyParamPo;
import com.yatang.sc.facade.domain.ProdPurchasePriceAuditPo;
import com.yatang.sc.facade.domain.ProdPurchaseQueryParamPo;
import com.yatang.sc.facade.service.ProdPurchaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @描述: 商品采购价格Service实现类
 * @类名: ProdPurchaseSeviceImpl
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:12
 * @版本: v1.0
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseServiceImpl implements ProdPurchaseService {

    private final ProdPurchaseInfoDao prodPurchaseInfoDao;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    private final ProdPurchaseInfoLogDao prodPurchaseInfoLogDao;

    @Override
    public int getPurchasePriceCount(Long id, String spAdrId, String productId) {
        return prodPurchaseInfoDao.getPurchasePriceCount(id, spAdrId, productId);
    }

    @Override
    public boolean addProdPurchase(ProdPurchaseInfoPo prodPurchaseInfoPo) {
        prodPurchaseInfoPo.setCreateTime(new Date());//创建时间
        prodPurchaseInfoPo.setStatus(0);//默认未启用
        prodPurchaseInfoPo.setDeleteStatus(0);//伪删除
        String lockPath = "/prod_purchase_" + prodPurchaseInfoPo.getProductId() + prodPurchaseInfoPo.getBranchCompanyId();//根据商品id和分公司id枷锁 //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock();
            //判定
            int purchasePriceCount = prodPurchaseInfoDao.getPurchasePriceCount(null, prodPurchaseInfoPo.getSpAdrId(), prodPurchaseInfoPo.getProductId());
            if (purchasePriceCount >= 1) {
                return false;
            }
            if (1 == prodPurchaseInfoPo.getSupplierType()) {//当为主供应商时
                //prodPurchaseService 将其他的设定为普通供应商
                //检查是当前productId对应下的分公司否有存在是主供应商的有效数据
                int count = prodPurchaseInfoDao.getValidSupplierTypeCount(prodPurchaseInfoPo.getProductId(), prodPurchaseInfoPo.getBranchCompanyId(), prodPurchaseInfoPo.getSupplierType());
                if (count == 1) {
                    boolean a = prodPurchaseInfoDao.insertSelective(prodPurchaseInfoPo) >= 1;//插入
                    ProdPurchaseModifyParamPo prodPurchaseModifyParamPo = new ProdPurchaseModifyParamPo();
                    prodPurchaseModifyParamPo.setId(prodPurchaseInfoPo.getId());
                    prodPurchaseModifyParamPo.setModifyDateTime(new Date());
                    prodPurchaseModifyParamPo.setModifyUserId(prodPurchaseInfoPo.getCreateUserId());
                    prodPurchaseModifyParamPo.setSupplierType(0);//另一个主供应商设置为普通供应商
                    prodPurchaseModifyParamPo.setProductId(prodPurchaseInfoPo.getProductId());
                    prodPurchaseModifyParamPo.setBranchCompanyId(prodPurchaseInfoPo.getBranchCompanyId());//设置分公司id
                    boolean b = prodPurchaseInfoDao.changeOtherSupplierType(prodPurchaseModifyParamPo) >= 1;//修改
                    //记录采购价格变更日志
                    addProdPurchaseLog(prodPurchaseInfoPo, true);
                    return a && b;
                }
            }
            boolean insertSuccess = prodPurchaseInfoDao.insertSelective(prodPurchaseInfoPo) == 1;
            if (insertSuccess) {
                addProdPurchaseLog(prodPurchaseInfoPo, true);
            }
            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }


        return false;
    }


    @Override
    public boolean deleteProdPurchaseById(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo) {
        prodPurchaseModifyParamPo.setModifyDateTime(new Date());
        prodPurchaseModifyParamPo.setDeleteStatus(1);//设置删除状态
        boolean deleteSuccess = prodPurchaseInfoDao.deleteByPrimaryKey(prodPurchaseModifyParamPo) >= 1;

        //记录操作日志
        if (deleteSuccess) {

            ProdPurchaseInfoLogPo logPo = new ProdPurchaseInfoLogPo();
            logPo.setOperateDate(new Date());
            logPo.setPriceId(String.valueOf(prodPurchaseModifyParamPo.getId()));
            logPo.setOperate("删除采购关系操作");
            logPo.setOperateUserId(prodPurchaseModifyParamPo.getModifyUserId());
            prodPurchaseInfoLogDao.insertSelective(logPo);
        }

        return deleteSuccess;
    }

    @Override
    public ProdPurchaseExtPo getProdPurchaseById(Long id) {
        return prodPurchaseInfoDao.selectByPrimaryKey(id);
    }

    @Override
    public ProdPurchaseInfoPo selectByPrimaryId(Long id) {
        return prodPurchaseInfoDao.selectByPrimaryId(id);
    }


    @Override
    public boolean updateProdPurchaseSelective(ProdPurchaseInfoPo prodPurchaseInfoPo) {

        Integer supplierType = prodPurchaseInfoPo.getSupplierType() == null ? 0 : prodPurchaseInfoPo.getSupplierType();
        //是否更改了主供应商
        if (1 == supplierType) {//非主供应商直接更新
            ProdPurchaseModifyParamPo prodPurchaseModifyParamPo = new ProdPurchaseModifyParamPo();
            prodPurchaseModifyParamPo.setId(prodPurchaseInfoPo.getId());
            prodPurchaseModifyParamPo.setModifyDateTime(new Date());
            prodPurchaseModifyParamPo.setModifyUserId(prodPurchaseInfoPo.getCreateUserId());
            prodPurchaseModifyParamPo.setSupplierType(0);//另一个主供应商设置为普通供应商
            prodPurchaseModifyParamPo.setProductId(prodPurchaseInfoPo.getProductId());
            prodPurchaseModifyParamPo.setBranchCompanyId(prodPurchaseInfoPo.getBranchCompanyId());//设置分公司id
            prodPurchaseInfoDao.changeOtherSupplierType(prodPurchaseModifyParamPo);//修改
        }
        prodPurchaseInfoPo.setModifyTime(new Date());//创建时间

        addProdPurchaseLog(prodPurchaseInfoPo, false);
        return prodPurchaseInfoDao.updateByPrimaryKeySelective(prodPurchaseInfoPo) == 1;
    }

    @Override
    public boolean changeSupplierType(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo) {
        //1.当需要设置当前的id为主供应商 将为主供应商的设置为非主供应商
        //检查当前的id对应的是否是有效数据
        int number = prodPurchaseInfoDao.getValidCountById(prodPurchaseModifyParamPo.getId());
        if (0 == number) {
            return false;
        }
        Integer supplierType = prodPurchaseModifyParamPo.getSupplierType();
        if (1 == supplierType) {
            //检查是当前的商品是否有存在是主供应商的有效数据
            int count = prodPurchaseInfoDao.getValidSupplierTypeCount(prodPurchaseModifyParamPo.getProductId(), prodPurchaseModifyParamPo.getBranchCompanyId(), supplierType);
            if (count == 1) {
                ProdPurchaseModifyParamPo purchaseModifyParamPo = new ProdPurchaseModifyParamPo();
                purchaseModifyParamPo.setId(prodPurchaseModifyParamPo.getId());
                purchaseModifyParamPo.setSupplierType(0);//一般供应商类型
                purchaseModifyParamPo.setModifyDateTime(new Date());
                purchaseModifyParamPo.setProductId(prodPurchaseModifyParamPo.getProductId());
                purchaseModifyParamPo.setModifyUserId(prodPurchaseModifyParamPo.getModifyUserId());
                purchaseModifyParamPo.setBranchCompanyId(prodPurchaseModifyParamPo.getBranchCompanyId());
                prodPurchaseInfoDao.changeOtherSupplierType(purchaseModifyParamPo);
            }
        }

        //2.当需要将主供应商设置为非主供应商,直接切换

        //记录日志
        prodPurchaseModifyParamPo.setModifyDateTime(new Date());
        ProdPurchaseInfoLogPo logPo = new ProdPurchaseInfoLogPo();
        logPo.setOperateDate(new Date());
        logPo.setPriceId(String.valueOf(prodPurchaseModifyParamPo.getId()));
        logPo.setOperate("切换供应商类型操作");
        logPo.setOperateUserId(prodPurchaseModifyParamPo.getModifyUserId());
        prodPurchaseInfoLogDao.insertSelective(logPo);


        return prodPurchaseInfoDao.changeSupplierType(prodPurchaseModifyParamPo) >= 1;
    }


    @Override
    public boolean changeProPurchaseStatus(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo) {

        prodPurchaseModifyParamPo.setModifyDateTime(new Date());

        boolean updateSuccess = prodPurchaseInfoDao.changeProPurchaseStatus(prodPurchaseModifyParamPo) == 1;

        if (updateSuccess) {
            //记录日志
            prodPurchaseModifyParamPo.setModifyDateTime(new Date());
            ProdPurchaseInfoLogPo logPo = new ProdPurchaseInfoLogPo();
            logPo.setOperateDate(new Date());
            logPo.setPriceId(String.valueOf(prodPurchaseModifyParamPo.getId()));
            logPo.setOperate("更改采购关系启禁用状态");
            logPo.setOperateUserId(prodPurchaseModifyParamPo.getModifyUserId());
            prodPurchaseInfoLogDao.insertSelective(logPo);
        }
        return updateSuccess;
    }

    @Override
    public PageInfo<ProdPurchaseExtPo> queryProdPurchaseExtByCondition(ProdPurchaseQueryParamPo purchasePriceQueryParamPo) {
        PageHelper.startPage(purchasePriceQueryParamPo.getPageNum(), purchasePriceQueryParamPo.getPageSize());
        List<ProdPurchaseExtPo> list = prodPurchaseInfoDao.queryProdPurchaseExtByCondition(purchasePriceQueryParamPo);
        PageInfo<ProdPurchaseExtPo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public boolean batchChangeProPurchaseStatus(ProPurchaseBatchChangeStatusPo proPurchaseBatchChangeStatusPo) {
        proPurchaseBatchChangeStatusPo.setModifyDateTime(new Date());

        ProdPurchaseInfoLogPo logPo = new ProdPurchaseInfoLogPo();
        logPo.setOperateDate(new Date());
        logPo.setPriceId(JSON.toJSONString(proPurchaseBatchChangeStatusPo.getProductIdList()));
        logPo.setOperate("批量更改采购关系启禁用状态");
        logPo.setOperateUserId(proPurchaseBatchChangeStatusPo.getModifyUserId());
        prodPurchaseInfoLogDao.insertSelective(logPo);


        return prodPurchaseInfoDao.batchChangeProPurchaseStatus(proPurchaseBatchChangeStatusPo) >= 1;
    }

    @Override
    public boolean checkMainSupplier(String productId, String branchCompanyId, int supplierType) {
        return prodPurchaseInfoDao.getValidSupplierTypeCount(productId, branchCompanyId, supplierType) >= 1;
    }

    @Override
    public boolean checkPurchasePriceModifyById(Long id, BigDecimal purchasePrice) {
        return prodPurchaseInfoDao.checkPurchasePriceModifyById(id, purchasePrice) == 0;
    }

    @Override
    public boolean updateProdPurchaseByPrimaryKeySelective(ProdPurchaseInfoPo purchaseInfoPo) {
        return prodPurchaseInfoDao.updateProdPurchaseByPrimaryKeySelective(purchaseInfoPo) >= 1;
    }

    @Override
    public List<ProdPurchaseListPo> queryProdPurchaseListPoByProductIds(List<String> productIds) {
        return prodPurchaseInfoDao.queryProdPurchaseListPoByProductIds(productIds);
    }

    @Override
    public ProdPurchaseInfoPo getProdPurchaseByParam(ProdPurchaseQueryParamPo queryParamPo) {
        return prodPurchaseInfoDao.getProdPurchaseByParam(queryParamPo);
    }

    @Override
    public List<ProdPurchaseInfoPo> queryProdPurchaseListByParam(ProdPurchaseQueryParamPo queryParamPo) {
        return  prodPurchaseInfoDao.queryProdPurchaseListByParam(queryParamPo);
    }



    @Override
    public BigDecimal queryPurchasePriceForProdSell(String productId, String branchCompanyId) {
        List<ProdPurchaseInfoPo> prodPurchaseInfoPos=prodPurchaseInfoDao.queryPurchasePriceForProdSell(branchCompanyId,productId);
        if (prodPurchaseInfoPos==null || prodPurchaseInfoPos.size()<=0){
            return null;
        }
        for (ProdPurchaseInfoPo prodPurchaseInfoPo:prodPurchaseInfoPos){
            //优先返回主供应商的价格
            if (prodPurchaseInfoPo.getSupplierType()==1){
                if (null==prodPurchaseInfoPo.getPurchasePrice()||prodPurchaseInfoPo.getPurchasePrice().doubleValue()==0.00){
                    return null;
                }
                return prodPurchaseInfoPo.getPurchasePrice();
            }
        }
        //没有主供应商，则随机取第一个供应商的价格
        ProdPurchaseInfoPo prodPurchaseInfoPo=prodPurchaseInfoPos.get(0);
        if (null==prodPurchaseInfoPo.getPurchasePrice()||prodPurchaseInfoPo.getPurchasePrice().doubleValue()==0.00){
            return null;
        }
        return prodPurchaseInfoPo.getPurchasePrice();
    }

    @Override
    public ProdPurchasePriceAuditPo getProdPurchasePriceAuditDtoById(Long id) {
       return prodPurchaseInfoDao.getProdPurchasePriceAuditDtoById(id);

    }


    /**
     * 采购价格记录
     *
     * @param prodPurchaseInfoPo
     * @param isNew              是否是新增采购价格
     */

    private void addProdPurchaseLog(ProdPurchaseInfoPo prodPurchaseInfoPo, boolean isNew) {
        ProdPurchaseInfoLogPo logPo = new ProdPurchaseInfoLogPo();
        logPo.setOperateDate(new Date());
        logPo.setPriceId(String.valueOf(prodPurchaseInfoPo.getId()));
        logPo.setPurchasePrice(prodPurchaseInfoPo.getNewestPrice());
        if (isNew) {
            logPo.setOperate("新增商品采购关系");
            logPo.setOperateUserId(prodPurchaseInfoPo.getCreateUserId());
        } else {
            logPo.setOperate("更新商品采购关系");
            logPo.setOperateUserId(prodPurchaseInfoPo.getModifyUserId());
        }
        prodPurchaseInfoLogDao.insertSelective(logPo);
    }
}
