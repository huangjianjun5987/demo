package com.yatang.sc.glink.dto.saleOrder;

import java.io.Serializable;

/**
 * @描述: 发货人信息DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class SaleSendInfoDto  implements Serializable {

    private String shipper;//发货人
    private String forwardingOrg;//发货单位
    private String sPhone;//发货人手机

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getForwardingOrg() {
        return forwardingOrg;
    }

    public void setForwardingOrg(String forwardingOrg) {
        this.forwardingOrg = forwardingOrg;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }
}
