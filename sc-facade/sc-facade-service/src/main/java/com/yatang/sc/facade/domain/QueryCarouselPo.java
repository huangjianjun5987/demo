package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tangqi
 * @create 2017-11-23 10:36
 * @desc
 **/
@Getter
@Setter
public class QueryCarouselPo {
    private String companyId;
    private int areaType;//1:banner;2:floor;3:carousel;4:quick-nav
}
