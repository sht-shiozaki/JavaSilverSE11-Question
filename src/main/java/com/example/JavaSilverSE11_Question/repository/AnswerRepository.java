package com.example.JavaSilverSE11_Question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JavaSilverSE11_Question.entity.Answers;

public interface AnswerRepository extends JpaRepository<Answers, String> {
    // findBy[フィールド名] という命名で、自動的にクエリが生成されます⇒findBy + answer
    String findByAnswer(String answer); // questionIDを基に取得
}