package com.br.ciapoficial.enums;

public enum EspecialidadeEnum {
    NONE("Valor desconhecido"), PSICOLOGO("Psicólogo(a)"), ASSISTENTE_SOCIAL("Assistente Social");

    private String nome;

    EspecialidadeEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
