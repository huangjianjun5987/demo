package com.yatang.sc.kidd.service;
import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderDto;

/**
 * @描述:退货接口（调用心怡际链）
 * @类名:KiddReturnOrderService
 * @作者: lvheping
 * @创建时间: 2017/10/18 11:43
 * @版本: v1.0
 */

public interface KiddReturnOrderService {
    /**
     * 创建退货单给仓库
     * @param kiddReturnOrderDto
     * @return
     */
    Response<String> create(KiddReturnOrderDto kiddReturnOrderDto);
}
