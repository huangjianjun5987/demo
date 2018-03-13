package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdSellInfoEventsPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdSellInfoEventsDao {
    //没用到
    int deleteByPrimaryKey(Long id);

    int insert(ProdSellInfoEventsPo record);
    //没用到
    int insertSelective(ProdSellInfoEventsPo record);
    //没用到
    ProdSellInfoEventsPo selectByPrimaryKey(Long id);
    //没用到
    int updateByPrimaryKeySelective(ProdSellInfoEventsPo record);
    //没用到
    int updateByPrimaryKeyWithBLOBs(ProdSellInfoEventsPo record);
    //没用到
    int updateByPrimaryKey(ProdSellInfoEventsPo record);

    /**
     * 根据所用表id删除数据
     * @param priceId
     * @return
     */
    int deleteByPriceId(@Param(value = "priceId") Long priceId);

    /**
     * 根据关联外键查询审核中的区间价格,默认只取最新的一个（sql里已经筛选）
     * @param priceId 主表销售关系主键
     * @author yinyuxin
     * @return
     */
    ProdSellInfoEventsPo queryByPriceId(@Param(value = "priceId") Long priceId);

}