package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.TipoVinculoEnum;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario extends Pessoa {

    private String nomeGuerra;
    private Unidade unidade;
    private SituacaoFuncional situacaoFuncional;
    private String rgMilitar;
    private PostoGradCat postoGradCat;
    private Quadro quadro;
    private LocalDate dataInclusao;
    private Usuario titular;
    private TipoVinculoEnum tipoVinculo;


    public Usuario() {

    }

    @Override
    public String toString () {
        return nomeGuerra + " - " + rgMilitar;
    }

}
