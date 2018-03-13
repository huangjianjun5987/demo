package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;

/**
 * @描述: 通用工具dubbo服务接口
 * @作者: tankejia
 * @创建时间: 2017/7/27-15:58 .
 * @版本: 1.0 .
 */
public interface CommonUtilDubboService {

    /**
     * @Description: 根据编码生成条形码（返回字节流）
     * @author tankejia
     * @date 2017/7/27- 16:00
     * @param msg
     * @return byte[]
     */
    public Response<byte[]> generateBarCode(String msg);


    /**
     * 根据日期格式化标准生成订单流水号(日期+后4位)
     * @param redisKey 存放在redis里面的key
     * @param preFix 前缀
     * @param dateFormat 日期格式化
     * @param length 后缀长度
     * @return 流水号
     */
    Response<String> generateSerialNo(String redisKey,String preFix, String dateFormat, int length);
}
