package com.br.ciapoficial.view.fragments;

import android.os.Build;
import android.os.Bundle;
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
import com.br.ciapoficial.controller.AcessoAtendimentoController;
import com.br.ciapoficial.controller.EspecialistaController;
import com.br.ciapoficial.controller.ModalidadeController;
import com.br.ciapoficial.controller.UnidadeController;
import com.br.ciapoficial.controller.UsuarioController;
import com.br.ciapoficial.helper.AddRemoveTextView;
import com.br.ciapoficial.helper.Calendar;
import com.br.ciapoficial.helper.DateFormater;
import com.br.ciapoficial.helper.DropDownClick;
import com.br.ciapoficial.validation.FieldValidator;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.helper.Mascaras;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Especialista;
import com.br.ciapoficial.model.Unidade;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.in_servico.Acesso;
import com.br.ciapoficial.model.in_servico.Modalidade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.SneakyThrows;

public class AtendimentoRegisterFragment1 extends Fragment {

    private LinearLayout linearLayoutEspecialista, linearLayoutAtendido;
    private AutoCompleteTextView autoCompleteTextViewDataDoServico, autoCompleteTextViewEspecialista,
            autoCompleteTextViewAtendido, autoCompleteTextViewUnidadeDoServico, autoCompleteTextViewModalidadeDoServico,
            autoCompleteTextViewAcessoAoServico;
    private Button btnAdicionarEspecialista, btnAdicionarAtendido, btnProxima;

    private AtendimentoRegisterFragment2 atendimentoRegisterFragment2;

    private List<Especialista> listaDeEspecialistasRecuperados = new ArrayList<>();
    private List<Usuario> listaDeAtendidosRecuperados = new ArrayList<>();
    private List<Unidade> listaDeUnidadesRecuperadas = new ArrayList<>();
    private List<Modalidade> listaDeModalidadesRecuperadas = new ArrayList<>();
    private List<Acesso> listaAcessosAoServicoRecuperados = new ArrayList<>();

    private List<Especialista> listaDeEspecialistasSelecionadosNaoValidados;
    private List<Usuario> listaDeAtendidosSelecionadosNaoValidados;
    private ArrayAdapter<Especialista> adapterEspecialistas;
    private ArrayAdapter<Usuario> adapterAtendidos;

    private List<Especialista> listaDeEspecialistasSelecionadosValidados = new ArrayList<>();
    private List<Usuario> listaDeAtendidosSelecionadosValidados = new ArrayList<>();
    private LocalDate datatDoServico;
    private Unidade unidadeDoServico;
    private Modalidade modalidadeDoServico;
    private Acesso acessoAoServico;


    public AtendimentoRegisterFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atendimento_register1, container, false);

        configurarComponentes(view);
        configurarMascaraParaData();
        adicionarDataAtualNoEditTextData();
        adicionarCalendarioNoEditTextData();
        popularCampoEspecialistaComDB();
        popularCampoAtendidoComDB();
        popularCampoUnidadeDoServicoComDB();
        popularCampoModalidadeDoServicoComDB();
        popularCampoAcessoAoServicoComDb();
        abrirProximaTela();

        return view;
    }

    private void configurarComponentes(View view) {

        linearLayoutEspecialista = view.findViewById(R.id.linearLayoutEspecialista);
        linearLayoutAtendido = view.findViewById(R.id.linearLayoutAtendido);
        autoCompleteTextViewDataDoServico = view.findViewById(R.id.edtDataDoServico);
        autoCompleteTextViewEspecialista = view.findViewById(R.id.edtEspecialista);
        autoCompleteTextViewAtendido = view.findViewById(R.id.edtAtendido);
        autoCompleteTextViewUnidadeDoServico = view.findViewById(R.id.edtUnidadeDoServico);
        autoCompleteTextViewModalidadeDoServico = view.findViewById(R.id.edtModalidadeDoServico);
        autoCompleteTextViewAcessoAoServico = view.findViewById(R.id.edtAcessoAoServico);
        btnAdicionarEspecialista = view.findViewById(R.id.btnAdicionarEspecialista);
        btnAdicionarAtendido = view.findViewById(R.id.btnAdicionarAtendido);
        btnProxima = view.findViewById(R.id.btnProxima);
    }

    private void configurarMascaraParaData()
    {
        Mascaras mascara = new Mascaras();
        mascara.criarMascaraParaData(autoCompleteTextViewDataDoServico);
    }

    private void adicionarDataAtualNoEditTextData()
    {
        autoCompleteTextViewDataDoServico.setText(DateFormater.dataAtual());
    }

    private void adicionarCalendarioNoEditTextData()
    {
        Calendar.pegarCalendarioDataAtual(getActivity(), autoCompleteTextViewDataDoServico);
    }

    private void criarTextViewParaEspecialistasSelecionados()
    {
        TextView textView = new TextView(getContext());
            for(Especialista especialistaRecebido : listaDeEspecialistasSelecionadosValidados) {
                String textoRecebido = especialistaRecebido.toString();

                textView.setPadding(0, 10, 0, 10);
                textView.setText(textoRecebido);
                textView.setTag("lista");

                linearLayoutEspecialista.addView(textView);
        }
        removerItemDaListaDeOficiaisResponsaveis(textView, listaDeEspecialistasSelecionadosNaoValidados, adapterEspecialistas);
    }

    public static void removerItemDaListaDeOficiaisResponsaveis(TextView textView, List<Especialista> listaDisplay,
                                                                ArrayAdapter<Especialista> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<Especialista> iter = listaDisplay.iterator(); iter.hasNext();)
                {
                    Especialista oficialSelecionado = iter.next();
                    if(oficialSelecionado.toString().equals(string))
                    {
                        listaDisplay.remove(oficialSelecionado);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    private void criarTextViewParaAtendidosSelecionados()
    {
        TextView textView = new TextView(getContext());
        for(Usuario usuarioRecebido : listaDeAtendidosSelecionadosValidados) {
            String textoRecebido = usuarioRecebido.toString();

            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayoutAtendido.addView(textView);
        }
        removerItemDaListaDeAtendidosSelecionados(textView, listaDeAtendidosSelecionadosNaoValidados, adapterAtendidos);
    }

    public static void removerItemDaListaDeAtendidosSelecionados(TextView textView, List<Usuario> listaDisplay,
                                                                ArrayAdapter<Usuario> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<Usuario> iter = listaDisplay.iterator(); iter.hasNext();)
                {
                    Usuario atendidoSelecionado = iter.next();
                    if(atendidoSelecionado.toString().equals(string))
                    {
                        listaDisplay.remove(atendidoSelecionado);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    private void popularCampoEspecialistaComDB() {

        EspecialistaController especialistaController = new EspecialistaController();
        especialistaController.listar(getActivity(), new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSucess(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GsonBuilder customGson = new GsonBuilder();
                        customGson.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                        customGson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                        Gson gson = customGson.create();
                        Especialista especialista = gson.fromJson(String.valueOf(jsonObject), Especialista.class);

                        listaDeEspecialistasRecuperados.add(especialista);
                        configurarCampoOficialResponsavel(listaDeEspecialistasRecuperados);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoOficialResponsavel(List<Especialista> listaDeEspecialistasRecuperados) {

        linearLayoutEspecialista.removeAllViews();

        adapterEspecialistas = new ArrayAdapter<Especialista>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                (listaDeEspecialistasRecuperados));
        autoCompleteTextViewEspecialista.setAdapter(adapterEspecialistas);
        autoCompleteTextViewEspecialista.setThreshold(1);

        listaDeEspecialistasSelecionadosNaoValidados = new ArrayList<Especialista>();
        adapterEspecialistas = new ArrayAdapter<Especialista>(getActivity(),
                android.R.layout.simple_list_item_1,
                listaDeEspecialistasSelecionadosNaoValidados);

        if(listaDeEspecialistasSelecionadosValidados == null) {
            linearLayoutEspecialista.removeAllViews();
        }else{
            criarTextViewParaEspecialistasSelecionados();
            for(Especialista especialistaRecebido : listaDeEspecialistasSelecionadosValidados) {
                listaDeEspecialistasSelecionadosNaoValidados.add(especialistaRecebido);
            }
        }

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewEspecialista);

        btnAdicionarEspecialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewEspecialista(getActivity(), autoCompleteTextViewEspecialista,
                        AtendimentoRegisterFragment1.this.listaDeEspecialistasRecuperados, listaDeEspecialistasSelecionadosNaoValidados,
                        adapterEspecialistas, linearLayoutEspecialista);
            }
        });
    }

    private void popularCampoAtendidoComDB() {

        UsuarioController atendidoController = new UsuarioController();
        atendidoController.listar(getActivity(), new IVolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GsonBuilder customGson = new GsonBuilder();
                        customGson.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
                        customGson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                        Gson gson = customGson.create();
                        Usuario atendido = gson.fromJson(String.valueOf(jsonObject), Usuario.class);

                        listaDeAtendidosRecuperados.add(atendido);
                        configurarCampoAtendido(listaDeAtendidosRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoAtendido(List<Usuario> listaAtendidosMySql) {

        linearLayoutAtendido.removeAllViews();

        adapterAtendidos = new ArrayAdapter<Usuario>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaAtendidosMySql);
        autoCompleteTextViewAtendido.setAdapter(adapterAtendidos);
        autoCompleteTextViewAtendido.setThreshold(1);

        listaDeAtendidosSelecionadosNaoValidados = new ArrayList<Usuario>();
        adapterAtendidos = new ArrayAdapter<Usuario>(getActivity(),
                android.R.layout.simple_list_item_1,
                listaDeAtendidosSelecionadosNaoValidados);

        if(listaDeAtendidosSelecionadosValidados == null) {
            linearLayoutEspecialista.removeAllViews();
        }else{
            criarTextViewParaAtendidosSelecionados();
            for(Usuario atendidoRecebido : listaDeAtendidosSelecionadosValidados) {
                listaDeAtendidosSelecionadosNaoValidados.add(atendidoRecebido);
            }
        }

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewAtendido);

        btnAdicionarAtendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRemoveTextView.adicionarTextViewAtendido(getActivity(), autoCompleteTextViewAtendido,
                        listaDeAtendidosRecuperados, listaDeAtendidosSelecionadosNaoValidados, adapterAtendidos, linearLayoutAtendido);
            }
        });
    }

    private void popularCampoUnidadeDoServicoComDB()
    {
        UnidadeController unidadeController = new UnidadeController();
        unidadeController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Unidade unidade = new Unidade();
                        unidade.setId(Integer.parseInt(object.getString("id")));
                        unidade.setNome(object.getString("nome"));

                        listaDeUnidadesRecuperadas.add(unidade);
                        configurarCampoUnidadeServico(listaDeUnidadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoUnidadeServico(List<Unidade> listaUnidadesRecuperadas) {

        ArrayAdapter<Unidade> adapterUnidade= new ArrayAdapter<Unidade>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaUnidadesRecuperadas);
        autoCompleteTextViewUnidadeDoServico.setAdapter(adapterUnidade);
        autoCompleteTextViewUnidadeDoServico.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewUnidadeDoServico);

    }

    private void popularCampoModalidadeDoServicoComDB()
    {
        ModalidadeController modalidadeController = new ModalidadeController();
        modalidadeController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Modalidade modalidade = new Modalidade();
                        modalidade.setId(Integer.parseInt(object.getString("id")));
                        modalidade.setNome(object.getString("nome"));

                        listaDeModalidadesRecuperadas.add(modalidade);
                        configurarCampoModalidadeDoServico(listaDeModalidadesRecuperadas);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoModalidadeDoServico(List<Modalidade> listaModalidadesRecuperadas) {

        ArrayAdapter<Modalidade> adapterModalidade= new ArrayAdapter<Modalidade>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaModalidadesRecuperadas);
        autoCompleteTextViewModalidadeDoServico.setAdapter(adapterModalidade);
        autoCompleteTextViewModalidadeDoServico.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewModalidadeDoServico);

    }

    private void popularCampoAcessoAoServicoComDb()
    {
        AcessoAtendimentoController acessoAtendimentoController = new AcessoAtendimentoController();

        acessoAtendimentoController.listar(getActivity(), new IVolleyCallback() {
            @Override
            public void onSucess(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Acesso acesso = new Acesso();
                        acesso.setId(Integer.parseInt(object.getString("id")));
                        acesso.setNome(object.getString("nome"));

                        listaAcessosAoServicoRecuperados.add(acesso);
                        configurarCampoAcessoAoServico(listaAcessosAoServicoRecuperados);

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void configurarCampoAcessoAoServico(List<Acesso> listaAcessosAtendimentosRecuperados) {

        ArrayAdapter<Acesso> adapterAcessoServico= new ArrayAdapter<Acesso>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                listaAcessosAtendimentosRecuperados);
        autoCompleteTextViewAcessoAoServico.setAdapter(adapterAcessoServico);
        autoCompleteTextViewAcessoAoServico.setThreshold(1);

        DropDownClick.showDropDown(getActivity(), autoCompleteTextViewAcessoAoServico);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarCadastroDoServico() throws ParseException
    {
        if (
                FieldValidator.validarDataDoServico(autoCompleteTextViewDataDoServico) &&
                FieldValidator.isListEmptyOrNull(autoCompleteTextViewEspecialista,
                        listaDeEspecialistasSelecionadosNaoValidados, "ESPECIALISTA") &&
                FieldValidator.isListEmptyOrNull(autoCompleteTextViewAtendido,
                        listaDeAtendidosSelecionadosNaoValidados, "ATENDIDO") &&
                FieldValidator.validarUnidade(autoCompleteTextViewUnidadeDoServico, listaDeUnidadesRecuperadas) &&
                FieldValidator.validarModalidade(autoCompleteTextViewModalidadeDoServico, listaDeModalidadesRecuperadas) &&
                FieldValidator.validarAcessoAtendimento(autoCompleteTextViewAcessoAoServico, listaAcessosAoServicoRecuperados))
{
            receberDadosDoServicoPreenchidos();
            return true;
        }else { return false; }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void receberDadosDoServicoPreenchidos() throws ParseException {
        datatDoServico = DateFormater.StringToLocalDate(autoCompleteTextViewDataDoServico.getText().toString().trim());
        listaDeEspecialistasSelecionadosValidados = listaDeEspecialistasSelecionadosNaoValidados;
        listaDeAtendidosSelecionadosValidados = listaDeAtendidosSelecionadosNaoValidados;

        for (Unidade unidadeSelecionada : listaDeUnidadesRecuperadas)
        {
            if (unidadeSelecionada.getNome().equals
                    (autoCompleteTextViewUnidadeDoServico.getText().toString().trim()))
                unidadeDoServico = unidadeSelecionada;
        }

        for (Modalidade modalidadeSelecionado : listaDeModalidadesRecuperadas)
        {
            if (modalidadeSelecionado.getNome().equals
                    (autoCompleteTextViewModalidadeDoServico.getText().toString().trim()))
                modalidadeDoServico = modalidadeSelecionado;
        }


        for (Acesso acessoSelecionado : listaAcessosAoServicoRecuperados)
        {
            if (acessoSelecionado.getNome().equals
                    (autoCompleteTextViewAcessoAoServico.getText().toString().trim()))
                acessoAoServico = acessoSelecionado;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bundle encapsularValoresParaEnvio()
    {
        Bundle bundle = new Bundle();

        bundle.putString("data", DateFormater.localDateToString(datatDoServico));
        bundle.putSerializable("listaDeEspecialistas", (Serializable) listaDeEspecialistasSelecionadosValidados);
        bundle.putSerializable("listaDeAtendidos", (Serializable) listaDeAtendidosSelecionadosValidados);
        bundle.putSerializable("unidade", unidadeDoServico);
        bundle.putSerializable("modalidade", modalidadeDoServico);
        bundle.putSerializable("acesso", acessoAoServico);

        return bundle;
    }

    private void abrirProximaTela() {

        btnProxima.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(validarCadastroDoServico())
                {
                    Bundle valoresEncapsuladosParaEnvio = encapsularValoresParaEnvio();

                    atendimentoRegisterFragment2 = new AtendimentoRegisterFragment2();
                    atendimentoRegisterFragment2.setArguments(valoresEncapsuladosParaEnvio);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameConteudo, atendimentoRegisterFragment2);
                    fragmentTransaction.addToBackStack(null).commit();
                }

            }
        });

    }

    @Override
    public void onResume() {
        listaDeEspecialistasRecuperados.clear();
        listaDeAtendidosRecuperados.clear();
        listaDeUnidadesRecuperadas.clear();
        listaDeModalidadesRecuperadas.clear();
        listaAcessosAoServicoRecuperados.clear();
        super.onResume();
    }
}