package com.br.ciapoficial.view.fragments;

import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.adapters.AtendidosAdapter;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.network.UsuarioController;
import com.br.ciapoficial.network.api.config.ApiModule;
import com.br.ciapoficial.network.api.dto.PageUsuario;
import com.br.ciapoficial.view.DetalhesAtendidoActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesquisarAtendidoFragment extends Fragment {

    private View estaView;

    private RecyclerView recyclerViewAtendidos;
    private int currentPage = 0;
    private int totalPages = 1;
    private Long totalElements = 0L;

    private PageUsuario pageUsuario;
    private ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
    private ArrayList<Usuario> listaAtualizada = new ArrayList<>();
    private AtendidosAdapter adapter;

    private LinearLayout layout;
    private Button newButton;
    private OnBackPressedCallback callback;

    private SharedPreferences sharedPreferences;

    public PesquisarAtendidoFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        estaView = inflater.inflate(R.layout.fragment_pesquisar_atendido, container, false);
        layout = estaView.findViewById(R.id.buttonsPaginationLayout);

        recyclerViewAtendidos = estaView.findViewById(R.id.recyclerViewListaAtendidos);

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        adapter = new AtendidosAdapter(listaDeUsuarios, getActivity());
        listaAtualizada = listaDeUsuarios;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAtendidos.setLayoutManager(layoutManager);
        recyclerViewAtendidos.setHasFixedSize(true);
        recyclerViewAtendidos.setAdapter(adapter);

        recyclerViewAtendidos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewAtendidos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Usuario usuarioSelecionado = listaAtualizada.get(position);
                                Intent i = new Intent(getActivity(), DetalhesAtendidoActivity.class);
                                i.putExtra("atendidoSelecionado", usuarioSelecionado);
                                startActivity(i);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


        recuperarAtendidos();

        return estaView;
    }

    private void workingWithOnBackPressed(boolean isListFiltered) {
        callback = new OnBackPressedCallback(false) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleOnBackPressed() {
                recarregarAtendidos();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

        habilitarDesabilitarCallback(isListFiltered);
    }

    private void habilitarDesabilitarCallback(boolean isListFiltered) {
        callback.setEnabled(isListFiltered);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void recarregarAtendidos()
    {
        if(callback != null) callback.remove();
        callback.remove();
        workingWithOnBackPressed(false);

        adapter = new AtendidosAdapter(listaDeUsuarios, getActivity());
        listaAtualizada = listaDeUsuarios;
        recyclerViewAtendidos.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        currentPage = pageUsuario.getNumber();
        totalPages = pageUsuario.getTotalPages();
        totalElements = pageUsuario.getTotalElements();

        layout.removeAllViews();
        addButton(currentPage, totalPages, totalElements);


    }

    public void recuperarAtendidos()
    {
        UsuarioController usuarioController = new UsuarioController();
        usuarioController.listar(getActivity(), new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSucess(String response) {

                listaDeUsuarios.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    for(int i = 0; i < jsonObject.getJSONArray("content").length(); i++) {

                        GsonBuilder customGson = new GsonBuilder();
                        customGson.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                        customGson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                        Gson gson = customGson.create();
                        pageUsuario = gson.fromJson(String.valueOf(jsonObject), PageUsuario.class);

                        Usuario usuario = pageUsuario.getContent().get(i);
                        currentPage = pageUsuario.getNumber();
                        totalPages = pageUsuario.getTotalPages();
                        totalElements = pageUsuario.getTotalElements();

                        listaDeUsuarios.add(usuario);

                    }

                    addButton(currentPage, totalPages, totalElements);

                    adapter.notifyDataSetChanged();

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pesquisarAtendidos(String termoDePesquisa) {
        String token = sharedPreferences.getString("token", "");

        Toast.makeText(getContext(), termoDePesquisa, Toast.LENGTH_SHORT).show();
        if (!termoDePesquisa.isEmpty()) {
            ApiModule apiModule = new ApiModule();
            Call<PageUsuario> filtrarAtendidos = apiModule.getRetrofit(
                    getContext(), token)
                    .atendidosFiltrados(termoDePesquisa);
            filtrarAtendidos.enqueue(new Callback<PageUsuario>() {
                @Override
                public void onResponse(Call<PageUsuario> call, Response<PageUsuario> response) {
                    ArrayList<Usuario> list = new ArrayList<>();
                    if(response.isSuccessful()) {

                        PageUsuario pageUsuario = response.body();
                        for(int i = 0; i < pageUsuario.getContent().size(); i++) {
                            Usuario usuario = pageUsuario.getContent().get(i);
                            currentPage = pageUsuario.getNumber();
                            totalPages = pageUsuario.getTotalPages();
                            totalElements = pageUsuario.getTotalElements();

                            list.add(usuario);
                        }
                        if(callback != null) callback.remove();

                        workingWithOnBackPressed(true);

                        adapter = new AtendidosAdapter(list, getActivity());
                        listaAtualizada = list;
                        recyclerViewAtendidos.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        layout.removeAllViews();
                        addButton(currentPage, totalPages, totalElements);

                    }else {
                        Log.e("Solicitação falhou", "Solicitação falhou");
                    }
                }

                @Override
                public void onFailure(Call<PageUsuario> call, Throwable t) {
                    Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addButton(int currentPage, int totalPages, Long totalElements) {
        if(totalPages > 1) {
            newButton = new Button(this.getContext());
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    120, 120);
            layout.setGravity(Gravity.CENTER);

            newButton = new Button(this.getContext());
            newButton.setLayoutParams(buttonLayoutParams);
            newButton.setBackgroundResource(R.drawable.borda_retangular);
            newButton.setText("<<");
            layout.addView(newButton);
            actionButtonPagination(newButton);

            for(int i = currentPage ; i < totalPages; i++) {
                layout.setPadding(120, 10, 120, 0);
                newButton = new Button(this.getContext());
                newButton.setLayoutParams(buttonLayoutParams);
                newButton.setBackgroundResource(R.drawable.borda_retangular);
                newButton.setText(String.valueOf(i + 1));
                if(Integer.parseInt(newButton.getText().toString()) == currentPage + 1) {
                    newButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    newButton.setTextColor(getResources().getColor(R.color.white));
                }
                layout.addView(newButton);

                actionButtonPagination(newButton);
                if(i == (currentPage + 4)) i = totalPages;
            }

            newButton = new Button(this.getContext());
            newButton.setLayoutParams(buttonLayoutParams);
            newButton.setBackgroundResource(R.drawable.borda_retangular);
            newButton.setText(">>");
            layout.addView(newButton);
            actionButtonPagination(newButton);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void actionButtonPagination(Button button) {
        int nextPage = currentPage;
        String acionatedButton = button.getText().toString();

        if(button.getText().toString().equals("<<")
                || button.getText().toString().equals(">>")) {
            if(acionatedButton.equals("<<")) {
                nextPage = 0;
            } else if(acionatedButton.equals(">>")) {
                nextPage = totalPages - 1; }
        } else {
            nextPage = Integer.parseInt(acionatedButton) - 1;
        }

        String token = sharedPreferences.getString("token", "");
        final int finalNextPage = nextPage;
        button.setOnClickListener(v -> {
            ApiModule apiModule = new ApiModule();
            Call<PageUsuario> repaginarAtendidos = apiModule.getRetrofit(
                    getContext(), token)
                    .paginarAtendidos(finalNextPage);
            repaginarAtendidos.enqueue(new Callback<PageUsuario>() {
                @Override
                public void onResponse(Call<PageUsuario> call, Response<PageUsuario> response) {
                    ArrayList<Usuario> list = new ArrayList<>();
                    if(response.isSuccessful()) {

                        PageUsuario pageUsuario = response.body();
                        for(int i = 0; i < pageUsuario.getContent().size(); i++) {
                            Usuario usuario = pageUsuario.getContent().get(i);
                            currentPage = pageUsuario.getNumber();
                            totalPages = pageUsuario.getTotalPages();
                            totalElements = pageUsuario.getTotalElements();

                            list.add(usuario);
                        }
                        if(callback != null) callback.remove();

                        workingWithOnBackPressed(true);

                        adapter = new AtendidosAdapter(list, getActivity());
                        listaAtualizada = list;
                        recyclerViewAtendidos.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        layout.removeAllViews();
                        addButton(currentPage, totalPages, totalElements);

                    }else {
                        Log.e("Solicitação falhou", "Solicitação falhou");
                    }
                }

                @Override
                public void onFailure(Call<PageUsuario> call, Throwable t) {
                    Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}