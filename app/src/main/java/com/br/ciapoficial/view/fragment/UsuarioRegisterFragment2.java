package com.br.ciapoficial.view.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.controller.UfController;
import com.br.ciapoficial.enums.EscolaridadeEnum;
import com.br.ciapoficial.enums.EstadoCivilEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.TipoAtendido;
import com.br.ciapoficial.enums.TipoVinculoEnum;
import com.br.ciapoficial.enums.UfEnum;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.Cidade;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class UsuarioRegisterFragment2 extends Fragment {

    private PrincipalFragment principalFragment;
    private UsuarioRegisterFragment3 usuarioRegisterFragment3;
    private TextInputLayout textInputLayoutUf, textInputLayoutCidade;
    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
    private TextInputEditText textInputEditTextCep, textInputEditTextBairro, textInputEditTextLogradouro,
            textInputEditTextNumero;
    Button btnProxima;

    private ArrayList<UfEnum> listaEstadosRecuperados = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();

    private Endereco endereco;

    private String tipoAtendido;

    public UsuarioRegisterFragment2() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendido_register2, container, false);

        Bundle getTipoAtendidoFromFragment1 = this.getArguments();
        tipoAtendido = getTipoAtendidoFromFragment1.getString("tipoAtendido");

        configurarComponentes(view);
        abrirProximaTela();
        return view;
    }

    private void configurarComponentes(View view) {
        textInputLayoutUf = view.findViewById(R.id.textInputLayoutUf);
        textInputLayoutCidade = view.findViewById(R.id.textInputLayoutCidadeNatal);
        textInputEditTextCep = view.findViewById(R.id.edtCep);
        autoCompleteTextViewUf = view.findViewById(R.id.edtUf);
        autoCompleteTextViewCidade = view.findViewById(R.id.edtCidadeNatal);
        textInputEditTextBairro = view.findViewById(R.id.edtBairro);
        textInputEditTextLogradouro = view.findViewById(R.id.edtLogradouro);
        textInputEditTextNumero = view.findViewById(R.id.edtNumero);
        btnProxima = view.findViewById(R.id.btnProxima);

        configurarMascaraCep();
        alternarHintCampoUf();
        popularCampoUfComDB();
        popularCampoCidadeComDB();
    }

    private void chamarViaCep() {

    }

    private Bundle recuperarDadosAtendidoRegisterFragment1() {
        Bundle valoresRecebidosFragment1 = this.getArguments();

        return valoresRecebidosFragment1;

    }

    private void alternarHintCampoUf() {
        if (tipoAtendido.equals(TipoAtendido.PM)) {
            textInputLayoutUf.setHint(getResources().getString(R.string.uf));
            textInputLayoutCidade.setHint(getResources().getString(R.string.cidade));
        } else {
            textInputLayoutUf.setHint(getResources().getString(R.string.uf_dependente_civil));
            textInputLayoutCidade.setHint(getResources().getString(R.string.cidade_dependente_civil));
        }
    }

    private void configurarMascaraCep() {
        Mascaras mascaras = new Mascaras();
        mascaras.criarMascaraParaCep(textInputEditTextCep);
    }

    private void popularCampoUfComDB() {


        UfController ufController = new UfController();
        ufController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray= new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        UfEnum ufEnum = (UfEnum) object.get("uf");

                        listaEstadosRecuperados.add(ufEnum);
                        configurarCampoEstado(listaEstadosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstado(ArrayList<UfEnum> listaEstadosRecuperados) {

        ArrayAdapter<UfEnum> adapterUf = new ArrayAdapter<UfEnum>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosRecuperados));
        autoCompleteTextViewUf.setAdapter(adapterUf);
        autoCompleteTextViewUf.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUf);

    }

    private void popularCampoCidadeComDB() {

        MunicipioComBaseNaUF.mostrarMunicipioComBaseNaUf(getActivity(), autoCompleteTextViewUf,
                autoCompleteTextViewCidade, listaCidadesRecuperadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCidade);
    }

    private void receberDadosAtendidoPreenchidos()
    {
        endereco = new Endereco();
        endereco.setCep(Mascaras.removerMascaras(textInputEditTextCep.getText().toString()));

        for(int i = 0; i < listaCidadesRecuperadas.size(); i++) {
            Cidade cidadeSelecionada = listaCidadesRecuperadas.get(i);
            if(cidadeSelecionada.getNome().equals(autoCompleteTextViewCidade.getText().toString())) {
                endereco.setCidade(cidadeSelecionada);
            }
        }

        endereco.setBairro(textInputEditTextBairro.getText().toString());
        endereco.setLogradouro(textInputEditTextLogradouro.getText().toString());
        endereco.setNumero(Integer.parseInt(textInputEditTextNumero.getText().toString()));
    }

    private boolean validarAtendido() {
        receberDadosAtendidoPreenchidos();

        if (!TextUtils.isEmpty(endereco.getCep()) || !tipoAtendido.equals("PM")) {

                if (!TextUtils.isEmpty(endereco.getCidade().toString()) || !tipoAtendido.equals("PM")) {

                    if (!TextUtils.isEmpty(endereco.getBairro()) || !tipoAtendido.equals("PM")) {

                        if (!TextUtils.isEmpty(endereco.getLogradouro()) || !tipoAtendido.equals("PM")) {

                            if (!TextUtils.isEmpty(String.valueOf(endereco.getNumero())) || !tipoAtendido.equals("PM")) {

                                return true;
                            }
                            else {
                                textInputEditTextNumero.setError("O campo NÚMERO é obrigatório!");
                                textInputEditTextNumero.requestFocus();
                                return false; }

                        }
                        else {
                            textInputEditTextLogradouro.setError("O campo LOGRADOURO é obrigatório!");
                            textInputEditTextLogradouro.requestFocus();
                            return false; }

                    }
                    else {
                        textInputEditTextBairro.setError("O campo BAIRRO é obrigatório!");
                        textInputEditTextBairro.requestFocus();
                        return false; }

                }
                else {
                    autoCompleteTextViewCidade.setError("O campo CIDADE é obrigatório!.");
                    autoCompleteTextViewCidade.requestFocus();
                    return false; }

        }
        else {
            textInputEditTextCep.setError("O campo CEP é obrigatório!");
            textInputEditTextCep.requestFocus();
            return false; }
    }

    private Bundle encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1 =  recuperarDadosAtendidoRegisterFragment1();
        Bundle bundle = new Bundle();

        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
        bundle.putSerializable("cep", (endereco));

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if(validarAtendido())
                {
                    if(tipoAtendido.equals("1"))
                    {

                        Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                        usuarioRegisterFragment3 = new UsuarioRegisterFragment3();
                        usuarioRegisterFragment3.setArguments(valoresEncapsuladosParaEnvio);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameConteudo, usuarioRegisterFragment3);
                        fragmentTransaction.addToBackStack(null).commit();
                    }
                    else
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
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Usuario encapsularValoresParaCadastro() throws ParseException {
        Bundle valoresRecebidosFragment1 = recuperarDadosAtendidoRegisterFragment1();

       Usuario usuario = new Usuario();
       Usuario titular = new Usuario();

       LocalDate dataNascimento = DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("dataNascimento"));

        usuario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf(valoresRecebidosFragment1.getString("cpf"));
        usuario.setSexo((SexoEnum) valoresRecebidosFragment1.getSerializable("sexo"));
        usuario.setTelefones((ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefones"));
        usuario.setEmail(valoresRecebidosFragment1.getString("email"));
        usuario.setEstadoCivil((EstadoCivilEnum) valoresRecebidosFragment1.getSerializable("estadoCivil"));
        usuario.setNaturalidade((Cidade) valoresRecebidosFragment1.getSerializable("cidadeNatal"));
        usuario.setEscolaridade((EscolaridadeEnum) valoresRecebidosFragment1.getSerializable("escolaridade"));
        usuario.setNumeroFilhos((valoresRecebidosFragment1.getInt("numeroFilhos")));
        usuario.setTitular(titular);
        usuario.setTipoVinculo((TipoVinculoEnum) valoresRecebidosFragment1.getSerializable("vinculo"));
        usuario.setEndereco(endereco);

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

    @Override
    public void onResume() {

        listaCidadesRecuperadas.clear();
        listaEstadosRecuperados.clear();
        super.onResume();
    }


}