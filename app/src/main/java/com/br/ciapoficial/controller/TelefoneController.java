package com.br.ciapoficial.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.ciapoficial.Constants;
import com.br.ciapoficial.helper.Java2Json;
import com.br.ciapoficial.helper.VolleySingleton;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Telefone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelefoneController {

    public void listarTelefones(Context context, final VolleyCallback callback) {

        String url = Constants.URLTelefones + "/listar_telefones.php";

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
                Log.e("TelefoneController", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void deletarTelefone(Context context, final ArrayList<Telefone> dadosTelefones, final VolleyCallback callback) {

        String url = Constants.URLTelefones + "/deletar.php";

        RequestQueue queue = Volley.newRequestQueue(context);

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
                params.put("dadosTelefones", Java2Json.converterJava2JsonArrayTelefone(dadosTelefones));
                return params;

            }
        };
        queue.add(stringRequest);
    }

}
