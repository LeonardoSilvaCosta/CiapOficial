package com.br.ciapoficial.enums;

import java.io.Serializable;

public enum TipoServicoEnum implements Serializable {
    ATENDIMENTO_PSICOLOGICO("Atendimento psicológico"), ATENDIMENTO_SOCIAL("Atendimento social"),
    AVALIACAO_PSICOLOGICA("Avaliação psicológica"), AVALIACAO_SOCIAL("Avaliação social"),
    SAE("Serviço de Assistência Especial");

    private String nome;

    TipoServicoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
