package com.br.ciapoficial.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.br.ciapoficial.model.Servico;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GsonServicoSerializer implements JsonSerializer<Servico> {


    @Override
    public JsonElement serialize(Servico src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonServico = new JsonObject();

        jsonServico.addProperty("id", src.getId());
        for(int i = 0; i < src.getEspecialistas().size(); i++) {
            jsonServico.addProperty(
                    "especialistas", new ArrayList<>(src.getEspecialistas()).get(i).getId() );
        }
        for(int i = 0; i < src.getUsuarios().size(); i++) {
            jsonServico.addProperty("usuarios", new ArrayList<>(src.getUsuarios()).get(i).getId());
        }

        return jsonServico;
    }
}
