package com.yatang.sc.facade.dao;

import java.util.List;
import java.util.Map;

import com.yatang.sc.facade.domain.CategoryGoodsOrderPo;

public interface CategoryGoodsOrderDao {

	CategoryGoodsOrderPo selectByPrimaryKey(Integer pkId);
	
	List<CategoryGoodsOrderPo> selectBySelective(CategoryGoodsOrderPo record);
	
    int deleteByPrimaryKey(Integer pkId);

    int insert(CategoryGoodsOrderPo record);

    int insertSelective(CategoryGoodsOrderPo record);

    int countBySelective(CategoryGoodsOrderPo record);

    int updateByPrimaryKeySelective(CategoryGoodsOrderPo record);

    int updateByPrimaryKey(CategoryGoodsOrderPo record);
    
    List<CategoryGoodsOrderPo> queryByOrderRange(Map<String, Object> keys);
    
    int updateOrderNum(List<CategoryGoodsOrderPo> list);
}