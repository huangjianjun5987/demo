package com.yatang.sc.operation.vo.ag;

import com.yatang.xc.mbd.biz.org.sc.dto.StoreAreaGroupDto;

import java.io.Serializable;
import java.util.List;

public class StoreAreaGroupResultVo implements Serializable{


	private static final long serialVersionUID = -1689080704516363479L;

	private int						pageNum;
	private int						pageSize;
	private int						total;
	private List<StoreAreaGroupDto> data;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() { return total; }

	public void setTotal(int total) { this.total = total; }

	public List<StoreAreaGroupDto> getData() {
		return data;
	}

	public void setData(List<StoreAreaGroupDto> data) {
		this.data = data;
	}
}
