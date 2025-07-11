function moveToQuestion(qNo) {
    const form = document.getElementById('mainForm');

    // 現在のアクションを一時保存（戻すため）
    const originalAction = form.action;

    // アクションを移動用に変更
    form.action = '/question/move';

    // qNo を hidden で追加 or 更新
    let qNoInput = form.querySelector('input[name="qNo"]');
    if (!qNoInput) {
        qNoInput = document.createElement('input');
        qNoInput.type = 'hidden';
        qNoInput.name = 'qNo';
        form.appendChild(qNoInput);
    }
    qNoInput.value = qNo;

    // 残り時間を input に反映（必要なら）
    const remainingTimeField = document.getElementById("remainingTimeField");
    if (remainingTimeField) {
        remainingTimeField.value = remainingSeconds;
    }

    form.submit();

    // ※戻るボタンや「次へ」でも使用する為、submit後に action を元に戻す
    form.action = originalAction;
}