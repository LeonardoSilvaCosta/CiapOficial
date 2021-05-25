package com.br.ciapoficial.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class PostoGradCat {

    @EqualsAndHashCode.Include
    private int id;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }
}
