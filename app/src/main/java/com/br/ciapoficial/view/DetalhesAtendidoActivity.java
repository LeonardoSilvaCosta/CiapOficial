package com.br.ciapoficial.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.model.Atendido;

public class DetalhesAtendidoActivity extends AppCompatActivity {

    private TextView txtNome, txtDataNascimento, txtCpf, txtSexo, txtEmail, txtEstadoCivil,
    txtNaturalidade, txtEscolaridade, txtNumeroFilhos, txtEndereco, txtRgMilitar, txtPostoGradCat,
    txtUnidade, txtQuadro, txtDataInclusao, txtSituacaoFuncional, txtDataHoraCadastro;
    private Atendido atendidoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atendido);

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtDataNascimento = (TextView) findViewById(R.id.txtDataNascimento);
        txtCpf = (TextView) findViewById(R.id.txtCpf);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEstadoCivil = (TextView) findViewById(R.id.txtEstadoCivil);
        txtNaturalidade = (TextView) findViewById(R.id.txtNaturalidade);
        txtEscolaridade = (TextView) findViewById(R.id.txtEscolaridade);
        txtNumeroFilhos = (TextView) findViewById(R.id.txtNumeroFilhos);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        txtRgMilitar = (TextView) findViewById(R.id.txtRgMilitar);
        txtPostoGradCat= (TextView) findViewById(R.id.txtPostoGradCat);
        txtUnidade= (TextView) findViewById(R.id.txtUnidade);
        txtQuadro= (TextView) findViewById(R.id.txtQuadro);
        txtDataInclusao= (TextView) findViewById(R.id.txtDataInclusao);
        txtSituacaoFuncional= (TextView) findViewById(R.id.txtSituacaoFuncional);
        txtDataHoraCadastro= (TextView) findViewById(R.id.txtDataHoraCadastro);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            atendidoSelecionado = (Atendido) bundle.getSerializable("atendidoSelecionado");
            txtNome.setText(atendidoSelecionado.getNomeCompleto());
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
            txtRgMilitar.setText(atendidoSelecionado.getRgMilitar());
            txtPostoGradCat.setText(atendidoSelecionado.getPostoGradCat());
            txtUnidade.setText(atendidoSelecionado.getUnidade());
            txtQuadro.setText(atendidoSelecionado.getQuadro());
            txtDataInclusao.setText(atendidoSelecionado.getDataInclusao());
            txtSituacaoFuncional.setText(atendidoSelecionado.getSituacaoFuncional());
            txtDataHoraCadastro.setText(atendidoSelecionado.getDataHoraCadastro());
        }
    }

}