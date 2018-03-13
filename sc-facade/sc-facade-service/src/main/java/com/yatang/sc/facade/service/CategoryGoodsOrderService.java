package com.yatang.sc.facade.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.CategoryGoodsOrderPo;

/**
 * @描述: 列表页商品排序service.
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日15:30:39 .
 * @版本: 1.0 .
 * @param <T>
 */
public interface CategoryGoodsOrderService {

	/**
	* @Description: 新增分类商品排序  0:添加失败  1:新增成功 
	* @author yinyuxin
	* @date 2017年6月8日15:29:37
	* @param record 分类商品po对象
	 */
	int insertCategoryGoodsOrder(CategoryGoodsOrderPo record);
	
	/**
	* @Description: 修改分类商品排序号 0:更新失败  1:更新成功 2:排序号重复
	* @author yinyuxin
	* @date 2017年6月8日15:33:33
	* @param record 分类商品po对象
	 */
	int updateCategoryGoodsOrderNum(CategoryGoodsOrderPo record,Integer endOrderNum);
	
	/**
	* @Description: 删除分类商品排序记录
	* @author yinyuxin
	* @date 2017年6月8日15:33:33
	* @param pkId 分类商品po记录的主键id
	 */
	int deleteCategoryGoodsOrder(Integer pkId);
	
	/**
	* @Description: 分页查询分类商品排序记录
	* @author yinyuxin
	* @date 2017年6月8日15:36:53
	* @param record   分类商品po对象
	* @param pageNum  页码
	* @param pageSize 每页显示记录数
	 */
	PageInfo<CategoryGoodsOrderPo> queryCategoryGoodsOrder(CategoryGoodsOrderPo record,Integer pageNum,Integer pageSize);
	
	/**
	* @Description: 验证排序号是否重复
	* @author yinyuxin
	* @date 2017年6月8日19:45:15
	* @param record   分类商品po对象
	 */
	int checkOrderNumRepeat(CategoryGoodsOrderPo record);
	
	/**
	* @Description: 查询分类下 已经设置排序的商品数
	* @author yinyuxin
	* @date 2017年6月8日19:45:15
	* @param record   分类商品po对象
	 */
	int checkGoodOrderCountOver(CategoryGoodsOrderPo record);
	
}
