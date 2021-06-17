package com.br.ciapoficial.helper;

import android.os.Handler;
import android.os.Looper;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;

public class DellayAction {

    public static final void clearErrorAfter2Seconds(TextInputEditText textInputEditText)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textInputEditText.setError(null);
            }
        }, 2000);
    }

    public static final void clearErrorAfter2Seconds(AutoCompleteTextView autoCompleteTextView)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoCompleteTextView.setError(null);
            }
        }, 2000);
    }

    public static final void clearErrorAfter2Seconds(RadioButton rbtn)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rbtn.setError(null);
            }
        }, 2000);
    }

}
