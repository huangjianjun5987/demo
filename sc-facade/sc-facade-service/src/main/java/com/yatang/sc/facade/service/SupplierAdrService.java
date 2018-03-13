package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ProdSpAdrSearchBoxPo;
import com.yatang.sc.facade.domain.ProdSpAdrSearchParamPo;
import com.yatang.sc.facade.domain.QueryAvailableBranchCompanyPo;
import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SpAdrContactPo;
import com.yatang.sc.facade.domain.SpSearchBoxPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;

import java.util.List;

/**
 * 供应商地点服务类
 * 
 * @author huangjianjun
 */
public interface SupplierAdrService {
	/**
	 * @Description: 查询供应商地点详情
	 * @author huangjianjun
	 * @date 2017年7月17日 下午11:02:59
	 * @param id
	 *            供应商主表ID
	 */
	SupplierAdrInfoPo queryById(Integer id);



	/**
	 * @Description: 查询供应商地点主表信息
	 * @author tankejia
	 * @date 2017/7/28- 18:03
	 * @param id
	 */
	SupplierAdrInfoPo queryMainAddById(Integer id);



	/**
	 * @Description: 新增供应商地点主表信息
	 * @author huangjianjun
	 * @date 2017年7月17日 下午11:02:59
	 */
	void insertSupplierAdrInfo(SupplierAdrInfoPo record);



	/**
	 * @Description: 新增供应商地点主表信息
	 * @author huangjianjun
	 * @date 2017年7月17日 下午11:02:59
	 */
	void insertSpAdrBasicInfo(SpAdrBasicPo record);



	/**
	 * @Description: 新增供应商地点主表信息
	 * @author huangjianjun
	 * @date 2017年7月17日 下午11:02:59
	 */
	void insertSpAdrContactInfo(SpAdrContactPo record);




	/**
	 * @Description: 根据供应商地点编码或名称或供应商ID查询供应商地点分页列表组件
	 * @author huangjianjun
	 * @date 2017年7月20日上午11:20:18
	 * @param pId
	 * @param condition
	 */
	PageInfo<SpSearchBoxPo> supplierSearchBox(String pId,List<String> branchCompanyIds, String condition, Integer pageNum, Integer pageSize);

	/**
	 *
	 * <method description>
	 *
	 * @param supplierAdrInfoPo
	 * @return
	 */
	boolean updateByParentId(SupplierAdrInfoPo supplierAdrInfoPo);



	/**
	 * 
	 * <method description>单表更新
	 *
	 * @param supplierAdrInfoPo
	 * @return
	 */
	boolean updateByPrimaryKeySelective(SupplierAdrInfoPo supplierAdrInfoPo);



	/**
	 * @Description: 新增供应商地点
	 * @author tankejia
	 * @date 2017/7/19- 15:32
	 * @param supplierAdrInfoPo
	 */
	Integer insertSupplierAddInfo(SupplierAdrInfoPo supplierAdrInfoPo);



	/**
	 * @Description: 修改供应商地点
	 * @author tankejia
	 * @date 2017/7/19- 15:34
	 * @param supplierAdrInfoPo
	 */
	boolean updateSupplierAddInfo(SupplierAdrInfoPo supplierAdrInfoPo);



	/**
	 * @Description: 根据供应商信息id查询供应商地点列表
	 * @author tankejia
	 * @date 2017/8/4- 15:39
	 * @param parentId
	 */
	List<SupplierAdrInfoPo> querySupplierAddInfoListByParentId(String parentId);



	boolean updateSupplierAdrByParentId(SupplierAdrInfoPo record);



	/**
	 * @Description: 查询不可用的分公司id集合
	 * @author tankejia
	 * @date 2017/8/8- 19:07
	 * @param record
	 */
	List<String> queryUselessOrgId(QueryAvailableBranchCompanyPo record);



	/**
	 * @Description: 校验供应商地点编号
	 * @author tankejia
	 * @date 2017/8/10- 18:02
	 * @param record
	 */
	List<SpAdrBasicPo> checkSupplierAddNo(SpAdrBasicPo record);

	/**
	 * @Description: 根据supplierAddressNo查询供应商地点
	 * @author kangdong
	 * @date 2017年12月7日 下午16:10:25
	 * @param supplierAddressNo
	 */
    SupplierAdrInfoPo queryByProviderNo(String supplierAddressNo);

	/**
	 * @Description: 商品地点关系查询供应商及供应商地点值清单（查询子公司对应的供应商及供应商地点）
	 * @author tankejia
	 * @date 2018/1/19- 14:07
	 * @param paramPo
	 */
	PageInfo<ProdSpAdrSearchBoxPo> prodSpAdrSearchBox(ProdSpAdrSearchParamPo paramPo);
}
