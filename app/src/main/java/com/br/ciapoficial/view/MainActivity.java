package com.br.ciapoficial.view;

import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.PersistentCookieStore;
import com.br.ciapoficial.view.fragments.PrincipalFragment;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferencesCookie;
    private SharedPreferences sharedPreferencesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar ciapToolbar = findViewById(R.id.includedToolbar);
        setSupportActionBar(ciapToolbar);

        sharedPreferencesCookie = getSharedPreferences(PersistentCookieStore.PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferencesUser = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        PrincipalFragment principalFragment = new PrincipalFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.desconectar:
                desconectarFuncionario();
                abrirTelaLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void desconectarFuncionario() {

        SharedPreferences.Editor editor = sharedPreferencesCookie.edit();
        editor.remove("Authorization");
        editor.apply();

        SharedPreferences.Editor editor2 = sharedPreferencesUser.edit();
        editor2.remove(FILE_NAME);
        editor2.apply();

        abrirTelaLogin();
        finish();
    }

    private void abrirTelaLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}