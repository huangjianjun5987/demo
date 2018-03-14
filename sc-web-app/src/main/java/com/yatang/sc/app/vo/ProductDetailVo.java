package com.yatang.sc.app.vo;

import com.yatang.sc.app.vo.prod.AttributeVo;
import com.yatang.sc.app.vo.prod.ProdSellPriceInfoVo;
import com.yatang.sc.facade.dto.BundleProductScDto;
import com.yatang.sc.facade.dto.ServiceCommitmentsDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @描述: 商品详情
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/7 10:35
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ProductDetailVo implements Serializable {

    private static final long serialVersionUID = -2252347271529050430L;
    // 主键ID
    private String id;
    // 商品名称
    private String name;

    // 销售名称
    private String saleName;

    // 最小销售单位
    private String minUnit;

    // 标准包装单位参考值，例如：箱*盒*个'
    private String unitExplanation;

    // 标准包装规格参考值，例如：1*2*6',
    private String packingSpecifications;

    // 最小销售单位id
    private String minUnitId;
    // 更新或者新建商品来源 ：平台 or 门店
    private String updateSource;

    // 动态属性
    private List<AttributeVo>			attributes;

    // 图片信息
    private List<ImageVo> images;

    /**
     * 销售价格
     */
    private ProdSellPriceInfoVo prodSellPriceInfo;

    /**
     * 服务承诺
     */
    private List<ServiceCommitmentsDto> ServiceCommitments;
    /**
     * 销售库存量
     */
    private Long stock;

    // 是否整箱销售：0-否；1-是
    private Integer							sellFullCase;

    // 整箱单位
    private String							fullCaseUnit;

    // 整箱单位Id
    private String							fullCaseUnitId;

    /**
     * 建议零售价  yinyuxin
     */
    private BigDecimal                 suggestPrice;

    /******************************套餐商品字段 yinyuxin********************************/

    // 商品类型:1常规商品 2打包商品 3附属商品 4称重商品 5加工称重品 6加工非称重品 7优惠券商品

    private String 						productType;

    // 打包商品明细
    private List<BundleProductScDto> bundleProducts;

    //箱规 拼接仅用于app端显示使用  yinyuxin
    private String CNT;

    //起订量 拼接仅用于app端显示使用  yinyuxin
    private String miniNumber;

    //送货类型：0:直送，1:配送 yinyuxin
    private Integer logisticsModel;


    public String getId() {
        return id==""?null:id;
    }

    public String getName() {
        return name==""?null:name;
    }

    public String getSaleName() {
        return saleName==""?null:saleName;
    }

    public String getMinUnit() {
        return minUnit==""?null:minUnit;
    }

    public String getUnitExplanation() {
        return unitExplanation==""?null:unitExplanation;
    }

    public String getPackingSpecifications() {
        return packingSpecifications==""?null:packingSpecifications;
    }

    public String getMinUnitId() {
        return minUnitId==""?null:minUnitId;
    }

    public String getUpdateSource() {
        return updateSource==""?null:updateSource;
    }



}
