package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.facade.dao.PmPurchaseRefundDao;
import com.yatang.sc.facade.dao.PmPurchaseRefundItemDao;
import com.yatang.sc.facade.dao.ProcessDefinitionDao;
import com.yatang.sc.facade.domain.pm.*;
import com.yatang.sc.facade.service.PmpurchaseRefundService;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/10/17 14:58
 * @version: v1.0
 */
@Service
public class PmpurchaseRefundServiceImpl implements PmpurchaseRefundService {

	@Autowired
	private PmPurchaseRefundDao		pmPurchaseRefundDao;
	@Autowired
	private PmPurchaseRefundItemDao	pmPurchaseRefundItemDao;
	@Autowired
	private ProcessDefinitionDao	processDefinitionDao;
	@Autowired
	private RedisDistributedLockFactory mRedisDistributedLockFactory;


	@Transactional
	@Override
	public boolean createRefundWithItems(PmPurchaseRefundPo pmPurchaseRefundPo) {
		boolean status = false;

		String lockPath = "/createRefundWithItems" + pmPurchaseRefundPo.getPurchaseRefundNo();// 根据采购退货单单号枷锁
																								// //
		RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
		try {
			rLock.lock();
			// 1.新增采购退货单
			if (pmPurchaseRefundPo != null) {
				//// 查询审批流第一个节点的id
				//ProcessDefinitionPo processDefinitionPo = new ProcessDefinitionPo();
				//processDefinitionPo.setBranchCompanyId(pmPurchaseRefundPo.getBranchCompanyId());
				//processDefinitionPo.setIsFirstNode(0);
				//processDefinitionPo.setType(1L);
				//List<ProcessDefinitionPo> processDefinitionPos = processDefinitionDao
				//		.queryBySelective(processDefinitionPo);
				//if (processDefinitionPos == null || processDefinitionPos.size() <= 0) {
				//	throw new RuntimeException("未查询到审批流程设计");
				//}
				//pmPurchaseRefundPo.setProcessId(processDefinitionPos.get(0).getId());
				pmPurchaseRefundPo.setCreateTime(new Date());
				pmPurchaseRefundDao.insertSelective(pmPurchaseRefundPo);
				// 2.新增采购退货单清单
				List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos = pmPurchaseRefundPo.getPmPurchaseRefundItems();
				if (pmPurchaseRefundItemPos != null && pmPurchaseRefundItemPos.size() > 0) {
					for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo : pmPurchaseRefundItemPos) {
						pmPurchaseRefundItemPo.setPurchaseRefundId(pmPurchaseRefundPo.getId());
						pmPurchaseRefundItemPo.setCreateUserId(pmPurchaseRefundPo.getCreateUserId());
						pmPurchaseRefundItemPo.setCreateTime(new Date());
						pmPurchaseRefundItemPo.setIsValid(1);
						pmPurchaseRefundItemDao.insertSelective(pmPurchaseRefundItemPo);
					}
				}
				status = true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			rLock.unlock();
		}
		return status;
	}


	@Transactional
	@Override
	public boolean updateRefund(PmPurchaseRefundPo pmPurchaseRefundPo) {
		return pmPurchaseRefundDao.updateByPrimaryKeySelective(pmPurchaseRefundPo) == 1;
	}

	@Transactional
	@Override
	public boolean updateStatus(PmPurchaseRefundPo pmPurchaseRefundPo) {
		return pmPurchaseRefundDao.updateStatus(pmPurchaseRefundPo)==1;
	}


	@Transactional
	@Override
	public boolean updateRefundWithItems(PmPurchaseRefundPo pmPurchaseRefundPo) {
		boolean status = false;

		String lockPath = "/updateRefundWithItems" + pmPurchaseRefundPo.getPurchaseRefundNo();// 根据采购退货单单号枷锁
																								// //
		// //
		RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
		try {
			rLock.lock();
			// 1.修改采购退货单
			if (pmPurchaseRefundPo != null) {
				//// 查询审批流第一个节点的id
				//ProcessDefinitionPo processDefinitionPo = new ProcessDefinitionPo();
				//processDefinitionPo.setBranchCompanyId(pmPurchaseRefundPo.getBranchCompanyId());
				//processDefinitionPo.setIsFirstNode(0);
				//processDefinitionPo.setType(1L);
				//List<ProcessDefinitionPo> processDefinitionPos = processDefinitionDao
				//		.queryBySelective(processDefinitionPo);
				//if (processDefinitionPos == null || processDefinitionPos.size() <= 0) {
				//	throw new RuntimeException("未查询到审批流程设计");
				//}
				//pmPurchaseRefundPo.setProcessId(processDefinitionPos.get(0).getId());
				pmPurchaseRefundDao.updateByPrimaryKeySelective(pmPurchaseRefundPo);
				// 2.修改采购退货单清单
				List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos = pmPurchaseRefundPo.getPmPurchaseRefundItems();
				// 2.1 根据采购退货单id查询清单id集合
				List<Long> itemIds = pmPurchaseRefundItemDao.selectIdsByRefundId(pmPurchaseRefundPo.getId());
				// 2.2 没有id的itemPo执行新增、有id的执行修改或删除
				if (pmPurchaseRefundItemPos != null && pmPurchaseRefundItemPos.size() > 0) {
					List<Long> itemPoIds = new ArrayList<>();
					for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo : pmPurchaseRefundItemPos) {
						if (null == pmPurchaseRefundItemPo.getId() || 0 == pmPurchaseRefundItemPo.getId()) {
							pmPurchaseRefundItemPo.setPurchaseRefundId(pmPurchaseRefundPo.getId());
							pmPurchaseRefundItemPo.setCreateUserId(pmPurchaseRefundPo.getModifyUserId());
							pmPurchaseRefundItemPo.setCreateTime(new Date());
							pmPurchaseRefundItemPo.setIsValid(1);
							pmPurchaseRefundItemDao.insertSelective(pmPurchaseRefundItemPo);
						} else {
							if (itemIds.contains(pmPurchaseRefundItemPo.getId())) {
								pmPurchaseRefundItemPo.setModifyTime(new Date());
								pmPurchaseRefundItemPo.setModifyUserId(pmPurchaseRefundPo.getModifyUserId());
								pmPurchaseRefundItemDao.updateByPrimaryKeySelective(pmPurchaseRefundItemPo);
								itemPoIds.add(pmPurchaseRefundItemPo.getId());
							} else {
								throw new RuntimeException("参数有误:清单列表存在无记录情况对象");
							}
						}
					}
					itemIds.removeAll(itemPoIds);
					if (itemIds!=null && itemIds.size()>0){
						Map<String, Object> map = new HashMap<>();
						map.put("modifyUserId", pmPurchaseRefundPo.getModifyUserId());
						map.put("pmPurchaseRefundItemIds", itemIds);
						pmPurchaseRefundItemDao.invaliBatchByIds(map);
					}
				}
				status = true;
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		} finally {
			rLock.unlock();
		}
		return status;
	}


	@Transactional
	@Override
	public boolean updateRefundDetail(PmPurchaseRefundPo pmPurchaseRefundPo) {
		pmPurchaseRefundDao.updateByPrimaryKeySelective(pmPurchaseRefundPo);
		List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos=pmPurchaseRefundPo.getPmPurchaseRefundItems();
		if (pmPurchaseRefundItemPos!=null && pmPurchaseRefundItemPos.size()>0){
			for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo:pmPurchaseRefundItemPos){
				pmPurchaseRefundItemDao.updateByPrimaryKeySelective(pmPurchaseRefundItemPo);
			}
		}
		return true;
	}



	@Override
	public PageInfo<PmPurchaseRefundPo> queryPmPurchaseRefund(PmPurchaseRefundExtPo pmPurchaseRefundExtPo) {
		PageHelper.startPage(pmPurchaseRefundExtPo.getPageNum(), pmPurchaseRefundExtPo.getPageSize());
		List<PmPurchaseRefundPo> list = pmPurchaseRefundDao.queryPmPurchaseRefund(pmPurchaseRefundExtPo);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<PmPurchaseReturnProviderPo> queryPmPurchaseRefundProvider(PmPurchaseRefundProviderExtPo pmPurchaseRefundProviderExtPo) {
		PageHelper.startPage(pmPurchaseRefundProviderExtPo.getPageNum(), pmPurchaseRefundProviderExtPo.getPageSize());
		List<PmPurchaseReturnProviderPo> list = pmPurchaseRefundDao.queryPmPurchaseRefundProvider(pmPurchaseRefundProviderExtPo);
		return new PageInfo<>(list);
	}



	@Override
	public PmPurchaseRefundPo selectRefundDetailById(Long id) {
		return pmPurchaseRefundDao.selectRefundDetailById(id);
	}

	@Override
	public PmPurchaseRefundProviderPo selectRefundDetailProviderById(Long id) {
		return pmPurchaseRefundDao.selectRefundDetailProviderById(id);
	}

	@Override
	public PmPurchaseRefundPo selectRefundById(Long id) {
		return pmPurchaseRefundDao.selectByPrimaryKey(id);
	}



	/*
	@Override
	public PageInfo<PmPurchaseRefundWithProcessNodeNamePo> queryPurchaseRefundAuditList(
			PmPurchaseRefundAuditQueryPo pmPurchaseRefundAuditQueryPo) {
		PageHelper.startPage(pmPurchaseRefundAuditQueryPo.getPageNum(), pmPurchaseRefundAuditQueryPo.getPageSize());
		List<PmPurchaseRefundWithProcessNodeNamePo> list = pmPurchaseRefundDao
				.queryPurchaseRefundAuditList(pmPurchaseRefundAuditQueryPo);
		return new PageInfo<>(list);
	}
	*/


	@Transactional
	@Override
	public void deleteBatchRefundOrder(Long[] purchaseRefundIds) {
		pmPurchaseRefundDao.deleteByBatch(purchaseRefundIds);
		pmPurchaseRefundItemDao.deleteBatchByPurchaseRefundId(purchaseRefundIds);
	}



	@Override
	public boolean queryContainUndraftRefundOrder(Long[] purchaseRefundIds) {
		return pmPurchaseRefundDao.queryContainUndraftRefundOrder(purchaseRefundIds) >= 1;
	}


	/*
	@Override
	public boolean endApproveProcess(Long id,String auditUserId,Integer status) {
		return pmPurchaseRefundDao.endApproveProcess(id,auditUserId,status) == 1;
	}
	*/



	@Override
	public PmPurchaseRefundPo selectPurchaseRefundByPurchaseRefundNo(String purchaseRefundNo) {
		return pmPurchaseRefundDao.selectPurchaseRefundByPurchaseRefundNo(purchaseRefundNo);
	}


	@Transactional
	@Override
	public int updatePurchaseRefundStatusByPurchaseRefundNo(String purchaseRefundNo, Integer status) {
		return pmPurchaseRefundDao.updatePurchaseRefundStatusByPurchaseRefundNo(purchaseRefundNo, status);
	}

	@Override
	public long getAllPurchaseRefundCount(Integer status) {
		return pmPurchaseRefundDao.getAllPurchaseRefundCount(status);
	}


}
