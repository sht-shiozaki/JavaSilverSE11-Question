package com.example.JavaSilverSE11_Question.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaSilverSE11_Question.entity.Answers;
import com.example.JavaSilverSE11_Question.entity.UserAnswer;
import com.example.JavaSilverSE11_Question.repository.AnswersRepository;
import com.example.JavaSilverSE11_Question.repository.UserAnswerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private AnswersRepository answersRepository;

    // userの解答をリセット
    public List<UserAnswer> userAnswer(String userId) {
        userAnswerRepository.deleteUserAnswer(userId); // userAnswerのデータをリセット
        List<UserAnswer> userAnswerList = userAnswerRepository.findAll(); // 全問題を取得
        return userAnswerList;
    }

    // userの解答をDBに格納
    public void setUserAnswer(List<String> selectedChoices, int no, String userId) {
        if (selectedChoices != null) {
            String joinedChoices = String.join(",", selectedChoices); // selectedChoicesのリストを,区切りの文字列に変換
            userAnswerRepository.setUserAnswer(joinedChoices, no, userId);
        }
    }

    // 指定した問題Noの選択項目を文字列で取得
    public List<String> getSelectedChoices(String userId, int nextNo) {
        UserAnswer answer = userAnswerRepository.findByUserIdAndNo(userId, nextNo);
        if (answer != null && answer.getChoiceId() != null) {
            return Arrays.asList(answer.getChoiceId().split(",")); // ← カンマ区切りを分割
        }
        return Collections.emptyList();
    }

    // userの解答済みの件数
    public int getAnsweredCount(String userId) {
        int count = userAnswerRepository.countCorrectAnswers(userId);
        return count;
    }

    // 回答の判定
    public List<UserAnswer> getResult(HttpSession session, String userId) {
        List<UserAnswer> userAnswerList = userAnswerRepository.findByUserId(userId);
        List<Answers> answerList = answersRepository.findAll();
        int resultCount = 0;

        for (UserAnswer us : userAnswerList) {
            for (Answers a : answerList) {
                if (us.getQuestionId().equals(a.getQuestionID())) {
                    // choice_id: "s2-1,s2-2" → ["1", "2"]
                    List<String> userChoices = Arrays.stream(
                            us.getChoiceId() != null ? us.getChoiceId().split(",") : new String[0]) // split(",") :
                                                                                                    // "s2-1,s2-2"を["s2-1",
                                                                                                    // "s2-2"]
                            .map(s -> s.contains("-") ? s.split("-")[1] : s) // "-"の後ろの数字を取り出す
                            .collect(Collectors.toList());

                    // answer: "1,5" → ["1", "5"]
                    List<String> answers = Arrays.asList(
                            a.getAnswer() != null ? a.getAnswer().split(",") : new String[0]);

                    // 完全一致の比較（順不同 ["1", "5"]と["5", "1"] ⇒ true）
                    boolean result = new HashSet<>(userChoices).equals(new HashSet<>(answers)); // 順番関係なく中身が同じならtrue

                    if (result) {
                        resultCount++;
                        // メモリ上のオブジェクト(リスト)に反映
                        us.setResult(true);
                        // DBを更新
                        userAnswerRepository.updateByResult(userId, us.getQuestionId());
                    }
                }
            }
        }
        int Accuracy = resultCount * 100 / 80;
        session.setAttribute("Accuracy", Accuracy); // 正答率
        session.setAttribute("resultCount", resultCount); // 正解数
        return userAnswerList;
    }
}
