package com.br.ciapoficial.enums;

public enum EscolaridadeEnum {
    NONE("Valor desconhecido"), FUNDAMENTAL("Fundamental"), MEDIO("Médio"),
    SUPERIOR("Superior"), ESPECIALIZACAO("Especialização"), MESTRADO("Mestrado")
    , DOUTORADO("Doutorado");

    private String nome;

    EscolaridadeEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
