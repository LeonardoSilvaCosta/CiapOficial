package com.br.ciapoficial.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EscolaridadeController;
import com.br.ciapoficial.controller.EstadoCivilController;
import com.br.ciapoficial.controller.EstadoController;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.helper.ValidarCPF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.EstadoCivil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AtendidoRegisterFragment1 extends Fragment {

    private TextInputLayout textInputLayoutVinculo, textInputLayoutDataNascimento;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextTelefone, textInputEditTextEmail,
            textInputEditTextNumeroFilhos, textInputEditTextVinculo;
    private AutoCompleteTextView autoCompleteTextViewEstadoCivil, autoCompleteTextViewUfNatal,
            autoCompleteTextViewcidadeNatal, autoCompleteTextViewEscolaridade;
    private RadioGroup radioGroupSexo, radioGroupTipoAtendido;
    private RadioButton rbtnPm, rbtnDependente, rbtnCivil, rbtnMasculino, rbtnFeminino;
    private Button btnProxima;

    private AtendidoRegisterFragment2 atendidoPmRegisterFragment2;

    private ArrayList<EstadoCivil> listaEstadosCivisRecuperados = new ArrayList<>();
    private ArrayList<Estado> listaEstadosRecuperados = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();
    private ArrayList<Escolaridade> listaEscolaridadesRecuperadas = new ArrayList<>();

    private String tipoAtendido;
    private String nomeCompleto;
    private String dataNascimento;
    private String cpf;
    private String sexo;
    private String email;
    private String telefone;
    private String estadoCivil;
    private String ufNatal;
    private String cidadeNatal;
    private String escolaridade;
    private String numeroFilhos;
    private String vinculo;

    private ArrayAdapter arrayAdapterChild;

    public AtendidoRegisterFragment1() {
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
        definirComportamentoRadioButtonsTipoAtendido();
        definirComportamentoRadioButtonsSexo();
        abrirProximaTela();

        return view ;
    }



    private void configurarComponentes(View view)
    {
        textInputLayoutVinculo = view.findViewById(R.id.textInputLayoutVinculo);
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
        autoCompleteTextViewcidadeNatal = view.findViewById(R.id.edtCidadeNatal);
        autoCompleteTextViewEscolaridade = view.findViewById(R.id.edtEscolaridade);
        textInputEditTextNumeroFilhos = view.findViewById(R.id.edtNumeroFilhos);
        textInputEditTextNumeroFilhos = view.findViewById(R.id.edtNumeroFilhos);
        textInputEditTextVinculo = view.findViewById(R.id.edtVinculo);
        btnProxima = view.findViewById(R.id.btnProxima);

        configurarInicialVisibilidadeDeCampos();
        popularCampoEstadoCivilComDB();
        popularCampoUfNatalComDB();
        popularCampoCidadeComDB();
        popularCampoEscolaridadeComDB();
    }

    private void configurarInicialVisibilidadeDeCampos() {
        textInputLayoutVinculo.setVisibility(View.GONE);
        textInputEditTextVinculo.setVisibility(View.GONE);
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

    private void definirComportamentoRadioButtonsTipoAtendido() {

        radioGroupTipoAtendido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnPm) {
                    tipoAtendido = "PM";
                    textInputLayoutVinculo.setVisibility(View.GONE);
                    textInputEditTextVinculo.setVisibility(View.GONE);
                    textInputEditTextVinculo.setText("");
                    textInputLayoutDataNascimento.setHint(getResources().getString(R.string.data_de_nascimento));
                } else if (checkedId == R.id.rbtnDependente) {
                    tipoAtendido = "Dependente";
                    textInputLayoutVinculo.setVisibility(View.VISIBLE);
                    textInputEditTextVinculo.setVisibility(View.VISIBLE);
                    textInputEditTextVinculo.setText("");
                    textInputLayoutDataNascimento.setHint(getResources().getString(R.string.data_de_nascimento_atendido_dependente_e_civil));
                }else if (checkedId == R.id.rbtnCivil) {
                    tipoAtendido = "Civil";
                    textInputLayoutVinculo.setVisibility(View.GONE);
                    textInputEditTextVinculo.setVisibility(View.GONE);
                    textInputEditTextVinculo.setText("");
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
                    sexo = "Masculino";
                } else if (checkedId == R.id.rbtnFeminino) {
                    sexo = "Feminino";
                }
            }
        });
    }

    private void popularCampoEstadoCivilComDB() {

        EstadoCivilController estadoCivilController = new EstadoCivilController();
        estadoCivilController.listarEstadosCivis(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            EstadoCivil estadoCivil = new EstadoCivil();
                            estadoCivil.setId(Integer.valueOf(object.getString("id")));
                            estadoCivil.setDescricao(object.getString("descricao"));

                            listaEstadosCivisRecuperados.add(estadoCivil);
                            configurarCampoEstadoCivil(listaEstadosCivisRecuperados);

                        }
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

    private void popularCampoUfNatalComDB() {

        EstadoController estadoController = new EstadoController();
        estadoController.listarUfs(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Estado estado = new Estado();
                            estado.setId(Integer.valueOf(object.getString("id")));
                            estado.setUf(object.getString("uf"));

                            listaEstadosRecuperados.add(estado);
                            configurarCampoEstadoNatal(listaEstadosRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEstadoNatal(ArrayList<Estado> listaEstadosRecuperados) {

        ArrayAdapter<Estado> adapterUf = new ArrayAdapter<Estado>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaEstadosRecuperados));
        autoCompleteTextViewUfNatal.setAdapter(adapterUf);
        autoCompleteTextViewUfNatal.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUfNatal);

    }

    private void popularCampoCidadeComDB() {

        MunicipioComBaseNaUF.mostrarMunicipioComBaseNaUf(getActivity(), autoCompleteTextViewUfNatal,
                autoCompleteTextViewcidadeNatal, listaCidadesRecuperadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewcidadeNatal);
    }

    private void popularCampoEscolaridadeComDB() {

        EscolaridadeController escolaridadeController = new EscolaridadeController();
        escolaridadeController.listarEscolaridades(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Escolaridade escolaridade = new Escolaridade();
                            escolaridade.setId(Integer.valueOf(object.getString("id")));
                            escolaridade.setDescricao(object.getString("descricao"));

                            listaEscolaridadesRecuperadas.add(escolaridade);
                            configurarCampoEscolaridade(listaEscolaridadesRecuperadas);

                        }
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


    private void receberDadosUsuarioPreenchidos()
    {
        nomeCompleto = textInputEditTextNomeCompleto.getText().toString();
        dataNascimento = textInputEditTextDataNascimento.getText().toString();
        cpf = textInputEditTextCpf.getText().toString();
        telefone = textInputEditTextTelefone.getText().toString();
        email = textInputEditTextEmail.getText().toString();
        estadoCivil = autoCompleteTextViewEstadoCivil.getText().toString();
        ufNatal = autoCompleteTextViewUfNatal.getText().toString();
        cidadeNatal = autoCompleteTextViewcidadeNatal.getText().toString();
        escolaridade = autoCompleteTextViewEscolaridade.getText().toString();
        numeroFilhos = (textInputEditTextNumeroFilhos.getText().toString());
        vinculo = textInputEditTextVinculo.getText().toString();
    }

    private boolean validarCadastroUsuario() {
        receberDadosUsuarioPreenchidos();
        Boolean validarCPF = ValidarCPF.validarCPF(textInputEditTextCpf.getText().toString());

        if(rbtnPm.isChecked() || rbtnDependente.isChecked() || rbtnCivil.isChecked()) {

            if (!TextUtils.isEmpty(nomeCompleto)) {

                if (!TextUtils.isEmpty(dataNascimento) || !tipoAtendido.equals("PM")) {

                    if (dataNascimento.replaceAll("[/]", "").length() == 8 || !tipoAtendido.equals("PM")) {

                        if (!TextUtils.isEmpty(cpf)) {

                            if (rbtnMasculino.isChecked() || rbtnFeminino.isChecked()) {

                                if (!TextUtils.isEmpty(sexo)) {

                                    if (!TextUtils.isEmpty(telefone)) {

                                        if (telefone.replaceAll("[(, ), -]", "").length() == 11) {

                                                if (validarCPF.equals(true)) {

                                                    if (!TextUtils.isEmpty(vinculo) || !tipoAtendido.equals("Dependente")) {

                                                        encapsularValoresParaEnvio();
                                                        return true;

                                                    }
                                                    else {
                                                        textInputEditTextVinculo.setError("O campo VÍNCULO é obrigatório!");
                                                        textInputEditTextVinculo.requestFocus();
                                                        return false; }

                                                }
                                                else {
                                                    textInputEditTextCpf.setError("CPF invalido!");
                                                    textInputEditTextCpf.requestFocus();
                                                    return false; }

                                        }
                                        else {
                                            textInputEditTextTelefone.setError("digite um número de telefone válido.");
                                            textInputEditTextTelefone.requestFocus();
                                            return false; }


                                    }
                                    else {
                                        textInputEditTextTelefone.setError("o campo TELEFONE deve ser preenchido.");
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
        else {
            Toast.makeText(getActivity(), "Selecione uma opção de TIPO DE ATENDIDO!", Toast.LENGTH_SHORT).show();
            rbtnPm.requestFocusFromTouch();
            return false; }
    }

    public Bundle encapsularValoresParaEnvio()
    {
        Bundle bundle = new Bundle();

        bundle.putString("tipoAtendido", tipoAtendido);
        bundle.putString("nomeCompleto", nomeCompleto);
        bundle.putString("dataNascimento", DataEntreJavaEMysql.enviarDataParaMySqlComoString(dataNascimento));
        bundle.putString("cpf", Mascaras.removerMascaras(cpf));
        bundle.putString("sexo", sexo);
        bundle.putString("telefone", Mascaras.removerMascaras(telefone));
        bundle.putString("email", email);
        bundle.putString("estadoCivil", estadoCivil);
        bundle.putString("ufNatal", ufNatal);
        bundle.putString("cidadeNatal", cidadeNatal);
        bundle.putString("escolaridade", escolaridade);
        bundle.putString("numeroFilhos", numeroFilhos);
        bundle.putString("vinculo", vinculo);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCadastroUsuario())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    atendidoPmRegisterFragment2 = new AtendidoRegisterFragment2();
                    atendidoPmRegisterFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, atendidoPmRegisterFragment2);
                    fragmentTransaction.addToBackStack(null).commit();
                }

            }
        });

    }


}