package com.nuriggum.nuriframe.common.response.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    private boolean success;

    private int code;

    private String msg;

	public void setSuccess(boolean b) {
		// TODO Auto-generated method stub
		this.success = b;
	}

	public void setCode(int code2) {
		// TODO Auto-generated method stub
		this.code = code2;
	}

	public void setMsg(String msg2) {
		this.msg = msg2;
	}

	public boolean getSuccess() {
		// TODO Auto-generated method stub
		return this.success;
	}

	public int getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}
}

