package com.example.JavaSilverSE11_Question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 問題の解答選択文
@Entity
@Table(name = "choices") // ← テーブル名に合わせて変更
public class Choices {

    @Id
    @Column(nullable = false) // selectNo
    private String sNo;

    @Column(nullable = false) // 選択文の内容
    private String text;

    @Column(nullable = false) // questionID
    private String qID;

    // --- getter/setter ---
    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }
}
