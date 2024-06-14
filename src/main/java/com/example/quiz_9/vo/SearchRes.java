package com.example.quiz_9.vo;

import java.util.List;

import com.example.quiz_9.entity.Quiz;

public class SearchRes extends BasicRes {

	private List<Quiz> quizList;

	public SearchRes() {
		super();
	}

	public SearchRes(int statusCode, String massage, List<Quiz> quizList) {
		super(statusCode, massage);
		this.quizList = quizList;
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}
	
	
}
