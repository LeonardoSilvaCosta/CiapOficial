package com.br.ciapoficial.enums;

import java.io.Serializable;

public enum TipoAtendido implements Serializable {
    PM("Policial Militar"), DEPENDENTE("Dependente"), CIVIL("Civil");

    private String nome;

    TipoAtendido(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
