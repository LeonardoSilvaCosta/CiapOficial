package com.br.ciapoficial.helper;

import com.google.gson.Gson;

public class Json2Java {

    public Object converterJson2JavaObject(String json) {

        Gson gson = new Gson();
        Object object = gson.fromJson(json, Object.class);

        return object;

    }

}
