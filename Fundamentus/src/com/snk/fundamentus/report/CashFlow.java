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

    public double getTaxaDesconto(
            final double beta,
            final double custoDividaEmpresa,
            final double percentualRecursosProprios,
            final double percentualRecursosTerceiros) {

        /**
         * TODAS AS TAXAS SÃO PARA OS ÚLTIMOS 12 MESES
         */
        double selic = 13.75;
        double poupanca = 7.39;
        double cdi = 11.58;
        double titulos = 12.01;
        double ibovespa = 1.27;
        double inflacao = 8.47;
        double media = (selic + poupanca + cdi + titulos + ibovespa) / 5;

        ///retornoEsperadoSobreIndiceMercado
        double rm = media + inflacao;

        //retorno Ativo Livre de Risco (usada a TJLP dos últimos 12 meses)
        double rf = 5.45;

        //custo de capital próprio = KE
        double custoCapitalProprio = rf + beta * (rm - rf);

        double a1 = percentualRecursosProprios * custoCapitalProprio;
        double a2 = percentualRecursosTerceiros * custoDividaEmpresa;

        double taxaDesconto = (a1 + a2);
        System.out.println("A taxa de desconto é de = " + taxaDesconto);

        return taxaDesconto;

    }

    public static void main(final String[] args) {
        CashFlow flow = new CashFlow();

        //RiscoNegocio
        //crescimento esperado sobre o mercado 1 vez e 0.64 sobre o setor
        //exemplo o setor vai crescer 9% neste ano e a empresa espera um crescimento de 14.80
        // 14.80 - 9 = 5,8
        // 5,8 / 9 = 0,64
        // 1 + 0,64 = 1.64
        double beta = 1.64;
        //Taxa de juros sobre o capital de terceiros
        double custoDividaEmpresa = 6.2;

        //QUANTO DE RECURSOS PROPRIOS SERÁ INVESTIDO NA EMPRESA E QUANDO DE RECURSOS DE TERCEIROS (valor percentual)
        double recursosProprios = 0.7828;
        double recursosTerceiros = 0.2171;

        double taxaDesconto = flow.getTaxaDesconto(beta, custoDividaEmpresa, recursosProprios, recursosTerceiros);

        //crecimento lucro liquido media de 5 anos
        double taxaPerpetuidade = 11.60;
        double fluxoDeCaixa2015 = 3013804000D;
        int numeroAcoesEmitidas = 1614350000;

        double computed = flow.computeDiscountedCashFlow(fluxoDeCaixa2015, taxaDesconto, taxaPerpetuidade);

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
