package com.yatang.sc.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.order.dao.WishDetailDao;
import com.yatang.sc.order.dao.WishListDao;
import com.yatang.sc.order.domain.WishDetailPo;
import com.yatang.sc.order.domain.WishListParamPo;
import com.yatang.sc.order.domain.WishListPo;
import com.yatang.sc.order.service.WishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Map;

/**
 * @description: 心愿单service实现类
 * @author: yinyuxin
 * @date: 2018/1/2 17:19
 * @version: v1.0
 */
@Service("wishService")
public class WishServiceImpl implements WishService {

	@Autowired
	private WishListDao   wishListDao;
	@Autowired
	private WishDetailDao wishDetaildao;



	@Override
	public PageInfo<WishListPo> queryWishListsByParam(WishListParamPo wishListParamPo) {
		PageHelper.startPage(wishListParamPo.getPageNum()==null? Integer.valueOf(CommonsEnum.RESPONSE_PAGE_NUM.getCode()):wishListParamPo.getPageNum(),
				wishListParamPo.getPageSize()==null?Integer.valueOf(CommonsEnum.RESPONSE_PAGE_SIZE.getCode()):wishListParamPo.getPageSize());
		List<WishListPo> wishListPos=wishListDao.queryWishListsByParam(wishListParamPo);
		return new PageInfo<WishListPo>(wishListPos);
	}



	@Override
	public WishListPo queryWishListById(Long wishListId) {
		return wishListDao.selectByPrimaryKey(wishListId);
	}



	@Override
	public PageInfo<WishDetailPo> queryWishDetailsByWishListIdAndStoreId(WishListParamPo wishListParamPo) {
		PageHelper.startPage(wishListParamPo.getPageNum()==null? Integer.valueOf(CommonsEnum.RESPONSE_PAGE_NUM.getCode()):wishListParamPo.getPageNum(),
				wishListParamPo.getPageSize()==null?Integer.valueOf(CommonsEnum.RESPONSE_PAGE_SIZE.getCode()):wishListParamPo.getPageSize());
		WishDetailPo wishDetailPo=new WishDetailPo();
		wishDetailPo.setWishListId(wishListParamPo.getWishListId());
		wishDetailPo.setStoreId(wishListParamPo.getStoreId());
		List<WishDetailPo> wishDetailPos=wishDetaildao.queryWishDetailByWishListIdAndStoreId(wishDetailPo);
		return new PageInfo<WishDetailPo>(wishDetailPos);
	}

	@Override
	public WishListPo findWishForBarcode(String barCode) {
		return wishListDao.findWishForBarcode(barCode);
	}

	@Override
	public void updateWish(WishListPo wishListPo) {
		wishListDao.updateByPrimaryKey(wishListPo);
	}

	@Override
	public void saveWishListPo(WishListPo wishListPo) {
		wishListDao.insert(wishListPo);
	}

	@Override
	public void saveWishDetail(WishDetailPo wishDetailPo) {
		wishDetaildao.insert(wishDetailPo);
	}

	@Override
	public List<WishDetailPo> queryWishDetailByCondition(Map<String, Object> condition) {
		return wishDetaildao.queryWishDetailByCondition(condition);
	}


	@Override
	public long queryWishDetailCountByCondition(Map<String, Object> condition){
		return wishDetaildao.queryWishDetailCountByCondition(condition);
	}

	@Override
	public WishListPo findWishListByCondition(Map<String, Object> condition) {
		return wishListDao.findWishListByCondition(condition);
	}


	@Override
	public void modifyWishListStatus(Long wishListId, String status) {
		if (!("complete".equals(status)||"close".equals(status))){
			throw new RuntimeException("修改心愿单状态状态失败：无效的状态值");
		}
		WishListPo wishListPo=new WishListPo();
		wishListPo.setId(wishListId);
		wishListPo.setStatus(status);
		wishListDao.updateByPrimaryKeySelective(wishListPo);
	}



	@Override
	public List<String> queryWishDetailIdsByWishListId(Long wishListId) {
		return wishDetaildao.queryWishDetailIdsByWishListId(wishListId);
	}
}
