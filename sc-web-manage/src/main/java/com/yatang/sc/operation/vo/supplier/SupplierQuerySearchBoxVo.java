package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.yatang.sc.facade.common.BaseDto;

@Getter
@Setter
public class SupplierQuerySearchBoxVo extends BaseDto implements Serializable {

    private static final long serialVersionUID = 6572539906421235633L;

    private String pId;


    private String orgId;//

    private String condition;

    private Boolean isContainsHeadBranchCompany = false;//是否包含总公司编码(默认为不包含)







}
