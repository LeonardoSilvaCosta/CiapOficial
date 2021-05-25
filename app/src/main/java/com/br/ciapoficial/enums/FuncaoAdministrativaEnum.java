package com.br.ciapoficial.enums;

public enum FuncaoAdministrativaEnum {
    NONE("Valor desconhecido"), CHEFE("Chefe"), SUBCHEFE("Subchefe"), CHEFE_P1("Chefe do P1"),
    CHEFE_P2("Chefe do P2"), CHEFE_P3("Chefe do P3"), CHEFE_P4("Chefe do P4"),
    CHEFE_P5("Chefe do P5"), CHEFE_P6("Chefe do P6"), CHEFE_P7 ("Chefe do P7"),
    CHEFE_ST("Chefe da Seção Técnica"), AUXILIAR_P1("Auxiliar do P1"),
    AUXILIAR_P2("Auxiliar do P2"), AUXILIAR_P3("Auxiliar do P3"), AUXILIAR_P4("Auxiliar do P4"),
    AUXILIAR_P5("Auxiliar do P5"), AUXILIAR_P6("Auxiliar do P6"),
    AUXILIAR_P7("Auxiliar do P7"), AUXILIAR_ST("Auxiliar da Seção Técnica");

    private String nome;

    FuncaoAdministrativaEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
