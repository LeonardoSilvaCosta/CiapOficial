package com.br.ciapoficial.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

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
import com.br.ciapoficial.enums.FuncaoAdministrativaEnum;
import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.SituacaoFuncionalEnum;
import com.br.ciapoficial.enums.UnidadeEnum;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Especialista;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.Telefone;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

public class FuncionarioRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;
    private TextInputLayout textInputLayoutRegistroConselho;
    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra,
            textInputEditTextDataInclusao, textInputEditTextRegistroConselho;
    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewUnidade, autoCompleteTextViewQuadro,
            autoCompleteTextViewEspecialidade, autoCompleteTextViewFuncaoAdministrativa,
            autoCompleteTextViewSituacaoFuncional;
    Button btnCadastrar;

    Funcionario funcionario;
    Especialista especialista;

    private ArrayList<PostoGradCatEnum> listaPostoGradCatRecuperados = new ArrayList<>();
    private ArrayList<UnidadeEnum> listaUnidadesRecuperadas = new ArrayList<>();
    private ArrayList<QuadroEnum> listaQuadrosRecuperados = new ArrayList<>();
    private ArrayList<EspecialidadeEnum> listaEspecialidadesRecuperadas = new ArrayList<>();
    private ArrayList<FuncaoAdministrativaEnum> listaFuncoesAdministrativasRecuperadas = new ArrayList<>();
    private ArrayList<SituacaoFuncionalEnum> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();

    private PostoGradCatEnum postoGradCatEnum;
    private QuadroEnum quadroEnum;
    private String rgMilitar;
    private String nomeGuerra;
    private UnidadeEnum unidadeEnum;
    private Date dataInclusao;
    private FuncaoAdministrativaEnum funcaoAdministrativaEnum;
    private SituacaoFuncionalEnum situacaoFuncionalEnum;
    private EspecialidadeEnum especialidadeEnum;
    private String registroConselho;

    public FuncionarioRegisterFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionario_register3, container, false) ;


        configurarComponentes(view);
        enviarFormulario();
        return view;
    }

    private void configurarComponentes(View view)
    {
        textInputLayoutRegistroConselho = view.findViewById(R.id.textInputLayoutRegistroConselho);
        textInputEditTextRgMilitar = view.findViewById(R.id.edtRgMilitar);
        autoCompleteTextViewPostGradCat = view.findViewById(R.id.edtSpinPostoGrad);
        textInputEditTextNomeGuerra = view.findViewById(R.id.edtNomeGuerra);
        autoCompleteTextViewUnidade = view.findViewById(R.id.edtUnidade);
        textInputEditTextDataInclusao = view.findViewById(R.id.edtDataInclusao);
        autoCompleteTextViewQuadro = view.findViewById(R.id.edtQuadro);
        autoCompleteTextViewEspecialidade = view.findViewById(R.id.edtEspecialidade);
        textInputEditTextRegistroConselho = view.findViewById(R.id.edtRegistroConselho);
        autoCompleteTextViewFuncaoAdministrativa = view.findViewById(R.id.edtFuncaoAdministrativa);
        autoCompleteTextViewSituacaoFuncional = view.findViewById(R.id.edtSitucaoFuncional);
        btnCadastrar = view.findViewById(R.id.btnRegistrar);

        popularCampoPostoGradCatComDB();
        popularCampoUnidadeComDB();
        popularCampoQuadroComDB();
        popularCampoEspecialidadeComDB();
        configurarCampoRegistroConselhoComBaseNaEspecialidade();
        popularCampoFuncaoAdministrativaComDB();
        popularCampoSituacaoFuncionalComDB();
    }

    private void popularCampoPostoGradCatComDB() {

        PostoGradCatController postoGradCatController = new PostoGradCatController();
        postoGradCatController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);


                        PostoGradCatEnum postoGradCatEnum = PostoGradCatEnum.valueOf(object.getString("postoGradCat"));

                        listaPostoGradCatRecuperados.add(postoGradCatEnum);
                        configurarCampoPostoGradCat(listaPostoGradCatRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoPostoGradCat(ArrayList<PostoGradCatEnum> listaPostoGradCatRecuperados) {

        ArrayAdapter<PostoGradCatEnum> adapterPostoGradCat = new ArrayAdapter<PostoGradCatEnum>(getActivity(),
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

                        QuadroEnum quadro = QuadroEnum.valueOf(object.getString("quadro"));

                        listaQuadrosRecuperados.add(quadro);
                        configurarCampoQuadro(listaQuadrosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoQuadro(ArrayList<QuadroEnum> listaQuadrosRecuperados) {

        ArrayAdapter<QuadroEnum> adapterQuadro = new ArrayAdapter<QuadroEnum>(getActivity(),
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

                        UnidadeEnum unidade = UnidadeEnum.valueOf(object.getString("unidade"));

                        listaUnidadesRecuperadas.add(unidade);
                        configurarCampoUnidade(listaUnidadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoUnidade(ArrayList<UnidadeEnum> listaUnidadesRecuperadas) {

        ArrayAdapter<UnidadeEnum> adapterUnidade = new ArrayAdapter<UnidadeEnum>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaUnidadesRecuperadas));
        autoCompleteTextViewUnidade.setAdapter(adapterUnidade);
        autoCompleteTextViewUnidade.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUnidade);

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

                        EspecialidadeEnum especialidade = EspecialidadeEnum.valueOf(object.getString("especialidade"));
                        listaEspecialidadesRecuperadas.add(especialidade);
                        configurarCampoEspecialidade(listaEspecialidadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
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

                            FuncaoAdministrativaEnum funcaoAdministrativa =
                                    FuncaoAdministrativaEnum.valueOf(object.getString("funcaoAdministrativa"));

                            listaFuncoesAdministrativasRecuperadas.add(funcaoAdministrativa);
                            configurarCampoFuncaoAdministrativa(listaFuncoesAdministrativasRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoFuncaoAdministrativa(ArrayList<FuncaoAdministrativaEnum> listaFuncoesAdministrativasRecuperadas) {

        ArrayAdapter<FuncaoAdministrativaEnum> adapterFuncaoAdministrativa = new ArrayAdapter<FuncaoAdministrativaEnum>(getActivity(),
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

                            SituacaoFuncionalEnum situacaoFuncional = SituacaoFuncionalEnum.valueOf(
                                    object.getString("situacaoFuncional"));

                            listaSituacoesFuncionaisRecuperadas.add(situacaoFuncional);
                            configurarCampoSituacaoFuncional(listaSituacoesFuncionaisRecuperadas);

                        }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoSituacaoFuncional(ArrayList<SituacaoFuncionalEnum> listaSituacoesFuncionaisRecuperadas) {

        ArrayAdapter<SituacaoFuncionalEnum> adapterSituacaoFuncional = new ArrayAdapter<SituacaoFuncionalEnum>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaSituacoesFuncionaisRecuperadas));
        autoCompleteTextViewSituacaoFuncional.setAdapter(adapterSituacaoFuncional);
        autoCompleteTextViewSituacaoFuncional.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewSituacaoFuncional);

    }

    private void configurarCampoEspecialidade(ArrayList<EspecialidadeEnum> listaEspecialidadesRecuperadas) {

        ArrayAdapter<EspecialidadeEnum> adapterEspecialidade = new ArrayAdapter<EspecialidadeEnum>(getActivity(),
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

                if (EspecialidadeEnum.PSICOLOGO.equals(position) || EspecialidadeEnum.ASSISTENTE_SOCIAL.equals(position)) {
                    textInputLayoutRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setVisibility(View.VISIBLE);
                } else {
                    textInputEditTextRegistroConselho.setVisibility(View.GONE);
                    textInputLayoutRegistroConselho.setVisibility(View.GONE);
                }
            }
        });
    }

    private Bundle recuperarDadosUserRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();

        return valoresRecebidosFragment1e2;

    }

    private void receberDadosUsuarioPreenchidos() throws ParseException {
        for(int i = 0; i < listaPostoGradCatRecuperados.size(); i++) {
            PostoGradCatEnum postoGradCatSelecionado = listaPostoGradCatRecuperados.get(i);
            if(postoGradCatSelecionado.getNome().equals(autoCompleteTextViewPostGradCat.getText().toString())) {
                postoGradCatEnum = postoGradCatSelecionado;
            }
        }
        for(int i = 0; i < listaQuadrosRecuperados.size(); i++) {
            QuadroEnum quadroSelecionado = listaQuadrosRecuperados.get(i);
            if(quadroSelecionado.getNome().equals(autoCompleteTextViewQuadro.getText().toString())) {
                quadroEnum = quadroSelecionado;
            }
        }

        rgMilitar = textInputEditTextRgMilitar.getText().toString();
        nomeGuerra = textInputEditTextNomeGuerra.getText().toString();

        for(int i = 0; i < listaUnidadesRecuperadas.size(); i++) {
            UnidadeEnum unidadeSelecionada = listaUnidadesRecuperadas.get(i);
            if(unidadeSelecionada.getNome().equals(autoCompleteTextViewUnidade.getText().toString())) {
                unidadeEnum = unidadeSelecionada;
            }
        }

        dataInclusao = DateFormater.StringToDate(textInputEditTextDataInclusao.getText().toString());

        for(int i = 0; i < listaFuncoesAdministrativasRecuperadas.size(); i++) {
            FuncaoAdministrativaEnum funcaoAdministrativaSelecionada = listaFuncoesAdministrativasRecuperadas.get(i);
            if(funcaoAdministrativaSelecionada.getNome().equals(autoCompleteTextViewFuncaoAdministrativa.getText().toString())) {
                funcaoAdministrativaEnum = funcaoAdministrativaSelecionada;
            }
        }

        for(int i = 0; i < listaSituacoesFuncionaisRecuperadas.size(); i++) {
            SituacaoFuncionalEnum situacaoFuncionalSelecionada = listaSituacoesFuncionaisRecuperadas.get(i);
            if(situacaoFuncionalSelecionada.getNome().equals(autoCompleteTextViewSituacaoFuncional.getText().toString())) {
                situacaoFuncionalEnum = situacaoFuncionalSelecionada;
            }
        }

        for(int i = 0; i < listaEspecialidadesRecuperadas.size(); i++) {
            EspecialidadeEnum especialidadeSelecionada = listaEspecialidadesRecuperadas.get(i);
            if(especialidadeSelecionada.getNome().equals(autoCompleteTextViewEspecialidade.getText().toString())) {
                especialidadeEnum = especialidadeSelecionada;
            }
        }

        registroConselho = textInputEditTextRegistroConselho.getText().toString();

    }

    private boolean validarCadastroUsuario() throws ParseException {
        receberDadosUsuarioPreenchidos();

        if (!TextUtils.isEmpty(rgMilitar)) {

            if (!TextUtils.isEmpty(postoGradCatEnum.getNome())) {

                if (!TextUtils.isEmpty(nomeGuerra)) {

                    if (!TextUtils.isEmpty(unidadeEnum.getNome())) {

                        if(!TextUtils.isEmpty(dataInclusao.toString())) {

                        if (!TextUtils.isEmpty(quadroEnum.getNome())) {

                            if (!TextUtils.isEmpty(especialidadeEnum.getNome())) {

                                if (!TextUtils.isEmpty(registroConselho) ||
                                        especialidadeEnum.equals(String.valueOf(listaEspecialidadesRecuperadas.size()))) {

                                    if (!TextUtils.isEmpty(funcaoAdministrativaEnum.getNome())) {

                                        if (!TextUtils.isEmpty(funcaoAdministrativaEnum.getNome())) {

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

    private Funcionario encapsularValoresParaCadastroDeFuncionario() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosUserRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        Date dataNascimento = DateFormater.StringToDate(valoresRecebidosFragment1.getString("dataNascimento"));

        funcionario = new Funcionario();

        funcionario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        funcionario.setDataNascimento(dataNascimento);
        funcionario.setCpf(Mascaras.removerMascaras(valoresRecebidosFragment1.getString("cpf")));
        funcionario.setSexo((SexoEnum) valoresRecebidosFragment1.getSerializable("sexo"));
        funcionario.setTelefones((ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefones"));
        funcionario.setEmail(valoresRecebidosFragment1.getString("email"));
        funcionario.setEndereco((Endereco) valoresRecebidosFragment1e2.getSerializable("endereco"));
        funcionario.setRgMilitar(rgMilitar);
        funcionario.setPostoGradCat((postoGradCatEnum));
        funcionario.setNomeGuerra(nomeGuerra);
        funcionario.setUnidade((unidadeEnum));
        funcionario.setDataInclusao((dataInclusao));
        funcionario.setQuadro((quadroEnum));
        funcionario.setFuncaoAdministrativa((funcaoAdministrativaEnum));
        funcionario.setSituacaoFuncional((situacaoFuncionalEnum));

        Map<String, Funcionario> funcionarioMap = new HashMap<>();

        funcionarioMap.put("Funcionário", funcionario);

        if(quadroEnum.equals(QuadroEnum.QCOPM))
        {
            especialista = new Especialista();

            especialista.setEspecialidade((especialidadeEnum));
            if(especialidadeEnum.equals(EspecialidadeEnum.PSICOLOGO))
            {
                especialista.setRegistroConselho("10/" + registroConselho);
            }
            else if(especialidadeEnum.equals(EspecialidadeEnum.ASSISTENTE_SOCIAL))
            {
                especialista.setRegistroConselho("1/" + registroConselho);
            }else {
                especialista.setRegistroConselho("Não se aplica");
            }

//            funcionarioMap.put("Especialista", especialista);
        }

        return funcionario;
    }

    private void cadastrarUsuario(Funcionario novoFuncionario)
    {
        new FuncionarioController().cadastrar(
                getActivity(),
                novoFuncionario,
                new VolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        Log.e("CadUsuario", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            String mensagem = jsonObject.getString("mensagem");

                            if(isErro) {
                                Toast.makeText(principalFragment.getContext(),
                                        mensagem,
                                        Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(principalFragment.getContext(),
                                        "Cadastro realizado com sucesso!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void enviarFormulario() {

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if(validarCadastroUsuario())
                {
                    Funcionario novoFuncionario;
                    novoFuncionario = encapsularValoresParaCadastroDeFuncionario();

                    cadastrarUsuario(novoFuncionario);

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