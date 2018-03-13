package com.yatang.sc.inventory.service;

import java.util.Date;
import java.util.List;

import com.yatang.sc.inventory.domain.TranData;

/**
 * Created by xiangyonghong on 2017/7/27.
 */
public interface TranDataService {

    void save(TranData tranData);

    void update(TranData tranData);


    /**
     *  根据传递的时间和商品id,tran_code,仓库code查询事物表中的数据
     *
     * @param productId     商品id
     * @param warehouseCode 仓库编码
     * @param code          tran_code编号
     * @param startDate          时间
     * @return
     */
   Long getTranDataUnitsByProductIdAndDate(String productId, String warehouseCode, String code, Date startDate);

	void saveList(List<TranData> tranDataList);


}
