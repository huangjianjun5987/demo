package com.yatang.sc.payment.vo;

import java.util.Map;

/**
 * Created by yuwei on 2017/7/10.
 */
public class ValidatorResult {
    private Map<String, String> mErrorMsg;

    public boolean isHasErrors() {
        if (mErrorMsg == null || mErrorMsg.size() == 0) {
            return false;
        }
        return true;
    }

    public Map<String, String> getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(Map<String, String> pErrorMsg) {
        mErrorMsg = pErrorMsg;
    }
}
