package com.br.ciapoficial.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Calendar {

    public static void pegarCalendarioDataAtual(Context context, AutoCompleteTextView campoDeTexto) {

        final java.util.Calendar calendar = java.util.Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(java.util.Calendar.YEAR, year);
                calendar.set(java.util.Calendar.MONTH, month);
                calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, new Locale("pt", "Br"));

                campoDeTexto.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

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

                        new DatePickerDialog(context, date, calendar
                                .get(calendar.YEAR), calendar.get(calendar.MONTH),
                                calendar.get(java.util.Calendar.DAY_OF_MONTH)).show();
                        campoDeTexto.showDropDown();

                        return true;
                    }
                }

                return false;
            }
        });
    }
}
