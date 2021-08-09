package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Vinculo implements Serializable {
    @EqualsAndHashCode.Include
    private Integer id;
    private String nome;

    public Vinculo() {
    }

    @Override
    public String toString() {
        return nome;
    }
}
