package com.yatang.sc.facade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yatang.sc.facade.dao.ProdSellInfoLogDao;
import com.yatang.sc.facade.domain.ProdSellInfoLogPo;
import com.yatang.sc.facade.service.ProdSellInfoLogService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/11/3 14:13
 * @version: v1.0
 */
@Service("prodSellInfoLogService")
@Transactional
public class ProdSellInfoLogServiceImpl implements ProdSellInfoLogService {

	private static  final Logger log= LoggerFactory.getLogger(ProdSellInfoLogServiceImpl.class);

	@Autowired
	private ProdSellInfoLogDao prodSellInfoLogDao;

	@Override
	public Boolean insertLog(ProdSellInfoLogPo prodSellInfoLogPo) {
		try {
			return prodSellInfoLogDao.insertSelective(prodSellInfoLogPo)==1;
		} catch (Exception e) {
			//不能影响业务，所以在这里处理异常
			log.info("记录销售关系操作日志失败,参数:"+ JSONObject.toJSONString(prodSellInfoLogPo)+"errorInfo:"+ ExceptionUtils.getFullStackTrace(e));
			return false;
		}
	}
}
