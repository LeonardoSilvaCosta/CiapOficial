package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.EspecialidadeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Especialista extends Funcionario{

    private Especialidade especialidade;
    private String registroConselho;

    @Override
    public String toString () {
        return this.getPostoGradCat() + " " + this.getEspecialidade() + " - " + this.getRgMilitar();
    }
}
