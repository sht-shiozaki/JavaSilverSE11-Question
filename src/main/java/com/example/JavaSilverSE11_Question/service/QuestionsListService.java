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
import com.example.JavaSilverSE11_Question.repository.QuestionsListRepository;

@Service
public class QuestionsListService {

    @Autowired
    private QuestionsListItemService QLIService;

    @Autowired
    private QuestionsListRepository questionsListRepository;

    // 問題リスト作成
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

    public List setFilesPath(QuestionsList questionsList, int qNo) throws IOException {
        List<String> files = new ArrayList<>();

        System.out.println(questionsList.getItems());

        String fileName = questionsList.getItems().stream()
                .filter(item -> item.getNo() != null && item.getNo().equals(qNo))
                .map(QuestionsListItem::getFileName)
                .findFirst()
                .orElse(""); // Nullなら""

        if (fileName != null && !fileName.isEmpty()) {
            String[] splitFiles = fileName.split(","); // カンマ区切りで分割してリストに格納
            for (String f : splitFiles) {
                f = f.trim(); // 前後の空白も削除
                Path path = Paths.get("src/main/resources/static/file/" + f);
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
}