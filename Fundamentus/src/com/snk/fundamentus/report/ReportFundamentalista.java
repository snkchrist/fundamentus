package com.snk.fundamentus.report;

import com.snk.fundamentus.models.Empresa;


public class ReportFundamentalista {

    private final Empresa empresa;

    public ReportFundamentalista(final Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean teveLucroUltimos32Semestres() {

        return true;
    }

    /*
     * LUCRO POR AÇÃO
     */
    public double getLPA() {
        return ((double) empresa.getDemonstracao12meses().getLucroLiquido() / empresa.getNumeroDeAcoes());
    }

    /*
     * PRECO POR LUCRO P ACAO
     */
    public double getPL() {
        double pl = empresa.getCotacao() / getLPA();
        return pl;
    }

    /**
     * VALOR POR ACAO
     *
     * @return
     */
    public double getVPA() {
        double vpa = (double) empresa.getBalanco().getPatrimonioLiquido()
                / empresa.getNumeroDeAcoes();
        return vpa;
    }

    /*
     * PRECO POR VALOR POR ACAO
     */
    public double getPVPA() {
        return empresa.getCotacao() / getVPA();
    }

}
