// タスク管理の一覧表示、追加、削除、更新を行うためのコントローラー
package com.example.JavaSilverSE11_Question.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.servlet.http.HttpSession;

// リストコントローラー
@Controller // Spring MVCのコントローラーであることを示す
@RequestMapping("/main") // URLの先頭部分を指定
public class MainController {

    @Autowired
    private QuestionsListService QLService;

    @GetMapping("/home")
    public String showhome(HttpSession session, Model model) { // ここでsessionは既存のセッション or 新規セッションを取得
        String userId = (String) session.getAttribute("userId");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            return "redirect:/login";
        }
        QLService.deleteUserDate(userId);
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

    @PostMapping("/start")
    public String firstQuestion(HttpSession session,
            Model model,
            @RequestParam String No,
            @RequestParam String mode) throws IOException {
        String userId = (String) session.getAttribute("userId");
        int qNo = Integer.parseInt(No);
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            List<UserAnswer> answer = UAService.userAnswer(userId); // user解答をリセット
            QuestionsList QuestionsList = QLService.createQuestionList(userId);
            session.setAttribute("QuestionsList", QuestionsList); // 問題文

            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), qNo);
            List filesPath = QLService.setFilesPath(QuestionsList, qNo);

            if (mode.equals("exam")) {
                Map<String, Boolean> answeredMap = new HashMap<>();
                Map<String, Boolean> checkedMap = new HashMap<>();
                for (int i = 1; i <= 80; i++) {
                    answeredMap.put(String.valueOf(i), false); // すべて未回答として初期化
                    checkedMap.put(String.valueOf(i), false); // すべて未チェックとして初期化
                    if (i == 80)
                        System.err.println("完了");
                }
                session.setAttribute("answeredMap", answeredMap);
                session.setAttribute("checkedMap", checkedMap);
                session.setAttribute("remainingTime", 10800); // タイマー設定
            } else {
                List<String> highlightList = QLService.getAnswers(QuestionsList, qNo); // nextNoが次の問題Noの為、表示問題Noに変更
                session.setAttribute("highlightList", highlightList); // 問題NOの回答をリストで格納
            }

            session.setAttribute("answer", answer); // 回答用紙
            session.setAttribute("qNo", qNo); // 初回問題No(1)
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("DQ", DisplayQuestion);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        if (mode.equals("exam")) { // questions or questionAnswer
            session.setAttribute("useTimer", true);
            model.addAttribute("currentPage", "exam"); // ヘッダーの選択
            return "questions";
        } else {
            session.setAttribute("useTimer", false);
            model.addAttribute("currentPage", "qa");
            return "questionAnswer";
        }
    }

    @PostMapping("/next")
    // required = false:チェックがない時はNullになる
    public String nextQuestion(HttpSession session, Model model,
            @RequestParam int remainingTime,
            @RequestParam String mode,
            @RequestParam String action,
            @RequestParam(name = "selectedChoices", required = false) List<String> selectedChoices, // required:必須項目。デフォルトはtrueで省略可
            @RequestParam(name = "checkNo", required = false) boolean checkNo // チェックボックスから取得
    ) throws IOException {
        String userId = (String) session.getAttribute("userId");
        int nextNo = (Integer) session.getAttribute("qNo");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            return "redirect:/login";
        }

        if (mode.equals("exam")) {
            // 現在の回答Noでの処理
            UAService.setUserAnswer(selectedChoices, nextNo, userId); // 選択した解答をuser_answersに書込む
            UAService.setCheckFlag(userId, nextNo, checkNo); // チェック状態の保存
            int answeredCount = UAService.getAnsweredCount(userId); // 回答数取得
            session.setAttribute("answeredCount", answeredCount); // 終了ボタンの判定時に使用
        }

        if (action.equals("back")) {
            nextNo--;
        } else if (action.equals("next")) {
            nextNo++;
        } else {
            if (nextNo == 80)
                nextNo = 1; // Noリセット
            else
                nextNo++; // 一問一答
        }

        try {
            QuestionsList QuestionsList = (QuestionsList) session.getAttribute("QuestionsList"); // セッションからリスト取得
            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), nextNo); // 次の問題情報を格納
            List filesPath = QLService.setFilesPath(QuestionsList, nextNo); // 次の問題Fileを格納
            List<String> NoSelectedChoices = UAService.getSelectedChoices(userId, nextNo); // チェックした選択肢を保存

            if (mode.equals("exam")) {
                Map<String, Boolean> answeredMap = UAService.getAnsweredMap(userId); // No => true/false
                Map<String, Boolean> checkedMap = UAService.getCheckedMap(userId); // No => true/false
                session.setAttribute("answeredMap", answeredMap); // 回答済みリスト
                session.setAttribute("checkedMap", checkedMap); // チェックリスト
                session.setAttribute("remainingTime", remainingTime); // タイマー引継ぎ
            } else {
                List<String> highlightList = QLService.getAnswers(QuestionsList, nextNo); // nextNoが次の問題Noの為、表示問題Noに変更
                session.setAttribute("highlightList", highlightList); // 問題NOの回答をリストで格納
            }

            session.setAttribute("qNo", nextNo); // No設定
            session.setAttribute("filesPath", filesPath); // File設定
            model.addAttribute("DQ", DisplayQuestion); // 表示問題情報
            model.addAttribute("NSC", NoSelectedChoices); // 今まで選択した選択肢リスト

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        if (mode.equals("exam")) { // questions or questionAnswer
            session.setAttribute("useTimer", true); // タイマーON
            model.addAttribute("currentPage", "exam"); // ヘッダーの選択
            return "questions";
        } else {
            session.setAttribute("useTimer", false);
            model.addAttribute("currentPage", "qa");
            return "questionAnswer";
        }
    }

    @PostMapping("/move")
    // required = false:チェックがない時はNullになる
    public String moveQuestion(HttpSession session, Model model,
            @RequestParam int remainingTime,
            @RequestParam int qNo,
            // @RequestParam("characters") List<String> characters,
            @RequestParam(name = "selectedChoices", required = false) List<String> selectedChoices,
            @RequestParam(name = "checkNo", required = false) boolean checkNo // チェックボックスから取得
    ) throws IOException {
        String userId = (String) session.getAttribute("userId");
        int nextNo = (Integer) session.getAttribute("qNo");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            return "redirect:/login";
        }

        UAService.setUserAnswer(selectedChoices, nextNo, userId); // 選択した解答をuser_answersに書込む
        UAService.setCheckFlag(userId, nextNo, checkNo); // チェック状態の保存
        int answeredCount = UAService.getAnsweredCount(userId); // 回答数取得
        session.setAttribute("answeredCount", answeredCount);

        nextNo = qNo; // qNo:遷移先Noを設定

        try {
            QuestionsList QuestionsList = (QuestionsList) session.getAttribute("QuestionsList"); // セッションからリスト取得
            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), nextNo); // 問題Noの情報を格納
            List filesPath = QLService.setFilesPath(QuestionsList, nextNo); // 問題文を格納
            List<String> NoSelectedChoices = UAService.getSelectedChoices(userId, nextNo);

            Map<String, Boolean> answeredMap = UAService.getAnsweredMap(userId); // No => true/false
            Map<String, Boolean> checkedMap = UAService.getCheckedMap(userId); // No => true/false

            session.setAttribute("DQ", DisplayQuestion); // 表示問題
            session.setAttribute("qNo", nextNo); // No設定
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("DQ", DisplayQuestion);
            model.addAttribute("NSC", NoSelectedChoices); // 問題Noでチェックしたデータ

            // タイマー引継ぎ
            session.setAttribute("remainingTime", remainingTime);
            model.addAttribute("currentPage", "exam");
            session.setAttribute("answeredMap", answeredMap);
            session.setAttribute("checkedMap", checkedMap);
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
            return "redirect:/login";
        }

        try {
            // 選択した解答をuser_answersに書込む
            if (selectedChoices != null)
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

    // 試験終了
    @PostMapping("/check")
    // required = false:チェックがない時はNullになる
    public String checkQuestion(HttpSession session, Model model,
            @RequestParam int qNo) throws Exception {
        String userId = (String) session.getAttribute("userId");
        // ログイン情報が無ければログイン画面へ
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            QuestionsList QuestionsList = (QuestionsList) session.getAttribute("QuestionsList"); // セッションからリスト取得
            QuestionsListItem DisplayQuestion = QLService.setDisplayQuestion(QuestionsList.getItems(), qNo); // 問題Noの情報を格納
            List filesPath = QLService.setFilesPath(QuestionsList, qNo); // 問題文を格納
            List<String> NoSelectedChoices = UAService.getSelectedChoices(userId, qNo);

            List<String> highlightList = QLService.getAnswers(QuestionsList, qNo); // 色を変えたい解答
            session.setAttribute("highlightList", highlightList);
            session.setAttribute("DQ", DisplayQuestion); // 表示問題
            session.setAttribute("filesPath", filesPath);
            model.addAttribute("DQ", DisplayQuestion);
            model.addAttribute("NSC", NoSelectedChoices); // 問題Noでチェックしたデータ
            model.addAttribute("currentPage", "home");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("questionList", "ファイルの読み込みに失敗しました。");
        }
        return "checkedAnswer";
    }
}