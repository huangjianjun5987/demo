package com.yatang.sc.order.split;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.order.split.enums.SplitOrderFromType;
import com.yatang.sc.order.split.enums.SubOrderType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplitOrderRequestDto implements Serializable {

    /**
     * 父订单号
     */
    private String parentOrderId;
    /**
     * 拆单分组 NOTE:订单商品行需保证所有被分组，否则水平拆单中会出现拆单后商品行缺失
     */
    private List<SplitOrderSubGroupDto> groups;
    /**
     * 拆单来源
     */
    private SplitOrderFromType fromType;

    public SplitOrderRequestDto() {
        groups = new ArrayList<>();
    }

    public SplitOrderSubGroupDto getGroupByType(SubOrderType pSubOrderType) {
        if (pSubOrderType == null) {
            return null;
        }
        for (SplitOrderSubGroupDto splitOrderSubGroupDto : getGroups()) {
            if (pSubOrderType.equals(splitOrderSubGroupDto.getSubOrderType())) {
                return splitOrderSubGroupDto;
            }
        }
        return null;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static SplitOrderRequestDto fromJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, SplitOrderRequestDto.class);
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String pParentOrderId) {
        parentOrderId = pParentOrderId;
    }

    public List<SplitOrderSubGroupDto> getGroups() {
        return groups;
    }

    public SplitOrderFromType getFromType() {
        return fromType;
    }

    public void setFromType(SplitOrderFromType pFromType) {
        fromType = pFromType;
    }
}
