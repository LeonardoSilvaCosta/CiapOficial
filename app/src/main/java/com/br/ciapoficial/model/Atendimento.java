package com.br.ciapoficial.model;

import com.br.ciapoficial.model.in_atendimento.DemandaEspecifica;
import com.br.ciapoficial.model.in_atendimento.Deslocamento;
import com.br.ciapoficial.model.in_atendimento.DocumentoProduzido;
import com.br.ciapoficial.model.in_atendimento.Encaminhamento;
import com.br.ciapoficial.model.in_atendimento.MedicacaoPsiquiatrica;
import com.br.ciapoficial.model.in_atendimento.SinalSintoma;

import java.io.Serializable;
import java.util.ArrayList;

public class Atendimento implements Serializable {
   private int id;
   private String data;
   private ArrayList<String> oficiaisResponsaveis = new ArrayList<>();
   private ArrayList<String> atendidos = new ArrayList<>();
   private String unidade;
   private String modalidade;
   private String acesso;
   private String tipo;
   private String tipoAvaliacao;
   private String programa;
   private ArrayList<String> deslocamentos = new ArrayList<>();
   private String demandaGeral;
   private ArrayList<String> demandasEspecificas = new ArrayList<>();
   private String condicaoLaboral;
   private String procedimento;
   private ArrayList<String> documentosProduzidos = new ArrayList<>();
   private ArrayList<String> encaminhamentos = new ArrayList<>();
   private ArrayList<String> sinaisSintomas = new ArrayList<>();
   private ArrayList<String> medicacoesPsiquiatricas = new ArrayList<>();
   private boolean afastamento;
   private String evolucao;
   private String dataHoraCadastro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<String> getOficiaisResponsaveis() {
        return oficiaisResponsaveis;
    }

    public void setOficiaisResponsaveis(ArrayList<String> oficiaisResponsaveis) {
        this.oficiaisResponsaveis = oficiaisResponsaveis;
    }

    public ArrayList<String> getAtendidos() {
        return atendidos;
    }

    public void setAtendidos(ArrayList<String> atendidos) {
        this.atendidos = atendidos;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public ArrayList<String> getDeslocamentos() {
        return deslocamentos;
    }

    public void setDeslocamentos(ArrayList<String> deslocamentos) {
        this.deslocamentos = deslocamentos;
    }

    public String getDemandaGeral() {
        return demandaGeral;
    }

    public void setDemandaGeral(String demandaGeral) {
        this.demandaGeral = demandaGeral;
    }

    public ArrayList<String> getDemandasEspecificas() {
        return demandasEspecificas;
    }

    public void setDemandasEspecificas(ArrayList<String> demandasEspecificas) {
        this.demandasEspecificas = demandasEspecificas;
    }

    public String getCondicaoLaboral() {
        return condicaoLaboral;
    }

    public void setCondicaoLaboral(String condicaoLaboral) {
        this.condicaoLaboral = condicaoLaboral;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public ArrayList<String> getDocumentosProduzidos() {
        return documentosProduzidos;
    }

    public void setDocumentosProduzidos(ArrayList<String> documentosProduzidos) {
        this.documentosProduzidos = documentosProduzidos;
    }

    public ArrayList<String> getEncaminhamentos() {
        return encaminhamentos;
    }

    public void setEncaminhamentos(ArrayList<String> encaminhamentos) {
        this.encaminhamentos = encaminhamentos;
    }

    public ArrayList<String> getSinaisSintomas() {
        return sinaisSintomas;
    }

    public void setSinaisSintomas(ArrayList<String> sinaisSintomas) {
        this.sinaisSintomas = sinaisSintomas;
    }

    public ArrayList<String> getMedicacoesPsiquiatricas() {
        return medicacoesPsiquiatricas;
    }

    public void setMedicacoesPsiquiatricas(ArrayList<String> medicacoesPsiquiatricas) {
        this.medicacoesPsiquiatricas = medicacoesPsiquiatricas;
    }

    public boolean isAfastamento() {
        return afastamento;
    }

    public void setAfastamento(boolean afastamento) {
        this.afastamento = afastamento;
    }

    public String getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(String evolucao) {
        this.evolucao = evolucao;
    }

    public String getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(String dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }
}
