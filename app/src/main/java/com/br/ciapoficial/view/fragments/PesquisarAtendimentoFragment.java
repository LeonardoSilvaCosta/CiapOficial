package com.br.ciapoficial.view.fragments;

//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.br.ciapoficial.adapters.ServicosAdapter;
//import com.br.ciapoficial.model.Servico;
//
//import java.util.ArrayList;
//
//public class PesquisarAtendimentoFragment extends Fragment {
//
//    private RecyclerView recyclerViewAtendimentos;
//
//    private ArrayList<Servico> listaDeServicos = new ArrayList<>();
//    private ServicosAdapter adapter;
//
//    public PesquisarAtendimentoFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_pesquisar_atendimento, container, false);
//
//        recyclerViewAtendimentos = view.findViewById(R.id.recyclerViewListaAtendimentos);
//
//        adapter = new ServicosAdapter(listaDeServicos, getActivity());
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerViewAtendimentos.setLayoutManager(layoutManager);
//        recyclerViewAtendimentos.setHasFixedSize(true);
//        recyclerViewAtendimentos.setAdapter(adapter);
//
//        recyclerViewAtendimentos.addOnItemTouchListener(
//                new RecyclerItemClickListener(
//                        getActivity(),
//                        recyclerViewAtendimentos,
//                        new RecyclerItemClickListener.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//
//                                Servico servicoSelecionado = listaDeServicos.get(position);
//                                Intent i = new Intent(getActivity(), DetalhesAtendimentoActivity.class);
//                                i.putExtra("servicoSelecionado", servicoSelecionado);
//                                startActivity(i);
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
//        recuperarAtendimentos();
//
//        return view;
//    }
//
//    public void pesquisarAtendimentos(String texto)
//    {
//
//        List<Servico> listaAtendimentosBusca = new ArrayList<>();
//        for(Servico servico : listaDeServicos)
//        {
//            String data = String.valueOf(servico.getData()).toLowerCase();
//            if(data.contains(texto))
//            {
//                listaAtendimentosBusca.add(servico);
//            }
//        }
//
//        adapter = new ServicosAdapter(listaAtendimentosBusca, getActivity());
//        recyclerViewAtendimentos.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
//
//    public void recarregarAtendimentos()
//    {
//        adapter = new ServicosAdapter(listaDeServicos, getActivity());
//        recyclerViewAtendimentos.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
//
//    public void recuperarAtendimentos()
//    {
//        AtendimentoController atendimentoController = new AtendimentoController();
//        atendimentoController.listar(getActivity(), new IVolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                Log.e("ResAtd", response);
//
//                listaDeServicos.clear();
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonAtendimentos = jsonObject.getJSONArray("dados_atendimentos");
//                    JSONArray jsonOficiais = jsonObject.getJSONArray("dados_oficiais");
//                    JSONArray jsonAtendidos = jsonObject.getJSONArray("dados_atendidos");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonAtendimentos.length(); i++) {
//
//                            JSONObject object = jsonAtendimentos.getJSONObject(i);
//
//                            Servico servico = new Servico();
//                            servico.setId(Integer.valueOf(object.getString("id")));
//                            servico.setData(DataEntreJavaEMysql.receberDataDoMySqlComoString(object.getString("data")));
//                            servico.setUnidade((object.getString("unidade")));
//                            servico.setModalidade((object.getString("modalidade")));
//                            servico.setAcesso((object.getString("acesso")));
//                            servico.setTipo((object.getString("tipo")));
//                            servico.setTipoAvaliacao((object.getString("avaliacao")));
//                            servico.setPrograma((object.getString("programa")));
//                            servico.setDemandaGeral((object.getString("demanda_geral")));
//                            servico.setCondicaoLaboral((object.getString("condicao_laboral")));
//                            servico.setProcedimento((object.getString("procedimento")));
//                            servico.setAfastamento(object.getInt("afastamento") == 1);
//                            servico.setEvolucao((object.getString("evolucao")));
//                            servico.setDataHoraCadastro(DataEntreJavaEMysql.
//                                    receberDataHoraDoMySqlComoString(object.getString("dataHoraCadastro")));
//
//                            ArrayList<String> nomes_oficiais = new ArrayList<>();
//                            ArrayList<String> nomes_atendidos = new ArrayList<>();
//
//                            for(int j = 0; j < jsonOficiais.length(); j++)
//                            {
//                                JSONObject object2 = jsonOficiais.getJSONObject(j);
//                                if(servico.getId() == object2.getInt("id_atendimento"))
//                                {
//                                    nomes_oficiais.add((object2.getString("nomeCompleto")));
//                                }
//                            }
//
//                            for(int k = 0; k < jsonAtendidos.length(); k++)
//                            {
//                                JSONObject object3 = jsonAtendidos.getJSONObject(k);
//                                if(servico.getId() == object3.getInt("id_atendimento")) {
//                                    nomes_atendidos.add((object3.getString("nomeCompleto")));
//                                }
//                            }
//
//
//                            servico.setOficiaisResponsaveis(nomes_oficiais);
//                            servico.setAtendidos(nomes_atendidos);
//
//                            listaDeServicos.add(servico);
//
//                        }
//
//
//
//
//                        adapter.notifyDataSetChanged();
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
//}