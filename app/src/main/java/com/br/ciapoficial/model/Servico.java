package com.br.ciapoficial.model;

import com.br.ciapoficial.model.in_servico.Acesso;
import com.br.ciapoficial.model.in_servico.DemandaEspecifica;
import com.br.ciapoficial.model.in_servico.DemandaGeral;
import com.br.ciapoficial.model.in_servico.Deslocamento;
import com.br.ciapoficial.model.in_servico.DocumentoProduzido;
import com.br.ciapoficial.model.in_servico.Encaminhamento;
import com.br.ciapoficial.model.in_servico.Modalidade;
import com.br.ciapoficial.model.in_servico.Procedimento;
import com.br.ciapoficial.model.in_servico.Programa;
import com.br.ciapoficial.model.in_servico.TipoServico;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Servico implements Serializable {
    @EqualsAndHashCode.Include
    private Integer id;
    private TipoServico tipoServico;
    private LocalDate data;
    private Set<Especialista> especialistas = new HashSet<>();
    private Set<Usuario> usuarios = new HashSet<>();
    private Unidade unidade;
    private Modalidade modalidade;
    private Acesso acesso;
    private Programa programa;
    private Set<Deslocamento> deslocamentos = new HashSet<>();
    private DemandaGeral demandaGeral;
    private Set<DemandaEspecifica> demandasEspecificas = new HashSet<>();
    private Procedimento procedimento;
    private Set<Encaminhamento> encaminhamentos = new HashSet<>();
    private Set<DocumentoProduzido> documentosProduzidos = new HashSet<>();
    private boolean afastamento;
    private String evolucao;
    private LocalDateTime dataCadastro;
    private Funcionario responsavelCadastroServico;
}
