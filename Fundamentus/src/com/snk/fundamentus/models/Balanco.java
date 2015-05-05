package com.snk.fundamentus.models;

import javax.persistence.Embeddable;

@Embeddable
public class Balanco {

    @RepresentableOnTable(name = "Ativos")
    private Long ativos;
    @RepresentableOnTable(name = "Disponibilidades")
    private Long disponibilidades;
    @RepresentableOnTable(name = "Ativos Circulantes")
    private Long ativosCirculantes;
    @RepresentableOnTable(name = "Divida Bruta")
    private Long dividaBruta;
    @RepresentableOnTable(name = "Divida Liquida")
    private Long dividaLiquida;
    @RepresentableOnTable(name = "Patrimonio Liquido")
    private Long patrimonioLiquido;

    public Long getAtivos() {
        return ativos;
    }

    public void setAtivos(final Long ativos) {
        this.ativos = ativos;
    }

    public Long getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(final Long disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public Long getAtivosCirculantes() {
        return ativosCirculantes;
    }

    public void setAtivosCirculantes(final Long ativosCirculantes) {
        this.ativosCirculantes = ativosCirculantes;
    }

    public Long getDividaBruta() {
        return dividaBruta;
    }

    public void setDividaBruta(final Long dividaBruta) {
        this.dividaBruta = dividaBruta;
    }

    public Long getDividaLiquida() {
        return dividaLiquida;
    }

    public void setDividaLiquida(final Long dividaLiquida) {
        this.dividaLiquida = dividaLiquida;
    }

    public Long getPatrimonioLiquido() {
        return patrimonioLiquido;
    }

    public void setPatrimonioLiquido(final Long patrimonioLiquido) {
        this.patrimonioLiquido = patrimonioLiquido;
    }
}
