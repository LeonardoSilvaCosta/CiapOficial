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
    private Integer id;
    private String cep;
    private Estado estado;
    private Cidade cidade;
    private String bairro;
    private String logradouro;
    private Integer numero;

}
