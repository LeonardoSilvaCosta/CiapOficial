package com.br.ciapoficial.helper;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.ciapoficial.model.Especialista;
import com.br.ciapoficial.model.PessoaTelefoneId;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.in_servico.Deslocamento;
import com.br.ciapoficial.model.in_servico.DocumentoProduzido;
import com.br.ciapoficial.model.in_servico.Encaminhamento;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AddRemoveTextView {

//    public static void adicionarAutoCompletTextView(Context context, EditText campo, List<Object> listaDeDadosRecuperados,
//                                                    List<Type> listaDisplay, ArrayAdapter<Type> adapter, LinearLayout linearLayout,
//                                                    String mensagemParaCampoVazio, String mensagemParaDadoJaInserido)
//    {
//
//        String textoRecebido = campo.getText().toString().trim();
//        TextView textView = new TextView(context);
//        if (textoRecebido.isEmpty()) {
//            campo.setError(mensagemParaCampoVazio);
//            campo.requestFocus();
//            DellayAction.clearErrorAfter2Seconds((AutoCompleteTextView) campo);
//        } else {
//            if (!(listaDisplay.toString().contains(textoRecebido))) {
//                textView.setPadding(0, 10, 0, 10);
//                textView.setText(textoRecebido);
//                textView.setTag("lista");
//
//                linearLayout.addView(textView);
//
//                campo.setText("");
//
//                for(Object objeto : listaDeDadosRecuperados) {
//                    if(objeto.toString().equals(textoRecebido)) {
//                        listaDisplay.add(objeto);
//                        break;
//                    }
//                }
//
//            } else {
//                campo.setError(mensagemParaDadoJaInserido);
//                campo.requestFocus();
//                DellayAction.clearErrorAfter2Seconds((AutoCompleteTextView) campo);
//                campo.setText("");
//            }
//        }
//        removerItemDaListaDeObjetos(textView, listaDisplay, adapter);
//    }

    public static void removerItemDaListaDeObjetos(TextView textView, List<Object> listaDisplay,
                                                   ArrayAdapter<Object> adapter)
    {
        textView.setOnClickListener(v -> {

            String string = textView.getText().toString().trim();
            for(Iterator<Object> iter = listaDisplay.iterator(); iter.hasNext();)
            {
                Object objetoSelecionado = iter.next();
                if(objetoSelecionado.toString().equals(string))
                {
                    listaDisplay.remove(objetoSelecionado);
                    adapter.notifyDataSetChanged();

                    textView.setVisibility(View.GONE);
                    break;

                }
            }
        });
    }

    public static void adicionarTextViewEspecialista(Context context, AutoCompleteTextView campo,
                                                     List<Especialista> oficiasRecuperados, List<Especialista> listaDisplay,
                                                     ArrayAdapter<Especialista> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();
        TextView textView = new TextView(context);
        if (textoRecebido.isEmpty()) {
            campo.setError("O campo para inser????o de OFICIAL RESPONS??VEL est?? vazio.");
            campo.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campo);
        } else {
            if (!(listaDisplay.toString().contains(textoRecebido))) {
                textView.setPadding(0, 10, 0, 10);
                textView.setText(textoRecebido);
                textView.setTag("lista");

                linearLayout.addView(textView);

                campo.setText("");

                for(Especialista oficial : oficiasRecuperados) {
                    if(oficial.toString().equals(textoRecebido)) {
                        listaDisplay.add(oficial);
                        break;
                    }
                }

            } else {
                campo.setError("Esse OFICIAL j?? foi inserido.");
                campo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campo);
                campo.setText("");
            }
        }
        removerItemDaListaDeOficiaisResponsaveis(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeOficiaisResponsaveis(TextView textView, List<Especialista> listaDisplay,
                                                                ArrayAdapter<Especialista> adapter)
    {
        textView.setOnClickListener(v -> {

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
        });
    }

    public static void adicionarTextViewTelefoneUsuarioObjeto(Context context, TextInputEditText campo,
                                                              List<Telefone> listaDeTelefonesRecebidosParaAtualizar,
                                                              LinearLayout linearLayout) {


//        for (Iterator<Telefone> telefone = listaDeTelefonesPreviamenteAdicionados.iterator(); telefone.hasNext();) {
//            listaDeTelefonesRecebidosParaAtualizar.add(telefone.next());
//        }

        String textoRecebido = Mascaras.removerMascaras(Objects.requireNonNull(campo.getText()).toString());
        TextView textView = new TextView(context);

        if (textoRecebido.isEmpty()) {
            campo.setError("O campo para inser????o de TELEFONE est?? vazio");
            campo.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campo);
        } else {
            if (!(textoRecebido.length() < 11) && Patterns.PHONE.matcher(textoRecebido).matches()) {
                if(!(listaDeTelefonesRecebidosParaAtualizar.toString().contains(textoRecebido)))
                {
                    textView.setPadding(0, 10, 0, 10);
                    textView.setText(textoRecebido);
                    textView.setTag("lista");

                    linearLayout.addView(textView);

                    campo.setText("");

                    PessoaTelefoneId pessoaTelefoneId = new PessoaTelefoneId();
                    pessoaTelefoneId.setTelefone(textoRecebido);
                    listaDeTelefonesRecebidosParaAtualizar.add(new Telefone(pessoaTelefoneId));


                } else {
                    campo.setError("Esse TELEFONE j?? foi inserido.");
                    campo.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campo);
                    campo.setText("");
                }

            }else{
                campo.setError("Digite um n??mero de TELEFONE v??lido.");
                campo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campo);
            }
        }
        removerItemDaListaDeTelefonesObjeto(textView, listaDeTelefonesRecebidosParaAtualizar);

    }

    public static void removerItemDaListaDeTelefonesObjeto(TextView textView, List<Telefone> listaDisplay)
    {
        textView.setOnClickListener(v -> {

            String string = textView.getText().toString();
            for(Iterator<Telefone> iter = listaDisplay.iterator(); iter.hasNext();)
            {
                String telefoneSelecionado = iter.next().getPessoaTelefoneId().getTelefone();
                if(telefoneSelecionado.equals(string))
                {
                    listaDisplay.remove(telefoneSelecionado);

                    textView.setVisibility(View.GONE);
                    break;

                }
            }
        });
    }

    public static void adicionarTextViewTelefoneUsuarioString(Context context, TextInputEditText campo,
                                                              List<Telefone> telefoneListadosParaCadastro,
                                                              LinearLayout linearLayout) {

        String textoRecebido = Mascaras.removerMascaras(campo.getText().toString());
        TextView textView = new TextView(context);
        if (textoRecebido.isEmpty()) {
            campo.setError("O campo para inser????o de TELEFONE est?? vazio.");
            campo.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campo);
        } else {
            if (!(textoRecebido.length() < 11) && Patterns.PHONE.matcher(textoRecebido).matches()) {
                if (!(telefoneListadosParaCadastro.toString().contains(textoRecebido))) {
                    textView.setPadding(0, 10, 0, 10);
                    textView.setText(textoRecebido);
                    textView.setTag("lista");

                    linearLayout.addView(textView);

                    campo.setText("");

                    PessoaTelefoneId pessoaTelefoneId = new PessoaTelefoneId();
                    pessoaTelefoneId.setTelefone(textoRecebido);
                    telefoneListadosParaCadastro.add(new Telefone(pessoaTelefoneId));

                } else {
                    campo.setError("Esse TELEFONE j?? foi inserido.");
                    campo.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campo);
                    campo.setText("");
                }
            } else {
                campo.setError("Digite um n??mero de TELEFONE v??lido.");
                campo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campo);
            }
        }
        removerItemDaListaDeTelefonesString(textView, telefoneListadosParaCadastro);
    }

    public static void removerItemDaListaDeTelefonesString(TextView textView,
                                                           List<Telefone> listaDeTelefonesInseridos)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<Telefone> i = listaDeTelefonesInseridos.listIterator(); i.hasNext(); )
                {
                    String telefoneSelecionado = i.next().toString();
                    if(telefoneSelecionado.equals(string))
                    {
                        listaDeTelefonesInseridos.remove(0);

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    public static void removerItemDaListaDeTelefonesEListarParaDelecao(List<TextView> textViews, List<Telefone> listaDisplay,
                                                                       List<Telefone> telefoneParaDeletar)
    {
        for(TextView textView : textViews) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String string = textView.getText().toString();
                    for(Iterator<Telefone> i = listaDisplay.listIterator(); i.hasNext();)
                    {
                        Telefone telefoneSelecionado = i.next();
                        if(telefoneSelecionado.toString().equals(string))
                        {
                            telefoneParaDeletar.add(telefoneSelecionado);
                            listaDisplay.remove(telefoneSelecionado);

                            textView.setVisibility(View.GONE);
                            break;

                        }
                    }
                }
            });
        }

    }

    public static void adicionarTextViewAtendido(Context context, AutoCompleteTextView campo,
                                                 List<Usuario> usuariosRecuperados, List<Usuario> listaDisplay,
                                                 ArrayAdapter<Usuario> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();
        TextView textView = new TextView(context);
        if (textoRecebido.isEmpty()) {
            campo.setError("O campo para inser????o de ATENDIDO est?? vazio.");
            campo.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campo);
        } else {
            if (!(listaDisplay.toString().contains(textoRecebido))) {
                textView.setPadding(0, 10, 0, 10);
                textView.setText(textoRecebido);
                textView.setTag("lista");

                linearLayout.addView(textView);

                campo.setText("");

                for(Usuario atendido : usuariosRecuperados) {
                    if(atendido.toString().equals(textoRecebido)) {
                        listaDisplay.add(atendido);
                        break;
                    }
                }

            } else {
                campo.setError("Esse ATENDIDO j?? foi inserido.");
                campo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campo);
                campo.setText("");
            }
        }
        removerItemDaListaDeAtendidos(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeAtendidos(TextView textView, List<Usuario> listaDisplay,
                                                     ArrayAdapter<Usuario> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(Iterator<Usuario> iter = listaDisplay.iterator(); iter.hasNext();)
                {
                    Usuario oficialSelecionado = iter.next();
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

    public static void adicionarTextViewDeslocamento(Context context, AutoCompleteTextView campo,
                                                     List<Deslocamento> deslocamentosRecuperados,
                                                     List<Deslocamento> listaDisplay,
                                                     ArrayAdapter<Deslocamento> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();
        TextView textView = new TextView(context);
        if (textoRecebido.isEmpty()) {
            campo.setError("O campo para inser????o de DESLOCAMENTO est?? vazio.");
            campo.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campo);
        } else {
            if (!(listaDisplay.toString().contains(textoRecebido))) {
                textView.setPadding(0, 10, 0, 10);
                textView.setText(textoRecebido);
                textView.setTag("lista");

                linearLayout.addView(textView);

                campo.setText("");

                for(Deslocamento deslocamento : deslocamentosRecuperados) {
                    if(deslocamento.toString().equals(textoRecebido)) {
                        listaDisplay.add(deslocamento);
                        break;
                    }
                }

            } else {
                campo.setError("Esse DESLOCAMENTO j?? foi inserido.");
                campo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campo);
                campo.setText("");
            }
        }
        removerItemDaListaDeDeslocamento(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeDeslocamento(TextView textView, List<Deslocamento> listaDisplay,
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

    public static void adicionarTextViewDocumentoProduzido(Context context, AutoCompleteTextView campo,
                                                           List<DocumentoProduzido> documentosRecuperados,
                                                           List<DocumentoProduzido> listaDisplay,
                                                           ArrayAdapter<DocumentoProduzido> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Voc?? precisa selecionar um item para poder adicionar ?? lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(listaDisplay.toString().contains(textoRecebido)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayout.addView(textView);
            campo.setText("");

            for(int i = 0; i < documentosRecuperados.size(); i++) {
                DocumentoProduzido documentoSelecionado = documentosRecuperados.get(i);
                if(documentoSelecionado.getNome().equals(textoRecebido)) {
                    listaDisplay.add(documentoSelecionado);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item j?? foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeDocumento(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeDocumento(TextView textView, List<DocumentoProduzido> listaDisplay,
                                                     ArrayAdapter<DocumentoProduzido> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    DocumentoProduzido documentoSelecionado = listaDisplay.get(i);
                    String dadosDocumentoSelecionado = documentoSelecionado.toString();
                    if(dadosDocumentoSelecionado.equals(string))
                    {
                        listaDisplay.remove(documentoSelecionado);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    public static void adicionarTextViewEncaminhamento(Context context, AutoCompleteTextView campo,
                                                       List<Encaminhamento> encaminhamentosRecuperados,
                                                       List<Encaminhamento> listaDisplay,
                                                       ArrayAdapter<Encaminhamento> adapterEncaminhamento,
                                                       LinearLayout linearLayout)
    {

        String encaminhamentoDigitado = campo.getText().toString();

        TextView textView = new TextView(context);
        if(encaminhamentoDigitado.isEmpty()) {
            campo.setError(
                    "O campo para inser????o de ENCAMINHAMENTO est?? vazio.");
            campo.requestFocus();
            DellayAction.clearErrorAfter2Seconds(campo);
        }else {
            if (!(listaDisplay.toString().contains(encaminhamentoDigitado))) {
                textView.setPadding(0, 10, 0, 10);
                textView.setText(encaminhamentoDigitado);
                textView.setTag("lista");

                linearLayout.addView(textView);
                campo.setText("");

                for (Encaminhamento encaminhamentoSelecionado : encaminhamentosRecuperados) {
                    if (encaminhamentoSelecionado.toString().equals(encaminhamentoDigitado)) {

                        listaDisplay.add(encaminhamentoSelecionado);
                        break;
                    }
                }
                adapterEncaminhamento.notifyDataSetChanged();
            } else {
                campo.setError("Essa op????o de ENCAMINHAMENTO j?? foi inserida.");
                campo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campo);
                campo.setText("");
            }
        }

        removerItemDaListaDeEncaminhamento(textView, listaDisplay, adapterEncaminhamento);
    }

    public static void removerItemDaListaDeEncaminhamento(TextView textView, List<Encaminhamento> listaDisplay,
                                                          ArrayAdapter<Encaminhamento> adapterEncaminhamento)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String conteudoDoTextView = textView.getText().toString();
                for(Encaminhamento encaminhamentoSelecionado : listaDisplay) {
                    String dadosEncaminhamentoSelecionado = encaminhamentoSelecionado.toString();
                    if(dadosEncaminhamentoSelecionado.equals(conteudoDoTextView))
                    {
                        listaDisplay.remove(encaminhamentoSelecionado);

                        adapterEncaminhamento.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;
                    }
                }
            }
        });
    }

    public static void adicionarTextView(Context context, AutoCompleteTextView campo,
                                         ArrayList<String> lista, ArrayAdapter<String> adapter, LinearLayout linearLayout)
    {

        String string = campo.getText().toString();

        TextView textView = new TextView(context);

        if(string.equals(""))
        {
            Toast.makeText(context,
                    "Voc?? precisa selecionar um item para poder adicionar ?? lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(lista.contains(string)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(string);
            textView.setTag("lista");

            linearLayout.addView(textView);

            campo.setText("");
            lista.add(string);
            adapter.notifyDataSetChanged();

        }
        else
        {
            Toast.makeText(context,
                    "Esse item j?? foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaLista(textView, lista, adapter);
    }

    public static void removerItemDaLista(TextView textView, ArrayList<String> lista,
                                          ArrayAdapter<String> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                lista.remove(string);
                adapter.notifyDataSetChanged();

                textView.setVisibility(View.GONE);

            }
        });
    }
}
