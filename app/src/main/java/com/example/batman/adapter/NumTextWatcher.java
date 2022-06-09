package com.example.batman.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

//default
// https://devfunny.tistory.com/350

public class NumTextWatcher implements TextWatcher {
    String result = "";
    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    EditText editText;

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
        if(s.toString().equals("")) {
            editText.setText("0");
            editText.setSelection("0".length());
        }
    }
}
