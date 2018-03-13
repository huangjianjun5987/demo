package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 批量处理商品采购关系启禁用状态的Po类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/17 15:39
 * @版本: v1.0
 */
@Getter
@Setter
public class ProPurchaseBatchChangeStatusPo implements Serializable {

    private static final long serialVersionUID = -2654733648237341467L;
    private List<String> productIdList;//商品的id的集合

    private String spAdrId;//供应商地点信息

    private Integer status;//采购关系的状态:0,,失效,1启用

    private String modifyUserId;// 修改人id

    private Date modifyDateTime;//修改时间

}
