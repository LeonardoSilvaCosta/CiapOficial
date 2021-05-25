package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SituacaoFuncionalEnum;
import com.br.ciapoficial.enums.TipoVinculoEnum;
import com.br.ciapoficial.enums.UnidadeEnum;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario extends Pessoa {

    private String nomeGuerra;
    private UnidadeEnum unidade;
    private SituacaoFuncionalEnum situacaoFuncional;
    private String rgMilitar;
    private PostoGradCatEnum postoGradCat;
    private QuadroEnum quadro;
    private Date dataInclusao;
    private Usuario titular;
    private TipoVinculoEnum tipoVinculo;


    public Usuario() {

    }

    @Override
    public String toString () {
        return nomeGuerra + " - " + rgMilitar;
    }

}
