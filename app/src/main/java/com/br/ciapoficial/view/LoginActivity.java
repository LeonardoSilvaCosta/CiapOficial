package com.br.ciapoficial.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.AuthenticationController;
import com.br.ciapoficial.controller.FuncionarioController;
import com.br.ciapoficial.controller.HomeController;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.validation.FieldValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewLogin, txtRecuperarSenha;
    private TextInputEditText textInputEditTextEmail, textInputEditTextSenha;
    private Button btnEntrar;

    private SharedPreferences sharedPreferences;
    public final static String FILE_NAME = "userPrefs";
    public final static String USER_ID = "userId";
    public final static String USER_SEX = "userSex";
    public final static String TOKEN = "token";

    private Dialog dialog;

    //Verificar se a melhor opção é enviar o email para redefinição pelo header ou pelo body ou como
    //param
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        configurarComponentes();

        txtRecuperarSenha.setOnClickListener(v -> recuperarSenha());

        entrar();
    }

    private void configurarComponentes()
    {
        txtRecuperarSenha = findViewById(R.id.txtRecuperarSenha);
        textViewLogin = findViewById(R.id.txtLogin);
        textInputEditTextEmail = findViewById(R.id.txtInputEdtEmail);
        textInputEditTextSenha = findViewById(R.id.txtInputedtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
    }

    private boolean validarCampos() {

        String senha = Objects.requireNonNull(textInputEditTextSenha.getText()).toString().trim();

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

        btnEntrar.setOnClickListener(v -> {

            if(validarCampos()) {
                UserModel usuario = new UserModel();
                usuario.setEmail(Objects.requireNonNull(textInputEditTextEmail.getText()).toString().trim());
                usuario.setSenha(Objects.requireNonNull(textInputEditTextSenha.getText()).toString().trim());

                AuthenticationController authenticationController = new AuthenticationController();
                authenticationController.logarUsuario(getApplicationContext(), usuario, response -> {
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        String token = jsonObject.getString("jwtToken");

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(TOKEN, "Bearer "+ token);
                        editor.apply();

                        salvarDadosDaHomeEmSharedPreferences();

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

        });
    }

    private void salvarDadosDaHomeEmSharedPreferences() {
        HomeController homeController = new HomeController();
        homeController.get(getApplicationContext(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) throws JSONException {
                try {
                    JSONObject jsonObject =  new JSONObject(response);

                    Gson gson = new Gson();
                    Funcionario funcionario;
                    funcionario = gson.fromJson(jsonObject.toString(), Funcionario.class);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USER_ID, String.valueOf(funcionario.getId()));
                    editor.putString(USER_SEX, funcionario.getSexo().getNome());
                    editor.apply();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void recuperarSenha () {
        dialog = new Dialog(LoginActivity.this);

        dialog.setContentView(R.layout.alert_dialog_recuperar_senha);

        TextInputEditText edtEnviarEmail = dialog.findViewById(R.id.txtInputEdtEnviarEmail);
        Button btnEnviarEmail = dialog.findViewById(R.id.btnEnviarEmail);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);

        btnEnviarEmail.setOnClickListener(v -> {

            String email = Objects.requireNonNull(edtEnviarEmail.getText()).toString();

            FuncionarioController funcionarioController = new FuncionarioController();
            funcionarioController.recuperarSenha(getApplicationContext(), email, response -> {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String mensagem = jsonObject.getString("resposta");

                        Toast.makeText(LoginActivity.this,
                                mensagem,
                                Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            dialog.dismiss();

        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }

}