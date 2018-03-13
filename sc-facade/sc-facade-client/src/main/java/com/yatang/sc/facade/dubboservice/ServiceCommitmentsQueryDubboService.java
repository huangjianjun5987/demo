package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.ServiceCommitmentsDto;

import java.util.List;

/**
 * @描述: 服务承诺QueryDubboService接口
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 9:18
 * @版本: v1.0
 */
public interface ServiceCommitmentsQueryDubboService {


    /**
     * 获取所有有效服务承诺：后期可能会设定对应的商品和供应商
     * @author yanghsuang
     * @return
     */
    Response<List<ServiceCommitmentsDto>> queryAllServiceCommitmentsList();
}
