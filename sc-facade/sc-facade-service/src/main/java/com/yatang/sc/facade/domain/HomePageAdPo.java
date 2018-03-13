package com.yatang.sc.facade.domain;

/**
 * @author tangqi
 * @create 2017-11-17 9:49
 * @desc 公司对应的app首页表
 **/
public class HomePageAdPo {

    //首页ID
    private int id;
    //公司ID
    private String companyId;
    //该公司是否使用全国页面，0：独立运营，即使用自己的定制页面；1：使用总部运营
    private int isUsingNation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getIsUsingNation() {
        return isUsingNation;
    }

    public void setIsUsingNation(int isUsingNation) {
        this.isUsingNation = isUsingNation;
    }
}
