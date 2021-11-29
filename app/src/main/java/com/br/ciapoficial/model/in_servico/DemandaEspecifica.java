package com.br.ciapoficial.model.in_servico;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DemandaEspecifica {
    @EqualsAndHashCode.Include
    private int id;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }
}
