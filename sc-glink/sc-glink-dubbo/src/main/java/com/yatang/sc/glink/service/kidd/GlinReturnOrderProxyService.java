package com.yatang.sc.glink.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.returnrequest.ReturnOrderDto;

/**
 * @描述:际链退货接口调用
 * @类名:GlinReturnOrderProxyService
 * @作者: lvheping
 * @创建时间: 2017/10/18 13:48
 * @版本: v1.0
 */
@Service("glinReturnOrderProxyService")
public class GlinReturnOrderProxyService {
    /**
     * 调用际链的退货入库接口
     * @param returnOrderDto
     * @return
     */
    @KiddCaller(method = "g2matrix.entryorder.creatreturnUnloadingInfor", callType = CallTypeEnum.SYNC_CALL)
    public GlinkResponseDto<String> create(ReturnOrderDto returnOrderDto) {
        return null;
    }
}
