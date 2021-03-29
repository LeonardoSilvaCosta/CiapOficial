package com.br.ciapoficial.view;

import android.os.Bundle;

import com.br.ciapoficial.controller.AtendimentoController;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;
import com.br.ciapoficial.model.Atendimento;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.in_atendimento.DemandaEspecifica;
import com.br.ciapoficial.model.in_atendimento.Deslocamento;
import com.br.ciapoficial.model.in_atendimento.DocumentoProduzido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetalhesAtendimentoActivity extends AppCompatActivity {

    private TextView txtData, txtOficialResponsavel, txtAtendido, txtUnidade, txtModalidade, txtAcesso,
    txtTipo, txtAvaliacao, txtPrograma, txtDeslocamento, txtDemandaGeral, txtDemandaEspecifica,
            txtCondicaoLaboral, txtProcedimento, txtDocumentoProduzido,
            txtAfastamento, txtEvolucao, txtDataHoraCadastro;
    private Atendimento atendimentoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atendimento);

        txtData = findViewById(R.id.txtData);
        txtOficialResponsavel = findViewById(R.id.txtOficialResponsavel);
        txtAtendido = findViewById(R.id.txtAtendido);
        txtUnidade = findViewById(R.id.txtUnidade);
        txtModalidade = findViewById(R.id.txtModalidade);
        txtAcesso = findViewById(R.id.txtAcesso);
        txtTipo = findViewById(R.id.txtTipo);
        txtAvaliacao = findViewById(R.id.txtAvaliacao);
        txtPrograma = findViewById(R.id.txtPrograma);
        txtDeslocamento = findViewById(R.id.txtDeslocamento);
        txtDemandaGeral = findViewById(R.id.txtDemandaGeral);
        txtDemandaEspecifica = findViewById(R.id.txtDemandaEspecifica);
        txtCondicaoLaboral = findViewById(R.id.txtCondicaoLaboral);
        txtProcedimento = findViewById(R.id.txtProcedimento);
        txtDocumentoProduzido = findViewById(R.id.txtDocumentoProduzido);
        txtAfastamento = findViewById(R.id.txtAfastamento);
        txtEvolucao = findViewById(R.id.txtEvolucao);
        txtDataHoraCadastro = findViewById(R.id.txtDataHoraCadastro);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            atendimentoSelecionado = (Atendimento) bundle.getSerializable("atendimentoSelecionado");

            txtData.setText(atendimentoSelecionado.getData());
            txtUnidade.setText(atendimentoSelecionado.getUnidade());
            txtModalidade.setText(atendimentoSelecionado.getModalidade());
            txtAcesso.setText(atendimentoSelecionado.getAcesso());
            txtTipo.setText(atendimentoSelecionado.getTipo());
            txtAvaliacao.setText(atendimentoSelecionado.getTipoAvaliacao());
            txtPrograma.setText(atendimentoSelecionado.getPrograma());
            txtDemandaGeral.setText(atendimentoSelecionado.getDemandaGeral());
            txtCondicaoLaboral.setText(atendimentoSelecionado.getCondicaoLaboral());
            txtProcedimento.setText(atendimentoSelecionado.getProcedimento());
            if(atendimentoSelecionado.isAfastamento())
            {
                txtAfastamento.setText("Sim");
            }else
            {
                txtAfastamento.setText("Não");
            }
            txtEvolucao.setText(atendimentoSelecionado.getEvolucao());
            txtDataHoraCadastro.setText(atendimentoSelecionado.getDataHoraCadastro());
        }

        recuperarAtendimentosOficiais();
        recuperarAtendimentosAtendidos();
        recuperarAtendimentosDeslocamentos();
        recuperarAtendimentosDocumentosProduzidos();
        recuperarAtendimentosDemandasEspecificas();

    }

    public void recuperarAtendimentosOficiais()
    {
        AtendimentoController atendimentoController = new AtendimentoController();
        atendimentoController.listarAtendimentosOficiais(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                Log.e("ResA", response);

                ArrayList<Usuario> usuarios = new ArrayList<>();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id_atendimento = object.getString(
                                    "id_atendimento");

                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {

                                Usuario usuario = new Usuario();
                                usuario.setId(Integer.valueOf(object.getString("id_oficial")));
                                usuario.setNomeCompleto(object.getString("nomeCompleto"));
                                usuario.setNomeGuerra(object.getString("nomeGuerra"));
                                usuario.setPostoGradCat(object.getString("postoGradCat"));
                                usuario.setRgMilitar(object.getString("rgMilitar"));
                                usuario.setCpf(object.getString("cpf"));

                                usuarios.add(usuario);
                                txtOficialResponsavel.setText(usuarios.toString().
                                        replace("[", "").
                                        replace("]", ""));

                            }

                        }

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void recuperarAtendimentosAtendidos()
    {
        AtendimentoController atendimentoController = new AtendimentoController();
        atendimentoController.listarAtendimentosAtendidos(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                ArrayList<Atendido> atendidos = new ArrayList<>();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id_atendimento = object.getString(
                                    "id_atendimento");

                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {

                                Atendido atendido = new Atendido();
                                atendido.setId(Integer.valueOf(object.getString("id_atendido")));
                                atendido.setNomeCompleto(object.getString("nomeCompleto"));
                                atendido.setNomeGuerra(object.getString("nomeGuerra"));
                                atendido.setRgMilitar(object.getString("rgMilitar"));
                                atendido.setCpf(object.getString("cpf"));

                                atendidos.add(atendido);
                                txtAtendido.setText(atendidos.toString().
                                        replace("[", "").
                                        replace("]", ""));

                            }

                        }

                                               }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void recuperarAtendimentosDeslocamentos()
    {
        AtendimentoController atendimentoController = new AtendimentoController();
        atendimentoController.listarAtendimentosDeslocamentos(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                ArrayList<Deslocamento> deslocamentos = new ArrayList<>();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id_atendimento = object.getString(
                                    "id_atendimento");

                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {

                                Deslocamento deslocamento = new Deslocamento();
                                deslocamento.setId(Integer.valueOf(object.getString("id_deslocamento")));
                                deslocamento.setDescricao(object.getString("descricao"));

                                deslocamentos.add(deslocamento);
                                txtDeslocamento.setText(deslocamentos.toString().
                                        replace("[", "").
                                        replace("]", ""));

                            }

                        }

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void recuperarAtendimentosDocumentosProduzidos()
    {
        AtendimentoController atendimentoController = new AtendimentoController();
        atendimentoController.listarAtendimentosDocumentosProduzidos(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                ArrayList<DocumentoProduzido> documentosProduzidos = new ArrayList<>();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id_atendimento = object.getString(
                                    "id_atendimento");

                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {

                                DocumentoProduzido documentoProduzido = new DocumentoProduzido();
                                documentoProduzido.setId(Integer.valueOf(object.getString("id_documento_produzido")));
                                documentoProduzido.setDescricao(object.getString("descricao"));

                                documentosProduzidos.add(documentoProduzido);
                                txtDocumentoProduzido.setText(documentosProduzidos.toString().
                                        replace("[", "").
                                        replace("]", ""));

                            }

                        }

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void recuperarAtendimentosDemandasEspecificas()
    {
        AtendimentoController atendimentoController = new AtendimentoController();
        atendimentoController.listarAtendimentosDemandasEspecificas(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                ArrayList<DemandaEspecifica> demandaEspecificas = new ArrayList<>();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id_atendimento = object.getString(
                                    "id_atendimento");

                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {

                                DemandaEspecifica demandaEspecifica = new DemandaEspecifica();
                                demandaEspecifica.setId(Integer.valueOf(object.getString("id_demanda_especifica")));
                                demandaEspecifica.setDescricao(object.getString("descricao"));

                                demandaEspecificas.add(demandaEspecifica);
                                txtDemandaEspecifica.setText(demandaEspecificas.toString().
                                        replace("[", "").
                                        replace("]", ""));

                            }

                        }

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}