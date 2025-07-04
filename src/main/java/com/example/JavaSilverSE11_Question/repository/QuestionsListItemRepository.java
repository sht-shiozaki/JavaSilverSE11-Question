package com.example.JavaSilverSE11_Question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.JavaSilverSE11_Question.entity.QuestionsListItem;

public interface QuestionsListItemRepository extends JpaRepository<QuestionsListItem, Long> {
    // @Query("SELECT i FROM QuestionListItem i " +
    // "JOIN FETCH i.questionList ql " +
    // "JOIN FETCH Question q ON q.qId = i.questionId " +
    // "LEFT JOIN FETCH q.choices c " +
    // "LEFT JOIN FETCH q.answer a " +
    // "WHERE ql.userId = :userId")
    // List<QuestionsListItem> findFullQuestionListByUserId(@Param("userId") String
    // userId);
}