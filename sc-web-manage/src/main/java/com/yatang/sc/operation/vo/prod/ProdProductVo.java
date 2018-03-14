package com.yatang.sc.operation.vo.prod;

import com.yatang.xc.mbd.biz.prod.dubboservice.dto.InternationalCodeDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @描述: 商品信息prodVo类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/1 20:58
 * @版本: v1.0
 */
@Setter
@Getter
public class ProdProductVo implements Serializable {

    private static final long serialVersionUID = -516163213849565798L;
    private String id;                        // 商品id
    private String name;                    // 商品名称
    private Integer invoiceLimit;            // 是否限制开增值税发票(0,不限制,1.限制)

    private String firstLevelCategoryName;//一级商品分类名

    private String secondLevelCategoryName;//二级商品分类名

    private String thirdLevelCategoryName;//三级商品分类名

    private String fourthLevelCategoryName; // 第四级类别名称

    private String saleName;

    private BigDecimal inputTaxRate;            // 进项税率

    private BigDecimal deductibleTaxRate;        // 抵扣税率

    private String brandName;                // 品牌名

    private Integer supplyChainStatus;// 供应链状态：1-草稿 2-生效 3-暂停使用（全国性下架） 4-停止使用

    private Integer purchaseInsideNumber;//采购内装数
    private Integer salesInsideNumber;//销售内装数

    private String productCode;  // 商品编码

    // 国际码信息
    private List<InternationalCodeDto> internationalCodes;

    private String minUnit;//最小销售单位

    // 采购指导价
    private BigDecimal purchasePrice;

    // 出货指导价
    private BigDecimal suggestPrice;

    // 承诺发货时间
    private Integer							deliveryDay;

    // 是否整箱销售：0-否；1-是
    private Integer							sellFullCase;

    // 整箱单位
    private String							fullCaseUnit;

    // 整箱单位Id
    private String							fullCaseUnitId;


    public void setSaleName(String saleName) {//当销售名称为空时修改为商品名称
        this.saleName = saleName == null ? name : saleName;
    }


}
