package com.yatang.sc.facade.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.SupplierSettledPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptDetailPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptItemsPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;

/**
 * 采购收货单service
 *
 * @author: yinyuxin
 * @version: 1.0, 2017年7月29日
 */
public interface PmPurchaseReceiptService {

    /**
     * 查询收货单列表
     *
     * @param paramPo
     * @return
     */
    public PageInfo<PmPurchaseReceiptPo> queryReceipts(PmPurchaseReceiptParamPo paramPo);


    /**
     * 根据id查询收货单
     *
     * @param id
     * @return
     */
    public PmPurchaseReceiptPo queryReceiptById(Long id);


    /**
     * 根据采购单单号生成收货单及收货清单
     *
     * @param orderCode
     * @return
     */
    public PmPurchaseReceiptPo insertReceiptAndItmsByOrderCode(String orderCode);


    /**
     * 新增采购收货单
     *
     * @param pmPurchaseReceiptPo
     * @return
     */
    public Boolean insertReceipt(PmPurchaseReceiptPo pmPurchaseReceiptPo);


    /**
     * 修改采购收货单
     *
     * @param pmPurchaseReceiptPo
     * @return
     */
    public Boolean updateReceipt(PmPurchaseReceiptPo pmPurchaseReceiptPo);


    /**
     * 根据收货单id查询收货单商品表
     *
     * @param purchaseReceiptId
     * @return
     */
    public List<PmPurchaseReceiptItemsPo> queryReceiptItemsByReceiptId(String purchaseReceiptId);


    /**
     * 新增采购收货单商品清单
     *
     * @param pmPurchaseReceiptItemsPo
     * @return
     */
    public Boolean insertReceiptItems(PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo);


    /**
     * 修改采购收货单商品清单
     *
     * @param pmPurchaseReceiptItemsPo
     * @return
     */
    public Boolean updateReceiptItems(PmPurchaseReceiptItemsPo pmPurchaseReceiptItemsPo);


    /**
     * <method description>
     *
     * @param purchaseReceiptPo
     * @return
     * @author zhoubaiyun
     */
    public boolean updateByPurchaseReceiptNoSelective(PmPurchaseReceiptPo purchaseReceiptPo);


    /**
     * <method description>
     *
     * @param purchaseOrderId   采购订单ID
     * @param purchaseReceiptNo 采购收货单号
     * @param operateTime       操作时间
     * @param list              子表更新数据
     * @return
     * @author zhoubaiyun
     */
    public boolean updatePurchaseYSH(Long purchaseOrderId, String purchaseReceiptNo, Date operateTime,
                                     List<Map<String, Long>> list, List<SupplierSettledPo> supplierSettledPoList);


    /**
     * <method description>
     *
     * @param id                采购单ID
     * @param purchaseReceiptNo 收货单单号
     * @param list
     * @return
     * @author zhoubaiyun
     */
    public boolean updatePurchaseYQX(Long id, String purchaseReceiptNo, List<Map<String, Long>> list);


    /**
     * <method description>根据采购单ID查询收货单
     *
     * @param purchaseOrderId
     * @return
     * @author zhoubaiyun
     */
    public PmPurchaseReceiptPo queryPurchaseReceiptByPurchaseOrderId(Long purchaseOrderId);

    /**
     * 导出收货单列表
     *
     * @param paramPo
     * @return
     */
    public List<PmPurchaseReceiptPo> exportReceipts(PmPurchaseReceiptParamPo paramPo);

    /**
     * @param
     * @Description: 根据状态查询收货单的记录数
     * @author tankejia
     * @date 2017/12/27- 15:07
     */
    long getAllPurchaseReceiptCount(Integer status);

    /**
     * @param purchaseReceiptNo
     * @Description: 根据收货单号查询收货单
     * @author tankejia
     * @date 2017/12/28- 12:56
     */
    PmPurchaseReceiptPo selectByPurchaseReceiptNo(String purchaseReceiptNo);


    /**
     * 根据采购单id查询录入过asn的收货单list
     *
     * @param purchaseOrderId 采购单id
     * @param containAsn      true:必须有asn,false:(不强制要求需要asn)
     * @return List<PmPurchaseReceiptPo>
     * @author yangshuang
     */
    List<PmPurchaseReceiptPo> queryPurchaseReceiptListByPurchaseOrderId(String purchaseOrderId, Boolean containAsn);


    /**
     * 更新原收货单
     *
     * @param receiptDetailPo
     * @return
     * @author yangshuang
     */
    Boolean updatePurchaseReceipt(PmPurchaseReceiptDetailPo receiptDetailPo);


    /**
     * 拆分新的收货单
     *
     * @param receiptDetailPo
     * @param remainItemPoList 数量没有录入完的商品
     * @return
     * @author yangshuang
     */
    Boolean splitNewPurchaseReceipt(PmPurchaseReceiptDetailPo receiptDetailPo, List<PmPurchaseReceiptItemsPo> remainItemPoList);


    /**
     * <method description>更新直送收货单
     *
     * @param pmPurchaseReceiptPo
     * @param pmPurchaseReceiptItemsPoList
     * @return
     * @author zhoubaiyun
     */
    public void updateReceiptSH(PmPurchaseReceiptPo pmPurchaseReceiptPo,
                                List<PmPurchaseReceiptItemsPo> pmPurchaseReceiptItemsPoList, List<SupplierSettledPo> supplierSettledPoList);


    public PmPurchaseReceiptItemsPo queryReceiptItemsByPurchaseOrderItemsId(String purchaseOrderItemsId);


    /**
     * 根据采购单id查询未录入过asn的收货单list
     * asn为空和status=0
     * @param purchaseOrderId 采购单id
     * @return List<PmPurchaseReceiptPo>
     * @author kangdong
     */
    List<PmPurchaseReceiptPo> queryPurchaseReceiptListByOrderIdAndStatus(String purchaseOrderId);



}
