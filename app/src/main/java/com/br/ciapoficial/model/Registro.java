package com.br.ciapoficial.model;

import java.io.Serializable;
import java.util.List;

public class Registro implements Serializable {

    private Integer id;
    private String documento;
    private String url;
    private List<Prontuario> prontuarios;
}
