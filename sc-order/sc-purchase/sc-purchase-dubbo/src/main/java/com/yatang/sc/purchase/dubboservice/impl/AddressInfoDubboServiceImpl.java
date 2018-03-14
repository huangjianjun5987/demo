package com.yatang.sc.purchase.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.purchase.dto.AddressDto;
import com.yatang.sc.purchase.dubboservice.AddressInfoDubboService;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.region.dto.RegionDTO;
import com.yatang.xc.mbd.biz.region.dubboservice.RegionDubboService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liusongjie on 2017/7/13.
 */
@Service("addressInfoDubboService")
public class AddressInfoDubboServiceImpl implements AddressInfoDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RegionDubboService regionDubboService;

    @Override
    public Response<List<AddressDto>> queryAddressInfoByFranchiseeId(String franchiseeId) {
        log.info("start queryAddressInfoByFranchiseeId.");
        if(StringUtils.isEmpty(franchiseeId)){
            log.info("queryAddressInfoByFranchiseeId franchiseeId can't be empty.");
        }
        Response<List<AddressDto>> res = new Response<List<AddressDto>>();
        Response<List<StoreDto>> franchiseeDtos = organizationService.queryStoresByFranchiseeId(franchiseeId);
        if(franchiseeDtos==null||!franchiseeDtos.isSuccess()||CollectionUtils.isEmpty(franchiseeDtos.getResultObject())){
            log.info("queryAddressInfoByFranchiseeId can't find store franchiseeId:{} ",franchiseeId);
            res.setSuccess(false);
            res.setErrorMessage("没有获取到门店信息！");
            return res;
        }

        List<AddressDto> addressDtoList = convertToAddressDto(franchiseeDtos.getResultObject());
        if(CollectionUtils.isEmpty(addressDtoList)){
            log.info("queryAddressInfoByFranchiseeId convertToAddressDto failed.");
            res.setSuccess(false);
            res.setErrorMessage("地址信息转换出错！");
            return res;
        }
        res.setSuccess(true);
        res.setCode(CommonsEnum.RESPONSE_200.getCode());
        res.setResultObject(addressDtoList);
        res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return res;
    }

    private List<AddressDto> convertToAddressDto(List<StoreDto> storeDtos) {
        Response<List<AddressDto>> res = new Response<List<AddressDto>>();
        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        for (StoreDto storeDto : storeDtos) {
            AddressDto addressDto = new AddressDto();
            List<String> codes = new ArrayList<String>();

            String proviceId = storeDto.getProvinceId();
            String cityId = storeDto.getCityId();
            String districtId = storeDto.getDistrictId();
            codes.add(proviceId);
            codes.add(cityId);
            codes.add(districtId);
            Response<List<RegionDTO>> regionResp = regionDubboService.selectRegionListByMultiCode(codes);
            if (regionResp == null || !regionResp.isSuccess() || CollectionUtils.isEmpty(regionResp.getResultObject())) {
                log.warn("convertToAddressDto failed proviceId:{}, cityId:{}, districtId:{}", proviceId, cityId, districtId);
                return null;
            }

            for (RegionDTO regionDTO : regionResp.getResultObject()) {
                if (proviceId.equals(regionDTO.getCode())) {
                    addressDto.setProvince(regionDTO.getRegionName());
                } else if (cityId.equals(regionDTO.getCode())) {
                    addressDto.setCity(regionDTO.getRegionName());
                } else if (districtId.equals(regionDTO.getCode())) {
                    addressDto.setDistrict(regionDTO.getRegionName());
                }
            }
            addressDto.setDetailAddress(storeDto.getAddress());
            addressDto.setConsigneeName(storeDto.getContact());
            String phoneNumber = storeDto.getMobilePhone();
            addressDto.setPhoneNumber(phoneNumber);
            addressDtos.add(addressDto);
        }

        return addressDtos;
    }
}
