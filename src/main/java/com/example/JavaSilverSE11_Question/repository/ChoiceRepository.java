package com.example.JavaSilverSE11_Question.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.JavaSilverSE11_Question.entity.Choices;

public interface ChoiceRepository extends JpaRepository<Choices, String> {
    // findBy[フィールド名] という命名で、自動的にクエリが生成されます⇒findBy + qID
    List<Choices> findByQid(String qId); // questionIDを基に取得
}