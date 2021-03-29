package com.br.ciapoficial.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.view.MainActivity;

public class DetailActivity extends AppCompatActivity {

    TextView txtId, txtNome, txtEmail;
    private UserModel usuarioDestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtId = (TextView) findViewById(R.id.txtIdDetail);
        txtNome = (TextView) findViewById(R.id.txtNomeDetail);
        txtEmail = (TextView) findViewById(R.id.txtEmailDetail);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            usuarioDestinatario = (UserModel) bundle.getSerializable("usuarioSelecionado");
            txtId.setText(String.valueOf(usuarioDestinatario.getId()));
            txtNome.setText(usuarioDestinatario.getName());
            txtEmail.setText(usuarioDestinatario.getEmail());
        }
    }


}