package com.yatang.sc.operation.vo.im;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 商品库存相关信息查询条件
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/15 16:05
 * @版本: v1.0
 */
@Getter
@Setter
public class ItemInventoryQueryParamVo implements Serializable {

    private static final long serialVersionUID = 8735772026422383565L;
    private String productId;//商品id

    private String productCode;//商品编码

    private String logicWareHouseCode;//逻辑仓库code

    private String branchCompanyId;//分公司编号


}
