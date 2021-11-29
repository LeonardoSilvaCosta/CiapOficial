package com.br.ciapoficial.model.in_servico;

import com.br.ciapoficial.model.Servico;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Avaliacao extends Servico {

    private TipoAvaliacao tipoAvaliacao;
}
