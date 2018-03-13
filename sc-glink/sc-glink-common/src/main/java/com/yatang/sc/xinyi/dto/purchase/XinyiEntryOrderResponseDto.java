package com.yatang.sc.xinyi.dto.purchase;

import com.yatang.sc.xinyi.dto.ResponseDto;

import java.io.Serializable;

/**
 * @描述:推送采购订单给心怡响应信息
 * @类名:XinyiResponseEntryOrderDto
 * @作者: lvheping
 * @创建时间: 2017/9/25 16:10
 * @版本: v1.0
 */

public class XinyiEntryOrderResponseDto extends ResponseDto implements Serializable {
    private static final long serialVersionUID = 389588033017381868L;
    private String entryOrderId;//仓储系统入库单编码

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setEntryOrderId(String entryOrderId) {
        this.entryOrderId = entryOrderId;
    }

    @Override
    public String toString() {
        return "XinyiEntryOrderResponseDto{" +
                "entryOrderId='" + entryOrderId + '\'' +
                '}';
    }
}
