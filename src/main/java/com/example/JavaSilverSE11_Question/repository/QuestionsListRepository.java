package com.example.JavaSilverSE11_Question.repository;

import java.util.List;
import java.util.Optional;
import com.example.JavaSilverSE11_Question.entity.QuestionsList;

import jakarta.transaction.Transactional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionsListRepository extends JpaRepository<QuestionsList, Long> {

    @Query("SELECT q.id FROM QuestionsList q WHERE q.userId = :userId ORDER BY q.createDate DESC")
    List<Long> findTop10IdsByUserId(@Param("userId") String userId, PageRequest pageable); // JPQLではLIMIT使えないため、Pageable.of(0,10)

    // 指定したID以外を削除
    @Transactional
    @Modifying
    @Query("DELETE FROM QuestionsList q WHERE q.userId = :userId AND q.id NOT IN :ids")
    void deleteByUserIdAndIdNotIn(@Param("userId") String userId, @Param("ids") List<Long> ids);

    Optional<QuestionsList> findByUserId(String userId);
}