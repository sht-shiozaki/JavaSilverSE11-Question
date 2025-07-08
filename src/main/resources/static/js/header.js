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

const timerInterval = setInterval(updateTimer, 1000);
window.addEventListener("DOMContentLoaded", updateTimer);