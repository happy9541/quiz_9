package com.example.quiz_9.vo;

import java.time.LocalDateTime;

public class Feedback {

	private String userName;

	private LocalDateTime fillinDateTime;

	private FeedbackDetall feedbackDetall;

	public Feedback() {
		super();
	}

	public Feedback(String userName, LocalDateTime fillinDateTime, FeedbackDetall feedbackDetall) {
		super();
		this.userName = userName;
		this.fillinDateTime = fillinDateTime;
		this.feedbackDetall = feedbackDetall;
	}

	public String getUserName() {
		return userName;
	}

	public LocalDateTime getFillinDateTime() {
		return fillinDateTime;
	}

	public FeedbackDetall getFeedbackDetall() {
		return feedbackDetall;
	}

}
