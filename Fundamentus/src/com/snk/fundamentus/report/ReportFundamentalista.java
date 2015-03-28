package com.snk.fundamentus.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.snk.fundamentus.enums.Trimestre;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;


public class ReportFundamentalista {

    private static Logger logger = Logger.getLogger(ReportFundamentalista.class);

    private final Empresa empresa;

    public ReportFundamentalista(final Empresa empresa) {
        this.empresa = empresa;

    }

    public boolean teveLucroUltimos32Semestres() {

        List<DemonstrativoResultado> demonstrativoList = empresa.getDemonstrativoList();
        for (DemonstrativoResultado demonstrativoResultado : demonstrativoList) {
            if (demonstrativoResultado.getLucro_PrejuizoPeriodo() <= 0)
                return false;
        }

        return true;
    }

    public double getLiquidezCorrentePorAno(int ano) {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(ano);
        double liquidezCorrente = -1;

        if (listaBalancoPorAno != null) {
            double ativosCirculantes = 0;
            double passivosCirculantes = 0;

            for (BalancoPatrimonial balancoPatrimonial : listaBalancoPorAno) {
                ativosCirculantes += balancoPatrimonial.getAtivoCirculante();
                passivosCirculantes += balancoPatrimonial.getPassivoCirculante();

            }
            liquidezCorrente = ativosCirculantes / passivosCirculantes;
        }

        return liquidezCorrente;
    }

    public List<BalancoPatrimonial> getListaBalancoPorAno(int ano) {

        List<BalancoPatrimonial> lista = new ArrayList<BalancoPatrimonial>();

        try {

            Trimestre[] values = Trimestre.values();

            for (Trimestre trimestre : values) {
                String str = trimestre.getValue() + "/" + ano;

                Date formatString = ReportUtil.formatString(str);
                Calendar calendarFromDate = ReportUtil.getCalendarFromDate(formatString);

                BalancoPatrimonial balancoByDate = getBalancoByDate(calendarFromDate);
                if (null != balancoByDate) {
                    lista.add(balancoByDate);
                }
            }
        }
        catch (Exception e) {
            logger.error(e);
        }

        return lista;

    }

    public int getNumeroBalancosApuradorPorAno(int ano) {
        List<BalancoPatrimonial> balancoList = empresa.getBalancoList();
        int count = 0;

        for (BalancoPatrimonial balancoPatrimonial : balancoList) {
            Date dataDoBalanco = balancoPatrimonial.getDataDoBalanco();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataDoBalanco);

            int anoBalanco = calendar.get(Calendar.YEAR);

            if (ano == anoBalanco) {
                count++;
            }

        }

        return count;

    }

    public double getLiquidezCorrentePorTrimestre(Trimestre trimestre, int ano) {

        String data = trimestre.getValue() + "/" + ano;
        double liquidezCorrente = -1;

        Date formatString;
        try {
            formatString = ReportUtil.formatString(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatString);

            BalancoPatrimonial balancoByDate = getBalancoByDate(calendar);

            liquidezCorrente = getLiquidezCorrente(balancoByDate);
        }
        catch (ParseException e) {
            logger.error("Can't parse date while calling getLiquidezCorrentePorTrimestre() method.", e);
        }

        return liquidezCorrente;
    }

    private double getLiquidezCorrente(BalancoPatrimonial balancoByDate) {
        double liquidezCorrente = -1;

        if (balancoByDate != null) {
            double ativoCirculante = balancoByDate.getAtivoCirculante();
            double passivoCirculante = balancoByDate.getPassivoCirculante();

            liquidezCorrente = ativoCirculante / passivoCirculante;

        }
        return liquidezCorrente;
    }

    /**
     * Para cada real de dívidas vencíveis a curto prazo, a companhia possui XX
     * ativos de curto prazo.
     * 
     * @return
     */
    public double getLiquidezCorrenteUltimoTrimestre() {
        double liquidezCorrente = -1;
        BalancoPatrimonial balancoPatrimonialUltimoTrimestre = getBalancoPatrimonialUltimoTrimestre();

        if (null != balancoPatrimonialUltimoTrimestre) {
            liquidezCorrente = getLiquidezCorrente(balancoPatrimonialUltimoTrimestre);
        }

        return liquidezCorrente;
    }

    private BalancoPatrimonial getBalancoPatrimonialUltimoTrimestre() {

        Calendar ultimoTrimestre = ReportUtil.getUltimoTrimestre(Calendar.getInstance());
        return getBalancoByDate(ultimoTrimestre);
    }

    private BalancoPatrimonial getBalancoByDate(Calendar ultimoTrimestre) {
        String trimestre = ReportUtil.formatDate(ultimoTrimestre.getTime());

        BalancoPatrimonial demonstrativoResultado = null;

        List<BalancoPatrimonial> demonstrativoList = empresa.getBalancoList();

        for (BalancoPatrimonial demonstrativo : demonstrativoList) {

            String format = ReportUtil.formatDate(demonstrativo.getDataDoBalanco());

            if (format.equals(trimestre)) {
                demonstrativoResultado = demonstrativo;
                break;
            }
        }
        return demonstrativoResultado;
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
