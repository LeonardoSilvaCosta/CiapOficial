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
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;

import java.util.HashMap;
import java.util.Map;

public class AtendidoController {
    private static String TAG = AtendidoController.class.getName();

    public void cadastrarAtendido(Context context, Atendido atendido, final VolleyCallback callback) {

        String url = Constants.URLAtendidos + "/cadastrar.php";

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
                Log.e("AQUI", String.valueOf(error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("tipoAtendido", atendido.getTipoAtendido());
                params.put("nomeCompleto", atendido.getNomeCompleto());
                params.put("dataNascimento", (atendido.getDataNascimento()));
                params.put("cpf", atendido.getCpf());
                params.put("sexo", atendido.getSexo());
                params.put("telefone", atendido.getTelefone());
                params.put("email", atendido.getEmail());
                params.put("estadoCivil", atendido.getEstadoCivil());
                params.put("ufNatal", atendido.getUfNatal());
                params.put("cidadeNatal", atendido.getCidadeNatal());
                params.put("escolaridade", atendido.getEscolaridade());
                params.put("numeroFilhos", String.valueOf(atendido.getNumeroFilhos()));
                params.put("cep", atendido.getCep());
                params.put("uf", atendido.getUf());
                params.put("cidade", atendido.getCidade());
                params.put("bairro", atendido.getBairro());
                params.put("logradouro", atendido.getLogradouro());
                params.put("numero", atendido.getNumero());


                if(atendido.getTipoAtendido().equals("PM"))
                {
                    params.put("rgMilitar", atendido.getRgMilitar());
                    params.put("postoGradCat", atendido.getPostoGradCat());
                    params.put("nomeGuerra", atendido.getNomeGuerra());
                    params.put("unidade", atendido.getUnidade());
                    params.put("quadro", atendido.getQuadro());
                    params.put("dataInclusao", atendido.getDataInclusao());
                    params.put("situacaoFuncional", atendido.getSituacaoFuncional());

                }
                else if (atendido.getTipoAtendido().equals("Dependente"))
                {
                    params.put("vinculo", atendido.getVinculo());
                }

                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void listarAtendidos(Context context, final VolleyCallback callback) {

        String url = Constants.URLAtendidos + "/listar_atendidos.php";

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
        });

        queue.add(stringRequest);
    }
}
