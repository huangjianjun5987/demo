package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdSpAdrSearchBoxPo;
import com.yatang.sc.facade.domain.ProdSpAdrSearchParamPo;
import com.yatang.sc.facade.domain.QueryAvailableBranchCompanyPo;
import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SpSearchBoxPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpAdrBasicDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SpAdrBasicPo record);

    SpAdrBasicPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpAdrBasicPo record);
    
    /**
     * @Description: 查询最大编码
     * @author huangjianjun
     * @date 2017年7月19日下午3:59:27
     */
    String queryMaxNo();
    
    /**
     * @Description: 根据供应商地点编码或名称查询供应商地点列表
     * @author huangjianjun
     * @date 2017年7月19日下午3:59:27
     */
    List<SpSearchBoxPo> supplierAdrSearchBox(@Param("pId")String pId,@Param("branchCompanyIds")List<String> branchCompanyIds,@Param("condition")String condition);

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
     * @Description: 商品地点关系查询供应商及供应商地点值清单（查询子公司对应的供应商及供应商地点）
     * @author tankejia
     * @date 2018/1/19- 14:07
     * @param paramPo
     */
    List<ProdSpAdrSearchBoxPo> prodSpAdrSearchBox(ProdSpAdrSearchParamPo paramPo);


}