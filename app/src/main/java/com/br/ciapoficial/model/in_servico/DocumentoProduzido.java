package com.br.ciapoficial.model.in_servico;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DocumentoProduzido implements Serializable {
    @EqualsAndHashCode.Include
    private int id;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }
}
