package com.yatang.sc.juban.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.juban.dto.im.JubanInventoryQueryRequestDto;
import com.yatang.sc.juban.dto.im.JubanInventoryQueryResponseDto;
import com.yatang.sc.juban.dto.product.JubanProductSynchronizeRequestDto;
import com.yatang.sc.juban.dto.product.JubanProductSynchronizeResponseDto;

@Service
public class JubanProductProxyService {

    /**
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "singleitem.synchronize", callType = CallTypeEnum.SYNC_CALL)
    public JubanProductSynchronizeResponseDto synchronize(JubanProductSynchronizeRequestDto requestDto) {
        return null;
    }

    /**
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "inventory.query", callType = CallTypeEnum.SYNC_CALL)
    public JubanInventoryQueryResponseDto inventoryQuery(JubanInventoryQueryRequestDto requestDto) {
        return null;
    }
}
