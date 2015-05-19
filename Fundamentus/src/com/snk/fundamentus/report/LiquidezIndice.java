package com.snk.fundamentus.report;

import java.util.List;

import com.snk.fundamentus.models.BalancoPatrimonial;

/**
 * O grau de liquidez de um ativo depende da rapidez com que ele pode ser
 * transformado em dinheiro sem perder valor. A gest�o da liquidez da
 * empresa busca o equil�brio entre os prazos das d�vidas com os prazos dos
 * ativos, a fim de se evitar sua insolv�ncia. Portanto, essa an�lise
 * procura avaliar as condi��es que a empresa tem para saldar suas
 * exigibilidades.�
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
     * O �ndice de liquidez imediata � obtido atrav�s do resultado da divis�o
     * entre a soma do caixa e dos equivalentes de caixa pelo passivo. est�
     * representado possuindo XX ativos dispon�veis (ou reais em esp�cie) para
     * cobrir cada real de d�vida contra�da a curto prazo (PINHEIRO. 2009, p.
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
     * Para cada real de d�vidas venc�veis a curto prazo, a companhia possui XX
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
     * Para se obter o �ndice de liquidez seca � necess�rio subtrair os estoques
     * do ativo circulante, e deste resultado divide-se pelo passivo circulante.
     * Conforme Pinheiro (2009, p. 413), este �ndice representa a porcentagem
     * das d�vidas de curto prazo em condi��es de serem pagas mediante a
     * utiliza��o de itens monet�rios de maior liquidez no ativo circulante e
     * sup�e que os compromissos a curto prazo ser�o atendidos pela realiza��o
     * exclusivamente dos direitos realiz�veis a curto prazo e, como pode ser
     * observado na tabela 3, este resultado para a empresa Tegma � bastante
     * satisfat�rio.
     *
     * @param ano
     * @return
     */
    public double getLiquidezSecaUltimoTrimestre() {
        BalancoPatrimonial balancoUltimoTrimestre = report.getBalancoUltimoTrimestre();
        return getLiquidezSeca(balancoUltimoTrimestre);
    }

    /**
     * �ndice de liquidez geral retrata quantos reais a empresa disp�e de curto
     * e longo prazos para cobrir cada real de d�vida contra�da. Possui R$ XXX
     * dispon�veis no curto e longo prazos para quitar cada real de d�vida de
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
