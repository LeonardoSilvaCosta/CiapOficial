package com.br.ciapoficial.controller;

import android.content.Context;
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
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioController {
    private static String TAG = UsuarioController.class.getName();

    public void cadastrarUsuario(Context context, Usuario usuario, final VolleyCallback callback) {

        String url = Constants.URLUsuariosCasa + "/cadastrar.php";

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

                params.put("nomeCompleto", usuario.getNomeCompleto());
                params.put("dataNascimento", (usuario.getDataNascimento()));
                params.put("cpf", usuario.getCpf());
                params.put("sexo", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getSexo())));
                params.put("telefones",Java2Json.converterJava2JsonArrayString(usuario.getTelefones()));
                params.put("email", usuario.getEmail());
                params.put("cep", usuario.getCep());
                params.put("cidade", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getCidade())));
                params.put("bairro", usuario.getBairro());
                params.put("logradouro", usuario.getLogradouro());
                params.put("numero", usuario.getNumero());
                params.put("rgMilitar", usuario.getRgMilitar());
                params.put("postoGradCat", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getPostoGradCat())));
                params.put("nomeGuerra", usuario.getNomeGuerra());
                params.put("unidade", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getUnidade())));
                params.put("quadro", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getQuadro())));
                params.put("especialidade", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getEspecialidade())));
                params.put("registroConselho", usuario.getRegistroConselho());
                params.put("funcaoAdministrativa", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getFuncaoAdministrativa())));
                params.put("situacaoFuncional", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getSituacaoFuncional())));
                params.put("senha", usuario.getCpf());

                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void listarUsuarios(Context context, final VolleyCallback callback) {

        String url = Constants.URLUsuariosCasa + "/listar.php";

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
                Toast.makeText(context, "didn't work!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void atualizarUsuario(Context context, final Usuario usuario, final VolleyCallback callback) {

        String url = Constants.URLUsuariosCasa + "/atualizar.php";

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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(usuario.getId()));
                params.put("nomeCompleto", usuario.getNomeCompleto());
                params.put("dataNascimento", usuario.getDataNascimento());
                params.put("cpf", usuario.getCpf());
                params.put("sexo", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getSexo())));
                params.put("telefones",Java2Json.converterJava2JsonArrayString(usuario.getTelefones()));
                params.put("email", usuario.getEmail());
                params.put("cep", usuario.getCep());
                params.put("uf", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getUf())));
                params.put("cidade", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getCidade())));
                params.put("bairro", usuario.getBairro());
                params.put("logradouro", usuario.getLogradouro());
                params.put("numero", usuario.getNumero());
                params.put("rgMilitar", usuario.getRgMilitar());
                params.put("postoGradCat", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getPostoGradCat())));
                params.put("nomeGuerra", usuario.getNomeGuerra());
                params.put("unidade", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getUnidade())));
                params.put("quadro", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getQuadro())));
                params.put("especialidade", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getEspecialidade())));
                params.put("registroConselho", usuario.getRegistroConselho());
                params.put("funcaoAdministrativa", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getFuncaoAdministrativa())));
                params.put("situacaoFuncional", Java2Json.converterJava2JasonInt(Integer.parseInt(usuario.getSituacaoFuncional())));
                params.put("senha", usuario.getSenha());
                return params;
            }
        };
        queue.add(stringRequest);

    }

    public void deletarUsuario(Context context, final String id, final VolleyCallback callback) {

        String url = Constants.URLUsuariosCasa + "/deletar.php";

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

        String url = Constants.URLUsuariosCasa + "/logar.php";

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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("senha", user.getSenha());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void recuperarSenha(Context context, String email, final VolleyCallback callback) {

        String url = Constants.URLUsuariosCasa + "/recuperar_senha.php";

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

        String url = Constants.URLUsuariosCasa + "/carregar_imagem.php";

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

        String url = Constants.URLUsuariosCasa + "/recuperar_imagem.php";

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

        String url = Constants.URLUsuariosCasa + "/recuperar_usuario_logado.php";

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

        String url = Constants.URLUsuariosCasa + "/recuperar_telefones_usuario_logado.php";

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

        String url = Constants.URLUsuariosCasa + "/listar_oficiais.php";

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
