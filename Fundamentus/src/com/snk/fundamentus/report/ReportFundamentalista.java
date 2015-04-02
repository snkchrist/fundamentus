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
    private final int ano;

    private final Empresa empresa;

    public ReportFundamentalista(final Empresa empresa, final int ano) {
        this.empresa = empresa;
        this.ano = ano;
    }

    public int getAno() {
        return ano;
    }

    /*
     * LUCRO POR A��O
     */
    public double getLPA() {
        return ((double) empresa.getDemonstracao12meses().getLucroLiquido() / empresa.getNumeroDeAcoes());
    }

    /**
     * indica que para pre�o e lucro constantes, ser�o necess�rios X anos para
     * recuperar o capital
     * 
     * @return
     */
    public double getPL() {
        double pl = empresa.getCotacao() / getLPA();
        return pl;
    }

    /**
     * Indica a taxa de lucratividade anual da a��o
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
    public double getLiquidezImediata() {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(getAno());
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
     * transformado em dinheiro sem perder valor. A gest�o da liquidez da
     * empresa busca o equil�brio entre os prazos das d�vidas com os prazos dos
     * ativos, a fim de se evitar sua insolv�ncia. Portanto, essa an�lise
     * procura avaliar as condi��es que a empresa tem para saldar suas
     * exigibilidades.�
     *
     * @param ano
     * @return
     */
    public double getLiquidezCorrentePorAno() {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(getAno());
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
    public double getLiquidezSeca() {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(getAno());
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
     * �ndice de liquidez geral retrata quantos reais a empresa disp�e de curto
     * e longo prazos para cobrir cada real de d�vida contra�da. Possui R$ XXX
     * dispon�veis no curto e longo prazos para quitar cada real de d�vida de
     * curto e longo prazos (PINHEIRO. 2009, p. 412).
     *
     * @param ano
     * @return
     */
    public double getLiquidezGeral() {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(getAno());
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
     * empresa girou em determinado per�odo em fun��o das vendas realizadas, que
     * de acordo com Pinheiro (2009, p. 424), este �ndice reflete o grau de
     * utiliza��o dos ativos na gera��o das vendas.
     *
     * @param ano
     * @return
     */
    public double getIndiceRotatividade() {
        List<DemonstrativoResultado> listaDemonstrativoPorAno = getListaDemonstrativoResultadoPorAno(ano);
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(getAno());

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
     * O �ndice de lucratividade reflete a efici�ncia global da empresa na
     * gera��o do lucro resultante de todas as fases do neg�cio da empresa
     *
     * @param ano
     * @return
     */
    public double getIndiceLucratividade() {

        List<DemonstrativoResultado> listaDemonstrativoPorAno = getListaDemonstrativoResultadoPorAno(getAno());
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

    public double getDividaBruta() {

        double dividaBruta = -1;
        BalancoPatrimonial balanco = getBalancoPatrimonialUltimoTrimestre();

        if (null != balanco) {
            dividaBruta = balanco.getEmprestimosEFinanciamentos();
            logger.debug("DividaBruta: " + dividaBruta);
        }

        return dividaBruta;

    }

    public double getDividaLiquida() {
        double dividaBruta = getDividaBruta();
        double disponivilidades = getDisponivilidades();
        double dividaLiquida = -1;
        if (dividaBruta != -1 && disponivilidades != -1) {
            dividaLiquida = dividaBruta - disponivilidades;
            logger.debug("DividaLiquida: " + dividaLiquida);
        }
        return dividaLiquida;
    }

    public double getDisponivilidades() {
        double disponivilidades = -1;
        BalancoPatrimonial balanco = getBalancoPatrimonialUltimoTrimestre();

        if (null != balanco) {
            double caixaEEquivalentesDeCaixa = balanco.getCaixaEEquivalentesDeCaixa();
            double aplicacoesFinanceiras = balanco.getAplicacoesFinanceiras();
            disponivilidades = (caixaEEquivalentesDeCaixa + aplicacoesFinanceiras);
            logger.debug("Disponivilidades: " + disponivilidades);
        }

        return disponivilidades;
    }

    public double getRelacaoDividaLiquidaPatrimonioLiquido() {
        BalancoPatrimonial balanco = getBalancoPatrimonialUltimoTrimestre();
        double relacao = -1;
        if (null != balanco) {
            double dividaLiquida = getDividaLiquida();
            double patrimonioLiquido = balanco.getPatrimonioLiquido();
            relacao = dividaLiquida / patrimonioLiquido;
            logger.debug("Rela��o divida liquida sobre patrimonio liquido: " + relacao);
        }
        return relacao;

    }

    public List<BalancoPatrimonial> getListaBalancoPorAno(final int ano) {

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

    public List<DemonstrativoResultado> getListaDemonstrativoResultadoPorAno(final int ano) {

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

    public double getLiquidezCorrentePorTrimestre(final Trimestre trimestre, final int ano) {

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

    private double getLiquidezCorrente(final BalancoPatrimonial balancoByDate) {
        double liquidezCorrente = -1;

        if (balancoByDate != null) {
            double ativoCirculante = balancoByDate.getAtivoCirculante();
            double passivoCirculante = balancoByDate.getPassivoCirculante();

            liquidezCorrente = ativoCirculante / passivoCirculante;

        }
        return liquidezCorrente;
    }

    /**
     * Para cada real de d�vidas venc�veis a curto prazo, a companhia possui XX
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

    public double getVendasPorAno() {
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = getListaDemonstrativoResultadoPorAno(getAno());

        double vendas = 0;

        for (DemonstrativoResultado demonstrativoResultado : listaDemonstrativoResultadoPorAno) {
            vendas += demonstrativoResultado.getReceitaLiquidaVendasServicos();
        }

        return vendas;

    }

    public double getLucroPorAno(final int ano) {
        double lucroAno = 0;

        List<DemonstrativoResultado> demonstrativoList = getListaDemonstrativoResultadoPorAno(ano);

        if (null != demonstrativoList) {
            for (DemonstrativoResultado demonstrativoResultado : demonstrativoList) {
                lucroAno += demonstrativoResultado.getLucro_PrejuizoPeriodo();

            }
        }

        return lucroAno;
    }

    /**
     * Desenvolvido por James Tobin, em 1969, o 'q' de Tobin � calculado
     * dividindo-se o valor da firma (valor de mercado mais as d�vidas) pelo
     * valor de reposi��o dos seus ativos (VRA). Este representa o desembolso
     * te�rico para se adquirir novamente os bens operacionais da companhia. �
     * obtido pelo seguinte calculo (valor de mercado + passivo circulante +
     * divida de longo prazo - ativo circulante) / valor total dos ativos
     * 
     * @return
     */
    public double getQdeTobin() {
        BalancoPatrimonial balanco = getBalancoPatrimonialUltimoTrimestre();
        double qTobin = 0;

        if (null != balanco) {
            double soma = empresa.getValorMercado() + balanco.getPassivoCirculante()
                + balanco.getEmprestimosEFinanciamentos();
            qTobin = (soma - balanco.getAtivoCirculante()) / balanco.getAtivoTotal();
        }

        return qTobin;

    }

    public boolean isLucroMedio3AnosAcima4PorCento() {
        double percentual = 0;
        int anos = 3;
        logger.debug("#####################" + empresa.getSigla()
            + "#######################");
        logger.debug("Lucro Medio dos �ltimos [" + anos + "] anos. Come�ando em ["
            + getAno() + "]");

        for (int i = anos; i > 0; i--) {
            int anoInicial = ano - i;
            int anoAnterior = ano - (i - 1);

            logger.debug("Contabilizando o lucro do ano de [" + anoAnterior
                + "] menos o ano de [" + anoInicial + "]");
            double lucroPorAno = getLucroPorAno(anoInicial);
            double lucroPorAnoAnterior = getLucroPorAno(anoAnterior);
            logger.debug("Ano: [" + anoAnterior + "] - Lucro: [" + lucroPorAnoAnterior
                + "]");
            logger.debug("Ano: [" + anoInicial + "] - Lucro: [" + lucroPorAno + "]");

            double aumento_reducao = ((lucroPorAnoAnterior - lucroPorAno) / lucroPorAnoAnterior) * 100;
            percentual += aumento_reducao;

            logger.debug("Aumento/redu��o de [" + percentual + "]");
        }

        return (percentual / anos) >= 4;
    }

    private BalancoPatrimonial getBalancoPatrimonialUltimoTrimestre() {

        Calendar ultimoTrimestre = ReportUtil.getUltimoTrimestre(Calendar.getInstance());
        return getBalancoByDate(ultimoTrimestre);
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
}
