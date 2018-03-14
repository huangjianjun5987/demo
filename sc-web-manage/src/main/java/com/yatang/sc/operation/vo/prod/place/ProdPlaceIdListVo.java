package com.yatang.sc.operation.vo.prod.place;

import java.util.List;

/*
*@Author tangqi
*@Date 2018/1/15 10:57
*@Desc
*/
public class ProdPlaceIdListVo {

    List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "ProdPlaceIdListVo{" +
                "ids=" + ids +
                '}';
    }
}
