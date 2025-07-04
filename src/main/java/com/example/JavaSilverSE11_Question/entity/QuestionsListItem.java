package com.example.JavaSilverSE11_Question.entity;

import java.util.List;

import com.example.JavaSilverSE11_Question.dto.ChoicesDTO;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "question_list_items")
public class QuestionsListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 各リストアイテムID
    private Long id;

    @Column(nullable = false) // questionID
    private String questionId;

    @Column(nullable = false) // 問題文
    private String question;

    @Transient
    @Column(nullable = false) // 選択文（複数）
    private List<ChoicesDTO> choicesText;

    @Column(nullable = false) // 解答（,区切り）
    private String answer;

    @Column(nullable = false) // ファイル名（,区切り）
    private String fileName;

    @ManyToOne // 親子関係
    @JoinColumn(name = "question_list_id") // FK QuestionsList.idに紐付け
    private QuestionsList questionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoicesDTO> getChoicesText() {
        return choicesText;
    }

    public void setChoicesText(List<ChoicesDTO> choicesText) {
        this.choicesText = choicesText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public QuestionsList getQuestionList() {
        return questionList;
    }

    public void setQuestionList(QuestionsList questionList) {
        this.questionList = questionList;
    }
}
