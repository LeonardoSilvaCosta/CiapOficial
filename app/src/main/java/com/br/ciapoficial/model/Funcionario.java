package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.FuncaoAdministrativaEnum;
import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SituacaoFuncionalEnum;
import com.br.ciapoficial.enums.UnidadeEnum;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Funcionario extends Pessoa {


    private PostoGradCatEnum postoGradCat;
    private QuadroEnum quadro;
    private String rgMilitar;
    private String nomeGuerra;
    private UnidadeEnum unidade;
    private Date dataInclusao;
    private FuncaoAdministrativaEnum funcaoAdministrativa;
    private SituacaoFuncionalEnum situacaoFuncional;
    private String senha;

    @Override
    public String toString () {
        return postoGradCat + " " + nomeGuerra + " - " + rgMilitar;
    }
}
