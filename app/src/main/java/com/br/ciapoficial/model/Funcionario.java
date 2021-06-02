package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.FuncaoAdministrativaEnum;
import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SituacaoFuncionalEnum;
import com.br.ciapoficial.enums.UnidadeEnum;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Funcionario extends Pessoa {


    private PostoGradCat postoGradCat;
    private Quadro quadro;
    private String rgMilitar;
    private String nomeGuerra;
    private Unidade unidade;
    private LocalDate dataInclusao;
    private FuncaoAdministrativa funcaoAdministrativa;
    private SituacaoFuncional situacaoFuncional;
    private String senha;

    @Override
    public String toString () {
        return postoGradCat + " " + nomeGuerra + " - " + rgMilitar;
    }
}
