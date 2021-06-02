package com.br.ciapoficial.controller;

import android.content.Context;
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
import com.br.ciapoficial.helper.Java2Json;
import com.br.ciapoficial.helper.VolleySingleton;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Pessoa;

import java.util.HashMap;
import java.util.Map;

public abstract class PessoaController {

    private static String TAG = PessoaController.class.getName();

    public void cadastrar(Context context, Pessoa pessoa, final VolleyCallback callback) {

        String url = Constants.BASE_API_URL + "/pessoas";

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
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

//                params.put("nomeCompleto", pessoa.getNomeCompleto());
//                params.put("dataNascimento", (Java2Json.converterJava2JasonLocalDate(pessoa.getDataNascimento())));
//                params.put("cpf", pessoa.getCpf());
//                params.put("sexo", Java2Json.converterJava2JasonSexo(pessoa.getSexo()));
//                params.put("naturalidade", Java2Json.converterJava2JasonCidade(pessoa.getNaturalidade()));
//                params.put("estadoCivil", Java2Json.converterJava2JasonEstadoCivil(pessoa.getEstadoCivil()));
//                params.put("numeroFilhos", Java2Json.converterJava2JasonInt(pessoa.getNumeroFilhos()));
//                params.put("escolaridade", Java2Json.converterJava2JasonEscolaridade(pessoa.getEscolaridade()));
//                params.put("telefones",Java2Json.converterJava2JsonArrayTelefone(pessoa.getTelefones()));
//                params.put("email", pessoa.getEmail());
//                params.put("endereco", Java2Json.converterJava2JasonEndereco(pessoa.getEndereco()));
//                params.put("responsavelCadastro", Java2Json.converterJava2JasonFuncionario(pessoa.getResponsavelCadastro()));

                params.put("pessoa", Java2Json.converterJava2JsonPessoa(pessoa));
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
