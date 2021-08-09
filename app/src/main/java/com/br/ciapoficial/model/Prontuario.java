package com.br.ciapoficial.model;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Prontuario {

    @EqualsAndHashCode.Include
    private Long id;
    private Usuario usuario;
    private Status status;
    private LocalDateTime dataAbertura;
    private Funcionario responsavelAbertura;
    private LocalDateTime dataEdicao;
    private Funcionario responsavelEdicao;
}
