package com.br.ciapoficial.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.FuncionarioController;
import com.br.ciapoficial.helper.FieldValidator;
import com.br.ciapoficial.helper.PersistentCookieStore;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewLogin, txtRecuperarSenha;
    private TextInputEditText textInputEditTextEmail, textInputEditTextSenha;
    private Button btnEntrar;

    private SharedPreferences sharedPreferences;
    public static final String FILE_NAME = "login";
    public static final String USER_ID = "userId";
    public static final String USER_SEX = "userSex";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASS = "userPass";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(this),
                CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);

        txtRecuperarSenha = findViewById(R.id.txtRecuperarSenha);
        textViewLogin = findViewById(R.id.txtLogin);
        textInputEditTextEmail = findViewById(R.id.txtInputEdtEmail);
        textInputEditTextSenha = findViewById(R.id.txtInputedtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);

        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(USER_EMAIL)) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

        txtRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarSenha();
            }
        });

        entrar();
    }

    private boolean validarCampos() {

        String senha = textInputEditTextSenha.getText().toString();

            if(FieldValidator.validarEmail(textInputEditTextEmail)) {
                if(!senha.isEmpty()) {
                    return true;
                }
                else{
                    textInputEditTextSenha.setError("Preencha o campo de senha.");
                    textInputEditTextSenha.requestFocus();
                    return false; }

        }
        else{

            textInputEditTextEmail.setError("Preencha o campo de email.");
            textInputEditTextEmail.requestFocus();
            return false; }

    }

    private void entrar() {

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCampos()) {
                    UserModel usuario = new UserModel();
                    usuario.setEmail(textInputEditTextEmail.getText().toString());
                    usuario.setSenha(textInputEditTextSenha.getText().toString());

                    FuncionarioController funcionarioController = new FuncionarioController();
                    funcionarioController.logarUsuario(getApplicationContext(), usuario, new IVolleyCallback() {
                        @Override
                        public void onSucess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                    int id = jsonObject.getInt("id");
                                    String sexo = jsonObject.getString("sexo");

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(USER_ID, String.valueOf(id));
                                    editor.putString(USER_EMAIL, usuario.getEmail());
                                    editor.putString(USER_SEX, sexo);
                                    editor.putString(USER_PASS, usuario.getSenha());
                                    editor.commit();

                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                }

            }
        });

    }

    private void recuperarSenha () {
        dialog = new Dialog(LoginActivity.this);

        dialog.setContentView(R.layout.alert_dialog_recuperar_senha);

        TextInputEditText edtEnviarEmail = (TextInputEditText) dialog.findViewById(R.id.txtInputEdtEnviarEmail);
        Button btnEnviarEmail = (Button) dialog.findViewById(R.id.btnEnviarEmail);
        Button btnCancelar = (Button) dialog.findViewById(R.id.btnCancelar);

        btnEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEnviarEmail.getText().toString();

                FuncionarioController funcionarioController = new FuncionarioController();
                funcionarioController.recuperarSenha(getApplicationContext(), email, new IVolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");
                            boolean emailErro = jsonObject.getBoolean("email");

                            if (isErro) {

                                Toast.makeText(LoginActivity.this,
                                        "Email não cadastrado!",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                if(emailErro) {
                                    Toast.makeText(LoginActivity.this,
                                            "O email não pôde ser enviado",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(LoginActivity.this,
                                            "Uma mensagem com a sua senha foi enviada para o seu email. ",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

                dialog.dismiss();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();

    }

}