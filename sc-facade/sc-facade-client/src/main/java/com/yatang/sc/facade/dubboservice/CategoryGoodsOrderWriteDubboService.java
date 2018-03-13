package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.CategoryGoodsOrderDto;

/**
 * @描述: 分类下商品排序dubbo服务接口.
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日16:08:57
 * @版本: 1.0 .
 * @param <T>
 */
public interface CategoryGoodsOrderWriteDubboService {

	/**
	 * @Description: 新增分类下的商品排序(如果排序号重复会直接插入)
	 * @author yinyuxin
	 * @date 2017年6月8日16:21:19
	 */
	Response<Integer> insertCategoryGoodsOrder(CategoryGoodsOrderDto param);
	
	
	/**
	 * @Description: 修改分类下的商品排序号(如果排序号重复，新旧排序号之间的数据会自动批量修改排序号)
	 * @author yinyuxin
	 * @date 2017年6月8日16:21:19
	 */
	Response<Integer> updateCategoryGoodsOrderNum(CategoryGoodsOrderDto param);
	
	/**
	 * @Description: 删除分类下的商品排序记录（后面的商品排序自动往上移动）
	 * @author yinyuxin
	 * @date 2017年6月8日16:21:19
	 */
	Response<Integer> deleteCategoryGoodsOrderNum(Integer pkId);
	
	
}
