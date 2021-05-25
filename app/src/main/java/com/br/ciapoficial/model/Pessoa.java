package com.br.ciapoficial.model;

import com.br.ciapoficial.enums.EscolaridadeEnum;
import com.br.ciapoficial.enums.EstadoCivilEnum;
import com.br.ciapoficial.enums.SexoEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    private Date dataNascimento;
    private String cpf;
    private SexoEnum sexo;
    private Cidade naturalidade;
    private EstadoCivilEnum estadoCivil;
    private int numeroFilhos;
    private EscolaridadeEnum escolaridade;
    private ArrayList<Telefone> telefones;
    private String email;
    private Endereco endereco;
    private Date dataCadastro;
    private Funcionario responsavelCadastro;
    private Date dataEdicao;
    private Funcionario responsavelEdicao;

}

