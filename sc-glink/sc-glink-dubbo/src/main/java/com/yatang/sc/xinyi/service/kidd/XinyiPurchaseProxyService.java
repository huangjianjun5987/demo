package com.yatang.sc.xinyi.service.kidd;

import com.yatang.sc.xinyi.dto.purchase.XinyiEntryOrderRequestDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiEntryOrderResponseDto;
import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;

/**
 * @描述:心怡接口的代理调用
 * @类名:GLinkPurchaseProxyService
 * @作者: lvheping
 * @创建时间: 2017/9/25 10:43
 * @版本: v1.0
 */
@Service(value = "xinyiPurchaseProxyService")
public class XinyiPurchaseProxyService {
    /**
     * 心怡创建入库单
     * @param xinyiEntryOrderRequestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "entryorder.create", callType = CallTypeEnum.SYNC_CALL)
    public XinyiEntryOrderResponseDto create(XinyiEntryOrderRequestDto xinyiEntryOrderRequestDto) {
        return null;
    }
}
