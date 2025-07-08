// タスク管理の一覧表示、追加、削除、更新を行うためのコントローラー
package com.example.JavaSilverSE11_Question.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.imageio.IIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.data.jpa.domain.QAbstractAuditable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.JavaSilverSE11_Question.entity.QuestionsList;
import com.example.JavaSilverSE11_Question.entity.QuestionsListItem;
import com.example.JavaSilverSE11_Question.entity.UserAnswer;
import com.example.JavaSilverSE11_Question.service.QuestionAnswerService;
import com.example.JavaSilverSE11_Question.service.QuestionsListService;

// import com.example.JavaSilverSE11_Question.entity.TaskItem;
// import com.example.JavaSilverSE11_Question.repository.TaskItemRepository;
// import com.example.JavaSilverSE11_Question.service.TaskItemService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

// リストコントローラー
@Controller // Spring MVCのコントローラーであることを示す
@RequestMapping("/main") // URLの先頭部分を指定
public class MainController {

    //
    // @Autowired
    // private TaskItemService TIService;
    // @Autowired
    // private TaskItemRepository taskItemRepository;

    @GetMapping("/home")
    public String showhome(HttpSession session, Model model) { // ここでsessionは既存のセッション or 新規セッションを取得
        String userId = (String) session.getAttribute("userId");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした");
            return "redirect:/login";
        }
        return "home";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) { // ここでsessionは既存のセッション or 新規セッションを取得
        session.invalidate();
        return "redirect:/login";
    }
}

@Controller // Spring MVCのコントローラーであることを示す
@RequestMapping("/question") // URLの先頭部分を指定
class QuestionController {

    @Autowired
    private QuestionAnswerService QAService;

    @Autowired
    private QuestionsListService QLService;

    @PostMapping("/mockExam")
    public String firstQuestion(HttpSession session, Model model, @RequestParam String No) throws IOException {
        String userId = (String) session.getAttribute("userId");
        int qNo = Integer.parseInt(No);
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした");
            return "redirect:/login";
        }

        try {
            List<UserAnswer> answer = QAService.userAnswer(userId);
            QuestionsList QuestionsList = QLService.createQuestionList(userId);
            session.setAttribute("QuestionsList", QuestionsList); // 問題文

            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), qNo);
            List filesPath = QLService.setFilesPath(QuestionsList, qNo);

            session.setAttribute("DQ", DisplayQuestion);
            session.setAttribute("answer", answer); // 回答用紙
            session.setAttribute("qNo", qNo); // 初回問題No(1)
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("DQ", DisplayQuestion);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        return "questions";
    }

    @PostMapping("/next")
    public String nextQuestion(HttpSession session, Model model, @RequestParam String action) throws IOException {
        String userId = (String) session.getAttribute("userId");
        int nextNo = (Integer) session.getAttribute("qNo");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした");
            return "redirect:/login";
        }

        if (action.equals("back"))
            nextNo--;
        else
            nextNo++;

        try {
            QuestionsList QuestionsList = (QuestionsList) session.getAttribute("QuestionsList");
            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), nextNo);
            List filesPath = QLService.setFilesPath(QuestionsList, nextNo);

            session.setAttribute("DQ", DisplayQuestion); // 表示問題
            session.setAttribute("qNo", nextNo); // No
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("DQ", DisplayQuestion);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        return "questions";
    }
}