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
import com.br.ciapoficial.controller.EscolaridadeController;
import com.br.ciapoficial.controller.EstadoCivilController;
import com.br.ciapoficial.controller.UfController;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.FieldValidator;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Uf;
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
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextNumeroFilhos, textInputEditTextTelefone,
            textInputEditTextEmail;
    private AutoCompleteTextView autoCompleteTextViewUfNatal, autoCompleteTextViewCidadeNatal,
            autoCompleteTextViewEstadoCivil, autoCompleteTextViewEscolaridade;
    private RadioGroup radioGroupSexo;
    private RadioButton rbtnMasculino, rbtnFeminino;
    private Button btnAdicionarTelefone, btnProxima;

    private List<Telefone> arrayListDeTelefonesAdicionados = new ArrayList<>();
    private List<Telefone> listaDeTelefonesAdicionados = new ArrayList<>();
    private List<Uf> listaDeUfsRecuperadas = new ArrayList<>();
    private List<Cidade> listaDeCidadesRecuperadas = new ArrayList<>();
    private List<EstadoCivil> listaDeEstadosCivisRecuperados = new ArrayList<>();
    private List<Escolaridade> listaDeEscolaridadesRecuperadas = new ArrayList<>();

    private String nomeCompleto, cpf, email;
    private LocalDate dataNascimento;
    private Uf uf;
    private Cidade naturalidade;
    private EstadoCivil estadoCivil = new EstadoCivil();
    private int numeroFilhos;
    private Escolaridade escolaridade = new Escolaridade();
    private SexoEnum sexo;

    //Ajustar os valores recebidos (eliminar espaços, se for o caso settar como uper ou lowercase
    //Data de nascimento ter boa regra para validação de data ok
    //Resgatar Uf no campo naturalidade e cidade de residência ok

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

    public void configurarComponentes(View view)
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

    public void configurarMascaraParaData()
    {Mascaras.criarMascaraParaData(textInputEditTextDataNascimento);}

    public void configurarMascaraParaCpf()
    {Mascaras.criarMascaraParaCpf(textInputEditTextCpf);}

    public void configurarMascaraParaTelefone()
    {Mascaras.criarMascaraParaTelefone(textInputEditTextTelefone);}

    public void criarTextViewParaTelefonesSelecionados(String textoRecebido)
    {
        TextView textView = new TextView(getContext());
        textView.setPadding(0, 10, 0, 10);
        textView.setText(textoRecebido);
        textView.setTag("lista");

        linearLayoutTelefone.addView(textView);

    }

    public void configurarCampoDeTelefone()
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

    public void definirComportamentoDosRadioButtons() {

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

    public void popularCampoUfNatalComDB()
    {
        UfController ufController = new UfController();
        ufController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Uf uf = new Uf();
                        uf.setId(Integer.parseInt(object.getString("id")));
                        uf.setNome(object.getString("nome"));
                        listaDeUfsRecuperadas.add(uf);
                        configurarCampoEstadoNatal(listaDeUfsRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void configurarCampoEstadoNatal(List<Uf> listaEstadosRecuperados) {

        ArrayAdapter<Uf> adapterUf = new ArrayAdapter<Uf>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosRecuperados));
        autoCompleteTextViewUfNatal.setAdapter(adapterUf);
        autoCompleteTextViewUfNatal.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUfNatal);

    }

    public void popularCampoCidadeComDB()
    {
        MunicipioComBaseNaUF.mostrarMunicipioComBaseNaUf(getActivity(), autoCompleteTextViewUfNatal,
                autoCompleteTextViewCidadeNatal, listaDeCidadesRecuperadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCidadeNatal);
    }

    public void popularCampoEstadoCivilComDB() {

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

    public void configurarCampoEstadoCivil(List<EstadoCivil> listaEstadosCivisRecuperados) {

        ArrayAdapter<EstadoCivil> adapterEstadoCivil = new ArrayAdapter<EstadoCivil>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosCivisRecuperados));
        autoCompleteTextViewEstadoCivil.setAdapter(adapterEstadoCivil);
        autoCompleteTextViewEstadoCivil.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEstadoCivil);

    }

    public void popularCampoEscolaridadeComDB() {

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
                FieldValidator.validarNomeCompleto(textInputEditTextNomeCompleto) &&
                        FieldValidator.validarDataDeNascimento(textInputEditTextDataNascimento) &&
                        FieldValidator.validarCpf(textInputEditTextCpf) &&
                        FieldValidator.validarSexo(radioGroupSexo, rbtnMasculino) &&
                        FieldValidator.validarUf(autoCompleteTextViewUfNatal, listaDeUfsRecuperadas) &&
                        FieldValidator.validarCidade(autoCompleteTextViewCidadeNatal, listaDeCidadesRecuperadas) &&
                        FieldValidator.validarEstadoCivil(autoCompleteTextViewEstadoCivil, listaDeEstadosCivisRecuperados) &&
                        FieldValidator.validarNumeroDeFilhos(textInputEditTextNumeroFilhos) &&
                        FieldValidator.validarEscolaridade(autoCompleteTextViewEscolaridade, listaDeEscolaridadesRecuperadas) &&
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
        dataNascimento = DateFormater.StringToLocalDate(textInputEditTextDataNascimento.getText().toString().trim());
        cpf = textInputEditTextCpf.getText().toString().trim();

        for (Uf ufSelecionada : listaDeUfsRecuperadas) {
            if (ufSelecionada.getNome().equals
                    (autoCompleteTextViewUfNatal.getText().toString().trim()))
                uf = ufSelecionada;
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

        numeroFilhos = Integer.valueOf(textInputEditTextNumeroFilhos.getText().toString().trim());


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