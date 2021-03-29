package com.br.ciapoficial.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewLogin, txtRecuperarSenha;
    private TextInputEditText textInputEditTextEmail, textInputEditTextSenha;
    private Button btnEntrar;

    private SharedPreferences sharedPreferences;
    public static final String fileName = "login";
    public static final String userId = "userId";
    public static final String userSex = "userSex";
    public static final String userEmail = "userEmail";
    public static final String userPass = "userPass";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRecuperarSenha = findViewById(R.id.txtRecuperarSenha);
        textViewLogin = findViewById(R.id.txtLogin);
        textInputEditTextEmail = findViewById(R.id.txtInputEdtEmail);
        textInputEditTextSenha = findViewById(R.id.txtInputedtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(userEmail)) {
            Intent i = new Intent(this, NovaAtividadeMae.class);
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

        String email = textInputEditTextEmail.getText().toString();
        String senha = textInputEditTextSenha.getText().toString();

        if(!TextUtils.isEmpty(email)) {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if(!senha.isEmpty()) {
                    return true;
                }
                else{
                    textInputEditTextSenha.setError("Preencha o campo de senha.");
                    textInputEditTextSenha.requestFocus();
                    return false; }

            }
            else{

                textInputEditTextEmail.setError("Digite um email válido.");
                textInputEditTextEmail.requestFocus();
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

                    UsuarioController usuarioController = new UsuarioController();
                    usuarioController.logarUsuario(getApplicationContext(), usuario, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                boolean isErro = jsonObject.getBoolean("erro");

                                if (isErro) {

                                    Toast.makeText(LoginActivity.this,
                                            "Usuário não encontrado!",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    int id = jsonObject.getInt("id");
                                    int sexo = jsonObject.getInt("sexo");

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(userId, String.valueOf(id));
                                    editor.putString(userEmail, usuario.getEmail());
                                    editor.putString(userSex, String.valueOf(sexo));
                                    editor.putString(userPass, usuario.getSenha());
                                    editor.commit();

                                    Intent i = new Intent(LoginActivity.this, NovaAtividadeMae.class);
                                    startActivity(i);
                                    finish();
                                }

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

                UsuarioController usuarioController = new UsuarioController();
                usuarioController.recuperarSenha(getApplicationContext(), email, new VolleyCallback() {
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