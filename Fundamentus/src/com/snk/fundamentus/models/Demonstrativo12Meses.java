package com.snk.fundamentus.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Demonstrativo12Meses {

    @Id
    @GeneratedValue
    private long id;

    private Long receitaLiquida;
    private Long eBit;
    private Long lucroLiquido;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getReceitaLiquida() {
        return receitaLiquida;
    }

    public void setReceitaLiquida(Long receitaLiquida) {
        this.receitaLiquida = receitaLiquida;
    }

    public Long getEBit() {
        return eBit;
    }

    public void setEbit(Long eBit) {
        this.eBit = eBit;
    }

    public Long getLucroLiquido() {
        return lucroLiquido;
    }

    public void setLucroLiquido(Long lucroLiquido) {
        this.lucroLiquido = lucroLiquido;
    }

}
