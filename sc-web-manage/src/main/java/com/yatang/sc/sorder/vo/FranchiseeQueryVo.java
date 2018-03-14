package com.yatang.sc.sorder.vo;

import com.yatang.sc.operation.common.BaseVo;

import java.io.Serializable;

/**
 * Created by liusongjie on 2017/8/7.
 */
public class FranchiseeQueryVo extends BaseVo implements Serializable {
    private static final long serialVersionUID = -2391089555626788453L;

    // 查询参数
    private String				param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
