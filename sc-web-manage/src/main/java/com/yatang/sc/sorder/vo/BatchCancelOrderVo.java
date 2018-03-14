package com.yatang.sc.sorder.vo;

import java.io.Serializable;
import java.util.Arrays;

public class BatchCancelOrderVo implements Serializable {
    private String[] ids;
    private String remark;

    public String[] getIds() {
        return ids;
    }
    public void setIds(String[] ids) {
        this.ids = ids;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BatchCancelOrderVo{" +
                "ids=" + Arrays.toString(ids) +
                ", remark='" + remark + '\'' +
                '}';
    }
}
