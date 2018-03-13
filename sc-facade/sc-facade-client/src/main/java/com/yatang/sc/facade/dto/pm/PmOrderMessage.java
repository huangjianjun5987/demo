package com.yatang.sc.facade.dto.pm;

import com.yatang.sc.facade.enums.PmOrderMessageType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 供应商直销 定义
 *
 * @author yangshuang
 * @version v1.0
 */
@Getter
@Setter
public class PmOrderMessage implements Serializable {

    private static final long serialVersionUID = 4594793010879733712L;
    private PmOrderMessageType mssageType;

    private String orderId;

    private String body;


}
