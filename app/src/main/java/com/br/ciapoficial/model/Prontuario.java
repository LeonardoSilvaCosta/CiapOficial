package com.br.ciapoficial.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Prontuario implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;
    private String identificador;
    private Status statusProntuario;
    private LocalDateTime dataAbertura;
    private Funcionario responsavelAbertura;
    private LocalDateTime dataEdicao;
    private Funcionario responsavelEdicao;
    private List<Registro> registros;
}
