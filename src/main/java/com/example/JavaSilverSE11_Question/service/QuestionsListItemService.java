package com.example.JavaSilverSE11_Question.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaSilverSE11_Question.entity.QuestionsListItem;
import com.example.JavaSilverSE11_Question.dto.ChoicesDTO;
import com.example.JavaSilverSE11_Question.entity.Answers;
import com.example.JavaSilverSE11_Question.entity.Questions;
import com.example.JavaSilverSE11_Question.entity.QuestionsList;
import com.example.JavaSilverSE11_Question.repository.AnswersRepository;
import com.example.JavaSilverSE11_Question.repository.ChoiceRepository;
import com.example.JavaSilverSE11_Question.repository.QuestionRepository;

@Service
public class QuestionsListItemService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private AnswersRepository answersRepository;

    // 問題リストの詳細作成
    public List<QuestionsListItem> createQuestionListItems(QuestionsList questionList) {
        List<Questions> questions = questionRepository.findAll(); // 問題文取得
        Collections.shuffle(questions); // ← 順番をランダムに変更
        List<QuestionsListItem> itemList = new ArrayList<>(); // 問題文リストの格納先

        int no = 1;
        for (Questions q : questions) {
            String qId = q.getqID(); // Questionsのgetter使用してpIDを取得

            // 選択肢を取得し List<String> で格納
            List<ChoicesDTO> choices = choiceRepository.findByQid(qId)
                    .stream() // List<Choices> などのコレクションを Java のストリームに変換
                    .map(c -> new ChoicesDTO(c.getsNo(), c.getText())) // sNoとtextをペアで格納⇒DTO定義
                    .sorted(Comparator.comparing(ChoicesDTO::getsNo)) // getsNo 昇順ソート
                    .collect(Collectors.toList()); // .collect():どうまとめるか定義、Collectors.toList: Stream の結果を List にまとめる

            // 解答を取得
            String answer = answersRepository.findByAnswer(qId);

            // 1問に対しての情報を格納
            QuestionsListItem item = new QuestionsListItem();
            item.setQuestionId(qId); // questionID
            item.setQuestion(q.getText().replace("\\n", "\n")); // 問題文
            item.setChoicesText(choices); // 選択文（複数）
            item.setAnswer(answer); // 解答（,区切り）
            item.setFileName(q.getFileName()); // ファイル名（,区切り）
            item.setNo(no++);

            // questionList（親）があるならセットしてください
            item.setQuestionList(questionList);
            itemList.add(item);
        }
        return itemList;
    }
}