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
    private QuestionAnswerRepository QARepository;

    public List<UserAnswer> userAnswer(String userId) {
        QARepository.deleteUserAnswer(userId); // userAnswerのデータをリセット

        List<Question> allQuestions = questionRepository.findAll(); // 全問題を取得

        // 3. 空のUserAnswerリストを作成
        List<UserAnswer> newAnswers = new ArrayList<>();
        for (Question q : allQuestions) {
            UserAnswer ua = new UserAnswer();
            ua.setUserId(userId);
            ua.setQuestionId(q.getId());
            ua.setAnswerText(null); // 解答欄は空
            ua.setAnsweredAt(null); // 未回答
            newAnswers.add(ua);
        }

        // 4. 一括保存
        return QARepository.saveAll(newAnswers);
        return QARepository.getUserAnswer(userId); // userAnswerのカラリストを取得
    }

}
