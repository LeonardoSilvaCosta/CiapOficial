package com.br.ciapoficial.enums;

public enum QuadroEnum {
    NONE("Valor desconhecido"), QOPM("QOPM"), QCOPM("QCOPM"), QOSPM("QOSPM"), QOAPM("QOAPM"),
    QPMP("QPMP");

    private String nome;

    QuadroEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
