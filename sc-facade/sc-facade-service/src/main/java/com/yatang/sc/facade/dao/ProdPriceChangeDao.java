package com.yatang.sc.facade.dao;


import java.util.List;

import com.yatang.sc.facade.domain.ProdPriceChangePo;
import com.yatang.sc.facade.domain.ProdPriceChangeQueryPo;

public interface ProdPriceChangeDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProdPriceChangePo record);

    int insertSelective(ProdPriceChangePo record);

    ProdPriceChangePo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdPriceChangePo record);

    int updateByPrimaryKey(ProdPriceChangePo record);



	/**
	 * 
	 * <method description>查询列表
	 * 
	 * @author zhoubaiyun
	 * @param prodPriceChangeQueryPo
	 * @return
	 */
	List<ProdPriceChangePo> selectProdPriceChangePoList(ProdPriceChangeQueryPo prodPriceChangeQueryPo);
}