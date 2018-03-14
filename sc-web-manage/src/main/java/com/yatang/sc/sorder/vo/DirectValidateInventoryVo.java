package com.yatang.sc.sorder.vo;

import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class DirectValidateInventoryVo implements Serializable {

    private static final long serialVersionUID = -2201577333687798392L;

    @NotBlank(message = "{msg.notEmpty.message}",groups = GroupOne.class)
    private String loc;                     //仓库编码

    @NotBlank(message = "{msg.notEmpty.message}",groups = GroupOne.class)
    private String branchCompanyId;        //分公司id

    private List<ValidateProductVo> products;    //需要验证的商品id和数量类

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public List<ValidateProductVo> getProducts() {
        return products;
    }

    public void setProducts(List<ValidateProductVo> products) {
        this.products = products;
    }
}
