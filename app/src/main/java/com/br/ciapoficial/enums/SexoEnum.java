package com.br.ciapoficial.enums;

import java.io.Serializable;

public enum SexoEnum implements Serializable {
    NONE ("Valor desconhecido"), MASCULINO("Masculino"), FEMININO("Feminino");

    private String nome;

     SexoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
