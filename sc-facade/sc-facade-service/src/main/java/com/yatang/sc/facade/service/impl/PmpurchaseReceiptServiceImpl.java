package com.yatang.sc.facade.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.facade.dao.PmPurchaseOrderDao;
import com.yatang.sc.facade.dao.PmPurchaseOrderItemDao;
import com.yatang.sc.facade.dao.PmPurchaseReceiptDao;
import com.yatang.sc.facade.dao.PmPurchaseReceiptItemsDao;
import com.yatang.sc.facade.dao.SupplierSettledDao;
import com.yatang.sc.facade.dao.WarehouseLogicInfoDao;
import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.SupplierSettledPo;
import com.yatang.sc.facade.domain.WarehouseLogicInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptDetailPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;

/**
 * 采购收货单serveice
 *
 * @author: yinyuxin
 * @version: 1.0, 2017年7月29日
 */
@Service
@Transactional
public class PmpurchaseReceiptServiceImpl implements PmPurchaseReceiptService {
    private static final Logger log = LoggerFactory.getLogger(PmpurchaseReceiptServiceImpl.class);
    @Autowired
    private PmPurchaseReceiptDao pmPurchaseReceiptDao;
    @Autowired
    private PmPurchaseReceiptItemsDao pmPurchaseReceiptItemsDao;
    @Autowired
    private PmPurchaseOrderService pmPurchaseOrderService;
    @Autowired
    private PmPurchaseOrderDao pmPurchaseOrderDao;
    @Autowired
    private PmPurchaseOrderItemDao pmPurchaseOrderItemDao;
    @Autowired
    private SupplierSettledDao supplierSettledDao;
    @Autowired
    private WarehouseLogicInfoDao warehouseLogicInfoDao;
    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Override
    public PageInfo<PmPurchaseReceiptPo> queryReceipts(PmPurchaseReceiptParamPo paramPo) {
        if (!StringUtils.isBlank(paramPo.getBranchCompanyId())) {
            List<String> adrTypeCodes = getWarehouseAdrTypeCodes(paramPo.getBranchCompanyId());
            if (adrTypeCodes.size() > 0) {
                paramPo.setAdrTypeCodes(adrTypeCodes);
            }
        }

        PageHelper.startPage(paramPo.getPageNum(), paramPo.getPageSize());
        List<PmPurchaseReceiptPo> list = pmPurchaseReceiptDao.queryListByParam(paramPo);
        PageInfo<PmPurchaseReceiptPo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据子公司ID查询逻辑仓库，查询统采订单时使用
     *
     * @param companyId
     * @return
     */
    private List<String> getWarehouseAdrTypeCodes(String companyId) {
        List<WarehouseLogicInfoPo> warehouseLogicInfoPos = warehouseLogicInfoDao.getWarehouseByBranchCompanyId(companyId);
        List<String> adrTypeCodes = new ArrayList<>();
        if (warehouseLogicInfoPos.size() > 0) {
            for (WarehouseLogicInfoPo warehouseLogicInfoPo : warehouseLogicInfoPos) {
                String warehouseCode = warehouseLogicInfoPo.getWarehouseCode();
                adrTypeCodes.add(warehouseCode);
            }
        }

        return adrTypeCodes;
    }

    @Override
    public PmPurchaseReceiptPo queryReceiptById(Long id) {
        return pmPurchaseReceiptDao.selectByPrimaryKey(id);
    }

    @Override
    public PmPurchaseReceiptPo insertReceiptAndItmsByOrderCode(String orderCode) {
        // 2017.10.09 补充分布式锁
        String lockPath = "/insertReceiptAndItmsByOrderCode" + orderCode;// 根据采购单单号枷锁
        // //
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        try {
            rLock.lock();
            // 2017.10.09 重复记录校验
            PmPurchaseReceiptParamPo pmPurchaseReceiptParamPo = new PmPurchaseReceiptParamPo();
            pmPurchaseReceiptParamPo.setPurchaseOrderNo(orderCode);
            List<PmPurchaseReceiptPo> pmPurchaseReceiptPos = pmPurchaseReceiptDao
                    .queryListByParam(pmPurchaseReceiptParamPo);
            if (pmPurchaseReceiptPos != null && pmPurchaseReceiptPos.size() > 0) {
                log.info("采购单号:{}已存在收货单，新增收货单失败", orderCode);
                return null;
            }

            // 1：通过采购单单号获取采购单信息
            PmPurchaseOrderInfoPo pmPurchaseOrderInfoPo = pmPurchaseOrderService
                    .getPurchaseOrderInfoByOrderNo(orderCode);
            if (pmPurchaseOrderInfoPo != null) {
                // 采购单号对应的采购单唯一
                // 2：新增收货单
                PmPurchaseReceiptPo pmPurchaseReceiptPo = new PmPurchaseReceiptPo();
                pmPurchaseReceiptPo.setBranchCompanyId(pmPurchaseOrderInfoPo.getBranchCompanyId());
                pmPurchaseReceiptPo.setCreateTime(new Date());
                pmPurchaseReceiptPo.setCreateUserId(pmPurchaseOrderInfoPo.getCreateUserId());
                // TODO 预计到货日期,系统自动默认，值为采购单预计送货日期.注：预留与供应商系统对接预发货通知
                pmPurchaseReceiptPo.setEstimatedReceivedDate(pmPurchaseOrderInfoPo.getEstimatedDeliveryDate());
                pmPurchaseReceiptPo.setModifyTime(new Date());
                pmPurchaseReceiptPo.setModifyUserId(pmPurchaseOrderInfoPo.getCreateUserId());
                pmPurchaseReceiptPo.setPurchaseOrderId(pmPurchaseOrderInfoPo.getId() + "");
                // 新建时默认为待下发状态
                pmPurchaseReceiptPo.setStatus(0);
                pmPurchaseReceiptPo.setSaleOrderId(pmPurchaseOrderInfoPo.getSaleOrderId());
                pmPurchaseReceiptDao.insertSelective(pmPurchaseReceiptPo);

                // 3:查询采购单清单
                List<PmPurchaseOrderItemPo> pmPurchaseOrderItemPos = pmPurchaseOrderInfoPo.getPmPurchaseOrderItems();
                if (pmPurchaseOrderItemPos != null && pmPurchaseOrderItemPos.size() > 0) {
                    for (PmPurchaseOrderItemPo pmPurchaseOrderItemPo : pmPurchaseOrderItemPos) {
                        PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo = new PmPurchaseReceiptItemsPo();
                        pmPurchaseReceiptItemsPo.setCreateTime(new Date());
                        pmPurchaseReceiptItemsPo.setCreateUserId(pmPurchaseOrderInfoPo.getCreateUserId());
                        // TODO 供应商出库数量,默认等于采购数量,预留与供应商系统对接预发货通知
                        pmPurchaseReceiptItemsPo.setDeliveryNumber(pmPurchaseOrderItemPo.getPurchaseNumber());
                        pmPurchaseReceiptItemsPo.setModifyTime(new Date());
                        pmPurchaseReceiptItemsPo.setModifyUserId(pmPurchaseOrderItemPo.getCreateUserId());
                        pmPurchaseReceiptItemsPo.setPurchaseOrderId(pmPurchaseOrderInfoPo.getId() + "");
                        pmPurchaseReceiptItemsPo.setPurchaseOrderItemsId(pmPurchaseOrderItemPo.getId() + "");
                        pmPurchaseReceiptItemsPo.setPurchaseReceiptId(pmPurchaseReceiptPo.getId() + "");
                        pmPurchaseReceiptItemsDao.insertSelective(pmPurchaseReceiptItemsPo);
                    }
                }
                return pmPurchaseReceiptPo;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }
        return null;
    }


    @Override
    public Boolean insertReceipt(PmPurchaseReceiptPo pmPurchaseReceiptPo) {
        return pmPurchaseReceiptDao.insertSelective(pmPurchaseReceiptPo) > 0;
    }


    @Override
    public Boolean updateReceipt(PmPurchaseReceiptPo pmPurchaseReceiptPo) {
        return pmPurchaseReceiptDao.updateByPrimaryKeySelective(pmPurchaseReceiptPo) > 0;
    }


    @Override
    public List<PmPurchaseReceiptItemsPo> queryReceiptItemsByReceiptId(String purchaseReceiptId) {
        return pmPurchaseReceiptItemsDao.queryItemsByReceiptId(purchaseReceiptId);
    }


    @Override
    public Boolean insertReceiptItems(PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo) {

        return pmPurchaseReceiptItemsDao.insertSelective(pmPurchaseReceiptItemsPo) > 0;
    }


    @Override
    public Boolean updateReceiptItems(PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo) {
        return pmPurchaseReceiptItemsDao.updateByPrimaryKeySelective(pmPurchaseReceiptItemsPo) > 0;
    }


    @Override
    public boolean updateByPurchaseReceiptNoSelective(PmPurchaseReceiptPo purchaseReceiptPo) {
        purchaseReceiptPo.setModifyTime(new Date());
        return pmPurchaseReceiptDao.updateByPurchaseReceiptNoSelective(purchaseReceiptPo) > 0;
    }


    @Override
    public boolean updatePurchaseYSH(Long purchaseOrderId, String purchaseReceiptNo, Date operateTime,
                                     List<Map<String, Long>> list, List<SupplierSettledPo> supplierSettledPoList) {
        // 添加供应商结算数据插入功能
        for (SupplierSettledPo supplierSettledPo : supplierSettledPoList) {
            supplierSettledDao.insert(supplierSettledPo);
        }

        // a.更新采购入库单信息
        // 收货头信息：
        // 收货日期：接口返回的“operateTime”
        // 收货单状态：已收货 (YSH):”
        // 收货行信息：
        // 收货数：接口返回明细的“acualQty”
        // b.关闭采购单
        // 将采购单状态改为“已关闭”
        Date now = new Date();

        PmPurchaseReceiptPo pmPurchaseReceiptPo = new PmPurchaseReceiptPo();
        pmPurchaseReceiptPo.setPurchaseReceiptNo(purchaseReceiptNo);
        pmPurchaseReceiptPo.setReceivedTime(operateTime);
        pmPurchaseReceiptPo.setStatus(2);
        pmPurchaseReceiptPo.setModifyTime(now);
        pmPurchaseReceiptDao.updateByPurchaseReceiptNoSelective(pmPurchaseReceiptPo);
        for (Map<String, Long> map : list) {
            Long orderLineNo = MapUtils.getLong(map, "orderLineNo");
            int receivedNumber = MapUtils.getIntValue(map, "ReceivedNum");
            PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo = new PmPurchaseReceiptItemsPo();
            pmPurchaseReceiptItemsPo.setPurchaseOrderItemsId(String.valueOf(orderLineNo));
            pmPurchaseReceiptItemsPo.setReceivedNumber(receivedNumber);
            pmPurchaseReceiptItemsPo.setModifyTime(now);
            pmPurchaseReceiptItemsDao.updateByPurchaseOrderItemsId(pmPurchaseReceiptItemsPo);
            // PmPurchaseOrderItemPo pmPurchaseOrderItemPo =
            // pmPurchaseOrderItemDao
            // .selectPmPurchaseOrderItemByPurchaseReceiptItemId(map.get("orderLineNo"));
            PmPurchaseOrderItemPo pmPurchaseOrderItemPo = new PmPurchaseOrderItemPo();
            pmPurchaseOrderItemPo.setReceivedNumber(receivedNumber);
            pmPurchaseOrderItemPo.setModifyTime(now);
            pmPurchaseOrderItemDao.updateByPrimaryKeySelective(pmPurchaseOrderItemPo);
        }
        PmPurchaseOrderPo pmPurchaseOrderPo = new PmPurchaseOrderPo();
        pmPurchaseOrderPo.setId(purchaseOrderId);
        pmPurchaseOrderPo.setStatus(4);
        pmPurchaseOrderPo.setModifyTime(now);
        pmPurchaseOrderDao.updateByPrimaryKeySelective(pmPurchaseOrderPo);
        return true;
    }


    @Override
    public boolean updatePurchaseYQX(Long id, String purchaseReceiptNo, List<Map<String, Long>> list) {
        // 更新收货单状态为 “已取消(YQX)”,更新采购单状态为“已关闭”, 取消数量=采购数量-已收货数量，取消原因填写超期关闭
        // 状态:0:待下发，1：已下发，2:已收货，3:已取消，4：异常
        PmPurchaseReceiptPo pmPurchaseReceiptPo = new PmPurchaseReceiptPo();
        pmPurchaseReceiptPo.setPurchaseReceiptNo(purchaseReceiptNo);
        pmPurchaseReceiptPo.setStatus(3);
        pmPurchaseReceiptPo.setModifyTime(new Date());
        pmPurchaseReceiptDao.updateByPurchaseReceiptNoSelective(pmPurchaseReceiptPo);
        // 状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭
        PmPurchaseOrderPo pmPurchaseOrderPo = new PmPurchaseOrderPo();
        pmPurchaseOrderPo.setId(id);
        pmPurchaseOrderPo.setStatus(4);
        pmPurchaseOrderPo.setModifyTime(new Date());
        pmPurchaseOrderPo.setFailedReason("超期关闭");
        pmPurchaseOrderDao.updateByPrimaryKeySelective(pmPurchaseOrderPo);

        // for (Map<String, Long> map : list) {
        // PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo = new
        // PmPurchaseReceiptItemsPo();
        // pmPurchaseReceiptItemsPo.setId(map.get("orderLineNo"));
        // pmPurchaseReceiptItemsPo.setReceivedNumber(map.get("ReceivedNum").intValue());
        // pmPurchaseReceiptItemsPo.setModifyTime(new Date());
        // pmPurchaseReceiptItemsDao.updateByPrimaryKeySelective(pmPurchaseReceiptItemsPo);
        // }
        return true;
    }


    @Override
    public PmPurchaseReceiptPo queryPurchaseReceiptByPurchaseOrderId(Long purchaseOrderId) {
        return pmPurchaseReceiptDao.selectByPurchaseOrderId(purchaseOrderId);
    }

    @Override
    public List<PmPurchaseReceiptPo> exportReceipts(PmPurchaseReceiptParamPo paramPo) {
        return pmPurchaseReceiptDao.queryListByParam(paramPo);
    }

    @Override
    public long getAllPurchaseReceiptCount(Integer status) {
        return pmPurchaseReceiptDao.getAllPurchaseReceiptCount(status);
    }

    @Override
    public PmPurchaseReceiptPo selectByPurchaseReceiptNo(String purchaseReceiptNo) {
        return pmPurchaseReceiptDao.selectByPurchaseReceiptNo(purchaseReceiptNo);
    }

    @Override
    public List<PmPurchaseReceiptPo> queryPurchaseReceiptListByPurchaseOrderId(String purchaseOrderId, Boolean containAsn) {
        return pmPurchaseReceiptDao.queryPurchaseReceiptListByPurchaseOrderId(purchaseOrderId, containAsn);
    }


    @Override
    public Boolean updatePurchaseReceipt(PmPurchaseReceiptDetailPo receiptDetailPo) {

        PmPurchaseReceiptPo pmPurchaseReceipt = receiptDetailPo.getPmPurchaseReceipt();
        pmPurchaseReceipt.setModifyTime(new Date());
        boolean mainUpdate = pmPurchaseReceiptDao.updateByPrimaryKeySelective(pmPurchaseReceipt) == 1;
        if (mainUpdate) {
            List<PmPurchaseReceiptItemsPo> receiptProducts = receiptDetailPo.getReceiptPruducts();
            for (PmPurchaseReceiptItemsPo itemsPo : receiptProducts) {
                //主要更新收货数量
                pmPurchaseReceiptItemsDao.updateByPrimaryKeySelective(itemsPo);
            }

            return true;
        }
        return false;
    }

    @Override
    public Boolean splitNewPurchaseReceipt(PmPurchaseReceiptDetailPo receiptDetailPo, List<PmPurchaseReceiptItemsPo> remainItemPoList) {
        //1 生成新的收货单id
        PmPurchaseReceiptPo pmPurchaseReceiptPo = receiptDetailPo.getPmPurchaseReceipt();
        boolean insertSuccess = pmPurchaseReceiptDao.insertSelective(pmPurchaseReceiptPo) == 1;
        if (insertSuccess) {
            //2 更新未录入的收货单子项的purchase_receipt_id
            List<PmPurchaseReceiptItemsPo> receiptProductPos = receiptDetailPo.getReceiptPruducts();
            if (receiptProductPos != null && receiptProductPos.size() > 0) {
                for (PmPurchaseReceiptItemsPo itemsPo : receiptProductPos) {
                    //更新收货数量和新的收货单id
                    PmPurchaseReceiptItemsPo receiptItemPo = new PmPurchaseReceiptItemsPo();
                    receiptItemPo.setPurchaseReceiptId(String.valueOf(pmPurchaseReceiptPo.getId()));
                    receiptItemPo.setId(itemsPo.getId());
                    pmPurchaseReceiptItemsDao.updateByPrimaryKeySelective(receiptItemPo);
                }
            }
            //商品数量部分录入
            if (remainItemPoList != null && remainItemPoList.size() > 0) {
                for (PmPurchaseReceiptItemsPo itemsPo : remainItemPoList) {
                    itemsPo.setPurchaseReceiptId(String.valueOf(pmPurchaseReceiptPo.getId()));
                    itemsPo.setId(null);
                    pmPurchaseReceiptItemsDao.insertSelective(itemsPo);
                }

            }
            return true;
        }
        return false;
    }


    @Override
    public void updateReceiptSH(PmPurchaseReceiptPo pmPurchaseReceiptPo,
                                List<PmPurchaseReceiptItemsPo> pmPurchaseReceiptItemsPoList, List<SupplierSettledPo> supplierSettledPoList) {
        pmPurchaseReceiptDao.updateByPrimaryKey(pmPurchaseReceiptPo);
        for (PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo : pmPurchaseReceiptItemsPoList) {
            pmPurchaseReceiptItemsDao.updateByPrimaryKey(pmPurchaseReceiptItemsPo);

            PmPurchaseOrderItemPo pmPurchaseOrderItemPo = new PmPurchaseOrderItemPo();
            pmPurchaseOrderItemPo.setId(Long.valueOf(pmPurchaseReceiptItemsPo.getPurchaseOrderItemsId()));
            pmPurchaseOrderItemPo.setReceivedNumber(pmPurchaseReceiptItemsPo.getReceivedNumber());
            pmPurchaseOrderItemPo.setModifyTime(pmPurchaseReceiptItemsPo.getModifyTime());
            pmPurchaseOrderItemDao.updateByPrimaryKeySelective(pmPurchaseOrderItemPo);
        }
        // 添加供应商结算数据插入功能
        for (SupplierSettledPo supplierSettledPo : supplierSettledPoList) {
            supplierSettledDao.insert(supplierSettledPo);
        }
    }

    @Override
    public PmPurchaseReceiptItemsPo queryReceiptItemsByPurchaseOrderItemsId(String purchaseOrderItemsId) {
        return pmPurchaseReceiptItemsDao.selectByPurchaseOrderItemsId(purchaseOrderItemsId);
    }


    @Override
    public List<PmPurchaseReceiptPo> queryPurchaseReceiptListByOrderIdAndStatus(String purchaseOrderId) {
        return pmPurchaseReceiptDao.queryPurchaseReceiptListByOrderIdAndStatus(purchaseOrderId);
    }
}
