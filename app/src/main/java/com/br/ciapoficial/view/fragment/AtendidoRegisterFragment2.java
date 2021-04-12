package com.br.ciapoficial.view.fragment;

import android.content.res.Resources;
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
import com.br.ciapoficial.controller.EstadoController;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendido;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.Titular;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AtendidoRegisterFragment2 extends Fragment {

    private PrincipalFragment principalFragment;
    private AtendidoRegisterFragment3 atendidoRegisterFragment3;
    private TextInputLayout textInputLayoutUf, textInputLayoutCidade;
    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
    private TextInputEditText textInputEditTextCep, textInputEditTextBairro, textInputEditTextLogradouro,
            textInputEditTextNumero;
    Button btnProxima;

    private ArrayList<Estado> listaEstadosRecuperados = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();

    private String cep;
    private String uf;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;

    private String tipoAtendido;

    public AtendidoRegisterFragment2() {
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
        textInputLayoutCidade = view.findViewById(R.id.textInputLayoutCidade);
        textInputEditTextCep = view.findViewById(R.id.edtCep);
        autoCompleteTextViewUf = view.findViewById(R.id.edtUf);
        autoCompleteTextViewCidade = view.findViewById(R.id.edtCidade);
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
        if (tipoAtendido.equals("PM")) {
            textInputLayoutUf.setHint(getResources().getString(R.string.uf));
            textInputLayoutCidade.setHint(getResources().getString(R.string.cidade));
        } else {
            textInputLayoutUf.setHint(getResources().getString(R.string.uf_dependente_civil));
            textInputLayoutCidade.setHint(getResources().getString(R.string.cidade_dependente_civil));
        }
    }

    private void configurarMascaraCep() {
        Mascaras mascaras = new Mascaras();
        mascaras.criarMascaraCep(textInputEditTextCep);
    }

    private void popularCampoUfComDB() {

        EstadoController estadoController = new EstadoController();
        estadoController.listarUfs(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Estado estado = new Estado();
                            estado.setId(Integer.valueOf(object.getString("id")));
                            estado.setUf(object.getString("uf"));

                            listaEstadosRecuperados.add(estado);
                            configurarCampoEstado(listaEstadosRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstado(ArrayList<Estado> listaEstadosRecuperados) {

        ArrayAdapter<Estado> adapterUf = new ArrayAdapter<Estado>(getActivity(),
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

    private void receberDadosAtendidoPreenchidos() {
        cep = textInputEditTextCep.getText().toString();

        for(int i = 0; i < listaEstadosRecuperados.size(); i++) {
            Estado estadoSelecionado = listaEstadosRecuperados.get(i);
            if(estadoSelecionado.getUf().equals(autoCompleteTextViewUf.getText().toString())) {
                uf = String.valueOf(estadoSelecionado.getId());
            }
        }

        for(int i = 0; i < listaCidadesRecuperadas.size(); i++) {
            Cidade cidadeSelecionada = listaCidadesRecuperadas.get(i);
            if(cidadeSelecionada.getDescricao().equals(autoCompleteTextViewCidade.getText().toString())) {
                cidade = String.valueOf(cidadeSelecionada.getId());
            }
        }

        bairro = textInputEditTextBairro.getText().toString();
        logradouro = textInputEditTextLogradouro.getText().toString();
        numero = textInputEditTextNumero.getText().toString();
    }

    private boolean validarAtendido() {
        receberDadosAtendidoPreenchidos();

        if (!TextUtils.isEmpty(cep) || !tipoAtendido.equals("PM")) {

            if (!TextUtils.isEmpty(uf) || !tipoAtendido.equals("PM")) {

                if (!TextUtils.isEmpty(cidade) || !tipoAtendido.equals("PM")) {

                    if (!TextUtils.isEmpty(bairro) || !tipoAtendido.equals("PM")) {

                        if (!TextUtils.isEmpty(logradouro) || !tipoAtendido.equals("PM")) {

                            if (!TextUtils.isEmpty(numero) || !tipoAtendido.equals("PM")) {

                                return true;
                            }
                            else {
                                textInputEditTextNumero.setError("O campo NUÚMERO é obrigatório!");
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
                autoCompleteTextViewUf.setError("O campo UF é obrigatório!");
                autoCompleteTextViewUf.requestFocus();
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
        bundle.putString("cep", Mascaras.removerMascaras(cep));
        bundle.putString("uf", uf);
        bundle.putString("cidade", cidade);
        bundle.putString("bairro", bairro);
        bundle.putString("logradouro", logradouro);
        bundle.putString("numero", numero);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarAtendido())
                {
                    if(tipoAtendido.equals("1"))
                    {

                        Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                        atendidoRegisterFragment3 = new AtendidoRegisterFragment3();
                        atendidoRegisterFragment3.setArguments(valoresEncapsuladosParaEnvio);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameConteudo, atendidoRegisterFragment3);
                        fragmentTransaction.addToBackStack(null).commit();
                    }
                    else
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
            }
        });

    }

    private Atendido encapsularValoresParaCadastro()
    {
        Bundle valoresRecebidosFragment1 = recuperarDadosAtendidoRegisterFragment1();

       Atendido atendido = new Atendido();
       Titular titular = new Titular();
       titular.setId((valoresRecebidosFragment1.getInt("titular")));

        atendido.setTipoAtendido(valoresRecebidosFragment1.getString("tipoAtendido"));
        atendido.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        atendido.setDataNascimento(valoresRecebidosFragment1.getString("dataNascimento"));
        atendido.setCpf(valoresRecebidosFragment1.getString("cpf"));
        atendido.setSexo(valoresRecebidosFragment1.getString("sexo"));
        atendido.setTelefones((valoresRecebidosFragment1.getStringArrayList("telefones")));
        atendido.setEmail(valoresRecebidosFragment1.getString("email"));
        atendido.setEstadoCivil(valoresRecebidosFragment1.getString("estadoCivil"));
        atendido.setUfNatal(valoresRecebidosFragment1.getString("ufNatal"));
        atendido.setCidadeNatal(valoresRecebidosFragment1.getString("cidadeNatal"));
        atendido.setEscolaridade(valoresRecebidosFragment1.getString("escolaridade"));
        atendido.setNumeroFilhos((valoresRecebidosFragment1.getString("numeroFilhos")));
        atendido.setTitular(titular);
        atendido.setVinculo(valoresRecebidosFragment1.getString("vinculo"));
        atendido.setCep(Mascaras.removerMascaras(cep));
        atendido.setUf(uf);
        atendido.setCidade(cidade);
        atendido.setBairro(bairro);
        atendido.setLogradouro(logradouro);
        atendido.setNumero(numero);

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

                        Log.e("Erro-cadastrarAtendido", response);

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