package com.br.ciapoficial.view.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.adapters.ServicosAdapter;
import com.br.ciapoficial.network.ServicoController;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Servico;
import com.br.ciapoficial.model.in_servico.Atendimento;
import com.br.ciapoficial.model.in_servico.Avaliacao;
import com.br.ciapoficial.model.in_servico.ServicoDeAssistenciaEspecial;
import com.br.ciapoficial.view.DetalhesAtendimentoActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PesquisarAtendimentoFragment extends Fragment {

    private RecyclerView recyclerViewAtendimentos;

    private ArrayList<Servico> listaDeServicos = new ArrayList<>();
    private ArrayList<Servico> listaAtualizada = new ArrayList<>();
    private ServicosAdapter adapter;

    public PesquisarAtendimentoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pesquisar_atendimento, container, false);

        recyclerViewAtendimentos = view.findViewById(R.id.recyclerViewListaAtendimentos);

        adapter = new ServicosAdapter(listaDeServicos, getActivity());
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

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pesquisarAtendimentos(String texto)
    {

        ArrayList<Servico> listaAtendimentosBusca = new ArrayList<>();
        listaDeServicos.forEach(e -> {
            String data = (DateFormater.localDateToString(e.getData()).toLowerCase());
            String especialista = Normalizer
                    .normalize(e.getEspecialistas().toString().toLowerCase(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "");
            String atendido = Normalizer
                    .normalize(e.getUsuarios().toString().toLowerCase(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "");

            if(data.contains(texto) || especialista.contains(texto) || atendido.contains(texto))
            {
                listaAtendimentosBusca.add(e);
            }
        });

        adapter = new ServicosAdapter(listaAtendimentosBusca, getActivity());
        listaAtualizada = listaAtendimentosBusca;
        recyclerViewAtendimentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recarregarAtendimentos()
    {
        adapter = new ServicosAdapter(listaDeServicos, getActivity());
        listaAtualizada = listaDeServicos;
        recyclerViewAtendimentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                    adapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}