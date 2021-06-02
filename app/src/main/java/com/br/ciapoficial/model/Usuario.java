package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.PostoGradCatEnum;
import com.br.ciapoficial.enums.QuadroEnum;
import com.br.ciapoficial.enums.SituacaoFuncionalEnum;
import com.br.ciapoficial.enums.TipoVinculoEnum;
import com.br.ciapoficial.enums.UnidadeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
