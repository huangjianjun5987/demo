package com.yatang.sc.sorder.vo.returnedOrder;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotEmpty;



/**
 * @描述: 换退货单
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/18 10:48
 * @版本: v1.0
 */
public class ReturnRequestReceiptVo implements Serializable {

    private static final long serialVersionUID = 5962179891013330924L;


    @Valid
    private ReturnRequestVo returnRequest;
    @Valid
    @NotEmpty(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private List<ReturnRequestItemVo> requestItems;

    public ReturnRequestVo getReturnRequest() {
        return returnRequest;
    }

    public void setReturnRequest(ReturnRequestVo returnRequest) {
        this.returnRequest = returnRequest;
    }

    public List<ReturnRequestItemVo> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<ReturnRequestItemVo> requestItems) {
        this.requestItems = requestItems;
    }
}
