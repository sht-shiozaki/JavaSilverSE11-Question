package com.example.JavaSilverSE11_Question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.JavaSilverSE11_Question.entity.UserAnswer;
import com.example.JavaSilverSE11_Question.entity.UserAnswerId;

import jakarta.transaction.Transactional;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, UserAnswerId> { // 主キーが複合主キーなので、そのクラスを定義
    // userの解答を削除
    @Transactional
    @Modifying
    @Query("DELETE FROM UserAnswer u WHERE u.userId = :userId")
    void deleteUserAnswer(@Param("userId") String userId);

    @Transactional
    @Modifying // UPDATEには必要
    @Query("UPDATE UserAnswer u SET u.choiceId = :joinedChoices, u.answered = true WHERE u.userId = :userId AND u.no = :no")
    void setUserAnswer(
            @Param("joinedChoices") String joinedChoices,
            @Param("no") int no,
            @Param("userId") String userId);

    @Query("SELECT u FROM UserAnswer u WHERE u.userId = :userId AND u.no = :nextNo")
    UserAnswer findByUserIdAndNo(@Param("userId") String userId, @Param("nextNo") int nextNo);
}
