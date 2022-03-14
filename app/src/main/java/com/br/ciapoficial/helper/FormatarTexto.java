package com.br.ciapoficial.helper;

import java.text.Normalizer;

public class FormatarTexto {

    public String retirarColchetes(String texto) {
        return texto
                .replace("[", "")
                .replace("]", "");
    }

    public String substituirEspacos(String texto) {
        return texto.replace(" ", "-");
    }

    public String normalizar(String texto) {
        return Normalizer
                .normalize(texto.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
