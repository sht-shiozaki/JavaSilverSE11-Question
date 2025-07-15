let remainingSeconds = window.remainingSeconds;
// タイマー表示
function updateTimer() {
    const minutes = Math.floor(remainingSeconds / 60);
    const seconds = remainingSeconds % 60;
    const formatted = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
    document.getElementById("timeDisplay").textContent = formatted;

    if (remainingSeconds > 0) {
      remainingSeconds--;
      sessionStorage.setItem("remainingSeconds", remainingSeconds); // ブラウザに保存（再読み込み対策）
    } else {
      clearInterval(timerInterval);
      alert("時間切れです！");
    }
}

// 残り時間をサーバーに連携
function saveTimeToHidden() {
    document.getElementById("remainingTimeField").value = remainingSeconds;
}

//　終了ボタン押下時のチェック全回答済みチェック
let answeredCount = Number(window.answeredCount);
function ExamCompleted() {
  const endForm = document.getElementById("endForm");
  const mainForm = document.getElementById("mainForm");
  // チェックされた選択肢の数を数える
  const checked = mainForm.querySelectorAll('input[name="selectedChoices"]:checked');

  // 1つでもチェックされていれば answeredCount++
  if (checked.length > 0) answeredCount += 1;

  // 全問解答済みの判定
  if (answeredCount >= 3){
    checked.forEach(cd => {
      const choiceHidden = document.createElement("input");
      choiceHidden.type = "hidden";
      choiceHidden.name = "selectedChoices";
      choiceHidden.value = cd.value;
      endForm.appendChild(choiceHidden);
    });
    return true;
  }else {
    alert("未回答の問題があります。");
    return false;
  }
}

let timerInterval;
window.addEventListener("DOMContentLoaded", () => {
    updateTimer(); // DOM構築後に初回タイマー表示を即実行
    timerInterval = setInterval(updateTimer, 1000); // 1秒ごとに更新開始
});
