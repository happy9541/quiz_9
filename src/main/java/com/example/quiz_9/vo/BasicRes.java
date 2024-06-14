package com.example.quiz_9.vo;

public class BasicRes {

	private int statusCode;

	private String massage;

//	public BasicRes(int code, String message) {
//	}
	

	public BasicRes() {
	}
	
	public BasicRes(int statusCode, String massage) {
	super();
	this.statusCode = statusCode;
	this.massage = massage;
}



	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

}
