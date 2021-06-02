package com.br.ciapoficial.helper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.br.ciapoficial.controller.CidadeController;
import com.br.ciapoficial.enums.UfEnum;
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

                if(estado.equals(UfEnum.AC.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.AL.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.AM.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.AP.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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
                if(estado.equals(UfEnum.BA.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.CE.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(estado.equals(UfEnum.DF.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.ES.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(estado.equals(UfEnum.GO.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.MA.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.MG.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(estado.equals(UfEnum.MS.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.MT.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.PA.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.PB.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.PE.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.PI.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.PR.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.RJ.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(estado.equals(UfEnum.RN.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.RO.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.RR.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.RS.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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

                if(estado.equals(UfEnum.SC.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(estado.equals(UfEnum.SE.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(estado.equals(UfEnum.SP.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    arrayAdapterChild = new ArrayAdapter<Cidade>(context,
                            android.R.layout.simple_dropdown_item_1line,
                            (listaCidadesRecuperadas));

                }if(estado.equals(UfEnum.TO.getNome()))
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
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Cidade cidade = new Cidade();
                                    cidade.setId(Integer.parseInt(object.getString("id")));
                                    cidade.setNome(object.getString("nome"));
                                    listaCidadesRecuperadas.add(cidade);
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
