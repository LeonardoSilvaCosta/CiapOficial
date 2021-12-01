package com.br.ciapoficial.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.UfController;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.TipoAtendido;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.validation.FieldValidator;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Escolaridade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.EstadoCivil;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.Vinculo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class UsuarioRegisterFragment2 extends Fragment {

    private PrincipalFragment principalFragment;
    private UsuarioRegisterFragment3 usuarioRegisterFragment3;

    private TextInputLayout textInputLayoutUf, textInputLayoutCidade;
    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
    private TextInputEditText textInputEditTextCep, textInputEditTextBairro,
            textInputEditTextLogradouro, textInputEditTextNumero;
    Button btnProxima;

    private ArrayList<Estado> listaEstadosRecuperados = new ArrayList<>();
    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();

    private Endereco endereco = new Endereco();

    private TipoAtendido tipoAtendido;

    //Usar API de CEP para settar dados de endereço com mais facilidade

    public UsuarioRegisterFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario_register2, container, false);

        Bundle getTipoAtendidoFromFragment1 = this.getArguments();
        tipoAtendido = (TipoAtendido) getTipoAtendidoFromFragment1.getSerializable("tipoAtendido");

        configurarComponentes(view);
        configurarMascaraCep();
        alternarHintCampoUf();
        popularCampoUfComDB();
        popularCampoCidadeComDB();
        abrirProximaTela();
        return view;
    }

    private void configurarComponentes(View view) {
        textInputLayoutUf = view.findViewById(R.id.textInputLayoutUf);
        textInputLayoutCidade = view.findViewById(R.id.textInputLayoutCidadeNatal);
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

    private Bundle recuperarDadosUsuarioRegisterFragment1() {
        Bundle valoresRecebidosFragment1 = this.getArguments();

        return valoresRecebidosFragment1;

    }

    private void alternarHintCampoUf() {
        if (tipoAtendido.equals(TipoAtendido.PM)) {
            textInputLayoutUf.setHint(getResources().getString(R.string.estado));
            textInputLayoutCidade.setHint(getResources().getString(R.string.cidade));
        } else {
            textInputLayoutUf.setHint(getResources().getString(R.string.uf_dependente_civil));
            textInputLayoutCidade.setHint(getResources().getString(R.string.cidade_dependente_civil));
        }
    }

    private void configurarMascaraCep() {
        Mascaras mascaras = new Mascaras();
        mascaras.criarMascaraParaCep(textInputEditTextCep);
    }

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroUsuario() throws ParseException {

        //Aqui há validação diferente dependendo do tipo do atendido
        //Se for PM, preenche todos os campos, caso seja dependente ou civil, os seguintes campos não
        //são obrigatórios:
        // CEP, Cidade, Bairro, Logradouro e Número, ou seja, nenhum campo de endereço.

        if (
                FieldValidator.validarCep(textInputEditTextCep) &&
                FieldValidator.validarUF(autoCompleteTextViewUf, listaEstadosRecuperados) &&
                FieldValidator.validarCidade(autoCompleteTextViewCidade, listaCidadesRecuperadas) &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextBairro, "BAIRRO") &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextLogradouro, "LOGRADOURO") &&
                FieldValidator.isFieldEmptyOrNull(textInputEditTextNumero, "NÚMERO"))
        {
            receberDadosUsuarioPreenchidos();
            return true;
        }else { return false; }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void receberDadosUsuarioPreenchidos() throws ParseException {
        endereco.setCep(Mascaras.removerMascaras(textInputEditTextCep.getText().toString()));

        for(int i = 0; i < listaCidadesRecuperadas.size(); i++) {
            Cidade cidadeSelecionada = listaCidadesRecuperadas.get(i);
            if(cidadeSelecionada.getNome().equals(autoCompleteTextViewCidade.getText().toString())) {
                endereco.setCidade(cidadeSelecionada);
            }
        }

        endereco.setBairro(textInputEditTextBairro.getText().toString());
        endereco.setLogradouro(textInputEditTextLogradouro.getText().toString());
        endereco.setNumero(Integer.parseInt(textInputEditTextNumero.getText().toString()));

    }

    private Bundle encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1 =  recuperarDadosUsuarioRegisterFragment1();
        Bundle bundle = new Bundle();

        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
        bundle.putSerializable("endereco", (endereco));

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
                    if(tipoAtendido.equals(tipoAtendido.PM))
                    {

                        Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                        usuarioRegisterFragment3 = new UsuarioRegisterFragment3();
                        usuarioRegisterFragment3.setArguments(valoresEncapsuladosParaEnvio);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameConteudo, usuarioRegisterFragment3);
                        fragmentTransaction.addToBackStack(null).commit();
                    }
                    else
                    {
                        Usuario novoUsuario;
                        novoUsuario = encapsularValoresParaCadastro();
                        cadastrarUsuario(novoUsuario);

                        principalFragment = new PrincipalFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i)
                        {
                            fragmentManager.popBackStack();
                        }
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameConteudo, principalFragment);
                        fragmentTransaction.commit();
                    }

                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Usuario encapsularValoresParaCadastro() throws ParseException {
        Bundle valoresRecebidosFragment1 = recuperarDadosUsuarioRegisterFragment1();

       Usuario usuario = new Usuario();
       Usuario titular = new Usuario();

       LocalDate dataNascimento = DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("dataNascimento"));

        usuario.setNomeCompleto(valoresRecebidosFragment1.getString("nomeCompleto"));
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf(valoresRecebidosFragment1.getString("cpf"));
        usuario.setSexo((SexoEnum) valoresRecebidosFragment1.getSerializable("sexo"));
        usuario.setTelefones((ArrayList<Telefone>) valoresRecebidosFragment1.getSerializable("telefones"));
        usuario.setEmail(valoresRecebidosFragment1.getString("email"));
        usuario.setEstadoCivil((EstadoCivil) valoresRecebidosFragment1.getSerializable("estadoCivil"));
        usuario.setNaturalidade((Cidade) valoresRecebidosFragment1.getSerializable("cidadeNatal"));
        usuario.setEscolaridade((Escolaridade) valoresRecebidosFragment1.getSerializable("escolaridade"));
        usuario.setNumeroFilhos((valoresRecebidosFragment1.getInt("numeroFilhos")));
        usuario.setTitular(titular);
        usuario.setTipoVinculo((Vinculo) valoresRecebidosFragment1.getSerializable("vinculo"));

        usuario.setEndereco(endereco);

        return usuario;
    }

    private void cadastrarUsuario(Usuario novoUsuario)
    {
        new UsuarioController().cadastrar(
                getActivity(),
                novoUsuario,
                new IVolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(principalFragment.getContext(),
                                    "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onResume() {

        listaCidadesRecuperadas.clear();
        listaEstadosRecuperados.clear();
        super.onResume();
    }


}