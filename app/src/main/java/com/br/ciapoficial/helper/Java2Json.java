package com.br.ciapoficial.helper;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Java2Json {

    public static String converterJava2JsonObject(Object object) {

            Object obj = new Object();
            Gson gson = new Gson();

            // converte objetos Java para JSON e retorna JSON como String
            String jsonObj = gson.toJson(obj);
            return jsonObj;
    }

    public static String converterJava2JsonArrayString(ArrayList<String> arrayList)
    {
        String data = new Gson().toJson(arrayList);
        return data;
    }

    public static String converterJava2JsonArrayInteger(ArrayList<Integer> arrayList)
    {
        String data = new Gson().toJson(arrayList);
        return data;
    }

    public static String converterJava2JsonBoolean(boolean booleano)
    {
        String data = new Gson().toJson(booleano);
        return data;
    }

    public static String converterJava2JasonInt(int i)
    {
        String data = new Gson().toJson(i);
        return data;
    }
}
