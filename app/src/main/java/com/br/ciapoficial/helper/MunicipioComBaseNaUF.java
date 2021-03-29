package com.br.ciapoficial.helper;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.br.ciapoficial.controller.CidadeController;
import com.br.ciapoficial.interfaces.VolleyCallback;
import com.br.ciapoficial.model.Cidade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MunicipioComBaseNaUF {

    public static ArrayAdapter arrayAdapterChild;

    public static void mostrarMunicipioComBaseNaUf(Context context, AutoCompleteTextView campoUf,
                                                   AutoCompleteTextView campoCidade,
                                                   ArrayList<Cidade> listaCidadesRecuperadas) {

        campoUf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int acre = 0;
                int alagoas = 1;
                int amazonas = 2;
                int amapa = 3;
                int bahia = 4;
                int ceara = 5;
                int df = 6;
                int espiritoSanto = 7;
                int goias = 8;
                int maranhao = 9;
                int minhasGerais = 10;
                int matoGrossoDoSul = 11;
                int matoGrosso = 12;
                int para = 13;
                int paraiba = 14;
                int pernambuco = 15;
                int piaui = 16;
                int parana = 17;
                int rioDeJaneiro = 18;
                int rioGrandeDoNorte = 19;
                int rondonia = 20;
                int roraima = 21;
                int rioGrandeDoSul = 22;
                int santaCatarina = 23;
                int sergipe = 24;
                int saoPaulo = 25;
                int tocantins = 26;

                if(position == acre)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadesAcre(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == alagoas)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeAlagoas(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == amazonas)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeAmazonas(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == amapa)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeAmapa(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }
                if(position == bahia)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeBahia(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == ceara)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }
                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeCeara(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(position == df)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeDistritoFederal(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == espiritoSanto)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeEspiritoSanto(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(position == goias)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeGoias(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == maranhao)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMaranhao(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == minhasGerais)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMinasGerais(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(position == matoGrossoDoSul)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMatoGrossoDoSul(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == matoGrosso)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMatoGrosso(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == para)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadePara(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == paraiba)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeParaiba(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == pernambuco)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadePernambuco(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == piaui)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadePiaui(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == parana)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeParana(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == rioDeJaneiro)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRioDeJaneiro(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(position == rioGrandeDoNorte)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRioGrandeDoNorte(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == rondonia)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRondonia(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == roraima)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRoraima(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == rioGrandeDoSul)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRioGrandeDoSul(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));
                }

                if(position == santaCatarina)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeSantaCatarina(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(position == sergipe)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeSergipe(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(position == saoPaulo)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeSaoPaulo(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(position == tocantins)
                {
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeTocantins(context, new VolleyCallback() {
                        @Override
                        public void onSucess(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if(success.equals("1")){
                                    for(int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.valueOf(object.getString("id")));
                                        cidade.setDescricao(object.getString("descricao"));

                                        listaCidadesRecuperadas.add(cidade);

                                    }
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }

                campoCidade.setAdapter(arrayAdapterChild);
                campoCidade.setThreshold(1);

            }

            /*@Override
            public void onNothingSelected(AdapterView<?> parent) {


            }*/
        });
    }

}
