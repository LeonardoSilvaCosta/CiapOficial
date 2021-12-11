package com.br.ciapoficial.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
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
    private Set<Servico> servicos = new HashSet<>();

    public Usuario() {

    }

    @Override
    public String toString () {
        if(rgMilitar != null) {
            return postoGradCat + " " + quadro + " " + rgMilitar + " " + getNomeCompleto();
        }else if (tipoVinculo != null){
            return this.getNomeCompleto() + "(dependente)";
        }else {
            return this.getNomeCompleto() + "(civil)";
        }
    }

}
