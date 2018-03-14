package com.yatang.sc.sorder.vo;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class SimpleProductPageVo implements Serializable {

    private static final long serialVersionUID = 1310830203830596849L;

    private List<SimpleProductVo>  simpleProductVos = new ArrayList<SimpleProductVo>();
    /** 总页数 */
    private int					totalPage;
    /** 当前页数 */
    private int					pageNum;
    /** 总记录数 */
    private int					totalCount;

    public List<SimpleProductVo> getSimpleProductVos() {
        return simpleProductVos;
    }

    public void setSimpleProductVos(List<SimpleProductVo> simpleProductVos) {
        this.simpleProductVos = simpleProductVos;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
