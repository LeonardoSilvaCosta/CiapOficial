package com.br.ciapoficial.model.in_servico;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Encaminhamento implements Serializable {
    @EqualsAndHashCode.Include
    private int id;
    private String destino;
    private String tipo;

    public Encaminhamento(String destino, String tipo) {
        this.destino = destino;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return destino + " - " + tipo;
    }
}
