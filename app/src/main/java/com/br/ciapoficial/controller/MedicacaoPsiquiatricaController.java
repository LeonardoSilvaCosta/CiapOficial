package com.br.ciapoficial.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.ciapoficial.Constants;
import com.br.ciapoficial.helper.VolleySingleton;
import com.br.ciapoficial.interfaces.VolleyCallback;

public class MedicacaoPsiquiatricaController {

    public void listarMedicacoesPsiquiatricas(Context context, final VolleyCallback callback) {

        String url = Constants.URLMedicacoesPsiquiatricas + "/listar_medicacoes_psiquiatricas.php";

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
                Log.e("MedicacaoController", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }
}
