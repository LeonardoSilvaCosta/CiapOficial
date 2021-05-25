package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;
    private String cep;
    private Cidade cidade;
    private String bairro;
    private String logradouro;
    private int numero;

}
