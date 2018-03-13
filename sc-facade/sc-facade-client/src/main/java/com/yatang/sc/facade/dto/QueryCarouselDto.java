package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tangqi
 * @create 2017-11-23 10:33
 * @desc
 **/
@Getter
@Setter
public class QueryCarouselDto {
    private String companyId;
    private int areaType;//1:banner;2:floor;3:carousel;4:quick-nav
}
