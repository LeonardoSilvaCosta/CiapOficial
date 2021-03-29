package com.br.ciapoficial.view.fragment;

import android.os.Bundle;

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
import com.br.ciapoficial.controller.AtendidoController;
import com.br.ciapoficial.controller.EspecialidadeController;
import com.br.ciapoficial.controller.PostoGradCatController;
import com.br.ciapoficial.controller.QuadroController;
import com.br.ciapoficial.controller.SituacaoFuncionalController;
import com.br.ciapoficial.controller.UnidadeController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;
import com.br.ciapoficial.model.Especialidade;
import com.br.ciapoficial.model.FuncaoAdministrativa;
import com.br.ciapoficial.model.PostoGradCat;
import com.br.ciapoficial.model.Quadro;
import com.br.ciapoficial.model.SituacaoFuncional;
import com.br.ciapoficial.model.Unidade;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AtendidoRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;
    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra, textInputEditTextDataInclusao;
    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewUnidade, autoCompleteTextViewQuadro,
            autoCompleteTextViewSituacaoFuncional;
    Button btnCadastrar;

    Atendido atendido;

    private ArrayList<PostoGradCat> listaPostoGradCatRecuperados = new ArrayList<>();
    private ArrayList<Unidade> listaUnidadesRecuperadas = new ArrayList<>();
    private ArrayList<Quadro> listaQuadrosRecuperados = new ArrayList<>();
    private ArrayList<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();

    String rgMilitar;
    String postoGradCat;
    String nomeGuerra;
    String unidade;
    String quadro;
    String dataInclusao;
    String situacaoFuncional;

    public AtendidoRegisterFragment3() {
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
        autoCompleteTextViewPostGradCat = view.findViewById(R.id.edtSpinPostoGrad);
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
        postoGradCatController.listarPostoGradCat(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            PostoGradCat postoGradCat = new PostoGradCat();
                            postoGradCat.setId(Integer.valueOf(object.getString("id")));
                            postoGradCat.setDescricao(object.getString("descricao"));

                            listaPostoGradCatRecuperados.add(postoGradCat);
                            configurarCampoPostoGradCat(listaPostoGradCatRecuperados);

                        }
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

    private void popularCampoUnidadeComDB() {

        UnidadeController unidadeController = new UnidadeController();
        unidadeController.listarUnidades(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Unidade unidade = new Unidade();
                            unidade.setId(Integer.valueOf(object.getString("id")));
                            unidade.setDescricao(object.getString("descricao"));

                            listaUnidadesRecuperadas.add(unidade);
                            configurarCampoUnidade(listaUnidadesRecuperadas);

                        }
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

    private void popularCampoQuadroComDB()
    {
        QuadroController quadroController = new QuadroController();
        quadroController.listarQuadros(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Quadro quadro = new Quadro();
                            quadro.setId(Integer.valueOf(object.getString("id")));
                            quadro.setDescricao(object.getString("descricao"));

                            listaQuadrosRecuperados.add(quadro);
                            configurarCampoQuadro(listaQuadrosRecuperados);

                        }
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

    private void popularCampoSituacaoFuncionalComDB()
    {
        SituacaoFuncionalController situacaoFuncionalController = new SituacaoFuncionalController();
        situacaoFuncionalController.listarSituacoesFuncionais(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            SituacaoFuncional situacaoFuncional = new SituacaoFuncional();
                            situacaoFuncional.setId(Integer.valueOf(object.getString("id")));
                            situacaoFuncional.setDescricao(object.getString("descricao"));

                            listaSituacoesFuncionaisRecuperadas.add(situacaoFuncional);
                            configurarCampoSituacaoFuncional(listaSituacoesFuncionaisRecuperadas);

                        }
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

    private void configurarMascaraData()
    {
        Mascaras mascara = new Mascaras();
        mascara.mascaraData(textInputEditTextDataInclusao);
    }

    private Bundle recuperarDadosAtendidoRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();

        return valoresRecebidosFragment1e2;

    }

    private void receberDadosUsuarioPreenchidos()
    {
        rgMilitar = textInputEditTextRgMilitar.getText().toString();
        postoGradCat = autoCompleteTextViewPostGradCat.getText().toString();
        nomeGuerra = textInputEditTextNomeGuerra.getText().toString();
        unidade = autoCompleteTextViewUnidade.getText().toString();
        quadro = autoCompleteTextViewQuadro.getText().toString();
        dataInclusao = textInputEditTextDataInclusao.getText().toString();
        situacaoFuncional = autoCompleteTextViewSituacaoFuncional.getText().toString();
    }

    private boolean validarCadastroAtendido() {
        receberDadosUsuarioPreenchidos();

        if (!TextUtils.isEmpty(rgMilitar)) {

            if (!TextUtils.isEmpty(postoGradCat)) {

                if (!TextUtils.isEmpty(nomeGuerra)) {

                    if (!TextUtils.isEmpty(unidade)) {

                        if (!TextUtils.isEmpty(quadro)) {

                                if (!TextUtils.isEmpty(dataInclusao)) {

                                    if (!TextUtils.isEmpty(situacaoFuncional)) {

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

    private Atendido encapsularValoresParaCadastro()
    {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosAtendidoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        atendido = new Atendido();

        atendido.setTipoAtendido(valoresRecebidosFragment1.getString("tipoAtendido"));
        atendido.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        atendido.setDataNascimento((valoresRecebidosFragment1.getString("dataNascimento")));
        atendido.setCpf((valoresRecebidosFragment1.getString("cpf")));
        atendido.setSexo(valoresRecebidosFragment1.getString("sexo"));
        atendido.setTelefone((valoresRecebidosFragment1.getString("telefone")));
        atendido.setEmail(valoresRecebidosFragment1.getString("email"));
        atendido.setEstadoCivil(valoresRecebidosFragment1.getString("estadoCivil"));
        atendido.setUfNatal(valoresRecebidosFragment1.getString("ufNatal"));
        atendido.setCidadeNatal(valoresRecebidosFragment1.getString("cidadeNatal"));
        atendido.setEscolaridade(valoresRecebidosFragment1.getString("escolaridade"));
        atendido.setNumeroFilhos(valoresRecebidosFragment1.getString("numeroFilhos"));
        atendido.setCep(valoresRecebidosFragment1e2.getString("cep"));
        atendido.setUf(valoresRecebidosFragment1e2.getString("uf"));
        atendido.setCidade(valoresRecebidosFragment1e2.getString("cidade"));
        atendido.setBairro(valoresRecebidosFragment1e2.getString("bairro"));
        atendido.setLogradouro(valoresRecebidosFragment1e2.getString("logradouro"));
        atendido.setNumero(valoresRecebidosFragment1e2.getString("numero"));
        atendido.setRgMilitar(rgMilitar);
        atendido.setPostoGradCat(postoGradCat);
        atendido.setNomeGuerra(nomeGuerra);
        atendido.setUnidade(unidade);
        atendido.setQuadro(quadro);
        atendido.setDataInclusao(DataEntreJavaEMysql.enviarDataParaMySqlComoString(dataInclusao));
        atendido.setSituacaoFuncional(situacaoFuncional);

        return atendido;
    }

    private void cadastrarAtendido(Atendido novoAtendido)
    {
        new AtendidoController().cadastrarAtendido(
                getActivity(),
                novoAtendido,
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
            @Override
            public void onClick(View v) {

                if(validarCadastroAtendido())
                {
                    Atendido novoAtendido;
                    novoAtendido = encapsularValoresParaCadastro();

                    cadastrarAtendido(novoAtendido);

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