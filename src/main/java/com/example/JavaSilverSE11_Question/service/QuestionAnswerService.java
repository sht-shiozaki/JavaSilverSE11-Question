package com.example.JavaSilverSE11_Question.service;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaSilverSE11_Question.entity.UserAnswer;
import com.example.JavaSilverSE11_Question.repository.QuestionAnswerRepository;

@Service
public class QuestionAnswerService {

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    public List<UserAnswer> userAnswer(String userId) {
        questionAnswerRepository.deleteUserAnswer(userId); // userAnswerのデータをリセット
        List<UserAnswer> userAnswerList = questionAnswerRepository.findAll(); // 全問題を取得
        return userAnswerList;
    }
}
