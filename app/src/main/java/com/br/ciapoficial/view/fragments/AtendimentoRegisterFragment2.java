package com.br.ciapoficial.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.CondicaoLaboralController;
import com.br.ciapoficial.controller.DemandaEspecificaController;
import com.br.ciapoficial.controller.DemandaGeralController;
import com.br.ciapoficial.controller.DeslocamentoController;
import com.br.ciapoficial.controller.ProgramaController;
import com.br.ciapoficial.controller.TipoAvaliacaoController;
import com.br.ciapoficial.controller.TipoServicoController;
import com.br.ciapoficial.enums.TipoServicoEnum;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DellayAction;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.TextChangedListener;
import com.br.ciapoficial.interfaces.ITextWatcherCallback;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.in_servico.CondicaoLaboral;
import com.br.ciapoficial.model.in_servico.DemandaEspecifica;
import com.br.ciapoficial.model.in_servico.DemandaGeral;
import com.br.ciapoficial.model.in_servico.Deslocamento;
import com.br.ciapoficial.model.in_servico.Programa;
import com.br.ciapoficial.model.in_servico.TipoAvaliacao;
import com.br.ciapoficial.validation.FieldValidator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.SneakyThrows;

public class AtendimentoRegisterFragment2 extends Fragment {

    private AtendimentoRegisterFragment3 atendimentoRegisterFragment3;

    private LinearLayout linearLayoutDeslocamento, linearLayoutDemandaEspecifica;
    private TextInputLayout textInputLayoutTipoAvaliacao, textInputLayoutCondicaoLaboral;
    private AutoCompleteTextView autoCompleteTextViewTipoServico, autoCompleteTextViewTipoAvaliacao,
            autoCompleteTextViewPrograma, autoCompleteTextViewDeslocamento, autoCompleteTextViewDemandaGeral,
            autoCompleteTextViewDemandaEspecifica, autoCompleteTextViewCondicaoLaboral;
    private Button btnAdicionarDeslocamento, btnAdicionarDemandaEspecifica, btnProxima;

    private List<String> listaTiposServicosRecuperados = new ArrayList<>();
    private List<TipoAvaliacao> listaTiposAvaliacaoRecuperadas = new ArrayList<>();
    private List<Programa> listaProgramasRecuperados = new ArrayList<>();
    private List<Deslocamento> listaDeslocamentosRecuperados = new ArrayList<>();
    private List<DemandaGeral> listaDemandasGeraisRecuperadas = new ArrayList<>();
    private List<DemandaEspecifica> listaDemandasEspecificasRecuperadas = new ArrayList<>();
    private List<CondicaoLaboral> listaCondicoesLaboraisRecuperadas = new ArrayList<>();

    private List<Deslocamento> listaDeDeslocamentosSelecionadosNaoValidados;
    private List<DemandaEspecifica> listaDeDemandasEspecificasSelecionadasNaoValidadas;
    private ArrayAdapter<Deslocamento> adapterDeslocamentos;
    private ArrayAdapter<DemandaEspecifica> adapterDemandasEspecificas;

    private List<Deslocamento> listaDeDeslocamentosSelecionadosValidados = new ArrayList<>();
    private List<DemandaEspecifica> listaDeDemandasEspecificasSelecionadasValidadas = new ArrayList<>();
    private String tipoServico;
    private TipoAvaliacao tipoAvaliacao = new TipoAvaliacao();
    private Programa programa;
    private DemandaGeral demandaGeral;
    private CondicaoLaboral condicaoLaboral;

    public AtendimentoRegisterFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendimento_register2, container, false);

        configurarComponentes(view);
        definirVisibilidadeInicialDosComponentes();
        definirVisibilidadeCondicionalDosComponentes();
        popularCampoTipoServicoComDB();
        popularCampoTipoAvaliacaoComDB();
        popularCampoProgramaComDB();
        popularCampoDeslocamentoComDB();
        popularCampoDemandaGeralComDB();
        popularCampoDemandaEspecificaComDB();
        popularCampoCondicaoLaboralComDB();
        abrirProximaTela();
        return view;
    }

    private void configurarComponentes(View view) {

        linearLayoutDeslocamento = view.findViewById(R.id.linearLayoutDeslocamento);
        linearLayoutDemandaEspecifica = view.findViewById(R.id.linearLayoutDemandaEspecifica);
        textInputLayoutTipoAvaliacao = view.findViewById(R.id.textInputLayoutTipoAvaliacao);
        textInputLayoutCondicaoLaboral = view.findViewById(R.id.textInputLayoutCondicaoLaboral);
        autoCompleteTextViewTipoServico = view.findViewById(R.id.edtTipoAtendimento);
        autoCompleteTextViewTipoAvaliacao = view.findViewById(R.id.edtTipoAvaliacao);
        autoCompleteTextViewPrograma = view.findViewById(R.id.edtPrograma);
        autoCompleteTextViewDeslocamento = view.findViewById(R.id.edtDeslocamento);
        autoCompleteTextViewDemandaGeral = view.findViewById(R.id.edtDemandaGeral);
        autoCompleteTextViewDemandaEspecifica = view.findViewById(R.id.edtDemandaEspecifica);
        autoCompleteTextViewCondicaoLaboral = view.findViewById(R.id.edtCondicaoLaboral);
        btnAdicionarDeslocamento = view.findViewById(R.id.btnAdicionarDeslocamento);
        btnAdicionarDemandaEspecifica = view.findViewById(R.id.btnAdicionarDemandaEspecifica);
        btnProxima = view.findViewById(R.id.btnProxima);
    }

    private void definirVisibilidadeInicialDosComponentes()
    {
        textInputLayoutTipoAvaliacao.setVisibility(View.GONE);
        autoCompleteTextViewTipoAvaliacao.setVisibility(View.GONE);
        textInputLayoutCondicaoLaboral.setVisibility(View.GONE);
        autoCompleteTextViewCondicaoLaboral.setVisibility(View.GONE);
    }

    private void definirVisibilidadeCondicionalDosComponentes()
    {
        TextChangedListener.textChangedListener(autoCompleteTextViewTipoServico, new ITextWatcherCallback() {
            @Override
            public void afterTextChanged(Editable s) {
                if(autoCompleteTextViewTipoServico.getText().toString().contains(TipoServicoEnum.AVALIACAO_PSICOLOGICA.getNome()) ||
                        autoCompleteTextViewTipoServico.getText().toString().contains(TipoServicoEnum.AVALIACAO_SOCIAL.getNome())) {
                    textInputLayoutTipoAvaliacao.setVisibility(View.VISIBLE);
                    autoCompleteTextViewTipoAvaliacao.setVisibility(View.VISIBLE);
                }
                else
                {
                    textInputLayoutTipoAvaliacao.setVisibility(View.GONE);
                    autoCompleteTextViewTipoAvaliacao.setVisibility(View.GONE);
                }
            }
        });

    }

    private Bundle recuperarDadosAtendimentoRegisterFragment1() {
        Bundle valoresRecebidosFragment1 = this.getArguments();

        return valoresRecebidosFragment1;
    }

    private void criarTextViewParaDeslocamentosSelecionados()
    {
        TextView textView = new TextView(getContext());
        for(Deslocamento deslocamentoRecebido : listaDeDeslocamentosSelecionadosValidados) {
            String textoRecebido = deslocamentoRecebido.toString();

            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayoutDeslocamento.addView(textView);
        }
        removerItemDaListaDeDeslocamentos(textView, listaDeDeslocamentosSelecionadosNaoValidados, adapterDeslocamentos);
    }

    private static void removerItemDaListaDeDeslocamentos(TextView textView, List<Deslocamento> listaDisplay,
                                                          ArrayAdapter<Deslocamento> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<Deslocamento> iter = listaDisplay.iterator(); iter.hasNext();)
                {
                    Deslocamento deslocamentoSelecionado = iter.next();
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

    private void criarTextViewParaDemandasEspecificasSelecionadas()
    {
        TextView textView = new TextView(getContext());
        for(DemandaEspecifica demandaEspecificaRecebida : listaDeDemandasEspecificasSelecionadasValidadas) {
            String textoRecebido = demandaEspecificaRecebida.toString();

            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayoutDemandaEspecifica.addView(textView);
        }
        removerItemDaListaDeDemandasEspecificas(textView, listaDeDemandasEspecificasSelecionadasNaoValidadas, adapterDemandasEspecificas);
    }

    public static void removerItemDaListaDeDemandasEspecificas(TextView textView, List<DemandaEspecifica> listaDisplay,
                                                               ArrayAdapter<DemandaEspecifica> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<DemandaEspecifica> iter = listaDisplay.iterator(); iter.hasNext();)
                {
                    DemandaEspecifica demandaEspecificaSelecionada = iter.next();
                    if(demandaEspecificaSelecionada.toString().equals(string))
                    {
                        listaDisplay.remove(demandaEspecificaSelecionada);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    private void popularCampoTipoServicoComDB() {

        TipoServicoController tipoServicoController = new TipoServicoController();
        tipoServicoController.listar(getActivity(), new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSucess(String response) {

                try {

                    Gson gson = new GsonBuilder().create();

                    List<String> tiposDeServico = gson.fromJson(response, List.class);
                    listaTiposServicosRecuperados = tiposDeServico;

                    configurarCampoTipoServico(listaTiposServicosRecuperados);

            }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void configurarCampoTipoServico(List<String> listaTiposServicosRecuperados) {

        ArrayAdapter<String> adapterTipoDeServico= new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaTiposServicosRecuperados);
        autoCompleteTextViewTipoServico.setAdapter(adapterTipoDeServico);
        autoCompleteTextViewTipoServico.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewTipoServico);

    }

    private void popularCampoTipoAvaliacaoComDB() {

        TipoAvaliacaoController tipoAvaliacaoController = new TipoAvaliacaoController();
        tipoAvaliacaoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        TipoAvaliacao tipoAvaliacao = new TipoAvaliacao();
                        tipoAvaliacao.setId(Integer.parseInt(object.getString("id")));
                        tipoAvaliacao.setNome(object.getString("nome"));

                        listaTiposAvaliacaoRecuperadas.add(tipoAvaliacao);
                        configurarCampoTipoAvaliacao(listaTiposAvaliacaoRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoTipoAvaliacao(List<TipoAvaliacao> listaTiposAavaliacaoRecuperadas) {

        ArrayAdapter<TipoAvaliacao> adapterTipoAvaliacao= new ArrayAdapter<TipoAvaliacao>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaTiposAavaliacaoRecuperadas);
        autoCompleteTextViewTipoAvaliacao.setAdapter(adapterTipoAvaliacao);
        autoCompleteTextViewTipoAvaliacao.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewTipoAvaliacao);

    }

    private void popularCampoProgramaComDB() {

        ProgramaController programaController = new ProgramaController();
        programaController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Programa programa = new Programa();
                        programa.setId(Integer.parseInt(object.getString("id")));
                        programa.setNome(object.getString("nome"));

                        listaProgramasRecuperados.add(programa);
                        configurarCampoPrograma(listaProgramasRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoPrograma(List<Programa> listaProgramasRecuperados) {

        ArrayAdapter<Programa> adapterPrograma= new ArrayAdapter<Programa>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaProgramasRecuperados);
        autoCompleteTextViewPrograma.setAdapter(adapterPrograma);
        autoCompleteTextViewPrograma.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewPrograma);

    }

    private void popularCampoDeslocamentoComDB() {

        DeslocamentoController deslocamentoController = new DeslocamentoController();
        deslocamentoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Deslocamento deslocamento = new Deslocamento();
                        deslocamento.setId(Integer.parseInt(object.getString("id")));
                        deslocamento.setNome(object.getString("nome"));

                        listaDeslocamentosRecuperados.add(deslocamento);
                        configurarCampoDeslocamento(listaDeslocamentosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDeslocamento(List<Deslocamento> listaDeslocamentosRecuperados) {

        linearLayoutDeslocamento.removeAllViews();

        adapterDeslocamentos = new ArrayAdapter<Deslocamento>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDeslocamentosRecuperados);
        autoCompleteTextViewDeslocamento.setAdapter(adapterDeslocamentos);
        autoCompleteTextViewDeslocamento.setThreshold(1);

        listaDeDeslocamentosSelecionadosNaoValidados = new ArrayList<Deslocamento>();
        adapterDeslocamentos = new ArrayAdapter<Deslocamento>(getActivity(),
                android.R.layout.simple_list_item_1,
                listaDeDeslocamentosSelecionadosNaoValidados);

        if(listaDeDeslocamentosSelecionadosValidados == null) {
            linearLayoutDeslocamento.removeAllViews();
        }else{
            criarTextViewParaDeslocamentosSelecionados();
            for(Deslocamento deslocamentoRecebido : listaDeDeslocamentosSelecionadosValidados) {
                listaDeDeslocamentosSelecionadosNaoValidados.add(deslocamentoRecebido);
            }
        }

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDeslocamento);

        btnAdicionarDeslocamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewDeslocamento(getActivity(), autoCompleteTextViewDeslocamento,
                        listaDeslocamentosRecuperados, listaDeDeslocamentosSelecionadosNaoValidados, adapterDeslocamentos,
                        linearLayoutDeslocamento);

            }
        });
    }

    private void popularCampoDemandaGeralComDB() {

        DemandaGeralController demandaGeralController = new DemandaGeralController();
        demandaGeralController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        DemandaGeral demandaGeral = new DemandaGeral();
                        demandaGeral.setId(Integer.parseInt(object.getString("id")));
                        demandaGeral.setNome(object.getString("nome"));

                        listaDemandasGeraisRecuperadas.add(demandaGeral);
                        configurarCampoDemandaGeral(listaDemandasGeraisRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDemandaGeral(List<DemandaGeral> listaDemandasGeraisRecuperadas) {

        ArrayAdapter<DemandaGeral> adapterDemandaGeral= new ArrayAdapter<DemandaGeral>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDemandasGeraisRecuperadas);
        autoCompleteTextViewDemandaGeral.setAdapter(adapterDemandaGeral);
        autoCompleteTextViewDemandaGeral.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDemandaGeral);
    }

    private void popularCampoDemandaEspecificaComDB() {

        DemandaEspecificaController demandaEspecificaController = new DemandaEspecificaController();
        demandaEspecificaController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        DemandaEspecifica demandaEspecifica = new DemandaEspecifica();
                        demandaEspecifica.setId(Integer.parseInt(object.getString("id")));
                        demandaEspecifica.setNome(object.getString("nome"));

                        listaDemandasEspecificasRecuperadas.add(demandaEspecifica);
                        configurarCampoDemandaEspecifica(listaDemandasEspecificasRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDemandaEspecifica(List<DemandaEspecifica> listaDemandasEspecificasRecuperadas) {

        linearLayoutDemandaEspecifica.removeAllViews();

        adapterDemandasEspecificas = new ArrayAdapter<DemandaEspecifica>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDemandasEspecificasRecuperadas);
        autoCompleteTextViewDemandaEspecifica.setAdapter(adapterDemandasEspecificas);
        autoCompleteTextViewDemandaEspecifica.setThreshold(1);

        listaDeDemandasEspecificasSelecionadasNaoValidadas = new ArrayList<DemandaEspecifica>();
        adapterDemandasEspecificas = new ArrayAdapter<DemandaEspecifica>(getActivity(),
                android.R.layout.simple_list_item_1,
                listaDeDemandasEspecificasSelecionadasNaoValidadas);

        if(listaDeDemandasEspecificasSelecionadasValidadas == null) {
            linearLayoutDemandaEspecifica.removeAllViews();
        }else{
            criarTextViewParaDemandasEspecificasSelecionadas();
            for(DemandaEspecifica demandasEspecificasRecebidas : listaDeDemandasEspecificasSelecionadasValidadas) {
                listaDeDemandasEspecificasSelecionadasNaoValidadas.add(demandasEspecificasRecebidas);
            }
        }

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDemandaEspecifica);

        btnAdicionarDemandaEspecifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoRecebido = autoCompleteTextViewDemandaEspecifica.getText().toString().trim();
                TextView textViewDemandasEspecificasSelecionadas = new TextView(getActivity());
                if(textoRecebido.isEmpty()) {
                    autoCompleteTextViewDemandaEspecifica.setError(
                            "O campo para inserção de DEMANDA ESPECÍFICA está vazio.");
                    autoCompleteTextViewDemandaEspecifica.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(autoCompleteTextViewDemandaEspecifica);
                } else {
                    if(!(listaDeDemandasEspecificasSelecionadasNaoValidadas.toString().contains(textoRecebido))) {
                        textViewDemandasEspecificasSelecionadas.setPadding(0, 10, 0, 10);
                        textViewDemandasEspecificasSelecionadas.setText(textoRecebido);
                        textViewDemandasEspecificasSelecionadas.setTag("lista");

                        linearLayoutDemandaEspecifica.addView(textViewDemandasEspecificasSelecionadas);

                        autoCompleteTextViewDemandaEspecifica.setText("");

                        for (DemandaEspecifica demandaEspecificaSelecionada : listaDemandasEspecificasRecuperadas) {
                            if (demandaEspecificaSelecionada.getNome().equals(textoRecebido)) {
                                listaDeDemandasEspecificasSelecionadasNaoValidadas.add(demandaEspecificaSelecionada);

                                break;
                            }

                            if (textoRecebido.equals("Óbito(militar)")) {
                                textInputLayoutCondicaoLaboral.setVisibility(View.VISIBLE);
                                autoCompleteTextViewCondicaoLaboral.setVisibility(View.VISIBLE);
                            }
                            adapterDemandasEspecificas.notifyDataSetChanged();
                        }
                    } else {
                        autoCompleteTextViewDemandaEspecifica.setError("Essa opção de DEMANDA ESPECÍFICA já foi inserida.");
                        autoCompleteTextViewDemandaEspecifica.requestFocus();
                        DellayAction.clearErrorAfter2Seconds(autoCompleteTextViewDemandaEspecifica);
                        autoCompleteTextViewDemandaEspecifica.setText("");
                    }
                }

                removerItemDaListaDeDemandasEspecificas(textViewDemandasEspecificasSelecionadas, listaDeDemandasEspecificasSelecionadasNaoValidadas);
            }
        });

    }

    private void removerItemDaListaDeDemandasEspecificas(TextView textView, List<DemandaEspecifica> arrayListDemandasEspecificasSelecionadas )
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(DemandaEspecifica demandaEspecificaSelecionada : arrayListDemandasEspecificasSelecionadas) {
                    String dadosDemandaEspecificaSelecionada = demandaEspecificaSelecionada.toString();
                    if(dadosDemandaEspecificaSelecionada.equals(string)) {
                        arrayListDemandasEspecificasSelecionadas.remove(demandaEspecificaSelecionada);
                        adapterDemandasEspecificas.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;
                    }
                }
                if(string.equals("Óbito(militar)")) {
                    textInputLayoutCondicaoLaboral.setVisibility(View.GONE);
                    autoCompleteTextViewCondicaoLaboral.setVisibility(View.GONE);
                }
            }
        });
    }

    private void popularCampoCondicaoLaboralComDB() {

        CondicaoLaboralController condicaoLaboralController = new CondicaoLaboralController();
        condicaoLaboralController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        CondicaoLaboral condicaoLaboral = new CondicaoLaboral();
                        condicaoLaboral.setId(Integer.parseInt(object.getString("id")));
                        condicaoLaboral.setNome(object.getString("nome"));

                        listaCondicoesLaboraisRecuperadas.add(condicaoLaboral);
                        configurarCampoCondicaoLaboral(listaCondicoesLaboraisRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoCondicaoLaboral(List<CondicaoLaboral> listaCondicoesLaboraisRecuperadas) {

        ArrayAdapter<CondicaoLaboral> adapterCondicaoLaboral= new ArrayAdapter<CondicaoLaboral>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaCondicoesLaboraisRecuperadas);
        autoCompleteTextViewCondicaoLaboral.setAdapter(adapterCondicaoLaboral);
        autoCompleteTextViewCondicaoLaboral.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCondicaoLaboral);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroAtendimento() throws ParseException {
        if (
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewTipoServico,
                        listaTiposServicosRecuperados, "O campo TIPO DE SERVIÇO é obrigatório.",
                        "Insira uma opção de TIPO DE SERVIÇO válida.") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewPrograma, listaProgramasRecuperados,
                        "O campo PROGRAMA é obrigatório.", "Insira uma opção de PROGRAMA válida.") &&
                FieldValidator.isListEmptyOrNull(autoCompleteTextViewDeslocamento,
                        listaDeDeslocamentosSelecionadosNaoValidados, "DESLOCAMENTO") &&
                FieldValidator.validarAutoCompleteTextView(autoCompleteTextViewDemandaGeral, listaDemandasGeraisRecuperadas,
                        "O campo DEMANDA GERAL é obrigatório.", "Insira uma opção de DEMANDA GERAL válida.") &&
                FieldValidator.isListEmptyOrNull(autoCompleteTextViewDemandaEspecifica,
                        listaDeDemandasEspecificasSelecionadasNaoValidadas, "DEMANDA ESPECÍFICA") &&
                FieldValidator.validarCondicaoLaboral(autoCompleteTextViewCondicaoLaboral,
                        listaCondicoesLaboraisRecuperadas, listaDeDemandasEspecificasSelecionadasNaoValidadas)) {
            receberDadosAtendimentoPreenchidos();
            return true;
        } else { return false; }
    }

    private void receberDadosAtendimentoPreenchidos() {

        tipoServico = autoCompleteTextViewTipoServico.getText().toString().trim();

        if (autoCompleteTextViewTipoServico.getText().toString().contains("Avaliação")) {
            for (TipoAvaliacao tipoAvaliacaoSelecionada : listaTiposAvaliacaoRecuperadas)
            {
                if (tipoAvaliacaoSelecionada.getNome().equals(autoCompleteTextViewTipoAvaliacao.getText().toString().trim()))
                    tipoAvaliacao = tipoAvaliacaoSelecionada;
            }
        } else {
            for (TipoAvaliacao tipoAvaliacaoSelecionada : listaTiposAvaliacaoRecuperadas)
            {
                if (tipoAvaliacaoSelecionada.getNome().equals("Não se aplica")) {
                    tipoAvaliacao = tipoAvaliacaoSelecionada;
                }
            }
        }

        for (Programa programaSelecionado : listaProgramasRecuperados)
        {
            if (programaSelecionado.getNome().equals
                    (autoCompleteTextViewPrograma.getText().toString().trim()))
                programa = programaSelecionado;
        }

        listaDeDeslocamentosSelecionadosValidados = listaDeDeslocamentosSelecionadosNaoValidados;


        for (DemandaGeral demandaGeralSelecionada : listaDemandasGeraisRecuperadas)
        {
            if (demandaGeralSelecionada.getNome().equals
                    (autoCompleteTextViewDemandaGeral.getText().toString().trim()))
            demandaGeral = demandaGeralSelecionada;
        }

        listaDeDemandasEspecificasSelecionadasValidadas = listaDeDemandasEspecificasSelecionadasNaoValidadas;

        if(listaDeDemandasEspecificasSelecionadasValidadas.toString().contains("Óbito(militar)")) {
            for (CondicaoLaboral condicaoLaboralSelecionada : listaCondicoesLaboraisRecuperadas) {
                if (condicaoLaboralSelecionada.getNome().equals
                        (autoCompleteTextViewCondicaoLaboral.getText().toString().trim()))
                    condicaoLaboral = condicaoLaboralSelecionada;
            }
        } else {
            for (CondicaoLaboral condicaoLaboralSelecionada : listaCondicoesLaboraisRecuperadas)
            {
                if (condicaoLaboralSelecionada.getNome().equals("N/A")) {
                    condicaoLaboral = condicaoLaboralSelecionada;
                }
            }
        }

    }

    private Bundle encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1 =  recuperarDadosAtendimentoRegisterFragment1();
        Bundle bundle = new Bundle();

        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
        bundle.putString("tipoServico", tipoServico);
        bundle.putSerializable("tipoAvaliacao", (Serializable) tipoAvaliacao);
        bundle.putSerializable("programa", (Serializable) programa);
        bundle.putSerializable("listaDeDeslocamentos", (Serializable) listaDeDeslocamentosSelecionadosValidados);
        bundle.putSerializable("demandaGeral", (Serializable) demandaGeral);
        bundle.putSerializable("listaDeDemandasEspecificas", (Serializable) listaDeDemandasEspecificasSelecionadasValidadas);
        bundle.putSerializable("condicaoLaboral", (Serializable) condicaoLaboral);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(validarCadastroAtendimento())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    atendimentoRegisterFragment3 = new AtendimentoRegisterFragment3();
                    atendimentoRegisterFragment3.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, atendimentoRegisterFragment3);
                    fragmentTransaction.addToBackStack(null).commit();
                }
            }
        });

    }

    @Override
    public void onResume() {

        listaTiposServicosRecuperados.clear();
        listaTiposAvaliacaoRecuperadas.clear();
        listaProgramasRecuperados.clear();
        listaDeslocamentosRecuperados.clear();
        listaDemandasGeraisRecuperadas.clear();
        listaDemandasEspecificasRecuperadas.clear();
        listaCondicoesLaboraisRecuperadas.clear();
        super.onResume();
    }
}