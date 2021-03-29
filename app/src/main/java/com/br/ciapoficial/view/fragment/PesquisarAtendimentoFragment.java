package com.br.ciapoficial.view.fragment;

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
import android.widget.Toast;

import com.br.ciapoficial.R;
import com.br.ciapoficial.adapters.AtendidosAdapter;
import com.br.ciapoficial.adapters.AtendimentosAdapter;
import com.br.ciapoficial.controller.AtendidoController;
import com.br.ciapoficial.controller.AtendimentoController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.PadraoDeVisualizacao;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;
import com.br.ciapoficial.model.Atendimento;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.view.DetalhesAtendidoActivity;
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
        atendimentoController.listarAtendimentos(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                Log.e("ResAtd", response);

                listaDeAtendimentos.clear();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonAtendimento = jsonObject.getJSONArray("data");
                    JSONArray jsonOficiais = jsonObject.getJSONArray("dados");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonAtendimento.length(); i++) {

                            JSONObject object = jsonAtendimento.getJSONObject(i);

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