package com.yatang.sc.facade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yatang.sc.facade.domain.SellSectionPricePo;

public interface SellSectionPriceDao {
    int deleteByPrimaryKey(Long id);

    int insert(SellSectionPricePo record);

    int insertSelective(SellSectionPricePo record);

    SellSectionPricePo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SellSectionPricePo record);

    /**
     * 修改区间价格为删除状态
     * @param sellPriceId
     * @param deleteStatus
     * @return
     */
    int deleteByPrimaryKey(@Param(value = "sellPriceId") Long sellPriceId, @Param(value = "deleteStatus") Integer deleteStatus);

    /**
     * 批量插入价格信息
     * @param sellSectionPrices
     */
    int insertPriceInfo(List<SellSectionPricePo> sellSectionPrices);

    /**
     * 物理删除区间价格
     * @param sellPriceId
     * @return
     */
    int deleteByPriceId(@Param(value = "sellPriceId") Long sellPriceId);

    /**
     * 根据主表id查询子表集合（子查询）
     * @param id 主表销售关系主键id
     * @return
     */
    List<SellSectionPricePo> selectSectionPriceinfo(Long id);


    /**
     * 物理删除区间价格（根据版本删除版本）
     * @param sellPriceId
     * @return
     */
    int deleteSellByPriceId(@Param(value = "sellPriceId") Long sellPriceId,@Param(value = "modifyVersion")Integer modifyVersion);
}