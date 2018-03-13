package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.StaticPageInfoDao;
import com.yatang.sc.facade.domain.StaticPageInfoPo;
import com.yatang.sc.facade.domain.StaticPageQueyPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.StaticPageService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yatang.sc.facade.enums.PublicEnum.*;

/**
 * @描述:静态页管理service实现类
 * @类名:StaticPageServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/6/8 14:25
 * @版本: v1.0
 */
@Service
public class StaticPageServiceImpl implements StaticPageService {
	@Autowired
	private StaticPageInfoDao staticPageInfoDao;



	/**
	 * 根据传入的参数查询静态列表页
	 *
	 * @param staticPageQueyPo
	 * @return
	 */
	@Override
	public PageInfo<StaticPageInfoPo> findStaticPageList(StaticPageQueyPo staticPageQueyPo) {
		PageHelper.startPage(staticPageQueyPo.getPageNum(), staticPageQueyPo.getPageSize());
		List<StaticPageInfoPo> staticPageInfoPos = staticPageInfoDao.findStaticPageList(staticPageQueyPo);
		PageInfo<StaticPageInfoPo> pageInfo = new PageInfo<StaticPageInfoPo>(staticPageInfoPos);
		return pageInfo;
	}



	/**
	 * 添加静态页
	 *
	 * @param convert
	 * @return
	 */
	@Override
	public Boolean InsertStaticPage(StaticPageInfoPo convert) {
	    //设置创建时间
	    convert.setCreateTime(new Date());
		int i = staticPageInfoDao.insertSelective(convert);
		return i > 0;
	}

    /**
     * 修改静态页基本信息
     *
     * @param staticPageInfoPo
     * @return
     */
    @Override
    public Boolean updateStaticPageBase(StaticPageInfoPo staticPageInfoPo) {
        //设置修改时间
        staticPageInfoPo.setUpdateTime(new Date());
        int i = staticPageInfoDao.updateByPrimaryKeySelective(staticPageInfoPo);
        return i > 0;
    }

	/**
	 * 根据传入ID查询静态页信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public StaticPageInfoPo getStaticPageById(Integer id) {
		StaticPageInfoPo staticPageInfoPo = staticPageInfoDao.selectByPrimaryKey(id);
		return staticPageInfoPo;
	}
}
