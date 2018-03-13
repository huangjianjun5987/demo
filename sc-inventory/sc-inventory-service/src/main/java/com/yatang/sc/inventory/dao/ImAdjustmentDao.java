package com.yatang.sc.inventory.dao;


import com.yatang.sc.inventory.domain.ImAdjustmentPo;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryListPo;
import com.yatang.sc.inventory.domain.ImAdjustmentQueryParamPo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ImAdjustmentDao {
    int deleteByPrimaryKey(Long id);

    int insert(ImAdjustmentPo record);

    int insertSelective(ImAdjustmentPo record);

    ImAdjustmentPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImAdjustmentPo record);

    int updateByPrimaryKey(ImAdjustmentPo record);

    /**
     * 根据传入参数分页查询库存调整列表
     * @param convert1
     * @return
     */
    List<ImAdjustmentQueryListPo> queryListImAdjustment(ImAdjustmentQueryParamPo convert1);

    /**
     * 根据传入id查询库存调整详情
     * @param id
     * @return
     */
    ImAdjustmentQueryListPo getImAdjustmentById(Long id);

    int updateAdjustmentReceipt(@Param(value = "id") Long id, @Param(value = "status") int status, @Param(value = "totalAdjustmentCost") BigDecimal totalAdjustmentCost);

    /**
     * 根据传入参数outBizCode查询库存调整详情
     *
     * @param outBizCode 外部唯一编码
     * @author yangshuang
     * @return ImAdjustmentPo
     */
    ImAdjustmentPo getImAdjustmentByOutBizCode(String outBizCode);
}