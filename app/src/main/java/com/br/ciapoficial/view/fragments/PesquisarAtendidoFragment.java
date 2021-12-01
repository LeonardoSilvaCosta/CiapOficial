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
import com.br.ciapoficial.adapters.AtendidosAdapter;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.view.DetalhesAtendidoActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PesquisarAtendidoFragment extends Fragment {

    private RecyclerView recyclerViewAtendidos;

    private ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
    private AtendidosAdapter adapter;

    public PesquisarAtendidoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesquisar_atendido, container, false);

        recyclerViewAtendidos = view.findViewById(R.id.recyclerViewListaAtendidos);

        adapter = new AtendidosAdapter(listaDeUsuarios, getActivity());

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

                                        Usuario usuarioSelecionado = listaDeUsuarios.get(position);
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

        return view;
    }


    public void pesquisarAtendidos(String texto)
    {

        List<Usuario> listaAtendidosBusca = new ArrayList<>();
        for(Usuario usuario : listaDeUsuarios)
        {
            String nome = usuario.getNomeCompleto().toLowerCase();
            String cpf = usuario.getCpf().toLowerCase();

            if(nome.contains(texto) || cpf.contains(texto))
            {
                listaAtendidosBusca.add(usuario);
            }
        }

        adapter = new AtendidosAdapter(listaAtendidosBusca, getActivity());
        recyclerViewAtendidos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recarregarAtendidos()
    {
        adapter = new AtendidosAdapter(listaDeUsuarios, getActivity());
        recyclerViewAtendidos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                    JSONArray jsonArray = new JSONArray(response);

                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            GsonBuilder customGson = new GsonBuilder();
                            customGson.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                            customGson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                            Gson gson = customGson.create();
                            Usuario usuario = gson.fromJson(String.valueOf(jsonObject), Usuario.class);

                            listaDeUsuarios.add(usuario);

                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }

        });
    }
}