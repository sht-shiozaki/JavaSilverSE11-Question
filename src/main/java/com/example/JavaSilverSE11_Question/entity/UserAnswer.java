package com.example.JavaSilverSE11_Question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// userの解答用紙
@Entity
@Table(name = "user_answers") // ← テーブル名に合わせて変更
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // インクルメント
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false) // 問題ID
    private Long questionId;

    @Column(nullable = false) // 解答
    private Long choiceId;

    @Column(nullable = false) // 回答済みか
    private boolean answered; // 初期値 false（Javaの仕様）

    // --- getter/setter ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}