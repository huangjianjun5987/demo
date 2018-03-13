package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ProdPriceChangePo;
import com.yatang.sc.facade.domain.ProdPriceChangeQueryPo;

/**
 * @描述: 商品价格(进价,售价)变动 service实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 11:28
 * @版本: v1.0
 */
public interface ProdPriceChangeService {


    /**
     * 新增价格变动记录
     * @param priceChangePo
     * @return
     */
    boolean addProdPriceChange(ProdPriceChangePo  priceChangePo);


	/**
     *
    * <method description>分页查询列表
    * @author zhoubaiyun
    * @return
     */

	PageInfo<ProdPriceChangePo> selectProdPriceChangePoList(ProdPriceChangeQueryPo prodPriceChangeQueryPo);
}
