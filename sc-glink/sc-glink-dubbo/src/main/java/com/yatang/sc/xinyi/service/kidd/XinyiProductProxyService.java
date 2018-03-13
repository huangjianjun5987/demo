package com.yatang.sc.xinyi.service.kidd;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.xinyi.dto.im.InventoryQueryRequestDto;
import com.yatang.sc.xinyi.dto.im.InventoryQueryResponseDto;
import com.yatang.sc.xinyi.dto.product.ProductSynchronizeRequestDto;
import com.yatang.sc.xinyi.dto.product.ProductSynchronizeResponseDto;
import org.springframework.stereotype.Service;

@Service
public class XinyiProductProxyService {

    /**
     * http://api.cangyibao.com/doc/?docs=api%E6%96%87%E6%A1%A3%E8%AF%B4%E6%98%8E/2-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E/2-1-%E5%95%86%E5%93%81%E5%90%8C%E6%AD%A5%E6%8E%A5%E5%8F%A3
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "singleitem.synchronize", callType = CallTypeEnum.SYNC_CALL)
    public ProductSynchronizeResponseDto synchronize(ProductSynchronizeRequestDto requestDto) {
        return null;
    }

    /**
     * http://api.cangyibao.com/doc/?docs=api%E6%96%87%E6%A1%A3%E8%AF%B4%E6%98%8E/2-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E/%E5%BA%93%E5%AD%98%E6%9F%A5%E8%AF%A2%E6%8E%A5%E5%8F%A3%EF%BC%88%E5%A4%9A%E5%95%86%E5%93%81%EF%BC%89
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "inventory.query", callType = CallTypeEnum.SYNC_CALL)
    public InventoryQueryResponseDto inventoryQuery(InventoryQueryRequestDto requestDto) {
        return null;
    }
}
