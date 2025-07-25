package com.example.JavaSilverSE11_Question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// userの解答用紙
@Entity
@Table(name = "answers") // ← テーブル名に合わせて変更
public class Answers {

    @Id
    @Column(name = "ano", nullable = false)
    private String aNo; // answerNo

    @Column(nullable = false) // 正解（,区切り）
    private String answer;

    @Column(nullable = false) // questionID
    private String qID;

    // --- getter/setter ---
    public String getAnswerNo() {
        return aNo;
    }

    public void setAnswerNo(String aNo) {
        this.aNo = aNo;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionID() {
        return qID;
    }

    public void setQuestionID(String qID) {
        this.qID = qID;
    }
}