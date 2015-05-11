package com.snk.fundamentus.models;

import javax.persistence.Embeddable;

import com.snk.fundamentus.enums.ShowOnTable;

@Embeddable
public class Demonstrativo12Meses {
    @ShowOnTable(name = "Receita liquida em 12 meses")
    private Long receitaLiquida;
    @ShowOnTable(name = "Ebit/Lucro antes impostos/taxas - 12 meses")
    private Long eBit;
    @ShowOnTable(name = "Lucro liquido em 12 meses")
    private Long lucroLiquido;

    public Long getReceitaLiquida() {
        return receitaLiquida;
    }

    public void setReceitaLiquida(final Long receitaLiquida) {
        this.receitaLiquida = receitaLiquida;
    }

    public Long getEBit() {
        return eBit;
    }

    public void setEbit(final Long eBit) {
        this.eBit = eBit;
    }

    public Long getLucroLiquido() {
        return lucroLiquido;
    }

    public void setLucroLiquido(final Long lucroLiquido) {
        this.lucroLiquido = lucroLiquido;
    }
}
