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
import com.br.ciapoficial.interfaces.VolleyCallback;

public class CidadeController {

    public void listarCidades(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades.php";

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
                Log.e("CidadeController", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadesAcre(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_acre.php";

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
                Log.e("CidadeAcreController", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeAlagoas(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_alagoas.php";

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
                Log.e("CidadeAlagoasController", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeAmazonas(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_amazonas.php";

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
                Log.e("CidadeAmazonasControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeAmapa(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_amapa.php";

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
                Log.e("CidadeAmapaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeBahia(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_bahia.php";

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
                Log.e("CidadeBahiaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeCeara(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_ceara.php";

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
                Log.e("CidadeCearaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeDistritoFederal(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_distrito_federal.php";

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
                Log.e("CidadeDFControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeEspiritoSanto(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_espirito_santo.php";

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
                Log.e("CidadeESControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeGoias(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_goias.php";

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
                Log.e("CidadeGoiasControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeMaranhao(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_maranhao.php";

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
                Log.e("CidadeMaranhaoControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeMinasGerais(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_minas_gerais.php";

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
                Log.e("CidadeMinasControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeMatoGrossoDoSul(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_mato_grosso_do_sul.php";

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
                Log.e("CidadeMSSulControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeMatoGrosso(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_mato_grosso.php";

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
                Log.e("CidadeMTControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadePara(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_para.php";

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
                Log.e("CidadeParaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeParaiba(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_paraiba.php";

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
                Log.e("CidadeParaibaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadePernambuco(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_pernambuco.php";

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
                Log.e("CidadePernambucoControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadePiaui(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_piaui.php";

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
                Log.e("CidadePiauiControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeParana(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_parana.php";

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
                Log.e("CidadeParanaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeRioDeJaneiro(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_rio_de_janeiro.php";

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
                Log.e("CidadeRioControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeRioGrandeDoNorte(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_rio_grande_do_norte.php";

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
                Log.e("CidadeRNControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeRondonia(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_rondonia.php";

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
                Log.e("CidadeRondoniaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeRoraima(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_roraima.php";

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
                Log.e("CidadeRoraimaControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeRioGrandeDoSul(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_rio_grande_do_sul.php";

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
                Log.e("CidadeRSControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeSantaCatarina(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_santa_catarina.php";

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
                Log.e("CidadeSantaCatControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeSergipe(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_sergipe.php";

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
                Log.e("CidadeSergipeControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeSaoPaulo(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_sao_paulo.php";

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
                Log.e("CidadeSPControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void listarCidadeTocantins(Context context, final VolleyCallback callback) {

        String url = Constants.URLCidadesCasa + "/listar_cidades_tocantins.php";

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
                Log.e("CidadeTocantinsControl", error.toString());
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }
}
