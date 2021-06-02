package com.br.ciapoficial.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EspecialidadeController;
import com.br.ciapoficial.controller.FuncaoAdministrativaController;
import com.br.ciapoficial.controller.FuncionarioController;
import com.br.ciapoficial.controller.PostoGradCatController;
import com.br.ciapoficial.controller.QuadroController;
import com.br.ciapoficial.controller.SituacaoFuncionalController;
import com.br.ciapoficial.controller.UnidadeController;
import com.br.ciapoficial.enums.EspecialidadeEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Especialidade;
import com.br.ciapoficial.model.Especialista;
import com.br.ciapoficial.model.FuncaoAdministrativa;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.PostoGradCat;
import com.br.ciapoficial.model.Quadro;
import com.br.ciapoficial.model.SituacaoFuncional;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Unidade;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

public class FuncionarioRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;

    private TextInputLayout textInputLayoutRegistroConselho;
    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra,
            textInputEditTextDataInclusao, textInputEditTextRegistroConselho;
    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewQuadro,
            autoCompleteTextViewUnidade, autoCompleteTextViewFuncaoAdministrativa,
            autoCompleteTextViewSituacaoFuncional, autoCompleteTextViewEspecialidade;
    private Button btnCadastrar;

    private ArrayList<PostoGradCat> listaPostoGradCatRecuperados = new ArrayList<>();
    private ArrayList<Quadro> listaQuadrosRecuperados = new ArrayList<>();
    private ArrayList<Unidade> listaUnidadesRecuperadas = new ArrayList<>();
    private ArrayList<FuncaoAdministrativa> listaFuncoesAdministrativasRecuperadas = new ArrayList<>();
    private ArrayList<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();
    private ArrayList<Especialidade> listaEspecialidadesRecuperadas = new ArrayList<>();

    private Funcionario funcionario;
    private Especialista especialista;

    private PostoGradCat postoGradCat;
    private Quadro quadro;
    private String rgMilitar;
    private String nomeGuerra;
    private Unidade unidade;
    private LocalDate dataInclusao;
    private FuncaoAdministrativa funcaoAdministrativa;
    private SituacaoFuncional situacaoFuncional;
    private Especialidade especialidade;
    private String registroConselho;

    public FuncionarioRegisterFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionario_register3, container, false) ;

        configurarComponentes(view);
        configurarMascaraParaDataDeInclusao();
        popularCampoPostoGradCatComDB();
        popularCampoUnidadeComDB();
        popularCampoQuadroComDB();
        popularCampoEspecialidadeComDB();
        configurarCampoRegistroConselhoComBaseNaEspecialidade();
        popularCampoFuncaoAdministrativaComDB();
        popularCampoSituacaoFuncionalComDB();
        enviarFormulario();
        return view;
    }

    private void configurarComponentes(View view)
    {
        autoCompleteTextViewPostGradCat = view.findViewById(R.id.edtPostoGradCat);
        autoCompleteTextViewQuadro = view.findViewById(R.id.edtQuadro);
        textInputEditTextRgMilitar = view.findViewById(R.id.edtRgMilitar);
        textInputEditTextNomeGuerra = view.findViewById(R.id.edtNomeGuerra);
        autoCompleteTextViewUnidade = view.findViewById(R.id.edtUnidade);
        textInputEditTextDataInclusao = view.findViewById(R.id.edtDataInclusao);
        autoCompleteTextViewFuncaoAdministrativa = view.findViewById(R.id.edtFuncaoAdministrativa);
        autoCompleteTextViewSituacaoFuncional = view.findViewById(R.id.edtSitucaoFuncional);
        autoCompleteTextViewEspecialidade = view.findViewById(R.id.edtEspecialidade);
        textInputLayoutRegistroConselho = view.findViewById(R.id.textInputLayoutRegistroConselho);
        textInputEditTextRegistroConselho = view.findViewById(R.id.edtRegistroConselho);
        btnCadastrar = view.findViewById(R.id.btnRegistrar);
    }

    private void configurarMascaraParaDataDeInclusao()
    {Mascaras.criarMascaraParaData(textInputEditTextDataInclusao);}

    private void popularCampoPostoGradCatComDB() {

        PostoGradCatController postoGradCatController = new PostoGradCatController();
        postoGradCatController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        PostoGradCat postoGradCat = new PostoGradCat();
                        postoGradCat.setId(Integer.parseInt(object.getString("id")));
                        postoGradCat.setNome(object.getString("nome"));

                        listaPostoGradCatRecuperados.add(postoGradCat);
                        configurarCampoPostoGradCat(listaPostoGradCatRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoPostoGradCat(ArrayList<PostoGradCat> listaPostoGradCatRecuperados) {

        ArrayAdapter<PostoGradCat> adapterPostoGradCat = new ArrayAdapter<PostoGradCat>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaPostoGradCatRecuperados));
        autoCompleteTextViewPostGradCat.setAdapter(adapterPostoGradCat);
        autoCompleteTextViewPostGradCat.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewPostGradCat);

    }

    private void popularCampoQuadroComDB()
    {
        QuadroController quadroController = new QuadroController();
        quadroController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Quadro quadro = new Quadro();
                        quadro.setId(Integer.parseInt(object.getString("id")));
                        quadro.setNome(object.getString("nome"));

                        listaQuadrosRecuperados.add(quadro);
                        configurarCampoQuadro(listaQuadrosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoQuadro(ArrayList<Quadro> listaQuadrosRecuperados) {

        ArrayAdapter<Quadro> adapterQuadro = new ArrayAdapter<Quadro>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaQuadrosRecuperados));
        autoCompleteTextViewQuadro.setAdapter(adapterQuadro);
        autoCompleteTextViewQuadro.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewQuadro);

    }

    private void popularCampoUnidadeComDB() {

        UnidadeController unidadeController = new UnidadeController();
        unidadeController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Unidade unidade = new Unidade();
                        unidade.setId(Integer.parseInt(object.getString("id")));
                        unidade.setNome(object.getString("nome"));

                        listaUnidadesRecuperadas.add(unidade);
                        configurarCampoUnidade(listaUnidadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoUnidade(ArrayList<Unidade> listaUnidadesRecuperadas) {

        ArrayAdapter<Unidade> adapterUnidade = new ArrayAdapter<Unidade>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaUnidadesRecuperadas));
        autoCompleteTextViewUnidade.setAdapter(adapterUnidade);
        autoCompleteTextViewUnidade.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUnidade);

    }

    private void popularCampoFuncaoAdministrativaComDB()
    {
        FuncaoAdministrativaController funcaoAdministrativaController = new FuncaoAdministrativaController();
        funcaoAdministrativaController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        FuncaoAdministrativa funcaoAdministrativa = new FuncaoAdministrativa();
                        funcaoAdministrativa.setId(object.getInt("id"));
                        funcaoAdministrativa.setNome(object.getString("nome"));

                        listaFuncoesAdministrativasRecuperadas.add(funcaoAdministrativa);
                        configurarCampoFuncaoAdministrativa(listaFuncoesAdministrativasRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoFuncaoAdministrativa(ArrayList<FuncaoAdministrativa> listaFuncoesAdministrativasRecuperadas) {

        ArrayAdapter<FuncaoAdministrativa> adapterFuncaoAdministrativa = new ArrayAdapter<FuncaoAdministrativa>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaFuncoesAdministrativasRecuperadas));
        autoCompleteTextViewFuncaoAdministrativa.setAdapter(adapterFuncaoAdministrativa);
        autoCompleteTextViewFuncaoAdministrativa.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewFuncaoAdministrativa);

    }

    private void popularCampoSituacaoFuncionalComDB()
    {
        SituacaoFuncionalController situacaoFuncionalController = new SituacaoFuncionalController();
        situacaoFuncionalController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        SituacaoFuncional situacaoFuncional = new SituacaoFuncional();
                        situacaoFuncional.setId(object.getInt("id"));
                        situacaoFuncional.setNome(object.getString("nome"));

                        listaSituacoesFuncionaisRecuperadas.add(situacaoFuncional);
                        configurarCampoSituacaoFuncional(listaSituacoesFuncionaisRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoSituacaoFuncional(ArrayList<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas) {

        ArrayAdapter<SituacaoFuncional> adapterSituacaoFuncional = new ArrayAdapter<SituacaoFuncional>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaSituacoesFuncionaisRecuperadas));
        autoCompleteTextViewSituacaoFuncional.setAdapter(adapterSituacaoFuncional);
        autoCompleteTextViewSituacaoFuncional.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewSituacaoFuncional);

    }

    private void popularCampoEspecialidadeComDB()
    {
        EspecialidadeController especialidadeController = new EspecialidadeController();
        especialidadeController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Especialidade especialidade = new Especialidade();
                        especialidade.setId(object.getInt("id"));
                        especialidade.setNome(object.getString("nome"));

                        listaEspecialidadesRecuperadas.add(especialidade);
                        configurarCampoEspecialidade(listaEspecialidadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEspecialidade(ArrayList<Especialidade> listaEspecialidadesRecuperadas) {

        ArrayAdapter<Especialidade> adapterEspecialidade = new ArrayAdapter<Especialidade>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEspecialidadesRecuperadas));
        autoCompleteTextViewEspecialidade.setAdapter(adapterEspecialidade);
        autoCompleteTextViewEspecialidade.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEspecialidade);

    }

    private void configurarCampoRegistroConselhoComBaseNaEspecialidade()
    {
        autoCompleteTextViewEspecialidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (EspecialidadeEnum.PSICOLOGO.ordinal() == position ||
                        EspecialidadeEnum.ASSISTENTE_SOCIAL.ordinal() == (position)) {
                    textInputLayoutRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setVisibility(View.VISIBLE);
                } else {
                    textInputEditTextRegistroConselho.setVisibility(View.GONE);
                    textInputLayoutRegistroConselho.setVisibility(View.GONE);
                }
            }
        });
    }

    private Bundle recuperarDadosDoFuncionarioDoRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();

        return valoresRecebidosFragment1e2;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void receberDadosDoFuncionarioPreenchidos() throws ParseException {

        postoGradCat = new PostoGradCat();
        quadro = new Quadro();
        unidade = new Unidade();
        funcaoAdministrativa = new FuncaoAdministrativa();
        situacaoFuncional = new SituacaoFuncional();
        especialidade = new Especialidade();

        for(int i = 0; i < listaPostoGradCatRecuperados.size(); i++) {
            PostoGradCat postoGradCatSelecionado = listaPostoGradCatRecuperados.get(i);
            if(postoGradCatSelecionado.getNome().equals(autoCompleteTextViewPostGradCat.getText().toString())) {
                postoGradCat.setId(postoGradCatSelecionado.getId());
                postoGradCat.setNome(postoGradCatSelecionado.getNome());
            }
        }
        for(int i = 0; i < listaQuadrosRecuperados.size(); i++) {
            Quadro quadroSelecionado = listaQuadrosRecuperados.get(i);
            if(quadroSelecionado.getNome().equals(autoCompleteTextViewQuadro.getText().toString())) {
                quadro.setId(quadroSelecionado.getId());
                quadro.setNome(quadroSelecionado.getNome());
            }
        }

        rgMilitar = textInputEditTextRgMilitar.getText().toString().trim();
        nomeGuerra = textInputEditTextNomeGuerra.getText().toString().trim();

        for(int i = 0; i < listaUnidadesRecuperadas.size(); i++) {
            Unidade unidadeSelecionada = listaUnidadesRecuperadas.get(i);
            if(unidadeSelecionada.getNome().equals(autoCompleteTextViewUnidade.getText().toString())) {
                unidade.setId(unidadeSelecionada.getId());
                unidade.setNome(unidadeSelecionada.getNome());
            }
        }

        dataInclusao = DateFormater.StringToLocalDate(textInputEditTextDataInclusao.getText().toString());

        for(int i = 0; i < listaFuncoesAdministrativasRecuperadas.size(); i++) {
            FuncaoAdministrativa funcaoAdministrativaSelecionada = listaFuncoesAdministrativasRecuperadas.get(i);
            if(funcaoAdministrativaSelecionada.getNome().equals(autoCompleteTextViewFuncaoAdministrativa.getText().toString())) {
                funcaoAdministrativa.setId(funcaoAdministrativaSelecionada.getId());
                funcaoAdministrativa.setNome(funcaoAdministrativaSelecionada.getNome());
            }
        }

        for(int i = 0; i < listaSituacoesFuncionaisRecuperadas.size(); i++) {
            SituacaoFuncional situacaoFuncionalSelecionada = listaSituacoesFuncionaisRecuperadas.get(i);
            if(situacaoFuncionalSelecionada.getNome().equals(autoCompleteTextViewSituacaoFuncional.getText().toString())) {
                situacaoFuncional.setId(situacaoFuncionalSelecionada.getId());
                situacaoFuncional.setNome(situacaoFuncionalSelecionada.getNome());
            }
        }

        for(int i = 0; i < listaEspecialidadesRecuperadas.size(); i++) {
            Especialidade especialidadeSelecionada = listaEspecialidadesRecuperadas.get(i);
            if(especialidadeSelecionada.getNome().equals(autoCompleteTextViewEspecialidade.getText().toString())) {
                especialidade.setId(especialidadeSelecionada.getId());
                especialidade.setNome(especialidadeSelecionada.getNome());
            }
        }

        registroConselho = textInputEditTextRegistroConselho.getText().toString().trim();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroDoFuncionario() throws ParseException {
        receberDadosDoFuncionarioPreenchidos();

        if (!TextUtils.isEmpty(rgMilitar)) {

            if (!TextUtils.isEmpty(postoGradCat.getNome())) {

                if (!TextUtils.isEmpty(nomeGuerra)) {

                    if (!TextUtils.isEmpty(unidade.getNome())) {

                        if(!TextUtils.isEmpty(dataInclusao.toString())) {

                            if (!TextUtils.isEmpty(quadro.getNome())) {

                                if (!TextUtils.isEmpty(especialidade.getNome())) {

                                    if (!TextUtils.isEmpty(registroConselho) ||
                                            especialidade.equals(String.valueOf(listaEspecialidadesRecuperadas.size()))) {

                                        if (!TextUtils.isEmpty(funcaoAdministrativa.getNome())) {

                                            if (!TextUtils.isEmpty(funcaoAdministrativa.getNome())) {

                                                return true;

                                            }
                                            else {
                                                autoCompleteTextViewSituacaoFuncional.setError("O campo SITUAÇÃO FUNCIONAL é obrigatório!");
                                                autoCompleteTextViewSituacaoFuncional.requestFocus();
                                                return false; }

                                        }
                                        else {
                                            autoCompleteTextViewFuncaoAdministrativa.setError("O campo FUNÇÃO ADMINISTRATIVA é obrigatório!");
                                            autoCompleteTextViewFuncaoAdministrativa.requestFocus();
                                            return false; }

                                    }
                                    else {
                                        textInputEditTextRegistroConselho.setError("O campo REGISTRO CONSELHO é obrigatório!");
                                        textInputEditTextRegistroConselho.requestFocus();
                                        return false; }

                                }
                                else {
                                    autoCompleteTextViewEspecialidade.setError("O campo ESPECIALIDADE é obrigatório!");
                                    autoCompleteTextViewEspecialidade.requestFocus();
                                    return false; }

                            }
                            else {
                                autoCompleteTextViewQuadro.setError("O campo QUADRO é obrigatório!");
                                autoCompleteTextViewQuadro.requestFocus();
                                return false; }

                        }
                        else {
                            textInputEditTextDataInclusao.setError("O campo DATA é obrigatório!");
                            textInputEditTextDataInclusao.requestFocus();
                            return false; }

                    }
                    else {
                        autoCompleteTextViewUnidade.setError("O campo UNIDADE é obrigatório!");
                        autoCompleteTextViewUnidade.requestFocus();
                        return false; }

                }
                else {
                    textInputEditTextNomeGuerra.setError("O campo NOME GUERRA é obrigatório!.");
                    textInputEditTextNomeGuerra.requestFocus();
                    return false; }

            }
            else {
                autoCompleteTextViewPostGradCat.setError("O campo POSTO/GRAD/CAT é obrigatório!");
                autoCompleteTextViewPostGradCat.requestFocus();
                return false; }

        }
        else {
            textInputEditTextRgMilitar.setError("O campo RG é obrigatório!");
            textInputEditTextRgMilitar.requestFocus();
            return false; }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Funcionario encapsularValoresParaCadastroDeFuncionario() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosDoFuncionarioDoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        LocalDate dataNascimento = DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("dataNascimento"));

        funcionario = new Funcionario();

        funcionario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        funcionario.setDataNascimento(dataNascimento);
        funcionario.setCpf(Mascaras.removerMascaras(valoresRecebidosFragment1.getString("cpf")));
        funcionario.setSexo((SexoEnum) valoresRecebidosFragment1.getSerializable("sexo"));
        funcionario.setTelefones((ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefones"));
        funcionario.setEmail(valoresRecebidosFragment1.getString("email"));
        funcionario.setEndereco((Endereco) valoresRecebidosFragment1e2.getSerializable("endereco"));
        funcionario.setRgMilitar(rgMilitar);
        funcionario.setPostoGradCat((postoGradCat));
        funcionario.setNomeGuerra(nomeGuerra);
        funcionario.setUnidade((unidade));
        funcionario.setDataInclusao((dataInclusao));
        funcionario.setQuadro((quadro));
        funcionario.setFuncaoAdministrativa((funcaoAdministrativa));
        funcionario.setSituacaoFuncional((situacaoFuncional));
        funcionario.setSenha(Mascaras.removerMascaras(valoresRecebidosFragment1.getString("cpf")));

        Map<String, Funcionario> funcionarioMap = new HashMap<>();

        funcionarioMap.put("Funcionário", funcionario);

        if(quadro.equals(QuadroEnum.QCOPM))
        {
            especialista = new Especialista();

            especialista.setEspecialidade((especialidade));
            if(especialidade.equals(EspecialidadeEnum.PSICOLOGO))
            {
                especialista.setRegistroConselho("10/" + registroConselho);
            }
            else if(especialidade.equals(EspecialidadeEnum.ASSISTENTE_SOCIAL))
            {
                especialista.setRegistroConselho("1/" + registroConselho);
            }else {
                especialista.setRegistroConselho("Não se aplica");
            }

//            funcionarioMap.put("Especialista", especialista);
        }

        return funcionario;
    }

    private void cadastrarFuncionario(Funcionario novoFuncionario)
    {
        new FuncionarioController().cadastrar(
                getActivity(),
                novoFuncionario,
                new VolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(principalFragment.getContext(),
                                    "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void enviarFormulario() {

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if(validarCadastroDoFuncionario())
                {
                    Funcionario novoFuncionario;
                    novoFuncionario = encapsularValoresParaCadastroDeFuncionario();

                    cadastrarFuncionario(novoFuncionario);

                    principalFragment = new PrincipalFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i)
                    {
                        fragmentManager.popBackStack();
                    }
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, principalFragment);
                    fragmentTransaction.commit();
                }
            }
        });

    }

}