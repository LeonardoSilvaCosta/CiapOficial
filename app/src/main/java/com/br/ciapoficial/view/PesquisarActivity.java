package com.br.ciapoficial.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.AtendidoController;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.view.fragment.AtendimentoRegisterFragment1;
import com.br.ciapoficial.view.fragment.PesquisarAtendidoFragment;
import com.br.ciapoficial.view.fragment.PesquisarAtendimentoFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesquisarActivity extends AppCompatActivity {

    MaterialSearchView searchView;

    private ArrayList<Atendido> listaAtendidosRecuperados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);

        Toolbar toolbar = findViewById(R.id.toolbarPesquisar);
        toolbar.setTitle("Pesquisar atendido");
        setSupportActionBar(toolbar);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Atendidos", PesquisarAtendidoFragment.class)
                        .add("Atendimentos", PesquisarAtendimentoFragment.class )
                        .create()
        );
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);

        searchView = (MaterialSearchView) findViewById(R.id.search_view_pesquisar);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed()
            {
                if(adapter.getPage(0).isAdded())
                {
                    PesquisarAtendidoFragment fragment = (PesquisarAtendidoFragment) adapter.getPage(0);
                    fragment.recarregarAtendidos();
                }else if (adapter.getPage(1).isAdded())
                {
                    PesquisarAtendimentoFragment fragment = (PesquisarAtendimentoFragment) adapter.getPage(1);
                    fragment.recarregarAtendimentos();
                }


            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                int fragmentAtendido = 0;
                int fragmentAtendimento = 1;
                if(viewPager.getCurrentItem() == fragmentAtendido)
                {
                    PesquisarAtendidoFragment fragment = (PesquisarAtendidoFragment) adapter.getPage(0);
                    if(newText != null && !newText.isEmpty())
                    {
                        fragment.pesquisarAtendidos(newText.toLowerCase());
                    }
                }else if (viewPager.getCurrentItem() == fragmentAtendimento)
                {
                    PesquisarAtendimentoFragment fragment = (PesquisarAtendimentoFragment) adapter.getPage(1);
                    if(newText != null && !newText.isEmpty())
                    {
                        fragment.pesquisarAtendimentos(newText.toLowerCase());
                    }
                }


                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ciap_menu, menu);

        MenuItem item = menu.findItem(R.id.menuPesquisa);
        searchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }
}