package com.example.batman.utils;

import android.text.Editable;

public interface ICallBackTextWatcher {
    void onTextChanged(int position, CharSequence s, int start, int before, int count);
}
