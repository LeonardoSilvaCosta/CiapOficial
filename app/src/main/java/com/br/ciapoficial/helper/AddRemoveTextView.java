package com.br.ciapoficial.helper;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.PessoaTelefoneId;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.in_atendimento.Deslocamento;
import com.br.ciapoficial.model.in_atendimento.DocumentoProduzido;
import com.br.ciapoficial.model.in_atendimento.Encaminhamento;
import com.br.ciapoficial.model.in_atendimento.MedicacaoPsiquiatrica;
import com.br.ciapoficial.model.in_atendimento.SinalSintoma;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddRemoveTextView {

    public static void adicionarTextViewUsuario(Context context, AutoCompleteTextView campo,
                                                List<Funcionario> oficiasRecuperados, List<Funcionario> listaDisplay,
                                                ArrayAdapter<Funcionario> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Você precisa selecionar um item para poder adicionar à lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(listaDisplay.toString().contains(textoRecebido)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayout.addView(textView);

            campo.setText("");

            String textoRecebidoSemEspacos = textoRecebido.replace(" ", "");
            String [] splitTextoRecebidoSemEspacos = textoRecebidoSemEspacos.split("-");
            String rgMilitar = splitTextoRecebidoSemEspacos[1];

            for(int i = 0; i < oficiasRecuperados.size(); i++) {
                Funcionario oficialSelecionado = oficiasRecuperados.get(i);
                if(oficialSelecionado.getRgMilitar().equals(rgMilitar)) {
                    listaDisplay.add(oficialSelecionado);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item já foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeOficiaisResponsaveis(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeOficiaisResponsaveis(TextView textView, List<Funcionario> listaDisplay,
                                                                ArrayAdapter<Funcionario> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    Funcionario oficialSelecionado = listaDisplay.get(i);
                    String dadosOficialSelecionado = oficialSelecionado.toString();
                    if(dadosOficialSelecionado.equals(string))
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

    public static void adicionarTextViewTelefoneUsuarioObjeto(Context context, TextInputEditText campo,
                                                              List<Telefone> listaDeTelefonesRecebidosParaAtualizar,
                                                              LinearLayout linearLayout) {


//        for (Iterator<Telefone> telefone = listaDeTelefonesPreviamenteAdicionados.iterator(); telefone.hasNext();) {
//            listaDeTelefonesRecebidosParaAtualizar.add(telefone.next());
//        }

        String textoRecebido = Mascaras.removerMascaras(campo.getText().toString());
        TextView textView = new TextView(context);

        if (textoRecebido.isEmpty()) {
            campo.setError("O campo para inserção de TELEFONE está vazio");
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
                    campo.setError("Esse TELEFONE já foi inserido.");
                    campo.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campo);
                    campo.setText("");
                }

            }else{
                campo.setError("Digite um número de TELEFONE válido.");
                campo.requestFocus();
                DellayAction.clearErrorAfter2Seconds(campo);
            }
        }
        removerItemDaListaDeTelefonesObjeto(textView, listaDeTelefonesRecebidosParaAtualizar);

    }

    public static void removerItemDaListaDeTelefonesObjeto(TextView textView, List<Telefone> listaDisplay)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });
    }

    public static void adicionarTextViewTelefoneUsuarioString(Context context, TextInputEditText campo,
                                                              List<Telefone> telefoneListadosParaCadastro,
                                                              LinearLayout linearLayout) {

        String textoRecebido = Mascaras.removerMascaras(campo.getText().toString());
        TextView textView = new TextView(context);
        if (textoRecebido.isEmpty()) {
            campo.setError("O campo para inserção de TELEFONE está vazio.");
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
                    campo.setError("Esse TELEFONE já foi inserido.");
                    campo.requestFocus();
                    DellayAction.clearErrorAfter2Seconds(campo);
                    campo.setText("");
                }
            } else {
                campo.setError("Digite um número de TELEFONE válido.");
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

    public static void adicionarTextViewAtendido(Context context, AutoCompleteTextView campo, ArrayList<Usuario> atendidosRecuperados, ArrayList<Usuario> listaDisplay,
                                                 ArrayAdapter<Usuario> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Você precisa selecionar um item para poder adicionar à lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(listaDisplay.toString().contains(textoRecebido)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayout.addView(textView);
            campo.setText("");

            String textoRecebidoSemEspacos = textoRecebido.replace(" ", "");
            String [] splitTextoRecebidoSemEspacos = textoRecebidoSemEspacos.split("-");
            String cpf = splitTextoRecebidoSemEspacos[1];

            for(int i = 0; i < atendidosRecuperados.size(); i++) {
                Usuario usuarioSelecionado = atendidosRecuperados.get(i);
                if(usuarioSelecionado.getCpf().equals(cpf)) {
                    listaDisplay.add(usuarioSelecionado);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item já foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeAtendidos(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeAtendidos(TextView textView, ArrayList<Usuario> listaDisplay,
                                                     ArrayAdapter<Usuario> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    Usuario usuarioSelecionado = listaDisplay.get(i);
                    String dadosAtendidoSelecionado = usuarioSelecionado.toString();
                    if(dadosAtendidoSelecionado.equals(string))
                    {
                        listaDisplay.remove(usuarioSelecionado);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    public static void adicionarTextViewDeslocamento(Context context, AutoCompleteTextView campo,
                                                     ArrayList<Deslocamento> deslocamentosRecuperados,
                                                     ArrayList<Deslocamento> listaDisplay,
                                                     ArrayAdapter<Deslocamento> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Você precisa selecionar um item para poder adicionar à lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(listaDisplay.toString().contains(textoRecebido)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayout.addView(textView);
            campo.setText("");

            for(int i = 0; i < deslocamentosRecuperados.size(); i++) {
                Deslocamento deslocamentoSelecionado = deslocamentosRecuperados.get(i);
                if(deslocamentoSelecionado.getDescricao().equals(textoRecebido)) {
                    listaDisplay.add(deslocamentoSelecionado);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item já foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeDeslocamento(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeDeslocamento(TextView textView, ArrayList<Deslocamento> listaDisplay,
                                                        ArrayAdapter<Deslocamento> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    Deslocamento deslocamentoSelecionado = listaDisplay.get(i);
                    String dadosDeslocamentoSelecionado = deslocamentoSelecionado.toString();
                    if(dadosDeslocamentoSelecionado.equals(string))
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
                                                           ArrayList<DocumentoProduzido> documentosRecuperados,
                                                           ArrayList<DocumentoProduzido> listaDisplay,
                                                           ArrayAdapter<DocumentoProduzido> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Você precisa selecionar um item para poder adicionar à lista.",
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
                if(documentoSelecionado.getDescricao().equals(textoRecebido)) {
                    listaDisplay.add(documentoSelecionado);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item já foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeDocumento(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeDocumento(TextView textView, ArrayList<DocumentoProduzido> listaDisplay,
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
                                                       ArrayList<Encaminhamento> encaminhamentosRecuperados,
                                                       ArrayList<Encaminhamento> listaDisplay,
                                                       ArrayAdapter<Encaminhamento> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Você precisa selecionar um item para poder adicionar à lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(listaDisplay.toString().contains(textoRecebido)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayout.addView(textView);
            campo.setText("");

            for(int i = 0; i < encaminhamentosRecuperados.size(); i++) {
                Encaminhamento encaminhamentoSelecionado = encaminhamentosRecuperados.get(i);
                if((encaminhamentoSelecionado.getDestino() + " - " + encaminhamentoSelecionado.getTipo()).equals(textoRecebido)) {
                    listaDisplay.add(encaminhamentoSelecionado);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item já foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeEncaminhamento(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeEncaminhamento(TextView textView, ArrayList<Encaminhamento> listaDisplay,
                                                          ArrayAdapter<Encaminhamento> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    Encaminhamento encaminhamentoSelecionado = listaDisplay.get(i);
                    String dadosEncaminhamentoSelecionado = encaminhamentoSelecionado.toString();
                    if(dadosEncaminhamentoSelecionado.equals(string))
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

    public static void adicionarTextViewSinalSintoma(Context context, AutoCompleteTextView campo,
                                                     ArrayList<SinalSintoma> sinaisSintomasRecuperados,
                                                     ArrayList<SinalSintoma> listaDisplay,
                                                     ArrayAdapter<SinalSintoma> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Você precisa selecionar um item para poder adicionar à lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(listaDisplay.toString().contains(textoRecebido)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayout.addView(textView);
            campo.setText("");

            for(int i = 0; i < sinaisSintomasRecuperados.size(); i++) {
                SinalSintoma sinalSintomaSelecionado = sinaisSintomasRecuperados.get(i);
                if(sinalSintomaSelecionado.getDescricao().equals(textoRecebido)) {
                    listaDisplay.add(sinalSintomaSelecionado);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item já foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeSinalSintoma(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeSinalSintoma(TextView textView, ArrayList<SinalSintoma> listaDisplay,
                                                        ArrayAdapter<SinalSintoma> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    SinalSintoma sinalSintomaSelecionado = listaDisplay.get(i);
                    String dadosSinalSintomaSelecionado = sinalSintomaSelecionado.toString();
                    if(dadosSinalSintomaSelecionado.equals(string))
                    {
                        listaDisplay.remove(sinalSintomaSelecionado);
                        adapter.notifyDataSetChanged();

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    public static void adicionarTextViewMedicacaoPsiquiatrica(Context context, AutoCompleteTextView campo,
                                                              ArrayList<MedicacaoPsiquiatrica> medicacoesRecuperadas,
                                                              ArrayList<MedicacaoPsiquiatrica> listaDisplay,
                                                              ArrayAdapter<MedicacaoPsiquiatrica> adapter, LinearLayout linearLayout)
    {

        String textoRecebido = campo.getText().toString();

        TextView textView = new TextView(context);

        if(textoRecebido.equals(""))
        {
            Toast.makeText(context,
                    "Você precisa selecionar um item para poder adicionar à lista.",
                    Toast.LENGTH_SHORT).show();
        }else if(!(listaDisplay.toString().contains(textoRecebido)))
        {
            textView.setPadding(0, 10, 0, 10);
            textView.setText(textoRecebido);
            textView.setTag("lista");

            linearLayout.addView(textView);
            campo.setText("");

            for(int i = 0; i < medicacoesRecuperadas.size(); i++) {
                MedicacaoPsiquiatrica medicacaoSelecionada = medicacoesRecuperadas.get(i);
                if(medicacaoSelecionada.getDescricao().equals(textoRecebido)) {
                    listaDisplay.add(medicacaoSelecionada);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context,
                    "Esse item já foi adicionado.",
                    Toast.LENGTH_SHORT).show();
            campo.setText("");
        }

        removerItemDaListaDeMedicacaoPsiquiatrica(textView, listaDisplay, adapter);
    }

    public static void removerItemDaListaDeMedicacaoPsiquiatrica(TextView textView, ArrayList<MedicacaoPsiquiatrica> listaDisplay,
                                                                 ArrayAdapter<MedicacaoPsiquiatrica> adapter)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    MedicacaoPsiquiatrica medicacaoSelecionada = listaDisplay.get(i);
                    String dadosMedicacaoSelecionada = medicacaoSelecionada.toString();
                    if(dadosMedicacaoSelecionada.equals(string))
                    {
                        listaDisplay.remove(medicacaoSelecionada);
                        adapter.notifyDataSetChanged();

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
                    "Você precisa selecionar um item para poder adicionar à lista.",
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
                    "Esse item já foi adicionado.",
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
