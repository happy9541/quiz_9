package com.example.quiz_9.vo;

import java.util.List;

public class FeedbackRes extends BasicRes {

	private List<Feedback> feedbackList;

	public FeedbackRes() {
		super();
	}

	public FeedbackRes(int statusCode, String massage) {
		super(statusCode, massage);
	}
	
	public FeedbackRes(int statusCode, String massage, List<Feedback> feedbackList) {
		super(statusCode, massage);
		this.feedbackList = feedbackList;
	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

}
