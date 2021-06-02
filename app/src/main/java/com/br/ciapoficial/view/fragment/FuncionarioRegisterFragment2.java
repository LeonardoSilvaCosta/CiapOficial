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
import com.br.ciapoficial.controller.UfController;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Uf;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FuncionarioRegisterFragment2 extends Fragment {

    private FuncionarioRegisterFragment3 funcionarioRegisterFragment3;

    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
    private TextInputEditText textInputEditTextCep, textInputEditTextBairro,
            textInputEditTextLogradouro, textInputEditTextNumero;
    private Button btnProxima;

    private ArrayList<Uf> listaEstadosRecuperados = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();

    private Endereco endereco;

    public FuncionarioRegisterFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionario_register2, container, false);

        configurarComponentes(view);
        configurarMascaraParaCep();
        popularCampoUfComDB();
        popularCampoCidadeComDB();
        abrirProximaTela();
        return view;
    }

    private void configurarComponentes(View view) {
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

    private Bundle recuperarDadosFuncionarioRegisterFragment1() {
        Bundle valoresRecebidosFragment1 = this.getArguments();

        return valoresRecebidosFragment1;

    }

    private void configurarMascaraParaCep() {Mascaras.criarMascaraParaCep(textInputEditTextCep);}

    private void popularCampoUfComDB() {

        UfController ufController = new UfController();
        ufController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Uf uf = new Uf();
                        uf.setId(Integer.parseInt(object.getString("id")));
                        uf.setNome(object.getString("nome"));

                        listaEstadosRecuperados.add(uf);
                        configurarCampoEstado(listaEstadosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstado(ArrayList<Uf> listaEstadosRecuperados) {

        ArrayAdapter<Uf> adapterUf = new ArrayAdapter<Uf>(getActivity(),
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

    private void receberDadosFuncionarioPreenchidos() {
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

    private boolean validarCadastroFuncionario() {
        receberDadosFuncionarioPreenchidos();

        if (!TextUtils.isEmpty(endereco.getCep())) {

            if (!TextUtils.isEmpty(endereco.getCidade().getNome())) {

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
        Bundle valoresRecebidosFragment1 =  recuperarDadosFuncionarioRegisterFragment1();
        Bundle bundle = new Bundle();

        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
        bundle.putSerializable("endereco", endereco);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCadastroFuncionario())
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