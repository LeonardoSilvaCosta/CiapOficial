package com.br.ciapoficial.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.network.EscolaridadeController;
import com.br.ciapoficial.network.EstadoCivilController;
import com.br.ciapoficial.network.UfController;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.validation.FieldValidator;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.Telefone;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class FuncionarioRegisterFragment1 extends Fragment {

    private FuncionarioRegisterFragment2 funcionarioRegisterFragment2;

    private LinearLayout linearLayoutTelefone;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataDeNascimento,
            textInputEditTextCpf, textInputEditTextNumeroDeFilhos, textInputEditTextTelefone,
            textInputEditTextEmail;
    private AutoCompleteTextView autoCompleteTextViewUfNatal, autoCompleteTextViewCidadeNatal,
            autoCompleteTextViewEstadoCivil, autoCompleteTextViewEscolaridade;
    private RadioGroup radioGroupSexo;
    private RadioButton rbtnMasculino, rbtnFeminino;
    private Button btnAdicionarTelefone, btnProxima;

    private List<Telefone> arrayListDeTelefonesAdicionados = new ArrayList<>();
    private List<Telefone> listaDeTelefonesAdicionados = new ArrayList<>();
    private List<Estado> listaDeUfsRecuperadas = new ArrayList<>();
    private List<Cidade> listaDeCidadesRecuperadas = new ArrayList<>();
    private List<EstadoCivil> listaDeEstadosCivisRecuperados = new ArrayList<>();
    private List<Escolaridade> listaDeEscolaridadesRecuperadas = new ArrayList<>();

    private String nomeCompleto, cpf, email;
    private LocalDate dataNascimento;
    private Estado estado;
    private Cidade naturalidade;
    private EstadoCivil estadoCivil = new EstadoCivil();
    private int numeroFilhos;
    private Escolaridade escolaridade = new Escolaridade();
    private SexoEnum sexo;

    //ajustar campo de telefone - lista não permite deletar número da tela após retornar de tela posterior

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
        textInputEditTextDataDeNascimento = view.findViewById(R.id.edtDataNascimento);
        textInputEditTextCpf = view.findViewById(R.id.edtCpf);
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);
        autoCompleteTextViewUfNatal = view.findViewById(R.id.edtUf);
        autoCompleteTextViewCidadeNatal = view.findViewById(R.id.edtCidadeNatal);
        autoCompleteTextViewEstadoCivil = view.findViewById(R.id.edtEstadoCivil);
        textInputEditTextNumeroDeFilhos = view.findViewById(R.id.edtNumeroFilhos);
        autoCompleteTextViewEscolaridade = view.findViewById(R.id.edtEscolaridade);
        textInputEditTextTelefone = view.findViewById(R.id.edtTelefone);
        textInputEditTextEmail = view.findViewById(R.id.edtEmail);
        btnAdicionarTelefone = view.findViewById(R.id.btnAdicionarTelefone);
        btnProxima = view.findViewById(R.id.btnProxima);
    }

    private void configurarMascaraParaData()
    {Mascaras.criarMascaraParaData(textInputEditTextDataDeNascimento);}

    private void configurarMascaraParaCpf()
    {Mascaras.criarMascaraParaCpf(textInputEditTextCpf);}

    private void configurarMascaraParaTelefone()
    {Mascaras.criarMascaraParaTelefone(textInputEditTextTelefone);}

    private void criarTextViewParaTelefonesSelecionados(String textoRecebido)
    {
        TextView textView = new TextView(getContext());
        textView.setPadding(0, 10, 0, 10);
        textView.setText(textoRecebido);
        textView.setTag("lista");

        linearLayoutTelefone.addView(textView);
    }

    private void configurarCampoDeTelefone()
    {
        linearLayoutTelefone.removeAllViews();

        if(listaDeTelefonesAdicionados != null) {
            for(Telefone telefoneRecebido : listaDeTelefonesAdicionados) {
                String textoRecebido = telefoneRecebido.toString();
                criarTextViewParaTelefonesSelecionados(textoRecebido);
            }
        }

        btnAdicionarTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewTelefoneUsuarioString(getActivity(), textInputEditTextTelefone,
                        arrayListDeTelefonesAdicionados, linearLayoutTelefone);
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

    private void popularCampoUfNatalComDB()
    {
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
                        listaDeUfsRecuperadas.add(estado);
                        configurarCampoUfNatal(listaDeUfsRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoUfNatal(List<Estado> listaEstadosRecuperados) {

        ArrayAdapter<Estado> adapterUf = new ArrayAdapter<Estado>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosRecuperados));
        autoCompleteTextViewUfNatal.setAdapter(adapterUf);
        autoCompleteTextViewUfNatal.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUfNatal);

    }

    private void popularCampoCidadeComDB()
    {
        MunicipioComBaseNaUF.mostrarMunicipioComBaseNaUf(getActivity(), autoCompleteTextViewUfNatal,
                autoCompleteTextViewCidadeNatal, listaDeCidadesRecuperadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCidadeNatal);
    }

    private void popularCampoEstadoCivilComDB() {

        EstadoCivilController estadoCivilController = new EstadoCivilController();
        estadoCivilController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        EstadoCivil estadoCivil = new EstadoCivil();
                        estadoCivil.setId(Integer.parseInt(object.getString("id")));
                        estadoCivil.setNome(object.getString("nome"));

                        listaDeEstadosCivisRecuperados.add(estadoCivil);
                        configurarCampoEstadoCivil(listaDeEstadosCivisRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstadoCivil(List<EstadoCivil> listaEstadosCivisRecuperados) {

        ArrayAdapter<EstadoCivil> adapterEstadoCivil = new ArrayAdapter<EstadoCivil>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosCivisRecuperados));
        autoCompleteTextViewEstadoCivil.setAdapter(adapterEstadoCivil);
        autoCompleteTextViewEstadoCivil.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEstadoCivil);

    }

    private void popularCampoEscolaridadeComDB() {

        EscolaridadeController escolaridadeController = new EscolaridadeController();
        escolaridadeController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Escolaridade escolaridade = new Escolaridade();
                        escolaridade.setId(Integer.parseInt(object.getString("id")));
                        escolaridade.setNome(object.getString("nome"));

                        listaDeEscolaridadesRecuperadas.add(escolaridade);
                        configurarCampoEscolaridade(listaDeEscolaridadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void configurarCampoEscolaridade(List<Escolaridade> listaEscolaridadesRecuperadas) {

        ArrayAdapter<Escolaridade> adapterEscolaridade = new ArrayAdapter<Escolaridade>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEscolaridadesRecuperadas));
        autoCompleteTextViewEscolaridade.setAdapter(adapterEscolaridade);
        autoCompleteTextViewEscolaridade.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEscolaridade);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validarCadastroFuncionario() throws ParseException
    {
        if (
                FieldValidator.isFieldEmptyOrNull(textInputEditTextNomeCompleto, "NOME COMPLETO") &&
                FieldValidator.validarData(textInputEditTextDataDeNascimento, "DATA DE NASCIMENTO") &&
                FieldValidator.validarCpf(textInputEditTextCpf) &&
                FieldValidator.validarRadioGroup(radioGroupSexo, rbtnMasculino, "SEXO") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewUfNatal, listaDeUfsRecuperadas,
                        "O Campo UF é obrigatório.", "Insira uma opção de UF válida.") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewCidadeNatal, listaDeCidadesRecuperadas,
                        "O campo Cidade é obrigatório.", "Insira uma opção de Cidade válida.") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewEstadoCivil, listaDeEstadosCivisRecuperados,
                        "O campo Estado Civil é obrigatório.", "Insira uma opção de Estado Civil válida.") &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextNumeroDeFilhos, "NÚMERO DE FILHOS") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewEscolaridade, listaDeEscolaridadesRecuperadas,
                        "O campo Escolaridade é obrigatório.", "Insira uma opção de Escolaridade válida.") &&
                FieldValidator.validarTelefones(textInputEditTextTelefone, arrayListDeTelefonesAdicionados) &&
                FieldValidator.validarEmail(textInputEditTextEmail))
        {
            receberDadosFuncionarioPreenchidos();
            return true;
        }else { return false; }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void receberDadosFuncionarioPreenchidos() throws ParseException {
        nomeCompleto = textInputEditTextNomeCompleto.getText().toString().trim();
        dataNascimento = DateFormater.StringToLocalDate(textInputEditTextDataDeNascimento.getText().toString().trim());
        cpf = textInputEditTextCpf.getText().toString().trim();

        for (Estado estadoSelecionado : listaDeUfsRecuperadas) {
            if (estadoSelecionado.getNome().equals
                    (autoCompleteTextViewUfNatal.getText().toString().trim()))
                estado = estadoSelecionado;
        }

        for (Cidade cidadeSelecionada : listaDeCidadesRecuperadas) {
            if (cidadeSelecionada.getNome().equals
                    (autoCompleteTextViewCidadeNatal.getText().toString().trim()))
                naturalidade = cidadeSelecionada;
        }

        for (EstadoCivil estadoCivilSelecionado : listaDeEstadosCivisRecuperados)
        {
            if (estadoCivilSelecionado.getNome().equals
                    (autoCompleteTextViewEstadoCivil.getText().toString().trim()))
                estadoCivil = estadoCivilSelecionado;
        }

        numeroFilhos = Integer.valueOf(textInputEditTextNumeroDeFilhos.getText().toString().trim());


        for (Escolaridade escolaridadeSelecionada : listaDeEscolaridadesRecuperadas)
        {
            if (escolaridadeSelecionada.getNome().equals
                    (autoCompleteTextViewEscolaridade.getText().toString().trim())) {
                escolaridade = escolaridadeSelecionada;
            }
        }

        listaDeTelefonesAdicionados = arrayListDeTelefonesAdicionados;

        email = textInputEditTextEmail.getText().toString().trim();

        encapsularValoresParaEnvio();
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
        bundle.putSerializable("estadoCivil", estadoCivil);
        bundle.putInt("numeroFilhos", numeroFilhos);
        bundle.putSerializable("escolaridade", escolaridade);
        bundle.putSerializable("telefones", (Serializable) listaDeTelefonesAdicionados);
        bundle.putString("email", email);

        return bundle;
    }

    public void abrirProximaTela()
    {
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
    public void onStop()
    {
        listaDeUfsRecuperadas.clear();
        listaDeEstadosCivisRecuperados.clear();
        listaDeEscolaridadesRecuperadas.clear();
        super.onStop();
    }
}