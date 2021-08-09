package com.br.ciapoficial.helper;

import android.widget.AutoCompleteTextView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

public class Mascaras {

    public static void criarMascaraParaData(TextInputEditText campoDeTexto)
    {
        SimpleMaskFormatter smfData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwData = new MaskTextWatcher(campoDeTexto, smfData);
        campoDeTexto.addTextChangedListener(mtwData);
    }

    public static void criarMascaraParaData(AutoCompleteTextView campoDeTexto)
    {
        SimpleMaskFormatter smfData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwData = new MaskTextWatcher(campoDeTexto, smfData);
        campoDeTexto.addTextChangedListener(mtwData);
    }

    public static void criarMascaraParaCpf(TextInputEditText campoDeTexto)
    {
        SimpleMaskFormatter smfCPF = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtwCPF = new MaskTextWatcher(campoDeTexto, smfCPF);
        campoDeTexto.addTextChangedListener(mtwCPF);
    }

    public static void criarMascaraParaTelefone(TextInputEditText campoDeTexto)
    {
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoDeTexto, smfTelefone);
        campoDeTexto.addTextChangedListener(mtwTelefone);
    }

    public static void criarMascaraParaConselho(TextInputEditText campoDeTexto)
    {
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("NN/NNNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoDeTexto, smfTelefone);
        campoDeTexto.addTextChangedListener(mtwTelefone);
    }

    public static void criarMascaraParaCep(TextInputEditText campoDeTexto) {

        SimpleMaskFormatter smfCep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtwCep = new MaskTextWatcher(campoDeTexto, smfCep);
        campoDeTexto.addTextChangedListener(mtwCep);
    }

    public static String removerMascaras(String dado) {
        String resultado = dado.replaceAll("[^0-9]", "");
        return resultado;
    }
}
