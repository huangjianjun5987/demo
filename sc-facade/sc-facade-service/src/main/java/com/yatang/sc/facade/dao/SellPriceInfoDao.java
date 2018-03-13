package com.yatang.sc.facade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.facade.domain.GoodsSellPriceQueryParamPo;
import com.yatang.sc.facade.domain.ProdBatchParameterPo;
import com.yatang.sc.facade.domain.SellPriceInfoPo;

public interface SellPriceInfoDao {
    int deleteByPrimaryKey(SellPriceInfoPo sellPriceInfoPo);

    int insert(SellPriceInfoPo record);

    int insertSelective(SellPriceInfoPo record);

    SellPriceInfoPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SellPriceInfoPo record);

    int updateByPrimaryKey(SellPriceInfoPo record);

    /**
     * 根据传入信息查询销售区间价格
     * @param goodsSellPriceQueryParamPo
     * @return
     */
    List<SellPriceInfoPo> getGoodsSellPrice(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo);

    /**
     * 物理删除处理
     * @param id
     * @param id
     * @return
     */
    int deleteSellPriceByPrimaryKey(@Param(value = "id") Long id);

    /**
     * 根据出入参数查询该商品在该子公司是否配置
     * @param goodsSellPriceQueryParamPo
     * @return
     */
    int checkSellPriceInfo(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo);

    /**
     * 根据传入参数修改销售区域价格状态
     * @param convert1
     * @return
     */
    int updateSellPriceStatus(SellPriceInfoPo convert1);

    /**
     * 根据出入的信息批量修改商品在该区间的状态为不启用
     * @param convert1
     * @return
     */
    int prodBatchUpdate(ProdBatchParameterPo convert1);

    /**
     * 根据传入商品id查询所有与商品有关的商品销售区域价格信息
     *
     * @param productId
     * @return
     */
    List<SellPriceInfoPo> getGoodsSellPriceByProductId(String productId);

    List<SellPriceInfoPo> getGoodsSellPriceByProductIdsAndCompanyId(@Param(value = "branchCompanyId") String branchCompanyId, @Param(value = "productIds") List<String> productIds);

    /**
     * 根据传入信息查询审核状态不是已提交和为未删除状态的销售区间价格
     * @param goodsSellPriceQueryParamPo
     * @return
     */
    SellPriceInfoPo getGoodsSellPriceInfo(GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo);

    /**
     * app端 商品列表显示规格（查询销售内装数）
     * @param branchCompanyId
     * @param productIds
     * @author yinyuxin
     * @return
     */
    List<SellPriceInfoPo> querySaleInnerNumberForApp(@Param("productIds") List<String> productIds,@Param("branchCompanyId") String branchCompanyId);
}