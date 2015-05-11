package com.snk.fundamentus.models;

import com.snk.fundamentus.enums.DataType;
import com.snk.fundamentus.enums.ShowOnTable;
import com.snk.fundamentus.report.ReportFundamentalista;

public class Indices {
    private final ReportFundamentalista report;

    @ShowOnTable(format = "%1.2f", name = "Disponibilidades", type = DataType.Currency)
    private final double disponibilidades;

    @ShowOnTable(format = "%1.2f", name = "Divida Bruta", type = DataType.Currency)
    private final double dividaBruta;

    @ShowOnTable(format = "%1.2f", name = "Divida Liquida", type = DataType.Currency)
    private final double dividaLiquida;

    @ShowOnTable(format = "%1.2f", name = "Indice Lucratividade")
    private final double indiceLucratividade;

    @ShowOnTable(format = "%1.2f", name = "Indice Rotatividade")
    private final double indiceRotatividade;

    @ShowOnTable(format = "%1.2f", name = "Liquidez corrente (12 meses)")
    private final double liquidezCorrentePorAno;

    @ShowOnTable(format = "%1.2f", name = "Liquidez Corrente (trimestre)")
    private final double liquidezCorrenteUltimoTrimestre;

    @ShowOnTable(format = "%1.2f", name = "Liquidez Geral")
    private final double liquidezGeral;

    @ShowOnTable(format = "%1.2f", name = "Liquidez Imediata")
    private final double liquidezImediata;

    @ShowOnTable(format = "%1.2f", name = "Liquidez seca")
    private final double liquidezSeca;

    @ShowOnTable(format = "%1.2f", name = "Lucro por ação")
    private final double lpa;

    @ShowOnTable(format = "%1.2f", name = "Lucratividade sobre P/L")
    private final double lucratividadePL;

    @ShowOnTable(format = "%1.2f", name = "Preço sobre Lucro por Ação")
    private final double pl;

    @ShowOnTable(format = "%1.2f", name = "Preço sobre Valor Patrimonial por Ação")
    private final double pvpa;

    @ShowOnTable(format = "%1.2f", name = "Q de Tobin")
    private final double qdeTobin;

    @ShowOnTable(format = "%1.2f", name = "Relação dívida liquida sobre Patrimônio Liquido")
    private final double relacaoDividaLiquidaPatrimonioLiquido;

    @ShowOnTable(format = "%1.2f", name = "Vendas 12 Meses", type = DataType.Currency)
    private final double vendasPorAno;

    @ShowOnTable(format = "%1.2f", name = "Valor Patrimonial por Ação")
    private final double vpa;

    @ShowOnTable(format = "%1.2f", name = "Lucro antes de Juros e Impostos (trimestre)", type = DataType.Currency)
    private final double ebitUltimoTrimestre;

    @ShowOnTable(format = "%1.2f", name = "Lucro antes de Juros e Impostos (12 Meses)", type = DataType.Currency)
    private final double ebit12Meses;

    @ShowOnTable(format = "%1.2f", name = "ROIC-Retorno sobre Capital Investido", type = DataType.Percentage)
    private final double roic;

    @ShowOnTable(format = "%1.2f", name = "ROE-Retorno sobre o Patrimônio Líquido", type = DataType.Percentage)
    private final double roe;

    @ShowOnTable(format = "%1.2f", name = "Lucro Líquido", type = DataType.Currency)
    private final double lucroLiquido;

    public Indices(final Empresa empresa, final int ano) {
        report = new ReportFundamentalista(empresa, ano);
        disponibilidades = report.getDisponibilidades();
        dividaBruta = report.getDividaBruta();
        dividaLiquida = report.getDividaLiquida();
        indiceLucratividade = report.getIndiceLucratividade();
        indiceRotatividade = report.getIndiceRotatividade();
        liquidezCorrentePorAno = report.getLiquidezCorrentePorAno();
        liquidezCorrenteUltimoTrimestre = report.getLiquidezCorrenteUltimoTrimestre();
        liquidezGeral = report.getLiquidezGeral();
        liquidezImediata = report.getLiquidezImediata();
        liquidezSeca = report.getLiquidezSeca();
        lpa = report.getLPA();
        lucratividadePL = report.getLucratividadePL();
        pl = report.getPL();
        pvpa = report.getPVPA();
        qdeTobin = report.getQdeTobin();
        relacaoDividaLiquidaPatrimonioLiquido = report.getRelacaoDividaLiquidaPatrimonioLiquido();
        vendasPorAno = report.getVendasUltimos12Meses();
        vpa = report.getVPA();
        ebitUltimoTrimestre = report.getEbitUltimoTrimestre();
        ebit12Meses = report.getEbit12Meses();
        roic = report.getROIC();
        roe = report.getROE();
        lucroLiquido = report.getLucroLiquido();
    }

    public ReportFundamentalista getReport() {
        return report;
    }

    public double getDisponibilidades() {
        return disponibilidades;
    }

    public double getDividaBruta() {
        return dividaBruta;
    }

    public double getDividaLiquida() {
        return dividaLiquida;
    }

    public double getIndiceLucratividade() {
        return indiceLucratividade;
    }

    public double getIndiceRotatividade() {
        return indiceRotatividade;
    }

    public double getLiquidezCorrentePorAno() {
        return liquidezCorrentePorAno;
    }

    public double getLiquidezCorrenteUltimoTrimestre() {
        return liquidezCorrenteUltimoTrimestre;
    }

    public double getLiquidezGeral() {
        return liquidezGeral;
    }

    public double getLiquidezImediata() {
        return liquidezImediata;
    }

    public double getLiquidezSeca() {
        return liquidezSeca;
    }

    public double getLpa() {
        return lpa;
    }

    public double getLucratividadePL() {
        return lucratividadePL;
    }

    public double getPl() {
        return pl;
    }

    public double getPvpa() {
        return pvpa;
    }

    public double getQdeTobin() {
        return qdeTobin;
    }

    public double getRelacaoDividaLiquidaPatrimonioLiquido() {
        return relacaoDividaLiquidaPatrimonioLiquido;
    }

    public double getVendasPorAno() {
        return vendasPorAno;
    }

    public double getVpa() {
        return vpa;
    }

}
