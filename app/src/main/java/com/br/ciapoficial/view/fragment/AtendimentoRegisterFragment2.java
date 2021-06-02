package com.br.ciapoficial.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.br.ciapoficial.R;
import com.br.ciapoficial.controller.CondicaoLaboralController;
import com.br.ciapoficial.controller.DemandaEspecificaController;
import com.br.ciapoficial.controller.DemandaGeralController;
import com.br.ciapoficial.controller.DeslocamentoController;
import com.br.ciapoficial.controller.ProgramaController;
import com.br.ciapoficial.controller.TipoAtendimentoController;
import com.br.ciapoficial.controller.TipoAvaliacaoController;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.helper.TextChangedListener;
import com.br.ciapoficial.interfaces.TextWatcherCallback;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.in_atendimento.CondicaoLaboral;
import com.br.ciapoficial.model.in_atendimento.DemandaEspecifica;
import com.br.ciapoficial.model.in_atendimento.DemandaGeral;
import com.br.ciapoficial.model.in_atendimento.Deslocamento;
import com.br.ciapoficial.model.in_atendimento.Programa;
import com.br.ciapoficial.model.in_atendimento.TipoAtendimento;
import com.br.ciapoficial.model.in_atendimento.TipoAvaliacao;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AtendimentoRegisterFragment2 extends Fragment {

    private LinearLayout linearLayoutDeslocamento, linearLayoutDemandaEspecifica;
    private TextInputLayout textInputLayoutTipoAvaliacao, textInputLayoutCondicaoLaboral;
    private AutoCompleteTextView autoCompleteTextViewTipoAtendimento, autoCompleteTextViewTipoAvaliacao,
            autoCompleteTextViewPrograma, autoCompleteTextViewDeslocamento, autoCompleteTextViewDemandaGeral,
            autoCompleteTextViewDemandaEspecifica, autoCompleteTextViewCondicaoLaboral;
    private Button btnAdicionarDeslocamento, btnAdicionarDemandaEspecifica, btnProxima;

    private AtendimentoRegisterFragment3 atendimentoRegisterFragment3;

    private ArrayList<TipoAtendimento> listaTiposAtendimentoRecuperados = new ArrayList<>();
    private ArrayList<TipoAvaliacao> listaTiposAvaliacaoRecuperadas = new ArrayList<>();
    private ArrayList<Programa> listaProgramasRecuperados = new ArrayList<>();
    private ArrayList<Deslocamento> listaDeslocamentosRecuperados = new ArrayList<>();
    private ArrayList<DemandaGeral> listaDemandasGeraisRecuperadas = new ArrayList<>();
    private ArrayList<DemandaEspecifica> listaDemandasEspecificasRecuperadas = new ArrayList<>();
    private ArrayList<CondicaoLaboral> listaCondicoesLaboraisRecuperadas = new ArrayList<>();

    private ArrayList<Deslocamento> arrayListDeslocamentosSelecionados;
    private ArrayList<DemandaEspecifica> arrayListDemandasEspecificasSelecionadas;
    private ArrayAdapter<Deslocamento> adapterDeslocamentos;
    private ArrayAdapter<DemandaEspecifica> adapterDemandasEspecificas;
    private ArrayList<String> listaDeDeslocamentosSelecionados = new ArrayList<>();
    private ArrayList<String> listaDeDemandasEspecificasSelecionadas = new ArrayList<>();
    private String tipoAtendimento, tipoAvaliacao, programa, demandaGeral, condicaoLaboral;



    public AtendimentoRegisterFragment2() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendimento_register2, container, false);

        configurarComponentes(view);
        abrirProximaTela();
        return view;
    }

    private void configurarComponentes(View view) {

        linearLayoutDeslocamento = view.findViewById(R.id.linearLayoutDeslocamento);
        linearLayoutDemandaEspecifica = view.findViewById(R.id.linearLayoutDemandaEspecifica);
        textInputLayoutTipoAvaliacao = view.findViewById(R.id.textInputLayoutTipoAvaliacao);
        textInputLayoutCondicaoLaboral = view.findViewById(R.id.textInputLayoutCondicaoLaboral);
        autoCompleteTextViewTipoAtendimento = view.findViewById(R.id.edtTipoAtendimento);
        autoCompleteTextViewTipoAvaliacao = view.findViewById(R.id.edtTipoAvaliacao);
        autoCompleteTextViewPrograma = view.findViewById(R.id.edtPrograma);
        autoCompleteTextViewDeslocamento = view.findViewById(R.id.edtDeslocamento);
        autoCompleteTextViewDemandaGeral = view.findViewById(R.id.edtDemandaGeral);
        autoCompleteTextViewDemandaEspecifica = view.findViewById(R.id.edtDemandaEspecifica);
        autoCompleteTextViewCondicaoLaboral = view.findViewById(R.id.edtCondicaoLaboral);
        btnAdicionarDeslocamento = view.findViewById(R.id.btnAdicionarDeslocamento);
        btnAdicionarDemandaEspecifica = view.findViewById(R.id.btnAdicionarDemandaEspecifica);
        btnProxima = view.findViewById(R.id.btnProxima);

        popularCampoTipoAtendimentoComDB();
        popularCampoTipoAvaliacaoComDB();
        popularCampoProgramaComDB();
        popularCampoDeslocamentoComDB();
        popularCampoDemandaGeralComDB();
        popularCampoDemandaEspecificaComDB();
        definirVisibilidadeInicialDosComponentes();
        definirVisibilidadeCondicionalDosComponentes();
        popularCampoCondicaoLaboralComDB();
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
        TextChangedListener.textChangedListener(autoCompleteTextViewTipoAtendimento, new TextWatcherCallback() {
            @Override
            public void afterTextChanged(Editable s) {
                if(autoCompleteTextViewTipoAtendimento.getText().toString().contains("Avaliação")) {
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

    private void popularCampoTipoAtendimentoComDB() {

        TipoAtendimentoController tipoServicoController = new TipoAtendimentoController();
        tipoServicoController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            TipoAtendimento tipoAtendimento = new TipoAtendimento();
                            tipoAtendimento.setId(Integer.parseInt(object.getString("id")));
                            tipoAtendimento.setDescricao(object.getString("descricao"));

                            listaTiposAtendimentoRecuperados.add(tipoAtendimento);

                            configurarCampoTipoAtendimento(listaTiposAtendimentoRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoTipoAtendimento(ArrayList<TipoAtendimento> listaTiposAtendimentoRecuperados) {

        ArrayAdapter<TipoAtendimento> adapterTipoAtendimento= new ArrayAdapter<TipoAtendimento>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
               listaTiposAtendimentoRecuperados);
        autoCompleteTextViewTipoAtendimento.setAdapter(adapterTipoAtendimento);
        autoCompleteTextViewTipoAtendimento.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewTipoAtendimento);

    }

    private void popularCampoTipoAvaliacaoComDB() {

        TipoAvaliacaoController tipoAvaliacaoController = new TipoAvaliacaoController();
        tipoAvaliacaoController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            TipoAvaliacao tipoAvaliacao = new TipoAvaliacao();
                            tipoAvaliacao.setId(Integer.parseInt(object.getString("id")));
                            tipoAvaliacao.setDescricao(object.getString("descricao"));

                            listaTiposAvaliacaoRecuperadas.add(tipoAvaliacao);

                            configurarCampoTipoAvaliacao(listaTiposAvaliacaoRecuperadas);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoTipoAvaliacao(ArrayList<TipoAvaliacao> listaTiposAavaliacaoRecuperadas) {

        ArrayAdapter<TipoAvaliacao> adapterTipoAvaliacao= new ArrayAdapter<TipoAvaliacao>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaTiposAavaliacaoRecuperadas);
        autoCompleteTextViewTipoAvaliacao.setAdapter(adapterTipoAvaliacao);
        autoCompleteTextViewTipoAvaliacao.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewTipoAvaliacao);

    }

    private void popularCampoProgramaComDB() {

        ProgramaController programaController = new ProgramaController();
        programaController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Programa programa = new Programa();
                            programa.setId(Integer.parseInt(object.getString("id")));
                            programa.setDescricao(object.getString("descricao"));

                            listaProgramasRecuperados.add(programa);

                            configurarCampoPrograma(listaProgramasRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoPrograma(ArrayList<Programa> listaProgramasRecuperados) {

        ArrayAdapter<Programa> adapterPrograma= new ArrayAdapter<Programa>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaProgramasRecuperados);
        autoCompleteTextViewPrograma.setAdapter(adapterPrograma);
        autoCompleteTextViewPrograma.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewPrograma);

    }

    private void popularCampoDeslocamentoComDB() {

        DeslocamentoController deslocamentoController = new DeslocamentoController();
        deslocamentoController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Deslocamento deslocamento = new Deslocamento();
                            deslocamento.setId(Integer.parseInt(object.getString("id")));
                            deslocamento.setDescricao(object.getString("descricao"));

                            listaDeslocamentosRecuperados.add(deslocamento);

                            configurarCampoDeslocamento(listaDeslocamentosRecuperados);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDeslocamento(ArrayList<Deslocamento> listaDeslocamentosRecuperados) {

        linearLayoutDeslocamento.removeAllViews();

        adapterDeslocamentos = new ArrayAdapter<Deslocamento>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDeslocamentosRecuperados);
        autoCompleteTextViewDeslocamento.setAdapter(adapterDeslocamentos);
        autoCompleteTextViewDeslocamento.setThreshold(1);

        arrayListDeslocamentosSelecionados = new ArrayList<Deslocamento>();
        adapterDeslocamentos = new ArrayAdapter<Deslocamento>(getActivity(),
                android.R.layout.simple_list_item_1,
                arrayListDeslocamentosSelecionados);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDeslocamento);

        btnAdicionarDeslocamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewDeslocamento(getActivity(), autoCompleteTextViewDeslocamento,
                        listaDeslocamentosRecuperados, arrayListDeslocamentosSelecionados, adapterDeslocamentos,
                        linearLayoutDeslocamento);

            }
        });
    }

    private void popularCampoDemandaGeralComDB() {

        DemandaGeralController demandaGeralController = new DemandaGeralController();
        demandaGeralController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            DemandaGeral demandaGeral = new DemandaGeral();
                            demandaGeral.setId(Integer.parseInt(object.getString("id")));
                            demandaGeral.setDescricao(object.getString("descricao"));

                            listaDemandasGeraisRecuperadas.add(demandaGeral);

                            configurarCampoDemandaGeral(listaDemandasGeraisRecuperadas);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDemandaGeral(ArrayList<DemandaGeral> listaDemandasGeraisRecuperadas) {

        ArrayAdapter<DemandaGeral> adapterDemandaGeral= new ArrayAdapter<DemandaGeral>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDemandasGeraisRecuperadas);
        autoCompleteTextViewDemandaGeral.setAdapter(adapterDemandaGeral);
        autoCompleteTextViewDemandaGeral.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDemandaGeral);
    }

    private void popularCampoDemandaEspecificaComDB() {

        DemandaEspecificaController demandaEspecificaController = new DemandaEspecificaController();
        demandaEspecificaController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            DemandaEspecifica demandaEspecifica = new DemandaEspecifica();
                            demandaEspecifica.setId(Integer.parseInt(object.getString("id")));
                            demandaEspecifica.setDescricao(object.getString("descricao"));

                            listaDemandasEspecificasRecuperadas.add(demandaEspecifica);

                            configurarCampoDemandaEspecifica(listaDemandasEspecificasRecuperadas);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoDemandaEspecifica(ArrayList<DemandaEspecifica> listaDemandasEspecificasRecuperadas) {

        linearLayoutDemandaEspecifica.removeAllViews();

        adapterDemandasEspecificas = new ArrayAdapter<DemandaEspecifica>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaDemandasEspecificasRecuperadas);
        autoCompleteTextViewDemandaEspecifica.setAdapter(adapterDemandasEspecificas);
        autoCompleteTextViewDemandaEspecifica.setThreshold(1);

        arrayListDemandasEspecificasSelecionadas = new ArrayList<DemandaEspecifica>();
        adapterDemandasEspecificas = new ArrayAdapter<DemandaEspecifica>(getActivity(),
                android.R.layout.simple_list_item_1,
                arrayListDemandasEspecificasSelecionadas);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewDemandaEspecifica);

        btnAdicionarDemandaEspecifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoRecebido = autoCompleteTextViewDemandaEspecifica.getText().toString();
                TextView textViewDemandasEspecificasSelecionadas = new TextView(getActivity());

                if(textoRecebido.equals(""))
                {
                    Toast.makeText(getActivity(),
                            "Você precisa selecionar uma opção de demanda específica para poder adicionar à lista.",
                            Toast.LENGTH_SHORT).show();
                }else if(!(arrayListDemandasEspecificasSelecionadas.contains(textoRecebido)))
                {
                    textViewDemandasEspecificasSelecionadas.setPadding(0, 10, 0, 10);
                    textViewDemandasEspecificasSelecionadas.setText(textoRecebido);
                    textViewDemandasEspecificasSelecionadas.setTag("deslocamentosSelecionados");

                    linearLayoutDemandaEspecifica.addView(textViewDemandasEspecificasSelecionadas);
                    autoCompleteTextViewDemandaEspecifica.setText("");

                    for(int i = 0; i < listaDemandasEspecificasRecuperadas.size(); i++) {
                        DemandaEspecifica demandaEspecificaSelecionada = listaDemandasEspecificasRecuperadas.get(i);
                        if(demandaEspecificaSelecionada.getDescricao().equals(textoRecebido)) {
                            arrayListDemandasEspecificasSelecionadas.add(demandaEspecificaSelecionada);
                            break;
                        }
                        adapterDemandasEspecificas.notifyDataSetChanged();

                        if(textViewDemandasEspecificasSelecionadas.getText().toString().contains("Óbito(militar)")) {
                            textInputLayoutCondicaoLaboral.setVisibility(View.VISIBLE);
                            autoCompleteTextViewCondicaoLaboral.setVisibility(View.VISIBLE);
                        }
                    }

                }
                else
                {
                    Toast.makeText(getActivity(),
                            "Essa opção de demanda específica já foi adicionada.",
                            Toast.LENGTH_SHORT).show();
                    autoCompleteTextViewDeslocamento.setText("");
                }

                removerItemDaListaDeDemandasEspecificas(textViewDemandasEspecificasSelecionadas, arrayListDemandasEspecificasSelecionadas);
            }
        });

    }

    private void removerItemDaListaDeDemandasEspecificas(TextView textView, ArrayList<DemandaEspecifica> arrayListDemandasEspecificasSelecionadas )
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < arrayListDemandasEspecificasSelecionadas.size(); i++) {
                    DemandaEspecifica demandaEspecificaSelecionada = arrayListDemandasEspecificasSelecionadas.get(i);
                    String dadosDemandaEspecificaSelecionada = demandaEspecificaSelecionada.toString();
                    if(dadosDemandaEspecificaSelecionada.equals(string))
                    {
                        arrayListDemandasEspecificasSelecionadas.remove(demandaEspecificaSelecionada);
                        adapterDemandasEspecificas.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;
                    }
                }

                if(string.equals("Óbito de militar"))
                {
                    textInputLayoutCondicaoLaboral.setVisibility(View.GONE);
                    autoCompleteTextViewCondicaoLaboral.setVisibility(View.GONE);
                }

                textView.setVisibility(View.GONE);

            }
        });
    }

    private void popularCampoCondicaoLaboralComDB() {

        CondicaoLaboralController condicaoLaboralController = new CondicaoLaboralController();
        condicaoLaboralController.listar(getActivity(), new VolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            CondicaoLaboral condicaoLaboral = new CondicaoLaboral();
                            condicaoLaboral.setId(Integer.parseInt(object.getString("id")));
                            condicaoLaboral.setDescricao(object.getString("descricao"));

                            listaCondicoesLaboraisRecuperadas.add(condicaoLaboral);

                            configurarCampoCondicaoLaboral(listaCondicoesLaboraisRecuperadas);

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoCondicaoLaboral(ArrayList<CondicaoLaboral> listaCondicoesLaboraisRecuperadas) {

        ArrayAdapter<CondicaoLaboral> adapterCondicaoLaboral= new ArrayAdapter<CondicaoLaboral>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaCondicoesLaboraisRecuperadas);
        autoCompleteTextViewCondicaoLaboral.setAdapter(adapterCondicaoLaboral);
        autoCompleteTextViewCondicaoLaboral.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewCondicaoLaboral);

    }

    private void receberDadosAtendimentoPreenchidos() {

        for (int i = 0; i < listaTiposAtendimentoRecuperados.size(); i++) {
            TipoAtendimento tipoAtendimentoSelecionado = listaTiposAtendimentoRecuperados.get(i);
            if (tipoAtendimentoSelecionado.getDescricao().equals(autoCompleteTextViewTipoAtendimento.getText().toString())) {
                tipoAtendimento = String.valueOf(tipoAtendimentoSelecionado.getId());
                break;
            }
        }


        if (autoCompleteTextViewTipoAtendimento.getText().toString().contains("Avaliação")) {
            for (int i = 0; i < listaTiposAvaliacaoRecuperadas.size(); i++) {
                TipoAvaliacao tipoAvaliacaoSelecionada = listaTiposAvaliacaoRecuperadas.get(i);
                if (tipoAvaliacaoSelecionada.getDescricao().equals(autoCompleteTextViewTipoAvaliacao.getText().toString())) {
                    tipoAvaliacao = String.valueOf(tipoAvaliacaoSelecionada.getId());
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaTiposAvaliacaoRecuperadas.size(); i++) {
                TipoAvaliacao tipoAvaliacaoSelecionada = listaTiposAvaliacaoRecuperadas.get(i);
                if (tipoAvaliacaoSelecionada.getDescricao().equals("Não se aplica")) {
                    tipoAvaliacao = String.valueOf(tipoAvaliacaoSelecionada.getId());
                }
            }
        }


        if (!autoCompleteTextViewPrograma.getText().toString().isEmpty()) {
            for (int i = 0; i < listaProgramasRecuperados.size(); i++) {
                Programa programaSelecionado = listaProgramasRecuperados.get(i);
                if (programaSelecionado.getDescricao().equals(autoCompleteTextViewPrograma.getText().toString())) {
                    programa = String.valueOf(programaSelecionado.getId());
                    break;
                }
            }
        }

            for (int i = 0; i < arrayListDeslocamentosSelecionados.size(); i++) {
                Deslocamento deslocamentoSelecionado = arrayListDeslocamentosSelecionados.get(i);
                listaDeDeslocamentosSelecionados.add(String.valueOf(deslocamentoSelecionado.getId()));
        }


        for (int i = 0; i < listaDemandasGeraisRecuperadas.size(); i++) {
            DemandaGeral demandaGeralSelecionada = listaDemandasGeraisRecuperadas.get(i);
            if (demandaGeralSelecionada.getDescricao().equals(autoCompleteTextViewDemandaGeral.getText().toString())) {
                demandaGeral = String.valueOf(demandaGeralSelecionada.getId());
                break;
            }
        }

        for (int i = 0; i < arrayListDemandasEspecificasSelecionadas.size(); i++) {
            DemandaEspecifica demandaEspecificaSelecionado = arrayListDemandasEspecificasSelecionadas.get(i);
            listaDeDemandasEspecificasSelecionadas.add(String.valueOf(demandaEspecificaSelecionado.getId()));
        }

        if (listaDeDemandasEspecificasSelecionadas.toString().contains("Óbito(militar")) {
            for (int i = 0; i < listaCondicoesLaboraisRecuperadas.size(); i++) {
                CondicaoLaboral condicaoLaboralSelecionada = listaCondicoesLaboraisRecuperadas.get(i);
                if (condicaoLaboralSelecionada.getDescricao().equals(autoCompleteTextViewCondicaoLaboral.getText().toString())) {
                    condicaoLaboral = String.valueOf(condicaoLaboralSelecionada.getId());
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaCondicoesLaboraisRecuperadas.size(); i++) {
                CondicaoLaboral condicaoLaboralSelecionada = listaCondicoesLaboraisRecuperadas.get(i);
                if (condicaoLaboralSelecionada.getDescricao().equals("Não se aplica")) {
                    condicaoLaboral = String.valueOf(condicaoLaboralSelecionada.getId());
                }
            }
        }
    }

    private boolean validarCadastroUsuario() {
        receberDadosAtendimentoPreenchidos();

        if (!TextUtils.isEmpty(tipoAtendimento)) {

            if (!tipoAtendimento.contains("Avaliação")
                    || !TextUtils.isEmpty(tipoAvaliacao)) {

                if (!TextUtils.isEmpty(programa)) {

                    if (!listaDeDeslocamentosSelecionados.isEmpty()) {

                        if (!TextUtils.isEmpty(demandaGeral)) {

                            if (!listaDeDemandasEspecificasSelecionadas.isEmpty()) {

                                if (listaCondicoesLaboraisRecuperadas.
                                        get(Integer.parseInt(condicaoLaboral) - 1).
                                        getDescricao().equals("Não se aplica")
                                        || !TextUtils.isEmpty(condicaoLaboral)) {

                                    encapsularValoresParaEnvio();
                                    return true;

                                }
                                else {
                                    Toast.makeText(getActivity(),
                                            "O campo CONDIÇÃO LABORAL é obrigatório.",
                                            Toast.LENGTH_SHORT).show();
                                    autoCompleteTextViewCondicaoLaboral.requestFocus();
                                    return false; }

                            }
                            else {
                                Toast.makeText(getActivity(),
                                        "É necessário adicionar ao menos um item em DEMANDA ESPECÍFICA.",
                                        Toast.LENGTH_SHORT).show();
                                autoCompleteTextViewDemandaEspecifica.requestFocus();
                                return false; }


                        }
                        else {
                            Toast.makeText(getActivity(),
                                    "O campo DEMANDA GERAL é obrigatório.",
                                    Toast.LENGTH_SHORT).show();
                            autoCompleteTextViewDemandaGeral.requestFocus();
                            return false; }

                    }
                    else {
                        Toast.makeText(getActivity(),
                                "É necessário adicionar ao menos um item em DESLOCAMENTO.",
                                Toast.LENGTH_SHORT).show();
                        autoCompleteTextViewDeslocamento.requestFocus();
                        return false; }

                }
                else {
                    Toast.makeText(getActivity(),
                            "O campo PROGRAMA é obrigatório.",
                            Toast.LENGTH_SHORT).show();
                    autoCompleteTextViewPrograma.requestFocus();
                    return false; }

            }
            else {
                Toast.makeText(getActivity(),
                        "O campo TIPO DE AVALIAÇÃO é obrigatório.",
                        Toast.LENGTH_SHORT).show();
                autoCompleteTextViewTipoAvaliacao.requestFocus();
                return false; }

        }
        else {
            Toast.makeText(getActivity(),
                    "O campo TIPO DE ATENDIMENTO é obrigatório.",
                    Toast.LENGTH_SHORT).show();
            autoCompleteTextViewTipoAvaliacao.requestFocus();
            return false; }
    }

    private Bundle encapsularValoresParaEnvio()
    {
        Bundle valoresRecebidosFragment1 =  recuperarDadosAtendimentoRegisterFragment1();
        Bundle bundle = new Bundle();

        bundle.putBundle("valoresRecebidosFragment1", valoresRecebidosFragment1);
        bundle.putString("tipo", tipoAtendimento);
        bundle.putString("tipoAvaliacao", tipoAvaliacao);
        bundle.putString("programa", programa);
        bundle.putStringArrayList("listaDeslocamentos", listaDeDeslocamentosSelecionados);
        bundle.putString("demandaGeral", demandaGeral);
        bundle.putStringArrayList("listaDemandasEspecificas", listaDeDemandasEspecificasSelecionadas);
        bundle.putString("condicaoLaboral", condicaoLaboral);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCadastroUsuario())
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

        listaTiposAtendimentoRecuperados.clear();
        listaTiposAvaliacaoRecuperadas.clear();
        listaProgramasRecuperados.clear();
        listaDeslocamentosRecuperados.clear();
        listaDemandasGeraisRecuperadas.clear();
        listaDemandasEspecificasRecuperadas.clear();
        listaCondicoesLaboraisRecuperadas.clear();
        super.onResume();
    }
}