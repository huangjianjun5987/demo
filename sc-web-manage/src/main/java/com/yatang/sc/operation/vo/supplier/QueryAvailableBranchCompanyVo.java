package com.yatang.sc.operation.vo.supplier;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 接收查询可用分公司信息参数（仅用于新增及修改供应商地点）
 * @作者: tankejia
 * @创建时间: 2017/8/8-15:13 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class QueryAvailableBranchCompanyVo implements Serializable {

    private static final long serialVersionUID = 3117857537665578945L;

    /**
     * 子公司Id
     * */
    private String              branchCompanyId;

    /**
     * 子公司名称
     * */
    private String              branchCompanyName;

    /**
     * 供应商地点基本信息表id
     * */
    private Integer             id;

    /**
     * 供应商地点对应供应商主表id
     * */
    private String              parentId;

}
