package com.br.ciapoficial.helper;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.ciapoficial.model.Usuario;
import com.br.ciapoficial.model.in_atendimento.Deslocamento;
import com.br.ciapoficial.model.in_atendimento.DocumentoProduzido;
import com.br.ciapoficial.model.in_atendimento.Encaminhamento;
import com.br.ciapoficial.model.in_atendimento.MedicacaoPsiquiatrica;
import com.br.ciapoficial.model.in_atendimento.SinalSintoma;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.Telefone;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddRemoveTextView {

    public static void adicionarTextViewUsuario(Context context, AutoCompleteTextView campo,
                                                ArrayList<Funcionario> oficiasRecuperados, ArrayList<Funcionario> listaDisplay,
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

    public static void removerItemDaListaDeOficiaisResponsaveis(TextView textView, ArrayList<Funcionario> listaDisplay,
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

    public static void adicionarTextViewTelefoneUsuarioObjeto(Context context, int id_usuario, TextInputEditText campo,
                                                              ArrayList<Telefone> listaDisplay,
                                                              LinearLayout linearLayout)
    {

        ArrayList<String> telefonesListados = new ArrayList<>();

        for(Telefone telefone : listaDisplay) {
            telefonesListados.add(telefone.getTelefone());
        }

        String textoRecebido = Mascaras.removerMascaras(campo.getText().toString());
        if(!(textoRecebido.length() < 11) && Patterns.PHONE.matcher(textoRecebido).matches())
        {
            TextView textView = new TextView(context);

            if(textoRecebido.equals(""))
            {
                Toast.makeText(context,
                        "O campo para inserção de telefone está vazio.",
                        Toast.LENGTH_SHORT).show();
            }else if(!(telefonesListados.contains(textoRecebido)))
            {
                textView.setPadding(0, 10, 0, 10);
                textView.setText(textoRecebido);
                textView.setTag("lista");

                linearLayout.addView(textView);

                campo.setText("");

                Telefone telefone = new Telefone();
                telefone.setId(id_usuario);
                telefone.setTelefone(textoRecebido);

                listaDisplay.add(telefone);

            }
            else
            {
                Toast.makeText(context,
                        "Esse telefone já foi inserido.",
                        Toast.LENGTH_SHORT).show();
                campo.setText("");
            }

            removerItemDaListaDeTelefonesObjeto(textView, listaDisplay);
        }else
        {
            Toast.makeText(context, "Digite um número de telefone válido.", Toast.LENGTH_SHORT).show();
        }

    }

    public static void removerItemDaListaDeTelefonesObjeto(TextView textView, ArrayList<Telefone> listaDisplay)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDisplay.size(); i++)
                {
                    String telefoneSelecionado = listaDisplay.get(i).getTelefone();
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
                                                              ArrayList<Telefone> telefonesListadosParaCadastro,
                                                              LinearLayout linearLayout)
    {

        String textoRecebido = Mascaras.removerMascaras(campo.getText().toString());
        if(!(textoRecebido.length() < 11) && Patterns.PHONE.matcher(textoRecebido).matches())
        {
            TextView textView = new TextView(context);

            if(textoRecebido.equals(""))
            {
                Toast.makeText(context,
                        "O campo para inserção de telefone está vazio.",
                        Toast.LENGTH_SHORT).show();
            }else if(!(telefonesListadosParaCadastro.toString().contains(textoRecebido)))
            {
                textView.setPadding(0, 10, 0, 10);
                textView.setText(textoRecebido);
                textView.setTag("lista");

                linearLayout.addView(textView);

                campo.setText("");

                telefonesListadosParaCadastro.add(new Telefone(textoRecebido));

            }
            else
            {
                Toast.makeText(context,
                        "Esse telefone já foi inserido.",
                        Toast.LENGTH_SHORT).show();
                campo.setText("");
            }

            removerItemDaListaDeTelefonesString(textView, telefonesListadosParaCadastro);
        }else
        {
            Toast.makeText(context, "Digite um número de telefone válido.", Toast.LENGTH_SHORT).show();
        }

    }

    public static void removerItemDaListaDeTelefonesString(TextView textView, ArrayList<Telefone> listaDeTelefonesInseridos)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = textView.getText().toString();
                for(int i = 0; i < listaDeTelefonesInseridos.size(); i++)
                {
                    String telefoneSelecionado = listaDeTelefonesInseridos.get(i).toString();
                    if(telefoneSelecionado.equals(string))
                    {
                        listaDeTelefonesInseridos.remove(telefoneSelecionado);

                        textView.setVisibility(View.GONE);
                        break;

                    }
                }
            }
        });
    }

    public static void removerItemDaListaDeTelefonesEListarParaDelecao(ArrayList<TextView> textViews, ArrayList<Telefone> listaDisplay,
                                                                       ArrayList<Telefone> telefonesParaDeletar)
    {
        for(TextView textView : textViews) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String string = textView.getText().toString();
                        for(int i = 0; i < listaDisplay.size(); i++)
                        {
                            String telefoneSelecionado = listaDisplay.get(i).getTelefone();
                            if(telefoneSelecionado.equals(string))
                            {
                                telefonesParaDeletar.add(listaDisplay.get(i));
                                listaDisplay.remove(listaDisplay.get(i));

                                textView.setVisibility(View.GONE);
                                break;

                            }
                        }
                    }
                });
        }

    }

    public static void adicionarTextViewAtendido(Context context, AutoCompleteTextView campo,
                                                 ArrayList<Usuario> atendidosRecuperados, ArrayList<Usuario> listaDisplay,
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
