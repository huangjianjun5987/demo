package com.yatang.sc.inventory.dao;

import com.yatang.sc.inventory.domain.TranData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TranDataDao {
    int deleteByPrimaryKey(String itemId);

    int insert(TranData record);

    int insertSelective(TranData record);

    TranData selectByPrimaryKey(String itemId);

    int updateByPrimaryKeySelective(TranData record);

    int updateByPrimaryKey(TranData record);

    int insertTranData(TranData record);

    /**
     * 根据传递的时间和商品id,tran_code,仓库code查询事物表中的数据
     *
     * @param productId     商品id
     * @param warehouseCode 仓库编码
     * @param code          tran_code编号
     * @param date          start 时间
     * @return
     */
   Long getTranDataUnitsByProductIdAndDate(@Param(value = "productId") String productId, @Param(value = "warehouseCode") String warehouseCode, @Param(value = "code") String code, @Param(value = "date") Date date);

}