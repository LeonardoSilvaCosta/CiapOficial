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
import com.br.ciapoficial.adapters.ServicosAdapter;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Servico;
import com.br.ciapoficial.model.in_servico.Atendimento;
import com.br.ciapoficial.model.in_servico.Avaliacao;
import com.br.ciapoficial.model.in_servico.ServicoDeAssistenciaEspecial;
import com.br.ciapoficial.network.ServicoController;
import com.br.ciapoficial.network.api.config.ApiModule;
import com.br.ciapoficial.network.api.dto.PageServico;
import com.br.ciapoficial.view.DetalhesAtendimentoActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesquisarAtendimentoFragment extends Fragment {

    private View estaView;

    private RecyclerView recyclerViewAtendimentos;
    private int currentPage = 0;
    private int totalPages = 1;
    private Long totalElements = 0L;

    private PageServico pageServico;
    private ArrayList<Servico> listaDeServicos = new ArrayList<>();
    private ArrayList<Servico> listaAtualizada = new ArrayList<>();
    private ServicosAdapter adapter;

    private LinearLayout layout;
    private Button newButton;
    private OnBackPressedCallback callback;

    private SharedPreferences sharedPreferences;

    private int images[] = {R.drawable.ic_check_circle_24, R.drawable.ic_remove_circle_24};

    public PesquisarAtendimentoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        estaView = inflater.inflate(R.layout.fragment_pesquisar_atendimento, container, false);
        layout = estaView.findViewById(R.id.buttonsPaginationLayout);

        recyclerViewAtendimentos = estaView.findViewById(R.id.recyclerViewListaAtendimentos);

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        adapter = new ServicosAdapter(listaDeServicos, getActivity(), images);
        listaAtualizada = listaDeServicos;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAtendimentos.setLayoutManager(layoutManager);
        recyclerViewAtendimentos.setHasFixedSize(true);
        recyclerViewAtendimentos.setAdapter(adapter);

        recyclerViewAtendimentos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewAtendimentos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Servico servicoSelecionado = listaAtualizada.get(position);
                                Intent i = new Intent(getActivity(), DetalhesAtendimentoActivity.class);
                                i.putExtra("servicoSelecionado", servicoSelecionado);
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

        recuperarAtendimentos();

        return estaView;
    }

    private void workingWithOnBackPressed(boolean isListFiltered) {
        callback = new OnBackPressedCallback(false) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleOnBackPressed() {
                recarregarAtendimentos();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

        habilitarDesabilitarCallback(isListFiltered);
    }

    private void habilitarDesabilitarCallback(boolean isListFiltered) {
        callback.setEnabled(isListFiltered);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pesquisarAtendimentos(String termoDePesquisa)
    {
        String token = sharedPreferences.getString("token", "");

        if (!termoDePesquisa.isEmpty()) {
            ApiModule apiModule = new ApiModule();
            Call<PageServico> filtrarServicos = apiModule.getRetrofit(
                    getContext(), token)
                    .servicosFiltrados(termoDePesquisa);
            filtrarServicos.enqueue(new Callback<PageServico>() {
                @Override
                public void onResponse(Call<PageServico> call, Response<PageServico> response) {
                    ArrayList<Servico> list = new ArrayList<>();
                    if(response.isSuccessful()) {

                        pageServico = response.body();
                        for(int i = 0; i < pageServico.getContent().size(); i++) {
                            Servico servico = pageServico.getContent().get(i);
                            currentPage = pageServico.getNumber();
                            totalPages = pageServico.getTotalPages();
                            totalElements = pageServico.getTotalElements();

                            list.add(servico);
                        }
                        if(callback != null) callback.remove();

                        workingWithOnBackPressed(true);

                        adapter = new ServicosAdapter(list, getActivity(), images);
                        listaAtualizada = list;
                        recyclerViewAtendimentos.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        layout.removeAllViews();
                        addButton(currentPage, totalPages, totalElements);

                    }else {
                        Log.e("Solicitação falhou", "Solicitação falhou");
                    }
                }

                @Override
                public void onFailure(Call<PageServico> call, Throwable t) {
                    Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void recarregarAtendimentos()
    {
        if(callback != null) callback.remove();
        callback.remove();
        workingWithOnBackPressed(false);

        adapter = new ServicosAdapter(listaDeServicos, getActivity(), images);
        listaAtualizada = listaDeServicos;
        recyclerViewAtendimentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        currentPage = pageServico.getNumber();
        totalPages = pageServico.getTotalPages();
        totalElements = pageServico.getTotalElements();

        layout.removeAllViews();
        addButton(currentPage, totalPages, totalElements);
    }

    public void recuperarAtendimentos()
    {
        ServicoController servicoController = new ServicoController();
        servicoController.listar(getActivity(), new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSucess(String response) {

                listaDeServicos.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GsonBuilder customGson = new GsonBuilder();
                        customGson.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                        customGson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                        Gson gson = customGson.create();
                        Avaliacao avaliacao = gson.fromJson(String.valueOf(jsonObject), Avaliacao.class);
                        ServicoDeAssistenciaEspecial sae = gson.fromJson(String.valueOf(jsonObject), ServicoDeAssistenciaEspecial.class);

                        if(avaliacao.getTipoAvaliacao() != null) {
                            listaDeServicos.add(avaliacao);
                        }else if(sae.getCondicaoLaboral() != null) {
                            listaDeServicos.add(sae);
                        }else {
                            Atendimento atendimento = gson.fromJson(String.valueOf(jsonObject), Atendimento.class);
                            listaDeServicos.add(atendimento);
                        }
                    }
                    addButton(currentPage, totalPages, totalElements);
                    adapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
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
            Call<PageServico> repaginarAtendimentos = apiModule.getRetrofit(
                    getContext(), token)
                    .paginarServicos(finalNextPage);
            repaginarAtendimentos.enqueue(new Callback<PageServico>() {
                @Override
                public void onResponse(Call<PageServico> call, Response<PageServico> response) {
                    ArrayList<Servico> list = new ArrayList<>();
                    if(response.isSuccessful()) {

                        PageServico pageServico = response.body();
                        for(int i = 0; i < pageServico.getContent().size(); i++) {
                            Servico servico = pageServico.getContent().get(i);
                            currentPage = pageServico.getNumber();
                            totalPages = pageServico.getTotalPages();
                            totalElements = pageServico.getTotalElements();

                            list.add(servico);
                        }
                        if(callback != null) callback.remove();

                        workingWithOnBackPressed(true);

                        adapter = new ServicosAdapter(list, getActivity(), images);
                        listaAtualizada = list;
                        recyclerViewAtendimentos.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        layout.removeAllViews();
                        addButton(currentPage, totalPages, totalElements);

                    }else {
                        Log.e("Solicitação falhou", "Solicitação falhou");
                    }
                }

                @Override
                public void onFailure(Call<PageServico> call, Throwable t) {
                    Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}