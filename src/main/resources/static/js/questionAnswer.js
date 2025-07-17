function Answer() {
    const answers = highlightList; // 正解（例: ["B", "E"]）
    const checkedOptionChars = [];
    
    // チェックされている optionChar を収集
    document.querySelectorAll("input.checkbox:checked").forEach(cb => {
        const charSpan = cb.closest("label").querySelector(".char-text");
        if (charSpan) {
            const optionChar = charSpan.getAttribute("data-option");
            if (optionChar) {
                checkedOptionChars.push(optionChar);
            }
        }
    });

    // 正解とチェックが完全一致か比較（順不同）
    const isCorrect = arraysMatch(checkedOptionChars, answers);

    // 結果表示
    const resultEl = document.getElementById("result");
    if (isCorrect) {
        resultEl.textContent = "正解";
    }else {
        resultEl.textContent = "不正解";
        resultEl.style.color = "red";
    }

    // ボタンの表示・非表示
    document.getElementById("answerButton").style.visibility = "hidden";
    document.getElementById("nextButton").style.visibility = "visible";

    // 正解の選択肢（answers）の .char-text を赤くする
    document.querySelectorAll(".char-text").forEach(span => {
        const optionChar = span.getAttribute("data-option");
        if (answers.includes(optionChar)) {
            span.style.color = "red";
        } else {
            span.style.color = ""; // 初期化（必要に応じて）
        }
    });

    return false; // 送信しない
}

// 2つの配列が順不同・完全一致か判定
function arraysMatch(arr1, arr2) {
    if (arr1.length !== arr2.length) return false;
    const sorted1 = [...arr1].sort();
    const sorted2 = [...arr2].sort();
    return sorted1.every((val, idx) => val === sorted2[idx]);
}