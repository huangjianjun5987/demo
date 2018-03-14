package com.yatang.sc.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.utils.JSONUtils;
import com.yatang.sc.order.dao.PromoCategoriesDao;
import com.yatang.sc.order.dao.PromoCompaniesPoDao;
import com.yatang.sc.order.dao.PromoRecordsDao;
import com.yatang.sc.order.dao.PromoStoresDao;
import com.yatang.sc.order.dao.PromotionDao;
import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.order.states.PromotionStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	private PromoCategoriesDao	categoriesDao;

	@Autowired
	private PromoCompaniesPoDao	companiesPoDao;

	@Autowired
	private PromoStoresDao		storesDao;

	@Autowired
	private PromotionDao		promotionDao;

	@Autowired
	private PromoRecordsDao		recordsDao;



	@Override
	public PageInfo<PromotionPo> listPromotions(PromoQueryConditionPo param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<PromotionPo> listPo = promotionDao.listPromotions(param);
		//将字符串的活动规则转换为对象的表现形式
		for (PromotionPo promotionPo : listPo) {
			String promotionRuleJson = promotionPo.getPromotionRuleJson();
			PromotionRulePo promotionRulePo = JSONUtils.toObject(promotionRuleJson,PromotionRulePo.class);
			promotionPo.setPromotionRule(promotionRulePo);
		}
		return new PageInfo<PromotionPo>(listPo);
	}



	@Override
	public List<PromotionPo> listAvailablePromotions(String promoType) {
		return promotionDao.listAvailablePromotions(promoType);
	}



	@Override
	public List<PromoCompaniesPo> listBranchCompany(String promoId) {
		return companiesPoDao.queryByPromoId(promoId);
	}



	@Override
	public Boolean insertPromotion(PromotionPo po) {
		//将规则的对象转换成字符串
		PromotionRulePo promotionRule = po.getPromotionRule();
		String strRule = JSONUtils.toJson(promotionRule);
		po.setPromotionRuleJson(strRule);
		// 指定区域（子公司）
		List<PromoCompaniesPo> companiesPos = po.getCompaniesPoList();
		if (null != companiesPos && companiesPos.size()>0) {
			for (PromoCompaniesPo promoCompaniesPo : companiesPos) {
				promoCompaniesPo.setPromoId(po.getId());
				companiesPoDao.insert(promoCompaniesPo);
			}
		}

		// 品类
		PromoCategoriesPo categoriesPo = po.getPromoCategoriesPo();
		if (null != categoriesPo) {
			categoriesPo.setPromoId(po.getId());
			categoriesDao.insert(categoriesPo);
		}

		// 所有门店
		PromoStoresPo storesPo = po.getStores();
		if (null != storesPo) {
			storesPo.setPromoId(po.getId());
			storesDao.insert(storesPo);
		}
		po.setStatus(PromotionStates.UNRELEASED);
		return promotionDao.insert(po) >= 1;
	}



	@Override
	public List<PromoRecordsPo> queryByPromoOrOrderId(String promoId, String orderId) {
		return recordsDao.queryByPromoOrOrderId(promoId, orderId);
	}



	@Override
	public Boolean insertPromoOrderRecord(PromoRecordsPo record) {
		return recordsDao.insert(record) >= 1;
	}



	@Override
	public Boolean updatePromoStatus(PromotionPo record) {
		return promotionDao.updateByPrimaryKey(record) >= 1;
	}



	@Override
	public PromotionPo queryPromotionDetail(String promotionId) {
		PromotionPo promotionPo = promotionDao.queryById(promotionId);
		promotionPo.setPromotionRule(JSONUtils.toObject(promotionPo.getPromotionRuleJson(),PromotionRulePo.class));
		return promotionPo;
	}



	@Override
	public long getParticipateDataPageListCount(Map<String, Object> queryParticipateDataMap) {
		return recordsDao.getParticipateDataPageListCount(queryParticipateDataMap);
	}



	@Override
	public List<Map<String, String>> getParticipateDataPageList(Map<String, Object> queryParticipateDataMap) {
		return recordsDao.getParticipateDataPageList(queryParticipateDataMap);
	}



	@Override
	public PromotionPo queryById(String id) {
		return promotionDao.queryById(id);
	}



	@Override
	public List<AvailableCouponActivityPo> queryAvailableCouponActivity(String storeId) {
		return promotionDao.queryAvailableCouponActivity(storeId);
	}

	@Override
	public List<PromotionPo> queryAvailablePromotions(String branchCompanyId, String promoType) {
		List<PromotionPo> listPromotions = promotionDao.queryAvailablePromotions(branchCompanyId, promoType);
		for (PromotionPo promotion : listPromotions) {
			String promotionRuleJson = promotion.getPromotionRuleJson();
			PromotionRulePo promotionRuleDto = JSONUtils.toObject(promotionRuleJson, PromotionRulePo.class);
			promotion.setPromotionRule(promotionRuleDto);
		}
		return listPromotions;
	}

	@Override
	public Boolean updateStoreId(PromotionPo record) {
		PromoStoresPo stores = record.getStores();
		return storesDao.updateByPrimaryKey(stores) >= 1;
	}

	@Override
	public Boolean batchUpdatePromoStatus(String[] couponPromotionIds,String status) {
		return promotionDao.batchUpdatePromoStatus(couponPromotionIds,status) >= 1;
	}


}
