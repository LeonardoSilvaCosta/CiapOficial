package com.br.ciapoficial.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.br.ciapoficial.R;
import com.br.ciapoficial.view.fragment.PrincipalFragment;
import com.br.ciapoficial.view.fragment.FuncionarioRegisterFragment1;

import static com.br.ciapoficial.view.LoginActivity.fileName;
import static com.br.ciapoficial.view.LoginActivity.userEmail;
import static com.br.ciapoficial.view.LoginActivity.userId;
import static com.br.ciapoficial.view.LoginActivity.userPass;
import static com.br.ciapoficial.view.LoginActivity.userSex;

public class MainActivity extends AppCompatActivity {

    private Toolbar ciapToolbar;
    private PrincipalFragment principalFragment;
    private FuncionarioRegisterFragment1 funcionarioRegisterFragment1;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ciapToolbar =  findViewById(R.id.includedToolbar);
        setSupportActionBar(ciapToolbar);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        principalFragment = new PrincipalFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo, principalFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ciap_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.desconectar:
                desconectarUsuario();
                abrirTelaLogin();
                break;
            case R.id.new_user:
                callUserRegisterForm();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callUserRegisterForm() {

        funcionarioRegisterFragment1 = new FuncionarioRegisterFragment1();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteudo, funcionarioRegisterFragment1);
        transaction.addToBackStack(null).commit();

    }

    private void desconectarUsuario() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(fileName);
        editor.remove(userId);
        editor.remove(userEmail);
        editor.remove(userSex);
        editor.remove(userPass);
        editor.commit();

        abrirTelaLogin();
    }

    private void abrirTelaLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}