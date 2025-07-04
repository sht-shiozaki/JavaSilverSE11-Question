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
    public String showhome(HttpSession session, Model model, @RequestParam String qNo) throws IOException {
        String userId = (String) session.getAttribute("userId");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした");
            return "redirect:/login";
        }

        try {
            List<UserAnswer> answer = QAService.userAnswer(userId);
            QuestionsList QuestionsList = QLService.createQuestionList(userId);
            List filesPath = QLService.setFilesPath(QuestionsList, Integer.parseInt(qNo));

            session.setAttribute("ql", QuestionsList);
            session.setAttribute("answer", answer);
            session.setAttribute("qNo", qNo); // 初回問題No(1)
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("questionList", QuestionsList);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        return "questions";
    }
}