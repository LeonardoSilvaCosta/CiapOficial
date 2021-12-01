package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco implements Serializable {

    @EqualsAndHashCode.Include
    private Integer id;
    private String cep;
    private Estado estado;
    private Cidade cidade;
    private String bairro;
    private String logradouro;
    private Integer numero;

    @Override
    public String toString() {
        return logradouro + ", n° " + numero + ", " + bairro +
                ", " + cidade + "+" + estado + ", " + cep + ".";
    }
}
