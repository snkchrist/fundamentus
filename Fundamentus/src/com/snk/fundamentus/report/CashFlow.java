package com.snk.fundamentus.report;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CashFlow {
    private static final Locale LOCALE_BR = new Locale("pt", "BR");

    /**
     *
     * @param fluxoDeCaixa
     * @param taxaRetorno (taxaPerpetuidade)
     * @param taxaPerpetuidade
     * @return
     */
    public Double computeDiscountedCashFlow(final Double fluxoDeCaixa, final double taxaRetorno, final double taxaPerpetuidade) {
        List<Double> fluxoList = new ArrayList<Double>();
        fluxoList.add(fluxoDeCaixa);

        double taxaDeRetornoAjustada = 1 + (taxaRetorno / 100);
        double taxaDePerpetuidadeAjustada = 1 + (taxaPerpetuidade / 100);
        double fluxoDeCaixaAjustado = fluxoDeCaixa;

        int controle = 1;
        double fluxo = 0;

        do {
            double pow = Math.pow(taxaDeRetornoAjustada, controle);
            fluxo = fluxoDeCaixaAjustado / pow;
            fluxoList.add(fluxo);
            ++controle;

            fluxoDeCaixaAjustado = fluxoDeCaixaAjustado * taxaDePerpetuidadeAjustada;
        }
        while (fluxo > 0);

        for (Double double1 : fluxoList) {
            fluxo += double1;
        }

        return fluxo;
    }

    public static void main(final String[] args) {
        //percentual
        double taxaDesconto = 15;
        double taxaPerpetuidade = 6;

        CashFlow flow = new CashFlow();
        double computed = flow.computeDiscountedCashFlow(3013804000D, taxaDesconto, taxaPerpetuidade);
        int numeroAcoesEmitidas = 1614350000;
        double valorJustoAcao = computed / numeroAcoesEmitidas;

        System.out.println("Valor da empresa [" + flow.convert(computed) + "]");
        System.out.println("Valor justo das ações [" + flow.convert(valorJustoAcao) + "]");
        System.out.println("Taxa de desconto [" + taxaDesconto + "%]");
        System.out.println("Taxa de perpetuidade [" + taxaPerpetuidade + "%]");

    }

    public String convert(final double valor) {

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(LOCALE_BR);
        return currencyFormatter.format(valor);
    }
}
