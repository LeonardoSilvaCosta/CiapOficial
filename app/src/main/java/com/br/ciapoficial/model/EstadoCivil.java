package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EstadoCivil implements Serializable {
    @EqualsAndHashCode.Include
    private int id;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }
}
