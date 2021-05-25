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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.EstadoController;
import com.br.ciapoficial.controller.FuncionarioController;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.helper.MunicipioComBaseNaUF;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Estado;
import com.br.ciapoficial.model.Funcionario;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.br.ciapoficial.view.LoginActivity.fileName;

public class UserUpdateFragment2 extends Fragment {
//
//    private UserUpdateFragment3 userUpdateFragment3;
//    private AutoCompleteTextView autoCompleteTextViewUf, autoCompleteTextViewCidade;
//    private TextInputEditText textInputEditTextCep, textInputEditTextBairro, textInputEditTextLogradouro,
//            textInputEditTextNumero;
//    private ArrayList<Estado> listaEstadosRecuperados = new ArrayList<>();
//    private ArrayList<Cidade> listaCidadesRecuperadas = new ArrayList<>();
//
//    Button btnProxima;
//
//    private String cep;
//    private String uf;
//    private String cidade;
//    private String bairro;
//    private String logradouro;
//    private String numero;
//
//    SharedPreferences sharedPreferences;
//
//    public UserUpdateFragment2() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_user_update2, container, false);
//
//        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
//
//        configurarComponentes(view);
//        configurarMascaraCep();
//        popularCampoUfComDB();
//        popularCampoCidadeComDB();
//        receberDadosUsuarioPreviamentePreenchidos();
//        abrirProximaTela();
//        return view;
//    }
//
//    private void configurarComponentes(View view) {
//        textInputEditTextCep = view.findViewById(R.id.edtCep);
//        autoCompleteTextViewUf = view.findViewById(R.id.edtUf);
//        autoCompleteTextViewCidade = view.findViewById(R.id.edtCidade);
//        textInputEditTextBairro = view.findViewById(R.id.edtBairro);
//        textInputEditTextLogradouro = view.findViewById(R.id.edtLogradouro);
//        textInputEditTextNumero = view.findViewById(R.id.edtNumero);
//        btnProxima = view.findViewById(R.id.btnProxima);
//    }
//
//    private void chamarViaCep() {
//
//    }
//
//    private Bundle recuperarDadosUserRegisterFragment1() {
//        Bundle valoresRecebidosFragment1 = this.getArguments();
//
//        return valoresRecebidosFragment1;
//
//    }
//
//    private void configurarMascaraCep() {
//        Mascaras mascaras = new Mascaras();
//        mascaras.criarMascaraCep(textInputEditTextCep);
//    }
//
//    private void receberDadosUsuarioPreviamentePreenchidos() {
//
//        String emailUsuarioLogado = sharedPreferences.getString("userEmail", "");
//
//        FuncionarioController funcionarioController = new FuncionarioController();
//        funcionarioController.recuperarUsuarioLogado(getActivity(), emailUsuarioLogado, new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            String email = object.getString("email");
//
//                            if(email.equals(emailUsuarioLogado)) {
//
//                                Funcionario funcionario = new Funcionario();
//                                funcionario.setCep(object.getString("cep"));
//                                funcionario.setUf(object.getString("uf"));
//                                funcionario.setCidade(object.getString("cidade"));
//                                funcionario.setBairro(object.getString("bairro"));
//                                funcionario.setLogradouro(object.getString("logradouro"));
//                                funcionario.setNumero(object.getString("numero"));
//
//                                textInputEditTextCep.setText(funcionario.getCep());
//                                autoCompleteTextViewUf.setText(funcionario.getUf());
//                                autoCompleteTextViewCidade.setText(funcionario.getCidade());
//                                textInputEditTextBairro.setText(funcionario.getBairro());
//                                textInputEditTextLogradouro.setText(funcionario.getLogradouro());
//                                textInputEditTextNumero.setText(funcionario.getNumero());
//
//
//                            }
//
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void popularCampoUfComDB() {
//
//        EstadoController estadoController = new EstadoController();
//        estadoController.listar(getActivity(), new VolleyCallback() {
//            @Override
//            public void onSucess(String response) {
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    if(success.equals("1")){
//                        for(int i = 0; i < jsonArray.length(); i++) {
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            Estado estado = new Estado();
//                            estado.setId(Integer.valueOf(object.getString("id")));
//                            estado.setUf(object.getString("uf"));
//
//                            listaEstadosRecuperados.add(estado);
//                            configurarCampoEstado(listaEstadosRecuperados);
//
//                        }
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//    private void configurarCampoEstado(ArrayList<Estado> listaEstadosRecuperados) {
//
//        ArrayAdapter<Estado> adapterUf = new ArrayAdapter<Estado>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                (listaEstadosRecuperados));
//        autoCompleteTextViewUf.setAdapter(adapterUf);
//        autoCompleteTextViewUf.setThreshold(1);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUf);
//
//    }
//
//    private void popularCampoCidadeComDB() {
//
//        MunicipioComBaseNaUF.mostrarMunicipioComBaseNaUf(getActivity(), autoCompleteTextViewUf,
//                autoCompleteTextViewCidade, listaCidadesRecuperadas);
//
//        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCidade);
//    }
//
//    private void receberDadosUsuarioPreenchidos() {
//        cep = textInputEditTextCep.getText().toString();
//
//        for(int i = 0; i < listaEstadosRecuperados.size(); i++) {
//            Estado estadoSelecionado = listaEstadosRecuperados.get(i);
//            if(estadoSelecionado.getUf().equals(autoCompleteTextViewUf.getText().toString())) {
//                uf = String.valueOf(estadoSelecionado.getId());
//            }
//        }
//
//        for(int i = 0; i < listaCidadesRecuperadas.size(); i++) {
//            Cidade cidadeSelecionada = listaCidadesRecuperadas.get(i);
//            if(cidadeSelecionada.getDescricao().equals(autoCompleteTextViewCidade.getText().toString())) {
//                cidade = String.valueOf(cidadeSelecionada.getId());
//            }
//        }
//        bairro = textInputEditTextBairro.getText().toString();
//        logradouro = textInputEditTextLogradouro.getText().toString();
//        numero = textInputEditTextNumero.getText().toString();
//    }
//
//    private boolean validarCadastroUsuario() {
//        receberDadosUsuarioPreenchidos();
//
//        if (!TextUtils.isEmpty(cep)) {
//
//            if (!TextUtils.isEmpty(uf)) {
//
//                if (!TextUtils.isEmpty(cidade)) {
//
//                    if (!TextUtils.isEmpty(bairro)) {
//
//                        if (!TextUtils.isEmpty(logradouro)) {
//
//                            if (!TextUtils.isEmpty(numero)) {
//
//                                return true;
//                            }
//                            else {
//                                textInputEditTextNumero.setError("O campo NUÚMERO é obrigatório!");
//                                textInputEditTextNumero.requestFocus();
//                                return false; }
//
//                        }
//                        else {
//                            textInputEditTextLogradouro.setError("O campo LOGRADOURO é obrigatório!");
//                            textInputEditTextLogradouro.requestFocus();
//                            return false; }
//
//                    }
//                    else {
//                        textInputEditTextBairro.setError("O campo BAIRRO é obrigatório!");
//                        textInputEditTextBairro.requestFocus();
//                        return false; }
//
//                }
//                else {
//                    autoCompleteTextViewCidade.setError("O campo CIDADE é obrigatório!.");
//                    autoCompleteTextViewCidade.requestFocus();
//                    return false; }
//
//            }
//            else {
//                autoCompleteTextViewUf.setError("O campo UF é obrigatório!");
//                autoCompleteTextViewUf.requestFocus();
//                return false; }
//
//        }
//        else {
//            textInputEditTextCep.setError("O campo CEP é obrigatório!");
//            textInputEditTextCep.requestFocus();
//            return false; }
//    }
//
//    private Bundle encapsularValoresParaEnvio()
//    {
//        Bundle valoresRecebidosFragment1 =  recuperarDadosUserRegisterFragment1();
//        Bundle bundle = new Bundle();
//
//        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
//        bundle.putString("cep", cep);
//        bundle.putString("uf", uf);
//        bundle.putString("cidade", cidade);
//        bundle.putString("bairro", bairro);
//        bundle.putString("logradouro", logradouro);
//        bundle.putString("numero", numero);
//
//        return bundle;
//    }
//
//    private void abrirProximaTela() {
//
//        btnProxima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(validarCadastroUsuario())
//                {
//                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();
//
//                    userUpdateFragment3 = new UserUpdateFragment3();
//                    userUpdateFragment3.setArguments(valoresEncapsuladosParaEnvio);
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frameConteudo, userUpdateFragment3);
//                    fragmentTransaction.addToBackStack(null).commit();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void onResume() {
//
//        listaCidadesRecuperadas.clear();
//        listaEstadosRecuperados.clear();
//        super.onResume();
//    }
}