package com.br.ciapoficial.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EscolaridadeController;
import com.br.ciapoficial.controller.EstadoCivilController;
import com.br.ciapoficial.controller.UfController;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.controller.VinculoController;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.TipoAtendido;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.Vinculo;
import com.br.ciapoficial.validation.FieldValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class UsuarioRegisterFragment1 extends Fragment {

    private UsuarioRegisterFragment2 usuarioRegisterFragment2;

    private LinearLayout linearLayoutConteudo, linearLayoutTelefone;
    private TextInputLayout textInputLayoutTitular, textInputLayoutVinculo, textInputLayoutDataNascimento;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextNumeroFilhos, textInputEditTextTelefone,
            textInputEditTextEmail;
    private AutoCompleteTextView autoCompleteTextViewUfNatal, autoCompleteTextViewCidadeNatal,
            autoCompleteTextViewEstadoCivil, autoCompleteTextViewEscolaridade,
            autoCompleteTextViewTitular, autoCompleteTextViewVinculo;
    private RadioGroup radioGroupSexo, radioGroupTipoAtendido;
    private RadioButton rbtnPm, rbtnDependente, rbtnCivil, rbtnMasculino, rbtnFeminino;
    private Button btnAdicionarTelefone, btnProxima;

    private List<Telefone> arrayListTelefoneAdicionados = new ArrayList<>();
    private List<Telefone> listaDeTelefoneAdicionados = new ArrayList<>();
    private List<Estado> listaUfsRecuperadas = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();
    private ArrayList<EstadoCivil> listaEstadosCivisRecuperados = new ArrayList<>();
    private ArrayList<Escolaridade> listaEscolaridadesRecuperadas = new ArrayList<>();
    private ArrayList<Usuario> listaTitularesRecuperados = new ArrayList<>();
    private ArrayList<Vinculo> listaVinculosRecuperados = new ArrayList<>();

    private String nomeCompleto, cpf, email;
    private LocalDate dataNascimento;
    private Estado estado;
    private Cidade naturalidade;
    private EstadoCivil estadoCivil = new EstadoCivil();
    private int numeroFilhos;
    private Escolaridade escolaridade = new Escolaridade();
    private SexoEnum sexo;

    private TipoAtendido tipoAtendido;
    private Usuario titular;
    private Vinculo vinculo;

    public UsuarioRegisterFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario_register1, container, false);

        configurarComponentes(view);
        configurarMascaraData();
        configurarMascaraCpf();
        configurarMascaraTelefone();
        configurarCampoDeTelefone();
        definirComportamentoRadioButtonsTipoAtendido();
        definirComportamentoRadioButtonsSexo();
        popularCampoUfNatalComDB();
        popularCampoCidadeComDB();
        popularCampoEstadoCivilComDB();
        popularCampoEscolaridadeComDB();
        popularCampoTitularComDB();
        popularCampoVinculoComDB();
        abrirProximaTela();

        return view ;
    }

    private void configurarComponentes(View view)
    {
        linearLayoutConteudo = view.findViewById(R.id.linearLayoutConteudo);
        linearLayoutTelefone = view.findViewById(R.id.linearLayoutTelefone);
        textInputLayoutVinculo = view.findViewById(R.id.textInputLayoutVinculo);
        textInputLayoutTitular = view.findViewById(R.id.textInputLayoutTitular);
        textInputEditTextNomeCompleto = view.findViewById(R.id.edtNomeCompleto);
        textInputLayoutDataNascimento = view.findViewById(R.id.textInputLayoutDataNascimento);
        textInputEditTextDataNascimento = view.findViewById(R.id.edtDataNascimento);
        textInputEditTextCpf = view.findViewById(R.id.edtCpf);
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo);
        radioGroupTipoAtendido = view.findViewById(R.id.radioGroupTipoUsuario);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);
        rbtnPm = view.findViewById(R.id.rbtnPm);
        rbtnDependente = view.findViewById(R.id.rbtnDependente);
        rbtnCivil = view.findViewById(R.id.rbtnCivil);
        autoCompleteTextViewUfNatal = view.findViewById(R.id.edtUf);
        autoCompleteTextViewCidadeNatal = view.findViewById(R.id.edtCidadeNatal);
        autoCompleteTextViewEstadoCivil = view.findViewById(R.id.edtEstadoCivil);
        textInputEditTextNumeroFilhos = view.findViewById(R.id.edtNumeroFilhos);
        autoCompleteTextViewEscolaridade = view.findViewById(R.id.edtEscolaridade);
        textInputEditTextTelefone = view.findViewById(R.id.edtTelefone);
        textInputEditTextEmail = view.findViewById(R.id.edtEmail);
        autoCompleteTextViewTitular = view.findViewById(R.id.edtTitular);
        autoCompleteTextViewVinculo = view.findViewById(R.id.edtVinculo);
        btnAdicionarTelefone = view.findViewById(R.id.btnAdicionarTelefone);
        btnProxima = view.findViewById(R.id.btnProxima);

        configurarVisibilidadeInicialDeCampos();
    }

    private void configurarVisibilidadeInicialDeCampos() {
        linearLayoutConteudo.setVisibility(View.GONE);
        textInputLayoutTitular.setVisibility(View.GONE);
        textInputLayoutVinculo.setVisibility(View.GONE);
        textInputLayoutVinculo.setVisibility(View.GONE);
    }

    private void configurarMascaraData()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaData(textInputEditTextDataNascimento); }

    private void configurarMascaraCpf()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaCpf(textInputEditTextCpf); }

    private void configurarMascaraTelefone()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaTelefone(textInputEditTextTelefone); }

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

        if(listaDeTelefoneAdicionados != null) {
            for(Telefone telefoneRecebido : listaDeTelefoneAdicionados) {
                String textoRecebido = telefoneRecebido.toString();
                criarTextViewParaTelefonesSelecionados(textoRecebido);
            }
        }

        btnAdicionarTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewTelefoneUsuarioString(getActivity(), textInputEditTextTelefone,
                        arrayListTelefoneAdicionados, linearLayoutTelefone);
            }
        });
    }

    private void definirComportamentoRadioButtonsTipoAtendido() {

        radioGroupTipoAtendido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnPm) {
                    tipoAtendido = TipoAtendido.PM;
                    linearLayoutConteudo.setVisibility(View.VISIBLE);
                    textInputLayoutTitular.setVisibility(View.GONE);
                    autoCompleteTextViewTitular.setVisibility(View.GONE);
                    autoCompleteTextViewTitular.setText("");
                    textInputLayoutVinculo.setVisibility(View.GONE);
                    autoCompleteTextViewVinculo.setVisibility(View.GONE);
                    autoCompleteTextViewVinculo.setText("");
                    textInputLayoutDataNascimento.setHint(getResources().getString(R.string.data_de_nascimento));
                } else if (checkedId == R.id.rbtnDependente) {
                    tipoAtendido = TipoAtendido.DEPENDENTE;
                    linearLayoutConteudo.setVisibility(View.VISIBLE);
                    textInputLayoutTitular.setVisibility(View.VISIBLE);
                    autoCompleteTextViewTitular.setVisibility(View.VISIBLE);
                    autoCompleteTextViewTitular.setText("");
                    textInputLayoutVinculo.setVisibility(View.VISIBLE);
                    autoCompleteTextViewVinculo.setVisibility(View.VISIBLE);
                    autoCompleteTextViewVinculo.setText("");
                    textInputLayoutDataNascimento.setHint(getResources().getString(R.string.data_de_nascimento_atendido_dependente_e_civil));
                }else if (checkedId == R.id.rbtnCivil) {
                    tipoAtendido = TipoAtendido.CIVIL;
                    linearLayoutConteudo.setVisibility(View.VISIBLE);
                    textInputLayoutTitular.setVisibility(View.GONE);
                    autoCompleteTextViewTitular.setVisibility(View.GONE);
                    autoCompleteTextViewTitular.setText("");
                    textInputLayoutVinculo.setVisibility(View.GONE);
                    autoCompleteTextViewVinculo.setVisibility(View.GONE);
                    autoCompleteTextViewVinculo.setText("");
                    textInputLayoutDataNascimento.setHint(getResources().getString(R.string.data_de_nascimento_atendido_dependente_e_civil));
                }
            }
        });
    }

    private void definirComportamentoRadioButtonsSexo() {

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
                        listaUfsRecuperadas.add(estado);
                        configurarCampoUfNatal(listaUfsRecuperadas);

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
                autoCompleteTextViewCidadeNatal, listaCidadesRecuperadas);

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

                        listaEstadosCivisRecuperados.add(estadoCivil);
                        configurarCampoEstadoCivil(listaEstadosCivisRecuperados);

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

                        listaEscolaridadesRecuperadas.add(escolaridade);
                        configurarCampoEscolaridade(listaEscolaridadesRecuperadas);

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

    private void popularCampoTitularComDB() {

        UsuarioController usuarioTitularController = new UsuarioController();
        usuarioTitularController.listar(getActivity(), new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GsonBuilder customGson = new GsonBuilder();
                        customGson.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                        customGson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                        Gson gson = customGson.create();
                        Usuario titular = gson.fromJson(String.valueOf(jsonObject), Usuario.class);

                        listaTitularesRecuperados.add(titular);
                        configurarCampoTitular(listaTitularesRecuperados);
                        Log.d("listaTit", listaTitularesRecuperados.toString());

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoTitular(ArrayList<Usuario> listaTitularesRecuperados) {

        ArrayAdapter<Usuario> adapterAtendidoTitular = new ArrayAdapter<Usuario>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaTitularesRecuperados));
        autoCompleteTextViewTitular.setAdapter(adapterAtendidoTitular);
        autoCompleteTextViewTitular.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewTitular);

    }

    private void popularCampoVinculoComDB() {

        VinculoController vinculoController = new VinculoController();
        vinculoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Vinculo vinculo = new Vinculo();
                        vinculo.setId(Integer.parseInt(object.getString("id")));
                        vinculo.setNome(object.getString("nome"));

                        listaVinculosRecuperados.add(vinculo);
                        configurarCampoVinculo(listaVinculosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoVinculo(ArrayList<Vinculo> listaVinculosRecuperados) {

        ArrayAdapter<Vinculo> adapterTipoVinculo = new ArrayAdapter<Vinculo>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaVinculosRecuperados));
        autoCompleteTextViewVinculo.setAdapter(adapterTipoVinculo);
        autoCompleteTextViewVinculo.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewVinculo);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validarCadastroUsuario() throws ParseException
    {
        if (
                FieldValidator.isFieldEmptyOrNull(textInputEditTextNomeCompleto, "NOME COMPLETO") &&
                FieldValidator.validarData(textInputEditTextDataNascimento, "DATA DE NASCIMENTO") &&
                FieldValidator.validarCpf(textInputEditTextCpf) &&
                FieldValidator.validarRadioGroup(radioGroupSexo, rbtnMasculino, "SEXO") &&
                FieldValidator.validarUF(autoCompleteTextViewUfNatal, listaUfsRecuperadas) &&
                FieldValidator.validarCidade(autoCompleteTextViewCidadeNatal, listaCidadesRecuperadas) &&
                FieldValidator.validarEstadoCivil(autoCompleteTextViewEstadoCivil, listaEstadosCivisRecuperados) &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextNumeroFilhos, "NÚMERO DE FILHOS") &&
                FieldValidator.validarEscolaridade(autoCompleteTextViewEscolaridade, listaEscolaridadesRecuperadas) &&
                FieldValidator.validarTelefones(textInputEditTextTelefone, arrayListTelefoneAdicionados) &&
                FieldValidator.validarEmail(textInputEditTextEmail) &&
                FieldValidator.validarTitular(autoCompleteTextViewTitular, listaTitularesRecuperados, rbtnDependente) &&
                FieldValidator.validarVinculo(autoCompleteTextViewVinculo, listaVinculosRecuperados, rbtnDependente))
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

        for (Estado estadoSelecionado : listaUfsRecuperadas) {
            if (estadoSelecionado.getNome().equals
                    (autoCompleteTextViewUfNatal.getText().toString().trim()))
                estado = estadoSelecionado;
        }

        for (Cidade cidadeSelecionada : listaCidadesRecuperadas) {
            if (cidadeSelecionada.getNome().equals
                    (autoCompleteTextViewCidadeNatal.getText().toString().trim()))
                naturalidade = cidadeSelecionada;
        }

        for (EstadoCivil estadoCivilSelecionado : listaEstadosCivisRecuperados)
        {
            if (estadoCivilSelecionado.getNome().equals
                    (autoCompleteTextViewEstadoCivil.getText().toString().trim()))
                estadoCivil = estadoCivilSelecionado;
        }

        numeroFilhos = Integer.valueOf(textInputEditTextNumeroFilhos.getText().toString().trim());


        for (Escolaridade escolaridadeSelecionada : listaEscolaridadesRecuperadas)
        {
            if (escolaridadeSelecionada.getNome().equals
                    (autoCompleteTextViewEscolaridade.getText().toString().trim())) {
                escolaridade = escolaridadeSelecionada;
            }
        }

        listaDeTelefoneAdicionados = arrayListTelefoneAdicionados;

        email = textInputEditTextEmail.getText().toString().trim();

        for(Usuario titularSelecionado: listaTitularesRecuperados)
        {
            if(titularSelecionado.toString().equals(autoCompleteTextViewTitular.getText().toString())) {
                titular = titularSelecionado;
            }
        }

        for(Vinculo vinculoSelecionado : listaVinculosRecuperados) {
            if(vinculoSelecionado.toString().equals(autoCompleteTextViewVinculo.getText().toString())) {
                vinculo = vinculoSelecionado;
            }
        }

        encapsularValoresParaEnvio();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bundle encapsularValoresParaEnvio()
    {
        Bundle bundle = new Bundle();

        bundle.putSerializable("tipoAtendido", tipoAtendido);
        bundle.putString("nomeCompleto", nomeCompleto);
        bundle.putString("dataNascimento", DateFormater.localDateToString(dataNascimento));
        bundle.putString("cpf", cpf);
        bundle.putSerializable("sexo", sexo);
        bundle.putSerializable("naturalidade", naturalidade);
        bundle.putSerializable("estadoCivil", estadoCivil);
        bundle.putInt("numeroFilhos", numeroFilhos);
        bundle.putSerializable("escolaridade", escolaridade);
        bundle.putSerializable("telefones", (Serializable) listaDeTelefoneAdicionados);
        bundle.putString("email", email);
        bundle.putSerializable("titular", titular);
        bundle.putSerializable("vinculo", vinculo);

        Toast.makeText(getContext(), titular.toString(), Toast.LENGTH_SHORT).show();

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SneakyThrows
            @Override
            public void onClick(View v) {

                if(validarCadastroUsuario())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    usuarioRegisterFragment2 = new UsuarioRegisterFragment2();
                    usuarioRegisterFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, usuarioRegisterFragment2);
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