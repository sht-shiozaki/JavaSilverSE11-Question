package com.example.JavaSilverSE11_Question.repository;

import java.util.Optional;
import com.example.JavaSilverSE11_Question.entity.QuestionsList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsListRepository extends JpaRepository<QuestionsList, Long> {
    Optional<QuestionsList> findByUserId(String userId);
}