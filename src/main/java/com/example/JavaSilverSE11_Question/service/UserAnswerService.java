package com.example.JavaSilverSE11_Question.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaSilverSE11_Question.dto.UserAnswerDTO;
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
    public List<UserAnswerDTO> getResult(HttpSession session, String userId) {
        List<UserAnswer> userAnswerList = userAnswerRepository.findByUserId(userId);
        List<Answers> answerList = answersRepository.findAll();
        int resultCount = 0;

        // 結果判定（正誤判定 & フラグ更新）
        for (UserAnswer us : userAnswerList) {
            for (Answers a : answerList) {
                if (us.getQuestionId().equals(a.getQuestionID())) {
                    // choice_id: "s2-A,s2B" → ["A", "B"]
                    List<String> userChoices = Arrays.stream(
                            us.getChoiceId() != null ? us.getChoiceId().split(",") : new String[0]) // split(","):"s2-1,s2-2"を["s2-1","s2-2"]
                            .map(s -> s.contains("-") ? s.split("-")[1] : s) // "-"の後ろの数字を取り出す
                            .collect(Collectors.toList());

                    // answer: "A,E" → ["A", "E"]
                    List<String> answers = Arrays.asList(
                            a.getAnswer() != null ? a.getAnswer().split(",") : new String[0]);

                    // 完全一致の比較（順不同 ["A", "E"]と["E", "A"] ⇒ true）
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

        List<UserAnswerDTO> dtoList = new ArrayList<>();
        for (UserAnswer us : userAnswerList) {
            // "s2-A" → "A" 変換
            String charChoices = Arrays.stream(
                    us.getChoiceId() != null ? us.getChoiceId().split(",") : new String[0])
                    .map(s -> s.contains("-") ? s.split("-")[1] : s) // "s2-A" → "A"
                    .collect(Collectors.joining(",")); // List<String> → "A,B"

            // 対応する正答を取得
            String answer = "";
            for (Answers a : answerList) {
                if (us.getQuestionId().equals(a.getQuestionID())) {
                    answer = a.getAnswer();
                    break;
                }
            }

            UserAnswerDTO dto = new UserAnswerDTO(charChoices, answer, us.isResult(), us.getNo());
            dtoList.add(dto);
        }
        int Accuracy = resultCount * 100 / 80;
        session.setAttribute("Accuracy", Accuracy); // 正答率
        session.setAttribute("resultCount", resultCount); // 正解数
        dtoList.sort(Comparator.comparing(UserAnswerDTO::getNo)); // ソート：No昇順
        return dtoList;
    }

    public void setCheckFlag(String userId, int qNo, boolean checked) {
        UserAnswer ua = userAnswerRepository.findByUserIdAndNo(userId, qNo);
        if (ua != null) {
            ua.setChecked(checked); // ← Entityに `checked` フィールド追加しておくこと
            userAnswerRepository.save(ua);
        }
    }

    public Map<Integer, Boolean> getAnsweredMap(String userId) {
        List<UserAnswer> list = userAnswerRepository.findByUserId(userId);
        Map<Integer, Boolean> map = new HashMap<>();
        for (UserAnswer ua : list) {
            map.put(ua.getNo(), ua.getChoiceId() != null && !ua.getChoiceId().isEmpty());
        }
        return map;
    }

    public Map<Integer, Boolean> getCheckedMap(String userId) {
        List<UserAnswer> list = userAnswerRepository.findByUserId(userId);
        Map<Integer, Boolean> map = new HashMap<>();
        for (UserAnswer ua : list) {
            map.put(ua.getNo(), ua.isChecked()); // ← boolean isChecked()
        }
        return map;
    }
}
