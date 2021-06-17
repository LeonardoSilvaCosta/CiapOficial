package com.br.ciapoficial.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.br.ciapoficial.R;
import com.br.ciapoficial.adapters.AtendimentosAdapter;
import com.br.ciapoficial.controller.AtendimentoController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Atendimento;
import com.br.ciapoficial.view.DetalhesAtendimentoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesquisarAtendimentoFragment extends Fragment {

    private RecyclerView recyclerViewAtendimentos;

    private ArrayList<Atendimento> listaDeAtendimentos = new ArrayList<>();
    private AtendimentosAdapter adapter;

    public PesquisarAtendimentoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pesquisar_atendimento, container, false);

        recyclerViewAtendimentos = view.findViewById(R.id.recyclerViewListaAtendimentos);

        adapter = new AtendimentosAdapter(listaDeAtendimentos, getActivity());

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

                                Atendimento atendimentoSelecionado = listaDeAtendimentos.get(position);
                                Intent i = new Intent(getActivity(), DetalhesAtendimentoActivity.class);
                                i.putExtra("atendimentoSelecionado", atendimentoSelecionado);
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

    public void pesquisarAtendimentos(String texto)
    {

        List<Atendimento> listaAtendimentosBusca = new ArrayList<>();
        for(Atendimento atendimento : listaDeAtendimentos)
        {
            String data = atendimento.getData().toLowerCase();
            if(data.contains(texto))
            {
                listaAtendimentosBusca.add(atendimento);
            }
        }

        adapter = new AtendimentosAdapter(listaAtendimentosBusca, getActivity());
        recyclerViewAtendimentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recarregarAtendimentos()
    {
        adapter = new AtendimentosAdapter(listaDeAtendimentos, getActivity());
        recyclerViewAtendimentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recuperarAtendimentos()
    {
        AtendimentoController atendimentoController = new AtendimentoController();
        atendimentoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                Log.e("ResAtd", response);

                listaDeAtendimentos.clear();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonAtendimentos = jsonObject.getJSONArray("dados_atendimentos");
                    JSONArray jsonOficiais = jsonObject.getJSONArray("dados_oficiais");
                    JSONArray jsonAtendidos = jsonObject.getJSONArray("dados_atendidos");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonAtendimentos.length(); i++) {

                            JSONObject object = jsonAtendimentos.getJSONObject(i);

                            Atendimento atendimento = new Atendimento();
                            atendimento.setId(Integer.valueOf(object.getString("id")));
                            atendimento.setData(DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("data")));
                            atendimento.setUnidade((object.getString("unidade")));
                            atendimento.setModalidade((object.getString("modalidade")));
                            atendimento.setAcesso((object.getString("acesso")));
                            atendimento.setTipo((object.getString("tipo")));
                            atendimento.setTipoAvaliacao((object.getString("avaliacao")));
                            atendimento.setPrograma((object.getString("programa")));
                            atendimento.setDemandaGeral((object.getString("demanda_geral")));
                            atendimento.setCondicaoLaboral((object.getString("condicao_laboral")));
                            atendimento.setProcedimento((object.getString("procedimento")));
                            atendimento.setAfastamento(object.getInt("afastamento") == 1);
                            atendimento.setEvolucao((object.getString("evolucao")));
                            atendimento.setDataHoraCadastro(DataEntreJavaEMysql.
                                    receberDataHoraDoMySqlComoString(object.getString("dataHoraCadastro")));

                            ArrayList<String> nomes_oficiais = new ArrayList<>();
                            ArrayList<String> nomes_atendidos = new ArrayList<>();

                            for(int j = 0; j < jsonOficiais.length(); j++)
                            {
                                JSONObject object2 = jsonOficiais.getJSONObject(j);
                                if(atendimento.getId() == object2.getInt("id_atendimento"))
                                {
                                    nomes_oficiais.add((object2.getString("nomeCompleto")));
                                }
                            }

                            for(int k = 0; k < jsonAtendidos.length(); k++)
                            {
                                JSONObject object3 = jsonAtendidos.getJSONObject(k);
                                if(atendimento.getId() == object3.getInt("id_atendimento")) {
                                    nomes_atendidos.add((object3.getString("nomeCompleto")));
                                }
                            }


                            atendimento.setOficiaisResponsaveis(nomes_oficiais);
                            atendimento.setAtendidos(nomes_atendidos);

                            listaDeAtendimentos.add(atendimento);

                        }




                        adapter.notifyDataSetChanged();

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}