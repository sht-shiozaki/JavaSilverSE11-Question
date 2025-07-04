package com.example.JavaSilverSE11_Question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.JavaSilverSE11_Question.entity.UserAnswer;

import jakarta.transaction.Transactional;

public interface QuestionAnswerRepository extends JpaRepository<UserAnswer, Long> {

    // 二日前の招待コードを削除
    @Transactional
    @Modifying
    @Query("DELETE FROM UserAnswer  u WHERE u.userId = :userId")
    void deleteUserAnswer(@Param("userId") String userId);

}
