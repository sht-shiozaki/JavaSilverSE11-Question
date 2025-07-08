package com.example.JavaSilverSE11_Question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

// userの解答用紙
@Entity
@Table(name = "user_answers") // ← テーブル名に合わせて変更
@IdClass(UserAnswerId.class) // 複合主キークラスを定義
public class UserAnswer {

    @Id
    @Column(nullable = false)
    private String userId;

    @Id
    @Column(nullable = false) // 問題ID
    private String questionId;

    @Column() // 解答
    private String choiceId;

    @Column(nullable = false) // 回答済みか
    private boolean answered; // 初期値 false（Javaの仕様）

    @Column(name = "no") // 表示No（）
    private Integer no;

    // --- getter/setter ---

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}