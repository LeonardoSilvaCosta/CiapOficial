package com.br.ciapoficial.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EscolaridadeController;
import com.br.ciapoficial.controller.EstadoCivilController;
import com.br.ciapoficial.controller.UfController;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.helper.ValidarCPF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Uf;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.SneakyThrows;

public class FuncionarioRegisterFragment1 extends Fragment {

    private FuncionarioRegisterFragment2 funcionarioRegisterFragment2;

    private LinearLayout linearLayoutTelefone;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextNumeroFilhos, textInputEditTextTelefone,
            textInputEditTextEmail;
    private AutoCompleteTextView autoCompleteTextViewUfNatal, autoCompleteTextViewCidadeNatal,
            autoCompleteTextViewEstadoCivil, autoCompleteTextViewEscolaridade;
    private RadioGroup radioGroupSexo;
    private RadioButton rbtnMasculino, rbtnFeminino;
    private Button btnAdicionarTelefone, btnProxima;

    private ArrayList<Telefone> arrayListTelefoneAdicionados = new ArrayList<>();
    private ArrayList<Telefone> listaDeTelefoneAdicionados = new ArrayList<>();
    private ArrayList<Uf> listaUfsRecuperadas = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();
    private ArrayList<EstadoCivil> listaEstadosCivisRecuperados = new ArrayList<>();
    private ArrayList<Escolaridade> listaEscolaridadesRecuperadas = new ArrayList<>();

    private String nomeCompleto, cpf, email;
    private LocalDate dataNascimento;
    private Cidade naturalidade;
    private EstadoCivil estadoCivil = new EstadoCivil();
    private int numeroFilhos;
    private Escolaridade escolaridade = new Escolaridade();
    private SexoEnum sexo;

    public FuncionarioRegisterFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_funcionario_register1, container, false);

        configurarComponentes(view);
        configurarMascaraParaData();
        configurarMascaraParaCpf();
        configurarMascaraParaTelefone();
        configurarCampoDeTelefone();
        definirComportamentoDosRadioButtons();
        popularCampoUfNatalComDB();
        popularCampoCidadeComDB();
        popularCampoEstadoCivilComDB();
        popularCampoEscolaridadeComDB();
        abrirProximaTela();

        return view ;
    }

    private void configurarComponentes(View view)
    {
        linearLayoutTelefone = view.findViewById(R.id.linearLayoutTelefone);
        textInputEditTextNomeCompleto = view.findViewById(R.id.edtNomeCompleto);
        textInputEditTextDataNascimento = view.findViewById(R.id.edtDataNascimento);
        textInputEditTextCpf = view.findViewById(R.id.edtCpf);
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);
        autoCompleteTextViewUfNatal = view.findViewById(R.id.edtUf);
        autoCompleteTextViewCidadeNatal = view.findViewById(R.id.edtCidadeNatal);
        autoCompleteTextViewEstadoCivil = view.findViewById(R.id.edtEstadoCivil);
        textInputEditTextNumeroFilhos = view.findViewById(R.id.edtNumeroFilhos);
        autoCompleteTextViewEscolaridade = view.findViewById(R.id.edtEscolaridade);
        textInputEditTextTelefone = view.findViewById(R.id.edtTelefone);
        textInputEditTextEmail = view.findViewById(R.id.edtEmail);
        btnAdicionarTelefone = view.findViewById(R.id.btnAdicionarTelefone);
        btnProxima = view.findViewById(R.id.btnProxima);
    }

    private void configurarMascaraParaData()
    {Mascaras.criarMascaraParaData(textInputEditTextDataNascimento);}

    private void configurarMascaraParaCpf()
    {Mascaras.criarMascaraParaCpf(textInputEditTextCpf);}

    private void configurarMascaraParaTelefone()
    {Mascaras.criarMascaraParaTelefone(textInputEditTextTelefone);}

    private void configurarCampoDeTelefone()
    {
        linearLayoutTelefone.removeAllViews();

        btnAdicionarTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewTelefoneUsuarioString(getActivity(), textInputEditTextTelefone,
                        arrayListTelefoneAdicionados, linearLayoutTelefone);
            }
        });
    }

    private void definirComportamentoDosRadioButtons() {

        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnMasculino) {
                    sexo = sexo.MASCULINO;
                } else if (checkedId == R.id.rbtnFeminino) {
                    sexo = sexo.FEMININO;
                }
            }
        });
    }

    private void popularCampoUfNatalComDB() {

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
                        listaUfsRecuperadas.add(uf);
                        configurarCampoEstadoNatal(listaUfsRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstadoNatal(ArrayList<Uf> listaEstadosRecuperados) {

        ArrayAdapter<Uf> adapterUf = new ArrayAdapter<Uf>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosRecuperados));
        autoCompleteTextViewUfNatal.setAdapter(adapterUf);
        autoCompleteTextViewUfNatal.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUfNatal);

    }

    private void popularCampoCidadeComDB() {

        MunicipioComBaseNaUF.mostrarMunicipioComBaseNaUf(getActivity(), autoCompleteTextViewUfNatal,
                autoCompleteTextViewCidadeNatal, listaCidadesRecuperadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCidadeNatal);
    }

    private void popularCampoEstadoCivilComDB() {

        EstadoCivilController estadoCivilController = new EstadoCivilController();
        estadoCivilController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        EstadoCivil estadoCivil = new EstadoCivil();
                        estadoCivil.setId(Integer.parseInt(object.getString("id")));
                        estadoCivil.setNome(object.getString("nome"));

                        listaEstadosCivisRecuperados.add(estadoCivil);
                        configurarCampoEstadoCivil(listaEstadosCivisRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstadoCivil(ArrayList<EstadoCivil> listaEstadosCivisRecuperados) {

        ArrayAdapter<EstadoCivil> adapterEstadoCivil = new ArrayAdapter<EstadoCivil>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosCivisRecuperados));
        autoCompleteTextViewEstadoCivil.setAdapter(adapterEstadoCivil);
        autoCompleteTextViewEstadoCivil.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEstadoCivil);

    }

    private void popularCampoEscolaridadeComDB() {

        EscolaridadeController escolaridadeController = new EscolaridadeController();
        escolaridadeController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Escolaridade escolaridade = new Escolaridade();
                        escolaridade.setId(Integer.parseInt(object.getString("id")));
                        escolaridade.setNome(object.getString("nome"));

                        listaEscolaridadesRecuperadas.add(escolaridade);
                        configurarCampoEscolaridade(listaEscolaridadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEscolaridade(ArrayList<Escolaridade> listaEscolaridadesRecuperadas) {

        ArrayAdapter<Escolaridade> adapterEscolaridade = new ArrayAdapter<Escolaridade>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEscolaridadesRecuperadas));
        autoCompleteTextViewEscolaridade.setAdapter(adapterEscolaridade);
        autoCompleteTextViewEscolaridade.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEscolaridade);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void receberDadosFuncionarioPreenchidos() throws ParseException {
        nomeCompleto = textInputEditTextNomeCompleto.getText().toString();
        dataNascimento = DateFormater.StringToLocalDate(textInputEditTextDataNascimento.getText().toString());

        cpf = textInputEditTextCpf.getText().toString();
        for(int i = 0; i < listaEstadosCivisRecuperados.size(); i++) {
            EstadoCivil estadoCivilSelecionado = listaEstadosCivisRecuperados.get(i);
            if(estadoCivilSelecionado.getNome().equals(autoCompleteTextViewEstadoCivil.getText().toString())) {

                estadoCivil.setId(estadoCivilSelecionado.getId());
                estadoCivil.setNome(estadoCivilSelecionado.getNome());
            }
        }
        for(int i = 0; i < listaCidadesRecuperadas.size(); i++) {
            Cidade cidadeSelecionada = listaCidadesRecuperadas.get(i);
            if(cidadeSelecionada.getNome().equals(autoCompleteTextViewCidadeNatal.getText().toString())) {
                naturalidade = cidadeSelecionada;
            }
        }
        numeroFilhos = Integer.valueOf(textInputEditTextNumeroFilhos.getText().toString());
        for(int i = 0; i < listaEscolaridadesRecuperadas.size(); i++) {
            Escolaridade escolaridadeSelecionada = listaEscolaridadesRecuperadas.get(i);
            if(escolaridadeSelecionada.getNome().equals(autoCompleteTextViewEscolaridade.getText().toString())) {
                escolaridade.setId(escolaridadeSelecionada.getId());
                escolaridade.setNome(escolaridadeSelecionada.getNome());
            }
        }
        listaDeTelefoneAdicionados = arrayListTelefoneAdicionados;
        email = textInputEditTextEmail.getText().toString();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroFuncionario() throws ParseException {
        receberDadosFuncionarioPreenchidos();
        Boolean validarCPF = ValidarCPF.validarCPF(textInputEditTextCpf.getText().toString());

        if (!TextUtils.isEmpty(nomeCompleto)) {

            if (dataNascimento != null) {

                if (!TextUtils.isEmpty(dataNascimento.toString())){

                    if (!TextUtils.isEmpty(cpf)) {

                        if (rbtnMasculino.isChecked() || rbtnFeminino.isChecked()) {

                            if (!TextUtils.isEmpty(sexo.getNome())) {

                                if(!TextUtils.isEmpty(naturalidade.toString())) {

                                    if(!TextUtils.isEmpty(estadoCivil.toString())) {

                                        if(!TextUtils.isEmpty(String.valueOf(numeroFilhos))) {

                                            if(!TextUtils.isEmpty(escolaridade.toString())) {

                                                if (!listaDeTelefoneAdicionados.isEmpty()) {

                                                    if (!TextUtils.isEmpty(email)) {

                                                        if (validarCPF.equals(true)) {

                                                            encapsularValoresParaEnvio();
                                                            return true;

                                                        }
                                                        else {
                                                            textInputEditTextCpf.setError("CPF invalido!");
                                                            textInputEditTextCpf.requestFocus();
                                                            return false; }

                                                    }
                                                    else {
                                                        textInputEditTextEmail.setError("o campo EMAIL deve ser preenchido.");
                                                        textInputEditTextTelefone.requestFocus();
                                                        return false; }

                                                }
                                                else {
                                                    textInputEditTextTelefone.setError("É necessário adicionar ao menos um telefone.");
                                                    textInputEditTextTelefone.requestFocus();
                                                    return false; }

                                            }
                                            else {
                                                autoCompleteTextViewEscolaridade.setError("É necessário adicionar o grau de escolaridade.");
                                                autoCompleteTextViewEscolaridade.requestFocus();
                                                return false; }

                                        }
                                        else {
                                            textInputEditTextNumeroFilhos.setError("É necessário adicionar um valor para número de filhos.");
                                            textInputEditTextNumeroFilhos.requestFocus();
                                            return false; }

                                    }
                                    else {
                                        Toast.makeText(getActivity(),
                                                "É necessário informar o Estado Civil",
                                                Toast.LENGTH_SHORT).show();
                                        autoCompleteTextViewEstadoCivil.requestFocus();
                                        return false; }

                                }
                                else {
                                    Toast.makeText(getActivity(),
                                            "É necessário informar a naturalidade",
                                            Toast.LENGTH_SHORT).show();
                                    autoCompleteTextViewUfNatal.requestFocus();
                                    return false; }

                            }
                            else {
                                Toast.makeText(getActivity(),
                                        "Selecione uma opção de SEXO",
                                        Toast.LENGTH_SHORT).show();
                                rbtnMasculino.requestFocus();
                                return false; }

                        }
                        else {
                            Toast.makeText(getActivity(),
                                    "Selecione uma opção de SEXO",
                                    Toast.LENGTH_SHORT).show();
                            rbtnMasculino.requestFocus();
                            return false; }

                    }
                    else {
                        textInputEditTextCpf.setError("O campo CPF é obrigatório!");
                        textInputEditTextCpf.requestFocus();
                        return false; }

                }
                else {
                    textInputEditTextDataNascimento.setError("Digite uma data válida.");
                    textInputEditTextDataNascimento.requestFocus();
                    return false; }

            }
            else {
                textInputEditTextDataNascimento.setError("O campo DATA DE NASCIMENTO é obrigatório!");
                textInputEditTextDataNascimento.requestFocus();
                return false; }

        }
        else {
            textInputEditTextNomeCompleto.setError("O campo NOME COMPLETO é obrigatório!");
            textInputEditTextNomeCompleto.requestFocus();
            return false; }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bundle encapsularValoresParaEnvio()
    {
        Bundle bundle = new Bundle();

        bundle.putString("nomeCompleto", nomeCompleto);
        bundle.putString("dataNascimento", DateFormater.localDateToString(dataNascimento));
        bundle.putString("cpf", cpf);
        bundle.putSerializable("sexo", sexo);
        bundle.putSerializable("naturalidade", naturalidade);
        bundle.putSerializable("estado_civil", estadoCivil);
        bundle.putInt("numeroFilhos", numeroFilhos);
        bundle.putSerializable("escolaridade", escolaridade);
        bundle.putSerializable("telefones", listaDeTelefoneAdicionados);
        bundle.putString("email", email);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if(validarCadastroFuncionario())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    funcionarioRegisterFragment2 = new FuncionarioRegisterFragment2();
                    funcionarioRegisterFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, funcionarioRegisterFragment2);
                    fragmentTransaction.addToBackStack(null).commit();
                }

            }
        });

    }

    @Override
    public void onResume() {

        arrayListTelefoneAdicionados.clear();
        listaDeTelefoneAdicionados.clear();

        super.onResume();
    }
}