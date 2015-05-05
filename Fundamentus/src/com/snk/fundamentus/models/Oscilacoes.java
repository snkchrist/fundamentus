package com.snk.fundamentus.models;

import javax.persistence.Embeddable;

@Embeddable
public class Oscilacoes {
    private Double dia;
    @RepresentableOnTable(name = "Oscilação do Mês")
    private Double mes;
    @RepresentableOnTable(name = "Oscilação dos últimos 30 dias")
    private Double ultimos30Dias;
    @RepresentableOnTable(name = "Oscilação dos últimos 12 meses")
    private Double ultimos12Meses;
    @RepresentableOnTable(name = "Oscilação Ano 2015")
    private Double ano2015;
    @RepresentableOnTable(name = "Oscilação Ano 2014")
    private Double ano2014;
    @RepresentableOnTable(name = "Oscilação Ano 2013")
    private Double ano2013;
    @RepresentableOnTable(name = "Oscilação Ano 2012")
    private Double ano2012;
    @RepresentableOnTable(name = "Oscilação Ano 2011")
    private Double ano2011;
    @RepresentableOnTable(name = "Oscilação Ano 2010")
    private Double ano2010;

    public Double getDia() {
        return dia;
    }

    public void setDia(final Double dia) {
        this.dia = dia;
    }

    public Double getMes() {
        return mes;
    }

    public void setMes(final Double mes) {
        this.mes = mes;
    }

    public Double getUltimos30Dias() {
        return ultimos30Dias;
    }

    public void setUltimos30Dias(final Double ultimos30Dias) {
        this.ultimos30Dias = ultimos30Dias;
    }

    public Double getUltimos12Meses() {
        return ultimos12Meses;
    }

    public void setUltimos12Meses(final Double ultimos12Meses) {
        this.ultimos12Meses = ultimos12Meses;
    }

    public Double getAno2015() {
        return ano2015;
    }

    public void setAno2015(final Double ano2015) {
        this.ano2015 = ano2015;
    }

    public Double getAno2014() {
        return ano2014;
    }

    public void setAno2014(final Double ano2014) {
        this.ano2014 = ano2014;
    }

    public Double getAno2013() {
        return ano2013;
    }

    public void setAno2013(final Double ano2013) {
        this.ano2013 = ano2013;
    }

    public Double getAno2012() {
        return ano2012;
    }

    public void setAno2012(final Double ano2012) {
        this.ano2012 = ano2012;
    }

    public Double getAno2011() {
        return ano2011;
    }

    public void setAno2011(final Double ano2011) {
        this.ano2011 = ano2011;
    }

    public Double getAno2010() {
        return ano2010;
    }

    public void setAno2010(final Double ano2010) {
        this.ano2010 = ano2010;
    }
}
