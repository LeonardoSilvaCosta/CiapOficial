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
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendimento;

import java.util.HashMap;
import java.util.Map;

public class AtendimentoController {
    private static String TAG = AtendimentoController.class.getName();

    public void registrarAtendimento(Context context, Atendimento atendimento, final VolleyCallback callback) {

        String url = Constants.URLAtendimentosCasa + "/cadastrar.php";

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
                Log.e("RegAtendimento", String.valueOf(error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("data", atendimento.getData());
                params.put("oficiaisResponsaveis", Java2Json.converterJava2JsonArrayString(atendimento.getOficiaisResponsaveis()));
                params.put("atendidos", Java2Json.converterJava2JsonArrayString(atendimento.getAtendidos()));
                params.put("unidade", atendimento.getUnidade());
                params.put("modalidade", atendimento.getModalidade());
                params.put("acesso", atendimento.getAcesso());
                params.put("tipo", atendimento.getTipo());
                params.put("tipoAvaliacao", atendimento.getTipoAvaliacao());
                params.put("programa", atendimento.getPrograma());
                params.put("deslocamentos", Java2Json.converterJava2JsonArrayString(atendimento.getDeslocamentos()));
                params.put("demandaGeral", atendimento.getDemandaGeral());
                params.put("demandasEspecificas", Java2Json.converterJava2JsonArrayString(atendimento.getDemandasEspecificas()));
                params.put("condicaoLaboral", atendimento.getCondicaoLaboral());
                params.put("procedimento", atendimento.getProcedimento());
                params.put("documentosProduzidos", Java2Json.converterJava2JsonArrayString(atendimento.getDocumentosProduzidos()));
                params.put("encaminhamentos", Java2Json.converterJava2JsonArrayString(atendimento.getEncaminhamentos()));
                params.put("sinaisSintomas", Java2Json.converterJava2JsonArrayString(atendimento.getSinaisSintomas()));
                params.put("medicacoesPsiquiatricas", Java2Json.converterJava2JsonArrayString(atendimento.getMedicacoesPsiquiatricas()));
                params.put("afastamento", Java2Json.converterJava2JsonBoolean(atendimento.isAfastamento()));
                params.put("evolucao", atendimento.getEvolucao());

                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void listarAtendimentos(Context context, final VolleyCallback callback) {

        String url = Constants.URLAtendimentosCasa + "/listar_atendimentos.php";

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

    public void listarAtendimentosOficiais(Context context, final VolleyCallback callback) {

        String url = Constants.URLAtendimentosCasa + "/listar_atendimentos_oficiais.php";

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

    public void listarAtendimentosAtendidos(Context context, final VolleyCallback callback) {

        String url = Constants.URLAtendimentosCasa + "/listar_atendimentos_atendidos.php";

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

    public void listarAtendimentosDeslocamentos(Context context, final VolleyCallback callback) {

        String url = Constants.URLAtendimentosCasa + "/listar_atendimentos_deslocamentos.php";

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

    public void listarAtendimentosDocumentosProduzidos(Context context, final VolleyCallback callback) {

        String url = Constants.URLAtendimentosCasa + "/listar_atendimentos_documentos_produzidos.php";

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

    public void listarAtendimentosDemandasEspecificas(Context context, final VolleyCallback callback) {

        String url = Constants.URLAtendimentosCasa + "/listar_atendimentos_demandas_especificas.php";

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
