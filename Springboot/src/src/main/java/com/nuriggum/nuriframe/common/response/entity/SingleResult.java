package com.nuriggum.nuriframe.common.response.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    private T data;

	public void setData(T data2) {
		// TODO Auto-generated method stub
		this.data = data2;
	}

	public T getData() {
		// TODO Auto-generated method stub
		return this.data;
	}
	
}