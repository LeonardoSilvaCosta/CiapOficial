package com.br.ciapoficial.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Especialista extends Funcionario{

    private Especialidade especialidade;
    private String regiaoConselho;
    private String registroConselho;

    private Set<Servico> servicos;

    @Override
    public String toString () {
        return this.getPostoGradCat() + " " + this.getEspecialidade() + " - " + this.getRgMilitar();
    }
}
