package com.br.ciapoficial.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.AtendidoController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.PadraoDeVisualizacao;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalhesAtendidoActivity extends AppCompatActivity {

    private LinearLayout linearLayoutRgMilitar, linearLayoutPostoGradCat, linearLayoutNomeGuerra, linearLayoutUnidade,
    linearLayoutQuadro, linearLayoutDataInclusao, linearLayoutSituacaoFuncional, linearLayoutIdTitular,
    linearLayoutVinculo, linearLayoutDataHoraAtualizacao;
    private TextView txtTipoAtendido, txtNomeCompleto,  txtDataNascimento, txtCpf, txtSexo, txtEmail, txtEstadoCivil,
    txtNaturalidade, txtEscolaridade, txtNumeroFilhos, txtEndereco, txtRgMilitar, txtPostoGradCat,
    txtNomeGuerra, txtUnidade, txtQuadro, txtDataInclusao, txtSituacaoFuncional, txtIdTitular,
            txtTitularHyperlink, txtVinculo, txtDataHoraCadastro, txtDataHoraAtualizacao;

    private Atendido atendidoSelecionado;
    private Atendido titularRecuperado = new Atendido();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atendido);

        linearLayoutRgMilitar = (LinearLayout) findViewById(R.id.linearLayoutRgMilitar);
        linearLayoutPostoGradCat = (LinearLayout) findViewById(R.id.linearLayoutPostoGradCat);
        linearLayoutNomeGuerra = (LinearLayout) findViewById(R.id.linearLayoutNomeGuerra);
        linearLayoutUnidade = (LinearLayout) findViewById(R.id.linearLayoutUnidade);
        linearLayoutQuadro = (LinearLayout) findViewById(R.id.linearLayoutQuadro);
        linearLayoutDataInclusao = (LinearLayout) findViewById(R.id.linearLayoutDataInclusao);
        linearLayoutSituacaoFuncional = (LinearLayout) findViewById(R.id.linearLayoutSituacaoFuncional);
        linearLayoutIdTitular = (LinearLayout) findViewById(R.id.linearLayoutIdTitular);
        linearLayoutVinculo = (LinearLayout) findViewById(R.id.linearLayoutVinculo);
        linearLayoutDataHoraAtualizacao = (LinearLayout) findViewById(R.id.linearLayoutDataHoraAtualizacao);
        txtTitularHyperlink = (TextView) findViewById(R.id.txtHyperlinkTitular);
        txtTipoAtendido = (TextView) findViewById(R.id.txtTipoAtendido);
        txtNomeCompleto = (TextView) findViewById(R.id.txtNome);
        txtDataNascimento = (TextView) findViewById(R.id.txtDataNascimento);
        txtCpf = (TextView) findViewById(R.id.txtCpf);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEstadoCivil = (TextView) findViewById(R.id.txtEstadoCivil);
        txtNaturalidade = (TextView) findViewById(R.id.txtNaturalidade);
        txtEscolaridade = (TextView) findViewById(R.id.txtEscolaridade);
        txtNumeroFilhos = (TextView) findViewById(R.id.txtNumeroFilhos);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        txtNomeGuerra = (TextView) findViewById(R.id.txtNomeGuerra);
        txtRgMilitar = (TextView) findViewById(R.id.txtRgMilitar);
        txtPostoGradCat= (TextView) findViewById(R.id.txtPostoGradCat);
        txtUnidade= (TextView) findViewById(R.id.txtUnidade);
        txtQuadro= (TextView) findViewById(R.id.txtQuadro);
        txtDataInclusao= (TextView) findViewById(R.id.txtDataInclusao);
        txtSituacaoFuncional= (TextView) findViewById(R.id.txtSituacaoFuncional);
        txtIdTitular = (TextView) findViewById(R.id.txtTitular);
        txtVinculo = (TextView) findViewById(R.id.txtVinculo);
        txtDataHoraCadastro= (TextView) findViewById(R.id.txtDataHoraCadastro);
        txtDataHoraAtualizacao= (TextView) findViewById(R.id.txtDataHoraAtualizacao);

        getExtras();
        solicitarProntuarioDoTitular();


    }

    public void getExtras()
    {
        Bundle bundle = getIntent().getExtras();

        if(bundle.getSerializable("atendidoSelecionado") != null)
        {
            atendidoSelecionado = (Atendido) bundle.getSerializable("atendidoSelecionado");
            txtTipoAtendido.setText(atendidoSelecionado.getTipoAtendido());
            txtNomeCompleto.setText(atendidoSelecionado.getNomeCompleto());
            txtDataNascimento.setText(atendidoSelecionado.getDataNascimento());
            txtCpf.setText(atendidoSelecionado.getCpf());
            txtSexo.setText(atendidoSelecionado.getSexo());
            txtEmail.setText(atendidoSelecionado.getEmail());
            txtEstadoCivil.setText(atendidoSelecionado.getEstadoCivil());
            txtNaturalidade.setText(atendidoSelecionado.getCidadeNatal() + "-" + atendidoSelecionado.getUfNatal());
            txtEscolaridade.setText(atendidoSelecionado.getEscolaridade());
            txtNumeroFilhos.setText(atendidoSelecionado.getNumeroFilhos());
            txtEndereco.setText(atendidoSelecionado.getLogradouro() + ", " + atendidoSelecionado.getNumero() + ", " +
                    atendidoSelecionado.getBairro() + ", " + atendidoSelecionado.getCidade() + "-" + atendidoSelecionado.getUf());
            txtDataHoraCadastro.setText(atendidoSelecionado.getDataHoraCadastro());

            String atualizacao = atendidoSelecionado.getDataHoraAtualizacao();
            if(atualizacao != null && !atualizacao.isEmpty()) {
                txtDataHoraAtualizacao.setText(atendidoSelecionado.getDataHoraAtualizacao());

            }else {
                linearLayoutDataHoraAtualizacao.setVisibility(View.GONE);
            }


            if(atendidoSelecionado.getTipoAtendido().equals("Policial Militar")) {
                txtNomeGuerra.setText(atendidoSelecionado.getNomeGuerra());
                txtRgMilitar.setText(atendidoSelecionado.getRgMilitar());
                txtPostoGradCat.setText(atendidoSelecionado.getPostoGradCat());
                txtUnidade.setText(atendidoSelecionado.getUnidade());
                txtQuadro.setText(atendidoSelecionado.getQuadro());
                txtDataInclusao.setText(atendidoSelecionado.getDataInclusao());
                txtSituacaoFuncional.setText(atendidoSelecionado.getSituacaoFuncional());

                linearLayoutIdTitular.setVisibility(View.GONE);
                linearLayoutVinculo.setVisibility(View.GONE);
            }else if(atendidoSelecionado.getTipoAtendido().equals("Dependente")) {
                txtIdTitular.setText((atendidoSelecionado.getTitular().getNome()));
                txtVinculo.setText(atendidoSelecionado.getVinculo());

                linearLayoutNomeGuerra.setVisibility(View.GONE);
                linearLayoutRgMilitar.setVisibility(View.GONE);
                linearLayoutPostoGradCat.setVisibility(View.GONE);
                linearLayoutUnidade.setVisibility(View.GONE);
                linearLayoutQuadro.setVisibility(View.GONE);
                linearLayoutDataInclusao.setVisibility(View.GONE);
                linearLayoutSituacaoFuncional.setVisibility(View.GONE);

            }else {
                linearLayoutNomeGuerra.setVisibility(View.GONE);
                linearLayoutRgMilitar.setVisibility(View.GONE);
                linearLayoutPostoGradCat.setVisibility(View.GONE);
                linearLayoutUnidade.setVisibility(View.GONE);
                linearLayoutQuadro.setVisibility(View.GONE);
                linearLayoutDataInclusao.setVisibility(View.GONE);
                linearLayoutSituacaoFuncional.setVisibility(View.GONE);
                linearLayoutIdTitular.setVisibility(View.GONE);
                linearLayoutVinculo.setVisibility(View.GONE);
            }

        }

    }

    public void solicitarProntuarioDoTitular() {
        txtTitularHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarDadosAtendidoTitular();
            }
        });
    }

    public void recuperarDadosAtendidoTitular() {

        AtendidoController atendidoController = new AtendidoController();
        atendidoController.listarAtendidos(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            if(object.getInt("id") == atendidoSelecionado.getTitular().getId()) {
                                titularRecuperado.setId(atendidoSelecionado.getId());
                                titularRecuperado.setDataNascimento(
                                        DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataNascimento")));
                                titularRecuperado.setTipoAtendido(object.getString("tipoAtendido"));
                                titularRecuperado.setNomeCompleto(object.getString("nomeCompleto"));
                                titularRecuperado.setCpf(PadraoDeVisualizacao.visualizarCpf(object.getString("cpf")));
                                titularRecuperado.setSexo(object.getString("sexo"));
                                titularRecuperado.setEmail(object.getString("email"));
                                titularRecuperado.setEstadoCivil(object.getString("estadoCivil"));
                                titularRecuperado.setCidadeNatal(object.getString("cidadeNatal"));
                                titularRecuperado.setUfNatal(object.getString("ufNatal"));
                                titularRecuperado.setEscolaridade(object.getString("escolaridade"));
                                titularRecuperado.setNumeroFilhos(object.getString("numeroFilhos"));
                                titularRecuperado.setCep(object.getString("cep"));
                                titularRecuperado.setCidade(object.getString("cidade"));
                                titularRecuperado.setUf(object.getString("uf"));
                                titularRecuperado.setBairro(object.getString("bairro"));
                                titularRecuperado.setLogradouro(object.getString("logradouro"));
                                titularRecuperado.setNumero(object.getString("numero"));
                                titularRecuperado.setDataHoraCadastro(
                                        DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraCadastro")));
                                titularRecuperado.setRgMilitar(object.getString("rgMilitar"));
                                titularRecuperado.setPostoGradCat(object.getString("postoGradCat"));
                                titularRecuperado.setNomeGuerra(object.getString("nomeGuerra"));
                                titularRecuperado.setUnidade(object.getString("unidade"));
                                titularRecuperado.setQuadro(object.getString("quadro"));
                                titularRecuperado.setDataInclusao(
                                        DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataInclusao")));
                                titularRecuperado.setSituacaoFuncional(object.getString("situacaoFuncional"));

                                String atualizacao = object.getString("dataHoraAtualizacao");
                                if(atualizacao.equals("null")) {
                                    titularRecuperado.setDataHoraAtualizacao(null);
                                }else {
                                    titularRecuperado.setDataHoraAtualizacao(
                                            DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraAtualizacao")));
                                }
                            }

                            irParaProntuarioDoTitular(titularRecuperado);
                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void irParaProntuarioDoTitular(Atendido titularRecuperado)
    {
        Intent i = getIntent();
        i.putExtra("atendidoSelecionado", titularRecuperado);
        startActivity(i);
        //DetalhesAtendidoActivity.this.finish();
    }

}