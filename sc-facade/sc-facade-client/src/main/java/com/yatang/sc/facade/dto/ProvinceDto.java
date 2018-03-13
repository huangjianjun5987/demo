package com.yatang.sc.facade.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商地点所属区域(省份)
 * @类名:ProvinceVo
 * @作者: kangdong
 * @创建时间: 2017/7/26 15:29
 * @版本: v1.0
 */
@Getter
@Setter
public class ProvinceDto implements Serializable{

    private static final long serialVersionUID = -4057223138636742037L;
    private String code;//区域code
    private String name;//区域名称
}
