package com.example.JavaSilverSE11_Question.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "question_lists")
public class QuestionsList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 問題リストID
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false) // 作成日
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "questionList", cascade = CascadeType.ALL, orphanRemoval = true) // 親子関係。mappedBy：キー
    private List<QuestionsListItem> items;

    // getter/setter
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<QuestionsListItem> getItems() {
        return items;
    }

    public void setItems(List<QuestionsListItem> items) {
        this.items = items;
    }
}
