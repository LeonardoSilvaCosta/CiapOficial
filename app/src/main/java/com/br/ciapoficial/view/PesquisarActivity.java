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
import com.br.ciapoficial.view.fragments.PesquisarAtendidoFragment;
import com.br.ciapoficial.view.fragments.PesquisarAtendimentoFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.text.Normalizer;

public class PesquisarActivity extends AppCompatActivity {

    MaterialSearchView searchView;

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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSearchViewClosed()
            {
//                if(adapter.getPage(0).isAdded())
//                {
//                    PesquisarAtendidoFragment fragment = (PesquisarAtendidoFragment) adapter.getPage(0);
//                    fragment.recarregarAtendidos();
//                }else if (adapter.getPage(1).isAdded())
//                {
//                    PesquisarAtendimentoFragment fragment = (PesquisarAtendimentoFragment) adapter.getPage(1);
//                    fragment.recarregarAtendimentos();
//                }


            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {

                int fragmentAtendido = 0;
                int fragmentAtendimento = 1;
                if(viewPager.getCurrentItem() == fragmentAtendido)
                {
                    PesquisarAtendidoFragment fragment = (PesquisarAtendidoFragment) adapter.getPage(0);
                    if(query != null && !query.isEmpty())
                    {
                        String text = Normalizer.normalize(query, Normalizer.Form.NFD)
                                .replaceAll("[^\\p{ASCII}]", "");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            fragment.pesquisarAtendidos(text);
                        }
                    }
                } else if(viewPager.getCurrentItem() == fragmentAtendimento) {
                    PesquisarAtendimentoFragment fragment = (PesquisarAtendimentoFragment) adapter.getPage(1);
                    String text = Normalizer.normalize(query, Normalizer.Form.NFD)
                            .replaceAll("[^\\p{ASCII}]", "");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        fragment.pesquisarAtendimentos(text);
                    }
                }
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
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
