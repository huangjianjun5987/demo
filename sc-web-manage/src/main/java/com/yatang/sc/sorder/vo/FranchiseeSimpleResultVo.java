package com.yatang.sc.sorder.vo;

import java.io.Serializable;

/**
 * Created by liusongjie on 2017/8/7.
 */
public class FranchiseeSimpleResultVo implements Serializable{
    private static final long serialVersionUID = -7805965394524930607L;

    // 加盟商编码
    private String	franchiseeId;

    // 加盟商名称
    private String	franchiseeName;

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getFranchiseeName() {
        return franchiseeName;
    }

    public void setFranchiseeName(String franchiseeName) {
        this.franchiseeName = franchiseeName;
    }
}
