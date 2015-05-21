package com.snk.fundamentus.models;

import com.snk.fundamentus.enums.DataType;
import com.snk.fundamentus.enums.ShowOnTable;
import com.snk.fundamentus.report.LiquidezIndice;
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

    @ShowOnTable(format = "%1.2f", name = "Liquidez corrente (Média 12 meses)")
    private final double liquidezCorrente12Meses;

    @ShowOnTable(format = "%1.2f", name = "Liquidez Corrente (trimestre)")
    private final double liquidezCorrenteUltimoTrimestre;

    @ShowOnTable(format = "%1.2f", name = "Liquidez Geral (trimestre)")
    private final double liquidezGeral;

    @ShowOnTable(format = "%1.2f", name = "Liquidez Imediata (trimestre)")
    private final double liquidezImediata;

    @ShowOnTable(format = "%1.2f", name = "Liquidez seca (trimestre)")
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

    @ShowOnTable(format = "%1.2f", name = "Lucro Líquido (12 meses)", type = DataType.Currency)
    private final double lucroLiquido;

    @ShowOnTable(format = "%1.2f", name = "Dividendos (12 meses)", type = DataType.Currency)
    private final double dividendos;

    @ShowOnTable(name = "Trimestre")
    private final String trimestre;

    @ShowOnTable(format = "%1.2f", name = "Desvio padrão histórico")
    private final double desvioPadrao;

    @ShowOnTable(format = "%1.2f", name = "Desvio padrão últimos 5 anos")
    private final double desvioPadraoUltimos5Anos;

    public Indices(final Empresa empresa, final int ano) {
        report = new ReportFundamentalista(empresa, ano);
        LiquidezIndice liquidez = new LiquidezIndice(report);

        disponibilidades = report.getDisponibilidadesUltimoTrimestre();
        dividaBruta = report.getDividaBrutaUltimoTrimestre();
        dividaLiquida = report.getDividaLiquidaUltimoTrimestre();
        indiceLucratividade = report.getIndiceLucratividade();
        indiceRotatividade = report.getIndiceRotatividade();
        liquidezCorrente12Meses = liquidez.getMediaLiquidezCorrenteUltimos12Meses();
        liquidezCorrenteUltimoTrimestre = liquidez.getLiquidezCorrenteUltimoTrimestre();
        liquidezGeral = liquidez.getLiquidezGeralUltimoTrimestre();
        liquidezImediata = liquidez.getLiquidezImediataUltimoTrimestre();
        liquidezSeca = liquidez.getLiquidezSecaUltimoTrimestre();
        lpa = report.getLPA();
        lucratividadePL = report.getLucratividadePL();
        pl = report.getPL();
        pvpa = report.getPVPA();
        qdeTobin = report.getQdeTobinUltimoTrimestre();
        relacaoDividaLiquidaPatrimonioLiquido = report.getRelacaoDividaLiquidaPatrimonioLiquido();
        vendasPorAno = report.getVendasUltimos12Meses();
        vpa = report.getVPA();
        ebitUltimoTrimestre = report.getEbitUltimoTrimestre();
        ebit12Meses = report.getEbitUltimos12Meses();
        roic = report.getROICUltimoTrimestre();
        roe = report.getROEUltimoTrimestre();
        lucroLiquido = report.getLucroLiquidoUltimos12Meses();
        dividendos = report.getDividendosDistribuidosUltimos12Meses();
        trimestre = report.getTrimestreStrByDate(empresa.getUltimoBalancoRegistrado());
        desvioPadrao = report.getDesvioPadraoHistorico();
        desvioPadraoUltimos5Anos = report.getDesvioPadraoUltimos5Anos();
    }

}
