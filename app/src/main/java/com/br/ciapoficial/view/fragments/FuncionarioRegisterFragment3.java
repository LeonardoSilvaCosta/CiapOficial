package com.br.ciapoficial.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.network.EspecialidadeController;
import com.br.ciapoficial.network.EspecialistaController;
import com.br.ciapoficial.network.FuncaoAdministrativaController;
import com.br.ciapoficial.network.FuncionarioController;
import com.br.ciapoficial.network.PostoGradCatController;
import com.br.ciapoficial.network.QuadroController;
import com.br.ciapoficial.network.SituacaoFuncionalController;
import com.br.ciapoficial.network.UnidadeController;
import com.br.ciapoficial.enums.EspecialidadeEnum;
import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.validation.FieldValidator;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.Especialidade;
import com.br.ciapoficial.model.Especialista;
import com.br.ciapoficial.model.EstadoCivil;
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
import java.util.List;

import lombok.SneakyThrows;

public class FuncionarioRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;
    private TextInputLayout textInputLayoutQuadro, textInputLayoutRgMilitar,
            textInputLayoutEspecialidade, textInputLayoutRegistroConselho;
    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra,
            textInputEditTextDataInclusao, textInputEditTextRegistroConselho;
    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewQuadro,
            autoCompleteTextViewUnidade, autoCompleteTextViewFuncaoAdministrativa,
            autoCompleteTextViewSituacaoFuncional, autoCompleteTextViewEspecialidade;
    private Button btnCadastrar;

    private List<PostoGradCat> listaPostoGradCatRecuperados = new ArrayList<>();
    private List<Quadro> listaQuadrosRecuperados = new ArrayList<>();
    private List<Unidade> listaUnidadesRecuperadas = new ArrayList<>();
    private List<FuncaoAdministrativa> listaFuncoesAdministrativasRecuperadas = new ArrayList<>();
    private List<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();
    private List<Especialidade> listaEspecialidadesRecuperadas = new ArrayList<>();

    private Funcionario funcionario;
    private Especialista especialista;

    private PostoGradCat postoGradCat = new PostoGradCat();
    private Quadro quadro = new Quadro();
    private String rgMilitar;
    private String nomeGuerra;
    private Unidade unidade = new Unidade();
    private LocalDate dataInclusao;
    private FuncaoAdministrativa funcaoAdministrativa = new FuncaoAdministrativa();
    private SituacaoFuncional situacaoFuncional = new SituacaoFuncional();
    private Especialidade especialidade = new Especialidade();
    private String registroConselho;

    //verificar por que não está sendo realizado o cadastro de funcionários do tipo VC

    public FuncionarioRegisterFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionario_register3, container, false) ;

        configurarComponentes(view);
        configurarMascaraParaDataDeInclusao();
        definirVisibilidadeDoCampoQuadroComBaseNoPostoGradCat();
        definirVisibilidadeDoCampoEspecialidadeComBaseNoQuadro();
        definirVisibilidadeDoCampoRegistroConselhoComBaseNaEspecialidade();
        popularCampoPostoGradCatComDB();
        popularCampoUnidadeComDB();
        popularCampoQuadroComDB();
        popularCampoEspecialidadeComDB();
        popularCampoFuncaoAdministrativaComDB();
        popularCampoSituacaoFuncionalComDB();
        enviarFormulario();
        return view;
    }

    private void configurarComponentes(View view)
    {
        textInputLayoutQuadro = view.findViewById(R.id.textInputLayoutQuadro);
        textInputLayoutRgMilitar = view.findViewById(R.id.textInputLayoutRgMilitar);
        textInputLayoutEspecialidade = view.findViewById(R.id.textInputLayoutEspecialidade);
        textInputLayoutRegistroConselho = view.findViewById(R.id.textInputLayoutRegistroConselho);
        autoCompleteTextViewPostGradCat = view.findViewById(R.id.edtPostoGradCat);
        autoCompleteTextViewQuadro = view.findViewById(R.id.edtQuadro);
        textInputEditTextRgMilitar = view.findViewById(R.id.edtRgMilitar);
        textInputEditTextNomeGuerra = view.findViewById(R.id.edtNomeGuerra);
        autoCompleteTextViewUnidade = view.findViewById(R.id.edtUnidade);
        textInputEditTextDataInclusao = view.findViewById(R.id.edtDataInclusao);
        autoCompleteTextViewFuncaoAdministrativa = view.findViewById(R.id.edtFuncaoAdministrativa);
        autoCompleteTextViewSituacaoFuncional = view.findViewById(R.id.edtSitucaoFuncional);
        autoCompleteTextViewEspecialidade = view.findViewById(R.id.edtEspecialidade);
        textInputEditTextRegistroConselho = view.findViewById(R.id.edtRegistroConselho);
        btnCadastrar = view.findViewById(R.id.btnRegistrar);
    }

    private void configurarMascaraParaDataDeInclusao()
    {Mascaras.criarMascaraParaData(textInputEditTextDataInclusao);}

    private void definirVisibilidadeDoCampoQuadroComBaseNoPostoGradCat()
    {
        autoCompleteTextViewPostGradCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textoPostoGradCat = (String) ((TextView) view).getText();

                if (!textoPostoGradCat.equals(PostoGradCatEnum.VC.getNome())) {
                    textInputLayoutQuadro.setVisibility(View.VISIBLE);
                    autoCompleteTextViewQuadro.setVisibility(View.VISIBLE);
                    autoCompleteTextViewQuadro.setText("");

                    textInputLayoutRgMilitar.setVisibility(View.VISIBLE);
                    textInputEditTextRgMilitar.setVisibility(View.VISIBLE);
                    textInputEditTextRgMilitar.setText("");

                    textInputLayoutEspecialidade.setVisibility(View.VISIBLE);
                    autoCompleteTextViewEspecialidade.setVisibility(View.VISIBLE);
                    autoCompleteTextViewEspecialidade.setText("");

                    textInputLayoutRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setText("");

                    autoCompleteTextViewSituacaoFuncional.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    autoCompleteTextViewSituacaoFuncional.setNextFocusDownId(R.id.edtEspecialidade);
                } else {
                    textInputLayoutQuadro.setVisibility(View.GONE);
                    autoCompleteTextViewQuadro.setVisibility(View.GONE);
                    autoCompleteTextViewQuadro.setText(QuadroEnum.NAO_SE_APLICA.getNome());

                    textInputLayoutRgMilitar.setVisibility(View.GONE);
                    textInputEditTextRgMilitar.setVisibility(View.GONE);
                    textInputEditTextRgMilitar.setText("Não se aplica");

                    textInputLayoutEspecialidade.setVisibility(View.GONE);
                    autoCompleteTextViewEspecialidade.setVisibility(View.GONE);
                    autoCompleteTextViewEspecialidade.setText(EspecialidadeEnum.NAO_SE_APLICA.getNome());

                    textInputLayoutRegistroConselho.setVisibility(View.GONE);
                    textInputEditTextRegistroConselho.setVisibility(View.GONE);
                    textInputEditTextRegistroConselho.setText("Não se aplica");

                    autoCompleteTextViewSituacaoFuncional.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }
            }
        });
    }

    private void definirVisibilidadeDoCampoEspecialidadeComBaseNoQuadro()
    {
        autoCompleteTextViewQuadro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textoQuadro = (String) ((TextView) view).getText();

                if (textoQuadro.equals(QuadroEnum.QCOPM.getNome())) {
                    textInputLayoutEspecialidade.setVisibility(View.VISIBLE);
                    autoCompleteTextViewEspecialidade.setVisibility(View.VISIBLE);
                    autoCompleteTextViewEspecialidade.setText("");

                    textInputLayoutRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setText("");

                    autoCompleteTextViewSituacaoFuncional.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                } else {
                    textInputLayoutEspecialidade.setVisibility(View.GONE);
                    autoCompleteTextViewEspecialidade.setVisibility(View.GONE);
                    autoCompleteTextViewEspecialidade.setText(EspecialidadeEnum.NAO_SE_APLICA.getNome());

                    textInputLayoutRegistroConselho.setVisibility(View.GONE);
                    textInputEditTextRegistroConselho.setVisibility(View.GONE);
                    textInputEditTextRegistroConselho.setText("Não se aplica");

                    autoCompleteTextViewSituacaoFuncional.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }
            }
        });
    }

    private void definirVisibilidadeDoCampoRegistroConselhoComBaseNaEspecialidade()
    {
        autoCompleteTextViewEspecialidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textoEspecialidade = (String) ((TextView) view).getText();

                if (textoEspecialidade.equals(EspecialidadeEnum.PSICOLOGO.getNome()) ||
                        textoEspecialidade.equals(EspecialidadeEnum.ASSISTENTE_SOCIAL.getNome())) {
                    textInputLayoutRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setVisibility(View.VISIBLE);
                    textInputEditTextRegistroConselho.setText("");
                } else {
                    textInputEditTextRegistroConselho.setVisibility(View.GONE);
                    textInputLayoutRegistroConselho.setVisibility(View.GONE);
                    textInputEditTextRegistroConselho.setText("Não se aplica");

                }
            }
        });
    }

    private void popularCampoPostoGradCatComDB() {

        PostoGradCatController postoGradCatController = new PostoGradCatController();
        postoGradCatController.listar(getActivity(), new IVolleyCallback() {
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

    private void configurarCampoPostoGradCat(List<PostoGradCat> listaPostoGradCatRecuperados) {

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
        quadroController.listar(getActivity(), new IVolleyCallback() {
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

    private void configurarCampoQuadro(List<Quadro> listaQuadrosRecuperados) {

        ArrayAdapter<Quadro> adapterQuadro = new ArrayAdapter<Quadro>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaQuadrosRecuperados));
        autoCompleteTextViewQuadro.setAdapter(adapterQuadro);
        autoCompleteTextViewQuadro.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewQuadro);

    }

    private void popularCampoUnidadeComDB() {

        UnidadeController unidadeController = new UnidadeController();
        unidadeController.listar(getActivity(), new IVolleyCallback() {
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

    public void configurarCampoUnidade(List<Unidade> listaUnidadesRecuperadas) {

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
        funcaoAdministrativaController.listar(getActivity(), new IVolleyCallback() {
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

    private void configurarCampoFuncaoAdministrativa(List<FuncaoAdministrativa> listaFuncoesAdministrativasRecuperadas) {

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
        situacaoFuncionalController.listar(getActivity(), new IVolleyCallback() {
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

    private void configurarCampoSituacaoFuncional(List<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas) {

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
        especialidadeController.listar(getActivity(), new IVolleyCallback() {
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

    private void configurarCampoEspecialidade(List<Especialidade> listaEspecialidadesRecuperadas) {

        ArrayAdapter<Especialidade> adapterEspecialidade = new ArrayAdapter<Especialidade>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEspecialidadesRecuperadas));
        autoCompleteTextViewEspecialidade.setAdapter(adapterEspecialidade);
        autoCompleteTextViewEspecialidade.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEspecialidade);

    }

    private Bundle recuperarDadosDoFuncionarioDoRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();

        return valoresRecebidosFragment1e2;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroDoFuncionario() throws ParseException {
        boolean isQCOPM = false;
        if(autoCompleteTextViewQuadro.getText().toString().equals("QCOPM"))  isQCOPM = true;
        Log.e("quadro", autoCompleteTextViewQuadro.getText().toString());
        if (
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewPostGradCat,
                        listaPostoGradCatRecuperados, "O campo POSTO/GRAD/CAT é obrigatório.",
                        "Insira uma opção de POSTO/GRAD/CAT válida.") &&
                        FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewQuadro, listaQuadrosRecuperados,
                                "O campo QUADRO é obrigatório.", "Insira uma opção de QUADRO válida.") &&
                        FieldValidator.isFieldEmptyOrNull(textInputEditTextRgMilitar, "RG MILITAR") &&
                        FieldValidator.isFieldEmptyOrNull(textInputEditTextNomeGuerra, "NOME DE GUERRA") &&
                        FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewUnidade, listaUnidadesRecuperadas,
                                "O campo UNIDADE é obrigatório.", "Insira uma opção de UNIDADE válida.") &&
                        FieldValidator.validarData(textInputEditTextDataInclusao, "DATA DE INCLUSÃO") &&
                        FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewFuncaoAdministrativa,
                                listaFuncoesAdministrativasRecuperadas, "O campo FUNCAO ADMINISTRATIVA é obrigatório.",
                                "Insira uma opção de FUNÇÃO ADMINISTRATIVA válida.") &&
                        FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewSituacaoFuncional,
                                listaSituacoesFuncionaisRecuperadas, "o campo SITUAÇÃO FUNCIONAL é obrigatório",
                                "Insira uma SITUAÇÃO FUNCIONAL válida.") &&
                        FieldValidator.validarAutoCompleteTextViewFuncionario(isQCOPM, autoCompleteTextViewEspecialidade,
                                listaEspecialidadesRecuperadas, "O campo ESPECIALIDADE é obrigatório.",
                                "Insira uma opção de ESPECIALIDADE válida.") &&
                        FieldValidator.isFieldEmptyOrNullFuncionario(isQCOPM, textInputEditTextRegistroConselho, "REGISTRO DO CONSELHO"))
        {
            receberDadosDoFuncionarioPreenchidos();
            return true;
        }else { return false; }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void receberDadosDoFuncionarioPreenchidos() throws ParseException {

        for(int i = 0; i < listaPostoGradCatRecuperados.size(); i++) {
            PostoGradCat postoGradCatSelecionado = listaPostoGradCatRecuperados.get(i);
            if(postoGradCatSelecionado.getNome().equals(autoCompleteTextViewPostGradCat.getText().toString())) {
                postoGradCat = postoGradCatSelecionado;
            }
        }
        for(int i = 0; i < listaQuadrosRecuperados.size(); i++) {
            Quadro quadroSelecionado = listaQuadrosRecuperados.get(i);
            if(quadroSelecionado.getNome().equals(autoCompleteTextViewQuadro.getText().toString())) {
                quadro = quadroSelecionado;
            }
        }

        rgMilitar = textInputEditTextRgMilitar.getText().toString().trim();
        nomeGuerra = textInputEditTextNomeGuerra.getText().toString().trim();

        for(int i = 0; i < listaUnidadesRecuperadas.size(); i++) {
            Unidade unidadeSelecionada = listaUnidadesRecuperadas.get(i);
            if(unidadeSelecionada.getNome().equals(autoCompleteTextViewUnidade.getText().toString())) {
                unidade = unidadeSelecionada;
            }
        }

        dataInclusao = DateFormater.StringToLocalDate(textInputEditTextDataInclusao.getText().toString().trim());

        for(int i = 0; i < listaFuncoesAdministrativasRecuperadas.size(); i++) {
            FuncaoAdministrativa funcaoAdministrativaSelecionada = listaFuncoesAdministrativasRecuperadas.get(i);
            if(funcaoAdministrativaSelecionada.getNome().equals(autoCompleteTextViewFuncaoAdministrativa.getText().toString())) {
                funcaoAdministrativa = funcaoAdministrativaSelecionada;
            }
        }

        for(int i = 0; i < listaSituacoesFuncionaisRecuperadas.size(); i++) {
            SituacaoFuncional situacaoFuncionalSelecionada = listaSituacoesFuncionaisRecuperadas.get(i);
            if(situacaoFuncionalSelecionada.getNome().equals(autoCompleteTextViewSituacaoFuncional.getText().toString())) {
                situacaoFuncional = situacaoFuncionalSelecionada;
            }
        }

        for(int i = 0; i < listaEspecialidadesRecuperadas.size(); i++) {
            Especialidade especialidadeSelecionada = listaEspecialidadesRecuperadas.get(i);
            if(especialidadeSelecionada.getNome().equals(autoCompleteTextViewEspecialidade.getText().toString())) {
                especialidade = especialidadeSelecionada;
            }
        }

        registroConselho = textInputEditTextRegistroConselho.getText().toString().trim();
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
        funcionario.setNaturalidade( (Cidade)valoresRecebidosFragment1.getSerializable("naturalidade"));
        funcionario.setEstadoCivil( (EstadoCivil) valoresRecebidosFragment1.getSerializable("estadoCivil"));
        funcionario.setNumeroFilhos(valoresRecebidosFragment1.getInt("numeroFilhos"));
        funcionario.setEscolaridade( (Escolaridade) valoresRecebidosFragment1.getSerializable("escolaridade"));
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

        return funcionario;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Especialista encapsularValoresParaCadastroDeEspecialista() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosDoFuncionarioDoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        LocalDate dataNascimento = DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("dataNascimento"));

        especialista = new Especialista();

        especialista.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        especialista.setDataNascimento(dataNascimento);
        especialista.setCpf(Mascaras.removerMascaras(valoresRecebidosFragment1.getString("cpf")));
        especialista.setSexo((SexoEnum) valoresRecebidosFragment1.getSerializable("sexo"));
        especialista.setNaturalidade( (Cidade)valoresRecebidosFragment1.getSerializable("naturalidade"));
        especialista.setEstadoCivil( (EstadoCivil) valoresRecebidosFragment1.getSerializable("estadoCivil"));
        especialista.setNumeroFilhos(valoresRecebidosFragment1.getInt("numeroFilhos"));
        especialista.setEscolaridade( (Escolaridade) valoresRecebidosFragment1.getSerializable("escolaridade"));
        especialista.setTelefones((ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefones"));
        especialista.setEmail(valoresRecebidosFragment1.getString("email"));
        especialista.setEndereco((Endereco) valoresRecebidosFragment1e2.getSerializable("endereco"));
        especialista.setRgMilitar(rgMilitar);
        especialista.setPostoGradCat((postoGradCat));
        especialista.setNomeGuerra(nomeGuerra);
        especialista.setUnidade((unidade));
        especialista.setDataInclusao((dataInclusao));
        especialista.setQuadro((quadro));
        especialista.setFuncaoAdministrativa((funcaoAdministrativa));
        especialista.setSituacaoFuncional((situacaoFuncional));
        especialista.setEspecialidade(especialidade);
        especialista.setRegistroConselho(registroConselho);
        especialista.setSenha(Mascaras.removerMascaras(valoresRecebidosFragment1.getString("cpf")));

        if(quadro.toString().equals(QuadroEnum.QCOPM.getNome()))
        {
            especialista.setEspecialidade((especialidade));

            if(especialidade.toString().equals(EspecialidadeEnum.PSICOLOGO.getNome())) {
                especialista.setRegiaoConselho("10"); }
            else if(especialidade.toString().equals(EspecialidadeEnum.ASSISTENTE_SOCIAL.getNome())) {
                especialista.setRegiaoConselho("1"); }
            else {
                especialista.setRegistroConselho("Não se aplica");
            }
        }

        return especialista;
    }


    public void cadastrarFuncionario(Funcionario novoFuncionario)
    {
        new FuncionarioController().cadastrar(
                getActivity(),
                novoFuncionario,
                new IVolleyCallback() {
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

    private void cadastrarEspecialista(Especialista novoEspecialista)
    {
        new EspecialistaController().cadastrar(
                getActivity(),
                novoEspecialista,
                new IVolleyCallback() {
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

    public void enviarFormulario() {

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if(validarCadastroDoFuncionario()) {
                    if(quadro.toString().equals(QuadroEnum.QCOPM.getNome()))
                    {
                        Especialista novoEspecialista;
                        novoEspecialista = encapsularValoresParaCadastroDeEspecialista();
                        cadastrarEspecialista(novoEspecialista);
                        Toast.makeText(getContext(), "especialista", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Funcionario novoFuncionario;
                        novoFuncionario = encapsularValoresParaCadastroDeFuncionario();
                        cadastrarFuncionario(novoFuncionario);
                        Toast.makeText(getContext(), "funcionário", Toast.LENGTH_SHORT).show();
                    }

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