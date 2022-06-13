package com.example.batman.share.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

//default
// https://devfunny.tistory.com/350

public class NumTextWatcher implements TextWatcher {
    private String result = "";
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private EditText editText;

    public NumTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)) {
            result = decimalFormat.format(Double.parseDouble(s.toString().replaceAll(",", "")));
            editText.setText(result);
            editText.setSelection(result.length());
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().equals("")) {
            editText.setText("0");
            editText.setSelection("0".length());
        }
    }
}
