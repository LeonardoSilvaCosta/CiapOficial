package com.br.ciapoficial.helper;

import android.text.TextUtils;

public class PadraoDeVisualizacao {

    public static String visualizarCpf(String string) {
        String cpfFormatado = null;
        if (!TextUtils.isEmpty(string)) {
            String cpfFirst = (String) string.substring(0, 3);
            String cpfSecond = (String) string.substring(3, 6);
            String cpfThrid = (String) string.substring(6, 9);
            String cpfFinal = (String) string.substring(9, 11);
            cpfFormatado = cpfFirst + "." + cpfSecond + "." + cpfThrid + "-" + cpfFinal;
        }
        else
        {
            cpfFormatado = "";
        }
        return cpfFormatado;
    }
}
