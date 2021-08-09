package com.br.ciapoficial.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Status {

    @EqualsAndHashCode.Include
    private Integer id;
    private String nome;
}
