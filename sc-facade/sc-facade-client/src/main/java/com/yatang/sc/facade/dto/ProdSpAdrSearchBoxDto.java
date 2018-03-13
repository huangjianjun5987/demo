package com.yatang.sc.facade.dto;

import java.io.Serializable;

/**
 * @描述: 商品地点关系查询供应商地点组件
 * @作者: tankejia
 * @创建时间: 2018/1/18-14:43 .
 * @版本: 1.0 .
 */
public class ProdSpAdrSearchBoxDto implements Serializable {

    private static final long serialVersionUID = 7917090648680052410L;

    private String	spId;				// 供应商ID

    private String	spNo;				// 供应商编号

    private String	companyName;		// 供应商名称

    private Integer	spAdrId;			// 供应商地点ID

    private String	providerNo;			// 供应商地点编号

    private String	providerName;		// 供应商地点名称

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpNo() {
        return spNo;
    }

    public void setSpNo(String spNo) {
        this.spNo = spNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getSpAdrId() {
        return spAdrId;
    }

    public void setSpAdrId(Integer spAdrid) {
        this.spAdrId = spAdrid;
    }

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
