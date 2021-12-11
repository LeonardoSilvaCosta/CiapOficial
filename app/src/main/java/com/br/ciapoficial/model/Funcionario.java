package com.br.ciapoficial.model;

import java.time.LocalDate;

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
        return postoGradCat + " " + nomeGuerra;
    }
}
