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
                Toast.makeText(context, "didn't work!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("nomeCompleto", pessoa.getNomeCompleto());
                params.put("dataNascimento", (Java2Json.converterJava2JasonDate(pessoa.getDataNascimento())));
                params.put("cpf", pessoa.getCpf());
                params.put("sexo", Java2Json.converterJava2JasonSexo(pessoa.getSexo()));
                params.put("naturalidade", Java2Json.converterJava2JasonCidade(pessoa.getNaturalidade()));
                params.put("estadoCivil", Java2Json.converterJava2JasonEstadoCivil(pessoa.getEstadoCivil()));
                params.put("numeroFilhos", Java2Json.converterJava2JasonInt(pessoa.getNumeroFilhos()));
                params.put("escolaridade", Java2Json.converterJava2JasonEscolaridade(pessoa.getEscolaridade()));
                params.put("telefones",Java2Json.converterJava2JsonArrayTelefone(pessoa.getTelefones()));
                params.put("email", pessoa.getEmail());
                params.put("endereco", Java2Json.converterJava2JasonEndereco(pessoa.getEndereco()));
                params.put("responsavelCadastro", Java2Json.converterJava2JasonFuncionario(pessoa.getResponsavelCadastro()));

                return params;
            }
        };

        queue.add(stringRequest);
    }
}
