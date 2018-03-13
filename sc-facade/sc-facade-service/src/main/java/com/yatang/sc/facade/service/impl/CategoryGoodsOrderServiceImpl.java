package com.yatang.sc.facade.service.impl;

import static com.yatang.sc.facade.enums.PublicEnum.DEFAULT_PAGE_NUM;
import static com.yatang.sc.facade.enums.PublicEnum.DEFAULT_PAGE_SIZE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.CategoryGoodsOrderDao;
import com.yatang.sc.facade.domain.CategoryGoodsOrderPo;
import com.yatang.sc.facade.service.CategoryGoodsOrderService;

/**
 * @描述: 列表页商品排序serviceImpl.
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日15:30:39 .
 * @版本: 1.0 .
 * @param <T>
 */
@Service
@Transactional
public class CategoryGoodsOrderServiceImpl implements CategoryGoodsOrderService {
	
	@Autowired
	private CategoryGoodsOrderDao categoryGoodsOrderDao;

	@Override
	public int insertCategoryGoodsOrder(CategoryGoodsOrderPo record) {
		int sum=0;
		//首先验证该商品是否已经排序
		CategoryGoodsOrderPo temp=CategoryGoodsOrderPo.builder().id(record.getId()).firstCategoryId(record.getFirstCategoryId())
								   .secondCategoryId(record.getSecondCategoryId()).thirdCategoryId(record.getThirdCategoryId()).build();
		if (categoryGoodsOrderDao.countBySelective(temp)>0) {
			sum=2;
			return sum;
		}
		if (this.checkOrderNumRepeat(record)==0) {
			//如果排序号重复了，就换成插入到该位置前面，后面的数据排序号依次+1
			Map<String, Object> keys=new HashMap<String,Object>();
			keys.put("firstCategoryId", record.getFirstCategoryId());
			keys.put("secondCategoryId", record.getSecondCategoryId());
			keys.put("thirdCategoryId", record.getThirdCategoryId());
			keys.put("endOrderNum", record.getOrderNum());
			List<CategoryGoodsOrderPo> list=categoryGoodsOrderDao.queryByOrderRange(keys);
			for (CategoryGoodsOrderPo categoryGoodsOrderPo : list) {
				categoryGoodsOrderPo.setOrderNum(categoryGoodsOrderPo.getOrderNum()+1);
			}
			categoryGoodsOrderDao.updateOrderNum(list);
		}
		sum=categoryGoodsOrderDao.insertSelective(record);
		return sum;
	}

	@Override
	public int updateCategoryGoodsOrderNum(CategoryGoodsOrderPo record,Integer endOrderNum) {
		int sum=0;
		List<CategoryGoodsOrderPo> list=new ArrayList<CategoryGoodsOrderPo>();
		if (this.checkOrderNumRepeat(record)==0) {
			//如果重复了，则拖动批量移动
			Map<String, Object> keys=new HashMap<String,Object>();
			keys.put("firstCategoryId", record.getFirstCategoryId());
			keys.put("secondCategoryId", record.getSecondCategoryId());
			keys.put("thirdCategoryId", record.getThirdCategoryId());
			keys.put("startOrderNum", (record.getOrderNum()-endOrderNum<0)?record.getOrderNum():endOrderNum);
			keys.put("endOrderNum", (record.getOrderNum()-endOrderNum>0)?record.getOrderNum():endOrderNum);
			List<CategoryGoodsOrderPo> list2=categoryGoodsOrderDao.queryByOrderRange(keys);
			for (CategoryGoodsOrderPo categoryGoodsOrderPo : list2) {
				if (categoryGoodsOrderPo.getPkId()!=record.getPkId()) {
					if (record.getOrderNum()<endOrderNum) {
						categoryGoodsOrderPo.setOrderNum(categoryGoodsOrderPo.getOrderNum()-1);
						list.add(categoryGoodsOrderPo);
					}else if (record.getOrderNum()>endOrderNum) {
						categoryGoodsOrderPo.setOrderNum(categoryGoodsOrderPo.getOrderNum()+1);
						list.add(categoryGoodsOrderPo);
					}
				}
			}
		}
			CategoryGoodsOrderPo temp=CategoryGoodsOrderPo.builder().pkId(record.getPkId())
										.orderNum(endOrderNum).build();
			list.add(temp);
			sum=categoryGoodsOrderDao.updateOrderNum(list);
		return sum;
	}

	@Override
	public int deleteCategoryGoodsOrder(Integer pkId) {
		CategoryGoodsOrderPo record=categoryGoodsOrderDao.selectByPrimaryKey(pkId);
		int sum=categoryGoodsOrderDao.deleteByPrimaryKey(pkId);
		Map<String, Object> keys=new HashMap<String,Object>();
		keys.put("firstCategoryId", record.getFirstCategoryId());
		keys.put("secondCategoryId", record.getSecondCategoryId());
		keys.put("thirdCategoryId", record.getThirdCategoryId());
		keys.put("endOrderNum", record.getOrderNum());
		List<CategoryGoodsOrderPo> list=categoryGoodsOrderDao.queryByOrderRange(keys);
		for (CategoryGoodsOrderPo categoryGoodsOrderPo : list) {
			categoryGoodsOrderPo.setOrderNum(categoryGoodsOrderPo.getOrderNum()-1);
		}
		if (list!=null && list.size()>0) {
			categoryGoodsOrderDao.updateOrderNum(list);
		}
		return sum;
	}

	@Override
	public PageInfo<CategoryGoodsOrderPo> queryCategoryGoodsOrder(CategoryGoodsOrderPo record,Integer pageNum,Integer pageSize) {
		PageHelper.startPage((null != pageNum && pageNum != 0)?pageNum:Integer.valueOf(DEFAULT_PAGE_NUM.getCodeStr())
				, (null != pageSize && pageSize != 0)?pageSize:Integer.valueOf(DEFAULT_PAGE_SIZE.getCodeStr()));
		List<CategoryGoodsOrderPo> datas=categoryGoodsOrderDao.selectBySelective(record);
		PageInfo<CategoryGoodsOrderPo> pageInfo=new PageInfo<>(datas);
		return pageInfo;
	}

	@Override
	public int checkOrderNumRepeat(CategoryGoodsOrderPo record) {
		CategoryGoodsOrderPo test=CategoryGoodsOrderPo.builder()
				.firstCategoryId(record.getFirstCategoryId())
				.secondCategoryId(record.getSecondCategoryId())
				.thirdCategoryId(record.getThirdCategoryId())
				.orderNum(record.getOrderNum()).build();
		int count=categoryGoodsOrderDao.countBySelective(test);
		if (count>0) return 0;
		return 1;
	}

	@Override
	public int checkGoodOrderCountOver(CategoryGoodsOrderPo record) {
		int count=categoryGoodsOrderDao.countBySelective(record);
		//每个新建分类中，只能添加序号1-48号
		if (count>=48) return 0;
		return 1;
	}

}
