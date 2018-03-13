package com.yatang.sc.facade.dubboservice;

import java.util.List;
import java.util.Map;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.RegionBasicDto;
import com.yatang.sc.facade.dto.prod.ProSellPriceBpmDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceQueryParamDto;

/**
 * @描述:商品价格管理服务接口
 * @类名:GoodsPriceManage
 * @作者: lvheping
 * @创建时间: 2017/5/22 16:29
 * @版本: v1.0
 */

public interface GoodsPriceQueryDubboService {
	/**
	 * 查询商品价格详情
	 *
	 * @param
	 * @return
	 */
	Response<List<ProdSellPriceInfoDto>> getGoodsSellPriceByCondition(ProdSellPriceQueryParamDto prodSellPriceQueryParamDto);


	Response<ProdSellPriceInfoDto> getGoodsSellPrice(String productId, String companyCode);

	/**
	 * 根据销售关系id查询销售关系（包括区间价格）
	 * @param id 销售关系主键id
	 * @author yinyuxin
	 * @return
	 */
	Response<ProdSellPriceInfoDto> queryPriceDetailById(Long id);

	/**
	 * 分页查询查询商品价格详情
	 *
	 * @param
	 * @return
	 */
	Response<PageResult<ProdSellPriceInfoDto>> pageQuerySellPrice(ProdSellPriceQueryParamDto prodSellPriceQueryParamDto);

	/**
	 * 根据供应商id查询查询下面的市
	 *
	 * @return
	 * @author yangshuang
	 */
	Response<List<RegionBasicDto>> queryRegionListBySupplierId(String supplierId);



	/**
	 * 根据多个商品id批量查询价格详情
	 *
	 * @param list
	 * @return
	 */
	Response<Map<String, List<ProdSellPriceInfoDto>>> findSellPriceInfo(List<String> list);

	/**
	 * 根据传入信息查询商品在该子公司是否已经配置
	 * @param prodSellPriceQueryParamDto
	 * @return
	 */
    Response<Integer> checkSellPriceInfo(ProdSellPriceQueryParamDto prodSellPriceQueryParamDto);

	/**
	 * 根据传入商品ids和子公司ID批量查询商品有关的商品销售区域价格信息
	 *
	 * @param branchCompanyId
	 * @param productIds
	 * @return
	 */
	Response<List<ProdSellPriceInfoDto>> getGoodsSellPriceByProductIdsAndCompanyId(String branchCompanyId, List<String> productIds);

	/**
	 * 根据售价id查询商品售价详情（提供给流程管理使用）
	 * @param id
	 * @return
	 */
	Response<ProSellPriceBpmDto> getProdSellPriceInfo(Long id);

	/**
	 * app端 商品列表显示规格（查询销售内装数）
	 * @param branchCompanyId
	 * @param productIds
	 * @author yinyuxin
	 * @return
	 */
	Response<Map<String,Integer>> querySaleInnerNumberForApp(List<String> productIds,String branchCompanyId);
}


