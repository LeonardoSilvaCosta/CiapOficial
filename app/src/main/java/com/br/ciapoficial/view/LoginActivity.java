package com.br.ciapoficial.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.network.FuncionarioController;
import com.br.ciapoficial.network.HomeController;
import com.br.ciapoficial.network.api.config.ApiModule;
import com.br.ciapoficial.network.api.dto.LoginRequest;
import com.br.ciapoficial.network.api.dto.LoginResponse;
import com.br.ciapoficial.validation.FieldValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static LoginActivity loginContextInstance;

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

    public static LoginActivity getInstance() {
        return loginContextInstance;
    }

    public static Context getContext(){
        return loginContextInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginContextInstance = this;

        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        logarComToken();

        configurarComponentes();

        txtRecuperarSenha.setOnClickListener(v -> recuperarSenha());

        logar();
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

    private void logar() {
        btnEntrar.setOnClickListener(v -> {
            if(validarCampos()) {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(Objects.requireNonNull(textInputEditTextEmail.getText()).toString().trim());
                loginRequest.setSenha(Objects.requireNonNull(textInputEditTextSenha.getText()).toString().trim());

                ApiModule apiModule = new ApiModule();
                Call<LoginResponse> login = apiModule.getRetrofit(
                        getContext(), sharedPreferences.getString("token", ""))
                        .logar(loginRequest);
                login.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                        if(response.isSuccessful()) {

                            LoginResponse loginResponse = response.body();
                            String token = loginResponse.getJwtToken();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(TOKEN, "Bearer "+ token);
                            editor.apply();

                            salvarDadosDaHomeEmSharedPreferences();

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    public void logarComToken() {
        String token = sharedPreferences.getString("token", "");

        if(!token.isEmpty()) {
            ApiModule apiModule = new ApiModule();
            Call<LoginResponse> loginByToken = apiModule.getRetrofit(
                    getContext(), token)
                    .authorization(token);
            loginByToken.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if(response.isSuccessful()) {

                        LoginResponse loginResponse = response.body();
                        String token = loginResponse.getJwtToken();

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Log.e("Login failed", "Login failed");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}