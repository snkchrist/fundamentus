package com.snk.fundamentus.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Empresa {

    @Id
    @GeneratedValue
    private long id;

    private String sigla;

    private String tipo;

    private String nome;

    private String setor;

    private String subSetor;

    private Double cotacao;

    private String dataUltimaCotacao;

    private Double minimo52Semanas;

    private Double maximo52Semanas;

    private Long volumeMedio2Meses;

    private String ultimoBalancoProcessado;

    private Long numeroDeAcoes;

    private Long valorMercado;

    private Long valorEmpresa;

    private Balanco balanco;
    private Oscilacoes oscilacoes;
    private Demonstrativo3Meses demonstracao3meses;
    private Demonstrativo12Meses demonstracao12meses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getSubSetor() {
        return subSetor;
    }

    public void setSubSetor(String subSetor) {
        this.subSetor = subSetor;
    }

    public Double getCotacao() {
        return cotacao;
    }

    public void setCotacao(Double cotacao) {
        this.cotacao = cotacao;
    }

    public String getDataUltimaCotacao() {
        return dataUltimaCotacao;
    }

    public void setDataUltimaCotacao(String dataUltimaCotacao) {
        this.dataUltimaCotacao = dataUltimaCotacao;
    }

    public Double getMinimo52Semanas() {
        return minimo52Semanas;
    }

    public void setMinimo52Semanas(Double minimo52Semanas) {
        this.minimo52Semanas = minimo52Semanas;
    }

    public Double getMaximo52Semanas() {
        return maximo52Semanas;
    }

    public void setMaximo52Semanas(Double maximo52Semanas) {
        this.maximo52Semanas = maximo52Semanas;
    }

    public Long getVolumeMedio2Meses() {
        return volumeMedio2Meses;
    }

    public void setVolumeMedio2Meses(Long volumeMedio2Meses) {
        this.volumeMedio2Meses = volumeMedio2Meses;
    }

    public String getUltimoBalancoProcessado() {
        return ultimoBalancoProcessado;
    }

    public void setUltimoBalancoProcessado(String ultimoBalancoProcessado) {
        this.ultimoBalancoProcessado = ultimoBalancoProcessado;
    }

    public Long getNumeroDeAcoes() {
        return numeroDeAcoes;
    }

    public void setNumeroDeAcoes(Long numeroDeAcoes) {
        this.numeroDeAcoes = numeroDeAcoes;
    }

    public Long getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(Long valorMercado) {
        this.valorMercado = valorMercado;
    }

    public Long getValorEmpresa() {
        return valorEmpresa;
    }

    public void setValorEmpresa(Long valorEmpresa) {
        this.valorEmpresa = valorEmpresa;
    }

    public Balanco getBalanco() {
        return balanco;
    }

    public void setBalanco(Balanco balanco) {
        this.balanco = balanco;
    }

    public Oscilacoes getOscilacoes() {
        return oscilacoes;
    }

    public void setOscilacoes(Oscilacoes oscilacoes) {
        this.oscilacoes = oscilacoes;
    }

    public Demonstrativo3Meses getDemonstracao3meses() {
        return demonstracao3meses;
    }

    public void setDemonstracao3meses(Demonstrativo3Meses demonstracao3meses) {
        this.demonstracao3meses = demonstracao3meses;
    }

    public Demonstrativo12Meses getDemonstracao12meses() {
        return demonstracao12meses;
    }

    public void setDemonstracao12meses(Demonstrativo12Meses demonstracao12meses) {
        this.demonstracao12meses = demonstracao12meses;
    }
}
