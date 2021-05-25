package com.br.ciapoficial.enums;

import java.io.Serializable;

public enum EstadoCivilEnum implements Serializable {
    NONE("Valor desconhecido"), SOLTEIRO("Solteiro(a)"), CASADO("Casado(a)"), UE("União estável"),
    DIVORCIADO("Divorciado(a)"), VIUVO("Viúvo(a)");

    private String nome;

    EstadoCivilEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}


