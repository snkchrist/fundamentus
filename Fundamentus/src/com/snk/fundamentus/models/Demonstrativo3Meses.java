package com.snk.fundamentus.models;

import javax.persistence.Embeddable;

@Embeddable
public class Demonstrativo3Meses {

    private Long receitaLiquida;
    private Long eBit;
    private Long lucroLiquido;

    public Long getReceitaLiquida() {
        return receitaLiquida;
    }

    public void setReceitaLiquida(final Long receitaLiquida) {
        this.receitaLiquida = receitaLiquida;
    }

    public Long geteBit() {
        return eBit;
    }

    public void seteBit(final Long eBit) {
        this.eBit = eBit;
    }

    public Long getLucroLiquido() {
        return lucroLiquido;
    }

    public void setLucroLiquido(final Long lucroLiquido) {
        this.lucroLiquido = lucroLiquido;
    }

}
