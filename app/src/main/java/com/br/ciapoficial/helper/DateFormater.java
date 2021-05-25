package com.br.ciapoficial.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {

    public static String dataAtual() {

        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;

    }

    public static String mesAnoEscolhido(String data) {
        String retornoData [] = data.split("/");
        String dia = retornoData [0];
        String mes = retornoData[1];
        String ano = retornoData[2];

        String mesAno = mes + ano;
        return mesAno;
    }

    public static Date StringToDate(String data) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        return date;

    }

}
