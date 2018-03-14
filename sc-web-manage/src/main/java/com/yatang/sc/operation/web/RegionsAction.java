package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.operation.vo.LagerRegionVo;
import com.yatang.xc.mbd.biz.region.dto.LagerRegionDTO;
import com.yatang.xc.mbd.biz.region.dto.RegionDTO;
import com.yatang.xc.mbd.biz.region.dubboservice.RegionDubboService;
import com.yatang.xc.mbd.web.system.vo.RegionVO;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;
import java.util.Map;

/**
 * @描述:区域的action
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/5/25 14:33
 * @版本: v1.0
 */

@Log4j
@RequestMapping(value = "/sc/region")
@RestController
public class RegionsAction {
    //国家的区域code
    private final static String CODE = "100000";
    @Autowired
    private RegionDubboService regionDubboService;
    /**
     * 查询所有的省
     * @return
     */
    @RequestMapping(value = "/queryAllProvince",method = RequestMethod.GET)
    public Response<List<RegionVO>> queryAllProvince() {
        Response<List<RegionVO>> response= new Response<>();
        Response<List<RegionDTO>> listResponse = regionDubboService.selectRegionByParentCode(CODE);
        if (!CommonsEnum.RESPONSE_200.getCode().equals(listResponse.getCode())) {
            return BeanConvertUtils.convert(listResponse, Response.class);
        }
        List<RegionDTO> regionDtoList = listResponse.getResultObject();
        List<RegionVO> provinceList = BeanConvertUtils.convertList(regionDtoList, RegionVO.class);
        response.setResultObject(provinceList);
        response.setCode(String.valueOf(CommonsEnum.RESPONSE_200.getCode()));
        response.setSuccess(true);
        return response;
    }
    /**
     *
     * 省市县三级联动 根据code查询
     * @param code
     * @return
     */
    @RequestMapping(value = "/queryRegionByCode",method = RequestMethod.GET)
    public  Response<List<RegionVO>>  queryRegionByCode(String code) {
        Response<List<RegionVO>> response=new Response<>();
        //根据code调用接口
        Response<List<RegionDTO>> regionResponse = regionDubboService.selectRegionByParentCode(code);
        if (!CommonsEnum.RESPONSE_200.getCode().equals(regionResponse.getCode())) {
            return BeanConvertUtils.convert(regionResponse, Response.class);
        }
        List<RegionDTO> regionDtoList = regionResponse.getResultObject();
        List<RegionVO> provinceList = BeanConvertUtils.convertList(regionDtoList, RegionVO.class);
        response.setResultObject(provinceList);
        response.setCode(String.valueOf(CommonsEnum.RESPONSE_200.getCode()));
        response.setSuccess(true);
        return response;
    }


    /**
     * @Description: 查询供应商管理列表
     * @author yipeng
     * @date 2017年06月07日11:23:52
     */
    @RequestMapping(value = "/queryAllLargerRegionProvince",method = RequestMethod.GET)
    public Response<List<LagerRegionVo>> queryAllLargerRegionProvince() {
        Response<List<LagerRegionVo>> response= new Response<>();

        Response<List<RegionDTO>> regionResponse = regionDubboService.selectRegionByParentCode(CODE);
        if (!regionResponse.isSuccess()) {
            return BeanConvertUtils.convert(regionResponse, Response.class);
        }

        List<RegionDTO> regions = regionResponse.getResultObject();

        Map<String, LagerRegionVo> tempLargerRegions = Maps.newLinkedHashMap();
        Response<List<LagerRegionDTO>> lagerRegionResponse;
        for (RegionDTO region : regions) {
            lagerRegionResponse = regionDubboService.getLagerRegionList(region.getCode());
            if (!lagerRegionResponse.isSuccess()) {
                return BeanConvertUtils.convert(regionResponse, Response.class);
            }

            List<LagerRegionDTO> lagerRegions = lagerRegionResponse.getResultObject();
            if (CollectionUtils.isEmpty(lagerRegions)) {
                continue;
            }

            LagerRegionDTO lagerRegion = lagerRegions.get(0);

            LagerRegionVo.RegionVo regionVo = BeanConvertUtils.convert(region, LagerRegionVo.RegionVo.class);
            // 设置区域
            Response<List<RegionDTO>> cityRegionResponse = regionDubboService.selectRegionByParentCode(regionVo.getCode());
            if (!cityRegionResponse.isSuccess()) {
                return BeanConvertUtils.convert(cityRegionResponse, Response.class);
            }
            regionVo.setRegions(BeanConvertUtils.convertList(cityRegionResponse.getResultObject(), LagerRegionVo.RegionVo.class));


            LagerRegionVo lagerRegionVo = tempLargerRegions.get(lagerRegion.getLargeCode());
            if (lagerRegionVo == null) {
                lagerRegionVo = BeanConvertUtils.convert(lagerRegion, LagerRegionVo.class);
                lagerRegionVo.setRegions(Lists.<LagerRegionVo.RegionVo>newArrayList());
                tempLargerRegions.put(lagerRegion.getLargeCode(), lagerRegionVo);
            }
            lagerRegionVo.getRegions().add(regionVo);
        }

        response.setResultObject(Lists.newArrayList(tempLargerRegions.values()));
        response.setSuccess(true);
        return response;
    }
}
