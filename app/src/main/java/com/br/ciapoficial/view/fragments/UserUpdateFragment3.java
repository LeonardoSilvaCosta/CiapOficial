package com.br.ciapoficial.view.fragments;

import androidx.fragment.app.Fragment;

public class UserUpdateFragment3 extends Fragment {

//    private PrincipalFragment principalFragment;
//    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra,
//            textInputEditTextRegistroConselho;
//    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewUnidade, autoCompleteTextViewQuadro,
//            autoCompleteTextViewEspecialidade, autoCompleteTextViewFuncaoAdministrativa, autoCompleteTextViewSituacaoFuncional;
//    Button btnCadastrar;
//
//    Funcionario funcionario;
//
//    private ArrayList<PostoGradCat> listaPostoGradCatRecuperados = new ArrayList<>();
//    private ArrayList<Unidade> listaUnidadesRecuperadas = new ArrayList<>();
//    private ArrayList<Quadro> listaQuadrosRecuperados = new ArrayList<>();
//    private ArrayList<Especialidade> listaEspecialidadesRecuperadas = new ArrayList<>();
//    private ArrayList<FuncaoAdministrativa> listaFuncoesAdministrativasRecuperadas = new ArrayList<>();
//    private ArrayList<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();
//
//    String rgMilitar;
//    String postoGradCat;
//    String nomeGuerra;
//    String unidade;
//    String quadro;
//    String especialidade;
//    String registroConselho;
//    String funcaoAdministrativa;
//    String situacaoFuncional;
//
//    SharedPreferences sharedPreferences;
//
//    public UserUpdateFragment3() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_user_update3, container, false) ;
//
//        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
//
//
//        configurarComponentes(view);
//        enviarFormulario();
//        return view;
//    }
//
//    private void configurarComponentes(View view)
//    {
//        textInputEditTextRgMilitar = view.findViewById(R.id.edtRgMilitar);
//        autoCompleteTextViewPostGradCat = view.findViewById(R.id.edtSpinPostoGrad);
//        textInputEditTextNomeGuerra = view.findViewById(R.id.edtNomeGuerra);
//        autoCompleteTextViewUnidade = view.findViewById(R.id.edtUnidade);
//        autoCompleteTextViewQuadro = view.findViewById(R.id.edtQuadro);
//        autoCompleteTextViewEspecialidade = view.findViewById(R.id.edtEspecialidade);
//        textInputEditTextRegistroConselho = view.findViewById(R.id.edtRegistroConselho);
//        autoCompleteTextViewFuncaoAdministrativa = view.findViewById(R.id.edtFuncaoAdministrativa);
//        autoCompleteTextViewSituacaoFuncional = view.findViewById(R.id.edtSitucaoFuncional);
//        btnCadastrar = view.findViewById(R.id.btnRegistrar);
//
//        popularCampoPostoGradCatComDB();
//        popularCampoUnidadeComDB();
//        popularCampoQuadroComDB();
//        popularCampoEspecialidadeComDB();
//        popularCampoFuncaoAdministrativaComDB();
//        popularCampoSituacaoFuncionalComDB();
//
//        receberDadosUsuarioPreviamentePreenchidos();
//    }
//
//    private void popularCampoPostoGradCatComDB() {
//
//        PostoGradCatController postoGradCatController = new PostoGradCatController();
//        postoGradCatController.listar(getActivity(), new VolleyCallback() {
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
//                            PostoGradCat postoGradCat = new PostoGradCat();
//                            postoGradCat.setId(Integer.valueOf(object.getString("id")));
//                            postoGradCat.setNome(object.getString("descricao"));
//
//                            listaPostoGradCatRecuperados.add(postoGradCat);
//                            configurarCampoPostoGradCat(listaPostoGradCatRecuperados);
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
//    private void configurarCampoPostoGradCat(ArrayList<PostoGradCat> listaPostoGradCatRecuperados) {
//
//        ArrayAdapter<PostoGradCat> adapterPostoGradCat = new ArrayAdapter<PostoGradCat>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaPostoGradCatRecuperados));
//        autoCompleteTextViewPostGradCat.setAdapter(adapterPostoGradCat);
//        autoCompleteTextViewPostGradCat.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewPostGradCat);
//
//    }
//
//    private void popularCampoUnidadeComDB() {
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
//                            configurarCampoUnidade(listaUnidadesRecuperadas);
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
//    private void configurarCampoUnidade(ArrayList<Unidade> listaUnidadesRecuperadas) {
//
//        ArrayAdapter<Unidade> adapterUnidade = new ArrayAdapter<Unidade>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaUnidadesRecuperadas));
//        autoCompleteTextViewUnidade.setAdapter(adapterUnidade);
//        autoCompleteTextViewUnidade.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUnidade);
//
//    }
//
//    private void popularCampoQuadroComDB()
//    {
//        QuadroController quadroController = new QuadroController();
//        quadroController.listar(getActivity(), new VolleyCallback() {
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
//                            Quadro quadro = new Quadro();
//                            quadro.setId(Integer.valueOf(object.getString("id")));
//                            quadro.setDescricao(object.getString("descricao"));
//
//                            listaQuadrosRecuperados.add(quadro);
//                            configurarCampoQuadro(listaQuadrosRecuperados);
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
//    private void configurarCampoQuadro(ArrayList<Quadro> listaQuadrosRecuperados) {
//
//        ArrayAdapter<Quadro> adapterQuadro = new ArrayAdapter<Quadro>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaQuadrosRecuperados));
//        autoCompleteTextViewQuadro.setAdapter(adapterQuadro);
//        autoCompleteTextViewQuadro.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewQuadro);
//
//    }
//
//    private void popularCampoEspecialidadeComDB()
//    {
//        EspecialidadeController especialidadeController = new EspecialidadeController();
//        especialidadeController.listar(getActivity(), new VolleyCallback() {
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
//                            Especialidade especialidade = new Especialidade();
//                            especialidade.setId(Integer.valueOf(object.getString("id")));
//                            especialidade.setDescricao(object.getString("descricao"));
//
//                            listaEspecialidadesRecuperadas.add(especialidade);
//                            configurarCampoEspecialidade(listaEspecialidadesRecuperadas);
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
//    private void configurarCampoEspecialidade(ArrayList<Especialidade> listaEspecialidadesRecuperadas) {
//
//        ArrayAdapter<Especialidade> adapterEspecialidade = new ArrayAdapter<Especialidade>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaEspecialidadesRecuperadas));
//        autoCompleteTextViewEspecialidade.setAdapter(adapterEspecialidade);
//        autoCompleteTextViewEspecialidade.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEspecialidade);
//
//    }
//
//    private void popularCampoFuncaoAdministrativaComDB()
//    {
//        FuncaoAdministrativaController funcaoAdministrativaController = new FuncaoAdministrativaController();
//        funcaoAdministrativaController.listar(getActivity(), new VolleyCallback() {
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
//                            FuncaoAdministrativa funcaoAdministrativa = new FuncaoAdministrativa();
//                            funcaoAdministrativa.setId(Integer.valueOf(object.getString("id")));
//                            funcaoAdministrativa.setDescricao(object.getString("descricao"));
//
//                            listaFuncoesAdministrativasRecuperadas.add(funcaoAdministrativa);
//                            configurarCampoFuncaoAdministrativa(listaFuncoesAdministrativasRecuperadas);
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
//    private void configurarCampoFuncaoAdministrativa(ArrayList<FuncaoAdministrativa> listaFuncoesAdministrativasRecuperadas) {
//
//        ArrayAdapter<FuncaoAdministrativa> adapterFuncaoAdministrativa = new ArrayAdapter<FuncaoAdministrativa>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaFuncoesAdministrativasRecuperadas));
//        autoCompleteTextViewFuncaoAdministrativa.setAdapter(adapterFuncaoAdministrativa);
//        autoCompleteTextViewFuncaoAdministrativa.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewFuncaoAdministrativa);
//
//    }
//
//    private void popularCampoSituacaoFuncionalComDB()
//    {
//        SituacaoFuncionalController situacaoFuncionalController = new SituacaoFuncionalController();
//        situacaoFuncionalController.listar(getActivity(), new VolleyCallback() {
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
//                            SituacaoFuncional situacaoFuncional = new SituacaoFuncional();
//                            situacaoFuncional.setId(Integer.valueOf(object.getString("id")));
//                            situacaoFuncional.setDescricao(object.getString("descricao"));
//
//                            listaSituacoesFuncionaisRecuperadas.add(situacaoFuncional);
//                            configurarCampoSituacaoFuncional(listaSituacoesFuncionaisRecuperadas);
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
//    private void configurarCampoSituacaoFuncional(ArrayList<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas) {
//
//        ArrayAdapter<SituacaoFuncional> adapterSituacaoFuncional = new ArrayAdapter<SituacaoFuncional>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaSituacoesFuncionaisRecuperadas));
//        autoCompleteTextViewSituacaoFuncional.setAdapter(adapterSituacaoFuncional);
//        autoCompleteTextViewSituacaoFuncional.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewSituacaoFuncional);
//
//    }
//
//    private Bundle recuperarDadosUserRegisterFragment2() {
//        Bundle valoresRecebidosFragment1e2 = this.getArguments();
//
//        return valoresRecebidosFragment1e2;
//
//    }
//
//    private void receberDadosUsuarioPreviamentePreenchidos() {
//
//        String emailUsuarioLogado = sharedPreferences.getString("userEmail", "userEmail");
//
//        FuncionarioController funcionarioController = new FuncionarioController();
//        funcionarioController.recuperarUsuarioLogado(getActivity(), emailUsuarioLogado, new VolleyCallback() {
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
//                            String email = object.getString("email");
//
//                            if(email.equals(emailUsuarioLogado)) {
//
//                                Funcionario funcionario = new Funcionario();
//                                funcionario.setRgMilitar(object.getString("rgMilitar"));
//                                funcionario.setPostoGradCat(object.getString("postoGradCat"));
//                                funcionario.setNomeGuerra(object.getString("nomeGuerra"));
//                                funcionario.setUnidade(object.getString("unidade"));
//                                funcionario.setQuadro(object.getString("quadro"));
//                                funcionario.setEspecialidade(object.getString("especialidade"));
//                                funcionario.setRegistroConselho(object.getString("registroConselho"));
//                                funcionario.setFuncaoAdministrativa(object.getString("funcaoAdministrativa"));
//                                funcionario.setSituacaoFuncional(object.getString("situacaoFuncional"));
//
//                                textInputEditTextRgMilitar.setText(funcionario.getRgMilitar());
//                                autoCompleteTextViewPostGradCat.setText(funcionario.getPostoGradCat());
//                                textInputEditTextNomeGuerra.setText(funcionario.getNomeGuerra());
//                                autoCompleteTextViewUnidade.setText(funcionario.getUnidade());
//                                autoCompleteTextViewQuadro.setText(funcionario.getQuadro());
//                                autoCompleteTextViewEspecialidade.setText(funcionario.getEspecialidade());
//                                textInputEditTextRegistroConselho.setText(funcionario.getRegistroConselho());
//                                autoCompleteTextViewFuncaoAdministrativa.setText(funcionario.getFuncaoAdministrativa());
//                                autoCompleteTextViewSituacaoFuncional.setText(funcionario.getSituacaoFuncional());
//
//
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
//    private void receberDadosUsuarioPreenchidos()
//    {
//        rgMilitar = textInputEditTextRgMilitar.getText().toString();
//
//        for(int i = 0; i < listaPostoGradCatRecuperados.size(); i++) {
//            PostoGradCat postoGradCatSelecionado = listaPostoGradCatRecuperados.get(i);
//            if(postoGradCatSelecionado.getNome().equals(autoCompleteTextViewPostGradCat.getText().toString())) {
//                postoGradCat = String.valueOf(postoGradCatSelecionado.getId());
//            }
//        }
//        nomeGuerra = textInputEditTextNomeGuerra.getText().toString();
//        for(int i = 0; i < listaUnidadesRecuperadas.size(); i++) {
//            Unidade unidadeSelecionada = listaUnidadesRecuperadas.get(i);
//            if(unidadeSelecionada.getDescricao().equals(autoCompleteTextViewUnidade.getText().toString())) {
//                unidade = String.valueOf(unidadeSelecionada.getId());
//            }
//        }
//
//        for(int i = 0; i < listaQuadrosRecuperados.size(); i++) {
//            Quadro quadroSelecionado = listaQuadrosRecuperados.get(i);
//            if(quadroSelecionado.getDescricao().equals(autoCompleteTextViewQuadro.getText().toString())) {
//                quadro = String.valueOf(quadroSelecionado.getId());
//            }
//        }
//
//        for(int i = 0; i < listaEspecialidadesRecuperadas.size(); i++) {
//            Especialidade especialidadeSelecionada = listaEspecialidadesRecuperadas.get(i);
//            if(especialidadeSelecionada.getDescricao().equals(autoCompleteTextViewEspecialidade.getText().toString())) {
//                especialidade = String.valueOf(especialidadeSelecionada.getId());
//            }
//        }
//        registroConselho = textInputEditTextRegistroConselho.getText().toString();
//        for(int i = 0; i < listaFuncoesAdministrativasRecuperadas.size(); i++) {
//            FuncaoAdministrativa funcaoAdministrativaSelecionada = listaFuncoesAdministrativasRecuperadas.get(i);
//            if(funcaoAdministrativaSelecionada.getDescricao().equals(autoCompleteTextViewFuncaoAdministrativa.getText().toString())) {
//                funcaoAdministrativa = String.valueOf(funcaoAdministrativaSelecionada.getId());
//            }
//        }
//
//        for(int i = 0; i < listaSituacoesFuncionaisRecuperadas.size(); i++) {
//            SituacaoFuncional situacaoFuncionalSelecionada = listaSituacoesFuncionaisRecuperadas.get(i);
//            if(situacaoFuncionalSelecionada.getDescricao().equals(autoCompleteTextViewSituacaoFuncional.getText().toString())) {
//                situacaoFuncional = String.valueOf(situacaoFuncionalSelecionada.getId());
//            }
//        }
//    }
//
//    private boolean validarCadastroUsuario() {
//        receberDadosUsuarioPreenchidos();
//
//        if (!TextUtils.isEmpty(rgMilitar)) {
//
//            if (!TextUtils.isEmpty(postoGradCat)) {
//
//                if (!TextUtils.isEmpty(nomeGuerra)) {
//
//                    if (!TextUtils.isEmpty(unidade)) {
//
//                        if (!TextUtils.isEmpty(quadro)) {
//
//                            if (!TextUtils.isEmpty(especialidade)) {
//
//                                if (!TextUtils.isEmpty(registroConselho)) {
//
//                                    if (!TextUtils.isEmpty(funcaoAdministrativa)) {
//
//                                        return true;
//
//                                    }
//                                    else {
//                                        autoCompleteTextViewFuncaoAdministrativa.setError("O campo FUNÇÃO ADMINISTRATIVA é obrigatório!");
//                                        autoCompleteTextViewFuncaoAdministrativa.requestFocus();
//                                        return false; }
//
//                                }
//                                else {
//                                    textInputEditTextRegistroConselho.setError("O campo REGISTRO CONSELHO é obrigatório!");
//                                    textInputEditTextRegistroConselho.requestFocus();
//                                    return false; }
//
//                            }
//                            else {
//                                autoCompleteTextViewEspecialidade.setError("O campo ESPECIALIDADE é obrigatório!");
//                                autoCompleteTextViewEspecialidade.requestFocus();
//                                return false; }
//
//                        }
//                        else {
//                            autoCompleteTextViewQuadro.setError("O campo QUADRO é obrigatório!");
//                            autoCompleteTextViewQuadro.requestFocus();
//                            return false; }
//
//                    }
//                    else {
//                        autoCompleteTextViewUnidade.setError("O campo UNIDADE é obrigatório!");
//                        autoCompleteTextViewUnidade.requestFocus();
//                        return false; }
//
//                }
//                else {
//                    textInputEditTextNomeGuerra.setError("O campo NOME GUERRA é obrigatório!.");
//                    textInputEditTextNomeGuerra.requestFocus();
//                    return false; }
//
//            }
//            else {
//                autoCompleteTextViewPostGradCat.setError("O campo POSTO/GRAD/CAT é obrigatório!");
//                autoCompleteTextViewPostGradCat.requestFocus();
//                return false; }
//
//        }
//        else {
//            textInputEditTextRgMilitar.setError("O campo RG é obrigatório!");
//            textInputEditTextRgMilitar.requestFocus();
//            return false; }
//    }
//
//    private Funcionario encapsularValoresParaCadastro()
//    {
//        Bundle valoresRecebidosFragment1e2 =  recuperarDadosUserRegisterFragment2();
//        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");
//
//        funcionario = new Funcionario();
//
//        funcionario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
//        funcionario.setDataNascimento(DataEntreJavaEMysql.enviarDataParaMySqlComoString(valoresRecebidosFragment1.getString("dataNascimento")));
//        funcionario.setCpf(Mascaras.removerMascaras(valoresRecebidosFragment1.getString("cpf")));
//        funcionario.setSexo(valoresRecebidosFragment1.getString("sexo"));
//        funcionario.setTelefones((valoresRecebidosFragment1.getStringArrayList("telefones")));
//        funcionario.setEmail(valoresRecebidosFragment1.getString("email"));
//        funcionario.setCep(Mascaras.removerMascaras(valoresRecebidosFragment1e2.getString("cep")));
//        funcionario.setUf(valoresRecebidosFragment1e2.getString("uf"));
//        funcionario.setCidade(valoresRecebidosFragment1e2.getString("cidade"));
//        funcionario.setBairro(valoresRecebidosFragment1e2.getString("bairro"));
//        funcionario.setLogradouro(valoresRecebidosFragment1e2.getString("logradouro"));
//        funcionario.setNumero(valoresRecebidosFragment1e2.getString("numero"));
//        funcionario.setRgMilitar(rgMilitar);
//        funcionario.setPostoGradCat(postoGradCat);
//        funcionario.setNomeGuerra(nomeGuerra);
//        funcionario.setUnidade(unidade);
//        funcionario.setQuadro(quadro);
//        funcionario.setEspecialidade(especialidade);
//        funcionario.setRegistroConselho(registroConselho);
//        funcionario.setFuncaoAdministrativa(funcaoAdministrativa);
//        funcionario.setSituacaoFuncional((situacaoFuncional));
//        funcionario.setSenha(valoresRecebidosFragment1.getString("senha"));
//
//        return funcionario;
//    }
//
//    private void atualizarUsuario(Funcionario novoFuncionario)
//    {
//        String id = sharedPreferences.getString("userId", "");
//        novoFuncionario.setId(Integer.valueOf(id));
//        new FuncionarioController().atualizarUsuario(
//                getActivity(),
//                novoFuncionario,
//                new VolleyCallback() {
//                    @Override
//                    public void onSucess(String response) {
//                        Log.e("UpdateError", response);
//
//                        try{
//
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            boolean isErro = jsonObject.getBoolean("erro");
//
//                            String mensagem = jsonObject.getString("mensagem");
//
//                            if(isErro) {
//                                Toast.makeText(principalFragment.getContext(),
//                                        mensagem,
//                                        Toast.LENGTH_SHORT).show();
//                            }else {
//
//                                Toast.makeText(principalFragment.getContext(),
//                                        mensagem,
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                        }catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//    private void deletarTelefones() {
//
//        Bundle valoresRecebidosFragment1e2 =  recuperarDadosUserRegisterFragment2();
//        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");
//
//        TelefoneController telefoneController = new TelefoneController();
//        telefoneController.deletarTelefone(
//                getActivity(),
//                (ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefonesParaDeletar"),
//                new VolleyCallback() {
//                    @Override
//                    public void onSucess(String response) {
//
//                        try {
//
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            boolean isErro = jsonObject.getBoolean("erro");
//                            String mensagem = jsonObject.getString("mensagem");
//
//                            if(isErro) {
//                                Toast.makeText(getActivity(),
//                                        "Exclusão falhou " + mensagem,
//                                        Toast.LENGTH_SHORT).show();
//                            }else {
//
//                                Toast.makeText(getActivity(),
//                                        "Telefone excluído com sucesso! " + mensagem,
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                        }catch (JSONException jsonE) {
//                            jsonE.printStackTrace();
//                        }
//
//                    }
//                }
//        );
//
//    }
//
//    private void enviarFormulario() {
//
//        btnCadastrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(validarCadastroUsuario())
//                {
//                    Funcionario novoFuncionario;
//                    novoFuncionario = encapsularValoresParaCadastro();
//
//                    atualizarUsuario(novoFuncionario);
//                    deletarTelefones();
//
//                    principalFragment = new PrincipalFragment();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i)
//                    {
//                        fragmentManager.popBackStack();
//                    }
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frameConteudo, principalFragment);
//                    fragmentTransaction.commit();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void onResume() {
//        listaPostoGradCatRecuperados.clear();
//        listaUnidadesRecuperadas.clear();
//        listaQuadrosRecuperados.clear();
//        listaEspecialidadesRecuperadas.clear();
//        listaFuncoesAdministrativasRecuperadas.clear();
//        listaSituacoesFuncionaisRecuperadas.clear();
//        super.onResume();
//    }
}