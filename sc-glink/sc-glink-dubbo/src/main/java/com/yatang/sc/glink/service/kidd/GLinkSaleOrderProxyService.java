package com.yatang.sc.glink.service.kidd;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.glink.dto.CancelAllOrdersDto;
import com.yatang.sc.glink.dto.CancelAllOrdersRepDto;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.saleOrder.SaleOrderDto;
import com.yatang.sc.glink.dto.saleOrder.SelectOrderCodeOKDto;
import com.yatang.sc.glink.dto.saleOrder.SelectOrderCodeOKResultDto;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:50
 * @版本: v1.0
 */
@Service("gLinkSaleOrderProxyService")
public class GLinkSaleOrderProxyService {

    /**
     * 新增销售订单
     * @param saleOrderDto
     * @return
     */
    @KiddCaller(method = "g2matrix.saleorder.syncInsertOrders", callType = CallTypeEnum.SYNC_CALL)
    public GlinkResponseDto<String> add(SaleOrderDto saleOrderDto) {
        return null;
    }

    /**
     * 查询销售订单
     * @param selectOrderCodeOKDto
     * @return
     */
    @KiddCaller(method = "g2matrix.saleorder.selectordercodeOK", callType = CallTypeEnum.SYNC_CALL)
    public GlinkResponseDto<SelectOrderCodeOKResultDto> select(SelectOrderCodeOKDto selectOrderCodeOKDto) {
        return null;
    }

}
