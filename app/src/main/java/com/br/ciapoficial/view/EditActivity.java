package com.br.ciapoficial.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity {

    private EditText edtTextId, edtTextNome, edtTextEmail;
    private UserModel usuarioDestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtTextId = (EditText) findViewById(R.id.ed_id);
        edtTextNome = (EditText) findViewById(R.id.ed_nome);
        edtTextEmail = (EditText) findViewById(R.id.ed_email);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            usuarioDestinatario = (UserModel) bundle.getSerializable("usuarioSelecionado");
            edtTextId.setText(String.valueOf(usuarioDestinatario.getId()));
            edtTextNome.setText(usuarioDestinatario.getName());
            edtTextEmail.setText(usuarioDestinatario.getEmail());
        }

    }

    public void updateData(View view) {

        Usuario userModel = new Usuario();
        userModel.setId(Integer.valueOf(edtTextId.getText().toString()));
        userModel.setNomeCompleto(edtTextNome.getText().toString());
        userModel.setEmail((edtTextEmail.getText().toString()));

        UsuarioController usuarioController = new UsuarioController();
        usuarioController.atualizarUsuario(getApplicationContext(), userModel, new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean isErro = jsonObject.getBoolean("erro");

                    if(isErro) {
                        Toast.makeText(EditActivity.this,
                                "Falha ao atualizar os dados",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EditActivity.this,
                                "Dados atualizados com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException jsone) {
                    jsone.printStackTrace();
                }
            }

        });

    }
}