package com.yatang.sc.xinyi.dto.im;

import com.busi.kidd.serialize.xml.LevinDateConverter;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 心怡库存调整ReqDto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/28 10:34
 * @版本: v1.0
 */
@Getter
@Setter
public class XinYiInventoryAdjustNoticeRequestDto implements Serializable {
    private static final long serialVersionUID = 7957610716391070051L;


    private Integer totalPage;//总页数

    private Integer currentPage;//当前页

    private Integer pageSize;//每页记录的条数

    private String warehouseCode;//仓库编码

    private String checkOrderCode;//盘点单编码

    private String checkOrderId;//仓储系统的盘点单编码

    private String ownerCode;//货主编码

    @XStreamConverter(value = LevinDateConverter.class, strings = {"yyyy-MM-dd HH:mm:ss"})
    private Date checkTime;//盘点时间

    private String outBizCode;//

    private String remark;//备注  這個備註很重要

    private List<XinYiInventoryAdjustNoticeItemDto> items;


}
