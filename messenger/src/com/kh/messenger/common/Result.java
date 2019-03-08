package com.kh.messenger.common;

import java.io.Serializable;
import java.util.Vector;

//서버처리 결과물 담는 객체
public class Result extends Vector<Object> implements Serializable{

	
	private static final long serialVersionUID = 3262815877444752680L;
	
	public int status; // Vector객체에 저장될 데이터의 성격
	
	public Result() {
		super(1,1);
		
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
}
