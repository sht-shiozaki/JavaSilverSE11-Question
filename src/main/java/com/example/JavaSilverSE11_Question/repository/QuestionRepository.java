package com.example.JavaSilverSE11_Question.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.JavaSilverSE11_Question.entity.Questions;

public interface QuestionRepository extends JpaRepository<Questions, String> {
    List<Questions> findAll(); // 問題文を全件取得
}