package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Estado implements Serializable {

    @EqualsAndHashCode.Include
    private Integer id;
    private String nome;
    private String uf;

    public Estado() {
    }

    @Override
    public String toString() {
        return nome;
    }
}
