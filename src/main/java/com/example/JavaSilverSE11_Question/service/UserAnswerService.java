package com.example.JavaSilverSE11_Question.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaSilverSE11_Question.entity.UserAnswer;
import com.example.JavaSilverSE11_Question.repository.UserAnswerRepository;

@Service
public class UserAnswerService {

    @Autowired
    private UserAnswerRepository UserAnswerRepository;

    public List<UserAnswer> userAnswer(String userId) {
        UserAnswerRepository.deleteUserAnswer(userId); // userAnswerのデータをリセット
        List<UserAnswer> userAnswerList = UserAnswerRepository.findAll(); // 全問題を取得
        return userAnswerList;
    }

    public void setUserAnswer(List<String> selectedChoices, int no, String userId) {
        if (selectedChoices != null) {
            String joinedChoices = String.join(",", selectedChoices); // selectedChoicesのリストを,区切りの文字列に変換
            System.out.println(joinedChoices);
            UserAnswerRepository.setUserAnswer(joinedChoices, no, userId);
        }
    }

    public List<String> getSelectedChoices(String userId, int nextNo) {
        UserAnswer answer = UserAnswerRepository.findByUserIdAndNo(userId, nextNo);
        if (answer != null && answer.getChoiceId() != null) {
            // System.err.println(answer);
            return Arrays.asList(answer.getChoiceId().split(",")); // ← カンマ区切りを分割
        }
        return Collections.emptyList();
    }
}
