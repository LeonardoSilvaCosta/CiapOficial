package com.br.ciapoficial.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.model.Usuario;

public class DetalhesAtendidoActivity extends AppCompatActivity {

    private LinearLayout linearLayoutRgMilitar, linearLayoutPostoGradCat, linearLayoutNomeGuerra, linearLayoutUnidade,
    linearLayoutQuadro, linearLayoutDataInclusao, linearLayoutSituacaoFuncional, linearLayoutIdTitular,
    linearLayoutVinculo, linearLayoutDataHoraAtualizacao;
    private TextView txtTipoAtendido, txtNomeCompleto,  txtDataNascimento, txtCpf, txtSexo, txtEmail, txtEstadoCivil,
    txtNaturalidade, txtEscolaridade, txtNumeroFilhos, txtEndereco, txtRgMilitar, txtPostoGradCat,
    txtNomeGuerra, txtUnidade, txtQuadro, txtDataInclusao, txtSituacaoFuncional, txtIdTitular,
            txtTitularHyperlink, txtVinculo, txtDataHoraCadastro, txtDataHoraAtualizacao;

    private Usuario usuarioSelecionado;
    private Usuario titularRecuperado = new Usuario();

    @RequiresApi(api = Build.VERSION_CODES.O)
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
//        solicitarProntuarioDoTitular();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getExtras()
    {
        Bundle bundle = getIntent().getExtras();

        if(bundle.getSerializable("atendidoSelecionado") != null)
        {
            usuarioSelecionado = (Usuario) bundle.getSerializable("atendidoSelecionado");
            txtNomeCompleto.setText(usuarioSelecionado.getNomeCompleto());
            txtDataNascimento.setText(DateFormater.localDateToString(usuarioSelecionado.getDataNascimento()));
            txtCpf.setText(usuarioSelecionado.getCpf());
            txtSexo.setText(usuarioSelecionado.getSexo().getNome());
            txtEmail.setText(usuarioSelecionado.getEmail());
            txtEstadoCivil.setText(usuarioSelecionado.getEstadoCivil().getNome());
            txtNaturalidade.setText(usuarioSelecionado.getNaturalidade().getNome());
            txtEscolaridade.setText(usuarioSelecionado.getEscolaridade().getNome());
            txtNumeroFilhos.setText(String.valueOf(usuarioSelecionado.getNumeroFilhos()));
            txtEndereco.setText(usuarioSelecionado.getEndereco().toString());
            txtDataHoraCadastro.setText(DateFormater.localDateTimeToString(usuarioSelecionado.getDataCadastro()));
            if(usuarioSelecionado.getDataEdicao() != null) {
                txtDataHoraAtualizacao.setText(DateFormater.localDateTimeToString(usuarioSelecionado.getDataEdicao()));
            }else {
                linearLayoutDataHoraAtualizacao.setVisibility(View.GONE);
                txtDataHoraAtualizacao.setVisibility(View.GONE);
            }

            if(usuarioSelecionado.getRgMilitar() != null && !usuarioSelecionado.getRgMilitar().isEmpty()) {
                txtNomeGuerra.setText(usuarioSelecionado.getNomeGuerra());
                txtRgMilitar.setText(usuarioSelecionado.getRgMilitar());
                txtPostoGradCat.setText(usuarioSelecionado.getPostoGradCat().getNome());
                txtUnidade.setText(usuarioSelecionado.getUnidade().getNome());
                txtQuadro.setText(usuarioSelecionado.getQuadro().getNome());
                txtDataInclusao.setText(DateFormater.localDateToString(usuarioSelecionado.getDataInclusao()));
                txtSituacaoFuncional.setText(usuarioSelecionado.getSituacaoFuncional().getNome());

                txtTipoAtendido.setText("PM");

                linearLayoutIdTitular.setVisibility(View.GONE);
                linearLayoutVinculo.setVisibility(View.GONE);
            }else if(usuarioSelecionado.getTipoVinculo() != null && !usuarioSelecionado.getTipoVinculo().getNome().isEmpty()) {
                txtIdTitular.setText((usuarioSelecionado.getTitular().toString()));
                txtVinculo.setText(usuarioSelecionado.getTipoVinculo().getNome());

                txtTipoAtendido.setText("Dependente");

                linearLayoutNomeGuerra.setVisibility(View.GONE);
                linearLayoutRgMilitar.setVisibility(View.GONE);
                linearLayoutPostoGradCat.setVisibility(View.GONE);
                linearLayoutUnidade.setVisibility(View.GONE);
                linearLayoutQuadro.setVisibility(View.GONE);
                linearLayoutDataInclusao.setVisibility(View.GONE);
                linearLayoutSituacaoFuncional.setVisibility(View.GONE);

            }else {
                txtTipoAtendido.setText("Civil");

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

//    public void solicitarProntuarioDoTitular() {
//        txtTitularHyperlink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recuperarDadosAtendidoTitular();
//            }
//        });
//    }
//
//    public void recuperarDadosAtendidoTitular() {
//
//        UsuarioController usuarioController = new UsuarioController();
//        usuarioController.listar(getApplicationContext(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
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
//
//                            if(object.getInt("id") == usuarioSelecionado.getTitular().getId()) {
//                                titularRecuperado.setId(usuarioSelecionado.getId());
//                                titularRecuperado.setDataNascimento(
//                                        DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataNascimento")));
//                                titularRecuperado.setTipoAtendido(object.getString("tipoAtendido"));
//                                titularRecuperado.setNomeCompleto(object.getString("nomeCompleto"));
//                                titularRecuperado.setCpf(PadraoDeVisualizacao.visualizarCpf(object.getString("cpf")));
//                                titularRecuperado.setSexo(object.getString("sexo"));
//                                titularRecuperado.setEmail(object.getString("email"));
//                                titularRecuperado.setEstadoCivil(object.getString("estadoCivil"));
//                                titularRecuperado.setCidadeNatal(object.getString("cidadeNatal"));
//                                titularRecuperado.setUfNatal(object.getString("ufNatal"));
//                                titularRecuperado.setEscolaridade(object.getString("escolaridade"));
//                                titularRecuperado.setNumeroFilhos(object.getString("numeroFilhos"));
//                                titularRecuperado.setCep(object.getString("cep"));
//                                titularRecuperado.setCidade(object.getString("cidade"));
//                                titularRecuperado.setUf(object.getString("uf"));
//                                titularRecuperado.setBairro(object.getString("bairro"));
//                                titularRecuperado.setLogradouro(object.getString("logradouro"));
//                                titularRecuperado.setNumero(object.getString("numero"));
//                                titularRecuperado.setDataHoraCadastro(
//                                        DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraCadastro")));
//                                titularRecuperado.setRgMilitar(object.getString("rgMilitar"));
//                                titularRecuperado.setPostoGradCat(object.getString("postoGradCat"));
//                                titularRecuperado.setNomeGuerra(object.getString("nomeGuerra"));
//                                titularRecuperado.setUnidade(object.getString("unidade"));
//                                titularRecuperado.setQuadro(object.getString("quadro"));
//                                titularRecuperado.setDataInclusao(
//                                        DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataInclusao")));
//                                titularRecuperado.setSituacaoFuncional(object.getString("situacaoFuncional"));
//
//                                String atualizacao = object.getString("dataHoraAtualizacao");
//                                if(atualizacao.equals("null")) {
//                                    titularRecuperado.setDataHoraAtualizacao(null);
//                                }else {
//                                    titularRecuperado.setDataHoraAtualizacao(
//                                            DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraAtualizacao")));
//                                }
//                            }
//
//                            irParaProntuarioDoTitular(titularRecuperado);
//                        }
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
//    public void irParaProntuarioDoTitular(Usuario titularRecuperado)
//    {
//        Intent i = getIntent();
//        i.putExtra("atendidoSelecionado", titularRecuperado);
//        startActivity(i);
//        //DetalhesAtendidoActivity.this.finish();
//    }

}