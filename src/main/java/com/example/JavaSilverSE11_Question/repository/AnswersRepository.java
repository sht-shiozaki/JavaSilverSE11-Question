package com.example.JavaSilverSE11_Question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.JavaSilverSE11_Question.entity.Answers;

public interface AnswersRepository extends JpaRepository<Answers, String> {
    @Query("SELECT a.answer FROM Answers a WHERE a.qID = :qId")
    String findByAnswer(@Param("qId") String qId);
}