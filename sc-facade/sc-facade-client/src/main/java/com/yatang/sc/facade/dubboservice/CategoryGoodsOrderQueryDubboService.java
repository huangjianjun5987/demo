package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.CategoryGoodsOrderDto;
import com.yatang.sc.facade.dto.ProductInfoQueryDto;

/**
 * @描述: 分类下商品排序dubbo服务接口.
 * @作者: yinyuxin
 * @创建时间: 2017年6月8日16:08:57
 * @版本: 1.0 .
 * @param <T>
 */
public interface CategoryGoodsOrderQueryDubboService {

	/**
	 * @Description: 根据条件分页查询
	 * @author yinyuxin
	 * @date 2017年6月8日16:22:39
	 */
	Response<PageResult<CategoryGoodsOrderDto>> queryCategoryGoodsOrders(CategoryGoodsOrderDto param);
	
	
	/**
	 * @Description: 验证分类下 已经排序的商品是否超过48条
	 * @author yinyuxin
	 * @date 2017年6月8日19:49:53
	 */
	Response<Boolean> checkGoodsOrderCountOver(CategoryGoodsOrderDto param);
	
	/**
	 * 根据商品编号获取商品名称（需验证商品与分类是否匹配）
	 * @param param
	 * @return
	 */
	Response<String> queryGoodsName(ProductInfoQueryDto productQueryInfoDto);
}
