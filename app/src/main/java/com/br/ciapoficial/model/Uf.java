package com.br.ciapoficial.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Uf {
    @EqualsAndHashCode.Include
    private int id;
    private String nome;
    private String uf;

    public Uf() {
    }

    @Override
    public String toString() {
        return nome;
    }
}
