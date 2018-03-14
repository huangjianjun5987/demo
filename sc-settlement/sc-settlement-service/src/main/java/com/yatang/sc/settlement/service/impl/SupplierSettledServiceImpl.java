package com.yatang.sc.settlement.service.impl;

import com.yatang.sc.settlement.dao.SupplierSettledDao;
import com.yatang.sc.settlement.domain.SupplierSettledPo;
import com.yatang.sc.settlement.domain.SupplierSettlementMultQueryPo;
import com.yatang.sc.settlement.service.SupplierSettledService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * <class description>供应商结算实现类
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年8月31日
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierSettledServiceImpl implements SupplierSettledService {
	@Autowired
	private SupplierSettledDao supplierSettledDao;



	// 不可修改
	@Override
	public int insert(SupplierSettledPo supplierSettledPo) {

		return supplierSettledDao.insert(supplierSettledPo);
	}



	// 不可修改
	@Override
	public int insertSelective(SupplierSettledPo supplierSettledPo) {
		return supplierSettledDao.insertSelective(supplierSettledPo);
	}

	@Override
	public List<SupplierSettledPo> listSupplierSettlementByMultParam(SupplierSettlementMultQueryPo record) {
		return supplierSettledDao.listSupplierSettlementByMultParam(record);
	}

}
