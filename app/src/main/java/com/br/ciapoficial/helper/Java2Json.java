package com.br.ciapoficial.helper;

import com.br.ciapoficial.enums.EscolaridadeEnum;
import com.br.ciapoficial.enums.EstadoCivilEnum;
import com.br.ciapoficial.enums.SexoEnum;
import com.br.ciapoficial.enums.TipoVinculoEnum;
import com.br.ciapoficial.model.Cidade;
import com.br.ciapoficial.model.Endereco;
import com.br.ciapoficial.model.Especialidade;
import com.br.ciapoficial.model.FuncaoAdministrativa;
import com.br.ciapoficial.model.Funcionario;
import com.br.ciapoficial.model.Pessoa;
import com.br.ciapoficial.model.PostoGradCat;
import com.br.ciapoficial.model.Quadro;
import com.br.ciapoficial.model.SituacaoFuncional;
import com.br.ciapoficial.model.Telefone;
import com.br.ciapoficial.model.Unidade;
import com.br.ciapoficial.model.Usuario;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;

public class Java2Json {


    public static String converterJava2JsonArrayString(ArrayList<String> arrayList)
    {
        String data = new Gson().toJson(arrayList);
        return data;
    }

    public static String converterJava2JsonArrayTelefone(ArrayList<Telefone> telefones)
    {
        Gson gson = new Gson();

        String jsonObj = gson.toJson(telefones);
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

    public static String converterJava2JasonLocalDate(LocalDate date)
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

    public static String converterJava2JasonEspecialidade(Especialidade especialidade)
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

    public static String converterJava2JasonFuncaoAdministrativa(FuncaoAdministrativa funcaoAdministrativa)
    {
        String data = new Gson().toJson(funcaoAdministrativa);
        return data;
    }

    public static String converterJava2JasonInt(int i)
    {
        String data = new Gson().toJson(i);
        return data;
    }

    public static String converterJava2JsonPessoa(Pessoa pessoa)
    {
        String data = new Gson().toJson(pessoa);
        return data;
    }

    public static String converterJava2JasonPostoGradCat(PostoGradCat postoGradCat)
    {
        String data = new Gson().toJson(postoGradCat);
        return data;
    }

    public static String converterJava2JasonQuadro(Quadro quadro)
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

    public static String converterJava2JasonSituacaoFuncional(SituacaoFuncional situacaoFuncional)
    {
        String data = new Gson().toJson(situacaoFuncional);
        return data;
    }

    public static String converterJava2JasonUnidade(Unidade unidade)
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
