package com.br.ciapoficial.network;

import static com.br.ciapoficial.Constants.BASE_API_URL;
import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

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
import com.android.volley.toolbox.Volley;
import com.br.ciapoficial.helper.GsonLocalDateSerializer;
import com.br.ciapoficial.helper.GsonLocalDateTimeSerializer;
import com.br.ciapoficial.helper.VolleySingleton;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.in_servico.Atendimento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

public class AtendimentoController {
    private static String TAG = AtendimentoController.class.getName();

    private String url = BASE_API_URL + "/atendimentos";
    private SharedPreferences sharedPreferences;

    public void registrar(Context context, Atendimento atendimento, final IVolleyCallback callback) {

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                    error.printStackTrace();
                    Log.e("erro-registro", error.toString());
                }
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/Json; charset=utf8");
                headers.put("Accept","application/Json; charset=utf8");
                headers.put("Authorization", token);
                return headers;
            }

            @Override
            public String getBodyContentType() { return "application/json; charset=utf-8"; }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public byte[] getBody() throws AuthFailureError {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocalDate.class, new GsonLocalDateSerializer());
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeSerializer());
                Gson gson = gsonBuilder.setPrettyPrinting().create();

                return gson.toJson(atendimento).getBytes();
            }
        };

        queue.add(stringRequest);
    }

    public void listar(Context context, final IVolleyCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(context);
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
                    error.printStackTrace();
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
