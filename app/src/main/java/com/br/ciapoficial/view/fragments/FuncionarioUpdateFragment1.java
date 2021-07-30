package com.br.ciapoficial.view.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.Constants;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EscolaridadeController;
import com.br.ciapoficial.controller.EstadoCivilController;
import com.br.ciapoficial.controller.FuncionarioController;
import com.br.ciapoficial.controller.UfController;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.FieldValidator;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.helper.Permissao;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.Telefone;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.SneakyThrows;

import static android.app.Activity.RESULT_OK;
import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;

public class FuncionarioUpdateFragment1 extends Fragment {

    private String[] permissoesNecessarias = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;

    private LinearLayout linearLayoutTelefone;
    private TextInputLayout textInputLayoutAtualizarSenha;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
            textInputEditTextCpf, textInputEditTextNumeroFilhos, textInputEditTextTelefone,
            textInputEditTextEmail, textInputEditTextSenha;
    private AutoCompleteTextView autoCompleteTextViewUfNatal, autoCompleteTextViewCidadeNatal,
            autoCompleteTextViewEstadoCivil, autoCompleteTextViewEscolaridade;
    private RadioGroup radioGroupSexo, radioGroupAtualizarSenha;
    private RadioButton rbtnMasculino, rbtnFeminino,
            rbtnAtualizarSenhaSim, rbtnAtualizarSenhaNao;

    private FuncionarioUpdateFragment2 funcionarioUpdateFragment2;
    private Button btnAdicionarTelefone, btnProxima;
    private CircleImageView fotoPerfil;
    private ImageButton imageButtonCamera, imageButtonGaleria;
    private String encodedImage;

    private List<Telefone> listaDeTelefonesAdicionados = new ArrayList<>();
    private List<Telefone> listaDeTelefonesProntosParaAtualizar = new ArrayList<>();
    private List<Telefone> listaDeTelefonesParaDeletar = new ArrayList<>();
    private List<TextView> listaDeTextView = new ArrayList<>();
    private List<Estado> listaDeUfsRecuperadas = new ArrayList<>();
    private List<Cidade> listaDeCidadesRecuperadas = new ArrayList<>();
    private List<EstadoCivil> listaDeEstadosCivisRecuperados = new ArrayList<>();
    private List<Escolaridade> listaDeEscolaridadesRecuperadas = new ArrayList<>();

    private String nomeCompleto, cpf, email, senha;
    private LocalDate dataNascimento;
    private Estado estado;
    private Cidade naturalidade;
    private EstadoCivil estadoCivil = new EstadoCivil();
    private Integer numeroFilhos;
    private Escolaridade escolaridade = new Escolaridade();
    private SexoEnum sexo;

    private Telefone telefone;
    private Funcionario funcionarioRecebidoDoDB;

    private SharedPreferences sharedPreferences;

    //adicionar Naturalidade, estado civil, número de filhos, escolaridade,

    public FuncionarioUpdateFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionario_update1, container, false);

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Permissao.validarPermissoes(permissoesNecessarias, getActivity(), 1);

        configurarComponentes(view);
        configurarMascaraParaData();
        configurarMascaraParaCpf();
        configurarMascaraParaTelefone();
        definirComportamentoDosRadioButtons();
        receberDadosFuncionarioPreviamentePreenchidos();
        popularCampoUfNatalComDB();
        popularCampoCidadeComDB();
        popularCampoEstadoCivilComDB();
        popularCampoEscolaridadeComDB();
        abrirCamera();
        abrirGaleria();
        abrirProximaTela();
        recuperarImagem();

        return view ;
    }

    private void configurarComponentes(View view)
    {
        fotoPerfil = view.findViewById(R.id.imgUsuario);
        imageButtonCamera = view.findViewById(R.id.imageButtonCamera);
        imageButtonGaleria = view.findViewById(R.id.imageButtonGaleria);
        linearLayoutTelefone = view.findViewById(R.id.linearLayoutTelefone);
        textInputLayoutAtualizarSenha = view.findViewById(R.id.textInputLayoutSenha);
        textInputEditTextNomeCompleto = view.findViewById(R.id.edtNomeCompleto);
        textInputEditTextDataNascimento = view.findViewById(R.id.edtDataNascimento);
        textInputEditTextCpf = view.findViewById(R.id.edtCpf);
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo);
        radioGroupAtualizarSenha = view.findViewById(R.id.radioGroupAtualizarSenha);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);
        rbtnAtualizarSenhaSim = view.findViewById(R.id.rbtnAtualiarSenhaSim);
        rbtnAtualizarSenhaNao = view.findViewById(R.id.rbtnAtualizarSenhaNao);
        autoCompleteTextViewUfNatal = view.findViewById(R.id.edtUf);
        autoCompleteTextViewCidadeNatal = view.findViewById(R.id.edtCidadeNatal);
        autoCompleteTextViewEstadoCivil = view.findViewById(R.id.edtEstadoCivil);
        textInputEditTextNumeroFilhos = view.findViewById(R.id.edtNumeroFilhos);
        autoCompleteTextViewEscolaridade = view.findViewById(R.id.edtEscolaridade);
        textInputEditTextTelefone = view.findViewById(R.id.edtTelefone);
        textInputEditTextEmail = view.findViewById(R.id.edtEmail);
        textInputEditTextSenha = view.findViewById(R.id.edtSenha);
        btnAdicionarTelefone = view.findViewById(R.id.btnAdicionarTelefone);
        btnProxima = view.findViewById(R.id.btnProxima);

        bloquearEditText();
        configurarRbtnAtualizarSenhaPadrao();
    }

    private void bloquearEditText() {
        textInputEditTextCpf.setFocusable(false);
        textInputEditTextCpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Campo não editável", Toast.LENGTH_SHORT).show();
            }
        });
        textInputEditTextEmail.setFocusable(false);
        textInputEditTextEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Campo não editável", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarRbtnAtualizarSenhaPadrao() {
        rbtnAtualizarSenhaNao.setChecked(true);
        textInputLayoutAtualizarSenha.setVisibility(View.GONE);
        textInputEditTextSenha.setVisibility(View.GONE);
    }

    private void configurarMascaraParaData()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaData(textInputEditTextDataNascimento);
    }

    private void configurarMascaraParaCpf()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaCpf(textInputEditTextCpf);
    }

    private void configurarMascaraParaTelefone()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaTelefone(textInputEditTextTelefone);
    }

    public void criarTextViewParaTelefonesSelecionados(String textoRecebido)
    {
        TextView textView = new TextView(getContext());
        textView.setPadding(0, 10, 0, 10);
        textView.setText(textoRecebido);
        textView.setTag("lista");

        linearLayoutTelefone.addView(textView);
        listaDeTextView.add(textView);

    }

    private void configurarCampoDeTelefone(List<Telefone> listaDeTelefonesAdd)
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

                AddRemoveTextView.adicionarTextViewTelefoneUsuarioObjeto(getActivity(), textInputEditTextTelefone,
                        listaDeTelefonesAdd, linearLayoutTelefone);

            }

        });

        AddRemoveTextView.removerItemDaListaDeTelefonesEListarParaDelecao(listaDeTextView, listaDeTelefonesAdd,
                listaDeTelefonesParaDeletar);
    }

    private void definirComportamentoDosRadioButtons() {

        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnMasculino) {
                    sexo = SexoEnum.MASCULINO;
                } else if (checkedId == R.id.rbtnFeminino) {
                    sexo = SexoEnum.FEMININO;
                }
            }
        });

        radioGroupAtualizarSenha.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbtnAtualiarSenhaSim) {
                    textInputLayoutAtualizarSenha.setVisibility(View.VISIBLE);
                    textInputEditTextSenha.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.rbtnAtualizarSenhaNao) {
                    textInputLayoutAtualizarSenha.setVisibility(View.GONE);
                    textInputEditTextSenha.setVisibility(View.GONE);

                    textInputEditTextSenha.setText("");
                }
            }
        });
    }

    private void receberDadosFuncionarioPreviamentePreenchidos() {

        FuncionarioController funcionarioController = new FuncionarioController();
        funcionarioController.recuperarUsuarioLogado(getActivity(), new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSucess(String response) {

                Log.d("testando", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    GsonBuilder customGson = new GsonBuilder();
                    customGson.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                    customGson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                    Gson gson = customGson.create();
                    Funcionario funcionario = gson.fromJson(String.valueOf(jsonObject), Funcionario.class);

                    if(funcionario != null) {

                        if(nomeCompleto == null) {
                            if(funcionario.getNomeCompleto() != null) {
                                textInputEditTextNomeCompleto.setText(funcionario.getNomeCompleto()); }
                        }
                        else{
                            textInputEditTextNomeCompleto.setText(nomeCompleto); }
                        if(dataNascimento == null) {
                            if(funcionario.getDataNascimento() != null) {
                                textInputEditTextDataNascimento.setText(DateFormater.localDateToString(funcionario.getDataNascimento())); }
                        }
                        else{
                            textInputEditTextDataNascimento.setText(DateFormater.localDateToString(dataNascimento));
                        }
                        textInputEditTextCpf.setText(funcionario.getCpf());
                        textInputEditTextEmail.setText(funcionario.getEmail());
                        if(estadoCivil.getNome() == null) {
                            if(funcionario.getEstadoCivil() != null) {
                                autoCompleteTextViewEstadoCivil.setText(funcionario.getEstadoCivil().toString()); }
                        }else{
                            autoCompleteTextViewEstadoCivil.setText(estadoCivil.toString()); }
                        if(numeroFilhos == null) {
                            textInputEditTextNumeroFilhos.setText(String.valueOf(funcionario.getNumeroFilhos()));
                        }else{
                            textInputEditTextNumeroFilhos.setText(String.valueOf(numeroFilhos)); }
                        if(escolaridade.getNome() == null) {
                            if(funcionario.getEscolaridade() != null) {
                                autoCompleteTextViewEscolaridade.setText(funcionario.getEscolaridade().toString()); }
                        }else{
                            autoCompleteTextViewEscolaridade.setText(escolaridade.toString()); }

                        if(estado == null) {
                            if(funcionario.getNaturalidade() != null) {
                                autoCompleteTextViewUfNatal.setText(
                                        funcionario.getNaturalidade().getEstado().getNome());
                                estado = funcionario.getNaturalidade().getEstado();
                            }else{
                                autoCompleteTextViewUfNatal.setText(naturalidade.getEstado().getNome());
                            }
                        }

                        if(naturalidade == null) {
                            if(funcionario.getNaturalidade() != null) {
                                autoCompleteTextViewCidadeNatal.setText(
                                        funcionario.getNaturalidade().toString());
                                naturalidade = funcionario.getNaturalidade();

                                listaDeCidadesRecuperadas.add(funcionario.getNaturalidade());
                            }else{
                                autoCompleteTextViewCidadeNatal.setText(naturalidade.getNome());
                            }
                        }

                        if(listaDeTelefonesProntosParaAtualizar.isEmpty()) {
                            if(funcionario.getTelefones() != null) {
                                listaDeTelefonesAdicionados = funcionario.getTelefones();
                            }
                        }else{
                            listaDeTelefonesAdicionados = listaDeTelefonesProntosParaAtualizar;
                        }

                        configurarCampoDeTelefone(listaDeTelefonesAdicionados);

                        funcionarioRecebidoDoDB = funcionario;

                        if(sexo == null) {
                            if(funcionario.getSexo() != null) {
                                if(funcionario.getSexo().getNome().equals("Masculino")) {

                                    rbtnMasculino.setChecked(true);
                                }else {
                                    rbtnFeminino.setChecked(true);
                                }
                            }

                        }else{
                            if(sexo.getNome().equals("Masculino")){
                                rbtnMasculino.setChecked(true);
                            }else{
                                rbtnFeminino.setChecked(true);
                            }
                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
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

    public void configurarCampoUfNatal(List<Estado> listaEstadosRecuperados) {

        ArrayAdapter<Estado> adapterUf = new ArrayAdapter<Estado>(getActivity(),
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
                        FieldValidator.validarTelefones(textInputEditTextTelefone, listaDeTelefonesAdicionados) &&
                        FieldValidator.validarEmail(textInputEditTextEmail) &&
                        FieldValidator.validarSenhaUpdate(textInputEditTextSenha, rbtnAtualizarSenhaSim.isChecked()))
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

        numeroFilhos = Integer.valueOf(textInputEditTextNumeroFilhos.getText().toString().trim());


        for (Escolaridade escolaridadeSelecionada : listaDeEscolaridadesRecuperadas)
        {
            if (escolaridadeSelecionada.getNome().equals
                    (autoCompleteTextViewEscolaridade.getText().toString().trim())) {
                escolaridade = escolaridadeSelecionada;
            }
        }

        listaDeTelefonesProntosParaAtualizar = listaDeTelefonesAdicionados;

        email = textInputEditTextEmail.getText().toString().trim();
        senha = textInputEditTextSenha.getText().toString().trim();

        encapsularValoresParaEnvio();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bundle encapsularValoresParaEnvio()
    {
        Bundle bundle = new Bundle();

        bundle.putInt("id", funcionarioRecebidoDoDB.getId());
        bundle.putString("nomeCompleto", nomeCompleto);
        bundle.putString("dataNascimento", DateFormater.localDateToString(dataNascimento));
        bundle.putString("cpf", cpf);
        bundle.putSerializable("sexo", sexo);
        bundle.putSerializable("naturalidade", naturalidade);
        bundle.putSerializable("estadoCivil", estadoCivil);
        bundle.putInt("numeroFilhos", numeroFilhos);
        bundle.putSerializable("escolaridade", escolaridade);
        bundle.putSerializable("telefones", (Serializable) listaDeTelefonesProntosParaAtualizar);
        bundle.putSerializable("telefonesParaDeletar", (Serializable) listaDeTelefonesParaDeletar);
        bundle.putString("email", email);
        bundle.putString("novaSenha", senha);
        bundle.putSerializable("funcionarioRecebidoDoDB", funcionarioRecebidoDoDB);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(validarCadastroFuncionario())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    funcionarioUpdateFragment2 = new FuncionarioUpdateFragment2();
                    funcionarioUpdateFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, funcionarioUpdateFragment2);
                    fragmentTransaction.addToBackStack(null).commit();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults) {

            if(permissaoResultado == getActivity().getPackageManager().PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permissões negadas.");
        builder.setMessage("Para utilziar o app, é necessário aceitar as permissões.");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void abrirCamera() {
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivityForResult(i, SELECAO_CAMERA);
                }

            }
        });

    }

    private void abrirGaleria() {

        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void uploadImage() {
        FuncionarioController funcionarioController = new FuncionarioController();
        funcionarioController.carregarImagemUsuario(getActivity(), encodedImage, new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean isErro = jsonObject.getBoolean("erro");

                    if(isErro) {
                        Toast.makeText(getActivity(),
                                "Falha ao realizar apload",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getActivity(),
                                "Upload realizado com sucesso",
                                Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        });
    }

    private void recuperarImagem() {

        FuncionarioController funcionarioController = new FuncionarioController();
        Funcionario funcionario = new Funcionario();
        //
        funcionarioController.recuperarImagem(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                String emailUsuarioLogado = sharedPreferences.getString("userEmail", "");

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucesso = jsonObject.getString("success");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(sucesso.equals("1")) {

                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            String email = object.getString("email");

                            if(email.equals(emailUsuarioLogado)) {

                                String imagemUrl = object.getString("imagem");

                                String url = Constants.URLUsuarios + "/Images/"+imagemUrl;


                                Glide.with(getActivity()).
                                        load(url).
                                        centerCrop().
                                        placeholder(R.drawable.ic_launcher_foreground)
                                        .into(fotoPerfil);

                                break;
                            }


                        }

                    }

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK) {

            Bitmap imagem = null;

            try {
                switch (requestCode) {
                    case SELECAO_CAMERA:

                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), localImagemSelecionada);
                        break;
                }

                if(imagem != null) {

                    fotoPerfil.setImageBitmap(imagem);

                    imageStore(imagem);
                    uploadImage();
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        listaDeUfsRecuperadas.clear();
        listaDeEstadosCivisRecuperados.clear();
        listaDeEscolaridadesRecuperadas.clear();
        super.onStop();
    }
}