package com.yatang.sc.glink.service.kidd;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.im.ImAdjuestmentParamentDto;
import com.yatang.sc.glink.dto.product.CommodityDto;
import com.yatang.sc.glink.dto.product.CommodityReqDto;
import com.yatang.sc.kidd.dto.im.ImAdjustmentResultDto;
import org.springframework.stereotype.Service;

@Service
public class GlinkProductProxyService {

    @KiddCaller(providerType = InterfaceProviderTypeEnum.GLINK, method = "g2matrix.commodities.creategoods", callType = CallTypeEnum.SYNC_CALL)
    public GlinkResponseDto<CommodityReqDto> createOrUpdateCommodity(CommodityDto requestDto) {
        return null;
    }

    /**
     * 根据传入参数查询仓库即时库存信息
     *
     * @param imAdjuestmentParamentDto
     * @return
     */
    @KiddCaller(method = "g2matrix.commodities.getJustintimeStock", callType = CallTypeEnum.SYNC_CALL)
    public GlinkResponseDto<ImAdjustmentResultDto> inventoryQuery(ImAdjuestmentParamentDto imAdjuestmentParamentDto) {
        return null;
    }
}
