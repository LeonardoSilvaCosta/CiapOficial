package com.br.ciapoficial.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.PostoGradCat;
import com.br.ciapoficial.model.Quadro;
import com.br.ciapoficial.model.SituacaoFuncional;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Unidade;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.network.PostoGradCatController;
import com.br.ciapoficial.network.QuadroController;
import com.br.ciapoficial.network.SituacaoFuncionalController;
import com.br.ciapoficial.network.UnidadeController;
import com.br.ciapoficial.network.UsuarioController;
import com.br.ciapoficial.validation.FieldValidator;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class UsuarioRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;
    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra, textInputEditTextDataInclusao;
    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewQuadro, autoCompleteTextViewUnidade,
            autoCompleteTextViewSituacaoFuncional;
    private Button btnCadastrar;

    private Usuario usuario;

    private List<PostoGradCat> listaPostoGradCatRecuperados = new ArrayList<>();
    private List<Quadro> listaQuadrosRecuperados = new ArrayList<>();
    private List<Unidade> listaUnidadesRecuperadas = new ArrayList<>();
    private List<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();

    private PostoGradCat postoGradCat = new PostoGradCat();
    private Quadro quadro = new Quadro();
    private String rgMilitar;
    private String nomeGuerra;
    private Unidade unidade = new Unidade();
    private LocalDate dataInclusao;
    private SituacaoFuncional situacaoFuncional = new SituacaoFuncional();

    public UsuarioRegisterFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario_register3, container, false) ;


        configurarComponentes(view);
        configurarMascaraDataDeInclusao();
        popularCampoPostoGradCatComDB();
        popularCampoUnidadeComDB();
        popularCampoQuadroComDB();
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
        autoCompleteTextViewSituacaoFuncional = view.findViewById(R.id.edtSitucaoFuncional);
        btnCadastrar = view.findViewById(R.id.btnRegistrar);
    }

    private void configurarMascaraDataDeInclusao()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaData(textInputEditTextDataInclusao);
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

    private Bundle recuperarDadosDoUsuarioDoRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();

        return valoresRecebidosFragment1e2;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroDoUsuario() throws ParseException {
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
                        FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewSituacaoFuncional,
                                listaSituacoesFuncionaisRecuperadas, "O campo SITUAÇÃO FUNCIONAL é obrigatório.",
                                "Insira uma opção de SITUAÇÃO FUNCIONAL válida."))
        {
            receberDadosDoUsuarioPreenchidos();
            return true;
        }else { return false; }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void receberDadosDoUsuarioPreenchidos() throws ParseException {

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

        for(int i = 0; i < listaSituacoesFuncionaisRecuperadas.size(); i++) {
            SituacaoFuncional situacaoFuncionalSelecionada = listaSituacoesFuncionaisRecuperadas.get(i);
            if(situacaoFuncionalSelecionada.getNome().equals(autoCompleteTextViewSituacaoFuncional.getText().toString())) {
                situacaoFuncional = situacaoFuncionalSelecionada;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Usuario encapsularValoresParaCadastro() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosDoUsuarioDoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        LocalDate dataNascimento = DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("dataNascimento"));

        usuario = new Usuario();

        usuario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf(valoresRecebidosFragment1.getString("cpf"));
        usuario.setSexo((SexoEnum) valoresRecebidosFragment1.getSerializable("sexo"));
        usuario.setNaturalidade( (Cidade)valoresRecebidosFragment1.getSerializable("naturalidade"));
        usuario.setEstadoCivil( (EstadoCivil) valoresRecebidosFragment1.getSerializable("estadoCivil"));
        usuario.setNumeroFilhos(valoresRecebidosFragment1.getInt("numeroFilhos"));
        usuario.setEscolaridade( (Escolaridade) valoresRecebidosFragment1.getSerializable("escolaridade"));
        usuario.setTelefones((ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefones"));
        usuario.setEmail(valoresRecebidosFragment1.getString("email"));
        usuario.setAtendido(valoresRecebidosFragment1.getBoolean("eAtendido"));
        usuario.setEndereco((Endereco) valoresRecebidosFragment1e2.getSerializable("endereco"));
        usuario.setRgMilitar(rgMilitar);
        usuario.setPostoGradCat((postoGradCat));
        usuario.setNomeGuerra(nomeGuerra);
        usuario.setUnidade((unidade));
        usuario.setDataInclusao((dataInclusao));
        usuario.setQuadro((quadro));
        usuario.setSituacaoFuncional((situacaoFuncional));

        return usuario;
    }

    private void cadastrarUsuario(Usuario novoUsuario)
    {
        new UsuarioController().cadastrar(
                getActivity(),
                novoUsuario,
                new IVolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        Log.e("CadastroUsuario", response);

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

                if(validarCadastroDoUsuario())
                {
                    Usuario novoUsuario;
                    novoUsuario = encapsularValoresParaCadastro();

                    cadastrarUsuario(novoUsuario);

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