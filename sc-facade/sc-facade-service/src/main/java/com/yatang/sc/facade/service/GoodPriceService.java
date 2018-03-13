package com.yatang.sc.facade.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.GoodsSellPriceQueryParamPo;
import com.yatang.sc.facade.domain.ProdBatchParameterPo;
import com.yatang.sc.facade.domain.RegionBasicPo;
import com.yatang.sc.facade.domain.SellPriceInfoPo;
import com.yatang.sc.facade.domain.SellSectionPricePo;

/**
 * @描述:商品价格管理
 * @类名:GoodPriceService
 * @作者: lvheping
 * @创建时间: 2017/5/22 20:50
 * @版本: v1.0
 */

public interface GoodPriceService {


	/**
	 * 添加销售价格管理
	 * 
	 * @param sellPriceInfoPo
	 * @return
	 */
	Long insertSellPrice(SellPriceInfoPo sellPriceInfoPo);



	/**
	 * 修改销售价格
	 * 
	 * @param sellPriceInfoPo
	 * @return
	 */
	boolean updateSellPrice(SellPriceInfoPo sellPriceInfoPo);



	/**
	 * 删除销售区间价格（伪删除处理）
	 *
	 * @author lvheping
	 * @param
	 */

	boolean deleteSellPriceById(Long id,String userId);



	/**
	 * 根据供应商id查询查询下面的市
	 *
	 * @param supplierId
	 * @return
	 * @author yangshuang
	 */
	List<RegionBasicPo> queryRegionListBySupplierId(String supplierId);

	/**
	 * 根据销售关系id查询销售关系（包括区间价格）
	 * @param id 销售关系主键id
	 * @author yinyuxin
	 * @return
	 */
	SellPriceInfoPo queryPriceDetailById(Long id);

	/**
	 * 根据传入信息查询商品销售区域价格信息
	 * 
	 * @param goodsSellPriceQueryParamPo
	 * @return
	 */
	List<SellPriceInfoPo> getGoodsSellPrice(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo);

	/**
	 * 根据传入商品id查询所有与商品有关的商品销售区域价格信息
	 *
	 * @param productId
	 * @return
	 */
	List<SellPriceInfoPo> getGoodsSellPriceByProductId(String productId);

	/**
	 * 根据传入参数分页查询商品销售区域信息
	 * @param goodsSellPriceQueryParamPo
	 * @return
	 */
	PageInfo<SellPriceInfoPo> pageQuerySellPrice(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo);

	/**
	 * 根据传入参数查询该商品在该子公司是否已配置
	 * @param goodsSellPriceQueryParamPo
	 * @returnP
	 */
    int checkSellPriceInfo(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo);

	/**
	 * 根据传入参数修改销售区间价状态
	 * @param convert1
	 * @return
	 */
    Boolean updateSellPriceStatus(SellPriceInfoPo convert1);

	/**
	 * 根据出入的信息批量修改商品在该区间的状态为不启用
	 * @param convert1
	 * @return
	 */
    Boolean prodBatchUpdate(ProdBatchParameterPo convert1);


	/**
	 * 根据传入的信息批量的添加商品与子公司的销售关系
	 * @param convert1
	 * @param sellPriceInfoPos
	 * @return
	 */
    Boolean prodBatchUpdateInfo(ProdBatchParameterPo convert1, List<SellPriceInfoPo> sellPriceInfoPos);

	/**
	 * 根据采购关系id查询采购关系（包含已删除）
	 * @param id
	 * @return
	 */
	SellPriceInfoPo getGoodsSellPriceById(Long id);

	/**
	 * 根据传入商品ids和子公司ID批量查询商品有关的商品销售区域价格信息
	 *
	 * @param branchCompanyId
	 * @param productIds
	 * @return
	 */
	List<SellPriceInfoPo> getGoodsSellPriceByProductIdsAndCompanyId(String branchCompanyId, List<String> productIds);

	/**
	 * 审核完成更改审核或已拒绝状态
	 * @param sellPriceInfoPo
	 * @return
	 */
	Boolean updateAuditSellPrice(SellPriceInfoPo sellPriceInfoPo);

	/**
	 * 根据传入参数修改子表信息
	 * @param sellSectionPricePo
	 * @return
	 */
	Boolean prodSellSectionUpadte(SellSectionPricePo sellSectionPricePo);

	/**
	 * 根据传入信息查询审核状态不是已提交和为未删除状态的销售区间价格
	 * @param goodsSellPriceQueryParamPo
	 * @author kangdong
	 * @return
	 */
	SellPriceInfoPo getGoodsSellPriceInfo(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo);

	/**
	 * 添加商品区间售价的毛利率
	 * @param sellPriceInfoPo 商品销售关系（带区间价格）
	 * @author yinyuxin
	 * @return
	 */
	SellPriceInfoPo addGrossRate(SellPriceInfoPo sellPriceInfoPo);

	/**
	 * app端 商品列表显示规格（查询销售内装数）
	 * @param branchCompanyId
	 * @param productIds
	 * @author yinyuxin
	 * @return
	 */
	Map<String,Integer> querySaleInnerNumberForApp(List<String> productIds,String branchCompanyId);
}
