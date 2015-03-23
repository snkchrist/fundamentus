package com.snk.fundamentus.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Balanco {

    @Id
    @GeneratedValue
    private long id;

    private Long ativos;
    private Long disponibilidades;
    private Long ativosCirculantes;
    private Long dividaBruta;
    private Long dividaLiquida;
    private Long patrimonioLiquido;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getAtivos() {
        return ativos;
    }

    public void setAtivos(Long ativos) {
        this.ativos = ativos;
    }

    public Long getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(Long disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public Long getAtivosCirculantes() {
        return ativosCirculantes;
    }

    public void setAtivosCirculantes(Long ativosCirculantes) {
        this.ativosCirculantes = ativosCirculantes;
    }

    public Long getDividaBruta() {
        return dividaBruta;
    }

    public void setDividaBruta(Long dividaBruta) {
        this.dividaBruta = dividaBruta;
    }

    public Long getDividaLiquida() {
        return dividaLiquida;
    }

    public void setDividaLiquida(Long dividaLiquida) {
        this.dividaLiquida = dividaLiquida;
    }

    public Long getPatrimonioLiquido() {
        return patrimonioLiquido;
    }

    public void setPatrimonioLiquido(Long patrimonioLiquido) {
        this.patrimonioLiquido = patrimonioLiquido;
    }
}
