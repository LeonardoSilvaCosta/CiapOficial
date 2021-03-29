package com.br.ciapoficial.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import com.br.ciapoficial.interfaces.TextWatcherCallback;

import java.util.ArrayList;

public class TextChangedListener {

    public static void textChangedListener(AutoCompleteTextView autoCompleteTextView, final TextWatcherCallback callback)
    {
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                callback.afterTextChanged(s);
            }
        });
    }

}
