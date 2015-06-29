package com.snk.fundamentus.models;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class Cotacao implements Comparable<Cotacao> {
    private Date data;
    private Double abertura;
    private Double maxima;
    private Double minima;
    private Double fechamento;
    private Double fechamentoAdj;
    private Long volume;

    public Date getData() {
        return data;
    }

    public Double getAbertura() {
        return abertura;
    }

    public Double getMaxima() {
        return maxima;
    }

    public Double getMinima() {
        return minima;
    }

    public Double getFechamento() {
        return fechamento;
    }

    public Double getFechamentoAdj() {
        return fechamentoAdj;
    }

    public Long getVolume() {
        return volume;
    }

    public void setData(final Date data) {
        this.data = data;
    }

    public void setAbertura(final Double abertura) {
        this.abertura = abertura;
    }

    public void setMaxima(final Double maxima) {
        this.maxima = maxima;
    }

    public void setMinima(final Double minima) {
        this.minima = minima;
    }

    public void setFechamento(final Double fechamento) {
        this.fechamento = fechamento;
    }

    public void setFechamentoAdj(final Double fechamentoAdj) {
        this.fechamentoAdj = fechamentoAdj;
    }

    public void setVolume(final Long volume) {
        this.volume = volume;
    }

    @Override
    public int compareTo(final Cotacao o) {
        int compare = 0;

        if (null != o) {
            compare = this.getData().compareTo(o.getData());
        }

        return compare;

    }

}
