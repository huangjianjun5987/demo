package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.PromoRecordsPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PromoRecordsDao {

    List<PromoRecordsPo> queryByPromoId(String id);

    int deleteByPrimaryKey(String id);

    int insert(PromoRecordsPo po);

    int updateByPrimaryKey(String id);

    List<PromoRecordsPo> queryByPromoOrOrderId(@Param("promoId") String promoId, @Param("orderId") String orderId);

    /**
     * 查询参与数据总条数
     * @param queryParticipateDataMap
     * @return
     */
    long getParticipateDataPageListCount(Map<String, Object> queryParticipateDataMap);

    /**
     * 分页查询参与数据
     * @param queryParticipateDataMap
     * @return
     */
    List<Map<String,String>> getParticipateDataPageList(Map<String, Object> queryParticipateDataMap);

}
