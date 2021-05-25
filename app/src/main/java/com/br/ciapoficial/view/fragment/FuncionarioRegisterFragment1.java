package com.br.ciapoficial.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EscolaridadeController;
import com.br.ciapoficial.controller.EstadoCivilController;
import com.br.ciapoficial.controller.EstadoController;
import com.br.ciapoficial.enums.EscolaridadeEnum;
import com.br.ciapoficial.enums.EstadoCivilEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.UfEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.helper.ValidarCPF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Telefone;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import lombok.SneakyThrows;

import static com.br.ciapoficial.view.LoginActivity.fileName;

public class FuncionarioRegisterFragment1 extends Fragment {

    private LinearLayout linearLayoutTelefone;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextNumeroFilhos, textInputEditTextTelefone, textInputEditTextEmail;
    private AutoCompleteTextView autoCompleteTextViewUfNatal, autoCompleteTextViewCidadeNatal,
            autoCompleteTextViewEstadoCivil, autoCompleteTextViewEscolaridade;

    private RadioGroup radioGroupSexo;
    private RadioButton rbtnMasculino, rbtnFeminino;
    private Button btnAdicionarTelefone, btnProxima;

    private FuncionarioRegisterFragment2 funcionarioRegisterFragment2;
    private SharedPreferences sharedPreferences;

    private ArrayList<Telefone> arrayListTelefonesAdicionados = new ArrayList<>();
    private ArrayList<Telefone> listaDeTelefonesAdicionados = new ArrayList<>();
    private ArrayList<UfEnum> listaUfsRecuperadas = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();
    private ArrayList<EstadoCivilEnum> listaEstadosCivisRecuperados = new ArrayList<>();
    private ArrayList<EscolaridadeEnum> listaEscolaridadesRecuperadas = new ArrayList<>();

    private String nomeCompleto, cpf, email;
    private Date dataNascimento;
    private Cidade naturalidade;
    private EstadoCivilEnum estadoCivilEnum;
    private int numeroFilhos;
    private EscolaridadeEnum escolaridadeEnum;
    private SexoEnum sexo;

    public FuncionarioRegisterFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_funcionario_register1, container, false);

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);

        configurarComponentes(view);
        configurarMascaraData();
        configurarMascaraCpf();
        configurarMascaraTelefone();
        configurarCampoTelefone();
        definirComportamentoRadioButtons();
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

    private void configurarMascaraData()
    {
        Mascaras mascara = new Mascaras();
        mascara.mascaraData(textInputEditTextDataNascimento);
    }

    private void configurarMascaraCpf()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraCpf(textInputEditTextCpf);
    }

    private void configurarMascaraTelefone()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraTelefone(textInputEditTextTelefone);
    }

    private void configurarCampoTelefone()
    {
        linearLayoutTelefone.removeAllViews();

        btnAdicionarTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewTelefoneUsuarioString(getActivity(), textInputEditTextTelefone,
                        arrayListTelefonesAdicionados, linearLayoutTelefone);
            }
        });
    }

    private void definirComportamentoRadioButtons() {

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

        EstadoController estadoController = new EstadoController();
        estadoController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        UfEnum estado = UfEnum.valueOf(object.getString("uf"));

                        listaUfsRecuperadas.add(estado);
                        configurarCampoEstadoNatal(listaUfsRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstadoNatal(ArrayList<UfEnum> listaEstadosRecuperados) {

        ArrayAdapter<UfEnum> adapterUf = new ArrayAdapter<UfEnum>(getActivity(),
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

                        EstadoCivilEnum estadoCivil = EstadoCivilEnum.valueOf(object.getString("estadoCivil"));

                        listaEstadosCivisRecuperados.add(estadoCivil);
                        configurarCampoEstadoCivil(listaEstadosCivisRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstadoCivil(ArrayList<EstadoCivilEnum> listaEstadosCivisRecuperados) {

        ArrayAdapter<EstadoCivilEnum> adapterEstadoCivil = new ArrayAdapter<EstadoCivilEnum>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosCivisRecuperados));
        autoCompleteTextViewEstadoCivil.setAdapter(adapterEstadoCivil);
        autoCompleteTextViewEstadoCivil.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEstadoCivil);

    }

    private void popularCampoEscolaridadeComDB() {

        EscolaridadeController escolaridadeController = new EscolaridadeController();
        escolaridadeController.listarEscolaridades(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        EscolaridadeEnum escolaridade = EscolaridadeEnum.valueOf(object.getString("escolaridade"));

                        listaEscolaridadesRecuperadas.add(escolaridade);
                        configurarCampoEscolaridade(listaEscolaridadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEscolaridade(ArrayList<EscolaridadeEnum> listaEscolaridadesRecuperadas) {

        ArrayAdapter<EscolaridadeEnum> adapterEscolaridade = new ArrayAdapter<EscolaridadeEnum>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEscolaridadesRecuperadas));
        autoCompleteTextViewEscolaridade.setAdapter(adapterEscolaridade);
        autoCompleteTextViewEscolaridade.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEscolaridade);

    }

    private void receberDadosUsuarioPreenchidos() throws ParseException {
        nomeCompleto = textInputEditTextNomeCompleto.getText().toString();
        dataNascimento = DateFormater.StringToDate(textInputEditTextDataNascimento.getText().toString());
        cpf = textInputEditTextCpf.getText().toString();
        for(int i = 0; i < listaEstadosCivisRecuperados.size(); i++) {
            EstadoCivilEnum estadoCivilSelecionado = listaEstadosCivisRecuperados.get(i);
            if(estadoCivilSelecionado.getNome().equals(autoCompleteTextViewEstadoCivil.getText().toString())) {
                estadoCivilEnum = estadoCivilSelecionado;
            }
        }
        for(int i = 0; i < listaCidadesRecuperadas.size(); i++) {
            Cidade cidadeSelecionada = listaCidadesRecuperadas.get(i);
            if(cidadeSelecionada.getDescricao().equals(autoCompleteTextViewCidadeNatal.getText().toString())) {
                naturalidade = cidadeSelecionada;
            }
        }
        numeroFilhos = Integer.valueOf(textInputEditTextNumeroFilhos.getText().toString());
        for(int i = 0; i < listaEscolaridadesRecuperadas.size(); i++) {
            EscolaridadeEnum escolaridadeSelecionada = listaEscolaridadesRecuperadas.get(i);
            if(escolaridadeSelecionada.getNome().equals(autoCompleteTextViewEscolaridade.getText().toString())) {
                escolaridadeEnum = escolaridadeSelecionada;
            }
        }
        listaDeTelefonesAdicionados = arrayListTelefonesAdicionados;
        email = textInputEditTextEmail.getText().toString();

    }

    private boolean validarCadastroUsuario() throws ParseException {
        receberDadosUsuarioPreenchidos();
        Boolean validarCPF = ValidarCPF.validarCPF(textInputEditTextCpf.getText().toString());

        if (!TextUtils.isEmpty(nomeCompleto)) {

            if (dataNascimento != null) {

                if (!TextUtils.isEmpty(dataNascimento.toString())){

                    if (!TextUtils.isEmpty(cpf)) {

                        if (rbtnMasculino.isChecked() || rbtnFeminino.isChecked()) {

                            if (!TextUtils.isEmpty(sexo.getNome())) {

                                if(!TextUtils.isEmpty(naturalidade.toString())) {

                                    if(!TextUtils.isEmpty(estadoCivilEnum.getNome())) {

                                        if(!TextUtils.isEmpty(String.valueOf(numeroFilhos))) {

                                            if(!TextUtils.isEmpty(escolaridadeEnum.getNome())) {

                                                if (!listaDeTelefonesAdicionados.isEmpty()) {

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

        public Bundle encapsularValoresParaEnvio()
        {
            Bundle bundle = new Bundle();

            bundle.putString("nomeCompleto", nomeCompleto);
            bundle.putString("dataNascimento", dataNascimento.toString());
            bundle.putString("cpf", cpf);
            bundle.putSerializable("sexo", sexo);
            bundle.putSerializable("naturalidade", naturalidade);
            bundle.putSerializable("estado_civil", estadoCivilEnum);
            bundle.putInt("numeroFilhos", numeroFilhos);
            bundle.putSerializable("escolaridade", escolaridadeEnum);
            bundle.putSerializable("telefones", listaDeTelefonesAdicionados);
            bundle.putString("email", email);

            return bundle;
        }

        private void abrirProximaTela() {

            btnProxima.setOnClickListener(new View.OnClickListener() {
                @SneakyThrows
                @Override
                public void onClick(View v) {

                    if(validarCadastroUsuario())
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

            arrayListTelefonesAdicionados.clear();
            listaDeTelefonesAdicionados.clear();

            super.onResume();
        }
    }