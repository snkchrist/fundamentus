package com.snk.fundamentus.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.snk.fundamentus.enums.Trimestre;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.Cotacao;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.models.Oscilacoes;

public class ReportFundamentalista {

    private static final String BANCOS = "Bancos";
    private static Logger logger = Logger.getLogger(ReportFundamentalista.class);
    private final int ano;

    private final Empresa empresa;
    private final LiquidezIndice liquidez;

    public ReportFundamentalista(final Empresa empresa) {

        this(empresa, Calendar.getInstance().get(Calendar.YEAR));
    }

    public ReportFundamentalista(final Empresa empresa, final int ano) {
        liquidez = new LiquidezIndice(this);
        this.empresa = empresa;
        this.ano = ano;
    }

    public int getAno() {
        return ano;
    }

    /*
     * LUCRO POR AÇÃO
     */
    public double getLPA() {
        return ((double) empresa.getDemonstracao12meses().getLucroLiquido() / empresa.getNumeroDeAcoes());
    }

    /**
     * indica que para preço e lucro constantes, serão necessários X anos para
     * recuperar o capital
     *
     * @return
     */
    public double getPL() {
        double pl = empresa.getCotacao() / getLPA();
        return pl;
    }

    /**
     * Indica a taxa de lucratividade anual da ação
     *
     * @return
     */
    public double getLucratividadePL() {
        return (1 / getPL()) * 100;
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

    public boolean teveLucroUltimos32Semestres() {

        List<DemonstrativoResultado> demonstrativoList = empresa.getDemonstrativoList();
        for (DemonstrativoResultado demonstrativoResultado : demonstrativoList) {
            if (demonstrativoResultado.getLucro_PrejuizoPeriodo() < 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isLucroAcoesUltimos5Anos(final double acimaDe) {
        double oscilacaoAcumuladaUltimos5Anos = getRentabilidadeAcumuladaUltimos5Anos();

        if (oscilacaoAcumuladaUltimos5Anos > acimaDe) {
            return true;
        }

        return false;
    }

    public double getRentabilidadeAcumuladaUltimos5Anos() {
        Oscilacoes oscilacoes = empresa.getOscilacoes();

        double acumulado = oscilacoes.getAno2010() +
                oscilacoes.getAno2011() +
                oscilacoes.getAno2012() +
                oscilacoes.getAno2013() +
                oscilacoes.getAno2014() +
                oscilacoes.getAno2015();

        return acumulado;
    }

    public boolean teveAumentoRentabilidadeUltimos5Anos() {
        Oscilacoes oscilacoes = empresa.getOscilacoes();

        if (oscilacoes.getAno2010() > 0 &&
                oscilacoes.getAno2011() > 0 &&
                oscilacoes.getAno2012() > 0 &&
                oscilacoes.getAno2013() > 0 &&
                oscilacoes.getAno2014() > 0 &&
                oscilacoes.getAno2015() > 0) {
            return true;
        }

        return false;
    }

    public boolean pagouDividendosUltimos5Anos() {

        int ano = getAno();
        List<BalancoPatrimonial> balancoList = new ArrayList<BalancoPatrimonial>();

        do {
            balancoList.addAll(getBalancoListPorAno(ano));
            ano--;
        }
        while (ano >= getAno() - 5);

        for (BalancoPatrimonial balancoPatrimonial : balancoList) {
            if (balancoPatrimonial.getDividendosEJCPAPagar() <= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * O Lucro antes de Juros e Impostos (LAJIR ou EBIT)
     * Conhecido como EBIT (earnings
     * before interests and taxes), é uma variável do EBITDA.
     * É o EBITDA (LAJIDA) menos
     * a depreciação e a amortização de intangíveis.
     * É um tipo de “Lucro antes de Impostos”, porém, não é um indicador
     * contábil apresentado normalmente nas
     * demonstrações de resultados.
     *
     * @return
     */
    public double getEbitUltimoTrimestre() {
        return getEbitByTrimestre(empresa.getDemonstrativoList().get(0));
    }

    public double getEbitByTrimestre(final DemonstrativoResultado demonstrativo) {
        DemonstrativoResultado resultadoUltimoTrimestre = demonstrativo;

        double ebit = resultadoUltimoTrimestre.getResultadoBruto() +
                resultadoUltimoTrimestre.getDespesasComVendas() +
                resultadoUltimoTrimestre.getDespesasGeraisAdministrativas();

        return ebit;

    }

    public double getEbitUltimos12Meses() {
        return getEbit(getDemonstrativoListUltimos12Meses());
    }

    public double getEbitUltimos12MesesAnteriorAoDemonstrativo(final DemonstrativoResultado demonstrativo) {
        List<DemonstrativoResultado> demonstrativoList = getDemonstrativoListUltimos12MesesAnteriorAoTrimestre(demonstrativo);
        return getEbit(demonstrativoList);
    }

    /**
     * O Lucro antes de Juros e Impostos (LAJIR ou EBIT) 12 meses
     */
    public double getEbitByAno(final int ano) {
        double ebit = Double.NaN;
        try {
            ebit = getEbit(getDemonstrativoListPorAno(ano));

        }
        catch (NullPointerException e) {
            logger.info("Não foi possivel calcular o ebit da empresa [" + empresa.getSigla() + "] para o ano de [" + ano + "]");
        }

        return ebit;
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
    public double getIndiceRotatividade() {
        List<DemonstrativoResultado> listaDemonstrativoPorAno = getDemonstrativoListUltimos12Meses();
        List<BalancoPatrimonial> listaBalancoPorAno = getBalancoListPorAno(getAno());

        double indiceRotatividade = -1;

        if (listaDemonstrativoPorAno != null) {
            double receitaLiquidaVendasServicos = 0;
            double receitaLiquidaBank = 0;
            double ativoTotal = 0;

            for (DemonstrativoResultado demonstrativoResultado : listaDemonstrativoPorAno) {
                receitaLiquidaVendasServicos += demonstrativoResultado.getReceitaLiquidaVendasServicos();
                receitaLiquidaBank += demonstrativoResultado.getReceitasIntermediaçaoFinanceira();

            }

            for (BalancoPatrimonial balancoPatrimonial : listaBalancoPorAno) {
                ativoTotal += balancoPatrimonial.getAtivoTotal();
            }

            if (receitaLiquidaVendasServicos > 0) {
                indiceRotatividade = ativoTotal / receitaLiquidaVendasServicos;
            }
            else {
                indiceRotatividade = ativoTotal / receitaLiquidaBank;
            }

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
    public double getIndiceLucratividade() {

        List<DemonstrativoResultado> listaDemonstrativoPorAno = getDemonstrativoListUltimos12Meses();
        double indiceLucratividade = -1;

        if (listaDemonstrativoPorAno != null) {
            double lucro_PrejuizoPeriodo = 0;
            double receitaLiquida = 0;

            for (DemonstrativoResultado balancoPatrimonial : listaDemonstrativoPorAno) {
                lucro_PrejuizoPeriodo += balancoPatrimonial.getLucro_PrejuizoPeriodo();

                if (balancoPatrimonial.getReceitaLiquidaVendasServicos() > 0) {
                    receitaLiquida += balancoPatrimonial.getReceitaLiquidaVendasServicos();
                }
                else {
                    receitaLiquida += balancoPatrimonial.getReceitasIntermediaçaoFinanceira();
                }
            }

            indiceLucratividade = lucro_PrejuizoPeriodo / receitaLiquida;
        }

        return indiceLucratividade;
    }

    public double getDividaBrutaUltimoTrimestre() {
        double dividaBruta = -1;
        BalancoPatrimonial balanco = getBalancoUltimoTrimestre();

        if (null != balanco) {
            dividaBruta = balanco.getEmprestimosEFinanciamentos();
            logger.debug("DividaBruta: " + dividaBruta);
        }

        return dividaBruta;
    }

    public double getDividaLiquidaUltimoTrimestre() {
        double dividaBruta = getDividaBrutaUltimoTrimestre();
        double disponivilidades = getDisponibilidadesUltimoTrimestre();
        double dividaLiquida = -1;
        if (dividaBruta != -1 && disponivilidades != -1) {
            dividaLiquida = dividaBruta - disponivilidades;
            logger.debug("DividaLiquida: " + dividaLiquida);
        }
        return dividaLiquida;
    }

    public double getDisponibilidadesUltimoTrimestre() {
        double disponivilidades = -1;
        BalancoPatrimonial balanco = getBalancoUltimoTrimestre();

        if (null != balanco) {
            double caixaEEquivalentesDeCaixa = balanco.getCaixaEEquivalentesDeCaixa();
            double aplicacoesFinanceiras = balanco.getAplicacoesFinanceiras();
            disponivilidades = (caixaEEquivalentesDeCaixa + aplicacoesFinanceiras);
            logger.debug("Disponivilidades: " + disponivilidades);
        }

        return disponivilidades;
    }

    public double getRelacaoDividaLiquidaPatrimonioLiquido() {
        BalancoPatrimonial balanco = getBalancoUltimoTrimestre();
        double relacao = -1;
        if (null != balanco) {
            double dividaLiquida = getDividaLiquidaUltimoTrimestre();
            double patrimonioLiquido = balanco.getPatrimonioLiquido();
            relacao = dividaLiquida / patrimonioLiquido;
            logger.debug("Relação divida liquida sobre patrimonio liquido: " + relacao);
        }
        return relacao;
    }

    public List<BalancoPatrimonial> getBalancoListPorAno(final int ano) {

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

    public List<DemonstrativoResultado> getDemonstrativoListUltimos12Meses() {

        List<DemonstrativoResultado> lista = new ArrayList<DemonstrativoResultado>();

        try {
            if (empresa.getDemonstrativoList().size() >= 4) {
                for (int i = 0; i < 4; i++) {
                    lista.add(empresa.getDemonstrativoList().get(i));
                }
            }
        }
        catch (Exception e) {
            logger.error(e);
        }

        return lista;
    }

    public List<DemonstrativoResultado> getDemonstrativoListUltimos12MesesAnteriorAoTrimestre(
            final DemonstrativoResultado demonstrativo) {

        Date dataDemonstrativo = demonstrativo.getDataDemonstrativo();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataDemonstrativo);
        String getTrimestreStr = ReportUtil.getTrimestreStr(calendar);

        List<DemonstrativoResultado> demonstrativoLst = new ArrayList<DemonstrativoResultado>();

        for (int i = 0; i < 4; i++) {
            DemonstrativoResultado demonstrativoPorTrimestreStr = getDemonstrativoPorTrimestreStr(getTrimestreStr);
            demonstrativoLst.add(demonstrativoPorTrimestreStr);
            getTrimestreStr = getTrimestreAnterior(getTrimestreStr);
        }

        return demonstrativoLst;

    }

    public String getTrimestreAnterior(final String trimestreStr) {
        String trimestre = trimestreStr.substring(0, 1);
        String ano = trimestreStr.substring(2, 4);

        int intTrimestre = Integer.parseInt(trimestre);
        int intAno = Integer.parseInt(ano);

        if (intTrimestre == 1) {
            intTrimestre = 4;
            --intAno;
        }
        else {
            --intTrimestre;
        }

        String str = intTrimestre + "T" + intAno;

        return str;
    }

    public DemonstrativoResultado getDemonstrativoPorTrimestreStr(final String trimestreStr) {
        List<DemonstrativoResultado> demonstrativoList = empresa.getDemonstrativoList();
        DemonstrativoResultado demonstrativo = null;
        for (DemonstrativoResultado demonstrativoResultado : demonstrativoList) {
            Date dataDemonstrativo = demonstrativoResultado.getDataDemonstrativo();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataDemonstrativo);
            String getTrimestreStr = ReportUtil.getTrimestreStr(calendar);

            if (getTrimestreStr.equalsIgnoreCase(trimestreStr)) {
                demonstrativo = demonstrativoResultado;
                break;
            }

        }
        return demonstrativo;
    }

    public List<DemonstrativoResultado> getDemonstrativoListPorAno(final int ano) {

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

        if (lista.size() == 0) {
            throw new NullPointerException("Não há demonstrativos para o ano especificado [" + ano + "]");
        }

        return lista;
    }

    public int getNumeroBalancosApuradorPorAno(final int ano) {
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

    public double getVendasUltimos12Meses() {
        List<DemonstrativoResultado> demonstrativoList = getDemonstrativoListUltimos12Meses();
        return getVendas(demonstrativoList);
    }

    public double getVendasPorAno() {
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = getDemonstrativoListPorAno(getAno());
        return getVendas(listaDemonstrativoResultadoPorAno);
    }

    public double getLucroLiquido(final List<DemonstrativoResultado> lst) {
        double lucroAno = 0;

        List<DemonstrativoResultado> demonstrativoList = lst;

        if (null != demonstrativoList) {
            for (DemonstrativoResultado demonstrativoResultado : demonstrativoList) {
                lucroAno += demonstrativoResultado.getLucro_PrejuizoPeriodo();
            }
        }

        return lucroAno;
    }

    public double getLucroLiquidoUltimos12Meses() {
        return getLucroLiquido(getDemonstrativoListUltimos12Meses());
    }

    public double getLucroLiquidoPorAno(final int ano) {
        double lucroLiquido = Double.NaN;

        try {
            List<DemonstrativoResultado> demonstrativoLst = getDemonstrativoListPorAno(ano);
            lucroLiquido = getLucroLiquido(demonstrativoLst);
        }
        catch (NullPointerException e) {
        }

        return lucroLiquido;
    }

    public double getCaixaAtual() {
        BalancoPatrimonial balancoUltimoTrimestre = getBalancoUltimoTrimestre();
        return balancoUltimoTrimestre.getCaixaEEquivalentesDeCaixa() + balancoUltimoTrimestre.getAplicacoesFinanceiras();
    }

    public double getDividendosDistribuidosUltimos12Meses() {
        List<BalancoPatrimonial> listaBalancoUltimos12Meses = getBalancoListUltimos12Meses();
        double dividendosEJCPAPagar = 0;

        for (BalancoPatrimonial balancoPatrimonial : listaBalancoUltimos12Meses) {
            dividendosEJCPAPagar += balancoPatrimonial.getDividendosEJCPAPagar();
        }

        return dividendosEJCPAPagar;
    }

    public double getLucroLiquidoAnual() {
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = getDemonstrativoListPorAno(getAno());
        return getLucroLiquido(listaDemonstrativoResultadoPorAno);
    }

    /**
     * Desenvolvido por James Tobin, em 1969, o 'q' de Tobin é calculado
     * dividindo-se o valor da firma (valor de mercado mais as dívidas) pelo
     * valor de reposição dos seus ativos (VRA). Este representa o desembolso
     * teórico para se adquirir novamente os bens operacionais da companhia. É
     * obtido pelo seguinte calculo (valor de mercado + passivo circulante +
     * divida de longo prazo - ativo circulante) / valor total dos ativos
     *
     * @return
     */
    public double getQdeTobinUltimoTrimestre() {
        BalancoPatrimonial balanco = getBalancoUltimoTrimestre();
        return getQdeTobinByTrimestre(balanco);
    }

    public double getQdeTobinByTrimestre(final BalancoPatrimonial balancoPatrimonial) {
        BalancoPatrimonial balanco = balancoPatrimonial;
        double qTobin = 0;

        if (null != balanco) {
            double soma = empresa.getValorMercado() + balanco.getPassivoCirculante()
                    + balanco.getEmprestimosEFinanciamentos();
            qTobin = (soma - balanco.getAtivoCirculante()) / balanco.getAtivoTotal();
        }

        return qTobin;
    }

    public double getMediaLPAUltimos5Anos() {
        int ano = getAno();

        double soma = 0;

        for (int i = 1; i <= 5; i++) {
            soma += getLucroLiquidoPorAno(ano) / empresa.getNumeroDeAcoes();
            ano--;
        }

        return soma / 5;
    }

    public double getMedianaLPAUltimos5Anos() {
        int ano = getAno();

        List<Double> lpaMedian = new ArrayList<Double>();

        for (int i = 1; i <= 5; i++) {
            lpaMedian.add(getLucroLiquidoPorAno(ano) / empresa.getNumeroDeAcoes());
            ano--;
        }

        Double[] median = new Double[lpaMedian.size()];
        lpaMedian.toArray(median);

        return getMedianaAritimetica(median);
    }

    /**
     * Este indice tem um erro por causa do valor de mercado não ser referente
     * ao trimestre realizado.
     * TODO
     *
     * @return
     */
    public double getMediaQdeTobinPorAno() {
        List<BalancoPatrimonial> listaBalancoPorAno = getBalancoListPorAno(getAno());
        int size = listaBalancoPorAno.size();
        double value = 0;

        for (int i = 0; i < size; i++) {
            value += getQdeTobinByTrimestre(listaBalancoPorAno.get(i));
        }

        double mediaQdeTobinAno = value / size;

        return mediaQdeTobinAno;
    }

    /**
     * Retorna a média do roic para o ano especificado
     *
     * @return
     */
    public double getMediaROICByAno() {
        List<BalancoPatrimonial> balancoListPorAno = getBalancoListPorAno(getAno());
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = getDemonstrativoListPorAno(getAno());

        return getMediaROIC(balancoListPorAno, listaDemonstrativoResultadoPorAno);
    }

    /**
     * Retorna a média do roic para o ano especificado
     *
     * @return
     */
    public double getMediaROIC(final List<BalancoPatrimonial> listaBalanco, final List<DemonstrativoResultado> listaDemonstrativo) {
        double roicByTrimestre = 0;

        int size = listaBalanco.size();

        for (int i = 0; i < size; i++) {
            BalancoPatrimonial balancoPatrimonial = listaBalanco.get(i);
            DemonstrativoResultado result = listaDemonstrativo.get(i);
            roicByTrimestre += getROICByTrimestre(balancoPatrimonial, result);
        }

        return (roicByTrimestre / size);
    }

    /**
     * Retorna o Roic do trimestre informado
     *
     * @param balanco
     * @param demonstrativo
     * @return
     */
    public double getROICByTrimestre(final BalancoPatrimonial balanco, final DemonstrativoResultado demonstrativo) {
        BalancoPatrimonial balancoPatrimonial = balanco;

        double roic = getROIC(
                getEbitUltimos12MesesAnteriorAoDemonstrativo(demonstrativo),
                balancoPatrimonial.getAtivoTotal() -
                balancoPatrimonial.getCaixaEEquivalentesDeCaixa() -
                balancoPatrimonial.getFornecedores() -
                balancoPatrimonial.getAplicacoesFinanceiras() -
                balancoPatrimonial.getAplicacoesFinanceirasAvaliadasAoCustoAmortizado() -
                balancoPatrimonial.getAplicacoesFinanceirasAvaliadasAValorJusto());

        return roic * 100;
    }

    private double getROIC(final double ebit, final double ativosCaixaFornecedoresOutros) {
        return ebit / ativosCaixaFornecedoresOutros;
    }

    public double getROICUltimoTrimestre() {
        return getROICByTrimestre(getBalancoUltimoTrimestre(), getDemonstrativoUltimoTrimestre());
    }

    public double getROEUltimoTrimestre() {
        BalancoPatrimonial balancoPatrimonial = getBalancoUltimoTrimestre();

        double roe = getLucroLiquidoUltimos12Meses() /
                balancoPatrimonial.getPatrimonioLiquido();

        return roe * 100;
    }

    public double getAumentoPercentualLucroLiquidoAnoAAno() {
        int ano = getAno() - 1;

        double lucroAnoAnterior = getLucroLiquidoPorAno(ano - 1);
        double lucroAnoSeguinte = getLucroLiquidoPorAno(ano);
        double lucroPercentual = 0;

        lucroPercentual = getAumentoPercentual(lucroAnoAnterior, lucroAnoSeguinte);

        return lucroPercentual;
    }

    public double getAumentoPercentualEbitAnoAAno() {
        int ano = getAno() - 1;

        double ebitSeguinte = getEbitByAno(ano);
        double ebitAnterior = getEbitByAno(ano - 1);

        double aumentoPercentual;

        if (Double.isNaN(ebitSeguinte) || Double.isNaN(ebitAnterior)) {
            aumentoPercentual = Double.NaN;
        }
        else {
            aumentoPercentual = getAumentoPercentual(ebitAnterior, ebitSeguinte);
        }

        return aumentoPercentual;

    }

    public boolean isVPAMaiorQueCotacao() {
        return getVPA() > empresa.getCotacao();
    }

    public boolean isGrahansMethodPorAno() {
        boolean vendasSubstanciais = getVendasPorAno() > 250000000;
        boolean liquidezCorrente = liquidez.getMediaLiquidezCorrentePorAno() >= 1;
        boolean relacaoDividaPatrimonio = getRelacaoDividaLiquidaPatrimonioLiquido() <= 0.5;
        boolean teveLucroUltimos32Semestres = teveLucroUltimos32Semestres();
        boolean lucroMedio3AnosAcima4PorCento = isLucroMedio3AnosAcima4Porcento();

        if (vendasSubstanciais && liquidezCorrente && relacaoDividaPatrimonio
                && teveLucroUltimos32Semestres && lucroMedio3AnosAcima4PorCento) {
            logger.info("Empresa: [" + empresa.getSigla() + "]");
            logger.info("vendasSubstanciais: [" + getVendasUltimos12Meses() + "]");
            logger.info("liquidezCorrente: [" + liquidez.getLiquidezCorrenteUltimoTrimestre() + "]");
            logger.info("relacaoDividaPatrimonio: [" + (getRelacaoDividaLiquidaPatrimonioLiquido()) + "]");
            logger.info("teveLucroUltimos32Semestres: [" + teveLucroUltimos32Semestres() + "]");
            logger.info("lucroMedio3AnosAcima4PorCento: [" + isLucroMedio3AnosAcima4Porcento() + "]");

            return true;
        }
        return false;
    }

    public boolean isGrahansMethod() {
        boolean vendasSubstanciais = getVendasUltimos12Meses() > 250000000;
        boolean liquidezCorrente = liquidez.getLiquidezCorrenteUltimoTrimestre() >= 1;
        boolean relacaoDividaPatrimonio = getRelacaoDividaLiquidaPatrimonioLiquido() <= 0.5;
        boolean teveLucroUltimos32Semestres = teveLucroUltimos32Semestres();
        boolean lucroMedio3AnosAcima4PorCento = isLucroMedio3AnosAcima4Porcento();

        if (vendasSubstanciais && liquidezCorrente && relacaoDividaPatrimonio
                && teveLucroUltimos32Semestres && lucroMedio3AnosAcima4PorCento) {
            logger.debug("Empresa: [" + empresa.getSigla() + "]");
            logger.debug("vendasSubstanciais: [" + getVendasUltimos12Meses() + "]");
            logger.debug("liquidezCorrente: [" + liquidez.getLiquidezCorrenteUltimoTrimestre() + "]");
            logger.debug("relacaoDividaPatrimonio: [" + (getRelacaoDividaLiquidaPatrimonioLiquido()) + "]");
            logger.debug("teveLucroUltimos32Semestres: [" + pagouDividendosUltimos5Anos() + "]");
            logger.debug("lucroMedio3AnosAcima4PorCento: [" + isLucroMedio3AnosAcima4Porcento() + "]");

            return true;
        }
        return false;
    }

    public boolean isFilipesMethod() {
        boolean isQTobinAnoAcima15 = getQdeTobinUltimoTrimestre() > 1.5;
        boolean roic15 = getROICUltimoTrimestre() > 10;
        boolean vendas = (getVendasUltimos12Meses() > 250000000);
        boolean aumentoEbit = getAumentoPercentualEbitAnoAAno() > 7;
        boolean aumentoLucro = getAumentoPercentualLucroLiquidoAnoAAno() > 7;
        boolean liquidezBoolean = liquidez.getLiquidezCorrenteUltimoTrimestre() > 1;

        if (isQTobinAnoAcima15 && vendas && roic15 && aumentoEbit && aumentoLucro && liquidezBoolean) {
            logger.debug("Empresa [" + empresa.getSigla() + "]");
            return true;
        }
        return false;

    }

    public boolean isFilipesMethodAno() {
        boolean isQTobinAnoAcima15 = getMediaQdeTobinPorAno() > 1.5;
        boolean roic15 = getMediaROICByAno() > 10;
        boolean vendas = (getVendasPorAno() > 250000000);
        boolean aumento = getAumentoPercentualEbitAnoAAno() > 7;
        boolean aumentoLucro = getAumentoPercentualLucroLiquidoAnoAAno() > 7;
        boolean liquidezBoolean = liquidez.getMediaLiquidezCorrentePorAno() > 1;

        if (isQTobinAnoAcima15 &&
                vendas &&
                roic15 &&
                aumento &&
                aumentoLucro &&
                liquidezBoolean) {
            logger.info("Empresa [" + empresa.getSigla() + "]");
            return true;
        }
        return false;

    }

    public String getTrimestreStrByDate(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return ReportUtil.getTrimestreStr(calendar);
    }

    public boolean isLucroMedio3AnosAcima4Porcento() {
        double percentual = 0;
        int anos = 3;
        int ano = getAno() - 1;
        for (int i = anos; i > 0; i--) {
            int anoInicial = ano - i;
            int anoAnterior = ano - (i - 1);

            logger.debug("Contabilizando o lucro do ano de [" + anoAnterior
                    + "] menos o ano de [" + anoInicial + "]");
            double lucroPorAno = getLucroLiquidoPorAno(anoInicial);
            double lucroPorAnoAnterior = getLucroLiquidoPorAno(anoAnterior);
            logger.debug("Ano: [" + anoAnterior + "] - Lucro: [" + lucroPorAnoAnterior
                    + "]");
            logger.debug("Ano: [" + anoInicial + "] - Lucro: [" + lucroPorAno + "]");

            double aumento_reducao = ((lucroPorAnoAnterior - lucroPorAno) / lucroPorAnoAnterior) * 100;
            percentual += aumento_reducao;

            logger.debug("Aumento/redução de [" + percentual + "]");
        }

        return (percentual / anos) >= 4;
    }

    private DemonstrativoResultado getDemonstrativoUltimoTrimestre() {
        return empresa.getDemonstrativoList().get(0);
    }

    protected BalancoPatrimonial getBalancoUltimoTrimestre() {
        return empresa.getBalancoList().get(0);
    }

    protected List<BalancoPatrimonial> getBalancoListUltimos12Meses() {
        List<BalancoPatrimonial> lista = new ArrayList<BalancoPatrimonial>();

        try {
            if (empresa.getBalancoList().size() >= 4) {
                for (int i = 0; i < 4; i++) {
                    lista.add(empresa.getBalancoList().get(i));
                }
            }
        }
        catch (Exception e) {
            logger.error(e);
        }

        return lista;
    }

    private BalancoPatrimonial getBalancoByDate(final Calendar ultimoTrimestre) {
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
            final Calendar ultimoTrimestre) {
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

    private double getAumentoPercentual(final Double valor1,
            final Double valor2) {
        double lucroPercentual;
        if (valor1.equals(valor2)) {
            lucroPercentual = 0;
        }
        else if (valor2 >= valor1) {
            double diff = valor2 - valor1;
            lucroPercentual = (diff / valor1) * 100;
        }
        else {
            double diff = valor1 - valor2;

            lucroPercentual = ((diff / valor2) * 100) - 1;
        }

        return lucroPercentual;
    }

    /**
     * O Lucro antes de Juros e Impostos (LAJIR ou EBIT) 12 meses
     */
    private double getEbit(final List<DemonstrativoResultado> demonstrativoList) {
        double ebit = -1;
        double resultadoBruto = 0;
        double despesasComVendas = 0;
        double despesasGerais = 0;

        double resultadoOperacional = 0;

        for (DemonstrativoResultado demonstrativoResultado : demonstrativoList) {
            resultadoBruto += demonstrativoResultado.getResultadoBruto();
            despesasComVendas += demonstrativoResultado.getDespesasComVendas();
            despesasGerais += demonstrativoResultado.getDespesasGeraisAdministrativas();

            resultadoOperacional += demonstrativoResultado.getResultadoOperacional();
        }

        if (resultadoBruto > 0) {
            ebit = resultadoBruto +
                    despesasComVendas +
                    despesasGerais;
        }
        else {
            ebit = resultadoOperacional;
        }

        return ebit;

    }

    private double getVendas(final List<DemonstrativoResultado> lst) {
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = lst;

        double vendas = 0;
        double vendasBank = 0;

        for (DemonstrativoResultado demonstrativoResultado : listaDemonstrativoResultadoPorAno) {
            vendas += demonstrativoResultado.getReceitaLiquidaVendasServicos();
            vendasBank += demonstrativoResultado.getReceitasIntermediaçaoFinanceira();
        }

        if (vendas > 0) {
            return vendas;
        }
        else {
            return vendasBank;
        }
    }

    private boolean isBanco(final Empresa empresa) {
        return BANCOS.equals(empresa.getSubSetor());
    }

    public double getDesvioPadraoHistorico() {
        List<Cotacao> cotacaoList = empresa.getCotacaoList();
        double desvio = 0;

        if (null != cotacaoList) {

            List<Double> listDouble = new ArrayList<Double>();

            for (Cotacao cotacao : cotacaoList) {
                listDouble.add(cotacao.getFechamento());
            }

            Double[] array = new Double[listDouble.size()];
            listDouble.toArray(array);

            desvio = getDesvioPadrao(array);

        }

        return desvio;
    }

    public double getDesvioPadraoUltimos5Anos() {
        List<Cotacao> cotacaoList = empresa.getCotacaoList();
        List<Double> listDouble = new ArrayList<Double>();
        double desvio = 0;

        if (null != cotacaoList) {
            for (Cotacao cotacao : cotacaoList) {

                Calendar instance = Calendar.getInstance();
                instance.add(Calendar.YEAR, -5);

                Date data = cotacao.getData();

                if (data.after(instance.getTime())) {
                    listDouble.add(cotacao.getFechamento());
                }
            }

            Double[] array = new Double[listDouble.size()];
            listDouble.toArray(array);
            desvio = getDesvioPadrao(array);
        }

        return desvio;

    }

    public double getDesvioPadrao(final Double[] objetos) {
        double mediaAritimetica = getMediaAritimetica(objetos);

        double somatorio = 0;
        for (int i = 0; i < objetos.length; i++) {
            double diferenca = objetos[i] - mediaAritimetica;
            somatorio += diferenca * diferenca;
        }
        somatorio = somatorio / objetos.length;
        return Math.sqrt(somatorio);
    }

    public double getMedianaAritimetica(final Double[] l) {
        Arrays.sort(l);
        int middle = l.length / 2;
        if (l.length % 2 == 0)
        {
            double left = l[middle - 1];
            double right = l[middle];
            return (left + right) / 2;
        }
        else
        {
            return l[middle];
        }

    }

    public double getMediaAritimetica(final Double[] doubleA) {
        double somatorio = 0l;
        for (Double d : doubleA) {
            somatorio += d;
        }
        return somatorio / doubleA.length;
    }
}
