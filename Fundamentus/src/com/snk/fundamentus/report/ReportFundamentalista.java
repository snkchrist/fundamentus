package com.snk.fundamentus.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.snk.fundamentus.enums.Trimestre;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.models.Oscilacoes;

public class ReportFundamentalista {

    private static Logger logger = Logger.getLogger(ReportFundamentalista.class);
    private final int ano;

    private final Empresa empresa;

    public ReportFundamentalista(final Empresa empresa) {
        this(empresa, Calendar.getInstance().get(Calendar.YEAR));
    }

    public ReportFundamentalista(final Empresa empresa, final int ano) {
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

    public boolean teveLucroAcoesUltimos5Anos() {
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
        double ebit = -1;

        DemonstrativoResultado resultadoUltimoTrimestre = demonstrativo;

        ebit = resultadoUltimoTrimestre.getResultadoBruto() +
                resultadoUltimoTrimestre.getDespesasComVendas() +
                resultadoUltimoTrimestre.getDespesasGeraisAdministrativas();

        return ebit;

    }

    /**
     * O Lucro antes de Juros e Impostos (LAJIR ou EBIT) 12 meses
     */
    public double getEbit(final List<DemonstrativoResultado> demonstrativoList) {
        double ebit = -1;
        double resultadoBruto = 0;
        double despesasComVendas = 0;
        double despesasGerais = 0;

        if (demonstrativoList.size() >= 4) {
            for (int i = 0; i < 4; i++) {
                resultadoBruto += demonstrativoList.get(i).getResultadoBruto();
                despesasComVendas += demonstrativoList.get(i).getDespesasComVendas();
                despesasGerais += demonstrativoList.get(i).getDespesasGeraisAdministrativas();
            }
        }

        ebit = resultadoBruto +
                despesasComVendas +
                despesasGerais;

        return ebit;

    }

    public double getEbitUltimos12Meses() {
        return getEbit(empresa.getDemonstrativoList());
    }

    /**
     * O Lucro antes de Juros e Impostos (LAJIR ou EBIT) 12 meses
     */
    public double getEbitByAno(final int ano) {
        return getEbit(getListaDemonstrativoResultadoPorAno(ano));
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
     * transformado em dinheiro sem perder valor. A gestão da liquidez da
     * empresa busca o equilíbrio entre os prazos das dívidas com os prazos dos
     * ativos, a fim de se evitar sua insolvência. Portanto, essa análise
     * procura avaliar as condições que a empresa tem para saldar suas
     * exigibilidades.”
     *
     * @param ano
     * @return
     */
    public double getLiquidezCorrenteUltimos12Meses() {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoUltimos12Meses();
        double liquidezCorrente = -1;

        if (listaBalancoPorAno != null) {
            double ativosCirculantes = 0;
            double passivosCirculantes = 0;
            int size = listaBalancoPorAno.size();

            for (int i = 0; i < size; i++) {
                ativosCirculantes += listaBalancoPorAno.get(i).getAtivoCirculante();
                passivosCirculantes += listaBalancoPorAno.get(i).getPassivoCirculante();
            }

            ativosCirculantes = ativosCirculantes / size;
            passivosCirculantes = passivosCirculantes / size;

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
    public double getLiquidezSecaByAno() {
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
     * índice de liquidez geral retrata quantos reais a empresa dispõe de curto
     * e longo prazos para cobrir cada real de dívida contraída. Possui R$ XXX
     * disponíveis no curto e longo prazos para quitar cada real de dívida de
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
     * empresa girou em determinado período em função das vendas realizadas, que
     * de acordo com Pinheiro (2009, p. 424), este índice reflete o grau de
     * utilização dos ativos na geração das vendas.
     *
     * @param ano
     * @return
     */
    public double getIndiceRotatividade() {
        List<DemonstrativoResultado> listaDemonstrativoPorAno = getListaDemonstrativoUltimos12Meses();
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
     * O índice de lucratividade reflete a eficiência global da empresa na
     * geração do lucro resultante de todas as fases do negócio da empresa
     *
     * @param ano
     * @return
     */
    public double getIndiceLucratividade() {

        List<DemonstrativoResultado> listaDemonstrativoPorAno = getListaDemonstrativoUltimos12Meses();
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

    private List<BalancoPatrimonial> getListaBalancoUltimos12Meses() {
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

    public List<DemonstrativoResultado> getListaDemonstrativoUltimos12Meses() {

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

    private double getLiquidezCorrenteByTrimestre(final BalancoPatrimonial balancoByDate) {
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
    public double getLiquidezCorrente() {
        double liquidezCorrente = -1;
        BalancoPatrimonial balancoPatrimonialUltimoTrimestre = getBalancoUltimoTrimestre();

        if (null != balancoPatrimonialUltimoTrimestre) {
            liquidezCorrente = getLiquidezCorrenteByTrimestre(balancoPatrimonialUltimoTrimestre);
        }

        return liquidezCorrente;
    }

    public double getVendasUltimos12Meses() {
        List<DemonstrativoResultado> demonstrativoList = getListaDemonstrativoUltimos12Meses();
        return getVendas(demonstrativoList);
    }

    private double getVendas(final List<DemonstrativoResultado> lst) {
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = lst;

        double vendas = 0;

        for (DemonstrativoResultado demonstrativoResultado : listaDemonstrativoResultadoPorAno) {
            vendas += demonstrativoResultado.getReceitaLiquidaVendasServicos();
        }

        return vendas;
    }

    public double getVendasPorAno() {
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = getListaDemonstrativoResultadoPorAno(getAno());
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
        return getLucroLiquido(getListaDemonstrativoUltimos12Meses());
    }

    public double getLucroLiquidoPorAno(final int ano) {
        List<DemonstrativoResultado> demonstrativoLst = getListaDemonstrativoResultadoPorAno(ano);
        return getLucroLiquido(demonstrativoLst);
    }

    public double getLucroLiquidoAnual() {
        List<DemonstrativoResultado> listaDemonstrativoResultadoPorAno = getListaDemonstrativoResultadoPorAno(getAno());
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
        double qTobin = 0;

        if (null != balanco) {
            double soma = empresa.getValorMercado() + balanco.getPassivoCirculante()
                    + balanco.getEmprestimosEFinanciamentos();
            qTobin = (soma - balanco.getAtivoCirculante()) / balanco.getAtivoTotal();
        }

        return qTobin;

    }

    public double getROICByAno() {
        List<BalancoPatrimonial> listaBalancoPorAno = getListaBalancoPorAno(getAno());

        double ativoTotal = 0;
        double caixa = 0;
        double fornecedores = 0;
        int size = listaBalancoPorAno.size();

        for (int i = 0; i < size; i++) {
            BalancoPatrimonial balancoPatrimonial = listaBalancoPorAno.get(i);

            ativoTotal += balancoPatrimonial.getAtivoTotal();
            caixa += balancoPatrimonial.getCaixaEEquivalentesDeCaixa();
            fornecedores += balancoPatrimonial.getFornecedores();
        }

        //MEDIA ANUAL
        ativoTotal = ativoTotal / size;
        caixa = caixa / size;
        fornecedores = fornecedores / size;
        //MEDIA

        return getROIC(getEbitByAno(ano), ativoTotal, caixa, fornecedores) * 100;
    }

    public double getROICByTrimestre(final BalancoPatrimonial balanco) {
        BalancoPatrimonial balancoPatrimonial = balanco;

        double roic = getROIC(
                getEbitUltimos12Meses(),
                balancoPatrimonial.getAtivoTotal(),
                balancoPatrimonial.getCaixaEEquivalentesDeCaixa(),
                balancoPatrimonial.getFornecedores());

        return roic * 100;
    }

    public double getROIC(final double ebit, final double ativos, final double caixa, final double forcenedores) {
        return ebit / (ativos - caixa - forcenedores);
    }

    public double getROICUltimoTrimestre() {
        return getROICByTrimestre(getBalancoUltimoTrimestre());
    }

    public double getROEUltimoTrimestre() {
        BalancoPatrimonial balancoPatrimonial = getBalancoUltimoTrimestre();

        double roe = getLucroLiquidoUltimos12Meses() /
                balancoPatrimonial.getPatrimonioLiquido();

        return roe * 100;
    }

    public double getAumentoPercentualLucroLiquidoAnoAAno(final int anoAnterior, final int anoSeguinte) {
        double lucroAnoAnterior = getLucroLiquidoPorAno(anoAnterior);
        double lucroAnoSeguinte = getLucroLiquidoPorAno(anoSeguinte);
        double lucroPercentual = 0;

        lucroPercentual = getAumentoPercentual(lucroAnoAnterior, lucroAnoSeguinte);

        return lucroPercentual;
    }

    public double getAumentoPercentualEbitAnoAAno(final int anoAnterior, final int anoSeguinte) {
        double ebitSeguinte = getEbitByAno(anoSeguinte);
        double ebitAnterior = getEbitByAno(anoAnterior);

        return getAumentoPercentual(ebitAnterior, ebitSeguinte);

    }

    private double getAumentoPercentual(final double valor1,
            final double valor2) {
        double lucroPercentual;
        if (valor2 >= valor1) {
            double diff = valor2 - valor1;

            lucroPercentual = (diff / valor1) * 100;
        }
        else {
            double diff = valor1 - valor2;

            lucroPercentual = ((diff / valor2) * 100) - 1;
        }
        return lucroPercentual;
    }

    public boolean isLucroMedio3AnosAcima4PorCento() {
        double percentual = 0;
        int anos = 3;

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

    private BalancoPatrimonial getBalancoUltimoTrimestre() {
        return empresa.getBalancoList().get(0);
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
