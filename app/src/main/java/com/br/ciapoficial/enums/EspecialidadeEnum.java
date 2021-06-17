package com.br.ciapoficial.enums;

public enum EspecialidadeEnum {
    NAO_SE_APLICA("Não se aplica"), PSICOLOGO("Psicólogo(a)"), ASSISTENTE_SOCIAL("Assistente Social");

    private String nome;

    EspecialidadeEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
