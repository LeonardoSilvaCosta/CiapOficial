package com.br.ciapoficial.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.br.ciapoficial.R;
import com.br.ciapoficial.adapters.AdapterUserModelsTest;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.UserModel;
import com.br.ciapoficial.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.br.ciapoficial.view.LoginActivity.fileName;
import static com.br.ciapoficial.view.LoginActivity.userEmail;
import static com.br.ciapoficial.view.LoginActivity.userPass;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private Button getRequest, postRequest;
    private EditText idEditText, nameEditText, emailEditText;

    private ListView listView;
    private ArrayList<UserModel> listaUsuarios = new ArrayList<>();
    private AdapterUserModelsTest adapter;

    final String TAG = MainActivity.class.getName();

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(userEmail)) {
            //txtMessage.setText("Seja bem-vindo(a) " + sharedPreferences.getString(userEmail, ""));
        }

        getRequest = (Button) findViewById(R.id.btn_GET_Request);
        postRequest = (Button) findViewById(R.id.btn_POST_Request);
        idEditText = (EditText) findViewById(R.id.id_edit_text);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        listView = (ListView) findViewById(R.id.list_view);

        adapter = new AdapterUserModelsTest(getApplicationContext(), listaUsuarios);
        listView.setAdapter(adapter);

        cadastrarUsuario();
        listarUsuarios();
       // listUser();
        listViewItemClickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ciap_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.desconectar:
                desconectarUsuario();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void listViewItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog .Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"View data", "Edit data", "Delete data"};

                builder.setTitle(listaUsuarios.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        UserModel usuarioSelecionado = listaUsuarios.get(position);

                        switch (which) {
                            case 0:

                                Intent iDetail = new Intent(getApplicationContext(), DetailActivity.class);
                                iDetail.putExtra("usuarioSelecionado", usuarioSelecionado);
                                startActivity(iDetail);

                                break;
                            case 1:

                                Intent iEdit = new Intent(getApplicationContext(), EditActivity.class);
                                iEdit.putExtra("usuarioSelecionado", usuarioSelecionado);
                                startActivity(iEdit);
                                break;
                            case 2:

                                deletarUsuario(listaUsuarios.get(position).getId());
                                break;

                        }

                    }
                });

                builder.create().show();
            }
        });
    }


    private void cadastrarUsuario() {
        postRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario user = new Usuario();
                user.setNomeCompleto(nameEditText.getText().toString());
                user.setEmail(emailEditText.getText().toString());

                new UsuarioController().cadastrarUsuario(
                        getApplicationContext(),
                        user,
                        new VolleyCallback() {
                            @Override
                            public void onSucess(String response) {

                                try{

                                    JSONObject jsonObject = new JSONObject(response);

                                    boolean isErro = jsonObject.getBoolean("erro");

                                    String mensagem = jsonObject.getString("mensagem");

                                    if(isErro) {
                                        Toast.makeText(MainActivity.this,
                                                mensagem,
                                                Toast.LENGTH_SHORT).show();
                                    }else {

                                        Toast.makeText(MainActivity.this,
                                                "Cadastro realizado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        });

            }
        });
    }


    private void listarUsuarios() {

        new UsuarioController().listarUsuarios(getApplicationContext(),  new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        listaUsuarios.clear();
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            int id = object.getInt("id");
                            String nome = object.getString("nome");
                            String email = object.getString("email");

                            userModel = new UserModel();
                            userModel.setId(id);
                            userModel.setName(nome);
                            userModel.setEmail(email);
                            listaUsuarios.add(userModel);

                        }

                        adapter.notifyDataSetChanged();
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void deletarUsuario(int position) {

        String id = String.valueOf(position);

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        UsuarioController usuarioController = new UsuarioController();
        usuarioController.deletarUsuario(getApplicationContext(), id, new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    boolean isErro = jsonObject.getBoolean("erro");
                    String mensagem = jsonObject.getString("mensagem");

                    if(isErro) {
                        Toast.makeText(MainActivity.this,
                                "Exclusão falhou " + mensagem,
                                Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(MainActivity.this,
                                "Contato excluído com sucesso! " + mensagem,
                                Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException jsonE) {
                    jsonE.printStackTrace();
                }

            }

        });

    }

    private void desconectarUsuario() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(fileName);
        editor.remove(userEmail);
        editor.remove(userPass);
        editor.commit();

        abrirTelaLogin();
    }

    private void abrirTelaLogin() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}