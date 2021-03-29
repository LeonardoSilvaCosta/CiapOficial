package com.br.ciapoficial.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
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
import android.widget.Toast;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.AtendimentoController;
import com.br.ciapoficial.controller.DocumentoProduzidoController;
import com.br.ciapoficial.controller.MedicacaoPsiquiatricaController;
import com.br.ciapoficial.controller.ProcedimentoController;
import com.br.ciapoficial.controller.SinalSintomaController;
import com.br.ciapoficial.controller.EncaminhamentoController;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DataEntreJavaEMysql;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Atendimento;
import com.br.ciapoficial.model.in_atendimento.DocumentoProduzido;
import com.br.ciapoficial.model.in_atendimento.MedicacaoPsiquiatrica;
import com.br.ciapoficial.model.in_atendimento.Procedimento;
import com.br.ciapoficial.model.in_atendimento.SinalSintoma;
import com.br.ciapoficial.model.in_atendimento.Encaminhamento;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class AtendimentoRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;

    private LinearLayout linearLayoutDocumentoProduzido, linearLayoutEncaminhamento, linearLayoutSinalSintoma,
            linearLayoutMedicacaoPsiquiatrica;
    private AutoCompleteTextView autoCompleteTextViewProcedimento, autoCompleteTextViewDocumentoProduzido,
            autoCompleteTextViewEncaminhamento, autoCompleteTextViewSinalSintoma,
            autoCompleteTextViewMedicacaoPsiquiatrica;
    private TextInputEditText textInputEditTextEvolucao;
    private RadioGroup radioGroupAfastamento;
    private RadioButton rbtnAfastamentoSim, rbtnAfastamentoNao;
    private Button btnAdicionarDocumentoProduzido, btnAdicionarEncaminhamento, btnAdicionarSinalSintoma,
            btnAdicionarMedicacao, btnRegistrar;

    Atendimento atendimento;

    private ArrayList<Procedimento> listaProcedimentosRecuperados = new ArrayList<>();
    private ArrayList<DocumentoProduzido> listaDocumentosProduzidosRecuperados = new ArrayList<>();
    private ArrayList<Encaminhamento> listaDeEncaminhamentosRecuperados = new ArrayList<>();
    private ArrayList<SinalSintoma> listaSinaisSintomasRecuperados = new ArrayList<>();
    private ArrayList<MedicacaoPsiquiatrica> listaMedicacoesPsiquiatricasRecuperadas = new ArrayList<>();

    private ArrayList<DocumentoProduzido> arrayListDocumentosSelecionados;
    private ArrayList<SinalSintoma> arrayListSinaisSintomasSelecionados;
    private ArrayList<MedicacaoPsiquiatrica> arrayListMedicacoesSelecionadas;
    private ArrayList<Encaminhamento> arrayListEncaminhamentosSelecionados;
    private ArrayAdapter<DocumentoProduzido>  adapterDocumentos;
    private ArrayAdapter<Encaminhamento> adapterEncaminhamento;
    private ArrayAdapter<SinalSintoma> adapterSinaisSintomas;
    private ArrayAdapter<MedicacaoPsiquiatrica> adapterMedicacoes;
    private ArrayList<String> listaDeDocumentosSelecionados = new ArrayList<>();
    private ArrayList<String> listaDeEncaminhamentosSelecionados = new ArrayList<>();
    private ArrayList<String> listadeSinaisSintomasSelecionados = new ArrayList<>();
    private ArrayList<String> listaDeMedicacoesPsiquiatricasSelecionadas = new ArrayList<>();
    private String procedimento, evolucao;
    private boolean afastamento;

    public AtendimentoRegisterFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendimento_register3, container, false);

        configurarComponentes(view);
        enviarFormulario();
        return view;
    }

    private void configurarComponentes(View view)
    {
        linearLayoutDocumentoProduzido = view.findViewById(R.id.linearLayoutDocumentoProduzido);
        linearLayoutEncaminhamento = view.findViewById(R.id.linearLayoutEncaminhamento);
        linearLayoutSinalSintoma = view.findViewById(R.id.linearLayoutSinaisSintomas);
        linearLayoutMedicacaoPsiquiatrica = view.findViewById(R.id.linearLayoutMedicacao);
        autoCompleteTextViewProcedimento = view.findViewById(R.id.edtProcedimento);
        autoCompleteTextViewDocumentoProduzido = view.findViewById(R.id.edtDocumentoProduzido);
        autoCompleteTextViewEncaminhamento = view.findViewById(R.id.edtEncaminhamento);
        autoCompleteTextViewSinalSintoma = view.findViewById(R.id.edtSinaisSintomas);
        autoCompleteTextViewMedicacaoPsiquiatrica = view.findViewById(R.id.edtMedicacao);
        radioGroupAfastamento = view.findViewById(R.id.radioGroupAfastamento);
        rbtnAfastamentoSim = view.findViewById(R.id.rbtnAfastamentoSim);
        rbtnAfastamentoNao = view.findViewById(R.id.rbtnAfastamentoNao);
        textInputEditTextEvolucao = view.findViewById(R.id.edtEvolucao);
        btnAdicionarDocumentoProduzido = view.findViewById(R.id.btnAdicionarDocumentoProduzido);
        btnAdicionarEncaminhamento = view.findViewById(R.id.btnAdicionarEncaminhamento);
        btnAdicionarSinalSintoma = view.findViewById(R.id.btnAdicionarSinaisSintomas);
        btnAdicionarMedicacao = view.findViewById(R.id.btnAdicionarMedicacao);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);


        popularCampoProcedimentoComDB();
        popularCampoDocumentoProduzidoComDB();
        popularCampoEncaminhamentoComDB();
        popularCampoSinalSintomaComDB();
        popularCampoMedicacaoPsiquiatricaComDB();
        popularCampoMedicacaoPsiquiatricaComDB();
        definirComportamentoRadioButtons();
    }

    private Bundle recuperarDadosAtendimentoRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();
        return valoresRecebidosFragment1e2;
    }

    private void popularCampoProcedimentoComDB() {

        ProcedimentoController procedimentoController = new ProcedimentoController();
        procedimentoController.listarProcedimentos(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Procedimento procedimento = new Procedimento();
                            procedimento.setId(Integer.parseInt(object.getString("id")));
                            procedimento.setDescricao(object.getString("descricao"));

                            listaProcedimentosRecuperados.add(procedimento);

                            configurarCampoProcedimento(listaProcedimentosRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoProcedimento(ArrayList<Procedimento> listaProcedimentosRecuperados) {

        ArrayAdapter<Procedimento> adapterProcedimentos= new ArrayAdapter<Procedimento>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaProcedimentosRecuperados);
        autoCompleteTextViewProcedimento.setAdapter(adapterProcedimentos);
        autoCompleteTextViewProcedimento.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewProcedimento);


    }

    private void popularCampoDocumentoProduzidoComDB() {

        DocumentoProduzidoController documentoProduzidoController = new DocumentoProduzidoController();
        documentoProduzidoController.listarDocumentosProduzidos(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            DocumentoProduzido documentoProduzido = new DocumentoProduzido();
                            documentoProduzido.setId(Integer.parseInt(object.getString("id")));
                            documentoProduzido.setDescricao(object.getString("descricao"));

                            listaDocumentosProduzidosRecuperados.add(documentoProduzido);

                            configurarCampoDocumentoProduzido(listaDocumentosProduzidosRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDocumentoProduzido(ArrayList<DocumentoProduzido> listaDocumentosProduzidosRecuperados)
    {
        linearLayoutDocumentoProduzido.removeAllViews();

        adapterDocumentos= new ArrayAdapter<DocumentoProduzido>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDocumentosProduzidosRecuperados);
        autoCompleteTextViewDocumentoProduzido.setAdapter(adapterDocumentos);
        autoCompleteTextViewDocumentoProduzido.setThreshold(1);

        arrayListDocumentosSelecionados = new ArrayList<DocumentoProduzido>();
        adapterDocumentos = new ArrayAdapter<DocumentoProduzido>(getActivity(),
                android.R.layout.simple_list_item_1,
                arrayListDocumentosSelecionados);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDocumentoProduzido);

        gerenciarListaDeDocumentosSelecionados();

    }

    private void gerenciarListaDeDocumentosSelecionados()


    {
        btnAdicionarDocumentoProduzido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewDocumentoProduzido(getActivity(), autoCompleteTextViewDocumentoProduzido,
                        listaDocumentosProduzidosRecuperados, arrayListDocumentosSelecionados,
                        adapterDocumentos, linearLayoutDocumentoProduzido);
            }
        });
    }

    private void popularCampoEncaminhamentoComDB() {

        EncaminhamentoController encaminhamentoController = new EncaminhamentoController();
        encaminhamentoController.listarEncaminhamentos(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Encaminhamento encaminhamento = new Encaminhamento();
                            encaminhamento.setId(Integer.parseInt(object.getString("id")));
                            encaminhamento.setDestino(object.getString("destino"));
                            encaminhamento.setTipo(object.getString("tipo"));

                            listaDeEncaminhamentosRecuperados.add(encaminhamento);

                            configurarCampoEncaminhamento(listaDeEncaminhamentosRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoEncaminhamento(ArrayList<Encaminhamento> listaEncaminhamentosRecuperados) {

        linearLayoutEncaminhamento.removeAllViews();

        adapterEncaminhamento = new ArrayAdapter<Encaminhamento>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaEncaminhamentosRecuperados);
        autoCompleteTextViewEncaminhamento.setAdapter(adapterEncaminhamento);
        autoCompleteTextViewEncaminhamento.setThreshold(1);

        arrayListEncaminhamentosSelecionados = new ArrayList<Encaminhamento>();
        adapterEncaminhamento = new ArrayAdapter<Encaminhamento>(getActivity(),
                android.R.layout.simple_list_item_1,
                arrayListEncaminhamentosSelecionados);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEncaminhamento);

        gerenciarListaDeEncaminhamentosSelecionados();
    }

    private void gerenciarListaDeEncaminhamentosSelecionados()


    {
        btnAdicionarEncaminhamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewEncaminhamento(getActivity(), autoCompleteTextViewEncaminhamento,
                        listaDeEncaminhamentosRecuperados, arrayListEncaminhamentosSelecionados,
                        adapterEncaminhamento, linearLayoutEncaminhamento);
            }
        });
    }

    private void popularCampoSinalSintomaComDB() {

        SinalSintomaController sinalSintomaController = new SinalSintomaController();
        sinalSintomaController.listarSinaisSintomas(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            SinalSintoma sinalSintoma = new SinalSintoma();
                            sinalSintoma.setId(Integer.parseInt(object.getString("id")));
                            sinalSintoma.setDescricao(object.getString("descricao"));

                            listaSinaisSintomasRecuperados.add(sinalSintoma);

                            configurarCampoSinalSintoma(listaSinaisSintomasRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoSinalSintoma(ArrayList<SinalSintoma> listaSinaisSintomasRecuperados)
    {
        linearLayoutSinalSintoma.removeAllViews();

        adapterSinaisSintomas= new ArrayAdapter<SinalSintoma>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaSinaisSintomasRecuperados);
        autoCompleteTextViewSinalSintoma.setAdapter(adapterSinaisSintomas);
        autoCompleteTextViewSinalSintoma.setThreshold(1);

        arrayListSinaisSintomasSelecionados = new ArrayList<SinalSintoma>();
        adapterSinaisSintomas = new ArrayAdapter<SinalSintoma>(getActivity(),
                android.R.layout.simple_list_item_1,
                arrayListSinaisSintomasSelecionados);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewSinalSintoma);

        gerenciarListaDeSinaisSintomasSelecionados();
    }

    private void gerenciarListaDeSinaisSintomasSelecionados()
    {
        btnAdicionarSinalSintoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewSinalSintoma(getActivity(), autoCompleteTextViewSinalSintoma,
                        listaSinaisSintomasRecuperados, arrayListSinaisSintomasSelecionados,
                        adapterSinaisSintomas, linearLayoutSinalSintoma);
            }
        });
    }

    private void popularCampoMedicacaoPsiquiatricaComDB() {

        MedicacaoPsiquiatricaController medicacaoPsiquiatricaController = new MedicacaoPsiquiatricaController();
        medicacaoPsiquiatricaController.listarMedicacoesPsiquiatricas(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            MedicacaoPsiquiatrica medicacaoPsiquiatrica = new MedicacaoPsiquiatrica();
                            medicacaoPsiquiatrica.setId(Integer.parseInt(object.getString("id")));
                            medicacaoPsiquiatrica.setDescricao(object.getString("descricao"));

                            listaMedicacoesPsiquiatricasRecuperadas.add(medicacaoPsiquiatrica);

                            configurarCampoMedicacaoPsiquiatrica(listaMedicacoesPsiquiatricasRecuperadas);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoMedicacaoPsiquiatrica(ArrayList<MedicacaoPsiquiatrica> listaMedicacoesPsiquiatricasRecuperadas) {

        adapterMedicacoes = new ArrayAdapter<MedicacaoPsiquiatrica>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaMedicacoesPsiquiatricasRecuperadas);
        autoCompleteTextViewMedicacaoPsiquiatrica.setAdapter(adapterMedicacoes);
        autoCompleteTextViewMedicacaoPsiquiatrica.setThreshold(1);

        arrayListMedicacoesSelecionadas = new ArrayList<MedicacaoPsiquiatrica>();
        adapterMedicacoes = new ArrayAdapter<MedicacaoPsiquiatrica>(getActivity(),
                android.R.layout.simple_list_item_1,
                arrayListMedicacoesSelecionadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewMedicacaoPsiquiatrica);


        gerenciarListaDeMedicacoesSelecionadas();

    }

    private void gerenciarListaDeMedicacoesSelecionadas()
    {
        btnAdicionarMedicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewMedicacaoPsiquiatrica(getActivity(), autoCompleteTextViewMedicacaoPsiquiatrica,
                        listaMedicacoesPsiquiatricasRecuperadas, arrayListMedicacoesSelecionadas,
                        adapterMedicacoes, linearLayoutMedicacaoPsiquiatrica);
            }
        });
    }

    private void definirComportamentoRadioButtons() {

        radioGroupAfastamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnAfastamentoSim) {
                    afastamento = true;
                } else if (checkedId == R.id.rbtnAfastamentoNao) {
                    afastamento = false;
                }
            }
        });
    }

    private void receberDadosAtendimentoPreenchidos()
    {

        for(int i = 0; i < listaProcedimentosRecuperados.size(); i++)
        {
            Procedimento procedimentoSelecionado = listaProcedimentosRecuperados.get(i);
            if(procedimentoSelecionado.getDescricao().equals(autoCompleteTextViewProcedimento.getText().toString())) {
                procedimento = String.valueOf(procedimentoSelecionado.getId());
                break;
            }
        }

        for(int i = 0; i < arrayListDocumentosSelecionados.size(); i++) {
            DocumentoProduzido documentoProduzidoSelecionado = arrayListDocumentosSelecionados.get(i);
            listaDeDocumentosSelecionados.add(String.valueOf(documentoProduzidoSelecionado.getId()));
        }

        for(int i = 0; i < arrayListEncaminhamentosSelecionados.size(); i++) {
            Encaminhamento encaminhamentoSelecionado = arrayListEncaminhamentosSelecionados.get(i);
            listaDeEncaminhamentosSelecionados.add(String.valueOf(encaminhamentoSelecionado.getId()));
        }


        for(int i = 0; i < arrayListSinaisSintomasSelecionados.size(); i++) {
            SinalSintoma sinalSintomaSelecionado = arrayListSinaisSintomasSelecionados.get(i);
            listadeSinaisSintomasSelecionados.add(String.valueOf(sinalSintomaSelecionado.getId()));
        }

        for(int i = 0; i < arrayListMedicacoesSelecionadas.size(); i++) {
            MedicacaoPsiquiatrica medicaoPsiquiatricaSelecionada = arrayListMedicacoesSelecionadas.get(i);
            listaDeMedicacoesPsiquiatricasSelecionadas.add(String.valueOf(medicaoPsiquiatricaSelecionada.getId()));
        }

        evolucao = textInputEditTextEvolucao.getText().toString();
    }

    private boolean validarCadastroAtendimento() {
        receberDadosAtendimentoPreenchidos();

        if (!TextUtils.isEmpty(procedimento)) {

            if (!listaDeDocumentosSelecionados.isEmpty()) {

                if (!listaDeEncaminhamentosSelecionados.isEmpty()) {

                    if (!listadeSinaisSintomasSelecionados.isEmpty()) {

                        if (!listaDeMedicacoesPsiquiatricasSelecionadas.isEmpty()) {

                            if (rbtnAfastamentoSim.isChecked() || rbtnAfastamentoNao.isChecked()) {

                                if (!TextUtils.isEmpty(evolucao)) {

                                    encapsularValoresParaEnvio();
                                    return true;

                                }
                                else {
                                    Toast.makeText(getActivity(), "É necessário evoluir.",
                                            Toast.LENGTH_SHORT).show();
                                    textInputEditTextEvolucao.requestFocus();
                                    return false; }

                                }
                                else {
                                    Toast.makeText(getActivity(), "Selecione alguma opção em AFASTAMENTO.",
                                            Toast.LENGTH_SHORT).show();
                                    rbtnAfastamentoSim.requestFocusFromTouch();
                                    return false; }

                            }
                            else {
                            Toast.makeText(getActivity(),
                                    "É necessário adicionar ao menos um item em MEDICAÇÃO PSIQUIÁTRICA.",
                                    Toast.LENGTH_SHORT).show();
                                autoCompleteTextViewMedicacaoPsiquiatrica.requestFocus();
                                return false; }


                        }
                        else {
                        Toast.makeText(getActivity(),
                                "É necessário adicionar ao menos um item em SINAIS/SINTOMAS.",
                                Toast.LENGTH_SHORT).show();
                            autoCompleteTextViewSinalSintoma.requestFocus();
                            return false; }

                }
                else {
                    Toast.makeText(getActivity(),
                            "É necessário adicionar ao menos um item em ENCAMINHAMENTO.",
                            Toast.LENGTH_SHORT).show();
                    autoCompleteTextViewEncaminhamento.requestFocus();
                    return false; }

            }
            else {
                Toast.makeText(getActivity(),
                        "É necessário adicionar ao menos um item em DOCUMENTO PRODUZIDO.",
                        Toast.LENGTH_SHORT).show();
                autoCompleteTextViewDocumentoProduzido.requestFocus();
                return false; }

        }
        else {
            Toast.makeText(getActivity(),
                    "É necessário adicionar ao menos um item em PROCEDIMENTO.",
                    Toast.LENGTH_SHORT).show();
            autoCompleteTextViewProcedimento.requestFocus();
            return false; }
    }

    private Atendimento encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosAtendimentoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        atendimento = new Atendimento();

        atendimento.setData(DataEntreJavaEMysql.enviarDataParaMySqlComoString(valoresRecebidosFragment1.getString("data")));
        atendimento.setOficiaisResponsaveis(valoresRecebidosFragment1.getStringArrayList("listaOficiais"));
        atendimento.setAtendidos(valoresRecebidosFragment1.getStringArrayList("listaAtendidos"));
        atendimento.setUnidade(valoresRecebidosFragment1.getString("unidade"));
        atendimento.setModalidade((valoresRecebidosFragment1.getString("modalidade")));
        atendimento.setAcesso((valoresRecebidosFragment1.getString("acesso")));
        atendimento.setTipo((valoresRecebidosFragment1e2.getString("tipo")));
        atendimento.setTipoAvaliacao((valoresRecebidosFragment1e2.getString("tipoAvaliacao")));
        atendimento.setPrograma((valoresRecebidosFragment1e2.getString("programa")));
        atendimento.setDeslocamentos(valoresRecebidosFragment1e2.getStringArrayList("listaDeslocamentos"));
        atendimento.setDemandaGeral((valoresRecebidosFragment1e2.getString("demandaGeral")));
        atendimento.setDemandasEspecificas(valoresRecebidosFragment1e2.getStringArrayList("listaDemandasEspecificas"));
        atendimento.setCondicaoLaboral((valoresRecebidosFragment1e2.getString("condicaoLaboral")));
        atendimento.setProcedimento((procedimento));
        atendimento.setDocumentosProduzidos(listaDeDocumentosSelecionados);
        atendimento.setEncaminhamentos(listaDeEncaminhamentosSelecionados);
        atendimento.setSinaisSintomas(listadeSinaisSintomasSelecionados);
        atendimento.setMedicacoesPsiquiatricas(listaDeMedicacoesPsiquiatricasSelecionadas);
        atendimento.setAfastamento(afastamento);
        atendimento.setEvolucao(evolucao);

        return atendimento;
    }

    private void registrarAtendimento(Atendimento novoAtendimento)
    {
        new AtendimentoController().registrarAtendimento(
                getActivity(),
                novoAtendimento,
                new VolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        Log.e("ERRO", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro");

                            String mensagem = jsonObject.getString("mensagem");

                            if(isErro) {
                                Toast.makeText(principalFragment.getContext(),
                                        mensagem,
                                        Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(principalFragment.getContext(),
                                        mensagem,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void enviarFormulario() {

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCadastroAtendimento())
                {
                    Atendimento novoAtendimento;
                    novoAtendimento = encapsularValoresParaEnvio();

                    registrarAtendimento(novoAtendimento);

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
        });

    }

    @Override
    public void onResume() {

        listaProcedimentosRecuperados.clear();
        listaDocumentosProduzidosRecuperados.clear();
        listaDeEncaminhamentosRecuperados.clear();
        listaSinaisSintomasRecuperados.clear();
        listaMedicacoesPsiquiatricasRecuperadas.clear();
        super.onResume();
    }
}