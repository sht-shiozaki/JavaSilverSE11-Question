package com.example.JavaSilverSE11_Question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// userの解答用紙
@Entity
@Table(name = "questions") // ← テーブル名に合わせて変更
public class Questions {

    @Id
    @Column(nullable = false) // questionID
    private String qID;

    @Column(nullable = false) // javaファイル名
    private String fileName;

    @Column(nullable = false) // 問題文
    private String text;

    @Column(nullable = false) // javaファイルのtrue/false
    private boolean file;

    // get,set
    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }
}