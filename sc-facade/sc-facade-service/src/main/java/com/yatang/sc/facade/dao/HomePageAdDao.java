package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.HomePageAdPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author tangqi
 * @create 2017-11-17 10:14
 * @desc 首页查询
 **/
public interface HomePageAdDao {
    HomePageAdPo queryHomePage(@Param("companyId") String companyId);
}
