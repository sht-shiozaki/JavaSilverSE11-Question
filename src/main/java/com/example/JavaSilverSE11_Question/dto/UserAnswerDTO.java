package com.example.JavaSilverSE11_Question.dto;

public class UserAnswerDTO {
    private String charChoices; // 変換後の選択肢文字列（例："A,B"）
    private String answer; // 正解文字列（例："A,E"）
    private boolean result; // 正誤判定
    private Integer no; // 問題番号（No）

    // コンストラクタ
    public UserAnswerDTO(String charChoices, String answer, boolean result, Integer no) {
        this.charChoices = charChoices;
        this.answer = answer;
        this.result = result;
        this.no = no;
    }

    // getter/setter
    public String getCharChoices() {
        return charChoices;
    }

    public void setCharChoices(String charChoices) {
        this.charChoices = charChoices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}