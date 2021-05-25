package com.br.ciapoficial.enums;

import java.io.Serializable;

public enum PostoGradCatEnum implements Serializable {
    NONE("Valor desconhecido"), CEL("Coronél"), TENCEL("Tenente-Coronel"), MAJ("Major"), CAP("Capitão"),
    _1TEN("1° Tenente"), _2TEN("2° Tenente"), OF_AL("Oficial-Aluno"),
    ASP("Aspirante"), AL_OF("Aluno-Oficial"), AL_CHO("Aluno-CHO"),
    SUBTEN("Subtenente"), _1SGT("1° Sargento"), _2SGT("2° Sargento"),
    _3SGT("3° Sargento"), CB("Cabo"), SD("Soldado"),
    AL_CFP("Aluno-CFP"), VC("Voluntário Civil");

    private String nome;

    PostoGradCatEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
