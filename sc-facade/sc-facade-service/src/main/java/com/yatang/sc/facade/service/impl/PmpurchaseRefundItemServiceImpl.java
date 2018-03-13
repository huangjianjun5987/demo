package com.yatang.sc.facade.service.impl;

import com.yatang.sc.facade.dao.PmPurchaseRefundItemDao;
import com.yatang.sc.facade.domain.pm.PmPurchaseRefundItemPo;
import com.yatang.sc.facade.service.PmpurchaseRefundItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/10/17 14:58
 * @version: v1.0
 */
@Service
public class PmpurchaseRefundItemServiceImpl implements PmpurchaseRefundItemService {
	@Autowired
	private PmPurchaseRefundItemDao pmPurchaseRefundItemDao;


	@Transactional
	@Override
	public int updateByPrimaryKey(PmPurchaseRefundItemPo record) {
		return pmPurchaseRefundItemDao.updateByPrimaryKey(record);
	}



	@Override
	public PmPurchaseRefundItemPo selectByPrimaryKey(Long refundItemId) {
		return pmPurchaseRefundItemDao.selectByPrimaryKey(refundItemId);
	}


	@Transactional
	@Override
	public void batchUpdate(List<PmPurchaseRefundItemPo> pmPurchaseRefundItemPos) {
		if (pmPurchaseRefundItemPos!=null && pmPurchaseRefundItemPos.size()>0){
			for (PmPurchaseRefundItemPo pmPurchaseRefundItemPo:pmPurchaseRefundItemPos){
				pmPurchaseRefundItemDao.updateByPrimaryKeySelective(pmPurchaseRefundItemPo);
			}
		}
	}

	@Override
	public List<PmPurchaseRefundItemPo> selectRefundItemsByRefundId(Long refundId) {
		return pmPurchaseRefundItemDao.selectByRefundId(refundId);
	}

}
