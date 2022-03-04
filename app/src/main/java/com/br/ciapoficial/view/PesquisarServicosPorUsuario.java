package com.br.ciapoficial.view;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.br.ciapoficial.R;
import com.br.ciapoficial.view.fragments.PesquisarServicosPorUsuarioFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.text.Normalizer;

public class PesquisarServicosPorUsuario extends AppCompatActivity {

    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_servicos_por_usuarios);

        Toolbar toolbar = findViewById(R.id.toolbarPesquisar);
        toolbar.setTitle("Pesquisar");
        setSupportActionBar(toolbar);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("servicos", PesquisarServicosPorUsuarioFragment.class)
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
                    PesquisarServicosPorUsuarioFragment fragment = (PesquisarServicosPorUsuarioFragment) adapter.getPage(0);
                    fragment.recarregarAtendimentos();
                }

            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {

                int fragmentAtendimento = 0;
                if (viewPager.getCurrentItem() == fragmentAtendimento)
                {
                    PesquisarServicosPorUsuarioFragment fragment = (PesquisarServicosPorUsuarioFragment) adapter.getPage(0);
                    if(newText != null && !newText.isEmpty())
                    {
                        String text = Normalizer.normalize(newText, Normalizer.Form.NFD)
                                .replaceAll("[^\\p{ASCII}]", "");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            fragment.pesquisarAtendimentos(text.toLowerCase());
                        }
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