package com.yatang.sc.facade.dubboservice.impl;

import com.yatang.sc.facade.dubboservice.ProdPriceChangeWriteDubboService;
import com.yatang.sc.facade.service.ProdPriceChangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 商品价格(进价,售价)变动 dubbo写操作service实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 11:22
 * @版本: v1.0
 */
@Slf4j
@Service("prodPriceChangeWriteDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPriceChangeWriteDubboServiceImpl implements ProdPriceChangeWriteDubboService {


    private final ProdPriceChangeService priceChangeService;
}
