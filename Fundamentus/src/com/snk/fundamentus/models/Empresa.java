package com.snk.fundamentus.models;

import java.util.List;

import javax.persistence.Embedded;
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

    @Embedded
    private List<DemonstrativoResultado> demonstrativoList;
    @Embedded
    private List<BalancoPatrimonial> balancoList;
    @Embedded
    private Balanco balanco;
    @Embedded
    private Oscilacoes oscilacoes;
    @Embedded
    private Demonstrativo3Meses demonstracao3meses;
    @Embedded
    private Demonstrativo12Meses demonstracao12meses;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(final String sigla) {
        this.sigla = sigla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(final String setor) {
        this.setor = setor;
    }

    public String getSubSetor() {
        return subSetor;
    }

    public void setSubSetor(final String subSetor) {
        this.subSetor = subSetor;
    }

    public Double getCotacao() {
        return cotacao;
    }

    public void setCotacao(final Double cotacao) {
        this.cotacao = cotacao;
    }

    public String getDataUltimaCotacao() {
        return dataUltimaCotacao;
    }

    public void setDataUltimaCotacao(final String dataUltimaCotacao) {
        this.dataUltimaCotacao = dataUltimaCotacao;
    }

    public Double getMinimo52Semanas() {
        return minimo52Semanas;
    }

    public void setMinimo52Semanas(final Double minimo52Semanas) {
        this.minimo52Semanas = minimo52Semanas;
    }

    public Double getMaximo52Semanas() {
        return maximo52Semanas;
    }

    public void setMaximo52Semanas(final Double maximo52Semanas) {
        this.maximo52Semanas = maximo52Semanas;
    }

    public Long getVolumeMedio2Meses() {
        return volumeMedio2Meses;
    }

    public void setVolumeMedio2Meses(final Long volumeMedio2Meses) {
        this.volumeMedio2Meses = volumeMedio2Meses;
    }

    public String getUltimoBalancoProcessado() {
        return ultimoBalancoProcessado;
    }

    public void setUltimoBalancoProcessado(final String ultimoBalancoProcessado) {
        this.ultimoBalancoProcessado = ultimoBalancoProcessado;
    }

    public Long getNumeroDeAcoes() {
        return numeroDeAcoes;
    }

    public void setNumeroDeAcoes(final Long numeroDeAcoes) {
        this.numeroDeAcoes = numeroDeAcoes;
    }

    public Long getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(final Long valorMercado) {
        this.valorMercado = valorMercado;
    }

    public Long getValorEmpresa() {
        return valorEmpresa;
    }

    public void setValorEmpresa(final Long valorEmpresa) {
        this.valorEmpresa = valorEmpresa;
    }

    public Balanco getBalanco() {
        return balanco;
    }

    public void setBalanco(final Balanco balanco) {
        this.balanco = balanco;
    }

    public Oscilacoes getOscilacoes() {
        return oscilacoes;
    }

    public void setOscilacoes(final Oscilacoes oscilacoes) {
        this.oscilacoes = oscilacoes;
    }

    public Demonstrativo3Meses getDemonstracao3meses() {
        return demonstracao3meses;
    }

    public void setDemonstracao3meses(final Demonstrativo3Meses demonstracao3meses) {
        this.demonstracao3meses = demonstracao3meses;
    }

    public Demonstrativo12Meses getDemonstracao12meses() {
        return demonstracao12meses;
    }

    public void setDemonstracao12meses(final Demonstrativo12Meses demonstracao12meses) {
        this.demonstracao12meses = demonstracao12meses;
    }

    public List<BalancoPatrimonial> getBalancoList() {
        return balancoList;
    }

    public void setBalancoList(final List<BalancoPatrimonial> balancoList) {
        this.balancoList = balancoList;
    }

    public List<DemonstrativoResultado> getDemonstrativoList() {
        return demonstrativoList;
    }

    public void setDemonstrativoList(final List<DemonstrativoResultado> demonstrativoList) {
        this.demonstrativoList = demonstrativoList;
    }

}
