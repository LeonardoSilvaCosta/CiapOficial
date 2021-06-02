package com.br.ciapoficial.view.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.controller.PostoGradCatController;
import com.br.ciapoficial.controller.QuadroController;
import com.br.ciapoficial.controller.SituacaoFuncionalController;
import com.br.ciapoficial.controller.UnidadeController;
import com.br.ciapoficial.enums.EscolaridadeEnum;
import com.br.ciapoficial.enums.EstadoCivilEnum;
import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.SituacaoFuncionalEnum;
import com.br.ciapoficial.enums.UnidadeEnum;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class UsuarioRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;
    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra, textInputEditTextDataInclusao;
    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewUnidade, autoCompleteTextViewQuadro,
            autoCompleteTextViewSituacaoFuncional;
    Button btnCadastrar;

    Usuario usuario;

    private ArrayList<PostoGradCatEnum> listaPostoGradCatRecuperados = new ArrayList<>();
    private ArrayList<UnidadeEnum> listaUnidadesRecuperadas = new ArrayList<>();
    private ArrayList<QuadroEnum> listaQuadrosRecuperados = new ArrayList<>();
    private ArrayList<SituacaoFuncionalEnum> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();

    private String rgMilitar;
    private PostoGradCatEnum postoGradCatEnum;
    private String nomeGuerra;
    private UnidadeEnum unidadeEnum;
    private QuadroEnum quadroEnum;
    private LocalDate dataInclusao;
    private SituacaoFuncionalEnum situacaoFuncionalEnum;

    public UsuarioRegisterFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendido_register3, container, false) ;


        configurarComponentes(view);
        enviarFormulario();
        return view;
    }

    private void configurarComponentes(View view)
    {
        textInputEditTextRgMilitar = view.findViewById(R.id.edtRgMilitar);
        autoCompleteTextViewPostGradCat = view.findViewById(R.id.edtPostoGradCat);
        textInputEditTextNomeGuerra = view.findViewById(R.id.edtNomeGuerra);
        autoCompleteTextViewUnidade = view.findViewById(R.id.edtUnidade);
        autoCompleteTextViewQuadro = view.findViewById(R.id.edtQuadro);
        textInputEditTextDataInclusao = view.findViewById(R.id.edtDataInclusao);
        autoCompleteTextViewSituacaoFuncional = view.findViewById(R.id.edtSitucaoFuncional);
        btnCadastrar = view.findViewById(R.id.btnRegistrar);

        popularCampoPostoGradCatComDB();
        popularCampoUnidadeComDB();
        popularCampoQuadroComDB();
        popularCampoSituacaoFuncionalComDB();
        configurarMascaraData();
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

    private void configurarMascaraData()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaData(textInputEditTextDataInclusao);
    }

    private Bundle recuperarDadosAtendidoRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();

        return valoresRecebidosFragment1e2;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void receberDadosUsuarioPreenchidos() throws ParseException {
        rgMilitar = textInputEditTextRgMilitar.getText().toString();

        for(int i = 0; i < listaPostoGradCatRecuperados.size(); i++) {
            PostoGradCatEnum postoGradCatSelecionado = listaPostoGradCatRecuperados.get(i);
            if(postoGradCatSelecionado.getNome().equals(autoCompleteTextViewPostGradCat.getText().toString())) {
                postoGradCatEnum = postoGradCatSelecionado;
            }
        }
        nomeGuerra = textInputEditTextNomeGuerra.getText().toString();

        for(int i = 0; i < listaUnidadesRecuperadas.size(); i++) {
            UnidadeEnum unidadeSelecionada = listaUnidadesRecuperadas.get(i);
            if(unidadeSelecionada.getNome().equals(autoCompleteTextViewUnidade.getText().toString())) {
                unidadeEnum = unidadeSelecionada;
            }
        }
        for(int i = 0; i < listaQuadrosRecuperados.size(); i++) {
            QuadroEnum quadroSelecionado = listaQuadrosRecuperados.get(i);
            if(quadroSelecionado.getNome().equals(autoCompleteTextViewQuadro.getText().toString())) {
                quadroEnum = quadroSelecionado;
            }
        }

        dataInclusao = DateFormater.StringToLocalDate(textInputEditTextDataInclusao.getText().toString());

        for(int i = 0; i < listaSituacoesFuncionaisRecuperadas.size(); i++) {
            SituacaoFuncionalEnum situacaoFuncionalSelecionada = listaSituacoesFuncionaisRecuperadas.get(i);
            if(situacaoFuncionalSelecionada.getNome().equals(autoCompleteTextViewSituacaoFuncional.getText().toString())) {
                situacaoFuncionalEnum = situacaoFuncionalSelecionada;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroAtendido() throws ParseException {
        receberDadosUsuarioPreenchidos();

        if (!TextUtils.isEmpty(rgMilitar)) {

            if (!TextUtils.isEmpty(postoGradCatEnum.getNome())) {

                if (!TextUtils.isEmpty(nomeGuerra)) {

                    if (!TextUtils.isEmpty(unidadeEnum.getNome())) {

                        if (!TextUtils.isEmpty(quadroEnum.getNome())) {

                            if (dataInclusao != null) {

                                if (!TextUtils.isEmpty(situacaoFuncionalEnum.getNome())) {

                                    return true;

                                }
                                else {
                                    autoCompleteTextViewSituacaoFuncional.setError("O campo SITUAÇÃO FUNCIONAL é obrigatório!");
                                    autoCompleteTextViewSituacaoFuncional.requestFocus();
                                    return false; }

                            }
                            else {
                                textInputEditTextDataInclusao.setError("O campo DATA DE INCLUSÃO é obrigatório!");
                                textInputEditTextDataInclusao.requestFocus();
                                return false; }

                        }
                        else {
                            autoCompleteTextViewQuadro.setError("O campo QUADRO é obrigatório!");
                            autoCompleteTextViewQuadro.requestFocus();
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
    private Usuario encapsularValoresParaCadastro() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosAtendidoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        LocalDate dataNascimento = DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("dataNascimento"));
        LocalDate dataInclusao = DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("dataInclusao"));

        usuario = new Usuario();

        usuario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf((valoresRecebidosFragment1.getString("cpf")));
        usuario.setSexo((SexoEnum) valoresRecebidosFragment1.getSerializable("sexo"));
        usuario.setTelefones((ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefones"));
        usuario.setEmail(valoresRecebidosFragment1.getString("email"));
        usuario.setEstadoCivil((EstadoCivilEnum) valoresRecebidosFragment1.getSerializable("estadoCivil"));
        usuario.setNaturalidade((Cidade) valoresRecebidosFragment1.getSerializable("cidadeNatal"));
        usuario.setEscolaridade((EscolaridadeEnum) valoresRecebidosFragment1.getSerializable("escolaridade"));
        usuario.setNumeroFilhos(valoresRecebidosFragment1.getInt("numeroFilhos"));
        usuario.setEndereco((Endereco) valoresRecebidosFragment1e2.getSerializable("endereco"));
        usuario.setRgMilitar(rgMilitar);
//        usuario.setPostoGradCat(postoGradCatEnum);
//        usuario.setNomeGuerra(nomeGuerra);
//        usuario.setUnidade(unidadeEnum);
//        usuario.setQuadro(quadroEnum);
//        usuario.setDataInclusao(dataInclusao);
//        usuario.setSituacaoFuncional(situacaoFuncionalEnum);

        return usuario;
    }

    private void cadastrarAtendido(Usuario novoUsuario)
    {
        new UsuarioController().cadastrar(
                getActivity(),
                novoUsuario,
                new VolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        Log.e("CadastroAtendido", response);

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if(validarCadastroAtendido())
                {
                    Usuario novoUsuario;
                    novoUsuario = encapsularValoresParaCadastro();

                    cadastrarAtendido(novoUsuario);

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