package com.br.ciapoficial.network.api.dto;

import com.br.ciapoficial.model.Servico;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PageServico implements Serializable {
    private List<Servico> content;
    private int totalPages;
    private Long totalElements;
    private int number;

}
