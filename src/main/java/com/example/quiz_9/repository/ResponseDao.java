package com.example.quiz_9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz_9.entity.Response;

@Repository
public interface ResponseDao extends JpaRepository<Response, Integer> {
	
	public boolean existsByQuizidAndPhone(int quizid , String phone);
	
	public List<Response> findByQuizid(int quizid);
}
