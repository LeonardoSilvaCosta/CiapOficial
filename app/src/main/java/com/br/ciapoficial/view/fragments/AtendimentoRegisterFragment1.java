package com.br.ciapoficial.view.fragments;

import androidx.fragment.app.Fragment;

public class AtendimentoRegisterFragment1 extends Fragment {
//
//    private LinearLayout linearLayoutOficialResponsavel, linearLayoutAtendido;
//    private AutoCompleteTextView autoCompleteTextViewDataAtendimento, autoCompleteTextViewOficialResponsavel,
//            autoCompleteTextViewAtendido, autoCompleteTextViewUnidadeAtendimento, autoCompleteTextViewModalidade,
//            autoCompleteTextViewAcessoAtendimento;
//    private Button btnAdicionarOficiail, btnAdicionarAtendido, btnProxima;
//
//    private AtendimentoRegisterFragment2 atendimentoRegisterFragment2;
//
//    private ArrayList<Funcionario> listaOficiaisRecuperados = new ArrayList<>();
//    private ArrayList<Usuario> listaAtendidosRecuperados = new ArrayList<>();
//    private ArrayList<Unidade> listaUnidadesRecuperadas = new ArrayList<>();
//    private ArrayList<Modalidade> listaModalidadesRecuperadas = new ArrayList<>();
//    private ArrayList<Acesso> listaAcessosAtendimentosRecuperados = new ArrayList<>();
//
//    private ArrayList<Funcionario> arrayListOficiaisSelecionados;
//    private ArrayList<Usuario> arrayListAtendidosSelecionados;
//    private ArrayAdapter<Funcionario> adapterOficiais;
//    private ArrayAdapter<Usuario> adapterAtendidos;
//
//    private ArrayList<String> listaDeOficiaisSelecionados = new ArrayList<>();
//    private ArrayList<String> listaDeAtendidosSelecionados = new ArrayList<>();
//    private String dataAtendimento;
//    private String unidadeAtendimento;
//    private String modalidade;
//    private String acessoAtendimento;
//
//
//
//    public AtendimentoRegisterFragment1() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_atendimento_register1, container, false);
//
//        configurarComponentes(view);
//        abrirProximaTela();
//        return view;
//    }
//
//    private void configurarComponentes(View view) {
//
//        linearLayoutOficialResponsavel = view.findViewById(R.id.linearLayoutOficialResponsavel);
//        linearLayoutAtendido = view.findViewById(R.id.linearLayoutAtendido);
//        autoCompleteTextViewDataAtendimento = view.findViewById(R.id.edtDataAtendimento);
//        autoCompleteTextViewOficialResponsavel = view.findViewById(R.id.edtOficialResponsavel);
//        autoCompleteTextViewAtendido = view.findViewById(R.id.edtAtendido);
//        autoCompleteTextViewUnidadeAtendimento = view.findViewById(R.id.edtUnidadeAtendimento);
//        autoCompleteTextViewModalidade = view.findViewById(R.id.edtModalidade);
//        autoCompleteTextViewAcessoAtendimento = view.findViewById(R.id.edtAcessAtendimento);
//        btnAdicionarOficiail = view.findViewById(R.id.btnAdicionarOficial);
//        btnAdicionarAtendido = view.findViewById(R.id.btnAdicionarAtendido);
//        btnProxima = view.findViewById(R.id.btnProxima);
//
//        configurarMascaraData();
//        adicionarDataAtualNoEditTextData();
//        adicionarCalendarioNoEditTextData();
//        popularCampoOficialResponsavelComDB();
//        popularCampoAtendidoComDB();
//        popularCampoUnidadeAtendidoComDB();
//        popularCampoModalidadeComDB();
//        popularCampoAcessoAtendimentoComDb();
//    }
//
//    private void configurarMascaraData()
//    {
//        Mascaras mascara = new Mascaras();
//        mascara.mascaraData(autoCompleteTextViewDataAtendimento);
//    }
//
//    private void adicionarDataAtualNoEditTextData()
//    {
//        autoCompleteTextViewDataAtendimento.setText(DateFormater.dataAtual());
//    }
//
//    private void adicionarCalendarioNoEditTextData()
//    {
//        Calendar.pegarCalendarioDataAtual(getActivity(), autoCompleteTextViewDataAtendimento);
//    }
//
//    private void popularCampoOficialResponsavelComDB() {
//
//        FuncionarioController funcionarioController = new FuncionarioController();
//        funcionarioController.listarOficiais(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
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
//                            Funcionario funcionario = new Funcionario();
//
//                            String quadro = object.getString("quadro");
//                            String especialidade = object.getString("especialidade");
//                            if(quadro.equals("QCOPM") && especialidade.equals("Psicólogo(a)") || especialidade.equals("Assistente Social")) {
//                                funcionario.setId(Integer.valueOf(object.getString("id")));
//                                funcionario.setPostoGradCat(object.getString("postoGradCat"));
//                                funcionario.setNomeGuerra(object.getString("nomeGuerra"));
//                                funcionario.setRgMilitar(object.getString("rgMilitar"));
//
//                                listaOficiaisRecuperados.add(funcionario);
//                                configurarCampoOficialResponsavel(listaOficiaisRecuperados);
//                            }
//
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void configurarCampoOficialResponsavel(ArrayList<Funcionario> listaOficiaisMySql) {
//
//        linearLayoutOficialResponsavel.removeAllViews();
//
//        adapterOficiais = new ArrayAdapter<Funcionario>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaOficiaisMySql));
//        autoCompleteTextViewOficialResponsavel.setAdapter(adapterOficiais);
//        autoCompleteTextViewOficialResponsavel.setThreshold(1);
//
//        arrayListOficiaisSelecionados = new ArrayList<Funcionario>();
//        adapterOficiais = new ArrayAdapter<Funcionario>(getActivity(),
//                android.R.layout.simple_list_item_1,
//                arrayListOficiaisSelecionados);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewOficialResponsavel);
//
//        btnAdicionarOficiail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AddRemoveTextView.adicionarTextViewUsuario(getActivity(), autoCompleteTextViewOficialResponsavel,
//                        listaOficiaisRecuperados, arrayListOficiaisSelecionados, adapterOficiais, linearLayoutOficialResponsavel);
//            }
//        });
//    }
//
//    private void popularCampoAtendidoComDB() {
//
//        UsuarioController usuarioController = new UsuarioController();
//        usuarioController.listar(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
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
//                            usuario.setId(Integer.valueOf(object.getString("id")));
//                            usuario.setNomeCompleto(object.getString("nomeCompleto"));
//                            usuario.setCpf(object.getString("cpf"));
//
//                            listaAtendidosRecuperados.add(usuario);
//
//                            configurarCampoAtendido(listaAtendidosRecuperados);
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void configurarCampoAtendido(ArrayList<Usuario> listaAtendidosMySql) {
//
//        linearLayoutAtendido.removeAllViews();
//
//        adapterAtendidos = new ArrayAdapter<Usuario>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                listaAtendidosMySql);
//        autoCompleteTextViewAtendido.setAdapter(adapterAtendidos);
//        autoCompleteTextViewAtendido.setThreshold(1);
//
//        arrayListAtendidosSelecionados = new ArrayList<Usuario>();
//        adapterAtendidos = new ArrayAdapter<Usuario>(getActivity(),
//                android.R.layout.simple_list_item_1,
//                arrayListAtendidosSelecionados);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewAtendido);
//
//        btnAdicionarAtendido.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AddRemoveTextView.adicionarTextViewAtendido(getActivity(), autoCompleteTextViewAtendido,
//                        listaAtendidosRecuperados, arrayListAtendidosSelecionados, adapterAtendidos, linearLayoutAtendido);
//            }
//        });
//    }
//
//    private void popularCampoUnidadeAtendidoComDB() {
//
//        UnidadeController unidadeController = new UnidadeController();
//        unidadeController.listar(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
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
//                            Unidade unidade = new Unidade();
//                            unidade.setId(Integer.valueOf(object.getString("id")));
//                            unidade.setDescricao(object.getString("descricao"));
//
//                            listaUnidadesRecuperadas.add(unidade);
//
//                            configurarCampoUnidadeAtendimento(listaUnidadesRecuperadas);
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void configurarCampoUnidadeAtendimento(ArrayList<Unidade> listaUnidadesRecuperadas) {
//
//        ArrayAdapter<Unidade> adapterUnidade= new ArrayAdapter<Unidade>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                listaUnidadesRecuperadas);
//        autoCompleteTextViewUnidadeAtendimento.setAdapter(adapterUnidade);
//        autoCompleteTextViewUnidadeAtendimento.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUnidadeAtendimento);
//
//    }
//
//    private void popularCampoModalidadeComDB() {
//
//        ModalidadeController modalidadeController = new ModalidadeController();
//        modalidadeController.listarModalidades(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
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
//                            Modalidade modalidade = new Modalidade();
//                            modalidade.setId(Integer.valueOf(object.getString("id")));
//                            modalidade.setDescricao(object.getString("descricao"));
//
//                            listaModalidadesRecuperadas.add(modalidade);
//
//                            configurarCampoModalidade(listaModalidadesRecuperadas);
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void configurarCampoModalidade(ArrayList<Modalidade> listaModalidadesRecuperadas) {
//
//        ArrayAdapter<Modalidade> adapterModalidade= new ArrayAdapter<Modalidade>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                listaModalidadesRecuperadas);
//        autoCompleteTextViewModalidade.setAdapter(adapterModalidade);
//        autoCompleteTextViewModalidade.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewModalidade);
//
//    }
//
//    private void popularCampoAcessoAtendimentoComDb() {
//
//        AcessoAtendimentoController acessoAtendimentoController = new AcessoAtendimentoController();
//        acessoAtendimentoController.listarAcessosAtendimento(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                Log.e("Erro_Atendimento", response);
//
//                try {
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
//                            Acesso acesso = new Acesso();
//                            acesso.setId(Integer.valueOf(object.getString("id")));
//                            acesso.setDescricao(object.getString("descricao"));
//
//                            listaAcessosAtendimentosRecuperados.add(acesso);
//
//                            configurarCampoAcessoAtendimento(listaAcessosAtendimentosRecuperados);
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void configurarCampoAcessoAtendimento(ArrayList<Acesso> listaAcessosAtendimentosRecuperados) {
//
//        ArrayAdapter<Acesso> adapterAcessoServico= new ArrayAdapter<Acesso>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//               listaAcessosAtendimentosRecuperados);
//        autoCompleteTextViewAcessoAtendimento.setAdapter(adapterAcessoServico);
//        autoCompleteTextViewAcessoAtendimento.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewAcessoAtendimento);
//
//    }
//
//    private void receberDadosAtendimentoPreenchidos()
//    {
//        dataAtendimento = autoCompleteTextViewDataAtendimento.getText().toString();
//
//        for(int i = 0; i < arrayListOficiaisSelecionados.size(); i++) {
//            Funcionario oficialSelecionado = arrayListOficiaisSelecionados.get(i);
//            listaDeOficiaisSelecionados.add(String.valueOf(oficialSelecionado.getId()));
//        }
//
//        for(int i = 0; i < arrayListAtendidosSelecionados.size(); i++) {
//            Usuario usuarioSelecionado = arrayListAtendidosSelecionados.get(i);
//            listaDeAtendidosSelecionados.add(String.valueOf(usuarioSelecionado.getId()));
//        }
//
//        for(int i = 0; i < listaUnidadesRecuperadas.size(); i++)
//        {
//            Unidade unidadeSelecionada = listaUnidadesRecuperadas.get(i);
//            if(unidadeSelecionada.getDescricao().equals(autoCompleteTextViewUnidadeAtendimento.getText().toString())) {
//                unidadeAtendimento = String.valueOf(unidadeSelecionada.getId());
//            }
//        }
//
//        for(int i = 0; i < listaModalidadesRecuperadas.size(); i++) {
//            Modalidade modalidadeSelecionada = listaModalidadesRecuperadas.get(i);
//            if(modalidadeSelecionada.getDescricao().equals(autoCompleteTextViewModalidade.getText().toString())) {
//                modalidade = String.valueOf(modalidadeSelecionada.getId());
//            }
//        }
//
//        for(int i = 0; i < listaAcessosAtendimentosRecuperados.size(); i++) {
//            Acesso acessoSelecionado = listaAcessosAtendimentosRecuperados.get(i);
//            if(acessoSelecionado.getDescricao().equals(autoCompleteTextViewAcessoAtendimento.getText().toString())) {
//                acessoAtendimento = String.valueOf(acessoSelecionado.getId());
//            }
//        }
//
//    }
//
//    private boolean validarCadastroAtendimento() {
//        receberDadosAtendimentoPreenchidos();
//
//        if (!TextUtils.isEmpty(dataAtendimento)) {
//
//            if (dataAtendimento.replaceAll("[/]", "").length() == 8) {
//
//                if (!listaDeOficiaisSelecionados.isEmpty()) {
//
//                    if (!listaDeAtendidosSelecionados.isEmpty()) {
//
//                        if (!TextUtils.isEmpty(unidadeAtendimento)) {
//
//                            if (!TextUtils.isEmpty(modalidade)) {
//
//                                if (!TextUtils.isEmpty(acessoAtendimento)) {
//
//                                encapsularValoresParaEnvio();
//                                return true;
//
//                                }
//                                else {
//                                    Toast.makeText(getActivity(),
//                                            "O campo ACESSO AO SERVIÇO é obrigatório.",
//                                            Toast.LENGTH_SHORT).show();
//                                    autoCompleteTextViewAcessoAtendimento.requestFocus();
//                                    return false; }
//
//                            }
//                            else {
//                                Toast.makeText(getActivity(),
//                                        "O campo MODALIDADE é obrigatório.",
//                                        Toast.LENGTH_SHORT).show();
//                                autoCompleteTextViewModalidade.requestFocus();
//                                return false; }
//
//
//                        }
//                        else {
//                            Toast.makeText(getActivity(),
//                                    "O campo UNIDADE DO SERVIÇO é obrigatório.",
//                                    Toast.LENGTH_SHORT).show();
//                            autoCompleteTextViewUnidadeAtendimento.requestFocus();
//                            return false; }
//
//                    }
//                    else {
//                        Toast.makeText(getActivity(),
//                                "É necessário adicionar ao menos um ATENDIDO.",
//                                Toast.LENGTH_SHORT).show();
//                        autoCompleteTextViewAtendido.requestFocus();
//                        return false; }
//
//                }
//                else {
//                    Toast.makeText(getActivity(),
//                            "É necessário adicionar ao menos um OFICIAL RESPONSÁVEL.",
//                            Toast.LENGTH_SHORT).show();
//                    autoCompleteTextViewOficialResponsavel.requestFocus();
//                    return false; }
//
//            }
//            else {
//                Toast.makeText(getActivity(),
//                        "Digite uma senha válida.",
//                        Toast.LENGTH_SHORT).show();
//                autoCompleteTextViewDataAtendimento.requestFocus();
//                return false; }
//
//        }
//        else {
//            Toast.makeText(getActivity(),
//                    "O campo DATA DE ATENDIMENTO é obrigatório.",
//                    Toast.LENGTH_SHORT).show();
//            autoCompleteTextViewDataAtendimento.requestFocus();
//            return false; }
//    }
//
//    public Bundle encapsularValoresParaEnvio()
//    {
//        Bundle bundle = new Bundle();
//
//        bundle.putString("data", dataAtendimento);
//        bundle.putStringArrayList("listaOficiais", listaDeOficiaisSelecionados);
//        bundle.putStringArrayList("listaAtendidos", listaDeAtendidosSelecionados);
//        bundle.putString("unidade", unidadeAtendimento);
//        bundle.putString("modalidade", modalidade);
//        bundle.putString("acesso", acessoAtendimento);
//
//        return bundle;
//    }
//
//    private void abrirProximaTela() {
//
//        btnProxima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(validarCadastroAtendimento())
//                {
//                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();
//
//                    atendimentoRegisterFragment2 = new AtendimentoRegisterFragment2();
//                    atendimentoRegisterFragment2.setArguments(valoresEncapsuladosParaEnvio);
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frameConteudo, atendimentoRegisterFragment2);
//                    fragmentTransaction.addToBackStack(null).commit();
//                }
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onResume() {
//
//        listaOficiaisRecuperados.clear();
//        listaAtendidosRecuperados.clear();
//        listaUnidadesRecuperadas.clear();
//        listaModalidadesRecuperadas.clear();
//        listaAcessosAtendimentosRecuperados.clear();
//        super.onResume();
//    }
}