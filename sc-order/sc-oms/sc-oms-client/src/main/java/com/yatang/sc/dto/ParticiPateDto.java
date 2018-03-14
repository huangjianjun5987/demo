package com.yatang.sc.dto;

import com.yatang.sc.common.PageResult;

import java.io.Serializable;

public class ParticiPateDto implements Serializable {

    private static final long serialVersionUID = 3269616048204587192L;

    private String promotionName;

    private PageResult<ParticipateDataDto> participateDataDtoPageResult;

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public PageResult<ParticipateDataDto> getParticipateDataDtoPageResult() {
        return participateDataDtoPageResult;
    }

    public void setParticipateDataDtoPageResult(PageResult<ParticipateDataDto> participateDataDtoPageResult) {
        this.participateDataDtoPageResult = participateDataDtoPageResult;
    }


}
