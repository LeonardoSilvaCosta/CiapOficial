package com.br.ciapoficial.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.br.ciapoficial.R;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.Permissao;
import com.br.ciapoficial.helper.ValidarCPF;
import com.br.ciapoficial.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static com.br.ciapoficial.view.LoginActivity.fileName;

public class UserRegisterFragment1 extends Fragment {

    private LinearLayout linearLayoutTelefone;
    private TextInputEditText textInputEditTextNomeCompleto, textInputEditTextDataNascimento,
    textInputEditTextCpf, textInputEditTextTelefone, textInputEditTextEmail;
    private RadioGroup radioGroupSexo;
    private RadioButton rbtnMasculino, rbtnFeminino;
    private Button btnAdicionarTelefone, btnProxima;

    private ArrayList<String> arrayListTelefonesAdicionados = new ArrayList<>();
    private ArrayList<String> listaDeTelefonesAdicionados = new ArrayList<>();

    private UserRegisterFragment2 userRegisterFragment2;

    private String nomeCompleto, dataNascimento, cpf, sexo, email;

    SharedPreferences sharedPreferences;

    public UserRegisterFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_register1, container, false);

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);

        configurarComponentes(view);
        configurarMascaraData();
        configurarMascaraCpf();
        configurarMascaraTelefone();
        configurarCampoTelefone();
        definirComportamentoRadioButtons();
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

                AddRemoveTextView.adicionarTextViewTelefoneUsuario(getActivity(), textInputEditTextTelefone,
                        arrayListTelefonesAdicionados, linearLayoutTelefone);
            }
        });
    }

    private void definirComportamentoRadioButtons() {

        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnMasculino) {
                    sexo = "1";
                } else if (checkedId == R.id.rbtnFeminino) {
                    sexo = "2";
                }
            }
        });
    }

    private void receberDadosUsuarioPreenchidos()
    {
        nomeCompleto = textInputEditTextNomeCompleto.getText().toString();
        dataNascimento = textInputEditTextDataNascimento.getText().toString();
        cpf = textInputEditTextCpf.getText().toString();
        listaDeTelefonesAdicionados = arrayListTelefonesAdicionados;
        email = textInputEditTextEmail.getText().toString();
    }

    private boolean validarCadastroUsuario() {
        receberDadosUsuarioPreenchidos();
        Boolean validarCPF = ValidarCPF.validarCPF(textInputEditTextCpf.getText().toString());

        if (!TextUtils.isEmpty(nomeCompleto)) {

            if (!TextUtils.isEmpty(dataNascimento)) {

                if (dataNascimento.replaceAll("[/]", "").length() == 8) {

                    if (!TextUtils.isEmpty(cpf)) {

                        if (rbtnMasculino.isChecked() || rbtnFeminino.isChecked()) {

                            if (!TextUtils.isEmpty(sexo)) {

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
        bundle.putString("dataNascimento", dataNascimento);
        bundle.putString("cpf", cpf);
        bundle.putString("sexo", sexo);
        bundle.putStringArrayList("telefones", listaDeTelefonesAdicionados);
        bundle.putString("email", email);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCadastroUsuario())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    userRegisterFragment2 = new UserRegisterFragment2();
                    userRegisterFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, userRegisterFragment2);
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