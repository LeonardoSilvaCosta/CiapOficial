package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class PostoGradCat implements Serializable {

    @EqualsAndHashCode.Include
    private Integer id;
    private String nome;

    public PostoGradCat() {
    }

    @Override
    public String toString() {
        return nome;
    }
}
