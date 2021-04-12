package com.br.ciapoficial.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Atendido implements Serializable {
    private int id;
    private String tipoAtendido;
    private String nomeCompleto;
    private String dataNascimento;
    private String cpf;
    private String sexo;
    private ArrayList<String> telefones;
    private String email;
    private String estadoCivil;
    private String ufNatal;
    private String cidadeNatal;
    private String escolaridade;
    private String numeroFilhos;
    private Titular titular;
    private String vinculo;
    private String cep;
    private String uf;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String rgMilitar;
    private String postoGradCat;
    private String nomeGuerra;
    private String unidade;
    private String quadro;
    private String dataInclusao;
    private String situacaoFuncional;
    private String dataHoraCadastro;
    private String dataHoraAtualizacao;

    public Atendido(Parcel in) {
        this.id = in.readInt();
        this.tipoAtendido = in.readString();
        this.nomeCompleto = in.readString();
        this.dataNascimento = in.readString();
        this.cpf = in.readString();
        this.sexo = in.readString();
        this.telefones = in.createStringArrayList();
        this.email = in.readString();
        this.estadoCivil = in.readString();
        this.ufNatal = in.readString();
        this.cidadeNatal = in.readString();
        this.escolaridade = in.readString();
        this.numeroFilhos = in.readString();
        this.titular = (Titular) in.readValue(Titular.class.getClassLoader());
        this.vinculo = in.readString();
        this.cep = in.readString();
        this.uf = in.readString();
        this.cidade = in.readString();
        this.bairro = in.readString();
        this.logradouro = in.readString();
        this.numero = in.readString();
        this.rgMilitar = in.readString();
        this.postoGradCat = in.readString();
        this.nomeGuerra = in.readString();
        this.unidade = in.readString();
        this.quadro = in.readString();
        this.dataInclusao = in.readString();
        this.situacaoFuncional = in.readString();
        this.dataHoraCadastro = in.readString();
        this.dataHoraAtualizacao = in.readString();
    }



    public Atendido() {

    }

    public String getTipoAtendido() {
        return tipoAtendido;
    }

    public void setTipoAtendido(String tipoAtendido) {
        this.tipoAtendido = tipoAtendido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public ArrayList<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(ArrayList<String> telefones) {
        this.telefones = telefones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getUfNatal() {
        return ufNatal;
    }

    public void setUfNatal(String ufNatal) {
        this.ufNatal = ufNatal;
    }

    public String getCidadeNatal() {
        return cidadeNatal;
    }

    public void setCidadeNatal(String cidadeNatal) {
        this.cidadeNatal = cidadeNatal;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getNumeroFilhos() {
        return numeroFilhos;
    }

    public void setNumeroFilhos(String numeroFilhos) {
        this.numeroFilhos = numeroFilhos;
    }

    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRgMilitar() {
        return rgMilitar;
    }

    public void setRgMilitar(String rgMilitar) {
        this.rgMilitar = rgMilitar;
    }

    public String getPostoGradCat() {
        return postoGradCat;
    }

    public void setPostoGradCat(String postoGradCat) {
        this.postoGradCat = postoGradCat;
    }

    public String getNomeGuerra() {
        return nomeGuerra;
    }

    public void setNomeGuerra(String nomeGuerra) {
        this.nomeGuerra = nomeGuerra;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getQuadro() {
        return quadro;
    }

    public void setQuadro(String quadro) {
        this.quadro = quadro;
    }

    public String getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(String dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getSituacaoFuncional() {
        return situacaoFuncional;
    }

    public void setSituacaoFuncional(String situacaoFuncional) {
        this.situacaoFuncional = situacaoFuncional;
    }

    public String getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(String dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public String getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(String dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    @Override
    public String toString () {
        return nomeCompleto + " - " + cpf;
    }

}
