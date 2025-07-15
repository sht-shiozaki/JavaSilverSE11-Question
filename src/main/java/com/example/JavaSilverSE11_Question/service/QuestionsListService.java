package com.example.JavaSilverSE11_Question.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JavaSilverSE11_Question.entity.QuestionsList;
import com.example.JavaSilverSE11_Question.entity.QuestionsListItem;
import com.example.JavaSilverSE11_Question.entity.UserAnswer;
import com.example.JavaSilverSE11_Question.repository.QuestionsListRepository;
import com.example.JavaSilverSE11_Question.repository.UserAnswerRepository;

@Service
public class QuestionsListService {

    @Autowired
    private QuestionsListItemService QLIService;

    @Autowired
    private QuestionsListRepository questionsListRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    // 問題リスト作成
    public QuestionsList createQuestionList(String userId) {
        QuestionsList questionsList = new QuestionsList();
        questionsList.setUserId(userId);
        questionsList.setCreateDate(LocalDateTime.now());
        List<QuestionsListItem> items = QLIService.createQuestionListItems(questionsList);
        questionsList.setItems(items);

        // 問題文の保存
        questionsListRepository.save(questionsList); // Repository側でもentityをimportすること
        // 回答に問題番号と答えを保存
        List<UserAnswer> updatedAnswers = new ArrayList<>();
        for (QuestionsListItem item : items) {
            UserAnswer ua = new UserAnswer();
            ua.setUserId(userId);
            ua.setQuestionId(item.getQuestionId());
            ua.setNo(item.getNo());
            ua.setAnswered(false);
            ua.setChoiceId(null); // 初期状態で未選択

            updatedAnswers.add(ua);
        }

        userAnswerRepository.saveAll(updatedAnswers); // DB保存
        return questionsList;
    }

    public List setFilesPath(QuestionsList questionsList, int qNo) throws IOException {
        List<String> files = new ArrayList<>();

        String fileName = questionsList.getItems().stream()
                .filter(item -> item.getNo() != null && item.getNo().equals(qNo))
                .map(QuestionsListItem::getFileName)
                .findFirst()
                .orElse(""); // Nullなら""

        if (fileName != null && !fileName.isEmpty()) {
            String[] splitFiles = fileName.split(","); // カンマ区切りで分割してリストに格納
            for (String f : splitFiles) {
                f = f.trim(); // 前後の空白も削除
                Path path = Paths.get("C:/SHTproject/JavaSilverSE11-Question/local_questions/" + f + ".java");
                String content = Files.readString(path);
                files.add(content);
            }
        }
        return files;
    }

    public QuestionsListItem setDisplayQuestion(List<QuestionsListItem> questionsList, int qNo) {
        for (QuestionsListItem item : questionsList) {
            if (item.getNo() == qNo) {
                return item;
            }
        }
        throw new NoSuchElementException("No matching question found for No: " + qNo);
    }

    public List<String> getAnswers(QuestionsList questionsList, int qNo) {
        List<String> answers = new ArrayList<>();

        String ans = questionsList.getItems().stream()
                .filter(item -> item.getNo() != null && item.getNo().equals(qNo))
                .map(QuestionsListItem::getAnswer)
                .findFirst()
                .orElse(""); // Nullなら""

        if (ans != null && !ans.isEmpty()) {
            String[] ANS = ans.split(","); // カンマ区切りで分割してリストに格納
            for (String a : ANS) {
                answers.add(a);
            }
        }
        return answers;
    }
}