package com.br.ciapoficial.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EspecialidadeController;
import com.br.ciapoficial.controller.FuncaoAdministrativaController;
import com.br.ciapoficial.controller.PostoGradCatController;
import com.br.ciapoficial.controller.QuadroController;
import com.br.ciapoficial.controller.SituacaoFuncionalController;
import com.br.ciapoficial.controller.UnidadeController;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Especialidade;
import com.br.ciapoficial.model.FuncaoAdministrativa;
import com.br.ciapoficial.model.PostoGradCat;
import com.br.ciapoficial.model.Quadro;
import com.br.ciapoficial.model.SituacaoFuncional;
import com.br.ciapoficial.model.Unidade;
import com.br.ciapoficial.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.br.ciapoficial.view.LoginActivity.fileName;

public class UserUpdateFragment3 extends Fragment {

    private PrincipalFragment principalFragment;
    private TextInputEditText textInputEditTextRgMilitar, textInputEditTextNomeGuerra,
            textInputEditTextRegistroConselho;
    private AutoCompleteTextView autoCompleteTextViewPostGradCat, autoCompleteTextViewUnidade, autoCompleteTextViewQuadro,
            autoCompleteTextViewEspecialidade, autoCompleteTextViewFuncaoAdministrativa, autoCompleteTextViewSituacaoFuncional;
    Button btnCadastrar;

    Usuario usuario;

    private ArrayList<PostoGradCat> listaPostoGradCatRecuperados = new ArrayList<>();
    private ArrayList<Unidade> listaUnidadesRecuperadas = new ArrayList<>();
    private ArrayList<Quadro> listaQuadrosRecuperados = new ArrayList<>();
    private ArrayList<Especialidade> listaEspecialidadesRecuperadas = new ArrayList<>();
    private ArrayList<FuncaoAdministrativa> listaFuncoesAdministrativasRecuperadas = new ArrayList<>();
    private ArrayList<SituacaoFuncional> listaSituacoesFuncionaisRecuperadas = new ArrayList<>();

    String rgMilitar;
    String postoGradCat;
    String nomeGuerra;
    String unidade;
    String quadro;
    String especialidade;
    String registroConselho;
    String funcaoAdministrativa;
    String situacaoFuncional;

    SharedPreferences sharedPreferences;

    public UserUpdateFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_update3, container, false) ;

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);


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
        autoCompleteTextViewEspecialidade = view.findViewById(R.id.edtEspecialidade);
        textInputEditTextRegistroConselho = view.findViewById(R.id.edtRegistroConselho);
        autoCompleteTextViewFuncaoAdministrativa = view.findViewById(R.id.edtFuncaoAdministrativa);
        autoCompleteTextViewSituacaoFuncional = view.findViewById(R.id.edtSitucaoFuncional);
        btnCadastrar = view.findViewById(R.id.btnRegistrar);

        popularCampoPostoGradCatComDB();
        popularCampoUnidadeComDB();
        popularCampoQuadroComDB();
        popularCampoEspecialidadeComDB();
        popularCampoFuncaoAdministrativaComDB();
        popularCampoSituacaoFuncionalComDB();

        receberDadosUsuarioPreviamentePreenchidos();
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

    private void popularCampoEspecialidadeComDB()
    {
        EspecialidadeController especialidadeController = new EspecialidadeController();
        especialidadeController.listarEspecialidades(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Especialidade especialidade = new Especialidade();
                            especialidade.setId(Integer.valueOf(object.getString("id")));
                            especialidade.setDescricao(object.getString("descricao"));

                            listaEspecialidadesRecuperadas.add(especialidade);
                            configurarCampoEspecialidade(listaEspecialidadesRecuperadas);

                        }
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

    private void popularCampoFuncaoAdministrativaComDB()
    {
        FuncaoAdministrativaController funcaoAdministrativaController = new FuncaoAdministrativaController();
        funcaoAdministrativaController.listarFuncoesAdministrativas(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            FuncaoAdministrativa funcaoAdministrativa = new FuncaoAdministrativa();
                            funcaoAdministrativa.setId(Integer.valueOf(object.getString("id")));
                            funcaoAdministrativa.setDescricao(object.getString("descricao"));

                            listaFuncoesAdministrativasRecuperadas.add(funcaoAdministrativa);
                            configurarCampoFuncaoAdministrativa(listaFuncoesAdministrativasRecuperadas);

                        }
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

    private Bundle recuperarDadosUserRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();

        return valoresRecebidosFragment1e2;

    }

    private void receberDadosUsuarioPreviamentePreenchidos() {

        String emailUsuarioLogado = sharedPreferences.getString("userEmail", "userEmail");

        UsuarioController usuarioController = new UsuarioController();
        usuarioController.recuperarUsuarioLogado(getActivity(), emailUsuarioLogado, new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            String email = object.getString("email");

                            if(email.equals(emailUsuarioLogado)) {

                                Usuario usuario = new Usuario();
                                usuario.setRgMilitar(object.getString("rgMilitar"));
                                usuario.setPostoGradCat(object.getString("postoGradCat"));
                                usuario.setNomeGuerra(object.getString("nomeGuerra"));
                                usuario.setUnidade(object.getString("unidade"));
                                usuario.setQuadro(object.getString("quadro"));
                                usuario.setEspecialidade(object.getString("especialidade"));
                                usuario.setRegistroConselho(object.getString("registroConselho"));
                                usuario.setFuncaoAdministrativa(object.getString("funcaoAdministrativa"));
                                usuario.setSituacaoFuncional(object.getString("situacaoFuncional"));

                                textInputEditTextRgMilitar.setText(usuario.getRgMilitar());
                                autoCompleteTextViewPostGradCat.setText(usuario.getPostoGradCat());
                                textInputEditTextNomeGuerra.setText(usuario.getNomeGuerra());
                                autoCompleteTextViewUnidade.setText(usuario.getUnidade());
                                autoCompleteTextViewQuadro.setText(usuario.getQuadro());
                                autoCompleteTextViewEspecialidade.setText(usuario.getEspecialidade());
                                textInputEditTextRegistroConselho.setText(usuario.getRegistroConselho());
                                autoCompleteTextViewFuncaoAdministrativa.setText(usuario.getFuncaoAdministrativa());
                                autoCompleteTextViewSituacaoFuncional.setText(usuario.getSituacaoFuncional());


                            }


                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void receberDadosUsuarioPreenchidos()
    {
        rgMilitar = textInputEditTextRgMilitar.getText().toString();
        postoGradCat = autoCompleteTextViewPostGradCat.getText().toString();
        nomeGuerra = textInputEditTextNomeGuerra.getText().toString();
        unidade = autoCompleteTextViewUnidade.getText().toString();
        quadro = autoCompleteTextViewQuadro.getText().toString();
        especialidade = autoCompleteTextViewEspecialidade.getText().toString();
        registroConselho = textInputEditTextRegistroConselho.getText().toString();
        funcaoAdministrativa = autoCompleteTextViewFuncaoAdministrativa.getText().toString();
        situacaoFuncional = autoCompleteTextViewSituacaoFuncional.getText().toString();
    }

    private boolean validarCadastroUsuario() {
        receberDadosUsuarioPreenchidos();

        if (!TextUtils.isEmpty(rgMilitar)) {

            if (!TextUtils.isEmpty(postoGradCat)) {

                if (!TextUtils.isEmpty(nomeGuerra)) {

                    if (!TextUtils.isEmpty(unidade)) {

                        if (!TextUtils.isEmpty(quadro)) {

                            if (!TextUtils.isEmpty(especialidade)) {

                                if (!TextUtils.isEmpty(registroConselho)) {

                                    if (!TextUtils.isEmpty(funcaoAdministrativa)) {

                                        return true;

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

    private Usuario encapsularValoresParaCadastro()
    {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosUserRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        usuario = new Usuario();

        usuario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        usuario.setDataNascimento(DataEntreJavaEMysql.enviarDataParaMySqlComoString(valoresRecebidosFragment1.getString("dataNascimento")));
        usuario.setCpf(Mascaras.removerMascaras(valoresRecebidosFragment1.getString("cpf")));
        usuario.setSexo(valoresRecebidosFragment1.getString("sexo"));
        usuario.setTelefones((valoresRecebidosFragment1.getStringArrayList("telefones")));
        usuario.setEmail(valoresRecebidosFragment1.getString("email"));
        usuario.setCep(Mascaras.removerMascaras(valoresRecebidosFragment1e2.getString("cep")));
        usuario.setUf(valoresRecebidosFragment1e2.getString("uf"));
        usuario.setCidade(valoresRecebidosFragment1e2.getString("cidade"));
        usuario.setBairro(valoresRecebidosFragment1e2.getString("bairro"));
        usuario.setLogradouro(valoresRecebidosFragment1e2.getString("logradouro"));
        usuario.setNumero(valoresRecebidosFragment1e2.getString("numero"));
        usuario.setRgMilitar(rgMilitar);
        usuario.setPostoGradCat(postoGradCat);
        usuario.setNomeGuerra(nomeGuerra);
        usuario.setUnidade(unidade);
        usuario.setQuadro(quadro);
        usuario.setEspecialidade(especialidade);
        usuario.setRegistroConselho(registroConselho);
        usuario.setFuncaoAdministrativa(funcaoAdministrativa);
        usuario.setSenha(valoresRecebidosFragment1.getString("senha"));

        return usuario;
    }

    private void atualizarUsuario(Usuario novoUsuario)
    {
        String id = sharedPreferences.getString("userId", "");
        novoUsuario.setId(Integer.valueOf(id));
        new UsuarioController().atualizarUsuario(
                getActivity(),
                novoUsuario,
                new VolleyCallback() {
                    @Override
                    public void onSucess(String response) {

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
                                        mensagem,
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

                if(validarCadastroUsuario())
                {
                    Usuario novoUsuario;
                    novoUsuario = encapsularValoresParaCadastro();

                    atualizarUsuario(novoUsuario);

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