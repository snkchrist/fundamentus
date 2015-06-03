package com.snk.fundamentus.models;

import javax.persistence.Embeddable;

import com.snk.fundamentus.enums.DataType;
import com.snk.fundamentus.enums.ShowOnTable;

@Embeddable
public class Demonstrativo3Meses {
    @ShowOnTable(name = "Receita liquida (Trimestre)", type = DataType.Currency, format = "%1.0f")
    private Long receitaLiquida;
    @ShowOnTable(name = "Ebit/Lucro antes impostos/taxas (Trimestre)", type = DataType.Currency, format = "%1.0f")
    private Long eBit;
    @ShowOnTable(name = "Lucro liquido (Trimestre)", type = DataType.Currency, format = "%1.0f")
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
