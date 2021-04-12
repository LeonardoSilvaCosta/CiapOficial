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

import com.br.ciapoficial.R;
import com.br.ciapoficial.adapters.AtendidosAdapter;
import com.br.ciapoficial.controller.AtendidoController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.helper.PadraoDeVisualizacao;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;
import com.br.ciapoficial.model.Titular;
import com.br.ciapoficial.view.DetalhesAtendidoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesquisarAtendidoFragment extends Fragment {

    private RecyclerView recyclerViewAtendidos;

    private ArrayList<Atendido> listaDeAtendidos = new ArrayList<>();
    private AtendidosAdapter adapter;

    public PesquisarAtendidoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pesquisar_atendido, container, false);

        recyclerViewAtendidos = view.findViewById(R.id.recyclerViewListaAtendidos);

        adapter = new AtendidosAdapter(listaDeAtendidos, getActivity());

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

                                        Atendido atendidoSelecionado = listaDeAtendidos.get(position);
                                        Intent i = new Intent(getActivity(), DetalhesAtendidoActivity.class);
                                        i.putExtra("atendidoSelecionado", atendidoSelecionado);
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

        List<Atendido> listaAtendidosBusca = new ArrayList<>();
        for(Atendido atendido : listaDeAtendidos)
        {
            String nome = atendido.getNomeCompleto().toLowerCase();
            String cpf = atendido.getCpf().toLowerCase();

            if(nome.contains(texto) || cpf.contains(texto))
            {
                listaAtendidosBusca.add(atendido);
            }
        }

        adapter = new AtendidosAdapter(listaAtendidosBusca, getActivity());
        recyclerViewAtendidos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recarregarAtendidos()
    {
        adapter = new AtendidosAdapter(listaDeAtendidos, getActivity());
        recyclerViewAtendidos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recuperarAtendidos()
    {
        AtendidoController atendidoController = new AtendidoController();
        atendidoController.listarAtendidos(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                listaDeAtendidos.clear();
                try {

                    Log.e("pesquisarAt", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Atendido atendido = new Atendido();
                            atendido.setId(object.getInt("id"));
                            atendido.setDataNascimento(
                                    DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataNascimento")));
                            atendido.setTipoAtendido(object.getString("tipoAtendido"));
                            atendido.setNomeCompleto(object.getString("nomeCompleto"));
                            atendido.setCpf(PadraoDeVisualizacao.visualizarCpf(object.getString("cpf")));
                            atendido.setSexo(object.getString("sexo"));
                            atendido.setEmail(object.getString("email"));
                            atendido.setEstadoCivil(object.getString("estadoCivil"));
                            atendido.setCidadeNatal(object.getString("cidadeNatal"));
                            atendido.setUfNatal(object.getString("ufNatal"));
                            atendido.setEscolaridade(object.getString("escolaridade"));
                            atendido.setNumeroFilhos(object.getString("numeroFilhos"));
                            atendido.setCep(object.getString("cep"));
                            atendido.setCidade(object.getString("cidade"));
                            atendido.setUf(object.getString("uf"));
                            atendido.setBairro(object.getString("bairro"));
                            atendido.setLogradouro(object.getString("logradouro"));
                            atendido.setNumero(object.getString("numero"));
                            atendido.setDataHoraCadastro(
                                    DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraCadastro")));

                            if(atendido.getTipoAtendido().equals("Policial Militar")) {
                                atendido.setRgMilitar(object.getString("rgMilitar"));
                                atendido.setPostoGradCat(object.getString("postoGradCat"));
                                atendido.setNomeGuerra(object.getString("nomeGuerra"));
                                atendido.setUnidade(object.getString("unidade"));
                                atendido.setQuadro(object.getString("quadro"));
                                atendido.setDataInclusao(
                                        DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataInclusao")));
                                atendido.setSituacaoFuncional(object.getString("situacaoFuncional"));
                            }else if(atendido.getTipoAtendido().equals("Dependente")) {
                                Titular titular = new Titular();
                                titular.setId(object.getInt("idTitular"));
                                titular.setNome(object.getString("nomeTitular"));
                                atendido.setTitular(titular);
                                atendido.setVinculo(object.getString("vinculo"));
                            }

                            String atualizacao = object.getString("dataHoraAtualizacao");
                            if(atualizacao.equals("null")) {
                                atendido.setDataHoraAtualizacao(null);
                            }else {
                                atendido.setDataHoraAtualizacao(
                                        DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraAtualizacao")));
                            }


                            listaDeAtendidos.add(atendido);

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