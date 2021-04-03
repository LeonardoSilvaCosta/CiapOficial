package com.br.ciapoficial.model;

import java.io.Serializable;

public class Telefone implements Serializable {
    int id;
    String telefone;

    public Telefone() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
