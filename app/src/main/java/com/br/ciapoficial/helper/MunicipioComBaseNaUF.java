package com.br.ciapoficial.helper;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.br.ciapoficial.controller.CidadeController;
import com.br.ciapoficial.enums.UfEnum;
import com.br.ciapoficial.interfaces.IVolleyCallback;
import com.br.ciapoficial.model.Cidade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MunicipioComBaseNaUF {

    public static ArrayAdapter arrayAdapterChild;

    public static void mostrarMunicipioComBaseNaUf(Context context, AutoCompleteTextView campoUf,
                                                   AutoCompleteTextView campoCidade,
                                                   List<Cidade> listaCidadesRecuperadas) {

        campoUf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String estado = (String) ((TextView) view).getText();

                if(estado.equals(UfEnum.AC.getNome()))
                {
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadesAcre(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeAlagoas(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeAmazonas(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeAmapa(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeBahia(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }
                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeCeara(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeDistritoFederal(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeEspiritoSanto(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeGoias(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMaranhao(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMinasGerais(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMatoGrossoDoSul(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeMatoGrosso(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadePara(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeParaiba(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadePernambuco(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadePiaui(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeParana(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRioDeJaneiro(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRioGrandeDoNorte(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRondonia(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRoraima(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeRioGrandeDoSul(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeSantaCatarina(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeSergipe(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeSaoPaulo(context, new IVolleyCallback() {
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
                    listaCidadesRecuperadas.clear();
                    campoCidade.setText("");
                    campoUf.clearFocus();
                    campoCidade.requestFocus();
                    if(arrayAdapterChild != null)
                    {
                        arrayAdapterChild.clear();
                    }

                    CidadeController cidadeController = new CidadeController();
                    cidadeController.listarCidadeTocantins(context, new IVolleyCallback() {
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
