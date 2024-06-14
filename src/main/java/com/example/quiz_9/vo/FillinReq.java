package com.example.quiz_9.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FillinReq {

	@JsonProperty("quiz_id")
	private int quizid;

	private String name;

	private String phone;

	private String email;

	private int age;

	@JsonProperty("fillin_list")
	private List<Fillin> fillinList;

	public FillinReq() {
		super();
	}

	public FillinReq(int quizid, String name, String phone, String email, int age, List<Fillin> fillinList) {
		super();
		this.quizid = quizid;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinList = fillinList;
	}

	public int getQuizid() {
		return quizid;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public List<Fillin> getFillinList() {
		return fillinList;
	}

}
