package com.nuriggum.nuriframe.common.response.entity;

import org.springframework.data.domain.Page;

//@Getter
//@Setter
public class PageResult<T> extends CommonResult {
    private Page<T> page;

	public void setPage(Page<T> page2) {
		// TODO Auto-generated method stub
		this.page = page2;
	}

	public Page<T> getPage() {
		// TODO Auto-generated method stub
		return this.page;
	}
}