package com.br.ciapoficial.controller;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
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
import com.br.ciapoficial.Constants;
import com.br.ciapoficial.helper.GsonLocalDateSerializer;
import com.br.ciapoficial.helper.Java2Json;
import com.br.ciapoficial.helper.VolleySingleton;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FuncionarioController extends PessoaController {
    private static String TAG = FuncionarioController.class.getName();

    public void cadastrar(Context context, Funcionario funcionario, final VolleyCallback callback) {

        String url = Constants.BASE_API_URL + "/funcionarios";

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/Json; charset=utf8");
                headers.put("Accept","application/Json; charset=utf8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public byte[] getBody() throws AuthFailureError {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocalDate.class, new GsonLocalDateSerializer());
                Gson gson = gsonBuilder.setPrettyPrinting().create();

                return gson.toJson(funcionario).getBytes();
            }
        };

        queue.add(stringRequest);
    }

    public void listar(Context context, final VolleyCallback callback) {

        String url = Constants.BASE_API_URL + "/funcionarios";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/Json");
                headers.put("Accept","application/Json; charset=utf8");
                return headers;
            }
        };

        queue.add(stringRequest);
    }

//    public void atualizarUsuario(Context context, final Funcionario funcionario, final VolleyCallback callback) {
//
//        String url = Constants.URLUsuarios + "/atualizar.php";
//
//        RequestQueue queue = Volley.newRequestQueue(context);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        callback.onSucess(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "didn't work!", Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", Java2Json.converterJava2JasonInt(funcionario.getId()));
//                params.put("nomeCompleto", funcionario.getNomeCompleto());
//                params.put("dataNascimento", funcionario.getDataNascimento());
//                params.put("cpf", funcionario.getCpf());
//                params.put("sexo", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getSexo())));
//                params.put("telefones",Java2Json.converterJava2JsonArrayString(funcionario.getTelefones()));
//                params.put("email", funcionario.getEmail());
//                params.put("cep", funcionario.getCep());
//                params.put("uf", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getUf())));
//                params.put("cidade", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getCidade())));
//                params.put("bairro", funcionario.getBairro());
//                params.put("logradouro", funcionario.getLogradouro());
//                params.put("numero", funcionario.getNumero());
//                params.put("rgMilitar", funcionario.getRgMilitar());
//                params.put("postoGradCat", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getPostoGradCat())));
//                params.put("nomeGuerra", funcionario.getNomeGuerra());
//                params.put("unidade", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getUnidade())));
//                params.put("quadro", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getQuadro())));
//                params.put("especialidade", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getEspecialidade())));
//                params.put("registroConselho", funcionario.getRegistroConselho());
//                params.put("funcaoAdministrativa", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getFuncaoAdministrativa())));
//                params.put("situacaoFuncional", Java2Json.converterJava2JasonInt(Integer.parseInt(funcionario.getSituacaoFuncional())));
//                params.put("senha", funcionario.getSenha());
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//
//    }

    public void deletarUsuario(Context context, final String id, final VolleyCallback callback) {

        String url = Constants.URLUsuarios + "/deletar.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
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
                params.put("id", id);
                return params;

            }
        };
        queue.add(stringRequest);
    }

    public void logarUsuario(Context context, UserModel user, final VolleyCallback callback) {

        String url = Constants.BASE_API_URL + "/login";

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
                String credential = String.format("%s:%s", user.getEmail(), user.getSenha());
                String auth = "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type","application/Json");
                headers.put("Accept","application/Json; charset=utf8");
                headers.put("Authorization", auth);
                headers.put("email", user.getEmail());

                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void recuperarSenha(Context context, String email, final VolleyCallback callback) {

        String url = Constants.URLUsuarios + "/recuperar_senha.php";

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
                Toast.makeText(context, "didn't work! ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void carregarImagemUsuario(Context context, String imagem, final VolleyCallback callback) {

        String url = Constants.URLUsuarios + "/carregar_imagem.php";

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("imagem", imagem);

                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void recuperarImagem(Context context, final VolleyCallback callback) {

        String url = Constants.URLUsuarios + "/recuperar_imagem.php";

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

    public void recuperarUsuarioLogado(Context context, final String email,  final VolleyCallback callback) {

        String url = Constants.URLUsuarios + "/recuperar_usuario_logado.php";

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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void recuperarTelefoneUsuarioLogado(Context context, final String id,  final VolleyCallback callback) {

        String url = Constants.URLUsuarios + "/recuperar_telefones_usuario_logado.php";

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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void listarOficiais(Context context, final VolleyCallback callback) {

        String url = Constants.URLUsuarios + "/listar_oficiais.php";

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
