package com.br.ciapoficial.controller;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.br.ciapoficial.Constants;
import com.br.ciapoficial.helper.Java2Json;
import com.br.ciapoficial.helper.VolleySingleton;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Especialista;

import java.util.HashMap;
import java.util.Map;

public class EspecialistaController extends FuncionarioController {

    private static String TAG = EspecialistaController.class.getName();

    public void cadastrar(Context context, Especialista especialista, final VolleyCallback callback) {

        String url = Constants.BASE_API_URL + "/especialistas";

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        callback.onSucess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "didn't work!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("especialidade", Java2Json.converterJava2JasonEspecialidade(especialista
                                    .getEspecialidade()));
                params.put("registroConselho", especialista.getRegistroConselho());

                return params;
            }
        };

        queue.add(stringRequest);
    }
}
