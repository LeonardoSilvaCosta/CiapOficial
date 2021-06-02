package com.br.ciapoficial.enums;

public enum UfEnum {

    AC("Acre"), AL("Alagoas"), AM("Amazonas"),
    AP("Amapá"), BA("Bahia") , CE("Ceara"),
    DF("Distrito Federal"), ES("Espirito Santo"), GO("Goiás"),
    MA("Maranhã"), MG("Minas Gerais"), MS("Mato Grosso do Sul"),
    MT("Mato Grosso"), PA("Pará"), PB("Paraíba"),
    PE("Pernanbuco"), PI("Piauí"), PR("Paraná"),
    RJ("Rio de Janeiro"), RN("Rio Grande do Norte"), RO("Rondônia"),
    RR("Roraima"), RS("Rio Grande do Sul"), SC("Santa Catarina"),
    SE("Sergipe"), SP("São Paulo"), TO("Tocantins");

    private String nome;

    UfEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
