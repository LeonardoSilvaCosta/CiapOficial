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
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.RecyclerItemClickListener;
import com.br.ciapoficial.helper.PadraoDeVisualizacao;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.Titular;
import com.br.ciapoficial.view.DetalhesAtendidoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesquisarAtendidoFragment extends Fragment {

//    private RecyclerView recyclerViewAtendidos;
//
//    private ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
//    private AtendidosAdapter adapter;
//
//    public PesquisarAtendidoFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_pesquisar_atendido, container, false);
//
//        recyclerViewAtendidos = view.findViewById(R.id.recyclerViewListaAtendidos);
//
//        adapter = new AtendidosAdapter(listaDeUsuarios, getActivity());
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerViewAtendidos.setLayoutManager(layoutManager);
//        recyclerViewAtendidos.setHasFixedSize(true);
//        recyclerViewAtendidos.setAdapter(adapter);
//
//        recyclerViewAtendidos.addOnItemTouchListener(
//                new RecyclerItemClickListener(
//                        getActivity(),
//                        recyclerViewAtendidos,
//                        new RecyclerItemClickListener.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//
//                                        Usuario usuarioSelecionado = listaDeUsuarios.get(position);
//                                        Intent i = new Intent(getActivity(), DetalhesAtendidoActivity.class);
//                                        i.putExtra("atendidoSelecionado", usuarioSelecionado);
//                                        startActivity(i);
//
//
//                            }
//
//                            @Override
//                            public void onLongItemClick(View view, int position) {
//
//                            }
//
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            }
//                        }
//                )
//        );
//
//        recuperarAtendidos();
//
//        return view;
//    }
//
//    public void pesquisarAtendidos(String texto)
//    {
//
//        List<Usuario> listaAtendidosBusca = new ArrayList<>();
//        for(Usuario usuario : listaDeUsuarios)
//        {
//            String nome = usuario.getNomeCompleto().toLowerCase();
//            String cpf = usuario.getCpf().toLowerCase();
//
//            if(nome.contains(texto) || cpf.contains(texto))
//            {
//                listaAtendidosBusca.add(usuario);
//            }
//        }
//
//        adapter = new AtendidosAdapter(listaAtendidosBusca, getActivity());
//        recyclerViewAtendidos.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
//
//    public void recarregarAtendidos()
//    {
//        adapter = new AtendidosAdapter(listaDeUsuarios, getActivity());
//        recyclerViewAtendidos.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
//
//    public void recuperarAtendidos()
//    {
//        UsuarioController usuarioController = new UsuarioController();
//        usuarioController.listar(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                listaDeUsuarios.clear();
//                try {
//
//                    Log.e("pesquisarAt", response);
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            Usuario usuario = new Usuario();
//                            usuario.setId(object.getInt("id"));
//                            usuario.setDataNascimento(
//                                    DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataNascimento")));
//                            usuario.setTipoAtendido(object.getString("tipoAtendido"));
//                            usuario.setNomeCompleto(object.getString("nomeCompleto"));
//                            usuario.setCpf(PadraoDeVisualizacao.visualizarCpf(object.getString("cpf")));
//                            usuario.setSexo(object.getString("sexo"));
//                            usuario.setEmail(object.getString("email"));
//                            usuario.setEstadoCivil(object.getString("estadoCivil"));
//                            usuario.setCidadeNatal(object.getString("cidadeNatal"));
//                            usuario.setUfNatal(object.getString("ufNatal"));
//                            usuario.setEscolaridade(object.getString("escolaridade"));
//                            usuario.setNumeroFilhos(object.getString("numeroFilhos"));
//                            usuario.setCep(object.getString("cep"));
//                            usuario.setCidade(object.getString("cidade"));
//                            usuario.setUf(object.getString("uf"));
//                            usuario.setBairro(object.getString("bairro"));
//                            usuario.setLogradouro(object.getString("logradouro"));
//                            usuario.setNumero(object.getString("numero"));
//                            usuario.setDataHoraCadastro(
//                                    DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraCadastro")));
//
//                            if(usuario.getTipoAtendido().equals("Policial Militar")) {
//                                usuario.setRgMilitar(object.getString("rgMilitar"));
//                                usuario.setPostoGradCat(object.getString("postoGradCat"));
//                                usuario.setNomeGuerra(object.getString("nomeGuerra"));
//                                usuario.setUnidade(object.getString("unidade"));
//                                usuario.setQuadro(object.getString("quadro"));
//                                usuario.setDataInclusao(
//                                        DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("dataInclusao")));
//                                usuario.setSituacaoFuncional(object.getString("situacaoFuncional"));
//                            }else if(usuario.getTipoAtendido().equals("Dependente")) {
//                                Titular titular = new Titular();
//                                titular.setId(object.getInt("idTitular"));
//                                titular.setNome(object.getString("nomeTitular"));
//                                usuario.setTitular(titular);
//                                usuario.setVinculo(object.getString("vinculo"));
//                            }
//
//                            String atualizacao = object.getString("dataHoraAtualizacao");
//                            if(atualizacao.equals("null")) {
//                                usuario.setDataHoraAtualizacao(null);
//                            }else {
//                                usuario.setDataHoraAtualizacao(
//                                        DataEntreJavaEMysql.receberDataHoraDoMySqlComoString(object.getString("dataHoraAtualizacao")));
//                            }
//
//
//                            listaDeUsuarios.add(usuario);
//
//                        }
//
//                        adapter.notifyDataSetChanged();
//
//
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
}