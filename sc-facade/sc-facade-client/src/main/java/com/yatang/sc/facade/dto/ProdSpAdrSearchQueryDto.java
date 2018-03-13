package com.yatang.sc.facade.dto;

import com.yatang.sc.facade.common.BaseDto;

import java.io.Serializable;

/**
 * @描述: 商品地点关系查询供应商地点DTO
 * @作者: tankejia
 * @创建时间: 2018/1/18-14:52 .
 * @版本: 1.0 .
 */
public class ProdSpAdrSearchQueryDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -792994691552695504L;

    /**
     * 地点类型(1:子公司，2:区域组，3:门店)
     * */
    private Integer placeType;

    /**
     * 地点编号
     * */
    private String placeId;

    /**
     * 地点名称
     * */
    private String placeName;

    /**
     * 查询条件(供应商地点编号或者名称)
     * */
    private String condition;

    /**
     * 查询类型(1:查询供应商； 查询供应商地点时不传值)
     * */
    private Integer queryType;

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
}
