package com.example.quiz_9.vo;

import java.time.LocalDate;
import java.util.Map;

public class StatisticsRes extends BasicRes {

	private String quizName;

	private LocalDate startDate;

	private LocalDate endDate;

	Map<Integer, Map<String, Integer>> countMap;

	public StatisticsRes() {
		super();
	}

	public StatisticsRes(int statusCode, String massage) {
		super(statusCode, massage);
	}

	public StatisticsRes(int statusCode, String massage, String quizName, LocalDate startDate, LocalDate endDate,
			Map<Integer, Map<String, Integer>> countMap) {
		super(statusCode, massage);
		this.quizName = quizName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.countMap = countMap;
	}

	public String getQuizName() {
		return quizName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Map<Integer, Map<String, Integer>> getCountMap() {
		return countMap;
	}

}
