package com.br.ciapoficial.network;

import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

public class JasperController {

    private String url = Constants.BASE_API_URL + "/relatorio/pdf/ciap";
    private SharedPreferences sharedPreferences;

    public void gerarRelatorio(Context context, final IVolleyCallback callback) {

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
                headers.put("Accept", "application/pdf");
                headers.put("Authorization", token);
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("code","03");
                return params;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public byte[] getBody() throws AuthFailureError {

                String acao = "v";
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.setPrettyPrinting().create();

                return gson.toJson(acao).getBytes();
            }
        };
        queue.add(stringRequest);
    }
}
