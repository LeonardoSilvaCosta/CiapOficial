package com.br.ciapoficial.helper;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

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

                String estado = (String) ((TextView) view).getText();

                if(estado.equals("AC"))
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

                if(estado.equals("AL"))
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

                if(estado.equals("AM"))
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

                if(estado.equals("AP"))
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
                if(estado.equals("BA"))
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

                if(estado.equals("CE"))
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

                }if(estado.equals("DF"))
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

                if(estado.equals("ES"))
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

                }if(estado.equals("GO"))
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

                if(estado.equals("MA"))
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

                if(estado.equals("MG"))
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

                }if(estado.equals("MS"))
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

                if(estado.equals("MT"))
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

                if(estado.equals("PA"))
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

                if(estado.equals("PB"))
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

                if(estado.equals("PE"))
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

                if(estado.equals("PI"))
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

                if(estado.equals("PR"))
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

                if(estado.equals("RJ"))
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

                }if(estado.equals("RN"))
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

                if(estado.equals("RO"))
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

                if(estado.equals("RR"))
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

                if(estado.equals("RS"))
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

                if(estado.equals("SC"))
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

                }if(estado.equals("SE"))
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

                }if(estado.equals("SP"))
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

                }if(estado.equals("TO"))
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
