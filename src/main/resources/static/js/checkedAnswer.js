function checkNo(qNo) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/question/check';

    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'qNo';
    input.value = qNo;

    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}
