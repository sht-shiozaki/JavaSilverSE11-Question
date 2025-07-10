// タスク管理の一覧表示、追加、削除、更新を行うためのコントローラー
package com.example.JavaSilverSE11_Question.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.JavaSilverSE11_Question.dto.UserAnswerDTO;
import com.example.JavaSilverSE11_Question.entity.QuestionsList;
import com.example.JavaSilverSE11_Question.entity.QuestionsListItem;
import com.example.JavaSilverSE11_Question.entity.UserAnswer;
import com.example.JavaSilverSE11_Question.service.UserAnswerService;
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

    @GetMapping("/home")
    public String showhome(HttpSession session, Model model) { // ここでsessionは既存のセッション or 新規セッションを取得
        String userId = (String) session.getAttribute("userId");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした");
            return "redirect:/login";
        }
        model.addAttribute("currentPage", "home");// ヘッダー
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
    private UserAnswerService UAService;

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
            List<UserAnswer> answer = UAService.userAnswer(userId); // user解答をリセット
            QuestionsList QuestionsList = QLService.createQuestionList(userId);
            session.setAttribute("QuestionsList", QuestionsList); // 問題文

            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), qNo);
            List filesPath = QLService.setFilesPath(QuestionsList, qNo);

            Map<Integer, Boolean> answeredMap = new HashMap<>();
            Map<Integer, Boolean> checkedMap = new HashMap<>();
            for (int i = 1; i <= 80; i++) {
                answeredMap.put(i, false); // すべて未回答として初期化
                checkedMap.put(i, false); // すべて未チェックとして初期化
            }
            model.addAttribute("answeredMap", answeredMap);
            model.addAttribute("checkedMap", checkedMap);

            session.setAttribute("DQ", DisplayQuestion);
            session.setAttribute("answer", answer); // 回答用紙
            session.setAttribute("qNo", qNo); // 初回問題No(1)
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("DQ", DisplayQuestion);

            // タイマー設定
            int initialTime = 600; // 例：10分（600秒）
            session.setAttribute("remainingTime", initialTime);
            model.addAttribute("remainingTime", initialTime);
            model.addAttribute("currentPage", "question");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        return "questions";
    }

    @PostMapping("/next")
    // required = false:チェックがない時はNullになる
    public String nextQuestion(HttpSession session, Model model,
            @RequestParam int remainingTime,
            @RequestParam String action,
            @RequestParam("characters") List<String> characters,
            @RequestParam(name = "selectedChoices", required = false) List<String> selectedChoices,
            @RequestParam(name = "checked", required = false) boolean checked // チェックボックスから取得
    ) throws IOException {
        String userId = (String) session.getAttribute("userId");
        int nextNo = (Integer) session.getAttribute("qNo");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした");
            return "redirect:/login";
        }

        UAService.setUserAnswer(selectedChoices, nextNo, userId); // 選択した解答をuser_answersに書込む
        UAService.setCheckFlag(userId, nextNo, checked); // チェック状態の保存
        int answeredCount = UAService.getAnsweredCount(userId); // 回答数取得
        session.setAttribute("answeredCount", answeredCount);

        if (action.equals("back")) {
            nextNo--;
        } else {
            nextNo++;
        }

        try {
            QuestionsList QuestionsList = (QuestionsList) session.getAttribute("QuestionsList"); // セッションからリスト取得
            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), nextNo); // 問題Noの情報を格納
            List filesPath = QLService.setFilesPath(QuestionsList, nextNo); // 問題文を格納
            List<String> NoSelectedChoices = UAService.getSelectedChoices(userId, nextNo);

            Map<Integer, Boolean> answeredMap = UAService.getAnsweredMap(userId); // No => true/false
            Map<Integer, Boolean> checkedMap = UAService.getCheckedMap(userId); // No => true/false

            session.setAttribute("DQ", DisplayQuestion); // 表示問題
            session.setAttribute("qNo", nextNo); // No設定
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("DQ", DisplayQuestion);
            model.addAttribute("NSC", NoSelectedChoices); // 問題Noでチェックしたデータ

            // タイマー引継ぎ
            session.setAttribute("remainingTime", remainingTime);
            model.addAttribute("currentPage", "question");
            model.addAttribute("answeredMap", answeredMap);
            model.addAttribute("checkedMap", checkedMap);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        return "questions";
    }

    // 試験終了
    @PostMapping("/end")
    // required = false:チェックがない時はNullになる
    public String endQuestion(HttpSession session, Model model,
            @RequestParam(name = "selectedChoices", required = false) List<String> selectedChoices) throws Exception {
        String userId = (String) session.getAttribute("userId");
        int nextNo = (Integer) session.getAttribute("qNo");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            model.addAttribute("error", "ユーザー情報が見つかりませんでした");
            return "redirect:/login";
        }

        try {
            // 選択した解答をuser_answersに書込む
            UAService.setUserAnswer(selectedChoices, nextNo, userId);

            // userの解答判定
            List<UserAnswerDTO> userAnswerResult = UAService.getResult(session, userId);
            model.addAttribute("resultList", userAnswerResult);
            model.addAttribute("currentPage", "home");// ヘッダー
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        return "resultPage";
    }
}