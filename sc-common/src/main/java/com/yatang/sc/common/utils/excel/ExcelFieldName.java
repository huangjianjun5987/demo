package com.yatang.sc.common.utils.excel;

import java.lang.annotation.*;

/**
 * Created by xiangyonghong on 2017/9/5.
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelFieldName {
    String name();
}
