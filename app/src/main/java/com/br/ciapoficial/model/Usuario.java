package com.br.ciapoficial.model;

import java.time.LocalDate;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario extends Pessoa {

    private PostoGradCat postoGradCat;
    private Quadro quadro;
    private String rgMilitar;
    private String nomeGuerra;
    private Unidade unidade;
    private LocalDate dataInclusao;
    private SituacaoFuncional situacaoFuncional;
    private Usuario titular;
    private Vinculo tipoVinculo;
    private Set<Especialista> especialista;

    public Usuario() {

    }

    @Override
    public String toString () {
        return nomeGuerra + " - " + rgMilitar;
    }

}
