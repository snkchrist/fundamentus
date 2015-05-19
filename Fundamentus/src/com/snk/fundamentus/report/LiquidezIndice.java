package com.snk.fundamentus.report;

import java.util.List;

import com.snk.fundamentus.models.BalancoPatrimonial;

/**
 * O grau de liquidez de um ativo depende da rapidez com que ele pode ser
 * transformado em dinheiro sem perder valor. A gestão da liquidez da
 * empresa busca o equilíbrio entre os prazos das dívidas com os prazos dos
 * ativos, a fim de se evitar sua insolvência. Portanto, essa análise
 * procura avaliar as condições que a empresa tem para saldar suas
 * exigibilidades.”
 *
 * @param ano
 * @return
 */
public class LiquidezIndice {
    private final ReportFundamentalista report;

    public LiquidezIndice(final ReportFundamentalista report) {
        this.report = report;
    }

    /**
     * O índice de liquidez imediata é obtido através do resultado da divisão
     * entre a soma do caixa e dos equivalentes de caixa pelo passivo. está
     * representado possuindo XX ativos disponíveis (ou reais em espécie) para
     * cobrir cada real de dívida contraída a curto prazo (PINHEIRO. 2009, p.
     * 412).
     *
     * @param ano
     * @return
     */
    public double getLiquidezImediataUltimoTrimestre() {
        BalancoPatrimonial balancoUltimoTrimestre = report.getBalancoUltimoTrimestre();
        double liquidezImediata = -1;

        double caixaEEquivalentesDeCaixa = 0;
        double passivoCirculante = 0;

        caixaEEquivalentesDeCaixa = balancoUltimoTrimestre.getCaixaEEquivalentesDeCaixa();
        passivoCirculante = balancoUltimoTrimestre.getPassivoCirculante();

        liquidezImediata = caixaEEquivalentesDeCaixa / passivoCirculante;

        return liquidezImediata;
    }

    /**
     * Para cada real de dívidas vencíveis a curto prazo, a companhia possui XX
     * ativos de curto prazo.
     *
     * @return
     */
    public double getLiquidezCorrenteUltimoTrimestre() {
        double liquidezCorrente = -1;
        BalancoPatrimonial balancoPatrimonialUltimoTrimestre = report.getBalancoUltimoTrimestre();

        if (null != balancoPatrimonialUltimoTrimestre) {
            liquidezCorrente = getLiquidezCorrente(balancoPatrimonialUltimoTrimestre);
        }

        return liquidezCorrente;
    }

    /**
     * Para se obter o índice de liquidez seca é necessário subtrair os estoques
     * do ativo circulante, e deste resultado divide-se pelo passivo circulante.
     * Conforme Pinheiro (2009, p. 413), este índice representa a porcentagem
     * das dívidas de curto prazo em condições de serem pagas mediante a
     * utilização de itens monetários de maior liquidez no ativo circulante e
     * supõe que os compromissos a curto prazo serão atendidos pela realização
     * exclusivamente dos direitos realizáveis a curto prazo e, como pode ser
     * observado na tabela 3, este resultado para a empresa Tegma é bastante
     * satisfatório.
     *
     * @param ano
     * @return
     */
    public double getLiquidezSecaUltimoTrimestre() {
        BalancoPatrimonial balancoUltimoTrimestre = report.getBalancoUltimoTrimestre();
        return getLiquidezSeca(balancoUltimoTrimestre);
    }

    /**
     * índice de liquidez geral retrata quantos reais a empresa dispõe de curto
     * e longo prazos para cobrir cada real de dívida contraída. Possui R$ XXX
     * disponíveis no curto e longo prazos para quitar cada real de dívida de
     * curto e longo prazos (PINHEIRO. 2009, p. 412).
     *
     * @param ano
     * @return
     */
    public double getLiquidezGeralUltimoTrimestre() {
        BalancoPatrimonial balancoPatrimonial = report.getBalancoUltimoTrimestre();
        return getLiquidezGeral(balancoPatrimonial);
    }

    public double getMediaLiquidezCorrentePorAno() {
        List<BalancoPatrimonial> balancoListPorAno = report.getBalancoListPorAno(report.getAno());
        return getMediaLiquidezCorrente(balancoListPorAno);
    }

    public double getMediaLiquidezCorrenteUltimos12Meses() {
        List<BalancoPatrimonial> listaBalancoPorAno = report.getBalancoListUltimos12Meses();
        return getMediaLiquidezCorrente(listaBalancoPorAno);
    }

    public double getMediaLiquidezSecaPorAno() {
        List<BalancoPatrimonial> listaBalancoPorAno = report.getBalancoListPorAno(report.getAno());
        return getMediaLiquidezSeca(listaBalancoPorAno);
    }

    private double getMediaLiquidezSeca(final List<BalancoPatrimonial> listaBalancoPorAno) {

        int size = listaBalancoPorAno.size();
        double liquidezSeca = 0;
        for (int i = 0; i < size; i++) {
            liquidezSeca += getLiquidezSeca(listaBalancoPorAno.get(i));
        }

        return liquidezSeca / size;
    }

    private double getLiquidezCorrente(final BalancoPatrimonial balancoByDate) {
        double liquidezCorrente = -1;

        if (balancoByDate != null) {
            double ativoCirculante = balancoByDate.getAtivoCirculante();
            double passivoCirculante = balancoByDate.getPassivoCirculante();
            liquidezCorrente = ativoCirculante / passivoCirculante;

        }
        return liquidezCorrente;
    }

    private double getMediaLiquidezCorrente(final List<BalancoPatrimonial> listaBalancoPorAno) {
        double liquidezCorrente = -1;
        int size = listaBalancoPorAno.size();

        for (int i = 0; i < size; i++) {
            liquidezCorrente += getLiquidezCorrente(listaBalancoPorAno.get(i));
        }

        return liquidezCorrente / size;

    }

    private double getLiquidezGeral(final BalancoPatrimonial balancoPatrimonial) {
        double liquidezGeral = -1;

        double ativosCirculantes = 0;
        double passivosCirculantes = 0;
        double ativoRealizavelALongoPrazo = 0;
        double passivoNaoCirculante = 0;

        ativosCirculantes += balancoPatrimonial.getAtivoCirculante();
        ativoRealizavelALongoPrazo += balancoPatrimonial.getAtivoRealizavelALongoPrazo();
        passivosCirculantes += balancoPatrimonial.getPassivoCirculante();
        passivoNaoCirculante += balancoPatrimonial.getPassivoNaoCirculante();

        liquidezGeral = (ativosCirculantes + ativoRealizavelALongoPrazo)
                / (passivosCirculantes + passivoNaoCirculante);

        return liquidezGeral;
    }

    private double getLiquidezSeca(final BalancoPatrimonial balancoPatrimonial) {
        double ativosCirculantes = 0;
        double passivosCirculantes = 0;
        double estoques = 0;

        ativosCirculantes = balancoPatrimonial.getAtivoCirculante();
        passivosCirculantes = balancoPatrimonial.getPassivoCirculante();
        estoques = balancoPatrimonial.getEstoques();

        double liquidezSeca = (ativosCirculantes - estoques) / passivosCirculantes;

        return liquidezSeca;
    }

}
