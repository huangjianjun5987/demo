package com.yatang.sc.sorder.res;

import com.busi.common.resp.Response;
import org.apache.commons.lang3.StringUtils;

public class ActionResponse {
    
    public static <T> Response<T> wrap(Response<T> pResponse) {
        return wrap(pResponse, null);
    }

    public static <T> Response<T> wrap(Response<T> pResponse, String customMsg) {
        if (pResponse == null) {
            return null;
        }
        if (!pResponse.isSuccess()) {
            pResponse.setCode("5001");
            if (!StringUtils.isEmpty(customMsg)) {
                pResponse.setErrorMessage(customMsg);
            }
        }
        return pResponse;
    }
}
