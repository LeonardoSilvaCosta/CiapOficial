package com.br.ciapoficial.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EstadoController;
import com.br.ciapoficial.enums.UfEnum;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FuncionarioRegisterFragment2 extends Fragment {

    private FuncionarioRegisterFragment3 funcionarioRegisterFragment3;
    private TextInputLayout tilUf;
    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
    private TextInputEditText textInputEditTextCep, textInputEditTextBairro,
            textInputEditTextLogradouro, textInputEditTextNumero;

    private ArrayList<UfEnum> listaEstadosRecuperados = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();

    Button btnProxima;

    private Endereco endereco;

    public FuncionarioRegisterFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionario_register2, container, false);

        configurarComponentes(view);
        configurarMascaraCep();
        popularCampoUfComDB();
        popularCampoCidadeComDB();
        abrirProximaTela();
        return view;
    }

    private void configurarComponentes(View view) {
        tilUf = view.findViewById(R.id.textInputLayoutUf);
        textInputEditTextCep = view.findViewById(R.id.edtCep);
        autoCompleteTextViewUf = view.findViewById(R.id.edtUf);
        autoCompleteTextViewCidade = view.findViewById(R.id.edtCidadeNatal);
        textInputEditTextBairro = view.findViewById(R.id.edtBairro);
        textInputEditTextLogradouro = view.findViewById(R.id.edtLogradouro);
        textInputEditTextNumero = view.findViewById(R.id.edtNumero);
        btnProxima = view.findViewById(R.id.btnProxima);
    }

    private void chamarViaCep() {

    }

    private Bundle recuperarDadosUserRegisterFragment1() {
        Bundle valoresRecebidosFragment1 = this.getArguments();

        return valoresRecebidosFragment1;

    }

    private void configurarMascaraCep() {
        Mascaras mascaras = new Mascaras();
        mascaras.criarMascaraCep(textInputEditTextCep);
    }

    private void popularCampoUfComDB() {

        EstadoController estadoController = new EstadoController();
        estadoController.listar(getActivity(), new VolleyCallback() {
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

    private void receberDadosUsuarioPreenchidos() {
        endereco = new Endereco();
        endereco.setCep(Mascaras.removerMascaras(textInputEditTextCep.getText().toString()));

        for(int i = 0; i < listaCidadesRecuperadas.size(); i++) {
            Cidade cidadeSelecionada = listaCidadesRecuperadas.get(i);
            if(cidadeSelecionada.getDescricao().equals(autoCompleteTextViewCidade.getText().toString())) {
                endereco.setCidade(cidadeSelecionada);
            }
        }

        endereco.setBairro(textInputEditTextBairro.getText().toString());
        endereco.setLogradouro(textInputEditTextLogradouro.getText().toString());
        endereco.setNumero(Integer.parseInt(textInputEditTextNumero.getText().toString()));
    }

    private boolean validarCadastroUsuario() {
        receberDadosUsuarioPreenchidos();

        if (!TextUtils.isEmpty(endereco.getCep())) {

            if (!TextUtils.isEmpty(endereco.getCidade().getDescricao())) {

                if (!TextUtils.isEmpty(endereco.getBairro())) {

                    if (!TextUtils.isEmpty(endereco.getLogradouro())) {

                        if (!TextUtils.isEmpty(String.valueOf(endereco.getNumero()))) {

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
            textInputEditTextCep.setError("O campo CEP é obrigatório!");
            textInputEditTextCep.requestFocus();
            return false; }
    }

    private Bundle encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1 =  recuperarDadosUserRegisterFragment1();
        Bundle bundle = new Bundle();

        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
        bundle.putSerializable("endereco", endereco);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCadastroUsuario())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    funcionarioRegisterFragment3 = new FuncionarioRegisterFragment3();
                    funcionarioRegisterFragment3.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, funcionarioRegisterFragment3);
                    fragmentTransaction.addToBackStack(null).commit();
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