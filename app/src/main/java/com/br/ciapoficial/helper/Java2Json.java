package com.br.ciapoficial.helper;

import com.br.ciapoficial.enums.EscolaridadeEnum;
import com.br.ciapoficial.enums.EspecialidadeEnum;
import com.br.ciapoficial.enums.EstadoCivilEnum;
import com.br.ciapoficial.enums.FuncaoAdministrativaEnum;
import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.SituacaoFuncionalEnum;
import com.br.ciapoficial.enums.TipoVinculoEnum;
import com.br.ciapoficial.enums.UnidadeEnum;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Usuario;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class Java2Json {


    public static String converterJava2JsonArrayString(ArrayList<String> arrayList)
    {
        String data = new Gson().toJson(arrayList);
        return data;
    }

    public static String converterJava2JsonArrayTelefone(ArrayList<Telefone> telefone)
    {
        Gson gson = new Gson();

        String jsonObj = gson.toJson(telefone);
        return jsonObj;
    }

    public static String converterJava2JsonBoolean(boolean booleano)
    {
        String data = new Gson().toJson(booleano);
        return data;
    }

    public static String converterJava2JasonCidade(Cidade cidade)
    {
        String data = new Gson().toJson(cidade);
        return data;
    }

    public static String converterJava2JasonDate(Date date)
    {
        String data = new Gson().toJson(date);
        return data;
    }

    public static String converterJava2JasonEndereco(Endereco endereco)
    {
        String data = new Gson().toJson(endereco);
        return data;
    }

    public static String converterJava2JasonEscolaridade(EscolaridadeEnum escolaridade)
    {
        String data = new Gson().toJson(escolaridade);
        return data;
    }

    public static String converterJava2JasonEspecialidade(EspecialidadeEnum especialidade)
    {
        String data = new Gson().toJson(especialidade);
        return data;
    }

    public static String converterJava2JasonEstadoCivil(EstadoCivilEnum estadoCivil)
    {
        String data = new Gson().toJson(estadoCivil);
        return data;
    }

    public static String converterJava2JasonFuncionario(Funcionario funcionario)
    {
        String data = new Gson().toJson(funcionario);
        return data;
    }

    public static String converterJava2JasonFuncaoAdministrativa(FuncaoAdministrativaEnum funcaoAdministrativa)
    {
        String data = new Gson().toJson(funcaoAdministrativa);
        return data;
    }

    public static String converterJava2JasonInt(int i)
    {
        String data = new Gson().toJson(i);
        return data;
    }

    public static String converterJava2JasonPostoGradCat(PostoGradCatEnum postoGradCat)
    {
        String data = new Gson().toJson(postoGradCat);
        return data;
    }

    public static String converterJava2JasonQuadro(QuadroEnum quadro)
    {
        String data = new Gson().toJson(quadro);
        return data;
    }

    public static String converterJava2JasonSexo(SexoEnum sexo)
    {
        String data = new Gson().toJson(sexo);
        return data;
    }

    public static String converterJava2JasonTipoVinculo(TipoVinculoEnum tipoVinculo)
    {
        String data = new Gson().toJson(tipoVinculo);
        return data;
    }

    public static String converterJava2JasonSituacaoFuncional(SituacaoFuncionalEnum situacaoFuncional)
    {
        String data = new Gson().toJson(situacaoFuncional);
        return data;
    }

    public static String converterJava2JasonUnidade(UnidadeEnum unidade)
    {
        String data = new Gson().toJson(unidade);
        return data;
    }

    public static String converterJava2JasonUsuario(Usuario usuario)
    {
        String data = new Gson().toJson(usuario);
        return data;
    }

}
