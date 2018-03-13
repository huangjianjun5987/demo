package com.yatang.sc.facade.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dao.SpAdrBasicDao;
import com.yatang.sc.facade.dao.SpAdrContactDao;
import com.yatang.sc.facade.dao.SpAdrDeliveryDao;
import com.yatang.sc.facade.dao.SupplierAdrInfoDao;
import com.yatang.sc.facade.domain.ProdSpAdrSearchBoxPo;
import com.yatang.sc.facade.domain.ProdSpAdrSearchParamPo;
import com.yatang.sc.facade.domain.QueryAvailableBranchCompanyPo;
import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SpAdrContactPo;
import com.yatang.sc.facade.domain.SpAdrDeliveryPo;
import com.yatang.sc.facade.domain.SpSearchBoxPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.enums.CommonEnum;
import com.yatang.sc.facade.service.SupplierAdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SupplierAdrServiceImpl implements SupplierAdrService {

    @Autowired
    private SupplierAdrInfoDao mainDao;
    @Autowired
    private SpAdrBasicDao basicDao;
    @Autowired
    private SpAdrContactDao contactDao;
    @Autowired
    private SpAdrDeliveryDao deliveryDao;


    @Override
    public SupplierAdrInfoPo queryById(Integer id) {
        return mainDao.selectByPrimaryKey(id);
    }


    @Override
    public SupplierAdrInfoPo queryMainAddById(Integer id) {
        return mainDao.queryMainAddById(id);
    }


    @Override
    public void insertSupplierAdrInfo(SupplierAdrInfoPo record) {
        mainDao.insertSelective(record);
    }


    @Override
    public void insertSpAdrBasicInfo(SpAdrBasicPo record) {
        basicDao.insertSelective(record);
    }


    @Override
    public void insertSpAdrContactInfo(SpAdrContactPo record) {
        contactDao.insertSelective(record);
    }



    @Override
    public PageInfo<SpSearchBoxPo> supplierSearchBox(String pId, List<String> branchCompanyIds, String condition, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<SpSearchBoxPo> list = basicDao.supplierAdrSearchBox(pId, branchCompanyIds, condition);
        PageInfo<SpSearchBoxPo> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }


    @Override
    public boolean updateByParentId(SupplierAdrInfoPo supplierAdrInfoPo) {
        return mainDao.updateByParentId(supplierAdrInfoPo) > 0;
    }

    @Override
    public boolean updateByPrimaryKeySelective(SupplierAdrInfoPo supplierAdrInfoPo) {
        return mainDao.updateByPrimaryKeySelective(supplierAdrInfoPo) > 0;
    }

    @Override
    public boolean updateSupplierAdrByParentId(SupplierAdrInfoPo record) {
        return mainDao.updateSupplierAdrByParentId(record) > 0;
    }

    @Override
    public List<String> queryUselessOrgId(QueryAvailableBranchCompanyPo record) {
        return basicDao.queryUselessOrgId(record);
    }

    @Override
    public List<SpAdrBasicPo> checkSupplierAddNo(SpAdrBasicPo record) {
        return basicDao.checkSupplierAddNo(record);
    }


    @Override
    public Integer insertSupplierAddInfo(SupplierAdrInfoPo supplierAdrInfoPo) {
        Integer id = -1;

        // 供应商地点基本信息
        SpAdrBasicPo spAdrBasicPo = supplierAdrInfoPo.getSpAdrBasic();
        Boolean flag1 = basicDao.insertSelective(spAdrBasicPo) >= 1;
        // 供应商地点联系信息
        SpAdrContactPo spAdrContactPo = supplierAdrInfoPo.getSpAdrContact();
        Boolean flag2 = contactDao.insertSelective(spAdrContactPo) >= 1;
        // 送货信息
        Set<SpAdrDeliveryPo> SpAdrDeliverySet = supplierAdrInfoPo.getSpAdrDeliverys();

        if (flag1 && flag2) {
            // 主表录入
            supplierAdrInfoPo.setBasicId(spAdrBasicPo.getId());
            supplierAdrInfoPo.setContId(spAdrContactPo.getId());
            // 根据提交类型设置主表状态
            if (supplierAdrInfoPo.getCommitType() != null) {
                if (CommonEnum.SUPPLIER_SUBMIT_ZERO.getCode().equals(supplierAdrInfoPo.getCommitType())) {
                    supplierAdrInfoPo.setStatus(CommonEnum.SUPPLIER_INFO_ZERO.getCode());
                } else {
                    supplierAdrInfoPo.setStatus(CommonEnum.SUPPLIER_INFO_ONE.getCode());
                }
            }

            Boolean flag3 = mainDao.insertSelective(supplierAdrInfoPo) >= 1;
            // 获取刚录入主表的数据的id
            id = supplierAdrInfoPo.getId();
            // 录入送货信息
            if (flag3) {
                for (SpAdrDeliveryPo deliveryPo : SpAdrDeliverySet) {
                    deliveryPo.setSpAdrId(id);
                    deliveryDao.insertSelective(deliveryPo);
                }
            }
        }
        return id;
    }


    @Override
    public boolean updateSupplierAddInfo(SupplierAdrInfoPo supplierAdrInfoPo) {
        Boolean flag = false;
        Boolean spAdrBasicFlag = false;
        Boolean spAdrContactFlag = false;
        Boolean spAdrDeliveryFlag = false;

        // 供应商地点基本信息
        if (supplierAdrInfoPo.getSpAdrBasic() != null) {
            SpAdrBasicPo basicPo = supplierAdrInfoPo.getSpAdrBasic();
            Integer originalBasicPoId = basicPo.getId();
            // 第一次修改非制单状态下
            if (!CommonEnum.SUPPLIER_SUBLIST_ONE.getCode().equals(basicPo.getStatus()) && supplierAdrInfoPo.getStatus() != 0) {
                basicPo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ONE.getCode());
                spAdrBasicFlag = basicDao.insertSelective(basicPo) >= 1;
                SpAdrBasicPo originalBasicPo = new SpAdrBasicPo();
                originalBasicPo.setId(originalBasicPoId);
                originalBasicPo.setModifyId(basicPo.getId());
                if (spAdrBasicFlag) {
                    spAdrBasicFlag = basicDao.updateByPrimaryKeySelective(originalBasicPo) >= 1;
                }
            } else {
                spAdrBasicFlag = basicDao.updateByPrimaryKeySelective(basicPo) >= 1;
            }
        }

        // 供应商地点联系信息
        if (supplierAdrInfoPo.getSpAdrContact() != null) {
            SpAdrContactPo contactPo = supplierAdrInfoPo.getSpAdrContact();
            Integer originalContactPoId = contactPo.getId();
            if (!CommonEnum.SUPPLIER_SUBLIST_ONE.getCode().equals(contactPo.getStatus()) && supplierAdrInfoPo.getStatus() != 0) {
                contactPo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ONE.getCode());
                spAdrContactFlag = contactDao.insertSelective(contactPo) >= 1;
                SpAdrContactPo originalContactPo = new SpAdrContactPo();
                originalContactPo.setId(originalContactPoId);
                originalContactPo.setModifyId(contactPo.getId());
                if (spAdrContactFlag) {
                    spAdrContactFlag = contactDao.updateByPrimaryKeySelective(originalContactPo) >= 1;
                }
            } else {
                spAdrContactFlag = contactDao.updateByPrimaryKeySelective(contactPo) >= 1;
            }
        }

        // 供应商地点送货信息
        Set<SpAdrDeliveryPo> deliverySet = supplierAdrInfoPo.getSpAdrDeliverys();
        if (deliverySet != null && !deliverySet.isEmpty()) {
            // 删除该供应商地点对应的所有送货信息
            spAdrDeliveryFlag = deliveryDao.deleteBySupplierAddId(supplierAdrInfoPo.getId()) >= 1;

            if (spAdrDeliveryFlag) {
                // 新增该供应商地点对应的送货信息
                for (SpAdrDeliveryPo deliveryPo : deliverySet) {
                    deliveryPo.setSpAdrId(supplierAdrInfoPo.getId());
                    spAdrDeliveryFlag = deliveryDao.insertSelective(deliveryPo) >= 1;
                }
            }
        }

        if (spAdrBasicFlag || spAdrContactFlag || spAdrDeliveryFlag) {
            SupplierAdrInfoPo po = mainDao.selectByPrimaryKey(supplierAdrInfoPo.getId());
            if (supplierAdrInfoPo.getCommitType() != null) {
                // 修改后保存为草稿
                if (CommonEnum.SUPPLIER_SUBMIT_ZERO.getCode().equals(supplierAdrInfoPo.getCommitType())) {
                    // 如果供应商地点状态为制单或者修改中则不需要改供应商地点状态
                    if (CommonEnum.SUPPLIER_INFO_ZERO.getCode().equals(supplierAdrInfoPo.getStatus())
                            || CommonEnum.SUPPLIER_INFO_FOUR.getCode().equals(supplierAdrInfoPo.getStatus())) {
                        po.setModifyUser(supplierAdrInfoPo.getModifyUser());
                        flag = mainDao.updateByPrimaryKeySelective(po) >= 1;
                    } else {
                        po.setStatus(CommonEnum.SUPPLIER_INFO_FOUR.getCode());
                        po.setModifyUser(supplierAdrInfoPo.getModifyUser());
                        flag = mainDao.updateByPrimaryKeySelective(po) >= 1;
                    }
                } else { // 修改后提交
                    po.setStatus(CommonEnum.SUPPLIER_INFO_ONE.getCode());
                    po.setModifyUser(supplierAdrInfoPo.getModifyUser());
                    flag = mainDao.updateByPrimaryKeySelective(po) >= 1;
                }
            }
        }
        return flag;
    }

    @Override
    public List<SupplierAdrInfoPo> querySupplierAddInfoListByParentId(String parentId) {
        return mainDao.querySupplierAddInfoListByParentId(parentId);
    }

    /**
     * @Description: 根据supplierAddressNo查询供应商地点
     * @author kangdong
     * @date 2017年12月7日 下午16:10:25
     * @param providerNo
     */
    @Override
    public SupplierAdrInfoPo queryByProviderNo(String providerNo) {
        return mainDao.queryByProviderNo(providerNo);
    }

    @Override
    public PageInfo<ProdSpAdrSearchBoxPo> prodSpAdrSearchBox(ProdSpAdrSearchParamPo paramPo) {
        PageHelper.startPage(paramPo.getPageNum(), paramPo.getPageSize());
        List<ProdSpAdrSearchBoxPo> list = basicDao.prodSpAdrSearchBox(paramPo);
        PageInfo<ProdSpAdrSearchBoxPo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
