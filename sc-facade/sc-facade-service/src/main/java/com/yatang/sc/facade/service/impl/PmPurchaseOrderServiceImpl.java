package com.yatang.sc.facade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.PmPurchaseOrderDao;
import com.yatang.sc.facade.dao.PmPurchaseOrderItemDao;
import com.yatang.sc.facade.dao.PmPurchaseReceiptDao;
import com.yatang.sc.facade.dao.ProdPurchaseInfoDao;
import com.yatang.sc.facade.dao.WarehouseLogicInfoDao;
import com.yatang.sc.facade.domain.PmPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.PmPurchaseQueryParamPo;
import com.yatang.sc.facade.domain.PmSupplierPurchaseOrderItemPo;
import com.yatang.sc.facade.domain.PmSupplierPurchaseOrderQueryParamPo;
import com.yatang.sc.facade.domain.WarehouseLogicInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderExtPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderItemQueryParamPo;
import com.yatang.sc.facade.enums.CommonEnum;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.PmPurchaseOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述: 商品采购单service实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:32
 * @版本: v1.0
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PmPurchaseOrderServiceImpl implements PmPurchaseOrderService {
	private final PmPurchaseOrderDao		pmPurchaseOrderDao;
	private final PmPurchaseOrderItemDao	pmPurchaseOrderItemDao;
    private final WarehouseLogicInfoDao warehouseLogicInfoDao;

	private final ProdPurchaseInfoDao		prodPurchaseInfoDao;	// 商品采购关系

	private final PmPurchaseReceiptDao pmPurchaseReceiptDao;
    public final static String TimeOut_Close ="超期关闭";
    public final static String Normal_Close="正常关闭";

    public final static Integer delivery_receipt_max_asn_count=2;//最大录入asn

    public final static Integer delivery_receipt_status=2;//收货状态


	@Override
	public Long addPmPurchaseOrder(PmPurchaseOrderExtPo pmPurchaseOrderExtPo) {

		// 2017/08/01
		if (pmPurchaseOrderExtPo.getPmPurchaseOrderItems().size() > 0) {// 订单子项为空
			//// 金额计算
			pmPurchaseOrderExtPo = calculateAmount(pmPurchaseOrderExtPo);
		}

		// 1.插入生成主表
		PmPurchaseOrderPo pmPurchaseOrder = pmPurchaseOrderExtPo.getPmPurchaseOrder();
		Date createTime = new Date();
		pmPurchaseOrder.setCreateTime(createTime);
		if (pmPurchaseOrder.getSaleOrderId()!=null){//直送采购单
			pmPurchaseOrder.setStatus(CommonEnum.SUPPLIER_INFO_TWO.getCode());//设置默认为已审核
//			pmPurchaseOrder.setCreateUserId(PublicEnum.NAME.getCodeStr());//设置创建人为系统创建
			pmPurchaseOrder.setSpAcceptStatus(0);//供应商接单状态:0未接单;1:已接单
			pmPurchaseOrder.setBusinessMode(2);//设置直送订单默认为寄售
		}
		String createUserId = pmPurchaseOrder.getCreateUserId();// 创建者id

		List<PmPurchaseOrderItemPo> pmPurchaseOrderItems = pmPurchaseOrderExtPo.getPmPurchaseOrderItems();
		// 插入
		boolean mainInsert = pmPurchaseOrderDao.insert(pmPurchaseOrder) >= 1;
		// 2.插入订单子订单
		Long mainId = pmPurchaseOrder.getId();// 获取主表id //item设置订单主键
		for (PmPurchaseOrderItemPo pmPurchaseOrderItem : pmPurchaseOrderItems) {
			pmPurchaseOrderItem.setPurchaseOrderId(mainId.toString());
			pmPurchaseOrderItem.setCreateTime(createTime);// 添加创建时间
			pmPurchaseOrderItem.setCreateUserId(createUserId);// 添加创建者
		}
		boolean itemInsert = pmPurchaseOrderItemDao.batchInsertPmPurchaseOrderItem(pmPurchaseOrderItems) >= 1;
		if (mainInsert && itemInsert) {// 插入成功返回主键
			return mainId;
		}
		return 0L;
	}



	@Override
	public boolean batchDeletePmPurchaseOrderByIds(List<String> pmPurchaseOrderIdList) {
		// 1.批量删除主表
		boolean mainBatchDeleteSuccess = pmPurchaseOrderDao.batchDeletePmPurchaseOrderByIds(pmPurchaseOrderIdList) >= 1;
		if (!mainBatchDeleteSuccess) {// 删除商品失败
			return mainBatchDeleteSuccess;
		}

		// 2.删除订单子项
		boolean itemsBatchDelete = pmPurchaseOrderItemDao
				.batchDeletePmPurchaseOrderItemsByIds(pmPurchaseOrderIdList) >= 1;
		return mainBatchDeleteSuccess && itemsBatchDelete;
	}



	/**
	 * 更新商品采购订单
	 *
	 * @param pmPurchaseOrderExtPo
	 * @return
	 */
	@Override
	public boolean updatePmPurchaseOrder(PmPurchaseOrderExtPo pmPurchaseOrderExtPo) {

		log.info("service--updatePmPurchaseOrder>>更新订单信息--pmPurchaseOrderExtPo:{}", pmPurchaseOrderExtPo);

		if (pmPurchaseOrderExtPo.getPmPurchaseOrderItems().size() > 0) {// 订单子项为空
			// 全部删除
			pmPurchaseOrderExtPo = calculateAmount(pmPurchaseOrderExtPo);
		}

		PmPurchaseOrderPo pmPurchaseOrder = pmPurchaseOrderExtPo.getPmPurchaseOrder();// main
		// 主表
		Long orderId = pmPurchaseOrder.getId();// 订单id
		String modifyUserId = pmPurchaseOrder.getModifyUserId();// 修改者id
		List<PmPurchaseOrderItemPo> newPmPurchaseOrderItems = new ArrayList<>();// 新增的
		List<PmPurchaseOrderItemPo> updatePmPurchaseOrderItems = new ArrayList<>();// 待更新的
		// 查出所有该订单的子项
		List<Long> updateIds = new ArrayList<>();// 待更新的id
		List<PmPurchaseOrderItemPo> pmPurchaseOrderItems = pmPurchaseOrderExtPo.getPmPurchaseOrderItems();// orderItem
		// 订单子项目
		for (PmPurchaseOrderItemPo pmPurchaseOrderItem : pmPurchaseOrderItems) {// todo如何判定
			Long orderItemId = pmPurchaseOrderItem.getId();
			if (null == orderItemId) {// 放入新增栏目
				newPmPurchaseOrderItems.add(pmPurchaseOrderItem);
			} else {// 放入更新栏目
				updatePmPurchaseOrderItems.add(pmPurchaseOrderItem);
				updateIds.add(orderItemId);
			}
		}
		// 先删除后更新
		// 根据主键id查询子单的 id集合
		List<Long> oldItemsIds = pmPurchaseOrderItemDao.getItemsSetByOrderId(orderId);
		oldItemsIds.removeAll(updateIds);
		// 1.批量删除
		if (oldItemsIds.size() > 0) {
			log.info("需要批量删除的>>oldItemsIds:{}", oldItemsIds);
			pmPurchaseOrderItemDao.batchDeletePmPurchaseOrderItems(oldItemsIds);
		}
		// 1.批量插入新增的
		Date createTime = new Date();
		if (newPmPurchaseOrderItems.size() > 0) {
			for (PmPurchaseOrderItemPo newPmPurchaseOrderItem : newPmPurchaseOrderItems) {
				newPmPurchaseOrderItem.setPurchaseOrderId(orderId.toString());// 设置订单id
				newPmPurchaseOrderItem.setCreateUserId(modifyUserId);
				newPmPurchaseOrderItem.setCreateTime(createTime);
			}
			log.info("需要新增的订单>>newPmPurchaseOrderItems:{}", newPmPurchaseOrderItems);
			pmPurchaseOrderItemDao.batchInsertPmPurchaseOrderItem(newPmPurchaseOrderItems);
		}
		// 2.批量更新
		boolean batchUpdate = true;
		if (updatePmPurchaseOrderItems.size() > 0) {
			for (PmPurchaseOrderItemPo updatePmPurchaseOrderItem : updatePmPurchaseOrderItems) {
				updatePmPurchaseOrderItem.setModifyTime(createTime);
				updatePmPurchaseOrderItem.setModifyUserId(modifyUserId);
			}
			log.info("需要新增的订单>>updatePmPurchaseOrderItems:{}", updatePmPurchaseOrderItems);
			batchUpdate = pmPurchaseOrderItemDao.batchUpdatePmPurchaseOrderItems(updatePmPurchaseOrderItems) >= 1;
		}
		// 主表更新
		pmPurchaseOrder.setModifyTime(createTime);
        boolean mainUpdate = pmPurchaseOrderDao.updateByPrimaryKeySelective(pmPurchaseOrder) >= 1;
		return mainUpdate && batchUpdate;
	}



	/**
	 * 金额计算(含税金额,不含税金额,税金)
	 *
	 * @param pmPurchaseOrderExtPo
	 * @return
	 */
	private PmPurchaseOrderExtPo calculateAmount(PmPurchaseOrderExtPo pmPurchaseOrderExtPo) {

		log.info("service--calculateAmount--金额计算>>pmPurchaseOrderExtPo:{}", pmPurchaseOrderExtPo);
		PmPurchaseOrderPo pmPurchaseOrder = pmPurchaseOrderExtPo.getPmPurchaseOrder();// 主表
		List<PmPurchaseOrderItemPo> pmPurchaseOrderItems = pmPurchaseOrderExtPo.getPmPurchaseOrderItems();// orderItem子项

		double orderTotalAmount = 0;// 订单的总得含税金额 main
		double orderTotalWithoutTaxAmount = 0;// 订单不含税采购金额 main
		double orderTaxAmount = 0;// 订单总税额 main
		for (PmPurchaseOrderItemPo pmPurchaseOrderItem : pmPurchaseOrderItems) {// 设置外键和时间和创建者
			// 处理金额相关问题
			/**
			 *
			 * BigDecimal mData = new
			 * BigDecimal(String.valueOf(1.591)).setScale(2,
			 * BigDecimal.ROUND_HALF_UP); 四舍五入
			 *
			 */
			// 1.计算单项金额(含税)
			Integer purchaseNumber = pmPurchaseOrderItem.getPurchaseNumber() == null ? 0
					: pmPurchaseOrderItem.getPurchaseNumber();// 采购数量
			double purchasePrice = pmPurchaseOrderItem.getPurchasePrice() == null ? 0
					: pmPurchaseOrderItem.getPurchasePrice().doubleValue();// 采购价格
			double itemTotalAccount = purchaseNumber * purchasePrice;// 当前单项税金
			BigDecimal totalAmount = new BigDecimal(String.valueOf(itemTotalAccount)).setScale(2,
					BigDecimal.ROUND_HALF_UP);// 四舍五入处理
			pmPurchaseOrderItem.setTotalAmount(totalAmount);// 设置当前的订单
			orderTotalAmount += totalAmount.doubleValue();// 订单的数量累加 main

			// 2.计算单项不含采购价税金额
			double inputTaxRate = pmPurchaseOrderItem.getInputTaxRate() == null ? 0
					: pmPurchaseOrderItem.getInputTaxRate().doubleValue() * 0.01;// 进项税
			// 例如：17%
			BigDecimal totalWithoutTaxAmount = totalAmount.divide(new BigDecimal(1 + inputTaxRate), 2,
					BigDecimal.ROUND_HALF_UP);// 四舍五入
			pmPurchaseOrderItem.setTotalWithoutTaxAmount(totalWithoutTaxAmount);
			orderTotalWithoutTaxAmount += totalWithoutTaxAmount.doubleValue();// main

			// 3. 单项税金
			BigDecimal taxAmount = totalAmount.subtract(totalWithoutTaxAmount);
			pmPurchaseOrderItem.setTaxAmount(taxAmount);
			orderTaxAmount += taxAmount.doubleValue();// main
		}

		pmPurchaseOrder.setTotalAmount(new BigDecimal(orderTotalAmount));// 订单的总得含税金额
		// main
		pmPurchaseOrder.setTotalWithoutTaxAmount(new BigDecimal(orderTotalWithoutTaxAmount));//// 订单不含税采购金额
		//// main
		pmPurchaseOrder.setTotalTaxAmount(new BigDecimal(orderTaxAmount));//// 订单总税额
		//// main
		PmPurchaseOrderExtPo purchaseOrder = new PmPurchaseOrderExtPo();// 返回数据
		purchaseOrder.setPmPurchaseOrder(pmPurchaseOrder);
		purchaseOrder.setPmPurchaseOrderItems(pmPurchaseOrderItems);
		return purchaseOrder;
	}

    /**
     * 根据传入参数查询采购单打印管理列表详细信息
     *
     * @param convert
     * @return
     */
    @Override
    public PageInfo<PmPurchaseOrderInfoPo> queryPurchaseOrderListInfo(PmPurchaseQueryParamPo convert) {
		if(!StringUtils.isBlank(convert.getBranchCompanyId())){
			List<String> adrTypeCodes = getWarehouseAdrTypeCodes(convert.getBranchCompanyId());
			if(adrTypeCodes.size()>0){
				convert.setAdrTypeCodes(adrTypeCodes);
			}
		}

        PageHelper.startPage(convert.getPageNum(), convert.getPageSize());
        List<PmPurchaseOrderInfoPo> pmPurchaseOrderInfoPos = pmPurchaseOrderDao.queryPurchaseOrderListInfo(convert);
        PageInfo<PmPurchaseOrderInfoPo> pageInfo = new PageInfo<PmPurchaseOrderInfoPo>(pmPurchaseOrderInfoPos);
        return pageInfo;
    }


    /**
     * 根据传入参数查询采购单管理列表
     *
     * @param convert
     * @return
     */
    @Override
    public PageInfo<PmPurchaseOrderPo> queryPurchaseOrderList(PmPurchaseQueryParamPo convert) {
		if(!StringUtils.isBlank(convert.getBranchCompanyId())){
			List<String> adrTypeCodes = getWarehouseAdrTypeCodes(convert.getBranchCompanyId());
			if(adrTypeCodes.size()>0){
				convert.setAdrTypeCodes(adrTypeCodes);
			}
		}

        PageHelper.startPage(convert.getPageNum(), convert.getPageSize());
        List<PmPurchaseOrderPo> pmPurchaseOrderPoList = pmPurchaseOrderDao.queryPurchaseOrderList(convert);
        PageInfo<PmPurchaseOrderPo> pageInfo = new PageInfo<PmPurchaseOrderPo>(pmPurchaseOrderPoList);
        return pageInfo;
    }

	/**
	 * 根据子公司ID查询逻辑仓库，查询统采订单时使用
	 *
	 * @param companyId
	 * @return
	 */
    private List<String> getWarehouseAdrTypeCodes(String companyId){
		List<WarehouseLogicInfoPo> warehouseLogicInfoPos = warehouseLogicInfoDao.getWarehouseByBranchCompanyId(companyId);
		List<String> adrTypeCodes = new ArrayList<>();
		if(warehouseLogicInfoPos.size()>0){
			for(WarehouseLogicInfoPo warehouseLogicInfoPo : warehouseLogicInfoPos){
				String warehouseCode = warehouseLogicInfoPo.getWarehouseCode();
				adrTypeCodes.add(warehouseCode);
			}
		}

		return adrTypeCodes;
	}

    @Override
    public int updateByPrimaryKeySelective(PmPurchaseOrderPo record) {
        return pmPurchaseOrderDao.updateByPrimaryKeySelective(record);
    }


	@Override
	public PmPurchaseOrderPo selectByPrimaryKey(Long id) {
		return pmPurchaseOrderDao.selectByPrimaryKey(id);
	}


	@Override
	public List<PmPurchaseOrderPo> selectListByCondition(PmPurchaseQueryParamPo pmPurchaseQueryParamPo) {
		return pmPurchaseOrderDao.queryPurchaseOrderList(pmPurchaseQueryParamPo);
	}



	@Override
	public int closePurchaseListByPrimaryKey(List<Long> updateListId, String modifyUser) {
		return pmPurchaseOrderDao.closePurchaseListByPrimaryKey(updateListId, new Date(), modifyUser);

	}



	@Override
	public int closePurchaseByPrimaryKey(Long id, String modifyUserId) {
		return pmPurchaseOrderDao.closePurchaseByPrimaryKey(id, new Date(), modifyUserId,TimeOut_Close);
	}



	@Override
	public boolean deletePmPurchaseOrderById(Long orderId) {
		// 1.批量删除主表
		boolean mainDeleteSuccess = pmPurchaseOrderDao.deletePmPurchaseOrderById(orderId) >= 1;
		if (!mainDeleteSuccess) {// 删除商品失败
			return mainDeleteSuccess;
		}
		// 2.删除订单子项
		boolean itemDeleteSuccess = pmPurchaseOrderItemDao.deletePmPurchaseOrderItemById(String.valueOf(orderId)) >= 1;
		return mainDeleteSuccess && itemDeleteSuccess;
	}



	/**
	 * 根据采购单id查询采购单详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public PmPurchaseOrderInfoPo getPurchaseOrderInfoById(Long id) {
		PmPurchaseOrderInfoPo purchaseOrderInfoById = pmPurchaseOrderDao.getPurchaseOrderInfoById(id);
		return purchaseOrderInfoById;
	}



	/**
	 * 审核采购单列表
	 *
	 * @param convert1
	 * @return
	 */
	@Override
	public boolean auditPurchaseOrderInfo(PmPurchaseOrderPo convert1) {
		int i = pmPurchaseOrderDao.updateByPrimaryKeySelective(convert1);
		return i >= 1;
	}



	@Override
	public PmPurchaseOrderItemPo selectBypurchaseOrderIdAndPrimaryKey(Long purchaseOrderId, Long id,String itemCode,long receivedNum) {

		return pmPurchaseOrderItemDao.selectBypurchaseOrderIdAndPrimaryKey(purchaseOrderId, id,itemCode,receivedNum);
	}



	@Override
	public boolean updateBarCodeUrlById(Long mainId, String barCodeUrl) {
		return pmPurchaseOrderDao.updateBarCodeUrlById(mainId, barCodeUrl) >= 1;
	}



	/**
	 * 根据采购订单编号查询采购订单详情
	 *
	 * @param purchaseOrderNo
	 * @return
	 */
	@Override
	public PmPurchaseOrderInfoPo getPurchaseOrderInfoByOrderNo(String purchaseOrderNo) {
		return pmPurchaseOrderDao.getPurchaseOrderInfoByOrderNo(purchaseOrderNo);
	}



	@Override
	public PmPurchaseOrderPo selectPmPurchaseOrderByPurchaseReceiptNo(String purchaseReceiptNo) {
		return pmPurchaseOrderDao.selectPmPurchaseOrderByPurchaseReceiptNo(purchaseReceiptNo);
	}



	@Override
	public Double selectPurchasePriceByPurchaseReceiptItemId(Long id) {
		return pmPurchaseOrderDao.selectPurchasePriceByPurchaseReceiptItemId(id);
	}



	@Override
	public PmPurchaseOrderItemPo selectPmPurchaseOrderItemByPurchaseReceiptItemId(Long id) {
		return pmPurchaseOrderItemDao.selectPmPurchaseOrderItemByPurchaseReceiptItemId(id);
	}



	@Override
	public List<PmPurchaseOrderPo> selectCloseablePurchaseOrder(int status, int timeout) {
		return pmPurchaseOrderDao.selectCloseablePurchaseOrder(status, timeout);
	}



	@Override
	public PageInfo<PmPurchaseOrderItemPo> selectBrandsByOrderNo(
			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo) {
		PageHelper.startPage(pmPurchaseOrderItemQueryParamPo.getPageNum(),
				pmPurchaseOrderItemQueryParamPo.getPageSize());
		List<PmPurchaseOrderItemPo> pmPurchaseOrderItemPos = pmPurchaseOrderItemDao
				.selectBrandsByOrderNo(pmPurchaseOrderItemQueryParamPo);
		PageInfo pageInfo = new PageInfo<>(pmPurchaseOrderItemPos);
		return pageInfo;
	}



	@Override
	public PageInfo<PmPurchaseOrderItemPo> selectProductByOrderNo(
			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo) {
		PageHelper.startPage(pmPurchaseOrderItemQueryParamPo.getPageNum(),
				pmPurchaseOrderItemQueryParamPo.getPageSize());
		List<PmPurchaseOrderItemPo> pmPurchaseOrderItemPos = pmPurchaseOrderItemDao
				.selectProductByOrderNo(pmPurchaseOrderItemQueryParamPo);
		PageInfo pageInfo = new PageInfo<>(pmPurchaseOrderItemPos);
		return pageInfo;
	}



	@Override
	public List<PmPurchaseOrderItemPo> addRefundProducts(
			PmPurchaseOrderItemQueryParamPo pmPurchaseOrderItemQueryParamPo) {
		return pmPurchaseOrderItemDao.addRefundProducts(pmPurchaseOrderItemQueryParamPo);
	}



	@Override
	public PmPurchaseOrderItemPo queryItemById(Long valueOf) {
		return pmPurchaseOrderItemDao.selectByPrimaryKey(valueOf);
	}


	/**
	 * @see com.yatang.sc.facade.service.PmPurchaseOrderService#updateStatus(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Boolean updateStatus(String orderNo, Integer status) {
		return pmPurchaseOrderDao.updateStatus(orderNo,status) >= 1;
	}


	/**
	 * @Description: 根据条件分页查询供应商采购单
	 * @author kangdong
	 * @date 2018年1月10日上午9:11:01
	 * @param convert
	 * @return
	 */
	@Override
	public PageInfo<PmSupplierPurchaseOrderItemPo> querySupplierPurchaseOrderList(PmSupplierPurchaseOrderQueryParamPo convert){
		PageHelper.startPage(convert.getPageNum(), convert.getPageSize());
		log.info("dao convert:{}", JSON.toJSONString(convert));
		List<PmSupplierPurchaseOrderItemPo> pmSupplierPurchaseOrderPos = pmPurchaseOrderDao.querySupplierPurchaseOrderList(convert);
		PageInfo<PmSupplierPurchaseOrderItemPo> pageInfo = new PageInfo<PmSupplierPurchaseOrderItemPo>(pmSupplierPurchaseOrderPos);
		return pageInfo;
	}

	/**
	 * 根据采购单id关闭采购单（直送关闭）
	 * @param purchaseOrderId
	 *  @author lvheping
	 * @return
	 */
	@Override
	public void closePmPurchaseOrder(Long purchaseOrderId,String userName){


		//查询所有的收货单
		List<PmPurchaseReceiptPo> receiptPoList = pmPurchaseReceiptDao.queryPurchaseReceiptListByPurchaseOrderId(String.valueOf(purchaseOrderId), null);
        if (receiptPoList!=null) {
            //只有一个收货单
            if (receiptPoList.size()==1) {
                PmPurchaseReceiptPo receiptPo = receiptPoList.get(0);
                if (StringUtils.isBlank(receiptPo.getAsn())) {
                    throw new RuntimeException("purchaseOrderId:"+purchaseOrderId+"直送订单的收货单单号："+receiptPo.getPurchaseReceiptNo()+"asn为空关闭失败");
                }
                if (delivery_receipt_status.equals(receiptPo.getStatus())) {
                    pmPurchaseOrderDao.closePurchaseByPrimaryKey(purchaseOrderId,new Date(),userName,Normal_Close);
                }
            }else {
                //多个收货单
                int deliveredReceiptCount=0;
                for (PmPurchaseReceiptPo receiptPo : receiptPoList) {
                    if (delivery_receipt_status.equals(receiptPo.getStatus())&&!StringUtils.isBlank(receiptPo.getAsn())) {
                        deliveredReceiptCount++;
                    }
                }
                if (delivery_receipt_max_asn_count==deliveredReceiptCount) {
                    pmPurchaseOrderDao.closePurchaseByPrimaryKey(purchaseOrderId,new Date(),userName,Normal_Close);
                }
            }
        }else {
            throw new RuntimeException("purchaseOrderId"+purchaseOrderId+"直送订单关闭失败");
        }

	}



	@Override
	public PmPurchaseOrderItemPo selectByPurchaseOrderIdAndProductCode(Long purchaseOrderId, String itemCode) {
		return pmPurchaseOrderItemDao.selectByPurchaseOrderIdAndProductCode(purchaseOrderId,itemCode);
	}



	@Override
	public PmPurchaseOrderPo selectBySaleOrderId(String saleOrderId) {
		return pmPurchaseOrderDao.selectBySaleOrderId(saleOrderId);
	}





}
