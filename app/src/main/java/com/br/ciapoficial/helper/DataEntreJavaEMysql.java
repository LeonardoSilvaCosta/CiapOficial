package com.br.ciapoficial.helper;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataEntreJavaEMysql {
    
    public static String enviarDataParaMySqlComoString(String data) {
        String dataParaMySql = null;
        if (!TextUtils.isEmpty(data)) {
            String dia = (String) data.substring(0, 2);
            String mes = (String) data.substring(3, 5);
            String ano = (String) data.substring(6, 10);
            dataParaMySql = ano + "-" + mes + "-" + dia;
        }
        else
        {
            dataParaMySql = "";
        }
        return dataParaMySql;
    }

    public static String receberDataDoMySqlComoString(String data) {
        String dia = (String) data.substring(8,10);
        String mes = (String) data.substring(5,7);
        String ano = (String) data.substring(0,4);
        String dataDoMySql = dia + "/" + mes + "/" + ano;
        return dataDoMySql;

    }

    public static String receberDataHoraDoMySqlComoString(String data) {
        String dia = (String) data.substring(8,10);
        String mes = (String) data.substring(5,7);
        String ano = (String) data.substring(0,4);
        String hora = (String) data.substring(11, 13);
        String minuto = (String) data.substring(14, 16);
        String segundo = (String) data.substring(17, 19);
        String dataDoMySql = dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto + ":" + segundo;
        return dataDoMySql;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate dataParaMySqlWithLocalDate(String data) {
       final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       final LocalDate date = LocalDate.parse(data,formatter);

       return date;
    }

}
