package com.example.batman.share.utils;

import android.text.Editable;
import android.widget.EditText;

public class TransactionNumTextWatcher extends NumTextWatcher{
    private EditText editText, operand, target;

    public TransactionNumTextWatcher(EditText editText, EditText operand, EditText target) {
        super(editText);
        this.editText = editText;
        this.operand = operand;
        this.target = target;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().equals("")) {
            editText.setText("0");
            editText.setSelection("0".length());
        }
        if(!s.toString().equals("") && !operand.getText().equals(""))
            target.setText(""+(Integer.parseInt(s.toString().replaceAll(",","")) * Integer.parseInt(operand.getText().toString().replaceAll(",",""))));

    }
}
