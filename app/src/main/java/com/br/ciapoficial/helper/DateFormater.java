package com.br.ciapoficial.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String localDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate StringToLocalDate(String data) throws ParseException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(data, df);
        return localDate;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String localDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String formattedString = dateTime.format(formatter);
        return formattedString;
    }

}
