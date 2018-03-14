package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.AddressDto;

import java.util.List;

/**
 * @描述: 地址信息维护dubbo服务接口.
 * @作者: liusongjie
 * @创建时间: 2017/7/13
 * @版本: 1.0 .
 * @param <T>
 */
public interface AddressInfoDubboService {

    /**
     * @Description: 根据用户ID获取该用户下所有门店的地址信息
     * @author liusongjie
     * @date 2017/7/19- 14:13
     * @param
     */
    public Response<List<AddressDto>> queryAddressInfoByFranchiseeId(String franchiseeId);
}
