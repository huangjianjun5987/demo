package com.yatang.sc.operation.vo.ag;

import com.yatang.xc.mbd.org.es.sc.dto.ScGroupStoreDto;

import java.io.Serializable;
import java.util.List;

public class ScGroupStoreResultVo implements Serializable {

	private static final long serialVersionUID = -804532612049093483L;

	private List<ScGroupStoreDto>   data;

	private int                    pageNum;

	private int					pageSize;

	// 总页数
	private int					total;



	public List<ScGroupStoreDto> getData() {
		return data;
	}

	public void setData(List<ScGroupStoreDto> data) {
		this.data = data;
	}

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
}
