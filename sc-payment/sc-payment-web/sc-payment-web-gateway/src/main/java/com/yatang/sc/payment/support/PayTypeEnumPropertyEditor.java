package com.yatang.sc.payment.support;

import com.yatang.sc.payment.enums.PayType;

import java.beans.PropertyEditorSupport;

/**
 * Created by yuwei on 2017/7/13.
 */
public class PayTypeEnumPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        setValue(PayType.parse(text));
    }
}
