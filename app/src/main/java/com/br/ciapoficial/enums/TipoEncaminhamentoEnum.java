package com.br.ciapoficial.enums;

import java.io.Serializable;

public enum TipoEncaminhamentoEnum implements Serializable {
    NONE (0, "Valor desconhecido"), INTERNO(1, "Interno"), EXTERNO(2, "Externo");

    private Integer id;
    private String nome;

     TipoEncaminhamentoEnum(Integer id, String nome) {
         this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
