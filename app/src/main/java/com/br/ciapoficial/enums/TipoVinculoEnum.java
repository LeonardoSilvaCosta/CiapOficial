package com.br.ciapoficial.enums;

public enum TipoVinculoEnum {
    NONE("Valor Desconhecido"), PAI("Pai"), FILHO("Filho"), ESPOSO("Esposo");

    private String nome;

    TipoVinculoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
