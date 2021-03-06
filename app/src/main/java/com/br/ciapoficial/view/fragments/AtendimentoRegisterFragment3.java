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
import com.br.ciapoficial.enums.TipoServicoEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Especialista;
import com.br.ciapoficial.model.Unidade;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.in_servico.Acesso;
import com.br.ciapoficial.model.in_servico.Atendimento;
import com.br.ciapoficial.model.in_servico.Avaliacao;
import com.br.ciapoficial.model.in_servico.CondicaoLaboral;
import com.br.ciapoficial.model.in_servico.DemandaEspecifica;
import com.br.ciapoficial.model.in_servico.DemandaGeral;
import com.br.ciapoficial.model.in_servico.Deslocamento;
import com.br.ciapoficial.model.in_servico.DocumentoProduzido;
import com.br.ciapoficial.model.in_servico.Encaminhamento;
import com.br.ciapoficial.model.in_servico.Modalidade;
import com.br.ciapoficial.model.in_servico.Procedimento;
import com.br.ciapoficial.model.in_servico.Programa;
import com.br.ciapoficial.model.in_servico.ServicoDeAssistenciaEspecial;
import com.br.ciapoficial.model.in_servico.TipoAvaliacao;
import com.br.ciapoficial.model.in_servico.TipoServico;
import com.br.ciapoficial.network.AtendimentoController;
import com.br.ciapoficial.network.AvaliacaoController;
import com.br.ciapoficial.network.DocumentoProduzidoController;
import com.br.ciapoficial.network.EncaminhamentoController;
import com.br.ciapoficial.network.ProcedimentoController;
import com.br.ciapoficial.network.ServicoDeAssistenciaEspecialController;
import com.br.ciapoficial.validation.FieldValidator;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import lombok.SneakyThrows;

public class AtendimentoRegisterFragment3 extends Fragment {

    private PrincipalFragment principalFragment;

    private LinearLayout linearLayoutDocumentoProduzido, linearLayoutEncaminhamento;
    private AutoCompleteTextView autoCompleteTextViewProcedimento, autoCompleteTextViewDocumentoProduzido,
            autoCompleteTextViewEncaminhamento;
    private TextInputEditText textInputEditTextEvolucao;
    private RadioGroup radioGroupAfastamento, radioGroupEncaminhanhamento;
    private RadioButton rbtnAfastamentoSim, rbtnAfastamentoNao, rbtnEncaminhamentoInterno,
            rbtnEncaminhamentoExterno;
    private Button btnAdicionarDocumentoProduzido, btnAdicionarEncaminhamento, btnRegistrar;

    private List<Procedimento> listaProcedimentosRecuperados = new ArrayList<>();
    private List<DocumentoProduzido> listaDocumentosProduzidosRecuperados = new ArrayList<>();
    private List<Encaminhamento> listaDeEncaminhamentosRecuperados = new ArrayList<>();

    private List<DocumentoProduzido> listaDeDocumentosSelecionadosNaoValidados;
    private List<Encaminhamento> listaDeEncaminhamentosSelecionadosNaoValidados = new ArrayList<>();
    private ArrayAdapter<DocumentoProduzido>  adapterDocumentos;
    private ArrayAdapter<Encaminhamento> adapterEncaminhamento;

    private List<DocumentoProduzido> listaDeDocumentosSelecionadosValidados = new ArrayList<>();
    private List<Encaminhamento> listaDeEncaminhamentosSelecionadosValidados = new ArrayList<>();

    private Atendimento atendimento;
    private Avaliacao avaliacao;
    private ServicoDeAssistenciaEspecial sae;

    private Procedimento procedimento;
    private String evolucao;
    private boolean afastamento;

    public AtendimentoRegisterFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendimento_register3, container, false);

        configurarComponentes(view);
        definirComportamentoRadioButtons();

        criarTextViewParaDocumentosProduzidosSelecionados();
        criarTextViewParaEncaminhamentosSelecionados();

        popularCampoProcedimentoComDB();
        popularCampoDocumentoProduzidoComDB();
        popularCampoEncaminhamentoComDB();

        enviarFormulario();
        return view;
    }

    private void configurarComponentes(View view)
    {
        linearLayoutDocumentoProduzido = view.findViewById(R.id.linearLayoutDocumentoProduzido);
        linearLayoutEncaminhamento = view.findViewById(R.id.linearLayoutEncaminhamento);
        autoCompleteTextViewProcedimento = view.findViewById(R.id.edtProcedimento);
        autoCompleteTextViewDocumentoProduzido = view.findViewById(R.id.edtDocumentoProduzido);
        autoCompleteTextViewEncaminhamento = view.findViewById(R.id.edtEncaminhamento);
        radioGroupAfastamento = view.findViewById(R.id.radioGroupAfastamento);
        rbtnAfastamentoSim = view.findViewById(R.id.rbtnAfastamentoSim);
        rbtnAfastamentoNao = view.findViewById(R.id.rbtnAfastamentoNao);
        textInputEditTextEvolucao = view.findViewById(R.id.edtEvolucao);
        btnAdicionarDocumentoProduzido = view.findViewById(R.id.btnAdicionarDocumentoProduzido);
        btnAdicionarEncaminhamento = view.findViewById(R.id.btnAdicionarEncaminhamento);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
    }

    private Bundle recuperarDadosAtendimentoRegisterFragment2() {
        Bundle valoresRecebidosFragment1e2 = this.getArguments();
        return valoresRecebidosFragment1e2;
    }

    private void criarTextViewParaDocumentosProduzidosSelecionados()
    {
        TextView textView = new TextView(getContext());
        for(DocumentoProduzido documentoRecebido : listaDeDocumentosSelecionadosValidados) {
            String textoRecebido = documentoRecebido.toString();

            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayoutDocumentoProduzido.addView(textView);
        }
        removerItemDaListaDeDocumentos(textView, listaDeDocumentosSelecionadosNaoValidados, adapterDocumentos);
    }

    private static void removerItemDaListaDeDocumentos(TextView textView, List<DocumentoProduzido> listaDisplay,
                                                       ArrayAdapter<DocumentoProduzido> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<DocumentoProduzido> iter = listaDisplay.iterator(); iter.hasNext();)
                {
                    DocumentoProduzido deslocamentoSelecionado = iter.next();
                    if(deslocamentoSelecionado.toString().equals(string))
                    {
                        listaDisplay.remove(deslocamentoSelecionado);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    private void criarTextViewParaEncaminhamentosSelecionados()
    {
        TextView textView = new TextView(getContext());
        for(Encaminhamento encaminhamentoRecebido : listaDeEncaminhamentosSelecionadosValidados) {
            String textoRecebido = encaminhamentoRecebido.toString();

            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayoutEncaminhamento.addView(textView);
        }
        removerItemDaListaDeEncaminhamentos(textView, listaDeEncaminhamentosSelecionadosNaoValidados, adapterEncaminhamento);
    }

    private static void removerItemDaListaDeEncaminhamentos(TextView textView, List<Encaminhamento> listaDisplay,
                                                            ArrayAdapter<Encaminhamento> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<Encaminhamento> iter = listaDisplay.iterator(); iter.hasNext();)
                {
                    Encaminhamento encaminhamentoSelecionado = iter.next();
                    if(encaminhamentoSelecionado.toString().equals(string))
                    {
                        listaDisplay.remove(encaminhamentoSelecionado);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;
                    }
                }
            }
        });
    }

    private void popularCampoProcedimentoComDB() {
        ProcedimentoController procedimentoController = new ProcedimentoController();
        procedimentoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Procedimento procedimento = new Procedimento();
                        procedimento.setId(Integer.parseInt(object.getString("id")));
                        procedimento.setNome(object.getString("nome"));

                        listaProcedimentosRecuperados.add(procedimento);
                        configurarCampoProcedimento(listaProcedimentosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoProcedimento(List<Procedimento> listaProcedimentosRecuperados) {

        ArrayAdapter<Procedimento> adapterProcedimentos= new ArrayAdapter<Procedimento>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaProcedimentosRecuperados);
        autoCompleteTextViewProcedimento.setAdapter(adapterProcedimentos);
        autoCompleteTextViewProcedimento.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewProcedimento);


    }

    private void popularCampoDocumentoProduzidoComDB() {

        DocumentoProduzidoController documentoProduzidoController = new DocumentoProduzidoController();
        documentoProduzidoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        DocumentoProduzido documentoProduzido = new DocumentoProduzido();
                        documentoProduzido.setId(Integer.parseInt(object.getString("id")));
                        documentoProduzido.setNome(object.getString("nome"));

                        listaDocumentosProduzidosRecuperados.add(documentoProduzido);
                        configurarCampoDocumentoProduzido(listaDocumentosProduzidosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDocumentoProduzido(List<DocumentoProduzido> listaDocumentosProduzidosRecuperados)
    {
        linearLayoutDocumentoProduzido.removeAllViews();

        adapterDocumentos= new ArrayAdapter<DocumentoProduzido>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDocumentosProduzidosRecuperados);
        autoCompleteTextViewDocumentoProduzido.setAdapter(adapterDocumentos);
        autoCompleteTextViewDocumentoProduzido.setThreshold(1);

        listaDeDocumentosSelecionadosNaoValidados = new ArrayList<DocumentoProduzido>();
        adapterDocumentos = new ArrayAdapter<DocumentoProduzido>(getActivity(),
                android.R.layout.simple_list_item_1,
                listaDeDocumentosSelecionadosNaoValidados);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDocumentoProduzido);

        gerenciarListaDeDocumentosSelecionados();

    }

    private void gerenciarListaDeDocumentosSelecionados()
    {
        btnAdicionarDocumentoProduzido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewDocumentoProduzido(getActivity(), autoCompleteTextViewDocumentoProduzido,
                        listaDocumentosProduzidosRecuperados, listaDeDocumentosSelecionadosNaoValidados,
                        adapterDocumentos, linearLayoutDocumentoProduzido);
            }
        });
    }

    private void popularCampoEncaminhamentoComDB() {

        EncaminhamentoController encaminhamentoController = new EncaminhamentoController();
        encaminhamentoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Encaminhamento encaminhamento = new Encaminhamento();
                        encaminhamento.setId(Integer.parseInt(object.getString("id")));
                        encaminhamento.setDestino(object.getString("destino"));
                        encaminhamento.setTipo(object.getString("tipo"));

                        listaDeEncaminhamentosRecuperados.add(encaminhamento);
                        configurarCampoEncaminhamento(listaDeEncaminhamentosRecuperados);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void configurarCampoEncaminhamento(List<Encaminhamento> listaEncaminhamentosRecuperados) {

        adapterEncaminhamento = new ArrayAdapter<Encaminhamento>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaEncaminhamentosRecuperados);
        autoCompleteTextViewEncaminhamento.setAdapter(adapterEncaminhamento);
        autoCompleteTextViewEncaminhamento.setThreshold(1);

        configurarCampoEncaminhamento();
    }

    private void configurarCampoEncaminhamento() {

        linearLayoutEncaminhamento.removeAllViews();

        listaDeEncaminhamentosSelecionadosNaoValidados = new ArrayList<Encaminhamento>();
        adapterEncaminhamento = new ArrayAdapter<Encaminhamento>(getActivity(),
                android.R.layout.simple_list_item_1,
                listaDeEncaminhamentosSelecionadosNaoValidados);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEncaminhamento);

        if(listaDeEncaminhamentosSelecionadosValidados == null) {
            linearLayoutEncaminhamento.removeAllViews();
        }else{
            criarTextViewParaEncaminhamentosSelecionados();
            for(Encaminhamento encaminhamentosRecebidos : listaDeEncaminhamentosSelecionadosValidados) {
                listaDeEncaminhamentosSelecionadosNaoValidados.add(encaminhamentosRecebidos);
            }
        }

        btnAdicionarEncaminhamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewEncaminhamento(getActivity(),
                        autoCompleteTextViewEncaminhamento, listaDeEncaminhamentosRecuperados,
                        listaDeEncaminhamentosSelecionadosNaoValidados, adapterEncaminhamento, linearLayoutEncaminhamento);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroAtendimento() throws ParseException {
        if (
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewProcedimento,
                        listaProcedimentosRecuperados, "O campo PROCEDIMENTO ?? obrigat??rio.",
                        "Insira uma op????o de PROCEDIMENTO v??lida.") &&
                        FieldValidator.isListEmptyOrNull(autoCompleteTextViewDocumentoProduzido,
                                listaDeDocumentosSelecionadosNaoValidados, "DOCUMENTO PRODUZIDO") &&
                        FieldValidator.isListEmptyOrNull(autoCompleteTextViewEncaminhamento,
                                listaDeEncaminhamentosSelecionadosNaoValidados, "ENCAMINHAMENTO") &&
                        FieldValidator.validarRadioGroup(radioGroupAfastamento, rbtnAfastamentoNao, "AFASTAMENTO") &&
                        FieldValidator.isFieldEmptyOrNull(textInputEditTextEvolucao, "EVOLU????O")) {
            receberDadosAtendimentoPreenchidos();
            return true;
        } else { return false; }
    }

    private void receberDadosAtendimentoPreenchidos()
    {

        for (Procedimento procedimentoSelecionado : listaProcedimentosRecuperados)
        {
            if (procedimentoSelecionado.getNome().equals
                    (autoCompleteTextViewProcedimento.getText().toString().trim()))
                procedimento = procedimentoSelecionado;
        }

        listaDeDocumentosSelecionadosValidados = listaDeDocumentosSelecionadosNaoValidados;
        listaDeEncaminhamentosSelecionadosValidados = listaDeEncaminhamentosSelecionadosNaoValidados;
        evolucao = textInputEditTextEvolucao.getText().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Atendimento encapsularValoresParaRegistroDeAtendimento() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosAtendimentoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        LocalDate data= DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("data"));

        atendimento = new Atendimento();

        atendimento.setTipo((TipoServico) valoresRecebidosFragment1e2.getSerializable("tipoServico"));
        atendimento.setData(data);
        atendimento.setEspecialistas(new HashSet<>(
                (ArrayList<Especialista>) valoresRecebidosFragment1.getSerializable("listaDeEspecialistas")));
        atendimento.setUsuarios(new HashSet<>(
                (ArrayList<Usuario>) valoresRecebidosFragment1.getSerializable("listaDeAtendidos")));
        atendimento.setUnidade( (Unidade) valoresRecebidosFragment1.getSerializable("unidade"));
        atendimento.setModalidade( (Modalidade)(valoresRecebidosFragment1.getSerializable("modalidade")));
        atendimento.setAcesso( (Acesso) (valoresRecebidosFragment1.getSerializable("acesso")));
        atendimento.setPrograma( (Programa) (valoresRecebidosFragment1e2.getSerializable("programa")));
        atendimento.setDeslocamentos( new HashSet<> (
                (ArrayList<Deslocamento>) valoresRecebidosFragment1e2.getSerializable("listaDeDeslocamentos")));
        atendimento.setDemandaGeral( (DemandaGeral) (valoresRecebidosFragment1e2.getSerializable("demandaGeral")));
        atendimento.setDemandasEspecificas( new HashSet<> (
                (ArrayList<DemandaEspecifica>) valoresRecebidosFragment1e2.getSerializable("listaDeDemandasEspecificas")));
        atendimento.setProcedimento((procedimento));
        atendimento.setDocumentosProduzidos(new HashSet<>(
                (ArrayList<DocumentoProduzido>) listaDeDocumentosSelecionadosValidados));
        atendimento.setEncaminhamentos(new HashSet<>(
                (ArrayList<Encaminhamento>) listaDeEncaminhamentosSelecionadosValidados));
        atendimento.setAfastamento(afastamento);
        atendimento.setEvolucao(evolucao);

        return atendimento;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Avaliacao encapsularValoresParaRegistroDeAvaliacao() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosAtendimentoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        LocalDate data= DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("data"));

        avaliacao = new Avaliacao();

        avaliacao.setTipo((TipoServico) valoresRecebidosFragment1e2.getSerializable("tipoServico"));
        avaliacao.setData(data);
        avaliacao.setEspecialistas(new HashSet<>((ArrayList<Especialista>) valoresRecebidosFragment1.getSerializable("listaDeEspecialistas")));
        avaliacao.setUsuarios(new HashSet<>((ArrayList<Usuario>) valoresRecebidosFragment1.getSerializable("listaDeAtendidos")));
        avaliacao.setUnidade( (Unidade) valoresRecebidosFragment1.getSerializable("unidade"));
        avaliacao.setModalidade( (Modalidade)(valoresRecebidosFragment1.getSerializable("modalidade")));
        avaliacao.setAcesso( (Acesso) (valoresRecebidosFragment1.getSerializable("acesso")));
        avaliacao.setTipoAvaliacao( (TipoAvaliacao) (valoresRecebidosFragment1e2.getSerializable("tipoAvaliacao")));
        avaliacao.setPrograma( (Programa) (valoresRecebidosFragment1e2.getSerializable("programa")));
        avaliacao.setDeslocamentos(new HashSet<> ((ArrayList<Deslocamento>) valoresRecebidosFragment1e2.getSerializable("listaDeDeslocamentos")));
        avaliacao.setDemandaGeral( (DemandaGeral) (valoresRecebidosFragment1e2.getSerializable("demandaGeral")));
        avaliacao.setDemandasEspecificas(new HashSet<> ((ArrayList<DemandaEspecifica>) valoresRecebidosFragment1e2.getSerializable("listaDeDemandasEspecificas")));
        avaliacao.setProcedimento((procedimento));
        avaliacao.setDocumentosProduzidos(new HashSet<>((ArrayList<DocumentoProduzido>) listaDeDocumentosSelecionadosValidados));
        avaliacao.setEncaminhamentos(new HashSet<>((ArrayList<Encaminhamento>) listaDeEncaminhamentosSelecionadosValidados));
        avaliacao.setAfastamento(afastamento);
        avaliacao.setEvolucao(evolucao);

        return avaliacao;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ServicoDeAssistenciaEspecial encapsularValoresParaRegistroDeSae() throws ParseException {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosAtendimentoRegisterFragment2();
        Bundle valoresRecebidosFragment1 = valoresRecebidosFragment1e2.getBundle("valoresRecebidosFragment1");

        LocalDate data= DateFormater.StringToLocalDate(valoresRecebidosFragment1.getString("data"));

        sae = new ServicoDeAssistenciaEspecial();

        sae.setTipo((TipoServico) valoresRecebidosFragment1e2.getSerializable("tipoServico"));
        sae.setData(data);
        sae.setEspecialistas(new HashSet<>((ArrayList<Especialista>) valoresRecebidosFragment1.getSerializable("listaDeEspecialistas")));
        sae.setUsuarios(new HashSet<>((ArrayList<Usuario>) valoresRecebidosFragment1.getSerializable("listaDeAtendidos")));
        sae.setUnidade( (Unidade) valoresRecebidosFragment1.getSerializable("unidade"));
        sae.setModalidade( (Modalidade)(valoresRecebidosFragment1.getSerializable("modalidade")));
        sae.setAcesso( (Acesso) (valoresRecebidosFragment1.getSerializable("acesso")));
        sae.setPrograma( (Programa) (valoresRecebidosFragment1e2.getSerializable("programa")));
        sae.setDeslocamentos(new HashSet<> ((ArrayList<Deslocamento>) valoresRecebidosFragment1e2.getSerializable("listaDeDeslocamentos")));
        sae.setDemandaGeral( (DemandaGeral) (valoresRecebidosFragment1e2.getSerializable("demandaGeral")));
        sae.setDemandasEspecificas(new HashSet<> ((ArrayList<DemandaEspecifica>) valoresRecebidosFragment1e2.getSerializable("listaDeDemandasEspecificas")));
        sae.setCondicaoLaboral( (CondicaoLaboral) (valoresRecebidosFragment1e2.getSerializable("condicaoLaboral")));
        sae.setProcedimento((procedimento));
        sae.setDocumentosProduzidos(new HashSet<>((ArrayList<DocumentoProduzido>) listaDeDocumentosSelecionadosValidados));
        sae.setEncaminhamentos(new HashSet<>((ArrayList<Encaminhamento>) listaDeEncaminhamentosSelecionadosValidados));
        sae.setAfastamento(afastamento);
        sae.setEvolucao(evolucao);

        return sae;
    }

    private void registrarAtendimento(Atendimento novoAtendimento)
    {
        new AtendimentoController().registrar(
                getActivity(),
                novoAtendimento,
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
                            Log.e("e.pritStack", e.getMessage());
                        }
                    }
                });
    }

    private void registrarAvaliacao(Avaliacao novaAvaliacao)
    {
        new AvaliacaoController().registrar(
                getActivity(),
                novaAvaliacao,
                new IVolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        Log.e("ERRO", response);

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

    private void registrarSae(ServicoDeAssistenciaEspecial novoSae)
    {
        new ServicoDeAssistenciaEspecialController().registrar(
                getActivity(),
                novoSae,
                new IVolleyCallback() {
                    @Override
                    public void onSucess(String response) {

                        Log.e("ERRO", response);

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

    private void enviarFormulario() {
        Bundle valoresRecebidosFragment1e2 =  recuperarDadosAtendimentoRegisterFragment2();
        TipoServico tipo = (TipoServico) valoresRecebidosFragment1e2.getSerializable("tipoServico");

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(validarCadastroAtendimento()) {
                    if(tipo.getNome().equals(TipoServicoEnum.ATENDIMENTO_PSICOLOGICO.getNome()) ||
                            tipo.getNome().equals(TipoServicoEnum.ATENDIMENTO_SOCIAL.getNome())) {
                        Atendimento novoAtendimento;
                        novoAtendimento = encapsularValoresParaRegistroDeAtendimento();
                        registrarAtendimento(novoAtendimento);
                    } else if (tipo.getNome().equals(TipoServicoEnum.AVALIACAO_PSICOLOGICA.getNome()) ||
                            tipo.getNome().equals(TipoServicoEnum.AVALIACAO_SOCIAL.getNome())) {
                        Avaliacao novaAvaliacao;
                        novaAvaliacao = encapsularValoresParaRegistroDeAvaliacao();
                        registrarAvaliacao(novaAvaliacao);
                    } else {
                        ServicoDeAssistenciaEspecial novoSae;
                        novoSae = encapsularValoresParaRegistroDeSae();
                        registrarSae(novoSae);
                    }

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
}