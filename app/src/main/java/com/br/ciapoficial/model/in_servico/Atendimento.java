package com.br.ciapoficial.model.in_servico;

import com.br.ciapoficial.model.Servico;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Atendimento extends Servico {

    private Set<SinalSintoma> sinaisSintomas = new HashSet<>();
    private Set<MedicacaoPsiquiatrica> medicacoesPsiquiatricas = new HashSet<>();
}
