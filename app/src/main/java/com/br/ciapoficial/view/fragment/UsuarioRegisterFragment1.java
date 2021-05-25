package com.br.ciapoficial.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.controller.EscolaridadeController;
import com.br.ciapoficial.controller.EstadoCivilController;
import com.br.ciapoficial.controller.EstadoController;
import com.br.ciapoficial.controller.VinculoController;
import com.br.ciapoficial.enums.EscolaridadeEnum;
import com.br.ciapoficial.enums.EstadoCivilEnum;
import com.br.ciapoficial.enums.UfEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.TipoAtendido;
import com.br.ciapoficial.enums.TipoVinculoEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.helper.ValidarCPF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Telefone;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import lombok.SneakyThrows;

public class UsuarioRegisterFragment1 extends Fragment {

    private LinearLayout linearLayoutConteudo, linearLayoutTelefone;
    private TextInputLayout textInputLayoutTitular, textInputLayoutVinculo, textInputLayoutDataNascimento;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextTelefone, textInputEditTextEmail,
            textInputEditTextNumeroFilhos;
    private AutoCompleteTextView autoCompleteTextViewEstadoCivil, autoCompleteTextViewUfNatal,
            autoCompleteTextViewCidadeNatal, autoCompleteTextViewEscolaridade,
            autoCompleteTextViewTitular, autoCompleteTextViewVinculo;
    private RadioGroup radioGroupSexo, radioGroupTipoAtendido;
    private RadioButton rbtnPm, rbtnDependente, rbtnCivil, rbtnMasculino, rbtnFeminino;
    private Button btnAdicionarTelefone, btnProxima;

    private ArrayList<Telefone> arrayListTelefonesAdicionados = new ArrayList<>();
    private ArrayList<Telefone> listaDeTelefonesAdicionados = new ArrayList<>();

    private UsuarioRegisterFragment2 atendidoPmRegisterFragment2;

    private ArrayList<UfEnum> listaUfsRecuperadas = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();
    private ArrayList<EstadoCivilEnum> listaEstadosCivisRecuperados = new ArrayList<>();
    private ArrayList<EscolaridadeEnum> listaEscolaridadesRecuperadas = new ArrayList<>();
    private ArrayList<Usuario> listaTitularesRecuperados = new ArrayList<>();
    private ArrayList<TipoVinculoEnum> listaVinculosRecuperados = new ArrayList<>();

    private TipoAtendido tipoAtendido;
    private String nomeCompleto;
    private Date dataNascimento;
    private String cpf;
    private SexoEnum sexoEnum;
    private String email;
    private Telefone telefone;
    private EstadoCivilEnum estadoCivilEnum;
    private Cidade naturalidade;
    private EscolaridadeEnum escolaridadeEnum;
    private int numeroFilhos;
    private Usuario titular;
    private TipoVinculoEnum vinculoEnum;

    private ArrayAdapter arrayAdapterChild;

    public UsuarioRegisterFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendido_register1, container, false);

        configurarComponentes(view);
        configurarMascaraData();
        configurarMascaraCpf();
        configurarMascaraTelefone();
        configurarCampoTelefone();
        definirComportamentoRadioButtonsTipoAtendido();
        definirComportamentoRadioButtonsSexo();
        abrirProximaTela();

        return view ;
    }

    private void configurarComponentes(View view)
    {
        linearLayoutConteudo = view.findViewById(R.id.linearLayoutConteudo);
        linearLayoutConteudo.setVisibility(View.GONE);

        linearLayoutTelefone = view.findViewById(R.id.linearLayoutTelefone);
        textInputLayoutVinculo = view.findViewById(R.id.textInputLayoutVinculo);
        textInputLayoutTitular = view.findViewById(R.id.textInputLayoutTitular);
        textInputLayoutDataNascimento = view.findViewById(R.id.textInputLayoutDataNascimento);
        textInputEditTextNomeCompleto = view.findViewById(R.id.edtNomeCompleto);
        textInputEditTextDataNascimento = view.findViewById(R.id.edtDataNascimento);
        textInputEditTextCpf = view.findViewById(R.id.edtCpf);
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo);
        radioGroupTipoAtendido = view.findViewById(R.id.radioGroupTipoAtendido);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);
        rbtnPm = view.findViewById(R.id.rbtnPm);
        rbtnDependente = view.findViewById(R.id.rbtnDependente);
        rbtnCivil = view.findViewById(R.id.rbtnCivil);
        textInputEditTextTelefone = view.findViewById(R.id.edtTelefone);
        textInputEditTextEmail = view.findViewById(R.id.edtEmail);
        autoCompleteTextViewEstadoCivil = view.findViewById(R.id.edtEstadoCivil);
        autoCompleteTextViewUfNatal = view.findViewById(R.id.edtUfNatal);
        autoCompleteTextViewCidadeNatal = view.findViewById(R.id.edtCidadeNatal);
        autoCompleteTextViewEscolaridade = view.findViewById(R.id.edtEscolaridade);
        textInputEditTextNumeroFilhos = view.findViewById(R.id.edtNumeroFilhos);
        autoCompleteTextViewTitular = view.findViewById(R.id.edtTitular);
        autoCompleteTextViewVinculo = view.findViewById(R.id.edtVinculo);
        btnAdicionarTelefone = view.findViewById(R.id.btnAdicionarTelefone);
        btnProxima = view.findViewById(R.id.btnProxima);

        configurarVisibilidadeInicialDeCampos();
        popularCampoUfNatalComDB();
        popularCampoCidadeComDB();
        popularCampoEstadoCivilComDB();
        popularCampoEscolaridadeComDB();
        popularCampoTitularComDB();
        popularCampoVinculoComDB();
    }

    private void configurarVisibilidadeInicialDeCampos() {
        textInputLayoutTitular.setVisibility(View.GONE);
        textInputLayoutVinculo.setVisibility(View.GONE);
        textInputLayoutVinculo.setVisibility(View.GONE);
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
                    sexoEnum = SexoEnum.MASCULINO;
                } else if (checkedId == R.id.rbtnFeminino) {
                    sexoEnum = SexoEnum.FEMININO;
                }
            }
        });
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

    private void popularCampoTitularComDB() {

        UsuarioController usuarioTitularController = new UsuarioController();
        usuarioTitularController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                   JSONArray jsonArray = new JSONArray(response);

                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Usuario titular = new Usuario();
                            titular = (Usuario) object.get("titular");

                            listaTitularesRecuperados.add(titular);
                            configurarCampoTitular(listaTitularesRecuperados);

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
        vinculoController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            TipoVinculoEnum tipoVinculo = (TipoVinculoEnum) object.get("tipoVinculo");

                            listaVinculosRecuperados.add(vinculoEnum);
                            configurarCampoVinculo(listaVinculosRecuperados);

                        }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoVinculo(ArrayList<TipoVinculoEnum> listaVinculosRecuperados) {

        ArrayAdapter<TipoVinculoEnum> adapterTipoVinculo = new ArrayAdapter<TipoVinculoEnum>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaVinculosRecuperados));
        autoCompleteTextViewVinculo.setAdapter(adapterTipoVinculo);
        autoCompleteTextViewVinculo.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewVinculo);

    }


    private void receberDadosUsuarioPreenchidos() throws ParseException {
        nomeCompleto = textInputEditTextNomeCompleto.getText().toString();
        dataNascimento = DateFormater.StringToDate(textInputEditTextDataNascimento.getText().toString());
        cpf = Mascaras.removerMascaras(textInputEditTextCpf.getText().toString());
        listaDeTelefonesAdicionados = arrayListTelefonesAdicionados;
        email = textInputEditTextEmail.getText().toString();
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
        for(int i = 0; i < listaEscolaridadesRecuperadas.size(); i++) {
            EscolaridadeEnum escolaridadeSelecionada = listaEscolaridadesRecuperadas.get(i);
            if(escolaridadeSelecionada.getNome().equals(autoCompleteTextViewEscolaridade.getText().toString())) {
                escolaridadeEnum = escolaridadeSelecionada;
            }
        }

        numeroFilhos = Integer.valueOf(textInputEditTextNumeroFilhos.getText().toString());

        for(int i = 0; i < listaTitularesRecuperados.size(); i++) {
            Usuario titularSelecionado = listaTitularesRecuperados.get(i);
            if(titularSelecionado.toString().equals(autoCompleteTextViewTitular.getText().toString())) {
                titular = titularSelecionado;
            }
        }

        for(int i = 0; i < listaVinculosRecuperados.size(); i++) {
            TipoVinculoEnum vinculoSelecionado = listaVinculosRecuperados.get(i);
            if(vinculoSelecionado.toString().equals(autoCompleteTextViewVinculo.getText().toString())) {
                vinculoEnum = vinculoSelecionado;
            }
        }

    }

    private boolean validarCadastroUsuario() throws ParseException {
        receberDadosUsuarioPreenchidos();
        Boolean validarCPF = ValidarCPF.validarCPF(textInputEditTextCpf.getText().toString());

        if(rbtnPm.isChecked() || rbtnDependente.isChecked() || rbtnCivil.isChecked()) {

            if (!TextUtils.isEmpty(nomeCompleto)) {

                if (dataNascimento != null || !tipoAtendido.equals("PM")) {

//                    if (dataNascimento.replaceAll("[/]", "").length() == 8 || !tipoAtendido.equals("PM")) {

                        if (!TextUtils.isEmpty(cpf)) {

                            if (rbtnMasculino.isChecked() || rbtnFeminino.isChecked()) {

                                if (!TextUtils.isEmpty(sexoEnum.getNome())) {

                                    if (!listaDeTelefonesAdicionados.isEmpty()) {

                                        if (validarCPF.equals(true)) {

                                            if(titular != null || !tipoAtendido.equals("Dependente")) {

                                                if (!TextUtils.isEmpty(vinculoEnum.getNome()) || !tipoAtendido.equals("Dependente")) {

                                                    encapsularValoresParaEnvio();
                                                    return true;

                                                }
                                                else {
                                                    autoCompleteTextViewVinculo.setError("O campo VÍNCULO é obrigatório!");
                                                    autoCompleteTextViewVinculo.requestFocus();
                                                    return false; }

                                            }
                                            else {
                                                autoCompleteTextViewVinculo.setError("O campo TITULAR é obrigatório!");
                                                autoCompleteTextViewVinculo.requestFocus();
                                                return false; }

                                        }
                                        else {
                                            textInputEditTextCpf.setError("CPF invalido!");
                                            textInputEditTextCpf.requestFocus();
                                            return false; }

                                    }
                                    else {
                                        textInputEditTextTelefone.setError("É necessário adicionar ao menos um telefone.");
                                        textInputEditTextTelefone.requestFocus();
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
                                rbtnMasculino.requestFocusFromTouch();
                                return false; }

                        }
                        else {
                            textInputEditTextCpf.setError("O campo CPF é obrigatório!");
                            textInputEditTextCpf.requestFocus();
                            return false; }

//                    }
//                    else {
//                        textInputEditTextDataNascimento.setError("Digite uma data válida.");
//                        textInputEditTextDataNascimento.requestFocus();
//                        return false; }

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
        else {
            Toast.makeText(getActivity(), "Selecione uma opção de TIPO DE ATENDIDO!", Toast.LENGTH_SHORT).show();
            rbtnPm.requestFocusFromTouch();
            return false; }
    }

    public Bundle encapsularValoresParaEnvio()
    {
        Bundle bundle = new Bundle();

        bundle.putSerializable("tipoAtendido", tipoAtendido);
        bundle.putString("nomeCompleto", nomeCompleto);
        bundle.putString("dataNascimento", dataNascimento.toString());
        bundle.putString("cpf", cpf);
        bundle.putSerializable("sexo", sexoEnum);
        bundle.putSerializable("telefones", listaDeTelefonesAdicionados);
        bundle.putString("email", email);
        bundle.putSerializable("estadoCivil", estadoCivilEnum);
        bundle.putSerializable("cidadeNatal", naturalidade);
        bundle.putSerializable("escolaridade", escolaridadeEnum);
        bundle.putInt("numeroFilhos", numeroFilhos);
        bundle.putSerializable("titular", titular);
        bundle.putSerializable("vinculo", vinculoEnum);

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

                    atendidoPmRegisterFragment2 = new UsuarioRegisterFragment2();
                    atendidoPmRegisterFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, atendidoPmRegisterFragment2);
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