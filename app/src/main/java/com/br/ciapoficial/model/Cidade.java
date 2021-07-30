package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade implements Serializable {

    @EqualsAndHashCode.Include
    private Integer id;
    private String nome;
    private Estado estado;

    @Override
    public String toString() {
        return nome;
    }
}
