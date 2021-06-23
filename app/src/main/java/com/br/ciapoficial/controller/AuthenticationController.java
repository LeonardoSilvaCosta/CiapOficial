package com.br.ciapoficial.controller;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieStore;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.br.ciapoficial.Constants.BASE_API_URL;

public class AuthenticationController {

    private static String TAG = AuthenticationController.class.getName();

    public void logarUsuario(Context context, UserModel user, final IVolleyCallback callback) {

        String url = BASE_API_URL + "/auth";

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

                if( error instanceof NetworkError) {
                    Toast.makeText(context,
                            "Falha na rede",
                            Toast.LENGTH_SHORT).show();
                } else if( error instanceof ServerError) {
                    Toast.makeText(context,
                            "500 Internal Server Error",
                            Toast.LENGTH_SHORT).show();
                } else if( error instanceof AuthFailureError) {
                    Toast.makeText(context,
                            "Email e/ou senha inválidos",
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
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                String credential = String.format("%s:%s", user.getEmail(), user.getSenha());
//                String auth = "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type","application/Json");
                headers.put("Accept","application/Json; charset=utf8");
//                headers.put("Authorization", CookieStore);
//                headers.put("email", user.getEmail());

                return headers;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public byte[] getBody() throws AuthFailureError {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocalDate.class, new GsonLocalDateSerializer());
                Gson gson = gsonBuilder.setPrettyPrinting().create();

                return gson.toJson(user).getBytes();
            }
        };
        queue.add(stringRequest);
    }
}
