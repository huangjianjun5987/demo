package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProdPurchaseInfoLogPo implements Serializable {
    private static final long serialVersionUID = -7153624767577522904L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 采购价格ID
     */
    private String priceId;

    /**
     * 采购价格
     */
    private BigDecimal purchasePrice;

    /**
     * 操作
     */
    private String operate;

    /**
     * 操作时间
     */
    private Date operateDate;

    /**
     * 操作用户id
     */
    private String operateUserId;
}