package com.br.ciapoficial.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

public class DropDownClick {

    public static void showDropDown(Context context, AutoCompleteTextView campoDeTexto)
    {
        campoDeTexto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (campoDeTexto.getRight()
                            - campoDeTexto.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        campoDeTexto.showDropDown();

                        return true;
                    }
                }

                return false;
            }
        });
    }

}
