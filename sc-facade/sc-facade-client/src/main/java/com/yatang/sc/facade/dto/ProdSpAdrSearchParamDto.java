package com.yatang.sc.facade.dto;

import com.yatang.sc.facade.common.BaseDto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 商品地点关系查询供应商及供应商地点值清单查询参数
 * @作者: tankejia
 * @创建时间: 2018/1/19-14:20 .
 * @版本: 1.0 .
 */
public class ProdSpAdrSearchParamDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -3639425207579177251L;

    /**
     * 地点类型(1:子公司，2:区域组，3:门店)
     * */
    private Integer placeType;

    /**
     * 地点编号
     * */
    private String placeId;

    /**
     * 分公司id
     * */
    private List<String> branchCompanyIds;

    /**
     * 查询类型(1:查询供应商； 2：查询供应商地点)
     * */
    private Integer queryType;

    /**
     * 查询类型为2：查询供应商地点时需要传
     * */
    private String parentId;

    /**
     * 查询条件(查询类型为1时供应商编号或者名称，查询类型为2时供应商地点编号或名称)
     * */
    private String condition;

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

    public List<String> getBranchCompanyIds() {
        return branchCompanyIds;
    }

    public void setBranchCompanyIds(List<String> branchCompanyIds) {
        this.branchCompanyIds = branchCompanyIds;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
