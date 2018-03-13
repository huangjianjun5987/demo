package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ProdSellInfoImportPo;
import com.yatang.sc.facade.domain.ProdSellInfoImportsPo;
import com.yatang.sc.facade.domain.ProdSellPriceUpdateParamPo;

import java.util.List;

/**
 * @描述: 商品售价导入记录Service接口定义
 * @类名: ProdSellInfoImportService
 * @作者: kangdong
 * @创建时间: 2017/12/6 13:46
 * @版本: v1.0
 */
public interface ProdSellInfoImportService {

	/**
	 * 根据id查询商品售价导入记录详情
	 * @param id 记录
	 * @return
	 */
	ProdSellInfoImportPo selectByPrimaryKey(Long id);

	/**
	 * 保存商品售价导入记录表
	 * @param prodPurchaseInfoImportsPo
	 * @return
	 */
	Long insertSellImportList(ProdSellInfoImportsPo prodPurchaseInfoImportsPo);

	/**
	 * 根据条件查询商品售价导入记录列表
	 * @param paramPo
	 * @return
	 */
	PageInfo<ProdSellInfoImportPo> getProdSellInfoImportByParam(ProdSellPriceUpdateParamPo paramPo);

	/**
	 * 查询最新一条商品售价导入(调价单)
	 * @return
	 */
	ProdSellInfoImportsPo selectById(Long id);

	/**
	 * 根据id更新处理结果:0:错误;1:已验证;2:已提交
	 * @param prodSellInfoImportPo
	 * @return
	 */
	Boolean updateHandleResult(ProdSellInfoImportPo prodSellInfoImportPo);
}
