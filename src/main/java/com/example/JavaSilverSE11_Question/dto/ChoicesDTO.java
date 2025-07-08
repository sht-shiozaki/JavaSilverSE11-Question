package com.example.JavaSilverSE11_Question.dto;

// CcoicesのsNoとtextを紐づけするクラス
public class ChoicesDTO {
    private String sNo;
    private String text;

    public ChoicesDTO(String sNo, String text) {
        this.sNo = sNo;
        this.text = text.replace("\\n", "\n");
    }

    // @Override
    // public String toString() {
    // return this.text; // 表示したいフィールド名
    // }

    // getter,setter
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

}
