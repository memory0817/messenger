package com.kh.messenger.sample;

import java.io.Serializable;

//객체 직렬화시 implements Serializable만 추가
public class LoginInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1602194437036152977L;
	String id;
	String pw;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	
}



