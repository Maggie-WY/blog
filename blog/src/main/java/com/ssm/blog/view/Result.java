package com.ssm.blog.view;

import java.util.HashMap;
import java.util.Map;

public class Result {
	
	private String code;	
	
	private String message;
	
	private Map<String,Object> date = new HashMap<String,Object>();
	
	public Result() { }
	
	public Result(String code,String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getDate() {
		return date;
	}

	public void setDate(Map<String, Object> date) {
		this.date = date;
	}
	

}
