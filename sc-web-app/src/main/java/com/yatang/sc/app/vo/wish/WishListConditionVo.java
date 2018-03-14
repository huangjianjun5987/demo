package com.yatang.sc.app.vo.wish;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class WishListConditionVo implements Serializable{
    private static final long serialVersionUID = -93521828220750884L;

    @Min(value = 1, message = "{msg.min.message}")
    @NotNull(message = "{msg.notEmpty.message}")
    private int page;

    @Min(value = 1, message = "{msg.min.message}")
    @NotNull(message = "{msg.notEmpty.message}")
    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
