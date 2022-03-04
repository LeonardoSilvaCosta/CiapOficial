package com.br.ciapoficial.view.fragments;

import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.br.ciapoficial.network.UfController;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.validation.FieldValidator;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.Funcionario;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioUpdateFragment2 extends Fragment {

    private FuncionarioUpdateFragment3 funcionarioUpdateFragment3;
    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
    private TextInputEditText textInputEditTextCep, textInputEditTextBairro,
            textInputEditTextLogradouro, textInputEditTextNumero;
    private ArrayList<Estado> listaEstadosRecuperados = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();

    private Endereco endereco = new Endereco();

    private Button btnProxima;

    SharedPreferences sharedPreferences;

    public FuncionarioUpdateFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionario_update2, container, false);

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        configurarComponentes(view);
        configurarMascaraParaCep();
        popularCampoUfComDB();
        popularCampoCidadeComDB();
        recuperarDadosFuncionarioUpdateFragment1();
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

    private Bundle recuperarDadosFuncionarioUpdateFragment1() {
        Bundle valoresRecebidosFragment1 = this.getArguments();

        Funcionario funcionario = (Funcionario) valoresRecebidosFragment1.getSerializable(
                "funcionarioRecebidoDoDB");

        receberDadosFuncionarioPreviamentePreenchidos(funcionario);

        return valoresRecebidosFragment1;

    }

    private void receberDadosFuncionarioPreviamentePreenchidos(Funcionario dadosFuncionarioDb)
    {
        if(dadosFuncionarioDb != null) {

            if(endereco.getCep() == null) {
                if(dadosFuncionarioDb.getEndereco().getCep() != null){
                    textInputEditTextCep.setText(dadosFuncionarioDb.getEndereco().getCep()); }
            }
            else{
                textInputEditTextCep.setText(endereco.getCep()); }
            if(endereco.getCidade() == null) {
                if(dadosFuncionarioDb.getEndereco().getCidade() != null) {
                    autoCompleteTextViewUf.setText(
                            dadosFuncionarioDb.getEndereco().getCidade().getEstado().getNome());
                }else{
                    autoCompleteTextViewUf.setText(endereco.getCidade().getEstado().getNome());
                }
            }

            if(endereco.getCidade() == null) {
                if(dadosFuncionarioDb.getEndereco().getCidade() != null) {
                    autoCompleteTextViewCidade.setText(
                            dadosFuncionarioDb.getEndereco().getCidade().getNome());

                    endereco.setCidade(dadosFuncionarioDb.getEndereco().getCidade());

                    listaCidadesRecuperadas.add(dadosFuncionarioDb.getEndereco().getCidade());
                }else{
                    autoCompleteTextViewCidade.setText(endereco.getCidade().getNome());
                }
            }

            if(endereco.getBairro() == null) {
                if(dadosFuncionarioDb.getEndereco().getBairro() != null) {
                    textInputEditTextBairro.setText(
                            dadosFuncionarioDb.getEndereco().getBairro()); }
            }else{
                textInputEditTextBairro.setText(endereco.getBairro()); }
            if(endereco.getLogradouro() == null) {
                if(dadosFuncionarioDb.getEndereco().getLogradouro() != null) {
                    textInputEditTextLogradouro.setText(
                            dadosFuncionarioDb.getEndereco().getLogradouro()); }
            }else{
                textInputEditTextLogradouro.setText(endereco.getLogradouro()); }
            if(endereco.getNumero() == null) {
                textInputEditTextNumero.setText(
                        String.valueOf(dadosFuncionarioDb.getEndereco().getNumero()));
            }
            else{
                textInputEditTextNumero.setText(String.valueOf(endereco.getNumero()));
            }

        }
    }

    public void configurarMascaraParaCep() {
        Mascaras.criarMascaraParaCep(textInputEditTextCep);}


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

    public void popularCampoCidadeComDB() {

        MunicipioComBaseNaUF.mostrarMunicipioComBaseNaUf(getActivity(), autoCompleteTextViewUf,
                autoCompleteTextViewCidade, listaCidadesRecuperadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCidade);

    }

    public boolean validarCadastroFuncionario() {
        if (
                FieldValidator.validarCep(textInputEditTextCep) &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewUf, listaEstadosRecuperados,
                        "O campo UF é obrigatório.", "Insira uma opção de UF válida.") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewCidade, listaCidadesRecuperadas,
                        "o Campo CIDADE é obrigatório.", "Insira uma opção de CIDADE válida.") &&
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

        encapsularValoresParaEnvio();
    }

    private Bundle encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1 =  recuperarDadosFuncionarioUpdateFragment1();
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

                    funcionarioUpdateFragment3 = new FuncionarioUpdateFragment3();
                    funcionarioUpdateFragment3.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, funcionarioUpdateFragment3);
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