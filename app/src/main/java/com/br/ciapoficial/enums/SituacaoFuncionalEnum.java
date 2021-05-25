package com.br.ciapoficial.enums;

public enum SituacaoFuncionalEnum {
    NONE("Valor desconhecido"), ATIVO("Ativo(a)"), RESERVA("Reserva Remunerada"), REFORMADO("Reformado(a)"),
    LICENCA("Licença"), AGREGADO("Agregado(a)"), RECISAOCONTRATUAL("Recisão Contratual");

    private String nome;

    SituacaoFuncionalEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
