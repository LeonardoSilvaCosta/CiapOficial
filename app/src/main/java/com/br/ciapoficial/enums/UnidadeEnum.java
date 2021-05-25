package com.br.ciapoficial.enums;

public enum UnidadeEnum {
    NONE("Valor desconhecido"), CITEL("CITEL"), CIAP("CIAP"), BOPE("BOPE"), BAC("BAC");

    private String nome;

    UnidadeEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
