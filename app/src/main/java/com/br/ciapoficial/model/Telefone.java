package com.br.ciapoficial.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Telefone implements Serializable {

    private PessoaTelefoneId pessoaTelefoneId;

    public Telefone() {
    }

    public Telefone(PessoaTelefoneId pessoaTelefoneId) {
        this.pessoaTelefoneId = pessoaTelefoneId;
    }

    @Override
    public String toString() {
        return this.pessoaTelefoneId.telefone;
    }
}
