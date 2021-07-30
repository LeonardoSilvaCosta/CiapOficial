package com.br.ciapoficial.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.time.LocalDate;

public class Json2Java {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate converterJson2JavaLocalDate(String json) {

        Gson gson = new Gson();
        LocalDate localDate = gson.fromJson(json, LocalDate.class);

        return localDate;
    }



}
