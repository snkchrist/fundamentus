package com.snk.fundamentus.report;

import java.text.ParseException;
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
    public double getLiquidezImediata(int ano) {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(ano);
        double liquidezImediata = -1;

        double caixaEEquivalentesDeCaixa = 0;
        double passivoCirculante = 0;

        for (BalancoPatrimonial balancoPatrimonial : listaBalancoPorAno) {
            caixaEEquivalentesDeCaixa += balancoPatrimonial.getCaixaEEquivalentesDeCaixa();
            passivoCirculante += balancoPatrimonial.getPassivoCirculante();
        }

        liquidezImediata = caixaEEquivalentesDeCaixa / passivoCirculante;

        return liquidezImediata;
    }

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
    public double getLiquidezSeca(int ano) {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(ano);
        double liquidezSeca = -1;

        if (listaBalancoPorAno != null) {
            double ativosCirculantes = 0;
            double passivosCirculantes = 0;
            double estoques = 0;

            for (BalancoPatrimonial balancoPatrimonial : listaBalancoPorAno) {
                ativosCirculantes += balancoPatrimonial.getAtivoCirculante();
                passivosCirculantes += balancoPatrimonial.getPassivoCirculante();
                estoques += balancoPatrimonial.getEstoques();

            }
            liquidezSeca = (ativosCirculantes - estoques) / passivosCirculantes;
        }

        return liquidezSeca;

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
    public double getLiquidezGeral(int ano) {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(ano);
        double liquidezGeral = -1;

        if (listaBalancoPorAno != null) {
            double ativosCirculantes = 0;
            double passivosCirculantes = 0;
            double ativoRealizavelALongoPrazo = 0;
            double passivoNaoCirculante = 0;

            for (BalancoPatrimonial balancoPatrimonial : listaBalancoPorAno) {
                ativosCirculantes += balancoPatrimonial.getAtivoCirculante();
                ativoRealizavelALongoPrazo += balancoPatrimonial.getAtivoRealizavelALongoPrazo();
                passivosCirculantes += balancoPatrimonial.getPassivoCirculante();
                passivoNaoCirculante += balancoPatrimonial.getPassivoNaoCirculante();

            }
            liquidezGeral = (ativosCirculantes + ativoRealizavelALongoPrazo)
                / (passivosCirculantes + passivoNaoCirculante);
        }

        return liquidezGeral;

    }

    /**
     * Indice de rotatividade, que representa quantas vezes o ativo total da
     * empresa girou em determinado período em função das vendas realizadas, que
     * de acordo com Pinheiro (2009, p. 424), este índice reflete o grau de
     * utilização dos ativos na geração das vendas.
     * 
     * @param ano
     * @return
     */
    public double getIndiceRotatividade(int ano) {
        List<DemonstrativoResultado> listaDemonstrativoPorAno = getListaDemonstrativoResultadoPorAno(ano);
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(ano);

        double indiceRotatividade = -1;

        if (listaDemonstrativoPorAno != null) {
            double receitaLiquidaVendasServicos = 0;
            double ativoTotal = 0;

            for (DemonstrativoResultado demonstrativoResultado : listaDemonstrativoPorAno) {
                receitaLiquidaVendasServicos += demonstrativoResultado.getReceitaLiquidaVendasServicos();
            }

            for (BalancoPatrimonial balancoPatrimonial : listaBalancoPorAno) {
                ativoTotal += balancoPatrimonial.getAtivoTotal();
            }

            indiceRotatividade = ativoTotal / receitaLiquidaVendasServicos;

        }

        return indiceRotatividade;
    }

    /**
     * O índice de lucratividade reflete a eficiência global da empresa na
     * geração do lucro resultante de todas as fases do negócio da empresa
     * 
     * @param ano
     * @return
     */
    public double getIndiceLucratividade(int ano) {

        List<DemonstrativoResultado> listaDemonstrativoPorAno = getListaDemonstrativoResultadoPorAno(ano);
        double indiceLucratividade = -1;

        if (listaDemonstrativoPorAno != null) {
            double lucro_PrejuizoPeriodo = 0;
            double receitaLiquidaVendasServicos = 0;

            for (DemonstrativoResultado balancoPatrimonial : listaDemonstrativoPorAno) {
                lucro_PrejuizoPeriodo += balancoPatrimonial.getLucro_PrejuizoPeriodo();
                receitaLiquidaVendasServicos += balancoPatrimonial.getReceitaLiquidaVendasServicos();
            }

            indiceLucratividade = lucro_PrejuizoPeriodo / receitaLiquidaVendasServicos;
        }

        return indiceLucratividade;

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

    public List<DemonstrativoResultado> getListaDemonstrativoResultadoPorAno(int ano) {

        List<DemonstrativoResultado> lista = new ArrayList<DemonstrativoResultado>();

        try {

            Trimestre[] values = Trimestre.values();

            for (Trimestre trimestre : values) {
                String str = trimestre.getValue() + "/" + ano;

                Date formatString = ReportUtil.formatString(str);
                Calendar calendarFromDate = ReportUtil.getCalendarFromDate(formatString);

                DemonstrativoResultado balancoByDate = getDemonstrativoResultadoByDate(calendarFromDate);
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

        BalancoPatrimonial balancoPatrimonial = null;

        List<BalancoPatrimonial> demonstrativoList = empresa.getBalancoList();

        for (BalancoPatrimonial demonstrativo : demonstrativoList) {

            String format = ReportUtil.formatDate(demonstrativo.getDataDoBalanco());

            if (format.equals(trimestre)) {
                balancoPatrimonial = demonstrativo;
                break;
            }
        }
        return balancoPatrimonial;
    }

    private DemonstrativoResultado getDemonstrativoResultadoByDate(
        Calendar ultimoTrimestre) {
        String trimestre = ReportUtil.formatDate(ultimoTrimestre.getTime());

        DemonstrativoResultado demonstrativoResultado = null;

        List<DemonstrativoResultado> demonstrativoList = empresa.getDemonstrativoList();

        for (DemonstrativoResultado demonstrativo : demonstrativoList) {

            String format = ReportUtil.formatDate(demonstrativo.getDataDemonstrativo());

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
