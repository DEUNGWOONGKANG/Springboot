package com.nuriggum.nuriframe.common.response.entity;

import java.util.List;

public class ListResult<T> extends CommonResult {
    private List<T> list;

	public void setList(List<T> list2) {
		// TODO Auto-generated method stub
		this.list = list2;
	}


	public List<T> getList() {
		// TODO Auto-generated method stub
		return this.list;
	}
}