package com.yatang.sc.juban.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.juban.dto.purchase.JubanEntryOrderRequestDto;
import com.yatang.sc.juban.dto.purchase.JubanEntryOrderResponseDto;

/**
 * @描述:桔瓣接口的代理调用
 * @类名:GLinkPurchaseProxyService
 * @作者: lvheping
 * @创建时间: 2017/9/25 10:43
 * @版本: v1.0
 */
@Service(value = "jubanPurchaseProxyService")
public class JubanPurchaseProxyService {
    /**
     * 桔瓣创建入库单
     * @param jubanEntryOrderRequestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "entryorder.create", callType = CallTypeEnum.SYNC_CALL)
    public JubanEntryOrderResponseDto create(JubanEntryOrderRequestDto jubanEntryOrderRequestDto) {
        return null;
    }
}
