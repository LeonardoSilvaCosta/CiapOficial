package com.br.ciapoficial.view;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.model.Servico;

public class DetalhesAtendimentoActivity extends AppCompatActivity {

    private TextView txtData, txtOficialResponsavel, txtAtendido, txtUnidade, txtModalidade, txtAcesso,
    txtTipo, txtAvaliacao, txtPrograma, txtDeslocamento, txtDemandaGeral, txtDemandaEspecifica,
            txtCondicaoLaboral, txtProcedimento, txtDocumentoProduzido,
            txtAfastamento, txtEvolucao, txtDataHoraCadastro;
    private Servico servicoSelecionado;

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            servicoSelecionado = (Servico) bundle.getSerializable("servicoSelecionado");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtData.setText(DateFormater.localDateToString(servicoSelecionado.getData())); }
            servicoSelecionado.getEspecialistas().forEach(e -> txtOficialResponsavel.setText(e.toString()));
            servicoSelecionado.getUsuarios().forEach(e -> txtAtendido.setText(e.toString()));
            txtUnidade.setText(servicoSelecionado.getUnidade().toString());
            txtModalidade.setText(servicoSelecionado.getModalidade().toString());
            txtAcesso.setText(servicoSelecionado.getAcesso().toString());
            txtTipo.setText("ainda implementando...");
//            txtAvaliacao.setText(atendimentoSelecionado.getTipoAvaliacao());
            txtPrograma.setText(servicoSelecionado.getPrograma().toString());
            txtDemandaGeral.setText(servicoSelecionado.getDemandaGeral().toString());
            servicoSelecionado.getDemandasEspecificas().forEach(e -> txtDemandaEspecifica.setText(e.toString()));
//            txtCondicaoLaboral.setText(atendimentoSelecionado.getCondicaoLaboral());
            txtProcedimento.setText(servicoSelecionado.getProcedimento().toString());
            servicoSelecionado.getDocumentosProduzidos().forEach(e -> txtDocumentoProduzido.setText(e.toString()));
            if(servicoSelecionado.isAfastamento())
            {
                txtAfastamento.setText("Sim");
            }else
            {
                txtAfastamento.setText("Não");
            }
            txtEvolucao.setText(servicoSelecionado.getEvolucao());
//            txtDataHoraCadastro.setText(servicoSelecionado.getDataHoraCadastro().toString());
        }

//        recuperarAtendimentosOficiais();
//        recuperarAtendimentosAtendidos();
//        recuperarAtendimentosDeslocamentos();
//        recuperarAtendimentosDocumentosProduzidos();
//        recuperarAtendimentosDemandasEspecificas();

    }

//    public void recuperarAtendimentosOficiais()
//    {
//        AtendimentoController atendimentoController = new AtendimentoController();
//        atendimentoController.listarAtendimentosOficiais(getApplicationContext(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                Log.e("ResA", response);
//
//                ArrayList<Funcionario> funcionarios = new ArrayList<>();
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            String id_atendimento = object.getString(
//                                    "id_atendimento");
//
//                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {
//
//                                Funcionario funcionario = new Funcionario();
//                                funcionario.setId(Integer.valueOf(object.getString("id_oficial")));
//                                funcionario.setNomeCompleto(object.getString("nomeCompleto"));
//                                funcionario.setNomeGuerra(object.getString("nomeGuerra"));
//                                funcionario.setPostoGradCat(object.getString("postoGradCat"));
//                                funcionario.setRgMilitar(object.getString("rgMilitar"));
//                                funcionario.setCpf(object.getString("cpf"));
//
//                                funcionarios.add(funcionario);
//                                txtOficialResponsavel.setText(funcionarios.toString().
//                                        replace("[", "").
//                                        replace("]", ""));
//
//                            }
//
//                        }
//
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    public void recuperarAtendimentosAtendidos()
//    {
//        AtendimentoController atendimentoController = new AtendimentoController();
//        atendimentoController.listarAtendimentosAtendidos(getApplicationContext(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                ArrayList<Usuario> usuarios = new ArrayList<>();
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            String id_atendimento = object.getString(
//                                    "id_atendimento");
//
//                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {
//
//                                Usuario usuario = new Usuario();
//                                usuario.setId(Integer.valueOf(object.getString("id_atendido")));
//                                usuario.setNomeCompleto(object.getString("nomeCompleto"));
//                                usuario.setNomeGuerra(object.getString("nomeGuerra"));
//                                usuario.setRgMilitar(object.getString("rgMilitar"));
//                                usuario.setCpf(object.getString("cpf"));
//
//                                usuarios.add(usuario);
//                                txtAtendido.setText(usuarios.toString().
//                                        replace("[", "").
//                                        replace("]", ""));
//
//                            }
//
//                        }
//
//                                               }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    public void recuperarAtendimentosDeslocamentos()
//    {
//        AtendimentoController atendimentoController = new AtendimentoController();
//        atendimentoController.listarAtendimentosDeslocamentos(getApplicationContext(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                ArrayList<Deslocamento> deslocamentos = new ArrayList<>();
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            String id_atendimento = object.getString(
//                                    "id_atendimento");
//
//                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {
//
//                                Deslocamento deslocamento = new Deslocamento();
//                                deslocamento.setId(Integer.valueOf(object.getString("id_deslocamento")));
//                                deslocamento.setDescricao(object.getString("descricao"));
//
//                                deslocamentos.add(deslocamento);
//                                txtDeslocamento.setText(deslocamentos.toString().
//                                        replace("[", "").
//                                        replace("]", ""));
//
//                            }
//
//                        }
//
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    public void recuperarAtendimentosDocumentosProduzidos()
//    {
//        AtendimentoController atendimentoController = new AtendimentoController();
//        atendimentoController.listarAtendimentosDocumentosProduzidos(getApplicationContext(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                ArrayList<DocumentoProduzido> documentosProduzidos = new ArrayList<>();
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            String id_atendimento = object.getString(
//                                    "id_atendimento");
//
//                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {
//
//                                DocumentoProduzido documentoProduzido = new DocumentoProduzido();
//                                documentoProduzido.setId(Integer.valueOf(object.getString("id_documento_produzido")));
//                                documentoProduzido.setDescricao(object.getString("descricao"));
//
//                                documentosProduzidos.add(documentoProduzido);
//                                txtDocumentoProduzido.setText(documentosProduzidos.toString().
//                                        replace("[", "").
//                                        replace("]", ""));
//
//                            }
//
//                        }
//
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    public void recuperarAtendimentosDemandasEspecificas()
//    {
//        AtendimentoController atendimentoController = new AtendimentoController();
//        atendimentoController.listarAtendimentosDemandasEspecificas(getApplicationContext(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                ArrayList<DemandaEspecifica> demandaEspecificas = new ArrayList<>();
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            String id_atendimento = object.getString(
//                                    "id_atendimento");
//
//                            if(id_atendimento.equals(String.valueOf(atendimentoSelecionado.getId()))) {
//
//                                DemandaEspecifica demandaEspecifica = new DemandaEspecifica();
//                                demandaEspecifica.setId(Integer.valueOf(object.getString("id_demanda_especifica")));
//                                demandaEspecifica.setDescricao(object.getString("descricao"));
//
//                                demandaEspecificas.add(demandaEspecifica);
//                                txtDemandaEspecifica.setText(demandaEspecificas.toString().
//                                        replace("[", "").
//                                        replace("]", ""));
//
//                            }
//
//                        }
//
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
}