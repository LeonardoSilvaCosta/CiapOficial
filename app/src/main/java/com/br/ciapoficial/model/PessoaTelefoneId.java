package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class PessoaTelefoneId implements Serializable {
    protected Integer idPessoa;
    protected String telefone;
}
