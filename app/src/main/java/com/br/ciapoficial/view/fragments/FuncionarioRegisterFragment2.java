package com.br.ciapoficial.view.fragments;

import android.os.Bundle;
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
import com.br.ciapoficial.validation.FieldValidator;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Estado;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioRegisterFragment2 extends Fragment {

    private FuncionarioRegisterFragment3 funcionarioRegisterFragment3;

    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
    private TextInputEditText textInputEditTextCep, textInputEditTextBairro,
            textInputEditTextLogradouro, textInputEditTextNumero;
    private Button btnProxima;

    private List<Estado> listaEstadosRecuperados = new ArrayList<>();
    private List<Cidade> listaCidadesRecuperadas = new ArrayList<>();

    private Endereco endereco = new Endereco();

    //Usar API de CEP para settar dados de endereço com mais facilidade

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
        ufController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Estado estado = new Estado();
                        estado.setId(Integer.parseInt(object.getString("id")));
                        estado.setNome(object.getString("nome"));

                        listaEstadosRecuperados.add(estado);
                        configurarCampoEstado(listaEstadosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstado(List<Estado> listaEstadosRecuperados) {

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

    private boolean validarCadastroFuncionario() {
        if (
                FieldValidator.validarCep(textInputEditTextCep) &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewUf, listaEstadosRecuperados,
                        "O campo UF é obrigatório.", "Insira uma opção de UF válida.") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewCidade, listaCidadesRecuperadas,
                        "O campo CIDADE é obrigatório.", "Insira uma opção de CIDADE válida. ") &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextBairro, "BAIRRO") &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextLogradouro, "LOGRADOURO") &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextNumero, "NÚMERO"))
        {
            receberDadosFuncionarioPreenchidos();
            return true;
        }else { return false; }
    }

    public void receberDadosFuncionarioPreenchidos() {
        endereco.setCep(Mascaras.removerMascaras(textInputEditTextCep.getText().toString()).trim());

        for(Cidade cidadeSelecionada : listaCidadesRecuperadas) {
            if(cidadeSelecionada.getNome().equals(autoCompleteTextViewCidade.getText().toString().trim()))
                endereco.setCidade(cidadeSelecionada);
        }

        endereco.setBairro(textInputEditTextBairro.getText().toString().trim());
        endereco.setLogradouro(textInputEditTextLogradouro.getText().toString().trim());
        endereco.setNumero(Integer.parseInt(textInputEditTextNumero.getText().toString().trim()));
    }


    private Bundle encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1 =  recuperarDadosFuncionarioRegisterFragment1();
        Bundle bundle = new Bundle();

        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
        bundle.putSerializable("endereco", endereco);

        return bundle;
    }

    public void abrirProximaTela() {

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
    public void onStop() {

        listaEstadosRecuperados.clear();

//        if (endereco.getCidade() !=null) { cidadeSaver = endereco.getCidade(); }
        super.onStop();
    }

//    @Override
//    public void onResume() {
//        if(cidadeSaver != null) endereco.setCidade(cidadeSaver);
//        super.onResume();
//    }
}