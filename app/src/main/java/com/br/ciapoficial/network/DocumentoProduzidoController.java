package com.br.ciapoficial.network;

import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.br.ciapoficial.Constants;
import com.br.ciapoficial.helper.VolleySingleton;
import com.br.ciapoficial.interfaces.IVolleyCallback;

import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

public class DocumentoProduzidoController {

    private String url = Constants.BASE_API_URL + "/documentos+produzidos";
    private SharedPreferences sharedPreferences;

    public void listar(Context context, final IVolleyCallback callback) {

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @SneakyThrows
                    @Override
                    public void onResponse(String response) {

                        callback.onSucess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof NetworkError) {
                    Toast.makeText(context,
                            "Falha na rede",
                            Toast.LENGTH_SHORT).show();
                } else if( error instanceof ServerError) {
                    Toast.makeText(context,
                            "500 Internal Server Error",
                            Toast.LENGTH_SHORT).show();

                } else if( error instanceof ParseError) {
                    Toast.makeText(context,
                            "ParseError",
                            Toast.LENGTH_SHORT).show();
                } else if( error instanceof NoConnectionError) {
                    Toast.makeText(context,
                            "Falha na conexão",
                            Toast.LENGTH_SHORT).show();
                } else if( error instanceof TimeoutError) {
                    Toast.makeText(context,
                            "504 Timeout Error",
                            Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/Json");
                headers.put("Accept","application/Json; charset=utf8");
                headers.put("Authorization", token);
                return headers;
            }
        };
        queue.add(stringRequest);
    }
}
