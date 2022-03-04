package com.br.ciapoficial.network.api.dto;

import com.br.ciapoficial.model.Usuario;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PageUsuario implements Serializable {
    private List<Usuario> content;
    private int totalPages;
    private Long totalElements;
    private int number;

}
