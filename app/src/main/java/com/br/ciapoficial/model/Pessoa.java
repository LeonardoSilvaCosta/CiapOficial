package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.SexoEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Pessoa implements Serializable {

    @EqualsAndHashCode.Include
    private Integer id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String cpf;
    private SexoEnum sexo;
    private Cidade naturalidade;
    private EstadoCivil estadoCivil;
    private int numeroFilhos;
    private Escolaridade escolaridade;
    private List<Telefone> telefones;
    private String email;
    private Endereco endereco;
    private LocalDateTime dataCadastro;
    private Funcionario responsavelCadastro;
    private LocalDateTime dataEdicao;
    private Funcionario responsavelEdicao;

}

