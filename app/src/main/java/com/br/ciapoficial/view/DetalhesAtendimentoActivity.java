package com.br.ciapoficial.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.model.Servico;
import com.br.ciapoficial.model.in_servico.Atendimento;
import com.br.ciapoficial.model.in_servico.Avaliacao;
import com.br.ciapoficial.model.in_servico.ServicoDeAssistenciaEspecial;

public class DetalhesAtendimentoActivity extends AppCompatActivity {

    private TextView txtData, txtOficialResponsavel, txtAtendido, txtUnidade, txtModalidade,
            txtAcesso, txtTipo, txtPrograma, txtDeslocamento, txtDemandaGeral,
            txtDemandaEspecifica, txtProcedimento, txtDocumentoProduzido,
            txtAfastamento, txtEvolucao, txtDataHoraCadastro, txtResponsavelCadastro,
            txtSinalSintoma, txtMedicaoPsiquiatrica, txtAvaliacao, txtCondicaoLaboral;
    private TextView legendaSinalSintoma, legendaMedicacaoPsiquiatrica, legendaAvaliacao,
            legendaCondicaoLaboral;
    private Servico servicoSelecionado;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atendimento);

        legendaSinalSintoma = findViewById(R.id.txtLegendaSinalSintoma);
        legendaMedicacaoPsiquiatrica = findViewById(R.id.txtLegendaMedicacaoPsiquiatrica);
        legendaAvaliacao = findViewById(R.id.txtLegendaAvaliacao);
        legendaCondicaoLaboral = findViewById(R.id.txtLegendaCondicaoLaboral);
        txtTipo = findViewById(R.id.txtTipo);
        txtData = findViewById(R.id.txtData);
        txtOficialResponsavel = findViewById(R.id.txtOficialResponsavel);
        txtAtendido = findViewById(R.id.txtAtendido);
        txtUnidade = findViewById(R.id.txtUnidade);
        txtModalidade = findViewById(R.id.txtModalidade);
        txtAcesso = findViewById(R.id.txtAcesso);
        txtPrograma = findViewById(R.id.txtPrograma);
        txtDeslocamento = findViewById(R.id.txtDeslocamento);
        txtDemandaGeral = findViewById(R.id.txtDemandaGeral);
        txtDemandaEspecifica = findViewById(R.id.txtDemandaEspecifica);
        txtProcedimento = findViewById(R.id.txtProcedimento);
        txtDocumentoProduzido = findViewById(R.id.txtDocumentoProduzido);
        txtAfastamento = findViewById(R.id.txtAfastamento);
        txtEvolucao = findViewById(R.id.txtEvolucao);
        txtDataHoraCadastro = findViewById(R.id.txtDataHoraCadastro);
        txtResponsavelCadastro = findViewById(R.id.txtResponsavelCadastro);
        txtAvaliacao = findViewById(R.id.txtAvaliacao);
        txtCondicaoLaboral = findViewById(R.id.txtCondicaoLaboral);
        txtSinalSintoma = findViewById(R.id.txtSinalSintoma);
        txtMedicaoPsiquiatrica = findViewById(R.id.txtMedicacaoPsiquiatrica);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            servicoSelecionado = (Servico) bundle.getSerializable("servicoSelecionado");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtData.setText(DateFormater.localDateToString(servicoSelecionado.getData())); }
            txtOficialResponsavel.setText(servicoSelecionado.getEspecialistas().toString()
                    .replace("[", "").replace("]", ""));
            txtAtendido.setText(servicoSelecionado.getUsuarios().toString()
                    .replace("[", "").replace("]", ""));
            txtUnidade.setText(servicoSelecionado.getUnidade().toString());
            txtModalidade.setText(servicoSelecionado.getModalidade().toString());
            txtAcesso.setText(servicoSelecionado.getAcesso().toString());
            txtPrograma.setText(servicoSelecionado.getPrograma().toString());
            txtDeslocamento.setText(servicoSelecionado.getDeslocamentos().toString()
                    .replace("[", "")
                    .replace("]", ""));
            txtDemandaGeral.setText(servicoSelecionado.getDemandaGeral().toString());
            txtDeslocamento.setText(servicoSelecionado.getDemandasEspecificas().toString()
                    .replace("[", "")
                    .replace("]", ""));
            txtProcedimento.setText(servicoSelecionado.getProcedimento().toString());
            txtDocumentoProduzido.setText(servicoSelecionado.getDocumentosProduzidos().toString()
                    .replace("[", "")
                    .replace("]", ""));
            if(servicoSelecionado.isAfastamento())
            {
                txtAfastamento.setText("Sim");
            }else
            {
                txtAfastamento.setText("Não");
            }
            txtEvolucao.setText(servicoSelecionado.getEvolucao());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtDataHoraCadastro.setText(DateFormater.localDateTimeToString(servicoSelecionado.getDataCadastro()));
            }
            txtResponsavelCadastro.setText(servicoSelecionado.getResponsavelCadastroServico().toString());
        }

        if(servicoSelecionado instanceof Atendimento) {
            txtSinalSintoma.setText(((Atendimento) servicoSelecionado).getSinaisSintomas().toString()
                    .replace("[", "")
                    .replace("]", ""));
            txtMedicaoPsiquiatrica.setText(((Atendimento) servicoSelecionado).getMedicacoesPsiquiatricas().toString()
                    .replace("[", "")
                    .replace("]", ""));
            txtTipo.setText("Atendimento");

            legendaAvaliacao.setVisibility(View.GONE);
            txtAvaliacao.setVisibility(View.GONE);
            legendaCondicaoLaboral.setVisibility(View.GONE);
            txtCondicaoLaboral.setVisibility(View.GONE);
        }else if(servicoSelecionado instanceof Avaliacao) {
            txtAvaliacao.setText(((Avaliacao) servicoSelecionado).getTipoAvaliacao().toString());
            txtTipo.setText("Avaliação");

            legendaSinalSintoma.setVisibility(View.GONE);
            txtSinalSintoma.setVisibility(View.GONE);
            legendaMedicacaoPsiquiatrica.setVisibility(View.GONE);
            txtMedicaoPsiquiatrica.setVisibility(View.GONE);
            legendaCondicaoLaboral.setVisibility(View.GONE);
            txtCondicaoLaboral.setVisibility(View.GONE);
        }else {
            txtCondicaoLaboral.setText(((ServicoDeAssistenciaEspecial) servicoSelecionado).getCondicaoLaboral().toString());
            txtTipo.setText("SAE");

            legendaSinalSintoma.setVisibility(View.GONE);
            txtSinalSintoma.setVisibility(View.GONE);
            legendaMedicacaoPsiquiatrica.setVisibility(View.GONE);
            txtMedicaoPsiquiatrica.setVisibility(View.GONE);
            legendaAvaliacao.setVisibility(View.GONE);
            txtAvaliacao.setVisibility(View.GONE);
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