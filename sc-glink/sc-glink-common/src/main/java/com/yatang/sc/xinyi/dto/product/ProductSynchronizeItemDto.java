package com.yatang.sc.xinyi.dto.product;

import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 请求dto<br>
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class ProductSynchronizeItemDto implements java.io.Serializable {

    private String itemCode;//货主商品编码，sku编码

    private String itemName;//商品名称

    private String shortName;//商品简称

    private String projectCode;//所属项目，存在link

    private String barCode;//条形码，可多个，用分号（;）隔开

    private String itemType;//商品类型(ZC=正常商品,FX=分销商品,ZH=组合商品,ZP=赠品,BC=包材,HC=耗材,FL=辅料,XN=虚拟品,FS=附属品,CC=残次品,OTHER=其它)

    private String skuProperty;//商品属性

    private double length;//长

    private double height;//高

    private double width;//宽

    private double netWeight;//净重

    private double grossWeight;//毛重

    private String pcs;//箱规

    private String stockUnit;//商品计量单位

    private String brandCode;//品牌代码

    private String brandName;//品牌名称

    private String originAddress;//商品的原产地

    private String categoryId;//商品类别ID

    private String categoryName;//商品类别名称

    private String isShelfLifeMgmt;//是否需要保质期管理Y/N默认为N

    private int shelfLife;//保质期(小时)

    private int rejectLifecycle;//保质期禁收天数（天）

    private int lockupLifecycle;//保质期禁售天数

    private int adventLifecycle;//保质期临期预警天数

    private String batchCode;//批次代码

    private String isFragile;//是否易碎品,Y/N默认为N

    private String isValid;//是否有效Y/N默认为Y

    private String picUrl;//图片URL

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date updateTime;//更新时间yyyy-MM-dd HH:mm:ss

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date createTime;//创建时间yyyy-MM-dd HH:mm:ss

}
