package com.example.JavaSilverSE11_Question.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.JavaSilverSE11_Question.entity.QuestionsList;
import com.example.JavaSilverSE11_Question.entity.QuestionsListItem;
import com.example.JavaSilverSE11_Question.repository.QuestionsListRepository;

@Service
public class QuestionsListService {

    @Autowired
    private QuestionsListItemService QLIService;

    @Autowired
    private QuestionsListRepository questionsListRepository;

    public QuestionsList createQuestionList(String userId) {
        QuestionsList questionsList = new QuestionsList();
        questionsList.setUserId(userId);
        questionsList.setCreateDate(LocalDateTime.now());
        List<QuestionsListItem> items = QLIService.createQuestionListItems(questionsList);
        questionsList.setItems(items);

        // 問題文の保存
        questionsListRepository.save(questionsList); // Repository側でもentityをimportすること
        return questionsList;
    }
}
